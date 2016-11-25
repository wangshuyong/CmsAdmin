package com.sea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.ContentDao;
import com.sea.dao.ContentDao;
import com.sea.model.Content;

@Component("contentDao")
public class ContentDaoImpl extends BaseDaoImpl<Content> implements ContentDao {

	@Override
	public Content getContentById(int id) {
		Content Content = this.load(id);
		return Content;
	}

	@Override
	public void delete(int id) {
		Content u = this.load(id);
 		this.delete(u);
	}

	@Override
	public List<Content> getContents() {
		String hql="from Content u order by u.id";
		return this.list(hql);
	}
	
	@Override
	public List<Content> ContentLogin(String phone, String pwd) {
		String hql="from Content u where u.phone='"+phone+"' and u.password='"+pwd+"'";
		return this.list(hql);
	}
	
	@Override
	public List<Content> getContentByPhone(String phone) {
		String hql="from Content u where u.phone=?";
		return this.list(hql,phone);
	}
	
	@Override
	public List<Content> listContentByPage(int startRows,int pageSize) {
		String hql ="from Content u order by u.id desc";
		List <Content> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	@Override
	public void updateContent(Content Content) {  
	        this.getHibernateTemplate().merge(Content);  
	} 
	

}
