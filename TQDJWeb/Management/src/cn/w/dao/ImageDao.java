package cn.w.dao;

import java.util.List;
import java.util.Map;

import cn.j.cloud.bridge.jdcb.TableData;
import cn.j.cloud.bridge.jdcb.processor.JdcbProcessor;
import cn.w.bean.Images;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;

public class ImageDao extends AbstractDao<Images> implements IDao {

	private TableData tableData;

	public ImageDao() {
		tableData = JdcbProcessor.getInstance().getTableData(DATABASE,
				IMAGE_TABLE);
	}

	@Override
	public String create(Images obj) {
		return String.valueOf(tableData.create(obj));
	}

	@Override
	public String delete(int[] ids) {
		return null;
	}

	@Override
	public String update(Images obj) {
		return null;
	}

	@Override
	public Map<String, String> searchById(int id) {
		return tableData.getById(id);
	}

	@Override
	public List<Map<String, String>> searchByPage(int page, int size) {
		return null;
	}

}
