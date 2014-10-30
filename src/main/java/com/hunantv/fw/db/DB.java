package com.hunantv.fw.db;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.hunantv.fw.Application;

public class DB {

	private JdbcTemplate jdbcTemplate;

	public DB() {
		jdbcTemplate = Application.getInstance().getSpringCtx().getBean("jdbcTemplate", JdbcTemplate.class);
	}

	public Transaction beginTransaction() {
		return new Transaction();
	}

	public List<Map<String, Object>> query(String sql) {
		return this.jdbcTemplate.queryForList(sql);
	}

	public List<Map<String, Object>> query(String sql, Object... args) {
		return this.jdbcTemplate.queryForList(sql, args);
	}

	public Map<String, Object> get(String sql) {
		return this.jdbcTemplate.queryForMap(sql);
	}

	public Map<String, Object> get(String sql, Object... args) {
		return this.jdbcTemplate.queryForMap(sql, args);
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

		private Transaction() {
			transactionManager = Application.getInstance().getSpringCtx()
			        .getBean("txManager", DataSourceTransactionManager.class);
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
