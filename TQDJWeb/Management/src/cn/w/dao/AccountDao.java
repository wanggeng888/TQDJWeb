package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SpliceColumns;
import cn.j.dataaccesss.sql.constraint.splice.SplicePage;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.bean.Account;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.utils.Transfer;

public class AccountDao extends AbstractDao<Account> implements IDao {

	private static final String USERNAME = "username";
	private static final String PASSWORD = "password";
	private static final String NICKNAME = "nickname";
	private static final String REGIST = "regist";
	private static final String TOKEN = "token";
	private Parameters params;

	public AccountDao() {
		params = new Parameters(DATABASE, ACCOUNT_TABLE);
	}

	@Override
	public String create(Account obj) {
		return DAQuery
				.insert(params)
				.addColumn(
						SpliceColumns.insertColumns(USERNAME, obj.getUsername()))
				.addColumn(
						SpliceColumns.insertColumns(PASSWORD, obj.getPassword()))
				.addColumn(
						SpliceColumns.insertColumns(NICKNAME, obj.getNickname()))
				.addColumn(
						SpliceColumns.insertColumns(REGIST,
								Transfer.getTimeString())).executeUpdate();
	}

	public String signin(String username, String password, String token) {
		return DAQuery
				.update(params)
				.addColumn(
						SpliceColumns.updateColumns("signin",
								Transfer.getTimeString()))
				.addColumn(SpliceColumns.updateColumns("token", token))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								"username", username)))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								"password", password))).executeUpdate();
	}

	public String autoSignin(String username, String token) {
		return DAQuery
				.update(params)
				.addColumn(
						SpliceColumns.updateColumns("signin",
								Transfer.getTimeString()))
				.addColumn(SpliceColumns.updateColumns("token", token))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								"username", username))).executeUpdate();
	}

	public String signout(String username, String token) {
		String result = DAQuery
				.update(params)
				.addColumn(SpliceColumns.updateColumns("token", null))
				.addColumn(
						SpliceColumns.updateColumns("signout",
								Transfer.getTimeString()))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								"username", username)))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								"token", token))).executeUpdate();
		return result;
	}

	@Override
	public String delete(int[] ids) {
		return null;
	}

	@Override
	public String update(Account obj) {
		return null;
	}

	/**
	 * 通过username和token查询
	 * 
	 * @return
	 */
	public Map<String, String> searchByUsernameToken(String username,
			String token) {
		CachedRowSet rowSet = DAQuery
				.search(params)
				.addColumn(SpliceColumns.searchColumns(IService.ID, null))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								USERNAME, username)))
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(TOKEN,
								token))).executeSearch();
		List<Map<String, String>> list = rowSetToList(rowSet);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return new HashMap<String, String>();
		}
	}

	/**
	 * 通过username和token查询
	 * 
	 * @return
	 */
	public String usernameIsExist(String username) {
		return DAQuery
				.search(params)
				.addCount()
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq(
								USERNAME, username))).executeCount();
	}

	@Override
	public Map<String, String> searchById(int id) {
		return null;
	}

	@Override
	public List<Map<String, String>> searchByPage(int page, int size) {
		CachedRowSet rowSet = DAQuery.search(params)
				.addPaging(SplicePage.addPage(page, size)).executeSearch();
		return rowSetToList(rowSet);
	}

}
