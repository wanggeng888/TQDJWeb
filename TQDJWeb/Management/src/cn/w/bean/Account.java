package cn.w.bean;

import java.util.Date;

/**
 * 账号
 * 
 * @author WenC
 * 
 */
public class Account {

	private int id;
	private String username;
	private String password;
	private String nickname;
	private String heading;
	private int phone;
	private String email;
	private String token;
	private int state;
	private Date signin;
	private Date signout;
	private Date regist;

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

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getHeading() {
		return heading;
	}

	public void setHeading(String heading) {
		this.heading = heading;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public Date getSignin() {
		return signin;
	}

	public void setSignin(Date signin) {
		this.signin = signin;
	}

	public Date getSignout() {
		return signout;
	}

	public void setSignout(Date signout) {
		this.signout = signout;
	}

	public Date getRegist() {
		return regist;
	}

	public void setRegist(Date regist) {
		this.regist = regist;
	}

}
