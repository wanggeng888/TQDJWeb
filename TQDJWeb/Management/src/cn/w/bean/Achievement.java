package cn.w.bean;

import java.util.Date;

/**
 * 成绩对象
 * 
 * @author WenC
 * 
 */
public class Achievement {

	private int id;
	private int aid;
	private double score;
	private Date time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getAid() {
		return aid;
	}

	public void setAid(int aid) {
		this.aid = aid;
	}

	public double getScore() {
		return score;
	}

	public void setScore(double score) {
		this.score = score;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

}
