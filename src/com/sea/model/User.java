package com.sea.model;

import java.lang.reflect.Field;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.apache.struts2.json.annotations.JSON;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "t_user")
public class User {

	private int id;
	private String phone;
	private String password;
	private String email;
	private String sex;
	private String realName;
	private String power;
	private String address;
	private String photo;
	private String carID;
	private String idPhoto;
	private int age;
	private String hobby;
	private String nativePlace;
	private Timestamp createTime;
	private Timestamp updateTime;
	private Timestamp lastLoginTime;
	private String userName;
	private Area area;
//	 private int areaId;
	// private Set<Log> log;

	// private Set<AreaUser> areaUser;

	public String getEmail() {
		return email;
	}

	@Id
	@Column(updatable = false)
	@GeneratedValue
	public int getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}

	public String getPhone() {
		return phone;
	}

	public String getSex() {
		return sex;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getHobby() {
		return hobby;
	}

	public void setHobby(String hobby) {
		this.hobby = hobby;
	}

	public String getNativePlace() {
		return nativePlace;
	}

	public void setNativePlace(String nativePlace) {
		this.nativePlace = nativePlace;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Timestamp getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Timestamp updateTime) {
		this.updateTime = updateTime;
	}

	public Timestamp getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Timestamp lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}

	public Timestamp getCreateTime() {
		return createTime;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCarID() {
		return carID;
	}

	public void setCarID(String carID) {
		this.carID = carID;
	}

	public String getIdPhoto() {
		return idPhoto;
	}

	public void setIdPhoto(String idPhoto) {
		this.idPhoto = idPhoto;
	}
	/*
	public int getAreaId() {
		return areaId;
	}

	public void setAreaId(int areaId) {
		this.areaId = areaId;
	}
	
	@OneToMany(mappedBy = "user")
	public Set<AreaUser> getAreaUser() {
		return areaUser;
	}

	public void setAreaUser(Set<AreaUser> areaUser) {
		this.areaUser = areaUser;
	}*/

	public Object getField(String name) {
		try {
			Field f = this.getClass().getDeclaredField(name);
			Object o = f.get(this);
			java.text.DateFormat format1 = new java.text.SimpleDateFormat(
					"yyyy-MM-dd hh:mm:ss");
			if (f.getType().equals(Date.class)) {
				o = format1.format(new Date());
			}
			return o;
		} catch (Exception e) {
			return null;
		}
	}

	@ManyToOne(targetEntity=Area.class,optional = false,fetch = FetchType.LAZY)
    @JoinColumn(name="areaId")
	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	// public Set<Log> getLog() {
	// return log;
	// }
	//
	// public void setLog(Set<Log> log) {
	// this.log = log;
	// }

}
