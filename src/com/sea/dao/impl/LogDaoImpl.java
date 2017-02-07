package com.sea.dao.impl;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sea.dao.LogDao;
import com.sea.model.Log;

@Component("logDao")
public class LogDaoImpl extends BaseDaoImpl<Log> implements LogDao {

	@Override
	public void delete(int id) {
		Log log = this.load(id);
 		this.delete(log);
	}

	@Override
	public List<Log> getLogs() {
		String hql="from Log log order by log.id";
		return this.list(hql);
	}
	
	@Override
	public List<Log> getLogs(int areaId) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.area.id = "+areaId+" order by l.createTime desc";
		return this.list(hql);
	}


	@Override
	public List<Log> listLogByPage(int areaId,int startRows, int pageSize) {
		String hql ="from Log l inner join fetch l.user inner join fetch l.area  where l.area.id = "+areaId+" order by l.createTime desc";
		List <Log> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	@Override
	public List<Log> listLogByPage(int startRows, int pageSize) {
		String hql ="from Log l inner join fetch l.user inner join fetch l.area order by l.createTime desc";
		List <Log> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	
	
	@Override
	public List<Log> getLogByTime(int areaId,String startTime, String endTime) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.createTime >= '"+startTime+"' and l.createTime <= '"+endTime+"' and l.area.id = "+areaId+" order by l.createTime desc";
		return this.list(hql);
	}
	@Override
	public List<Log> getLogByTime(String startTime, String endTime) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.createTime >= '"+startTime+"' and l.createTime <= '"+endTime+"' order by l.createTime desc";
		return this.list(hql);
	}

	
	
	@Override
	public List<Log> getLogsByText(int areaId,String searchText) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.moduleName like '%"+searchText+"%' or l.action like '%"+searchText+"%' and l.area.id = "+areaId+" order by l.createTime";
		return this.list(hql);
	}
	@Override
	public List<Log> getLogsByText(String searchText) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.moduleName like '%"+searchText+"%' or l.action like '%"+searchText+"%' order by l.createTime";
		return this.list(hql);
	}


	
	@Override
	public List<Log> getLogByTextAndTime(int areaId,String searchText, String startTime,
			String endTime) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.moduleName like '%"+searchText+"%' or l.action like '%"+searchText+"%' and l.area.id = "+areaId+" and l.createTime >= '"+startTime+"' and l.createTime <= '"+endTime+"' order by l.createTime";
		return null;
	}
	@Override
	public List<Log> getLogByTextAndTime(String searchText, String startTime,
			String endTime) {
		String hql="from Log l inner join fetch l.user inner join fetch l.area where l.moduleName like '%"+searchText+"%' or l.action like '%"+searchText+"%' and l.createTime >= '"+startTime+"' and l.createTime <= '"+endTime+"' order by l.createTime";
		return null;
	}

	

}
