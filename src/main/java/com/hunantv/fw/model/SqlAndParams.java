package com.hunantv.fw.model;

import java.util.ArrayList;
import java.util.List;

public class SqlAndParams {

	private String sql = "";
	private List<Object> params = new ArrayList<Object>();

	public SqlAndParams() {
	}

	public SqlAndParams(String sql, List<Object> params) {
		this.sql = sql;
		this.params = params;
	}

	public String sql() {
		return this.sql;
	}

	public List<Object> params() {
		return this.params;
	}
}
