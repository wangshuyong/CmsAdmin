package com.sea.dao;

import java.util.List;

import com.sea.model.Content;

public interface ContentDao extends BaseDao<Content> {

	public Content getContentById(int id);
	public List<Content> getContentByPhone(String phone);
	public void delete(int id);
	public List<Content> getContents();
	public List<Content> ContentLogin(String phone, String pwd);
	public List<Content> listContentByPage(int startRows,int pageSize);
	public void updateContent(Content Content);
}
