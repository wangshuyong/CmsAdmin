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
	public List<Log> listLogByPage(int startRows, int pageSize) {
		String hql ="from Log l order by l.id desc";
		List <Log> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

}
