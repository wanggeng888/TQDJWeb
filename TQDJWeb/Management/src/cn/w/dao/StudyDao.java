package cn.w.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.CachedRowSet;

import cn.j.cloud.bridge.jdcb.Jdcb;
import cn.j.cloud.bridge.jdcb.TableData;
import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.sql.DAQuery;
import cn.j.dataaccesss.sql.constraint.splice.SplicePage;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhere;
import cn.j.dataaccesss.sql.constraint.splice.SpliceWhereClause;
import cn.w.bean.Study;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;

public class StudyDao extends AbstractDao<Study> implements IDao {

	private Parameters params;
	private TableData tableData;

	public StudyDao() {
		params = new Parameters(DATABASE, STUDY_TABLE);
		tableData = Jdcb.getTableData(DATABASE, STUDY_TABLE);
	}

	@Override
	public String create(Study obj) {
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
	public String update(Study obj) {
		return String.valueOf(tableData.update(obj));
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

}
