package com.sea.dao;

import java.util.List;

import com.sea.model.Log;

public interface LogDao extends BaseDao<Log>{

	void delete(int id);
	public List<Log> getLogs();
	public List<Log> listLogByPage(int areaId,int startRows,int pageSize);
	List<Log> getLogByTime(int areaId,String startTime, String endTime);
	List<Log> getLogsByText(int areaId,String searchText);
	List<Log> getLogByTextAndTime(int areaId,String searchText, String startTime,
			String endTime);
	public List<Log> listLogByPage(int startRows, int pageSize);
	public List<Log> getLogByTime(String startTime, String endTime) ;
	public List<Log> getLogsByText(String searchText) ;
	public List<Log> getLogByTextAndTime(String searchText, String startTime,
			String endTime) ;
	public List<Log> getLogs(int areaId);
}
