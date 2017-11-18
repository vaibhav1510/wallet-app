package com.app.wallet.tp.paytm;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.wallet.http.HttpConfig;
import com.app.wallet.http.HttpConfig.AuthenticationScheme;
import com.app.wallet.http.HttpUtil;
import com.app.wallet.http.JsonElem;
import com.google.common.collect.ImmutableMap;

public class PayTmCli {

	private final HttpUtil http;

	public PayTmCli() {
		HttpConfig config = new HttpConfig("staging-hackathalon", "51e6d096-56f6-40b4-a2b9-9e0f8fa704b8", "application/json", "application/json;charset=utf-8")
				.authenticationScheme(AuthenticationScheme.BASIC);
		http = new HttpUtil(config);
	}

	public String requestOtp(String phone) throws Exception {
		try {
			JSONObject params = new JSONObject();
			params.put("phone", phone);
			params.put("clientId", "staging-hackathalon");
			params.put("scope", "wallet");
			params.put("responseType", "token");

			JsonElem res = sendRequest(PaytmConstants.BASE_URL + "/signin/otp", params.toString());
			System.out.println(res.toString(2));
			return res.str("state");
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}

	public String validateOtp(String state, String otp) throws Exception {
		try {
			JSONObject params = new JSONObject();
			params.put("otp", otp);
			params.put("state", state);
			params.put("scope", "paytm");
			params.put("responseType", "token");

			JsonElem res = sendRequest(PaytmConstants.BASE_URL + "/signin/validate/otp", params.toString());
			System.out.println(res.toString(2));
			return res.str("access_token");
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}

	public String getBalance(String token) throws Exception {
		try {
//			JSONObject params = new JSONObject();
//			params.put("ssotoken", token);
//			params.put("ssotoken", PaytmConstants.MID);
//			params.put("token", token);
			
			Map<String, String> extraHeaders= ImmutableMap.of("ssotoken",token);
			
			JsonElem res = getReq("https://trust-uat.paytm.in/wallet-web/checkBalance", null, extraHeaders);
			
			System.out.println(res.toString(2));
			return res.node("response").str("amount");
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}

	private JsonElem getReq(String path, String content, Map<String, String> extraHeaders) throws Exception {
		List<JsonElem> elems = getRequest(path, content, false, extraHeaders);
		return elems.get(0);
	}

	private JsonElem sendRequest(String path, String content) throws Exception {
		List<JsonElem> elems = _sendRequest(path, content, false);
		return elems.get(0);
	}

	private List<JsonElem> listRequest(String path, String content) throws Exception {
		return _sendRequest(path, content, true);
	}

	private List<JsonElem> _sendRequest(String path, String content, boolean isListReq) throws Exception {
		HttpUtil.Response resp = post(path, content);
		JsonElem respJson = null;
		List<JsonElem> respElems = new ArrayList<>();
		try {
			if (isListReq) {
				JSONArray jArr = resp._jsonArrayContent();
				for (int i = 0; i < jArr.length(); i++) {
					respElems.add(new JsonElem(jArr.getJSONObject(i)));
				}
			} else {
				respJson = resp.json();
				respElems.add(respJson);
			}
		} catch (JSONException jex) { // non-json-array output
			respJson = resp.json();
		} catch (Exception ignored) { // non-json output
			ignored.printStackTrace();
			throw ignored;
		}
		if (respJson != null) { // is null in case of list request and not an error
			String errJson = respJson.optStr("error");
			if (errJson != null) {
				throw new RuntimeException(respJson.toString());
			}
		}
		return respElems; // errCode cases should not come here
	}

	private HttpUtil.Response post(String path, String content) throws Exception {
		try {
			return http.post(path, content);
		} catch (IOException exp) {
			if (exp instanceof ConnectException || exp instanceof UnknownHostException) {
				throw new RuntimeException("connection timeout");
			} else if (exp instanceof SocketTimeoutException) {
				throw new RuntimeException("runtime timeout");

			} else {
				throw new RuntimeException(exp.getMessage());
			}
		}
	}

	private List<JsonElem> getRequest(String path, String content, boolean isListReq, Map<String, String> extraHeaders) throws Exception {		
		HttpUtil.Response resp = get(path, content, extraHeaders);
		JsonElem respJson = null;
		List<JsonElem> respElems = new ArrayList<>();
		try {
			if (isListReq) {
				JSONArray jArr = resp._jsonArrayContent();
				for (int i = 0; i < jArr.length(); i++) {
					respElems.add(new JsonElem(jArr.getJSONObject(i)));
				}
			} else {
				System.out.println(resp.toString());
				System.out.println(resp.json());
				respJson = resp.json();
				respElems.add(respJson);
			}
		} catch (JSONException jex) { // non-json-array output
			respJson = resp.json();
		} catch (Exception ignored) { // non-json output
			ignored.printStackTrace();
			throw ignored;
		}
		if (respJson != null) { // is null in case of list request and not an error
			String errJson = respJson.optStr("error");
			if (errJson != null) {
				throw new RuntimeException(respJson.toString());
			}
		}
		return respElems; // errCode cases should not come here
	}

	private HttpUtil.Response get(String path, String content, Map<String, String> extraHeaders) throws Exception {
		try {			
			return http.get(path, content, extraHeaders);
		} catch (IOException exp) {
			if (exp instanceof ConnectException || exp instanceof UnknownHostException) {
				throw new RuntimeException("connection timeout");
			} else if (exp instanceof SocketTimeoutException) {
				throw new RuntimeException("runtime timeout");

			} else {
				throw new RuntimeException(exp.getMessage());
			}
		}
	}

}
