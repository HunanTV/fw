package com.hunantv.fw.utils;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;

import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FwC3p0 {

	public DataSource getDataSource() throws Exception {
		Properties pros = new SysConf().read("c3p0.properties");
		DataSource ds = new ComboPooledDataSource();

		Map<String, Object> dsPros = new HashMap<String, Object>();
		for (Iterator iter = pros.keySet().iterator(); iter.hasNext();) {
			String key = (String) iter.next();
			if (key.startsWith("c3p0.")) {
				System.out.println(key.substring(5));
				dsPros.put(key.substring(5), pros.get(key));;
			}
			dsPros.put(key, pros.get(key));
		}
		BeanUtils.populate(ds, dsPros);
		return ds;
	}
	
	public static void main(String[] args) throws Exception {
		ComboPooledDataSource ds = (ComboPooledDataSource) new FwC3p0().getDataSource();
		System.out.println(ds.getMaxPoolSize());
	}
}
