package com.app.wallet.test;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;

public class TestMain {
	public static void main()  throws Exception{
		String sql = "select * from users";
		DbExecutor exec = DbExecutor.init();
		String[] cols = {"id", "email", "password"};
		JsonElem ele = exec.select(sql, cols);		
		System.out.println(ele.toString(2));
	}
}