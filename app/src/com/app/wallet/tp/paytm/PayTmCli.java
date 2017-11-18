package com.app.wallet.tp.paytm;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.app.wallet.http.HttpConfig;
import com.app.wallet.http.HttpUtil;
import com.app.wallet.http.JsonElem;

public class PayTmCli {

	private final HttpUtil http;

	public PayTmCli() {
		http = new HttpUtil(new HttpConfig(""));
	}

	public void requestOtp(String phone) throws Exception {
		try {
			JSONObject params = new JSONObject();
			params.put("phone", phone);
			params.put("clientId", "paytm-pg-client-staging");
			params.put("scope", "wallet");
			params.put("responseType", "token");
			params.put("redirectUri", "https://pguat.paytm.com/oltp-web/oauthResponse");

			JsonElem res = sendRequest("/signin/otp", params.toString());
			System.out.println(res.toString(2));
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}
	
	public void validateOtp(String state, String otp) throws Exception {
		try {
			JSONObject params = new JSONObject();
			params.put("otp", otp);
			params.put("state", state);
			params.put("scope", "paytm");
			params.put("responseType", "token");			

			JsonElem res = sendRequest("/login/validate/otp", params.toString());
			System.out.println(res.toString(2));
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}
	
	public void getBalance(String token) throws Exception {
		try {
			JSONObject params = new JSONObject();
			params.put("MID", PaytmConstants.MID);
			params.put("token", token);					

			JsonElem res = getReq("/login/validate/otp", params.toString());
			System.out.println(res.toString(2));
		} catch (JSONException jex) {
			throw new RuntimeException(jex.getMessage());
		}
	}

	private JsonElem getReq(String path, String content) throws Exception {
		List<JsonElem> elems = getRequest(path, content, false);
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
			return http.post(PaytmConstants.BASE_URL + path, content);
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
	
	
	private List<JsonElem> getRequest(String path, String content, boolean isListReq) throws Exception {
		HttpUtil.Response resp = get(path, content);
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
	
	private HttpUtil.Response get(String path, String content) throws Exception {
		try {
			return http.get(PaytmConstants.BASE_URL + path, content);
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
