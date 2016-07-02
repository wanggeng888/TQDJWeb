package cn.w.m.news.s;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import cn.w.bean.News;
import cn.w.dao.CarouselDao;
import cn.w.dao.abs.AbstractDao;
import cn.w.i.IService;
import cn.w.utils.Transfer;

public class CarouselService implements IService {

	private static final Logger LOG = Logger.getLogger(CarouselService.class);
	@Autowired
	private HttpServletRequest request;
	public static final String SOURCE = "source";
	public static final String TITLE = "title";
	public static final String LEAD = "lead";
	public static final String TEXT = "text";
	public AbstractDao<News> dao;

	public CarouselService() {
		dao = new CarouselDao();
	}

	@Override
	public String create(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			LOG.debug("jsonObject: " + jsonObject);
			String title = jsonObject.getString(TITLE);
			String lead = jsonObject.getString(LEAD);
			String source = jsonObject.getString(SOURCE);
			String text = jsonObject.getString(TEXT);
			String imgid = jsonObject.getString(IMGID);
			News news = new News();
			news.setTitle(title);
			news.setLead(lead);
			news.setSource(source);
			news.setText(new StringBuilder(text));
			news.setTime(new Date());
			news.setImgid(Integer.parseInt(imgid));
			String id = dao.create(news);
			LOG.debug("id: " + id);
			if (NUM_ERROR.compareTo(id) < 0) {
				LOG.debug("create new news success");
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
				LOG.debug("create new news error");
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
		if (jsonArray != null && !jsonArray.isEmpty()) {
			int arraySize = jsonArray.size();
			int[] ids = new int[arraySize];
			for (int i = 0; i < arraySize; i++) {
				String id = jsonArray.getJSONObject(i).getString(ID);
				ids[i] = Integer.parseInt(id);
			}
			String size = dao.delete(ids);
			if (!NUM_ERROR.equals(size)) {
				LOG.debug("delete " + arraySize + "s news success");
				result.put(CODE, SUCCESS);
			} else {
				LOG.debug("delete " + arraySize + "s news error");
				result.put(CODE, ERROR);
			}
		} else {
			LOG.debug("delete news error, id datas were null");
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
			String lead = jsonObject.getString(LEAD);
			String source = jsonObject.getString(SOURCE);
			String text = jsonObject.getString(TEXT);
			String imgid = jsonObject.getString(IMGID);
			News news = new News();
			news.setId(Integer.parseInt(id));
			news.setTitle(title);
			news.setLead(lead);
			news.setSource(source);
			news.setText(new StringBuilder(text));
			news.setTime(new Date());
			news.setImgid(Integer.parseInt(imgid));
			String size = dao.update(news);
			if (!NUM_ERROR.equals(size)) {
				LOG.debug("update news success");
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
				LOG.debug("update news error");
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

	public String searchSingle(String urlPath, String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String id = jsonObject.getString(ID);
			Map<String, String> map = dao.searchById(Integer.parseInt(id));
			if (!map.isEmpty()) {
				((CarouselDao) dao).updateReadCount(Integer.parseInt(id),
						Integer.parseInt(map.get("readcount")) + 1);
				String imgid = map.get(IMGID);
				String filepath = null;
				if (imgid != null && !imgid.isEmpty()) {
					filepath = urlPath + "?id=" + imgid;
				}
				map.put(IService.SRC, filepath == null ? "" : filepath);
				String time = map.get(TIME);
				time = time.substring(0, time.length() - 2);
				map.put(TIME, time);
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

	public String searchMulti(String urlPath, String data) {
		JSONObject result = new JSONObject();
		List<Map<String, String>> list = dao.searchByPage(1, 3);
		// 处理返回结果
		for (Map<String, String> map : list) {
			map.remove(TEXT);
			String time = map.get(TIME);
			time = time.substring(0, 10);
			map.put(TIME, time);

			String imgid = map.get(IMGID);
			String filepath = null;
			if (imgid != null && !imgid.isEmpty()) {
				filepath = urlPath + "?id=" + imgid;
			}
			map.put(IService.SRC, filepath == null ? "" : filepath);
		}
		LOG.debug("result carousel list: " + list);
		result.put(CODE, SUCCESS);
		result.put(DATA, list);
		return result.toString();
	}

	@Override
	public String searchMulti(String data) {
		// TODO Auto-generated method stub
		return null;
	}
}
