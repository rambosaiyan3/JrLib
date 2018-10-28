package org.com.ramboindustries.corp.sql.test.dao;

import java.sql.SQLException;
import java.util.List;

import org.com.ramboindustries.corp.sql.JDBCConnection;
import org.com.ramboindustries.corp.sql.SQLWhereCondition;
import org.com.ramboindustries.corp.sql.abstracts.SQLMySQLConstants;
import org.com.ramboindustries.corp.sql.test.entity.BaseEntity;

import sun.security.jca.JCAUtil;

public class BaseDAO<E extends BaseEntity> {

	private final String DATABASE = "sistema";
	private final String[] ACCESS = { SQLMySQLConstants.URL_LOCALHOST + DATABASE, "root", "" };

	private JDBCConnection jdbc = null;
	private final boolean SHOW_SQL = true;

	public BaseDAO() {
		jdbc = new JDBCConnection(ACCESS[0], ACCESS[1], ACCESS[2]);
	}

	public List<E> findAll(Class<E> clazz) throws SQLException {
		return jdbc.selectFrom(clazz, SHOW_SQL);
	}

	public void createTable(Class<E> clazz) throws Exception {
		jdbc.createSQLTable(clazz, SHOW_SQL);
		jdbc.commit();
	}

	public void executeSQL(String SQL) throws SQLException {
		jdbc.executeSQL(SQL);
		jdbc.commit();
	}

	public void save(E object)  {
		try {
			jdbc.persistObject(object, SHOW_SQL);
			jdbc.commit();
		} catch (Exception e) {
			//jdbc.rollback();
			e.printStackTrace();
		}
	}
	
	public void update(E object, SQLWhereCondition where) throws Exception {
		jdbc.mergeObject(object, where, SHOW_SQL);
		jdbc.commit();
	}
	
	public void update(E object, List<SQLWhereCondition> where) throws Exception {
		jdbc.mergeObject(object, where, SHOW_SQL);
		jdbc.commit();
	}
	
	public E find(Class<E> clazz, List<SQLWhereCondition> where) throws SQLException {
		return jdbc.findOne(clazz, where, SHOW_SQL);
	}

	public void delete(Class<E> clazz, SQLWhereCondition where) throws SQLException {
		jdbc.deleteObject(clazz, where, SHOW_SQL);
		jdbc.commit();
	}
	
}