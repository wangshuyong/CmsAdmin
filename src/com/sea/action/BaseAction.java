package com.sea.action;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ParameterAware;
import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.apache.struts2.interceptor.SessionAware;
import com.opensymphony.xwork2.ActionSupport;

public class BaseAction extends ActionSupport  implements ServletRequestAware,SessionAware,ServletResponseAware {

	private static final long serialVersionUID = 1L;
	
	private String iSortCol_0; // 排序字段所对应的索引号
	private String sSortDir_0; // 排序字段的排序方式，升、降序
	private String iDisplayLength; // 默认显示的记录数
	private String iDisplayStart; // 当前页的第一条纪录的索引号
	private String sSearch; // 用于搜索的关键字
	private String sEcho; // 当前页面的第N次请求数据
	private String iSortingCols;
	private String iSortCol_;
	HttpServletRequest request;  
    HttpServletResponse response;  
    protected Map<String, Object> session;

	

	/**
	 * 将beye[]转换为十六进制字符串
	 * 
	 * @param bArray
	 * @return
	 */
	
	
	public String getISortCol_0() {
		return iSortCol_0;
	}

	public void setISortCol_0(String sortCol_0) {
		iSortCol_0 = sortCol_0;
	}

	public String getSSortDir_0() {
		return sSortDir_0;
	}

	public void setSSortDir_0(String sortDir_0) {
		sSortDir_0 = sortDir_0;
	}

	public String getIDisplayLength() {
		return iDisplayLength;
	}

	public void setIDisplayLength(String displayLength) {
		iDisplayLength = displayLength;
	}

	public String getIDisplayStart() {
		return iDisplayStart;
	}

	public void setIDisplayStart(String displayStart) {
		iDisplayStart = displayStart;
	}

	public String getSSearch() {
		return sSearch;
	}

	public void setSSearch(String search) {
		sSearch = search;
	}

	public String getSEcho() {
		return sEcho;
	}

	public void setSEcho(String echo) {
		sEcho = echo;
	}

	public String getISortingCols() {
		return iSortingCols;
	}

	public void setISortingCols(String sortingCols) {
		iSortingCols = sortingCols;
	}

	public String getISortCol_() {
		return iSortCol_;
	}

	public void setISortCol_(String sortCol_) {
		iSortCol_ = sortCol_;
	}

	@Override
	public void setSession(Map<String, Object> session) {
		this.session = session;
	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;
	}

	@Override
	public void setServletResponse(HttpServletResponse response) {
		// TODO Auto-generated method stub
		this.response = response;  
	}

}
