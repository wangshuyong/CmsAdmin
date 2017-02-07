package com.sea.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="t_log")
public class Log {

	private int id;
	private String moduleName;
	private String Action;
//	private int operator_id;
	private Date createTime;
	private String operator_result;
	private User user;
	private Area area;
	
	@Id
	@GeneratedValue
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date date) {
		this.createTime = date;
	}

	public String getModuleName() {
		return moduleName;
	}
	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}
	public String getAction() {
		return Action;
	}
	public void setAction(String action) {
		Action = action;
	}
	public String getOperator_result() {
		return operator_result;
	}
	public void setOperator_result(String operator_result) {
		this.operator_result = operator_result;
	}
//	public int getOperator_id() {
//		return operator_id;
//	}
//	public void setOperator_id(int operator_id) {
//		this.operator_id = operator_id;
//	}
	@ManyToOne(targetEntity=User.class,cascade = CascadeType.ALL,optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="operator_id")
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	@ManyToOne(targetEntity=Area.class,cascade = CascadeType.ALL,optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="areaId")
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	
}
