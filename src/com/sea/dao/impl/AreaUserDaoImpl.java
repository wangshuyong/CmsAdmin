package com.sea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.AreaDao;
import com.sea.dao.AreaUserDao;
import com.sea.dao.UserDao;
import com.sea.model.Area;
import com.sea.model.AreaUser;
import com.sea.model.User;

@Component("areaUserDao")
public class AreaUserDaoImpl extends BaseDaoImpl<AreaUser> implements AreaUserDao{

	@Override
	public AreaUser getAreaByUserId(int userId) {
		AreaUser area = this.load(userId);
		return area;
	}
	
	@Override
	public int getAreaIdByUserId(int userId) {
		AreaUser area = this.load(userId);
		int areaId =  area.getId();
		return areaId;
	}

	@Override
	public void delete(int userId) {
		AreaUser u = this.load(userId);
 		this.delete(u);
	}

	@Override
	public List<AreaUser> getUserByAreaId(int areaId) {
		String hql="from AreaUser u order by u.areaId";
		return this.list(hql);
	}
	
	@Override
	public List<AreaUser> getAreaByLocation(String location) {
		String hql="from AreaUser u where u.location=?";
		return this.list(hql,location);
	}
	
	@Override
	public List<AreaUser> listAreaUserByPage(int startRows,int pageSize) {
		String hql ="from AreaUser u order by u.id desc";
		List <AreaUser> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	@Override
	public void updateAreaUser(AreaUser area) {  
	        this.getHibernateTemplate().merge(area);  
	}

	@Override
	public boolean existAreaUser(int areaId) {
		// TODO Auto-generated method stub
		AreaUser area =this.getAreaByUserId(areaId);
		if(area!=null|| !("".equals(area))) return true;
		return false;
	} 

}
