package com.app.wallet.helpers;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;

public class UserValidator {

	private String email;
	private String passwd;

	public UserValidator(String email, String passwd) {
		this.email = email;
		this.passwd = passwd;
	}

	public boolean isValid() throws Exception {
		String sql = "select * from users where email='" + email + "'";
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "id", "email", "password" };
		JsonElem ele = exec.select(sql, cols);
		return passwd.equals(ele.str("password"));
	}
	
	public boolean createNewUser() throws Exception{
		String sql =new StringBuilder() 
				.append("insert into users values ('','")
				.append(email)
				.append("','")
				.append(passwd)
				.append("'")
				.toString();

		DbExecutor exec = DbExecutor.init();		
		return exec.update(sql) > 1;
	}
}
