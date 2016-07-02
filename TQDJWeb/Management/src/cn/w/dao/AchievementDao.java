package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.database.MySQL;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SpliceColumns;
import cn.j.dataaccesss.sql.constraint.splice.SplicePage;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.bean.Achievement;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.m.exam.s.ExamService;
import cn.w.utils.Transfer;

public class AchievementDao extends AbstractDao<Achievement> implements IDao {

	private static final Logger LOG = Logger.getLogger(ExamDao.class);
	private Parameters params;
	private MySQL mysql;

	public AchievementDao() {
		params = new Parameters(DATABASE, ACHIEVEMENT_TABLE);
		mysql = new MySQL("mysql");
	}

	@Override
	public String create(Achievement obj) {
		DAQuery query = DAQuery.insert(params);
		if (obj.getAid() < 1) {
			LOG.debug("aid illegal");
			return "-1";
		}
		query.addColumn(SpliceColumns.insertColumns(ExamService.AID,
				"" + obj.getAid()));
		if (obj.getScore() == 0) {
			LOG.debug("score illegal");
			return "-1";
		}
		query.addColumn(SpliceColumns.insertColumns(ExamService.SCORE,
				"" + obj.getScore()));
		return query.addColumn(
				SpliceColumns.insertColumns(IService.TIME,
						"" + Transfer.getTimeString())).executeUpdate();
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
	public String update(Achievement obj) {

		DAQuery query = DAQuery.update(params);
		if (obj.getAid() < 1) {
			LOG.debug("aid illegal");
			return "-1";
		}
		query.addColumn(SpliceColumns.updateColumns(ExamService.AID,
				"" + obj.getAid()));
		if (obj.getScore() == 0) {
			LOG.debug("score illegal");
			return "-1";
		}
		query.addColumn(SpliceColumns.updateColumns(ExamService.SCORE,
				"" + obj.getScore()));
		return query.addColumn(
				SpliceColumns.insertColumns(IService.TIME, "" + obj.getTime()))
				.executeUpdate();

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

	@Override
	public List<Map<String, String>> searchByPage(int page, int size) {
		CachedRowSet rowSet = DAQuery.search(params)
				.addPaging(SplicePage.addPage(page, size)).executeSearch();
		return rowSetToList(rowSet);
	}

	public List<Map<String, String>> achievementRecordById(int id) {
		String sql = "SELECT score, time FROM " + DATABASE + "."
				+ ACHIEVEMENT_TABLE + " a WHERE aid='" + id + "' ORDER BY a.time DESC";
		return mysql.search(sql);
	}

}
