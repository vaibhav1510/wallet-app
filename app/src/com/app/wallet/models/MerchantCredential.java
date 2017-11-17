package com.app.wallet.models;

public class MerchantCredential {

	public enum AppName {
		CITRUSPAY
	}
	
	private AppName appName;
	private String clientId;
	private String clientToken;
	
	public AppName getAppName() {
		return appName;
	}
	public void setAppName(AppName appName) {
		this.appName = appName;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	public String getClientToken() {
		return clientToken;
	}
	public void setClientToken(String clientToken) {
		this.clientToken = clientToken;
	}
	

}