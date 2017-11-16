package com.app.wallet.dbconnection;

import java.lang.reflect.Constructor;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import org.json.JSONArray;
import org.json.JSONObject;

import com.app.wallet.http.JsonElem;

public class DbExecutor {

	private Connection conn;
	
	private DbExecutor() throws Exception{
		conn = DbConnector.open();
	}
	
	public static DbExecutor init() throws Exception{		
		return new DbExecutor();
	}
	
	public void close() throws Exception{		
		conn.close();
	}
	
	public void update(String sql) throws Exception {
		Statement stmt  = conn.createStatement();				
		stmt.executeUpdate(sql);
		stmt.close();
	}
	
	public JsonElem select(String sql, String[] cols) throws Exception {
		Statement stmt  = conn.createStatement();		
		ResultSet rs = stmt.executeQuery(sql);		
		JsonElem ele= createJsonElem(rs, cols);
		rs.close();
		stmt.close();
		return ele;
	}
	
	public JsonElem createJsonElem(ResultSet rs, String[] cols) throws Exception{
		JSONObject json = new JSONObject();				
		while(rs.next()){
			for(String key:cols) {
				json.put(key, rs.getObject(key).toString());	
			}			
		}		
		return new JsonElem(json.toString());		
	}
}
