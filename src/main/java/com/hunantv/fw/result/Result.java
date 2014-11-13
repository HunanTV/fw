package com.hunantv.fw.result;

import java.util.Map;

public interface Result {
	public abstract Map<String, Object> toMap();

	public String toJson();
}
