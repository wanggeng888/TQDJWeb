package cn.w.bean;

/**
 * 管理员
 * 
 * @author WenC
 * 
 */
public class Manager {

	private int id;
	private String username;
	private String password;
	private int role; // 角色，0为超级管理员，1为普通管理员
	private String token;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getRole() {
		return role;
	}

	public void setRole(int role) {
		this.role = role;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}
