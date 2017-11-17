package com.app.wallet.tp.citrus_pay;

import org.apache.http.util.Asserts;

import com.app.wallet.models.MerchantCredential;
import com.app.wallet.models.UserToken;
import com.app.wallet.tp.TpImpl;

public class CitrusPay implements TpImpl {
	
	private String clientId;
	private String clientSecret;
		
	public CitrusPay(MerchantCredential cred) {
		Asserts.check(cred.getAppName() == MerchantCredential.AppName.CITRUSPAY, "not handled");
		this.clientId = cred.getClientId();
		this.clientSecret = cred.getClientToken();
	}

	@Override
	public String balance(UserToken tok) throws Exception {		
		return null;
	}

	@Override
	public String getAccess() throws Exception {
		return null;
	}
	
	
	
}
