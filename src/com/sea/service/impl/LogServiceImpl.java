package com.sea.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import com.sea.dao.LogDao;
import com.sea.model.Log;
import com.sea.model.User;
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
	public List<Log> getLogsByPage(User user,int length, int offset) {
		List<Log> list=null;
		if ("0".equals(user.getPower())) {
			list = logDao.listLogByPage(length, offset);
		} else if ("1".equals(user.getPower())) {
			list = logDao.listLogByPage(user.getArea().getId(),length, offset);
		}
		return list;
	}
	
	@Override
	public List<Log> getLogs(User user,String searchText, String startTime,
			String endTime) {
		List<Log> list = null;
		if("0".equals(user.getPower())){
			if ("".equals(searchText) || searchText == null) {
				list = logDao.getLogByTime(startTime, endTime);
			} else if ("".equals(startTime) || startTime == null) {
				list = logDao.getLogsByText(searchText);
			} else
				list = logDao.getLogByTextAndTime(searchText, startTime, endTime);
		}else if("1".equals(user.getPower())){
			if ("".equals(searchText) || searchText == null) {
				list = logDao.getLogByTime(user.getArea().getId(),startTime, endTime);
			} else if ("".equals(startTime) || startTime == null) {
				list = logDao.getLogsByText(user.getArea().getId(),searchText);
			} else
				list = logDao.getLogByTextAndTime(user.getArea().getId(),searchText, startTime, endTime);
		}
		return list;
	}

	@Override
	public int getLogCount(User user) {
		int count=0;
		if("0".equals(user.getPower())){
			count = logDao.getLogs().size();
		}else if("1".equals(user.getPower())){
			count = logDao.getLogs(user.getArea().getId()).size();
		}
		return count;
		
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

	@Override
	public int getLogCount() {
		// TODO Auto-generated method stub
		return 0;
	}

}
