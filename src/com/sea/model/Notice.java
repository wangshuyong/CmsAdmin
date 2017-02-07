package com.sea.model;

import java.sql.Timestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
@Entity
@Table(name = "t_notice")
public class Notice {
	
	private int id;
	private String title;
	private String content;
	private String photo;
	private String type;
//	private int userId;
//	private int areaId;
	private Timestamp createTime;
	private Timestamp updateTime;
	private User user;
	private Area area;
	
	@Id
	@Column(updatable=false)
	@GeneratedValue
	public int getId() {
		return id;
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
	public Timestamp getCreateTime() {
		return createTime;
	}
	public Timestamp getUpdateTime() {
		return updateTime;
	}
	public void setId(int id) {
		this.id = id;
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
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	/*public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getAreaId() {
		return areaId;
	}
	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}*/
	@ManyToOne(targetEntity=Area.class,optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="areaId")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
	@ManyToOne(targetEntity=User.class,optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="userId")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}

}
