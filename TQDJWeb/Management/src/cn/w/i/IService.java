package cn.w.i;

public interface IService {

	public static final String NUM_ERROR = "-1";

	public static final String ID = "id";

	public static final String CODE = "code";

	public static final String SRC = "src";

	public static final String DATA = "data";

	public static final String IMGID = "imgid";

	public static final String SUCCESS = "success";

	public static final String ERROR = "error";

	public static final String TIME = "time";

	public static final String MESSAGE = "message";

	public static final String CURRENT_PAGE = "current_page";

	public static final String TOTAL_PAGE = "total_page";

	public static final String USERNAME = "username";

	public static final String TOKEN = "token";

	public static final int PAGESIZE = 10;
	
	public static final int EXAMSIZE = 50;

	public String create(String data);

	public String delete(String data);

	public String update(String data);

	public String searchSingle(String data); // 单条查询

	public String searchMulti(String data); // 多条查询

}
