package com.hunantv.fw.cache;

import java.util.Properties;

import com.hunantv.fw.utils.FwLogger;
import com.hunantv.fw.utils.SysConf;

public class LocalCacheConf {
	public static final FwLogger logger = new FwLogger(LocalCacheConf.class);

	public int max_len = 2048;
	public boolean overflowToDisk = false;
	public int expired_seconds = 0;
	public boolean eternal = true;

	public LocalCacheConf() {
		try {
			SysConf conf = new SysConf();
			Properties pros = conf.read("cache.properties");
			Object obj = pros.get("cache.local.length");
			if (null != obj) {
				max_len = Integer.valueOf(((String) obj).trim());
			}
			logger.debug("Local Cache Max Length: " + max_len);

			obj = pros.get("cache.local.expired");
			if (null != obj) {
				expired_seconds = Integer.valueOf(((String) obj).trim());
			}
			if (expired_seconds != 0) {
				eternal = false;
			}
			logger.debug("Local Cache Default Expired Seconds: " + expired_seconds);
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
