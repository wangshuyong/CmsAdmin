package com.sea.dao;

import java.util.List;

import com.sea.model.User;

public interface UserDao extends BaseDao<User> {

	public User getUserById(int id);
	public List<User> getUserByPhone(String phone);
	public void delete(int id);
	public List<User> getUsers();
	public List<User> userLogin(String phone, String pwd);
	public List<User> listUserByPage(int startRows,int pageSize);
	public void updateUser(User user);
}
