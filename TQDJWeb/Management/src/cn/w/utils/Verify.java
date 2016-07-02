package cn.w.utils;

import java.util.Map;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.w.dao.AccountDao;
import cn.w.dao.ManagerDao;
import cn.w.i.IService;

public class Verify {

	public static final Logger LOG = Logger.getLogger(Verify.class);

	/**
	 * 验证token是否合法
	 * 
	 * @param username
	 * @param token
	 * @return
	 */
	public static int token(String username, String token) {
		int id = -1;
		if (username != null && !username.isEmpty() && token != null
				&& !token.isEmpty()) {
			Map<String, String> map = null;
			if ("admin".equals(username)) {
				ManagerDao dao = new ManagerDao();
				map = dao.searchByUsernameToken(username, token);
			} else {
				AccountDao dao = new AccountDao();
				map = dao.searchByUsernameToken(username, token);
			}
			if (!map.isEmpty()) {
				id = Integer.parseInt(map.get(IService.ID));
			}
			return id;
		} else {
			if (username == null || username.isEmpty()) {
				LOG.debug("usernme is empty, vertify failed");
			}
			if (token == null || token.isEmpty()) {
				LOG.debug("token is empty, vertify failed");
			}
			return id;
		}
	}

	public static boolean username(String username) {
		if (username != null && !username.isEmpty()) {
			Pattern pattern = Pattern.compile("([a-z]|[A-Z]|[0-9]|\\_|\\-)+");
			return pattern.matcher(username).matches();
		}
		LOG.debug("username is null");
		return false;
	}

	public static boolean string(String str) {
		if (str != null) {
			Pattern pattern = Pattern
					.compile(".*[\\.|\\$|\\*|\\&|\\^|\\\\|\\/]a.*");
			if (pattern.matcher(str).matches()) {
				LOG.debug("string has contains illegal character");
				return false;
			}
			return true;
		}
		LOG.debug("str is null");
		return false;
	}

	public static boolean usernameIsExist(String username) {
		AccountDao dao = new AccountDao();
		String count = dao.usernameIsExist(username);
		if (!IService.NUM_ERROR.equals(count)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean password(String password) {
		if (password != null && !password.isEmpty()) {
			if (password.length() >= 7) {
				return true;
			} else {
				LOG.debug("password length illegal");
				return false;
			}
		}
		LOG.debug("password is null");
		return false;
	}

}
