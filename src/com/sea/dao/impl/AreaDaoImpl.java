package com.sea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.AreaDao;
import com.sea.dao.UserDao;
import com.sea.model.Area;
import com.sea.model.Notice;
import com.sea.model.User;

@Component("areaDao")
public class AreaDaoImpl extends BaseDaoImpl<Area> implements AreaDao{

	@Override
	public Area getAreaById(int id) {
		Area area = this.load(id);
		return area;
	}

	@Override
	public void delete(int id) {
		Area u = this.load(id);
 		this.delete(u);
	}

	@Override
	public List<Area> getAreas() {
		String hql="from Area u order by u.id";
		return this.list(hql);
	}
	
	@Override
	public List<Area> getAreaByAreaName(String name) {
		String hql="from Area u where u.areaName=?";
		return this.list(hql,name);
	}
	
	@Override
	public List<Area> listAreaByPage(int startRows,int pageSize) {
		String hql ="from Area a  order by a.id desc";
		List <Area> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	@Override
	public List<Area> listAreaByPage(int startRows, int pageSize,String param) {
		String hql="from Area a  where a.areaName like '%"+param+"%' or a.area_description like '%"+param+"%' or a.address like '%"+param+"%' order by a.id";
		List <Area> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	@Override
	public void updateArea(Area area) {  
	        this.getHibernateTemplate().merge(area);  
	}

	@Override
	public boolean existArea(int areaId) {
		// TODO Auto-generated method stub
		Area area =this.getAreaById(areaId);
		if(area!=null|| !("".equals(area))) return true;
		return false;
	}

	@Override
	public List<Area> getGroupByAreaName() {
		String hql ="select areaName from Area u group by u.areaName order by u.id desc";
		return this.list(hql);
	} 
	

}
