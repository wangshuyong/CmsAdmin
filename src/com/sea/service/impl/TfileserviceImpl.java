package com.sea.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sea.dao.FileDao;
import com.sea.model.Tfile;
import com.sea.service.TfileService;
import com.sea.util.BussAnnotation;
@Component("fileService")
public class TfileserviceImpl implements TfileService {

	private FileDao fileDao;
	@Override
	@BussAnnotation(moduleName="上传文件",option="上传文件")
	public String addFile(Tfile tFile) {
		fileDao.save(tFile);
		return null;
	}

	@Override
	public String deleteFile(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Tfile> listFiles(int start,int pageSize) {
		List<Tfile> list = fileDao.listFileByPage(start, pageSize);
		return list;
	}

	@Override
	public Tfile getFileById(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	public FileDao getFileDao() {
		return fileDao;
	}
	@Resource(name="fileDao")
	public void setFileDao(FileDao fileDao) {
		this.fileDao = fileDao;
	}

	
}
