package com.sea.dao;

import java.util.List;

import com.sea.model.Log;

public interface LogDao extends BaseDao<Log>{

	void delete(int id);
	public List<Log> getLogs();
	public List<Log> listLogByPage(int startRows,int pageSize);
}
