package com.app.wallet.helpers;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.language.bm.PhoneticEngine;
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
//		File f = new File("../../Hackathon/res.json");
//		System.out.println(f.getAbsolutePath());
//		String json = FileUtils.readFileToString(f);		
//		return new JSONObject(json);
		return getBalance1();
	}

	public JSONObject getBalance1() throws Exception {
		User u = User.getUser(email);
		App.getApps();
		List<UserWallet> wallets = UserWallet.getWallets(u);
		JSONObject o= convert(wallets);		
		return o;
	}

	private JSONObject convert(List<UserWallet> uWallets) throws Exception {
		Map<String, List<UserWallet>> phVsWallet = new HashMap<>();
		for (UserWallet uw : uWallets) {
			List<UserWallet> l = phVsWallet.get(uw.getMobileNum());
			if (l == null) {
				l = new ArrayList<>();
			}
			l.add(uw);
			phVsWallet.put(uw.getMobileNum(), l);
		}

		JSONObject toRet = new JSONObject();
		JSONArray phones = new JSONArray();
		
		for(String mobNum: phVsWallet.keySet()) {
			JSONObject phone = new JSONObject();
			phone.put("phone", mobNum);
			JSONArray wallets = new JSONArray();
			JSONArray travels = new JSONArray();
			for(UserWallet uw: phVsWallet.get(mobNum)) {
				JSONObject obj = new JSONObject();
				obj.put("name", uw.getApp().getName());
				obj.put("balance", uw.getBalance());
				if(uw.getApp().getCategory().equals("WALLET")) {
					wallets.put(obj);
				}else {
					travels.put(obj);
				}
			}			
			phone.put("wallet",wallets);
			phone.put("travel",travels);					
			phones.put(phone);
		}		
		toRet.put("data", phones);
		
		System.out.println(toRet.toString(2));
		return toRet;
	}

}
