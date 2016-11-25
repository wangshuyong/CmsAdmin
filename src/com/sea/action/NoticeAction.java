package com.sea.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.UserForm;
import com.sea.model.User;
import com.sea.service.UserService;
import com.sea.util.MD5Util;

public class NoticeAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private UserForm userForm = null;
	private User user;
	private List<User> list;
	private String userId;
	private String sEcho; // 鍖呭惈琛ㄦ牸鐨勪竴浜涗俊鎭紝闇�瑕佷笉鍙樼殑浼犲洖鍘�
	private String iDisplayStart; // 褰撲綘鐐瑰嚮涓嬩竴椤垫垨鑰呴〉鏁扮殑鏃跺�欎細浼犲埌鍚庡彴鐨勫��
	private String iDisplayLength;
	private String returnMessage;
	private String statName = null;
	private String aoData;

	public String excute(){
		User loginUser = (User) session.get("loginUser");
		return "success";
	}
	
	public String delete() {
		String strs[] = userId.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != "") {
				userService.deleteUser(Integer.parseInt(strs[i]));
			}
		}
		return "delete";
	}

	public String list() {
		JSONArray jsonArray = JSONArray.fromObject(aoData);
		for (int i = 0; i < jsonArray.size(); i++) {
			try {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				if (jsonObject.get("name").equals("sEcho"))
					sEcho = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("iDisplayStart"))
					iDisplayStart = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("iDisplayLength"))
					iDisplayLength = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("statId"))
					statName = jsonObject.get("value").toString();
			} catch (Exception e) {
				break;
			}
		}
		int initEcho = Integer.parseInt(sEcho) + 1;
		int displayLength = Integer.parseInt(iDisplayLength);
		int displayStart = Integer.parseInt(iDisplayStart);
		int totalRows = 0;
		totalRows = userService.getUserCount();
		list = userService.listUsersByPage(displayStart, displayLength);
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("aaData", list);
		Alltempobj.put("iTotalRecords", totalRows);
		Alltempobj.put("iTotalDisplayRecords", totalRows);
		Alltempobj.put("sEcho", initEcho);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "list";
	}

	/**
	 * 鍚庡彴鐢ㄦ埛绠＄悊
	 * 鐢ㄦ埛淇敼缂栬緫
	 * */
	public String edit() {
		userService.updateUser(user);
		returnMessage="success";
		return "edit";
	}
	/**
	 * 涓汉淇℃伅淇敼缂栬緫
	 * 涓汉璁剧疆
	 * 閫氳繃session 鑾峰彇璐﹀彿淇℃伅
	 * **/
	public String update() {
		userService.updateUser(user);
		returnMessage="success";
		session.put("loginUser",user);
		return "update";
	}

	public String add() {
		return userService.addUser(user);
	}
    /**
     * 璐﹀彿淇℃伅鑾峰彇
     * 涓汉璁剧疆
     * user
     **/
	

	@Override
	public Object getModel() {
		if (userForm == null)
			userForm = new UserForm();
		return userForm;
	}

	public UserForm getUserForm() {
		return userForm;
	}

	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<User> getList() {
		return list;
	}

	public void setList(List<User> list) {
		this.list = list;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public UserService getUserService() {
		return userService;
	}

	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(String iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public String getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(String iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public String getAoData() {
		return aoData;
	}

	public void setAoData(String aoData) {
		this.aoData = aoData;
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

}

