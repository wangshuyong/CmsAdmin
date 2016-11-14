package com.sea.action;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.UserForm;
import com.sea.model.User;
import com.sea.service.UserService;

public class LoginAction extends BaseAction implements ModelDriven{
	
	private static final long serialVersionUID = 1L;
	@Resource(name="userService")
	private UserService userService;
	private UserForm userForm =null;
	private User user ;
	private String result;
	private Map<String,Object> dataMap = new HashMap<String,Object>();
	
	public Map<String, Object> getDataMap() {
		return dataMap;
	}

	public void setDataMap(Map<String, Object> dataMap) {
		this.dataMap = dataMap;
	}

	public String adminLogin(){
		String phone = userForm.getPhone();
		String pwd = userForm.getPassword();
		if(!("".equals(phone)) && phone !=null && !("".equals(pwd)) && pwd !=null) {
			user = userService.login(phone,pwd);
		} else {
			result="请输入用户名、密码";
			return "fail";//检查输入用户名、密码
		}

		if(user==null || "".equals(user)){
			result="请输入正确的用户名、密码";
			return "error"; //用户名、密码错误
			//user.equals("")
		} else if ( user.getPower().equals("1")) {
			session.put("loginUser",user);
			return "adminLogin";//管理员登录，进入后台
		} else 
			result="您没有权限登录，请联系管理员";
			return "noPower";//普通用户登录 进入前端
	}
	
	public String login(){
		String phone = userForm.getPhone();
		String pwd = userForm.getPassword();
		user = userService.login(phone,pwd);
		
		if("".equals(phone) || phone ==null ) {
			dataMap.put("message", "手机号码不能为空");
			dataMap.put("flag","fail");
		} else if (!userService.exists(phone)) {
			dataMap.put("message", "手机号码不存在");
			dataMap.put("flag","fail");
		} else if ("".equals(pwd) || pwd == null){
			dataMap.put("message", "密码不能为空");
			dataMap.put("flag","fail");
		} else if(user==null || "".equals(user)){
			dataMap.put("message", "手机号码与密码不符，请重新输入");
			dataMap.put("flag","fail");
		} else {
			session.put("loginUser",user);
			dataMap.put("flag","success");
			dataMap.put("session", user);
			dataMap.put("message", "登录成功");
		}
		return "userLogin";	
			
	}
	
	public String rePassword(){
		
		return result;
		
	}
	public String logOut() {  
        session.clear();  
        return "logout";  
 	 }

	@Override
	public Object getModel() {
		if(userForm==null) userForm = new UserForm();
		return userForm;
	}

	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public UserForm getUserForm() {
		return userForm;
	}
	public void setUserForm(UserForm userForm) {
		this.userForm = userForm;
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}
}
