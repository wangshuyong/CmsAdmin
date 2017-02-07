package com.sea.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.sea.model.Area;
import com.sea.model.User;

public interface UserService {

	public void addUser(User u);

	public void deleteUser(int id);

	public int getUserCount(Area area, String type, String searchText,
			String startTime, String endTime);

	public User getUserById(int id);

	public User getUserByPhone(String phone);

	public boolean isExistsUserName(String userName);

	public void updateUser(User user);

	public boolean isExists(String param);

	public User login(String phone, String pwd);

	public String importUsers(ArrayList<ArrayList<String>> list, Area area);

	public void updatePassword(User user, String pwd);

	public List<User> exportUsers(int areaId, String power, String searchText,
			String startTime, String endTime);

	public List<User> exportUsers(String power, String searchText,
			String startTime, String endTime);

	public List<User> getUsersByPage(Area area, String searchText,
			String startTime, String endTime, int startRows, int pageSize);

	public List<User> getAdminUsersByPage(Area area, String type,
			int startIndex, int pageSize);
}
