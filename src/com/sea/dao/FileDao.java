package com.sea.dao;

import java.util.List;
import com.sea.model.Tfile;

public interface FileDao extends BaseDao<Tfile> {

	public Tfile getFileById(int id);
	public List<Tfile> listFileByUser(Tfile file);
	public int delete(int id);
	public List<Tfile> listFileByPage(int startRows,int pageSize);
}
