package cn.w.m.account.s;

import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;

import cn.w.bean.Account;
import cn.w.dao.AccountDao;
import cn.w.dao.ManagerDao;
import cn.w.dao.abs.AbstractDao;
import cn.w.dao.i.IDao;
import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.utils.Common;
import cn.w.utils.Transfer;

public class AccountService {

	private static final Logger LOG = Logger.getLogger(AccountService.class);
	private static final String NUM_ERROR = "-1";
	private static final String CODE = "code";
	private static final String TOKEN = "token";
	private static final String SUCCESS = "success";
	private static final String ERROR = "error";
	private AbstractDao<Account> accountDao;
	private ManagerDao managerDao;

	public AccountService() {
		accountDao = new AccountDao();
		managerDao = new ManagerDao();
	}

	/**
	 * 注册账号
	 * 
	 * @param username
	 * @param password
	 * @param nickname
	 * @return
	 */
	public String create(String username, String password, String nickname) {
		String pw = Common.md5(password);
		Account account = new Account();
		account.setUsername(username);
		account.setPassword(pw);
		account.setNickname(nickname);
		String id = accountDao.create(account);
		JSONObject jsonObj = new JSONObject();
		if (!NUM_ERROR.equals(id)) {
			LOG.debug("username: " + username + ", regist success");
			jsonObj.put(CODE, SUCCESS);
		} else {
			LOG.debug("username: " + username + ", regist failed");
			jsonObj.put(CODE, ERROR);
		}
		return jsonObj.toString();
	}

	public String signup(String username, String password) {
		String pw = Common.md5(password);
		String token = Common.token();
		JSONObject result = new JSONObject();
		int size = -1;
		if ("admin".equals(username)) {
			size = Integer.parseInt(managerDao.signin(username, pw, "0",
					token));
		} else {
			size = Integer.parseInt(((AccountDao) accountDao).signin(username,
					pw, token));
		}
		if (size > 0) {
			LOG.debug("username: " + username + ", signin success");
			result.put(CODE, SUCCESS);
			result.put(TOKEN, token);
			return result.toString();
		} else {
			LOG.debug("username: " + username + ", signin failed");
			return Common
					.errorResult(IMessageCode.LOGIN_ERROR_USERNAME_PASSWORD_MATCHING);
		}
	}

	/**
	 * 自动登录
	 * 
	 * @param username
	 * @return
	 */
	public String autoSigsnup(String username) {
		JSONObject result = new JSONObject();
		String token = Common.token();
		int size = Integer.parseInt(((AccountDao) accountDao).autoSignin(
				username, token));
		if (size > 0) {
			LOG.debug("username: " + username + ", signin success");
			result.put(CODE, SUCCESS);
			result.put(TOKEN, token);
			return result.toString();
		} else {
			LOG.debug("username: " + username + ", signin failed");
			return Common.errorResult(IMessageCode.VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	public String signout(String username, String token) {
		JSONObject result = new JSONObject();
		int size = Integer.parseInt(((AccountDao) accountDao).signout(username,
				token));
		if (size > 0) {
			LOG.debug("username: " + username + ", signout success");
		} else {
			LOG.debug("username: " + username + ", signout success");
		}
		result.put(CODE, SUCCESS);
		return result.toString();
	}

	public boolean isUsernameExist(String username) {
		int isExist = Integer.parseInt(((AccountDao) accountDao)
				.usernameIsExist(username));
		LOG.debug(isExist);
		if (isExist > 0) {
			LOG.debug("username: " + username + " exist");
			return true;
		} else {
			LOG.debug("username: " + username + " not exist");
			return false;
		}
	}

	/**
	 * 只有管理员账户才能查看账号列表
	 * 
	 * @param username
	 * @param token
	 * @param data
	 * @return
	 */
	public String getAllAccount(String username, String token, String data) {
		JSONObject result = new JSONObject();
		int count = ((ManagerDao) managerDao).searchUsernameTokenCount(
				username, token);
		if (count > 0) {
			JSONObject jsonObject = Transfer.dataToJSONObject(data);
			if (jsonObject != null) {
				String page = jsonObject.getString(IService.CURRENT_PAGE);
				List<Map<String, String>> list = accountDao.searchByPage(
						Integer.parseInt(page), IService.PAGESIZE);
				// 处理返回结果
				for (Map<String, String> map : list) {
					String time = map.get("regist");
					time = time.substring(0, 10);
					map.put("regist", time);
				}
				LOG.debug("result account list: " + list);
				int totalPage = accountDao.searchTotalPage(IDao.ACCOUNT_TABLE);
				result.put(IService.CODE, IService.SUCCESS);
				result.put(IService.TOTAL_PAGE, totalPage);
				result.put(IService.DATA, list);
			} else {
				LOG.debug("data params error");
				result.put(IService.CODE, IService.ERROR);
			}
		} else {
			result.put(IService.CODE, IService.ERROR);
		}
		return result.toString();
	}
}
