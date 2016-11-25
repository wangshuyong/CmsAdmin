package com.sea.model;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.Table;
@Entity
@Table(name = "t_content")
public class Content {

	private int id;
	private String titile;
	private String content;
	private String photo;
	private String type;
	private String auther;
	private Timestamp createTime;
	private Timestamp updateTime;
	
	public int getId() {
		return id;
	}
	public String getTitile() {
		return titile;
	}
	public String getContent() {
		return content;
	}
	public String getPhoto() {
		return photo;
	}
	public String getType() {
		return type;
	}
	public String getAuther() {
		return auther;
	}
	public Timestamp getCreateTime() {
		return createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setId(int id) {
		this.id = id;
	}
	public void setTitile(String titile) {
		this.titile = titile;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setAuther(String auther) {
		this.auther = auther;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

}
