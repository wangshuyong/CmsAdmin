package com.sea.service;

import java.util.ArrayList;
import java.util.List;

import com.sea.model.User;

public interface UserService {
	
	public String addUser(User u);
	public void deleteUser(int id);
	public List<User> listUsersByPage(int length,int offset);
	public int getUserCount();
	public User getUserById(int id);
	public User getUserByPhone(String phone);
	public void updateUser(User user);
	public boolean exists(String param);
	public User login(String phone, String pwd);
	public String importUsers(ArrayList<ArrayList<String>> list);
	public void updatePassword (User user,String pwd);
}
