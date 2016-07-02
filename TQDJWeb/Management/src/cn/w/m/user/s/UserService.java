package cn.w.m.user.s;

import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.bean.Person;
import cn.w.dao.UserDao;
import cn.w.dao.abs.AbstractDao;
import cn.w.i.IService;
import cn.w.utils.Transfer;

public class UserService implements IService {

	private static final Logger LOG = Logger.getLogger(UserService.class);
	private static final String NAME = "name";
	private static final String SEX = "sex";
	private static final String AGE = "age";
	private AbstractDao<Person> dao;

	public UserService() {
		dao = new UserDao();
	}

	@Override
	public String create(String data) {
		return null;
	}

	public String create(int aid, String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String name = jsonObject.getString(NAME);
			String sex = jsonObject.getString(SEX);
			String age = jsonObject.getString(AGE);
			Person person = new Person();
			person.setName(name);
			person.setSex(Integer.parseInt(sex));
			person.setAge(Integer.parseInt(age));
			String id = dao.create(person);
			if (!NUM_ERROR.equals(id)) {
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
				result.put(CODE, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	@Override
	public String delete(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		if (jsonObject != null) {

		}
		return null;
	}

	@Override
	public String update(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String name = jsonObject.getString(NAME);
			String sex = jsonObject.getString(SEX);
			String age = jsonObject.getString(AGE);
			String id = jsonObject.getString(ID);
			Person person = new Person();
			person.setName(name);
			person.setSex(Integer.parseInt(sex));
			person.setAge(Integer.parseInt(age));
			person.setId(Integer.parseInt(id));
			String size = dao.update(person);
			if (!NUM_ERROR.equals(size)) {
				result.put(CODE, SUCCESS);
				result.put(ID, id);
			} else {
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
			Map<String, String> map = ((UserDao) dao).searchByAid(Integer
					.parseInt(id));
			if (!map.isEmpty()) {
				result.put(CODE, SUCCESS);
				result.put(DATA, map);
			} else {
				result.put(CODE, ERROR);
			}
		} else {
			result.put(CODE, ERROR);
		}
		return result.toString();
	}

	public String searchSingle(int id) {
		Map<String, String> map = dao.searchById(id);
		JSONObject result = new JSONObject();
		if (!map.isEmpty()) {
			result.put(CODE, SUCCESS);
			result.put(DATA, map);
		} else {
			map.put(SUCCESS, ERROR);
		}
		return result.toString();
	}

	@Override
	public String searchMulti(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		LOG.debug("data to JSONObject: " + jsonObject);
		if (jsonObject != null) {
			
		}
		return null;
	}

}
