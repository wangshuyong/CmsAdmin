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
		if(("".equals(phone)) || phone==null ||("".equals(pwd)) || pwd ==null) {
			result="璇疯緭鍏ョ敤鎴峰悕銆佸瘑鐮�";
			return "fail";//妫�鏌ヨ緭鍏ョ敤鎴峰悕銆佸瘑鐮�
		} else {
			user = userService.login(phone,pwd);
		}

		if(user==null || "".equals(user)){
			result="璇疯緭鍏ユ纭殑鐢ㄦ埛鍚嶃�佸瘑鐮�";
			return "error"; //鐢ㄦ埛鍚嶃�佸瘑鐮侀敊璇�
			//user.equals("")
		} else if ( user.getPower().equals("1")) {
			session.put("loginUser",user);
			return "adminLogin";//绠＄悊鍛樼櫥褰曪紝杩涘叆鍚庡彴
		}  else if ( user.getPower().equals("0")) {
			return "userlogin";
		} else 
			result="鎮ㄦ病鏈夋潈闄愮櫥褰曪紝璇疯仈绯荤鐞嗗憳";
			return "noPower";//鏅�氱敤鎴风櫥褰� 杩涘叆鍓嶇
	}
	
	public String login(){
		String phone = userForm.getPhone();
		String pwd = userForm.getPassword();
		user = userService.login(phone,pwd);
		
		if("".equals(phone) || phone ==null ) {
			dataMap.put("message", "鎵嬫満鍙风爜涓嶈兘涓虹┖");
			dataMap.put("flag","fail");
		} else if (!userService.exists(phone)) {
			dataMap.put("message", "鎵嬫満鍙风爜涓嶅瓨鍦�");
			dataMap.put("flag","fail");
		} else if ("".equals(pwd) || pwd == null){
			dataMap.put("message", "瀵嗙爜涓嶈兘涓虹┖");
			dataMap.put("flag","fail");
		} else if(user==null || "".equals(user)){
			dataMap.put("message", "鎵嬫満鍙风爜涓庡瘑鐮佷笉绗︼紝璇烽噸鏂拌緭鍏�");
			dataMap.put("flag","fail");
		} else {
			session.put("loginUser",user);
			dataMap.put("flag","success");
			dataMap.put("session", user);
			dataMap.put("message", "鐧诲綍鎴愬姛");
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
