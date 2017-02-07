package com.sea.action;

import com.sea.model.User;

public class IndexAction extends BaseAction{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String sessionKey;
	
	
	public String areaAdmin(){
		User loginUser = (User) session.get("loginUser");
		if(loginUser==null)
			return "timeOut";
		return "areaAdmin";
	}
	
	public String administrator(){
		User loginUser = (User) session.get("loginUser");
		if(loginUser==null)
			return "timeOut";
		return "administrator";
	}
	
	public String user(){
		User loginUser = (User) session.get("loginUser");
		if(loginUser==null)
			return "timeOut";
		return "userlogin";
	}
	
	public String getSessionKey() {
		return sessionKey;
	}
	public void setSessionKey(String sessionKey) {
		this.sessionKey = sessionKey;
	}

}
