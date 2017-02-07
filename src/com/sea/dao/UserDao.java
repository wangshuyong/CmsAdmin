package com.sea.dao;

import java.util.List;

import com.sea.model.User;

public interface UserDao extends BaseDao<User> {

	public User getUserById(int id);

	public List<User> getUserByPhone(String phone);

	public List<User> getUserByUserName(String userName);

	public void delete(int id);

	public List<User> getUsersByPower(String type);

	public List<User> userLogin(String phone, String pwd);

	public List<User> listUserByPage(int startRows, int pageSize, int type);

	public void updateUser(User user);

	public List<User> list(Object[] args);

	public List<User> list(User user);

	public List<User> listAll();

	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int areaId);

	public List<User> getUserByTime(String startTime, String endTime, int areaId);

	public List<User> getUsersByText(String searchText, int areaId);

	public List<User> getUsersByText(String searchText);

	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime);

	public List<User> getUserByTime(String startTime, String endTime);

	public List<User> getUsersBycarId(String carID);

	public List<User> getUsersByAreaIdPower(int areaId, String power);

	public List<User> listUserByPage(int startRows, int pageSize);

	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int areaId, int startRows, int pageSize);

	public List<User> getUserByTime(String startTime, String endTime,
			int areaId, int startRows, int pageSize);

	public List<User> getUsersByText(String searchText, int areaId,
			int startRows, int pageSize);

	public List<User> listUserByPage(int startRows, int pageSize, String type,
			int areaId);

	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int startRows, int pageSize);

	public List<User> getUserByTime(String startTime, String endTime,
			int startRows, int pageSize);

	public List<User> getUsersByText(String searchText, int startRows,
			int pageSize);
}
