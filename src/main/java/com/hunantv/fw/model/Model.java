package com.hunantv.fw.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

import com.hunantv.fw.utils.Inflection;

public class Model {
	protected Map<String, Object> data = new HashMap<String, Object>();
	private String tableName;

	public Model() {
		tableName = this.tableName();
	}

	public Model(Map<String, Object> data) {
		this.data = data;
	}

	public Object get(String attrName) {
		return this.data.get(attrName);
	}

	public void set(String attrName, Object attrValue) {
		this.data.put(attrName, attrValue);
	}

	@Override
	public String toString() {
		return "DyModel [data=" + data + "]";
	}

	/**
	 * Class Name: Table Name:
	 * ---------------------------------------------------
	 * com.example.model.User users com.example.model.LineItem line_items
	 * com.example.model.UserAccount user_accounts com.example.model.UserURL
	 * user_urls
	 */
	public String tableName() {
		String className = Util.getShortClassName(this.getClass());
		String tname = className;
		tname = Inflection.pluralize(Inflection.underscore(tname));
		return tname;
	}

	public Set<String> columns() {
		return this.data.keySet();
	}

	public SqlAndParams update_misc() {
		List<String> markSql = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		this.columns().forEach(column -> {
			if (!column.equals(this.pkColumn())) {
				markSql.add(column + " = ?");
				params.add(this.get(column));
			}
		});

		StringBuilder strb = new StringBuilder();
		strb.append("update ").append(this.tableName).append(" set ").append(StringUtils.join(markSql, ", "));
		strb.append(" where id = ?");
		params.add(this.id());
		return new SqlAndParams(strb.toString(), params);
	}

	public String pkColumn() {
		return "id";
	}

	public Object id() {
		return this.get(this.pkColumn());
	}

	public SqlAndParams create_misc() {
		List<String> cs = new ArrayList<>();
		List<String> markSql = new ArrayList<>();
		List<Object> params = new ArrayList<>();
		this.columns().forEach(column -> {
			markSql.add("?");
			params.add(this.get(column));
			cs.add(column);
		});

		StringBuilder strb = new StringBuilder();
		strb.append("insert into ").append(this.tableName).append(" (").append(StringUtils.join(cs, ", ")).append(")");
		strb.append(" values (").append(StringUtils.join(markSql, ", ")).append(")");
		return new SqlAndParams(strb.toString(), params);
	}
}
