package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.database.MySQL;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.dao.i.IDao;

public class ManagerDao implements IDao {

	private static Logger LOG = Logger.getLogger(ManagerDao.class);
	private static final String USERNAME = "username";
	private static final String TOKEN = "token";
	private MySQL mysql;
	private Parameters params;

	public ManagerDao() {
		params = new Parameters(DATABASE, MANAGER_TABLE);
		mysql = new MySQL("mysql");
	}

	public String signin(String username, String password, String role,
			String token) {
		String sql = "UPDATE " + DATABASE + "." + MANAGER_TABLE
				+ " SET token='" + token + "' WHERE username='" + username
				+ "' AND password='" + password + "' AND role=" + role;
		LOG.info("Sql: " + sql);
		return mysql.update(sql);
	}

	/**
	 * 通过username和token查询
	 * 
	 * @return
	 */
	public int searchUsernameTokenCount(String username, String token) {
		String count = DAQuery
				.search(params)
				.addCount()
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								USERNAME, username)))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(TOKEN,
								token))).executeCount();
		return Integer.parseInt(count);
	}

	public Map<String, String> searchByUsernameToken(String username,
			String token) {
		String sql = "SELECT id from " + DATABASE + "." + MANAGER_TABLE
				+ " WHERE username='" + username + "' AND token='" + token
				+ "'";
		LOG.info("Sql: " + sql);
		List<Map<String, String>> list = mysql.search(sql);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return new HashMap<String, String>();
		}
	}

}
