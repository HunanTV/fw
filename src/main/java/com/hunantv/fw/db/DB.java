package com.hunantv.fw.db;

import org.springframework.jdbc.core.JdbcTemplate;

import com.hunantv.fw.Application;

public class DB extends JdbcTemplate {

	public DB() {
		super();
		this.setDataSource(Application.getInstance().getDs());
	}

}
