package cn.w.dao.abs;

import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.RowSet;
import javax.sql.rowset.CachedRowSet;

import org.apache.log4j.Logger;

import cn.j.dataaccesss.bean.Parameters;
import cn.j.dataaccesss.sql.DAQuery;
import cn.w.dao.i.IDao;
import cn.w.i.IService;

public abstract class AbstractDao<T> {

	private static final Logger LOG = Logger.getLogger(AbstractDao.class);

	/**
	 * 新增
	 * 
	 * @param obj
	 * @return
	 */
	public abstract String create(T obj);

	/**
	 * 通过id删除单条
	 * 
	 * @param id
	 * @return
	 */
	public abstract String delete(int[] ids);

	/**
	 * 更新
	 * 
	 * @param obj
	 * @return
	 */
	public abstract String update(T obj);

	/**
	 * 通过id查询单条信息
	 * 
	 * @param id
	 * @return
	 */
	public abstract Map<String, String> searchById(int id);

	/**
	 * 通过分页获得列表
	 * 
	 * @param page
	 * @param size
	 * @return
	 */
	public abstract List<Map<String, String>> searchByPage(int page, int size);

	public List<Map<String, String>> getAllList(String table) {
		CachedRowSet rowSet = DAQuery.search(
				new Parameters(IDao.DATABASE, table)).executeSearch();
		List<Map<String, String>> list = rowSetToList(rowSet);
		if (list == null) {
			list = new ArrayList<Map<String, String>>();
		}
		return list;
	}

	/**
	 * 获得全部的数量
	 * 
	 * @param table
	 * @return
	 */
	public String getAllCount(String table) {
		return DAQuery.search(new Parameters(IDao.DATABASE, table)).addCount()
				.executeCount();
	}

	/**
	 * 获得总页数
	 * 
	 * @param table
	 * @return
	 */
	public int searchTotalPage(String table) {
		int count = Integer.parseInt(DAQuery
				.search(new Parameters(IDao.DATABASE, table)).addCount()
				.executeCount());
		int totalPage = count % IService.PAGESIZE > 0 ? count
				/ IService.PAGESIZE + 1 : count / IService.PAGESIZE == 0 ? 1
				: count / IService.PAGESIZE;
		return totalPage;
	}

	/**
	 * rowset to list
	 * 
	 * @param rowSet
	 * @return
	 * @throws SQLException
	 */
	public List<Map<String, String>> rowSetToList(RowSet rowSet) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		try {
			ResultSetMetaData metaData = rowSet.getMetaData();
			while (rowSet.next()) {
				Map<String, String> map = new HashMap<String, String>();
				int columnCount = metaData.getColumnCount();
				for (int i = 0; i < columnCount; i++) {
					String columnName = metaData.getColumnLabel(i + 1);
					String columnValue = rowSet.getString(columnName);
					map.put(columnName, columnValue);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			LOG.error("rowset to list error", e);
		}
		return list;
	}

}
