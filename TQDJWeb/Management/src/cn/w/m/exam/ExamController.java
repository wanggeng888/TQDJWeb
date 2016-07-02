package cn.w.m.exam;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.m.exam.s.ExamService;
import cn.w.utils.Common;
import cn.w.utils.Transfer;
import cn.w.utils.Verify;

@Controller
@RequestMapping("/exam")
public class ExamController implements IMessageCode {

	private ExamService service;

	public ExamController() {
		service = new ExamService();
	}

	@RequestMapping(value = "/01", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String examList(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.examList();
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/02", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String submitScore(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.achievement(id, data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/03", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String createExam(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.createExam(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/04", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String updateExam(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.udpateExam(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/05", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String deleteExam(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.deleteExam(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/06", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String examListByPage(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.examListByPage(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/07", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String examById(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.searchSingle(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@RequestMapping(value = "/08", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String examRecord(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.examRecord(id);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

}
