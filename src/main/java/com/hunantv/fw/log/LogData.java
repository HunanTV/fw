package com.hunantv.fw.log;

import java.util.ArrayList;
import java.util.List;

import com.hunantv.fw.utils.SeqID;

public class LogData {

	private static ThreadLocal<LogData> local = new ThreadLocal<LogData>() {
		@Override
		protected LogData initialValue() {
			return new LogData();
		}
	};

	private String id;
	private List<String> data;

	public static LogData instance() {
		return local.get();
	}

	private LogData() {
		this.id = SeqID.rnd().toString();
		this.data = new ArrayList<String>();
	}

	public List<String> getData() {
		return this.data;
	}

	public String getId() {
		return local.get().id;
	}

	public void setId(String id) {
		local.get().id = id;
	}

	public void add(String key, String value) {
		local.get().data.add(key + "=" + value);
	}

	public void clear() {
		local.remove();
	}

	public String toString() {
		List<String> d = local.get().data;
		if (d.size() == 0)
			return "";
		StringBuilder strb = new StringBuilder();
		strb.append("[ ");
		for (String ele : d) {
			strb.append(ele).append(" ");
		}
		strb.append("]");
		return strb.toString();
	}
}
