package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SpliceColumns;
import cn.j.dataaccesss.sql.constraint.splice.SplicePage;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.bean.Person;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;

public class UserDao extends AbstractDao<Person> implements IDao {

	private static final Logger LOG = Logger.getLogger(UserDao.class);
	private Parameters params;

	public UserDao() {
		params = new Parameters(DATABASE, PINFO_TABLE);
	}

	@Override
	public String create(Person obj) {
		DAQuery query = DAQuery.insert(params);
		if (obj.getAid() > 0) {
			query.addColumn(SpliceColumns.insertColumns("aid",
					"" + obj.getAid()));
			if (obj.getName() != null) {
				query.addColumn(SpliceColumns.insertColumns("name",
						obj.getName()));
			}
			if (obj.getAge() > 0) {
				query.addColumn(SpliceColumns.insertColumns("age",
						"" + obj.getAge()));
			}
			if (obj.getSex() > 0) {
				query.addColumn(SpliceColumns.insertColumns("sex",
						"" + obj.getSex()));
			}
			return query.executeUpdate();
		} else {
			LOG.debug("aid is illegal");
			return "-1";
		}
	}

	@Override
	public String delete(int[] ids) {
		DAQuery query = DAQuery.delete(params);
		for (int i = 0; i < ids.length; i++) {
			query.addOrCondition(SpliceWhere.addWhereClause(SpliceWhereClause
					.eq("id", "" + ids[i])));
		}
		return query.executeUpdate();
	}

	@Override
	public String update(Person obj) {
		DAQuery query = DAQuery.update(params);
		if (obj.getId() > 0) {
			if (obj.getName() != null) {
				query.addColumn(SpliceColumns.updateColumns("name",
						obj.getName()));
			}
			if (obj.getAge() > 0) {
				query.addColumn(SpliceColumns.updateColumns("age",
						"" + obj.getAge()));
			}
			if (obj.getSex() > 0) {
				query.addColumn(SpliceColumns.updateColumns("sex",
						"" + obj.getSex()));
			}
			query.addCondition(SpliceWhere.addWhereClause(SpliceWhereClause.eq(
					"id", "" + obj.getId())));
			return query.executeUpdate();
		} else {
			LOG.debug("id is illegal");
			return "-1";
		}
	}

	@Override
	public Map<String, String> searchById(int id) {
		CachedRowSet rowSet = DAQuery
				.search(params)
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq("id",
								"" + id))).executeSearch();
		List<Map<String, String>> list = rowSetToList(rowSet);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return new HashMap<String, String>();
		}
	}
	
	public Map<String, String> searchByAid(int aid){
		CachedRowSet rowSet = DAQuery
				.search(params)
				.addCondition(
						SpliceWhere.addWhereClause(SpliceWhereClause.eq("aid",
								"" + aid))).executeSearch();
		List<Map<String, String>> list = rowSetToList(rowSet);
		if (!list.isEmpty()) {
			return list.get(0);
		} else {
			return new HashMap<String, String>();
		}
	}

	@Override
	public List<Map<String, String>> searchByPage(int page, int size) {
		CachedRowSet rowSet = DAQuery.search(params)
				.addPaging(SplicePage.addPage(page, size)).executeSearch();
		return rowSetToList(rowSet);
	}

}
