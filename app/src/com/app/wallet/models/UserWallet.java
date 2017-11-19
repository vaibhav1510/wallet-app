package com.app.wallet.models;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;

public class UserWallet {

	private User user;
	private App app;
	private String mobileNum;
	private Double balance;
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public static UserWallet getWallet(User u, String mobile) throws Exception {
		List<UserWallet> toRet = new ArrayList<UserWallet>();
		String sql = "select * from user_wallets where user_id=" + u.getId() + " and mobile_number='" + mobile + "'";
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "id", "user_id", "app_id", "mobile_number", "balance" };
		List<JsonElem> list = exec.select(sql, cols);
		if (list.size() == 0) {
			return null;
		}
		JsonElem e = list.get(0);
		UserWallet uw = new UserWallet();
		uw.setApp(App.getApps().get(e.optLong("app_id")));
		uw.setUser(u);
		uw.setId(e.optLong("id"));
		uw.setBalance(Double.parseDouble(e.str("balance")));
		uw.setMobileNum(e.str("mobile_number"));		
		return uw;
	}
	
	public void dbInsert() throws Exception {
		String sql = new StringBuilder().append("insert into user_wallets set user_id='")
				.append(getUser().getId()).append("', app_id='")
				.append(getApp().getId()).append("', mobile_number='")
				.append(getMobileNum()).append("', balance='")
				.append(getBalance()).append("'")
				.toString();		
		DbExecutor exec = DbExecutor.init();
		exec.update(sql);		
	}
	
	public void dbUpdate() throws Exception {
		String sql = "update user_wallets "
				+ " set balance="+getBalance()
				+ " where id=" + getId();
		DbExecutor exec = DbExecutor.init();		
		exec.update(sql);
	}

	public static List<UserWallet> getWallets(User u) throws Exception {
		List<UserWallet> toRet = new ArrayList<UserWallet>();
		String sql = "select * from user_wallets where user_id=" + u.getId();
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "user_id", "app_id", "mobile_number", "balance" };
		List<JsonElem> list = exec.select(sql, cols);
		if (list.size() == 0) {
			return toRet;
		}
		for (JsonElem e : list) {
			UserWallet uw = new UserWallet();
			uw.setApp(App.getApps().get(e.optLong("app_id")));
			uw.setUser(u);
			uw.setBalance(Double.parseDouble(e.str("balance")));
			uw.setMobileNum(e.str("mobile_number"));
			toRet.add(uw);
		}
		return toRet;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public App getApp() {
		return app;
	}

	public void setApp(App app) {
		this.app = app;
	}

	public String getMobileNum() {
		return mobileNum;
	}

	public void setMobileNum(String mobileNum) {
		this.mobileNum = mobileNum;
	}

	public Double getBalance() {
		return balance;
	}

	public void setBalance(Double balance) {
		this.balance = balance;
	}

	public String category() {
		return app.getCategory();
	}

	public JSONObject toJson() throws Exception {
		JSONObject obj = new JSONObject();
		obj.put("name", app.getName());
		obj.put("balance", balance);
		return obj;
	}
}
