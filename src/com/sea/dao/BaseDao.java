package com.sea.dao;

import java.util.List;

import org.hibernate.Session;

import com.sea.model.User;

public interface BaseDao<T> {

	public void save(T t);
	public void update(T t);
	public T load(int id);
	public void delete(T t);
	public List<T> list(String hql);
	public List<T> list(String hql,Object arg);
	public List<T> list(String hql,Object[] args);
	public List<T> queryForPage(final String hql,final int offset,final int length);
	public int getAllRowCount(String hql);
}
