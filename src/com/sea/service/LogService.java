package com.sea.service;

import java.util.List;

import org.aspectj.lang.JoinPoint;

import com.sea.model.Log;
import com.sea.model.User;

public interface LogService {
	
	 //无参的日志方法
    public void log();
    //有参的日志方法
    public void logArg(JoinPoint point);
    //有参有返回值的方法
    public void logArgAndReturn(JoinPoint point,Object returnObj);
	public String addLog(Log log);
	public void deleteLog(int id);
	public void updateLog(Log log);
	public List<Log> getLogsByPage(User user,int length,int offset);
	public int getLogCount();
	public Log getLogById(int id);
	public int getLogCount(User user);
	List<Log> getLogs(User user,String searchText, String startTime, String endTime);
	
}
