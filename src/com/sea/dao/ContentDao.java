package com.sea.dao;

import java.util.List;

import com.sea.model.Notice;

public interface ContentDao extends BaseDao<Notice> {

	public Notice getContentById(int id);
	public List<Notice> getContentByPhone(String phone);
	public void delete(int id);
	public List<Notice> getContents();
	public List<Notice> ContentLogin(String phone, String pwd);
	public List<Notice> listContentByPage(int startRows,int pageSize);
	public void updateContent(Notice Content);
}
