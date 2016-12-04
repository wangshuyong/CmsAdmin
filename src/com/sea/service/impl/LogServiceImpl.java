package com.sea.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.sea.dao.LogDao;
import com.sea.model.Log;
import com.sea.service.LogService;
@Component("logService")
public class LogServiceImpl implements LogService {

	private LogDao logDao;

	public LogDao getLogDao() {
		return logDao;
	}

	@Resource(name="logDao")
	public void setLogDao(LogDao logDao) {
		this.logDao = logDao;
	}

	@Override
	public String addLog(Log log) {
		logDao.save(log);
		return null;
	}

	@Override
	public void deleteLog(int id) {
		logDao.delete(id);
	}

	@Override
	public void updateLog(Log log) {
		logDao.update(log);
	}

	@Override
	public List<Log> getLogsByPage(int length, int offset) {
		return logDao.listLogByPage(length, offset);
	}

	@Override
	public int getLogCount() {
		return 0;
	}

	@Override
	public Log getLogById(int id) {
		return null;
	}

	@Override
	public void log() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logArg(JoinPoint point) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void logArgAndReturn(JoinPoint point, Object returnObj) {
		// TODO Auto-generated method stub
		
	}

}
