package com.sea.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.sea.model.Log;
import com.sea.model.User;
import com.sea.service.LogService;

public class LogAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private List<Log> list;
	private Log log;
	private LogService logService;
	private String startTime;
	private String endTime;
	private String searchText;
	private String draw ;
	private String fuzzySearch;
	private String total = "0";
	//数据起始位置
    private int startIndex;
    //数据长度
    private int pageSize;
    private String returnMessage;
    
	public String list() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null)
			return "timeOut";
		int totalRows = 0;
		if(("".equals(startTime)||startTime==null)&&("".equals(searchText)||searchText==null)){
			list = logService.getLogsByPage(userInfo,startIndex, pageSize);
			totalRows=logService.getLogCount(userInfo);
		}else {
			list = logService.getLogs(userInfo,searchText, startTime, endTime);
			totalRows=list.size();
		}
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		setReturnMessage(JSONObject.fromObject(Alltempobj).toString());
		return "logList";
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getSearchText() {
		return searchText;
	}
	public String getDraw() {
		return draw;
	}
	public String getFuzzySearch() {
		return fuzzySearch;
	}
	public String getTotal() {
		return total;
	}
	public int getStartIndex() {
		return startIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}
	public void setDraw(String draw) {
		this.draw = draw;
	}
	public void setFuzzySearch(String fuzzySearch) {
		this.fuzzySearch = fuzzySearch;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<Log> getList() {
		return list;
	}
	public Log getLog() {
		return log;
	}
	public LogService getLogService() {
		return logService;
	}
	public void setList(List<Log> list) {
		this.list = list;
	}
	public void setLog(Log log) {
		this.log = log;
	}
	@Resource(name = "logService")
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	public String getReturnMessage() {
		return returnMessage;
	}
	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}
}
