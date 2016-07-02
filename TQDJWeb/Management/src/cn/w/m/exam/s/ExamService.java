package cn.w.m.exam.s;

import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.bean.Achievement;
import cn.w.bean.Exam;
import cn.w.dao.AchievementDao;
import cn.w.dao.ExamDao;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.utils.Common;
import cn.w.utils.Transfer;

public class ExamService {

	private static final Logger LOG = Logger.getLogger(ExamService.class);
	public static final String SUBJECT = "subject";
	public static final String OPTION1 = "option1";
	public static final String OPTION2 = "option2";
	public static final String OPTION3 = "option3";
	public static final String OPTION4 = "option4";
	public static final String AID = "aid";
	public static final String ANSWER = "answer";
	public static final String SCORE = "score";
	public static final String TIME_USED = "time_used";
	private AbstractDao<Exam> examDao;
	private AbstractDao<Achievement> achievementDao;

	public ExamService() {
		examDao = new ExamDao();
		achievementDao = new AchievementDao();
	}

	public String createExam(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String subject = jsonObject.getString(SUBJECT);
			String option1 = jsonObject.getString(OPTION1);
			String option2 = jsonObject.getString(OPTION2);
			String option3 = jsonObject.getString(OPTION3);
			String option4 = jsonObject.getString(OPTION4);
			String anwser = jsonObject.getString(ANSWER);
			Exam exam = new Exam();
			exam.setSubject(subject);
			exam.setOption1(option1);
			exam.setOption2(option2);
			exam.setOption3(option3);
			exam.setOption4(option4);
			exam.setAnswer(Integer.parseInt(anwser));
			exam.setTime(new Date());
			String id = ((ExamDao) examDao).create(exam);
			if (!IService.NUM_ERROR.equals(id)) {
				LOG.debug("create new exam success");
				result.put(IService.CODE, IService.SUCCESS);
				result.put(IService.ID, id);
			} else {
				LOG.debug("create new exam error");
				result.put(IService.CODE, IService.ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String deleteExam(String data) {
		JSONArray jsonArray = Transfer.dataToJSONOArray(data);
		JSONObject result = new JSONObject();
		if (jsonArray != null) {
			int arraySize = jsonArray.size();
			int[] ids = new int[arraySize];
			for (int i = 0; i < arraySize; i++) {
				String id = jsonArray.getJSONObject(i).getString(IService.ID);
				ids[i] = Integer.parseInt(id);
			}
			String size = examDao.delete(ids);
			if (!IService.NUM_ERROR.equals(size)) {
				LOG.debug("delete " + arraySize + "s exam success");
				result.put(IService.CODE, IService.SUCCESS);
			} else {
				LOG.debug("create " + arraySize + "s exam error");
				result.put(IService.CODE, IService.ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String udpateExam(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String id = jsonObject.getString(IService.ID);
			String subject = jsonObject.getString(SUBJECT);
			String option1 = jsonObject.getString(OPTION1);
			String option2 = jsonObject.getString(OPTION2);
			String option3 = jsonObject.getString(OPTION3);
			String option4 = jsonObject.getString(OPTION4);
			String anwser = jsonObject.getString(ANSWER);
			Exam exam = new Exam();
			exam.setId(Integer.parseInt(id));
			exam.setSubject(subject);
			exam.setOption1(option1);
			exam.setOption2(option2);
			exam.setOption3(option3);
			exam.setOption4(option4);
			exam.setAnswer(Integer.parseInt(anwser));
			exam.setTime(new Date());
			LOG.debug("exam: " + exam);
			String size = ((ExamDao) examDao).update(exam);
			if (!IService.NUM_ERROR.equals(size)) {
				LOG.debug("update exam success");
				result.put(IService.CODE, IService.SUCCESS);
				result.put(IService.ID, id);
			} else {
				LOG.debug("update exam error");
				result.put(IService.CODE, IService.ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String achievement(int aid, String data) {
		JSONArray jsonArray = Transfer.dataToJSONOArray(data);
		JSONObject result = new JSONObject();
		if (jsonArray != null) {
			LOG.debug("考试答案：" + jsonArray.toString());
			double gread = 0;
			int examSize = jsonArray.size(); // 考试的题目的数量
			double perGread = 100.0 / IService.EXAMSIZE; // 计算每道题的分数
			for (int i = 0; i < examSize; i++) {
				JSONObject obj = jsonArray.getJSONObject(i);
				String id = obj.getString(IService.ID);
				String anwser = obj.getString(ANSWER);
				Map<String, String> map = examDao.searchById(Integer
						.parseInt(id));
				if (!map.isEmpty()) {
					if (map.get(ANSWER).equals(anwser)) {
						gread += perGread;
					}
				} else {
					LOG.debug("the sujbect of id: " + id + " was not exist");
				}
			}
			// 保存成绩
			Achievement achievement = new Achievement();
			achievement.setAid(aid);
			achievement.setScore(gread);
			String id = achievementDao.create(achievement);
			if (!IService.NUM_ERROR.equals(id)) {
				LOG.debug("exam successful by id: " + aid);
				result.put(IService.CODE, IService.SUCCESS);
				result.put(SCORE, "" + gread);
			} else {
				LOG.debug("exam error by id: " + aid);
				result.put(IService.CODE, IService.ERROR);
				result.put(IService.MESSAGE, IMessageCode.EXAM_SCORE_ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
			result.put(IService.MESSAGE, IMessageCode.EXAM_SCORE_ERROR);
		}
		return result.toString();
	}

	public String examList() {
		JSONObject result = new JSONObject();
		List<Map<String, String>> list = ((ExamDao) examDao).searchAllList();
		if (!list.isEmpty()) {
			result.put(IService.CODE, IService.SUCCESS);
			result.put(IService.DATA, list);
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String examListByPage(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String page = jsonObject.getString(IService.CURRENT_PAGE);
			List<Map<String, String>> list = examDao.searchByPage(
					Integer.parseInt(page), IService.PAGESIZE);
			// 处理返回结果
			for (Map<String, String> map : list) {
				map.remove(OPTION1);
				map.remove(OPTION2);
				map.remove(OPTION3);
				map.remove(OPTION4);
				String time = map.get(IService.TIME);
				time = time.substring(0, 10);
				map.put(IService.TIME, time);
			}
			LOG.debug("result exam list: " + list);
			int totalPage = examDao.searchTotalPage(IDao.EXAM_TABLE);
			result.put(IService.CODE, IService.SUCCESS);
			result.put(IService.TOTAL_PAGE, totalPage);
			result.put(IService.DATA, list);
		} else {
			LOG.debug("data params error");
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String searchSingle(String data) {
		JSONObject jsonObject = Transfer.dataToJSONObject(data);
		JSONObject result = new JSONObject();
		if (jsonObject != null) {
			String id = jsonObject.getString(IService.ID);
			Map<String, String> map = examDao.searchById(Integer.parseInt(id));
			if (!map.isEmpty()) {
				result.put(IService.CODE, IService.SUCCESS);
				result.put(IService.DATA, map);
			} else {
				map.put(IService.SUCCESS, IService.ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}

	public String examRecord(int id) {
		JSONObject result = new JSONObject();
		List<Map<String, String>> list = ((AchievementDao) achievementDao)
				.achievementRecordById(id);
		if (list != null) {
			for (Map<String, String> map : list) {
				Common.filterDate(map);
			}
			result.put(IService.CODE, IService.SUCCESS);
			result.put(IService.DATA, list);
		} else {
			result.put(IService.CODE, IService.ERROR);
			result.put(IService.MESSAGE, IMessageCode.ACHIEVEMENT_RECORD_ERROR);
		}
		return result.toString();
	}

}
