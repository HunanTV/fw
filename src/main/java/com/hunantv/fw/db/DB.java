package com.hunantv.fw.db;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import com.mysql.jdbc.Statement;


public class DB {
	private JdbcTemplate jdbcTemplate;
	private String name;

	/**
	 * 默认数据库名称
	 */
	public static final String DEFAULT_DB_NAME = "write";
	/**
	 * 默认事务管理器名称
	 */
	private static final String DEFAULT_TX_MANAGER = "txManager";

	public DB() {
		this(DEFAULT_DB_NAME);
	}

	public DB(String name) {
		this.name = name;
		DataSource ds = C3P0.instance().getDataSource(this.name);
		jdbcTemplate = new JdbcTemplate(ds);
	}

	/**
	 * 使用默认数据库和默认事务管理器的配置。
	 * 
	 * @return
	 */
	public Transaction beginTransaction() {
		return this.beginTransaction(DEFAULT_TX_MANAGER, DEFAULT_DB_NAME);
	}

	public Transaction beginTransaction(String transactionName, String name) {
		return new Transaction(transactionName, name);
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

	public <T> T queryForObject(String sql, RowMapper<T> rowMapper, Object... args) {
		return this.jdbcTemplate.queryForObject(sql, rowMapper, args);
	}

	public <T> T queryForObject(String sql, Class<T> requiredType, Object... args) {
		return this.jdbcTemplate.queryForObject(sql, requiredType, args);
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

	public int insertReturnKey(String sql, Object... args) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		this.jdbcTemplate.update(con -> {
			PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			for (int i = 0; i < args.length; i++) {
				ps.setObject(i + 1, args[i]);
			}
			return ps;
		}, keyHolder);
		return keyHolder.getKey().intValue();
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

		private Transaction(String transactionName, String name) {
			DataSource ds = C3P0.instance().getDataSource(name);
			transactionManager = new DataSourceTransactionManager(ds);
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
