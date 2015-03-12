package com.hunantv.fw.db;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hunantv.fw.Application;
import com.hunantv.fw.utils.FwLogger;

public class DB {
	protected static FwLogger logger = new FwLogger(DB.class);
	private JdbcTemplate jdbcTemplate;

	public DB() {
		this("jdbcTemplate");
	}

	public DB(String name) {
		jdbcTemplate = Application.getInstance().getSpringCtx().getBean(name, JdbcTemplate.class);
	}

	public Transaction beginTransaction() {
		return this.beginTransaction("txManager");
	}

	public Transaction beginTransaction(String transactionName) {
		return new Transaction(transactionName);
	}

	public List<Map<String, Object>> query(String sql) {
		List<Map<String, Object>> relt = this.jdbcTemplate.queryForList(sql);
		return relt;
	}

	public List<Map<String, Object>> query(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}
	
	public <T> List<T> query(String sql, RowMapper<T> rowMapper, Object... args) {
		return this.jdbcTemplate.query(sql, args, rowMapper);
	}

	public void query(String sql, RowCallbackHandler rowCallbackHandler, Object... args) {
		this.jdbcTemplate.query(sql, args, rowCallbackHandler);
	}

	public Map<String, Object> get(String sql) {
		try {
			return this.jdbcTemplate.queryForMap(sql);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}

	public Map<String, Object> get(String sql, Object... args) {
		try {
			return this.jdbcTemplate.queryForMap(sql, args);
		} catch (EmptyResultDataAccessException ex) {
			return null;
		}
	}
	
	public int count(String sql, Object... args) {
	    return this.jdbcTemplate.queryForInt(sql, args);    
	}

	public int execute(String sql) {
		return this.jdbcTemplate.update(sql);
	}

	public int execute(String sql, Object... args) {
		return this.jdbcTemplate.update(sql, args);
	}

	public int[] batchExecute(String... sql) {
		return this.jdbcTemplate.batchUpdate(sql);
	}

	public int[] batchExecute(String sql, List<Object[]> args) throws DataAccessException {
		return this.jdbcTemplate.batchUpdate(sql, args, new int[0]);
	}

	public class Transaction {
		private DefaultTransactionDefinition def;
		private DataSourceTransactionManager transactionManager;
		private TransactionStatus status;

		private Transaction(String transactionName) {
			transactionManager = Application.getInstance().getSpringCtx()
			        .getBean(transactionName, DataSourceTransactionManager.class);
			def = new DefaultTransactionDefinition();
			def.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRED);
			status = transactionManager.getTransaction(def);
		}

		public void rollback() {
			transactionManager.rollback(status);
		}

		public void commit() {
			transactionManager.commit(status);
		}
	}
}
