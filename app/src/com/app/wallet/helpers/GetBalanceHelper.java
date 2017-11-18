package com.app.wallet.helpers;

import org.json.JSONObject;

import com.app.wallet.models.App;
import com.app.wallet.models.User;

public class GetBalanceHelper {

	private String email;

	public GetBalanceHelper(String email) {
		this.email = email;
	}

	public JSONObject getBalance() throws Exception {
		JSONObject obj = new JSONObject();
		User u = User.getUser(email);
		App.getApps();
		return obj;
	}
	
	
}
