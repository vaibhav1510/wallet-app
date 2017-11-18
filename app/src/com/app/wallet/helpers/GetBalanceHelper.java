package com.app.wallet.helpers;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import com.app.wallet.models.App;
import com.app.wallet.models.User;
import com.app.wallet.models.UserWallet;

public class GetBalanceHelper {

	private String email;

	public GetBalanceHelper(String email) {
		this.email = email;
	}
	
	
	public JSONObject getBalance() throws Exception {
		File f = new File("../../../../../wallet-app/res.json");		
		System.out.println(f.getAbsolutePath());
		String json = FileUtils.readFileToString(f);
		//getBalance1();
		return new JSONObject(json);		
	}

	public JSONObject getBalance1() throws Exception {
		User u = User.getUser(email);
		App.getApps();
		List<UserWallet> wallets = UserWallet.getWallets(u);
		return convert(wallets);
	}

	private JSONObject convert(List<UserWallet> uWallets) throws Exception {
		Map<String, Map<String, UserWallet>> phVsWallet = new HashMap<>();
		JSONObject toRet = new JSONObject();
		JSONArray phones = new JSONArray();
		toRet.put("data", phones);
		for (UserWallet uw : uWallets) {
		}
		return toRet;
	}

}
