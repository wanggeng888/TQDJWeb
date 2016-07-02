package cn.w.m.tltd;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.w.i.IController;
import cn.w.i.IService;
import cn.w.m.tltd.s.TltdService;
import cn.w.utils.Verify;

@Controller
@RequestMapping("/tltd")
public class TltdController implements IController {

	private IService service;

	public TltdController() {
		service = new TltdService();
	}

	@Override
	@RequestMapping(value = "/03", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String create(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(params);
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		String result = ERROR;
		int id = Verify.token(username, token);
		if (id > 0) {
			result = service.create(data);
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/05", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String delete(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(params);
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		String result = ERROR;
		int id = Verify.token(username, token);
		if (id > 0) {
			result = service.delete(data);
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/04", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String update(@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(params);
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		String result = ERROR;
		int id = Verify.token(username, token);
		if (id > 0) {
			result = service.update(data);
		}
		return result;
	}

	@Override
	@RequestMapping(value = "/{id}", method = RequestMethod.POST, produces = { "application/json;charset=UTF-8" })
	@ResponseBody
	public String search(@PathVariable("id") String pathId,
			@RequestBody String params) {
		JSONObject jsonObj = JSONObject.fromObject(params);
		String username = jsonObj.getString(IService.USERNAME);
		String token = jsonObj.getString(IService.TOKEN);
		String data = jsonObj.getString(IService.DATA);
		String result = ERROR;
		if ("01".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				result = service.searchMulti(data);
			}
		} else if ("02".equals(pathId)) {
			if (Verify.token(username, token) > 0) {
				result = service.searchSingle(data);
			}
		}
		return result;
	}

}
