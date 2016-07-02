package cn.w.bean;

import java.util.Date;

/**
 * 图片
 * 
 * @author WenC
 * 
 */
public class Images {

	private int id;
	private String name; // 文件名
	private String path; // 路径
	private Date crtdate; // 创建时间

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Date getCrtdate() {
		return crtdate;
	}

	public void setCrtdate(Date crtdate) {
		this.crtdate = crtdate;
	}

}
