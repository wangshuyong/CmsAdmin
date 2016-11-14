package com.sea.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import com.sea.dao.FileDao;
import com.sea.model.Tfile;
import com.sea.model.User;
@Component("fileDao")
public class FileDaoImpl extends BaseDaoImpl<Tfile> implements FileDao {

	@Override
	public Tfile getFileById(int id) {
		Tfile tf = this.load(id);
		return tf;
	}

	@Override
	public List<Tfile> listFileByUser(Tfile file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int delete(int id) {
		String hql = "delete from Tfile tf where tf.id='"+id+"'";
		int tf = this.getHibernateTemplate().bulkUpdate(hql);
		return tf;

	}

	@Override
	public List<Tfile> listFileByPage(int startRows, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

}
