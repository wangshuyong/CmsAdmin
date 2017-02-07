package com.sea.action;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.UserForm;
import com.sea.model.Area;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.service.UserService;
import com.sea.util.ExcelUtils;
import com.sea.util.MD5Util;

@SuppressWarnings("rawtypes")
public class UserAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private UserService userService;
	private AreaService areaService;
	private UserForm userForm = null;
	private User user;
	private List<User> list;
	private String userId;
	private String areaId;
	private String returnMessage;
	private String startTime;
	private String endTime;
	private String searchText;
	private String draw;
	private String fuzzySearch;
	private String total = "0";
	// 数据起始位置
	private int startIndex;
	// 数据长度
	private int pageSize;
	private String areaName;
	private InputStream excelStream;
	private String fileName;

	public String delete() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		String strs[] = userId.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != "") {
				userService.deleteUser(Integer.parseInt(strs[i]));
			}
		}
		return "delete";
	}

	public String list() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		int totalRows = 0;
		totalRows = userService.getUserCount(userInfo.getArea(), "3",
				searchText, startTime, endTime);
		list = userService.getUsersByPage(userInfo.getArea(), searchText,
				startTime, endTime, startIndex, pageSize);
		List<Area> areaGroupList = areaService.getAreaGroup();
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("areaGroup", areaGroupList);
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "list";
	}

	public String adminList() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		int totalRows = 0;
		Area area = null;
		if ("所在小区".equals(areaName) || "所在小区" == areaName) {
			area = null;
		} else {
			area = areaService.getAreaByName(areaName);
		}
		totalRows = userService.getUserCount(area, "3", searchText, startTime,
				endTime);
		list = userService.getUsersByPage(area, searchText, startTime, endTime,
				startIndex, pageSize);
		List<Area> areaGroupList = areaService.getAreaGroup();
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("areaGroup", areaGroupList);
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "adminList";
	}

	public String adminUserList() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		int totalRows = 0;
		Area area = null;
		area = areaService.getAreaById(Integer.parseInt(areaId.trim()));
		totalRows = userService.getUserCount(area, "1", searchText, startTime,
				endTime);
		list = userService.getAdminUsersByPage(area, "1", startIndex, pageSize);
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "adminUserList";
	}

	public String exportUsers() {
		User queryUser = new User();
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		queryUser.setPower("3");
		queryUser.setArea(userInfo.getArea());
		SimpleDateFormat sdf = new SimpleDateFormat(
				"yyyy-MM-dd hh:ss:mm");
		fileName = sdf.format(System.currentTimeMillis());
		String title = "用户信息";
		List<User> list = null;
		if ("1".equals(userInfo.getPower())) {
			try {
				list = userService.exportUsers(userInfo.getArea().getId(), "3",
						URLDecoder.decode(searchText, "UTF-8"), startTime,
						endTime);
				title = userInfo.getArea().getAreaName()+" 的用户信息";
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else if ("0".equals(userInfo.getPower())) {
			Area area = null;
			if ("所在小区".equals(areaName) || "所在小区" == areaName) {
				area = null;
				try {
					list = userService.exportUsers("3",
							URLDecoder.decode(searchText, "UTF-8"), startTime,
							endTime);
						title = "的用户信息";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				area = areaService.getAreaByName(areaName);
				try {
					list = userService.exportUsers(area.getId(), "3",
							URLDecoder.decode(searchText, "UTF-8"), startTime,
							endTime);
					title = areaName+" 的用户信息";
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

		String[] headers = new String[] {"姓名", "手机号", "性别", "年龄","住址",
				"车牌号", "最后访问时间","注册时间" };
		String[] field = { "realName", "phone", "sex","age", "address", "carID",
		"lastLoginTime","createTime" };
		ExcelUtils eu = new ExcelUtils();
		excelStream = eu.exportExcel(title, headers,field, list);
		if (excelStream == null)
			return "exportNull";
		return "exportOk";
	}

	/**
	 * 后台用户管理 用户修改编辑
	 * */
	public String edit() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		user = userService.getUserById(Integer.parseInt(userId));
		return "edit";
	}

	/**
	 * 个人信息修改编辑 个人设置 通过session 获取账号信息
	 * **/
	public String update() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		User users = (User) userService.getUserById(user.getId());
		if (userService.isExists(user.getPhone()) && !(user.getPhone().equals( users.getPhone()))){
			returnMessage="手机号已存在，请另换一个";
			return "userOk";
		}
		else if (null == user.getPhone() || "".equals(user.getPhone())) {
			returnMessage="手机号不能为空";
			return "userOk";
		}
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		com.sea.util.BeanUtils.copyProperties(user, users);
		userService.updateUser(users);
		returnMessage="修改成功";
		return "userOk";
	}

	public String updateProfile() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		if (userService.isExists(user.getPhone()) && !(user.getPhone().equals( userInfo.getPhone()))){
			returnMessage="手机号已存在，请另换一个";
			return "profileOK";
		}
		else if (null == user.getPhone() || "".equals(user.getPhone())) {
			returnMessage="手机号不能为空";
			return "profileOK";
		}
		user.setId(userInfo.getId());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		com.sea.util.BeanUtils.copyProperties(user, userInfo);
		userService.updateUser(userInfo);
		returnMessage="操作成功";
		return "profileOK";
	}

	public String add() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		if (userService.isExists(user.getPhone())){
			returnMessage="手机号已存在，请另换一个";
			return "userOk";
		}
		else if (null == user.getPhone() || "".equals(user.getPhone())) {
			returnMessage="手机号不能为空";
			return "userOk";
		} else if (userInfo.getArea() == null) {
			returnMessage="小区不存在，请重试";
			return "userOk";
		} else
			// user.setAreaId(userInfo.getAreaId());
			user.setArea(userInfo.getArea());
		user.setPower("3");
		user.setPassword(MD5Util.getPassMD5("123456"));
		userService.addUser(user);
		returnMessage="添加成功";
		return "userOk";
	}

	/**
	 * 账号信息获取 个人设置 user
	 **/
	public String profile() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		user = userService.getUserByPhone(userInfo.getPhone());
		return "profile";
	}

	public String addAdmin() {
		User user = new User();
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		if ("".equals(userForm.getPhone()) || userForm.getPhone()==null) {
			returnMessage="手机号不能为空";
			return "adminAdd";
		} else if (areaService.getAreaById(Integer.parseInt(userForm
				.getAreaId())) == null) {
			returnMessage="小区不存在，请重试";
			return "adminAdd";
		} else if (userService.isExists(userForm.getPhone())) {
			returnMessage="手机号已存在，请另换一个";
			return "adminAdd";
		}
		else if (userService.isExistsUserName(userForm.getUsername()) && userForm.getUsername() != null && !("".equals(userForm.getUsername()))) {
			returnMessage="用户名已存在，请另换一个";
			return "adminAdd";
//			return "exist";
		}else if ("".equals(userForm.getReNewPassword()) || userForm.getNewPassword()==null) {
			returnMessage="密码不能为空";
			return "adminAdd";
		}else if (!(userForm.getNewPassword().equals(userForm.getReNewPassword()))) {
			returnMessage="密码输入不一致";
			return "adminAdd";
		}
		user.setPhone(userForm.getPhone());
		user.setUserName(userForm.getUsername());
		user.setPassword(MD5Util.getPassMD5(userForm.getNewPassword()));
		user.setArea(areaService.getAreaById(Integer.parseInt(userForm
				.getAreaId())));
		user.setPower("1");
		userService.addUser(user);
		returnMessage="添加成功";
		return "adminAdd";
		
		// action="User_addAdmin" method="post"  ||)
	}

	public String updatePassword() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		if (!( MD5Util.getPassMD5(userForm.getPassword()).equals(userInfo.getPassword()))) {
			returnMessage="原密码错误，请重新输入";
			return "profileOK";
		}
		if (!(userForm.getNewPassword().equals(userForm.getReNewPassword()))) {
			returnMessage="密码不一致";
			return "profileOK";
		} else
			userService.updatePassword(userInfo, userForm.getNewPassword());
		returnMessage="操作成功";
		return "profileOK";
	}

	public String updateUserPassword() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		if (!( MD5Util.getPassMD5(userForm.getPassword()).equals(userInfo.getPassword()))) {
			returnMessage="原密码错误，请重新输入";
			return "userOk";
		}
		if (!(userForm.getNewPassword().equals(userForm.getReNewPassword()))) {
			returnMessage="密码不一致";
			return "userOk";
		} else {
			User users = (User) userService.getUserById(userForm.getId());
			users.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			users.setPassword(MD5Util.getPassMD5(userForm.getNewPassword()));
			// com.sea.util.BeanUtils.copyProperties(user, users);
			userService.updateUser(users);
			// userService.updatePassword(users, userForm.getNewPassword());
		}
		returnMessage="操作成功";
		return "userOk";
	}

	public String updateAdminProfile() {
		User userInfo = (User) this.session.get("loginUser");
		if (userInfo == null)
			return "timeOut";
		User users = (User) userService.getUserById(userForm.getId());
		
		if ("".equals(userForm.getPhone()) || userForm.getPhone()==null) {
			returnMessage="手机号不能为空";
			return "adminAdd";
		} 
		if (userService.isExistsUserName(userForm.getUsername()) && !(users.getUserName().equals(userForm.getUsername())) && userForm.getUsername() != null && !("".equals(userForm.getUsername()))) {
			returnMessage="用户名已存在，请另换一个";
			return "adminAdd";
		}else if(userService.isExists(userForm.getPhone()) && !(users.getPhone().equals(userForm.getPhone()))){
			returnMessage="手机号已存在，请另换一个";
			return "adminAdd";
		}
		else if (!("".equals(userForm.getNewPassword()))
				&& userForm.getNewPassword()
						.equals(userForm.getReNewPassword())) {
			users.setPassword(MD5Util.getPassMD5(userForm.getNewPassword()));
		}
		users.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		users.setPhone(userForm.getPhone());
		users.setUserName(userForm.getUsername());
		userService.updateUser(users);
		returnMessage="修改成功";
		return "adminAdd";
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

	@Resource(name = "areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	/*
	 * public String getiDisplayLength() { return iDisplayLength; }
	 * 
	 * public void setiDisplayLength(String iDisplayLength) {
	 * this.iDisplayLength = iDisplayLength; }
	 * 
	 * public String getiDisplayStart() { return iDisplayStart; }
	 * 
	 * public void setiDisplayStart(String iDisplayStart) { this.iDisplayStart =
	 * iDisplayStart; }
	 */

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
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

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String getTotal() {
		return total;
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

	public String getDraw() {
		return draw;
	}

	public String getFuzzySearch() {
		return fuzzySearch;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public void setFuzzySearch(String fuzzySearch) {
		this.fuzzySearch = fuzzySearch;
	}

	public InputStream getExcelStream() {
		return excelStream;
	}

	public void setExcelStream(InputStream excelStream) {
		this.excelStream = excelStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getAreaName() {
		return areaName;
	}

	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}
}
