package com.sea.action;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.UserForm;
import com.sea.model.User;
import com.sea.service.UserService;

public class RegisterAction extends BaseAction{
	
	@Resource(name="userService")
	private UserService userService;
	private User user= new User();
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String excute(){	
		user.setPower("1");
		return userService.addUser(user);		
	}

}
