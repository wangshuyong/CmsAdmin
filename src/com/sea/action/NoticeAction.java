package com.sea.action;

import java.sql.Timestamp;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.UserForm;
import com.sea.model.Notice;
import com.sea.model.User;
import com.sea.service.NoticeService;
import com.sea.service.UserService;
import com.sea.util.MD5Util;

public class NoticeAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private NoticeService noticeService;
	private Notice notice;
	private String noticeId;
	private List<Notice> list;
	private String draw ;
	//数据起始位置
    private int startIndex;
    //数据长度
    private int pageSize;
    private String fuzzy;
    private String fuzzySearch;
    private String total = "0";
	private String returnMessage;

	public String delete() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		String strs[] = noticeId.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != "") {
				noticeService.deleteNotice(Integer.parseInt(strs[i]));
			}
		}
		return "delete";
	}

	public String list() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		int totalRows = 0;
		if(!("".equals(fuzzy))&&fuzzy!=null){
			list = noticeService.listNoticesByPage(userInfo,startIndex, pageSize,fuzzy);
			totalRows = list.size();
		}else {
			list = noticeService.listNoticesByPage(userInfo,startIndex, pageSize);
			totalRows=noticeService.getNoticeCount();
		}
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "noticeList";
	}
	
	public String listToUser() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		list = noticeService.listNoticeByUser(userInfo);
		int newNotice=noticeService.listNewNotices(userInfo, LoginAction.lastLogin.toString()).size();
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", newNotice);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "noticeListToUser";
	}

	public String edit() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		notice = noticeService.getNoticeById(Integer.parseInt(noticeId.trim()));
		return "notice";
	}
	public String update() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		notice.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		notice.setArea(userInfo.getArea());
		notice.setUser(userInfo);
		notice.setType("2");
		noticeService.updateNotice(notice);
		return "noticeUpdate";
	}

	public String add() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		notice.setUser(userInfo);
		notice.setArea(userInfo.getArea());
//		notice.setUserId(userInfo.getId());
//		notice.setAreaId(userInfo.getArea().getId());
		return noticeService.addNotice(notice);
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public NoticeService getNoticeService() {
		return noticeService;
	}

	public Notice getNotice() {
		return notice;
	}

	public List<Notice> getList() {
		return list;
	}
	@Resource(name = "noticeService")
	public void setNoticeService(NoticeService noticeService) {
		this.noticeService = noticeService;
	}

	public void setNotice(Notice notice) {
		this.notice = notice;
	}

	public void setList(List<Notice> list) {
		this.list = list;
	}

	public String getNoticeId() {
		return noticeId;
	}

	public void setNoticeId(String noticeId) {
		this.noticeId = noticeId;
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

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public void setFuzzySearch(String fuzzySearch) {
		this.fuzzySearch = fuzzySearch;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getFuzzy() {
		return fuzzy;
	}

	public void setFuzzy(String fuzzy) {
		this.fuzzy = fuzzy;
	}

}

