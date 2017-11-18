package com.app.wallet.dbconnection;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.wallet.http.JsonElem;

public class DbExecutor {

	private Connection conn;

	private DbExecutor() throws Exception {
		conn = DbConnector.open();
	}

	public static DbExecutor init() throws Exception {
		return new DbExecutor();
	}

	public void close() throws Exception {
		conn.close();
	}

	public int update(String sql) throws Exception {
		System.out.println("LOG >>> " + sql);
		Statement stmt = conn.createStatement();
		int count = stmt.executeUpdate(sql);
		stmt.close();
		return count;
	}

	public List<JsonElem> select(String sql, String[] cols) throws Exception {
		System.out.println("LOG >>> " + sql);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery(sql);
		JsonElem ele = createJsonElem(rs, cols);
		rs.close();
		stmt.close();
		return ele.nodes("data");
	}

	public JsonElem createJsonElem(ResultSet rs, String[] cols) throws Exception {
		JSONArray arr = new JSONArray();
		JSONObject json = new JSONObject();
		while (rs.next()) {
			for (String key : cols) {				
				json.put(key, rs.getObject(key).toString());
			}
			arr.put(json);			
		}
		JSONObject toRet = new JSONObject();
		toRet.put("data", arr);		
		return new JsonElem(toRet.toString());
	}
}
