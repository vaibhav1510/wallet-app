package com.app.wallet.helpers;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;
import com.app.wallet.utils.AESEncryption;
import com.app.wallet.utils.AESEncryption.Password;

public class UserValidator {

	private String email;
	private String passwd;

	private String clientToken;

	public UserValidator(String email, String passwd) {
		this.email = email;
		this.passwd = passwd;
	}

	public Boolean isValid() throws Exception {
		String sql = "select * from users where email='" + email + "'";
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "id", "email", "password", "sec_key" };
		JsonElem ele = exec.select(sql, cols);
		if (ele.keys().size() == 0) {
			return null;
		}
		this.clientToken = ele.str("sec_key");
		String dbPass = AESEncryption.inst.decrypt(new Password(clientToken, ele.str("password")));
		return passwd.equals(dbPass);
	}

	public String clientToken() {
		return clientToken;
	}

	public boolean createNewUser() throws Exception {
		Password password = AESEncryption.inst.encypt(passwd);
		String sql = new StringBuilder()
		.append("insert into users set email='").append(email).append("', password='")
				.append(password.encPasswd).append("', sec_key='").append(password.secretKey).append("'").toString();
		this.clientToken = password.secretKey;
		DbExecutor exec = DbExecutor.init();
		return exec.update(sql) > 1;
	}
}
