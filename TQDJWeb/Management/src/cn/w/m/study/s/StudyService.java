package cn.w.m.study.s;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.bean.Study;
import cn.w.dao.StudyDao;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.utils.Transfer;

public class StudyService implements IService {
	private static final Logger LOG = Logger.getLogger(StudyService.class);

	public static final String SUMMARY = "summary";
	public static final String SOURCE = "source";
	public static final String TITLE = "title";
	public static final String TEXT = "text";
	public AbstractDao<Study> dao;

	public StudyService() {
		dao = new StudyDao();
	}

	@Override
	public String create(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String title = jsonObject.getString(TITLE);
			String summary = jsonObject.getString(SUMMARY);
			String source = jsonObject.getString(SOURCE);
			String text = jsonObject.getString(TEXT);
			Study study = new Study();
			study.setTitle(title);
			study.setSummary(summary);
			study.setSource(source);
			study.setText(new StringBuilder(text));
			study.setTime(new Date());
			String id = dao.create(study);
			if (!NUM_ERROR.equals(id)) {
				LOG.debug("create new study success");
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
				LOG.debug("create new study error");
				result.put(CODE, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	@Override
	public String delete(String data) {
		JSONArray jsonArray = Transfer.dataToJSONOArray(data);
		JSONObject result = new JSONObject();
		if (jsonArray != null) {
			int arraySize = jsonArray.size();
			int[] ids = new int[arraySize];
			for (int i = 0; i < arraySize; i++) {
				String id = jsonArray.getJSONObject(i).getString(ID);
				ids[i] = Integer.parseInt(id);
			}
			String size = dao.delete(ids);
			if (!NUM_ERROR.equals(size)) {
				LOG.debug("delete " + arraySize + "s study success");
				result.put(CODE, SUCCESS);
			} else {
				LOG.debug("create " + arraySize + "s study error");
				result.put(CODE, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	@Override
	public String update(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String id = jsonObject.getString(ID);
			String title = jsonObject.getString(TITLE);
			String summary = jsonObject.getString(SUMMARY);
			String source = jsonObject.getString(SOURCE);
			String text = jsonObject.getString(TEXT);
			Study study = new Study();
			study.setId(Integer.parseInt(id));
			study.setTitle(title);
			study.setSummary(summary);
			study.setSource(source);
			study.setText(new StringBuilder(text));
			study.setTime(new Date());
			String size = dao.update(study);
			if (!NUM_ERROR.equals(size)) {
				LOG.debug("update study success");
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
				LOG.debug("update study error");
				result.put(CODE, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	@Override
	public String searchSingle(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String id = jsonObject.getString(ID);
			Map<String, String> map = dao.searchById(Integer.parseInt(id));
			if (!map.isEmpty()) {
				result.put(CODE, SUCCESS);
				result.put(DATA, map);
			} else {
				map.put(SUCCESS, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	@Override
	public String searchMulti(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String page = jsonObject.getString(CURRENT_PAGE);
			List<Map<String, String>> list = dao.searchByPage(
					Integer.parseInt(page), PAGESIZE);
			for (Map<String, String> map : list) {
				map.remove(TEXT);
				String time = map.get(TIME);
				time = time.substring(0, 10);
				map.put(TIME, time);
			}
			LOG.debug("result study list: " + list);
			int totalPage = dao.searchTotalPage(IDao.STUDY_TABLE);
			result.put(CODE, SUCCESS);
			result.put(TOTAL_PAGE, totalPage);
			result.put(DATA, list);
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

}
