package com.app.wallet.http;

import java.io.InputStreamReader;
import java.io.*;
import java.net.*;
import java.util.zip.GZIPInputStream;

import org.apache.commons.codec.binary.Base64;
import org.json.*;

import com.google.gdata.util.common.base.CharEscapers;

import java.util.HashMap;
import java.util.Map;
import static javax.servlet.http.HttpServletResponse.*;

public class HttpUtil {

	public enum Method {

		GET, POST, PUT, DELETE;

	}

	/**
	 * To temporarily capture the http response
	 */
	public static class Response {

		public final int httpCode;

		public final Map<String, String> headers;

		public final String content;

		private Response(int httpCode, Map<String, String> headers, String content) {
			this.httpCode = httpCode;
			this.headers = headers;
			this.content = content;
		}

		public boolean isSuccess() {
			return httpCode > 199 && httpCode < 300; // 20x codes
		}

		public boolean is40x() {
			return httpCode > 399 && httpCode < 500;
		}

		public boolean is50x() {
			return httpCode > 499;
		}

		public boolean isAccessError() {
			return httpCode == SC_UNAUTHORIZED || httpCode == SC_FORBIDDEN;
		}

		public boolean isNotFound() {
			return httpCode == SC_NOT_FOUND;
		}

		public XmlElem xml() {
			XmlElem elem = XmlElem.create(content);
			if (elem.name().equalsIgnoreCase("html")) { // html content !!
				throw new RuntimeException("Parse error. Not a Xml content as root node is <html>");
			}
			return elem;
		}

		public JsonElem json() {
			return new JsonElem(jsonContent());
		}

		public JSONObject jsonContent() {
			JSONObject obj;
			try {
				obj = new JSONObject(content);
			} catch (JSONException exp) {
				throw new RuntimeException(content);
			}
			checkRequiredJSONResp(obj);
			return obj;
		}

		public JSONArray jsonArrayContent() {
			JSONArray arr;
			try {
				arr = _jsonArrayContent();
			} catch (JSONException exp) {
				throw new RuntimeException(content);
			}
			return arr;
		}

		public JSONArray _jsonArrayContent() throws JSONException {
			return new JSONArray(content);
		}
	}

	private HttpConfig httpConfig;

	public HttpUtil(HttpConfig config) {
		this.httpConfig = config;
	}

	public Response get(String url, Params params) throws IOException {
		return get(url, params, null);
	}

	public Response get(String url, Params params, Map<String, String> extraHeaders) throws IOException {
		return get(url, params, extraHeaders, true);
	}

	public Response get(String url, Params params, Map<String, String> extraHeaders, boolean throwIfEmptyResponse)
			throws IOException {
		String queryStr = params == null ? null : toQueryStr(params.map());
		if (queryStr != null && !queryStr.isEmpty()) {
			url = url + '?' + queryStr;
		}
		HttpURLConnection conn = createConnection(url, Method.GET, extraHeaders);
		Response resp = sendRequest(conn, throwIfEmptyResponse);
		return resp;
	}

	public Response delete(String url, Params params) throws IOException {
		return delete(url, params, null);
	}

	public Response delete(String url, Params params, Map<String, String> extraHeaders) throws IOException {
		String queryStr = params == null ? null : toQueryStr(params.map());
		if (queryStr != null && !queryStr.isEmpty()) {
			url = url + '?' + queryStr;
		}
		HttpURLConnection conn = createConnection(url, Method.DELETE, extraHeaders);
		Response resp = sendRequest(conn, true);
		return resp;
	}

	public Response delete(String url, String content) throws IOException {
		url = url + '?' + content;
		HttpURLConnection conn = createConnection(url, Method.DELETE, null);
		Response resp = sendRequest(conn, true);
		return resp;
	}

	public Response post(String url, Params params) throws IOException {
		return post(url, params, null);
	}

	public Response post(String url, Params params, Map<String, String> extraHeaders) throws IOException {
		return post(url, params, extraHeaders, true);
	}

	public Response post(String url, Params params, Map<String, String> extraHeaders, boolean throwIfEmptyResp)
			throws IOException {
		return post(url, params == null ? null : toQueryStr(params.map()), extraHeaders, throwIfEmptyResp);
	}

	public Response get(String url, String content) throws IOException {
		String queryStr = content == null ? null : content;
		if (queryStr != null && !queryStr.isEmpty()) {
			url = url + '?' + queryStr;
		}
		HttpURLConnection conn = createConnection(url, Method.GET, null);
		Response resp = sendRequest(conn, true);
		return resp;
	}

	public Response post(String url, String content) throws IOException {
		return post(url, content, null);
	}

	public Response post(String url, String content, Map<String, String> extraHeaders) throws IOException {
		return post(url, content, extraHeaders, true);
	}

	public Response post(String url, String content, Map<String, String> extraHeaders, boolean throwIfEmptyResp)
			throws IOException {
		return doFormSubmit(url, Method.POST, content, extraHeaders, throwIfEmptyResp);
	}

	public Response put(String url, Params params, Map<String, String> extraHeaders) throws IOException {
		return put(url, toQueryStr(params.map()), extraHeaders);
	}

	public Response put(String url, String content) throws IOException {
		return doFormSubmit(url, Method.PUT, content, null, true);
	}

	public Response put(String url, String content, Map<String, String> extraHeaders) throws IOException {
		return doFormSubmit(url, Method.PUT, content, extraHeaders, true);
	}

	private Response doFormSubmit(String url, Method m, String queryStr, Map<String, String> extraHeaders,
			boolean throwIfEmptyResp) throws IOException {
		System.out.println("URL => " + url);
		System.out.println("Query String => " + queryStr);		

		HttpURLConnection conn = createConnection(url, m, extraHeaders);
		writeContent(conn, queryStr);
		Response resp = sendRequest(conn, throwIfEmptyResp);
		/*
		 * if(resp.httpCode > 299){ throw new
		 * RuntimeException(resp.jsonContent.toString(4)); }
		 */
		return resp;
	}

	private static void writeContent(HttpURLConnection conn, String queryStr) throws IOException {
		if (queryStr == null) {
			return;
		}
		OutputStream os = conn.getOutputStream();
		try {
			os.write(queryStr.getBytes("UTF-8"));
		} finally {
			os.close();
		}
	}

	private HttpURLConnection createConnection(String url, Method m, Map<String, String> extraHeaders)
			throws IOException {

		HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
		conn.setRequestMethod(m.name());
		setTimeouts(conn, httpConfig);
		addHeaders(conn, httpConfig, extraHeaders);
		if (m == Method.POST || m == Method.PUT) {
			addHeader(conn, "Content-Type", httpConfig.contentType + ";charset=" + "UTF-8");
			conn.setDoOutput(true);
		}
		conn.setUseCaches(false);
		return conn;
	}

	private static Response sendRequest(HttpURLConnection conn, boolean throwIfEmptyResponse) throws IOException {
		long time = System.currentTimeMillis();
		int httpRespCode;
		try {
			httpRespCode = conn.getResponseCode();
		} finally {
			//
		}
		if (httpRespCode == HttpURLConnection.HTTP_NO_CONTENT) {
			throw new RuntimeException("Got http_no_content response");
		}
		boolean error = httpRespCode < 200 || httpRespCode > 299;
		return new Response(httpRespCode, getRespHeaders(conn), getContentAsString(conn, error, throwIfEmptyResponse));
	}

	private static void setTimeouts(URLConnection conn, HttpConfig config) {
		conn.setConnectTimeout(config.connectTimeout);
		conn.setReadTimeout(config.readTimeout);
	}

	private static void addHeaders(HttpURLConnection conn, HttpConfig config, Map<String, String> extraHeaders) {
		addHeader(conn, "Accept-Charset", "UTF-8");
		if (config.username != null) {
			addHeader(conn, "Authorization", getAuthValue(config));
		}
		addHeader(conn, "Accept", config.respType);
		if (extraHeaders != null && !extraHeaders.isEmpty()) {
			for (Map.Entry<String, String> hdr : extraHeaders.entrySet()) {
				addHeader(conn, hdr.getKey(), hdr.getValue());
			}
		}
	}

	private static void addHeader(HttpURLConnection conn, String headerName, String value) {
		conn.setRequestProperty(headerName, value);
	}

	private static String getAuthValue(HttpConfig config) {
		switch (config.authScheme) {
		case BASIC:
			return "Basic " + Base64
					.encodeBase64String(
							(config.username + ":" + (config.password != null ? config.password : "")).getBytes())
					.replaceAll("\r?", "").replaceAll("\n?", "");
		case BEARER:
			return "Bearer " + config.username;
		default:
			throw new RuntimeException("not expected");
		}
	}

	private static Map<String, String> getRespHeaders(HttpURLConnection conn) {
		Map<String, String> toRet = new HashMap<String, String>();
		for (int i = 0;; i++) {
			String key = conn.getHeaderFieldKey(i);
			String val = conn.getHeaderField(i);
			if (key == null && val == null) { // no more headers
				break;
			}
			if (key != null) {
				toRet.put(key, val);
			}
		}
		return toRet;
	}

	private static String getContentAsString(HttpURLConnection conn, boolean error, boolean throwIfEmptyResponse)
			throws IOException {
		if (error) {
			// throw GlobalUtil.rtExp("");
		}
		InputStream resp = (error) ? conn.getErrorStream() : conn.getInputStream();
		if (resp == null) {
			if (throwIfEmptyResponse)
				throw new RuntimeException("Got Empty Response ");
			else
				return "Got Empty Response";

		}
		try {
			if ("gzip".equalsIgnoreCase(conn.getContentEncoding())) {
				resp = new GZIPInputStream(resp);
			}
			InputStreamReader inp = new InputStreamReader(resp, "UTF-8");
			StringBuilder buf = new StringBuilder();
			char[] buffer = new char[1024];// Should use content length.
			int bytesRead;
			while ((bytesRead = inp.read(buffer, 0, buffer.length)) >= 0) {
				buf.append(buffer, 0, bytesRead);
			}
			String content = buf.toString();
			return content;
		} finally {
			resp.close();
		}
	}

	private static void checkRequiredJSONResp(JSONObject respObj) {
		if (respObj == null) {
			throw new RuntimeException("Expected json formatted content in response");
		}
	}

	public static String toQueryStr(Map<String, String> map) {
		StringJoiner buf = new StringJoiner("&");
		for (Map.Entry<String, String> entry : map.entrySet()) {
			String keyValPair = escQuery(entry.getKey()) + "=" + escQuery(entry.getValue());
			buf.add(keyValPair);
		}
		return buf.toString();
	}

	public static String escQuery(String queryComp) {
		return CharEscapers.uriQueryStringEscaper().escape(queryComp);
	}

}
