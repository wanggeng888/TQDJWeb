package cn.w.i;

/**
 * 控制类接口
 * 
 * @author WenC
 * 
 */
public interface IController {

	public static final String ERROR = "{\"code\":\"ERROR\"}";

	public static final String DATA = "data";

	public static final String TOKEN = "token";

	public static final String USERNAME = "username";

	public String create(String params);

	public String delete(String params);

	public String update(String params);

	public String search(String pathId, String params);

}
