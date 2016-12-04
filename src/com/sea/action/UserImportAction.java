package com.sea.action;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import javax.annotation.Resource;
import org.apache.struts2.ServletActionContext;
import com.sea.service.UserService;
import com.sea.util.ExcelUtils;
import com.sea.util.FileUtils;

/**
 * 
 */
public class UserImportAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private File file = null;
	private String fileFileName;
	private String message = "";
	private String filePath;
	private UserService userService;

	String basePath = ServletActionContext.getServletContext().getRealPath("/");
	ExcelUtils excel = new ExcelUtils();

	public String fileUpload() throws Exception {
		if(fileFileName.endsWith(".xlsx")||fileFileName.endsWith(".xls")){
			SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
	    	String fileName = df.format(System.currentTimeMillis());//文件名为上传时间
	    	String newFIleName = fileName+"_"+fileFileName; // 保存在硬盘中的文件名 上传时间+原文件名
			filePath = FileUtils.getInstance().fileUpload(file, fileFileName, "upload_UserFile_Path",newFIleName);
			// 用于存储用户信息
			ArrayList<ArrayList<String>> list = null;
			try {
				// 遍历用户信息
				list = excel.readExcel(filePath);
				if (list.size() < 0) {
					message = "导入的文件没有数据，请录入数据后重新导入";
				}
				message = userService.importUsers(list);

			} catch (Exception e) {
				e.printStackTrace();
				message = "发生异常，请重新上传！！！";
			}
		}
		message ="文件格式错误，请上传 格式为：xlsx或者xls的文件！！！";
		return "upload";
	}

	

	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

}
