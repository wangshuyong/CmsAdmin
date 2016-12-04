package com.sea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.ContentDao;
import com.sea.dao.ContentDao;
import com.sea.model.Notice;

@Component("contentDao")
public class ContentDaoImpl extends BaseDaoImpl<Notice> implements ContentDao {

	@Override
	public Notice getContentById(int id) {
		Notice Content = this.load(id);
		return Content;
	}

	@Override
	public void delete(int id) {
		Notice u = this.load(id);
 		this.delete(u);
	}

	@Override
	public List<Notice> getContents() {
		String hql="from Content u order by u.id";
		return this.list(hql);
	}
	
	@Override
	public List<Notice> ContentLogin(String phone, String pwd) {
		String hql="from Content u where u.phone='"+phone+"' and u.password='"+pwd+"'";
		return this.list(hql);
	}
	
	@Override
	public List<Notice> getContentByPhone(String phone) {
		String hql="from Content u where u.phone=?";
		return this.list(hql,phone);
	}
	
	@Override
	public List<Notice> listContentByPage(int startRows,int pageSize) {
		String hql ="from Content u order by u.id desc";
		List <Notice> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}
	
	@Override
	public void updateContent(Notice Content) {  
	        this.getHibernateTemplate().merge(Content);  
	} 
	

}
