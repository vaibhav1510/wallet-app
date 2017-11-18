package com.app.wallet.models;

import java.util.ArrayList;
import java.util.List;

import com.app.wallet.dbconnection.DbExecutor;
import com.app.wallet.http.JsonElem;

public class App {
	private Long id;
	private String name;
	private String category;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public static List<App> getApps() throws Exception {
		List<App> toRet = new ArrayList<App>();
		String sql = "select * from apps";
		DbExecutor exec = DbExecutor.init();
		String[] cols = { "id", "name", "category" };
		List<JsonElem> list = exec.select(sql, cols);
		if (list.size() == 0) {
			return toRet;
		}
		for (JsonElem e : list) {
			App a = new App();
			a.setId(e.optLong("id"));
			a.setName(e.str("name"));
			a.setCategory(e.str("category"));
			toRet.add(a);
		}
		return toRet;
	}
}
