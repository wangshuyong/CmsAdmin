package com.sea.dao.impl;

import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.UserDao;
import com.sea.model.Notice;
import com.sea.model.User;

@Component("userDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {

	@Override
	public User getUserById(int id) {
		// User user = this.load(id);
		String hql = "from User n inner join fetch n.area inner join fetch n.area where n.id=?";
		List<User> list = this.list(hql, id);
		User user = null;
		for (int i = 0; i < list.size(); i++) {
			user = (User) list.get(i);
		}
		return user;
	}

	@Override
	public void delete(int id) {
		User u = this.load(id);
		this.delete(u);
	}

	@Override
	public void updateUser(User user) {
		this.getHibernateTemplate().update(user);
	}

	@Override
	public List<User> getUsersByPower(String type) {
		String hql = "from User u inner join fetch u.area where u.power="
				+ type + " order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUserByPhone(String phone) {
		String hql = "from User u inner join fetch u.area where u.phone=?";
		return this.list(hql, phone);
	}

	@Override
	public List<User> getUserByUserName(String userName) {
		String hql = "from User u inner join fetch u.area where u.userName=?";
		return this.list(hql, userName);
	}

	@Override
	public List<User> getUsersByAreaIdPower(int areaId, String type) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.power=" + type + " order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> userLogin(String phone, String pwd) {
		String hql = "from User u inner join fetch u.area where u.password= '"+ pwd +"'  and u.phone= '"
				+ phone + "' or u.userName= '" + phone + "'";
		return this.list(hql);
	}

	@Override
	public List<User> listUserByPage(int startRows, int pageSize, int areaId) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.power='3' order by u.id desc";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> listUserByPage(int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where u.power='3' order by u.id desc";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> listUserByPage(int startRows, int pageSize, String type,
			int areaId) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.power=" + type + " order by u.id desc";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	public List<User> list(Object[] args) {
		String hql = "from User u order by u.id";
		return this.list(hql, args);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<User> list(User user) {
		String hql = "from User u order by u.id";
		Query q = this.getSessionFactory().getCurrentSession().createQuery(hql); // this.getSessionFactory().openSession().createQuery(hql);
		q.setProperties(user);
		return q.list();
	}

	@Override
	public List<User> listAll() {
		String hql = "from User u order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUsersBycarId(String carID) {
		// TODO Auto-generated method stub
		return null;
	}

	// 区域分类总数
	@Override
	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int areaId) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.createTime >= '" + startTime
				+ "' and u.createTime <= '" + endTime
				+ "' and u.power='3' and (u.realName like '%" + searchText
				+ "%' or u.phone like '%" + searchText
				+ "%' or u.carID like '%" + searchText + "%')  order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUserByTime(String startTime, String endTime, int areaId) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.createTime >= '" + startTime
				+ "' and u.createTime <= '" + endTime
				+ "' and u.power=3 order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUsersByText(String searchText, int areaId) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.power='3' and (u.realName like '%"
				+ searchText + "%' or u.phone like '%" + searchText
				+ "%' or u.carID like '%" + searchText + "%')  order by u.id";
		return this.list(hql);
	}

	// 区域分类总数
	@Override
	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime) {
		String hql = "from User u inner join fetch u.area where u.createTime >= '"
				+ startTime
				+ "' and u.createTime <= '"
				+ endTime
				+ "' and u.power='3' and (u.realName like '%"
				+ searchText
				+ "%' or u.phone like '%"
				+ searchText
				+ "%' or u.carID like '%" + searchText + "%')  order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUserByTime(String startTime, String endTime) {
		String hql = "from User u inner join fetch u.area where  u.createTime >= '"
				+ startTime
				+ "' and u.createTime <= '"
				+ endTime
				+ "' and u.power=3 order by u.id";
		return this.list(hql);
	}

	@Override
	public List<User> getUsersByText(String searchText) {
		String hql = "from User u inner join fetch u.area where  and u.power='3' and (u.realName like '%"
				+ searchText
				+ "%' or u.phone like '%"
				+ searchText
				+ "%' or u.carID like '%" + searchText + "%')  order by u.id";
		return this.list(hql);
	}

	// 带分页的
	@Override
	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int areaId, int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.createTime >= '" + startTime
				+ "' and u.createTime <= '" + endTime
				+ "' and u.power='3' and (u.realName like '%" + searchText
				+ "%' or u.phone like '%" + searchText
				+ "%' or u.carID like '%" + searchText + "%') order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> getUserByTime(String startTime, String endTime,
			int areaId, int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.createTime >= '" + startTime
				+ "' and u.createTime <= '" + endTime
				+ "' and u.power=3 order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> getUsersByText(String searchText, int areaId,
			int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where u.area.id="
				+ areaId + " and u.power='3' and (u.realName like '%"
				+ searchText + "%' or u.phone like '%" + searchText
				+ "%' or u.carID like '%" + searchText + "%') order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	// 带分页的、不含区域的
	@Override
	public List<User> getUserByTextAndTime(String searchText, String startTime,
			String endTime, int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where  u.createTime >= '"
				+ startTime
				+ "' and u.createTime <= '"
				+ endTime
				+ "' and u.power='3' and (u.realName like '%"
				+ searchText
				+ "%' or u.phone like '%"
				+ searchText
				+ "%' or u.carID like '%" + searchText + "%') order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> getUserByTime(String startTime, String endTime,
			int startRows, int pageSize) {
		String hql = "from User u inner join fetch u.area where  u.createTime >= '"
				+ startTime
				+ "' and u.createTime <= '"
				+ endTime
				+ "' and u.power=3 order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<User> getUsersByText(String searchText, int startRows,
			int pageSize) {
		String hql = "from User u inner join fetch u.area where  u.power='3' and (u.realName like '%"
				+ searchText
				+ "%' or u.phone like '%"
				+ searchText
				+ "%' or u.carID like '%" + searchText + "%') order by u.id";
		List<User> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

}
