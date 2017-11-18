package com.app.wallet.tp.paytm;

import java.util.Random;

import com.payu.sdk.model.PaymentMethodComplete;
import java.util.*;

import com.app.wallet.http.HttpConfig;
import com.app.wallet.http.HttpUtil;
import com.paytm.pg.merchant.CheckSumServiceHelper;

public class PayTmClient {

	
	
	
	public void genChecksome() throws Exception {

		Random randomGenerator = new Random();
		int randomInt = randomGenerator.nextInt(1000000);

		Enumeration<String> paramNames = getParamNames();
		Map<String, String[]> mapData = getParameterMap();
		TreeMap<String, String> parameters = new TreeMap<String, String>();
		String paytmChecksum = "";
		while (paramNames.hasMoreElements()) {
			String paramName = (String) paramNames.nextElement();
			parameters.put(paramName, mapData.get(paramName)[0]);
		}
		parameters.put("MID", PaytmConstants.MID);
		parameters.put("CHANNEL_ID", PaytmConstants.CHANNEL_ID);
		parameters.put("INDUSTRY_TYPE_ID", PaytmConstants.INDUSTRY_TYPE_ID);
		parameters.put("WEBSITE", PaytmConstants.WEBSITE);
		parameters.put("MOBILE_NO", "7777777777");
		parameters.put("EMAIL", "test@gmail.com");
		parameters.put("CALLBACK_URL", "http://localhost:8080/app/index.html");

		String checkSum = CheckSumServiceHelper.getCheckSumServiceHelper().genrateCheckSum(PaytmConstants.MERCHANT_KEY,
				parameters);

	}

	public void printt(TreeMap<String, String> parameters) {

		for (Map.Entry<String, String> entry : parameters.entrySet()) {
			String key = entry.getKey();
			String value = entry.getValue();
			System.out.println("<input type='hidden' name='" + key + "' value='" + value + "'>");
		}
		System.out.println("<input type='hidden' name='CHECKSUMHASH' value='\"+checkSum+\"'>");
	}

	public Enumeration<String> getParamNames() {
		// request.getParameterNames();
		return null;
	}

	public Map<String, String[]> getParameterMap() {
		// request.getParameterMap();
		return null;
	}
}
