package com.app.wallet.tp.payu;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.app.wallet.models.UserToken;
import com.payu.sdk.PayU;
import com.payu.sdk.model.Language;
import com.payu.sdk.utils.LoggerUtil;

public class PayUClient {

	private static final String PAYMENTS_URL = "https://api.payulatam.com/payments-api/";
	
	private static final String REPORTS_URL = "https://api.payulatam.com/reports-api/";
	
	public PayUClient(String apiKey, String apiLogin) {
		PayU.apiKey = apiKey;
		PayU.apiLogin = apiLogin;
		PayU.language = Language.en;
		PayU.isTest = true;
		LoggerUtil.setLogLevel(Level.ALL);
		
		}
	
	public String getBalance(UserToken tok) throws Exception{
		Map<String, String>  parameters = new HashMap<>();

		parameters.put(PayU.PARAMETERS.TRANSACTION_ID, "");

		parameters.put(PayU.PARAMETERS.ORDER_ID, "");
		return  null;
	}
 	
}
