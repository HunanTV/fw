package com.hunantv.fw.db;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.beanutils.BeanUtils;

import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.SysConf;
import com.mchange.v2.c3p0.ComboPooledDataSource;

public class FwC3p0 {
	public static final FwLogger logger = new FwLogger(FwC3p0.class);
	private static FwC3p0 c3p0 = null;
	private DataSource dataSource = null;

	private FwC3p0() {
		try {
			Properties pros = new SysConf().read("c3p0.properties");
			dataSource = new ComboPooledDataSource();

			Map<String, Object> dsPros = new HashMap<String, Object>();
			for (Iterator iter = pros.keySet().iterator(); iter.hasNext();) {
				String key = (String) iter.next();
				if (key.startsWith("c3p0.")) {
					dsPros.put(key.substring(5), pros.get(key));
					;
				}
				dsPros.put(key, pros.get(key));
			}
			BeanUtils.populate(dataSource, dsPros);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static FwC3p0 instance() {
		if (c3p0 == null)
			c3p0 = new FwC3p0();
		return c3p0;
	}

	public DataSource getDataSource() {
		return this.dataSource;
	}

	public static void main(String[] args) throws Exception {
		ComboPooledDataSource ds = (ComboPooledDataSource) new FwC3p0().getDataSource();
		System.out.println(ds.getMaxPoolSize());
	}
}
