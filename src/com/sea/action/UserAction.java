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

@SuppressWarnings("rawtypes")
public class UserAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private UserForm userForm = null;
	private User user;
	private List<User> list;
	private String userId;
	private String sEcho; // 包含表格的一些信息，需要不变的传回去
	private String iDisplayStart; // 当你点击下一页或者页数的时候会传到后台的值
	private String iDisplayLength;
	private String returnMessage;
	private String statName = null;
	private String aoData;
	User userInfo = (User) this.session.get("loginUser");
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
	 * 后台用户管理
	 * 用户修改编辑
	 * */
	public String edit() {
		userService.updateUser(user);
		returnMessage="success";
		return "edit";
	}
	/**
	 * 个人信息修改编辑
	 * 个人设置
	 * 通过session 获取账号信息
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
     * 账号信息获取
     * 个人设置
     * user
     **/
	public String profile() {
		User userInfo = (User) this.session.get("loginUser");
		user = userService.getUserByPhone(userInfo.getPhone());
		return "profile";
	}
	
	public String updatePassword(){
		
		User userInfo = (User) this.session.get("loginUser");
		if(!(MD5Util.getPassMD5(userForm.getPassword())).equals(userInfo.getPassword()) ){
			return "fail";
		}
		
		if(userForm.getNewPassword().equals(userForm.getReNewPassword())) {
			user = userService.getUserByPhone(userInfo.getPhone());
			userService.updatePassword(user, userForm.getNewPassword());
			return "newPassOk";
		}else 
		return "fail";
		
	}

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
