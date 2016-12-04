package com.sea.action;

import java.util.Map;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.sea.model.User;

public class LoginInterceptor extends AbstractInterceptor {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext ctx = invocation.getInvocationContext();  
        Map<String, Object> session = ctx.getSession();  
		User user = (User) session.get("loginUser");
        // 如果user不为null,代表用户已经登录,允许执行action中的方法
        if (user != null){
            return invocation.invoke(); 
        }
        return "noLogin";
	}

}
 