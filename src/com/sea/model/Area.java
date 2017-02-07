package com.sea.model;

import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "t_area")
public class Area {

	private int id;
	private String areaName;
	private String area_description;
	private String fullName;
	private String address;
	private String logo;
	private Timestamp createTime;
	// private Set<User> user= new HashSet<User>();

	public Area() {

	}


	@Id
	@Column(updatable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getArea_description() {
		return area_description;
	}

	public void setArea_description(String area_description) {
		this.area_description = area_description;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public String getLogo() {
		return logo;
	}

	public void setLogo(String logo) {
		this.logo = logo;
	}


	/*
	 * @OneToMany(mappedBy =
	 * "area",cascade={CascadeType.ALL},fetch=FetchType.EAGER) public Set<User>
	 * getUser() { return user; } public void setUser(Set<User> user) {
	 * this.user = user; }
	 */
}
