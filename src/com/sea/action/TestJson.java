package com.sea.action;

import java.util.HashMap;
import java.util.Map;
import net.sf.json.JSONObject;

public class TestJson extends BaseAction {
	 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	// 用户Ajax返回数据  
    private String result;  
  
    public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	// struts的属性驱动模式，自动填充页面的属性到这里  
    private String loginName;  
    private String password;  
  

    public String excute() {  
    	System.out.println(loginName);  
        // 用一个Map做例子  
        Map<String, String> map = new HashMap<String, String>();  
  
        // 为map添加一条数据，记录一下页面传过来loginName  
        map.put("name", this.loginName);  
  
        // 将要返回的map对象进行json处理  
        JSONObject jo = JSONObject.fromObject(map);  
  
        // 调用json对象的toString方法转换为字符串然后赋值给result  
        this.result = jo.toString();  
  
        // 可以测试一下result  
        System.out.println(result);  
  
        return SUCCESS;  
  
    }  
  
    //getter  setter 方法省略  
}
