package cn.w.m;

import org.apache.log4j.Logger;

import cn.j.cloud.bridge.jdcb.Jdcb;
import cn.j.cloud.bridge.jdcb.TableClient;
import cn.w.bean.Account;
import cn.w.bean.Achievement;
import cn.w.bean.Exam;
import cn.w.bean.Images;
import cn.w.bean.Manager;
import cn.w.bean.News;
import cn.w.bean.Person;
import cn.w.bean.Study;
import cn.w.bean.Tltd;
import cn.w.dao.i.IDao;

public class InitDB {

	private static final Logger LOG = Logger.getLogger(InitDB.class);

	public boolean isInit() {
		// 初始化数据库
		if (!createDB()) {
			LOG.debug("init create db error.");
			return false;
		}
		return true;
	}

	private boolean createDB() {
		TableClient tableClient = Jdcb.getTableClient(IDao.DATABASE);
		if (!tableClient.isExist()) {
			if (!tableClient.createDatabase()) {
				return false;
			}
		}
		// 管理账号表
		if (!tableClient.isExist(IDao.MANAGER_TABLE)) {
			LOG.debug("table tltd not exist.");
			Manager manager = new Manager();
			if (!tableClient.createTable(IDao.MANAGER_TABLE, manager)) {
				return false;
			}
		}
		// 账号表
		if (!tableClient.isExist(IDao.ACCOUNT_TABLE)) {
			LOG.debug("table account not exist.");
			Account account = new Account();
			if (!tableClient.createTable(IDao.ACCOUNT_TABLE, account)) {
				return false;
			}
		}
		// 成绩表
		if (!tableClient.isExist(IDao.ACHIEVEMENT_TABLE)) {
			LOG.debug("table achievement not exist.");
			Achievement achievement = new Achievement();
			if (!tableClient.createTable(IDao.ACHIEVEMENT_TABLE, achievement)) {
				return false;
			}
		}
		// 考试表
		if (!tableClient.isExist(IDao.EXAM_TABLE)) {
			LOG.debug("table exam not exist.");
			Exam exam = new Exam();
			if (!tableClient.createTable(IDao.EXAM_TABLE, exam)) {
				return false;
			}
		}
		// 新闻表
		if (!tableClient.isExist(IDao.NEWS_TABLE)) {
			LOG.debug("table news not exist.");
			News news = new News();
			if (!tableClient.createTable(IDao.NEWS_TABLE, news)) {
				return false;
			}
		}
		// 个人信息表
		if (!tableClient.isExist(IDao.PINFO_TABLE)) {
			LOG.debug("table pinfo not exist.");
			Person pserson = new Person();
			if (!tableClient.createTable(IDao.PINFO_TABLE, pserson)) {
				return false;
			}
		}
		// 学习表
		if (!tableClient.isExist(IDao.STUDY_TABLE)) {
			LOG.debug("table study not exist.");
			Study study = new Study();
			if (!tableClient.createTable(IDao.STUDY_TABLE, study)) {
				return false;
			}
		}
		// 两学一做表
		if (!tableClient.isExist(IDao.TLTD_TABLE)) {
			LOG.debug("table tltd not exist.");
			Tltd tltd = new Tltd();
			if (!tableClient.createTable(IDao.TLTD_TABLE, tltd)) {
				return false;
			}
		}
		// 图片表
		if (!tableClient.isExist(IDao.IMAGE_TABLE)) {
			LOG.debug("table tltd not exist.");
			Images images = new Images();
			if (!tableClient.createTable(IDao.IMAGE_TABLE, images)) {
				return false;
			}
		}
		// 轮播图
		if (!tableClient.isExist(IDao.CAROUSEL_TABLE)) {
			LOG.debug("table tltd not exist.");
			News news = new News();
			if (!tableClient.createTable(IDao.CAROUSEL_TABLE, news)) {
				return false;
			}
		}
		return true;
	}

}
