package com.sea.action;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import org.apache.struts2.ServletActionContext;

//import com.sea.model.User;
import com.sea.util.FileUtils;

public class FileDownLoadAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileName;

	private String basePath = ServletActionContext.getServletContext()
			.getRealPath("/");
//	User userInfo = (User) this.session.get("loginUser");

	// 文件下载
	public InputStream getDownloadFile() {

		String filePath = FileUtils.getInstance().getProperty("downLoadpath");
		InputStream inputStream = null;
		try {
			inputStream = new FileInputStream(basePath + filePath
					+ this.getDownloadChineseFileName());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return inputStream;
	}

	// 如果下载文件名为中文，进行字符编码转换
	public String getDownloadChineseFileName() {
		String downloadChineseFileName = fileName;

		try {
			downloadChineseFileName = new String(
					downloadChineseFileName.getBytes(), "ISO8859-1");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return downloadChineseFileName;
	}
	
	@Override
	public String execute() throws Exception {
	return SUCCESS;
	}
	public String getFileName() throws UnsupportedEncodingException {
		// fileName = encodingFileName(fileName);
		return fileName;
	}

	public void setFileName(String fileName)
			throws UnsupportedEncodingException {
		this.fileName = new String(fileName.getBytes("ISO8859-1"), "utf-8");
	}

}
