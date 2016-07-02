package cn.w.m.news;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.w.i.IController;
import cn.w.i.IService;
import cn.w.m.IMessageCode;
import cn.w.m.news.s.NewsService;
import cn.w.utils.Common;
import cn.w.utils.Transfer;
import cn.w.utils.Verify;

@Controller
@RequestMapping("/news")
public class NewsController implements IController, IMessageCode {

	private IService service;

	public NewsController() {
		service = new NewsService();
	}

	@Override
	@RequestMapping(value = "/03", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String create(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.create(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@Override
	@RequestMapping(value = "/05", method = RequestMethod.POST)
	@ResponseBody
	public String delete(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.delete(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@Override
	@RequestMapping(value = "/04", method = RequestMethod.POST)
	@ResponseBody
	public String update(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		int id = Verify.token(username, token);
		if (id > 0) {
			return service.update(data);
		} else {
			return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
		}
	}

	@Override
	public String search(@PathVariable("id") String pathId,
			@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		if ("01".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				return service.searchMulti(data);
			} else {
				return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
			}
		} else if ("02".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				return service.searchSingle(data);
			} else {
				return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
			}
		} else {
			return Common.errorResult(PARAMS_ERROR);
		}
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String search(@PathVariable("id") String pathId,
			HttpServletRequest req, @RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(Transfer.urlDecode(params));
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		if ("01".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				String path = req.getRequestURL().toString();
				path = path.substring(0, path.length() - 8) + "/images/01";
				return service.searchMulti(data);
			} else {
				return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
			}
		} else if ("02".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				String path = req.getRequestURL().toString();
				path = path.substring(0, path.length() - 8) + "/images/01";
				return ((NewsService) service).searchSingle(path, data);
			} else {
				return Common.errorResult(VERIFY_ERROR_TOKEN_ERROR);
			}
		} else {
			return Common.errorResult(PARAMS_ERROR);
		}
	}

}
