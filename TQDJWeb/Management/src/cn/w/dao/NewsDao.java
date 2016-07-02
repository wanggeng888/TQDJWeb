package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import cn.j.cloud.bridge.jdcb.Jdcb;
import cn.j.cloud.bridge.jdcb.TableData;
import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.database.MySQL;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SpliceOrderBy;
import cn.j.dataaccesss.sql.constraint.splice.SplicePage;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.bean.News;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.utils.Transfer;

public class NewsDao extends AbstractDao<News> implements IDao {

	private static final Logger LOG = Logger.getLogger(NewsDao.class);
	private Parameters params;
	private TableData tableData;
	private MySQL mysql;

	public NewsDao() {
		params = new Parameters(DATABASE, NEWS_TABLE);
		tableData = Jdcb.getTableData(DATABASE, NEWS_TABLE);
		mysql = new MySQL("mysql");
	}

	@Override
	public String create(News obj) {
		obj.setReadcount(1);
		return String.valueOf(tableData.create(obj));
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
	public String update(News obj) {
		String sql = "UPDATE " + DATABASE + "." + NEWS_TABLE + " SET title='"
				+ obj.getTitle() + "', lead='" + obj.getLead() + "', source='"
				+ obj.getSource() + "', text='" + obj.getText() + "', imgid="
				+ obj.getImgid() + ", time='" + Transfer.getTimeString()
				+ "' WHERE id=" + obj.getId();
		LOG.debug("Sql: " + sql);
		return mysql.update(sql);
	}

	public String updateReadCount(int id, int count) {
		String sql = "UPDATE " + DATABASE + "." + NEWS_TABLE
				+ " SET readcount=" + count + " WHERE id=" + id;
		LOG.debug("Sql: " + sql);
		return mysql.update(sql);
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
				.addOrderBy(SpliceOrderBy.addOrderBy(IService.TIME).desc())
				.addPaging(SplicePage.addPage(page, size)).executeSearch();
		return rowSetToList(rowSet);
	}

}
