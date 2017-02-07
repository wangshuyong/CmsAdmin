package com.sea.service;

import java.util.List;

import com.sea.model.Tfile;

public interface TfileService {

	public String addFile(Tfile tFile);
	public String deleteFile(int id);
	public Tfile getFileById(int id);
	List<Tfile> listFiles(int start, int pageSize);
}
   