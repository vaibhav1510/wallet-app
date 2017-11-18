package com.app.wallet.models;

import java.util.List;

import com.app.wallet.dbconnection.DbConnector;
import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;
import com.app.wallet.utils.AESEncryption;
import com.app.wallet.utils.AESEncryption.Password;

public class User {
	private Long id;
	private String email;
	private String encPassword;
	private String secKey;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public static User getUser(String email) throws Exception {
		String sql = "select * from users where email='" + email + "'";
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "id", "email", "password", "sec_key" };
		List<JsonElem> list = exec.select(sql, cols);		
		if (list.size() == 0) {
			return null;
		}		
		User u = new User();		
		u.setEncPassword(list.get(0).str("password"));
		u.setSecKey(list.get(0).str("sec_key"));
		u.setId(list.get(0).optLong("id"));
		return u;
	}

	public String dbPass() throws Exception {
		return AESEncryption.inst.decrypt(new Password(secKey, encPassword));
	}

	public String getEncPassword() {
		return encPassword;
	}

	public void setEncPassword(String encPassword) {
		this.encPassword = encPassword;
	}

	public String getSecKey() {
		return secKey;
	}

	public void setSecKey(String secKey) {
		this.secKey = secKey;
	}

}
