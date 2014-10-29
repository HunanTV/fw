package com.hunantv.fw.dao;

import javax.sql.DataSource;

public class Dao {
    private DataSource ds;

    public DataSource getDs() {
	return ds;
    }

    public void setDs(DataSource ds) {
	this.ds = ds;
    }
}
