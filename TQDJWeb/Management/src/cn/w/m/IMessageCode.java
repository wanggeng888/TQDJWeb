package cn.w.m;

public interface IMessageCode {

	public static final int REGIST_ERROR_USERNAME_EXIST = 1001; // 用户名已存在

	public static final int REGIST_ERROR_PASSWORD_LENGTH_ILLEGAL = 1002; // 密码长度不合法

	public static final int REGIST_ERROR_USERNAME_ILLEGAL = 1003; // 用户名不合法

	public static final int LOGIN_ERROR_USERNAME_PASSWORD_MATCHING = 2001; // 用户名密码错误

	public static final int VERIFY_ERROR_TOKEN_ERROR = 3001; // token验证失败

	public static final int SIGNOUT_ERROR = 4001; // 注销失败

	public static final int PARAMS_ERROR = 5001; // 参数错误

	public static final int UPLOAD_IMAGE_ERROR = 6001;// 文件上传失败
	
	public static final int EXAM_SCORE_ERROR = 7001; // 考试成绩后台计算错误
	
	public static final int ACHIEVEMENT_RECORD_ERROR = 8001; // 成绩查询失败

}
