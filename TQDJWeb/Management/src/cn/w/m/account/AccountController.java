package cn.w.m.account;

import java.io.UnsupportedEncodingException;

import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.m.account.s.AccountService;
import cn.w.utils.Common;
import cn.w.utils.Transfer;
import cn.w.utils.Verify;

@Controller
@RequestMapping("/acct")
public class AccountController {

	private static final Logger LOG = Logger.getLogger(AccountController.class);
	private static final String PASSWORD = "password";
	private AccountService service;

	public AccountController() {
		service = new AccountService();
	}

	/**
	 * 注册账号
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/01", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String create(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String password = jsonObj.getString(PASSWORD);
		String nickname = username;
		if (Verify.username(username)) {
			if (Verify.password(password)) {
				if (!service.isUsernameExist(username)) {
					return service.create(username, password, nickname);
				} else {
					LOG.debug("username exist");
					return Common
							.errorResult(IMessageCode.REGIST_ERROR_USERNAME_EXIST);
				}
			} else {
				LOG.info("Verify password failed");
				return Common
						.errorResult(IMessageCode.REGIST_ERROR_PASSWORD_LENGTH_ILLEGAL);
			}
		} else {
			LOG.debug("Verify username and password failed");
			return Common
					.errorResult(IMessageCode.REGIST_ERROR_USERNAME_ILLEGAL);
		}
	}

	/**
	 * 登录
	 * 
	 * @param username
	 * @param password
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/02", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String login(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String password = jsonObj.getString(PASSWORD);
		if (Verify.username(username)) {
			return service.signup(username, password);
		} else {
			return Common
					.errorResult(IMessageCode.REGIST_ERROR_USERNAME_ILLEGAL);
		}
	}

	/**
	 * 注销
	 * 
	 * @param username
	 * @param token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/03", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String logout(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		if (Verify.token(username, token) > 0) {
			return service.signout(username, token);
		} else {
			return Common.errorResult(IMessageCode.SIGNOUT_ERROR);
		}
	}

	/**
	 * 管理员查询账号列表
	 * 
	 * @param username
	 * @param token
	 * @param data
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/04", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String searchAcctList(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		return service.getAllAccount(username, token, data);
	}

	/**
	 * app自动登录验证token
	 * 
	 * @param username
	 * @param token
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@RequestMapping(value = "/05", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String autoSignIn(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		if (Verify.token(username, token) > 0) {
			return service.autoSigsnup(username);
		} else {
			return Common.errorResult(IMessageCode.VERIFY_ERROR_TOKEN_ERROR);
		}
	}
}
