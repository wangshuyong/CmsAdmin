package com.sea.action;

import java.io.File;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;

import com.sea.model.Tfile;
import com.sea.model.User;
import com.sea.service.TfileService;
import com.sea.service.UserService;
import com.sea.util.FileUtils;

public class FileAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private File file = null; // 注意file名与表单name对应
	private String fileFileName; // file+FileName为固定写法,否则取不到
	private String message = "";
	private Tfile tFile;
	private String fileContentType; // file+ContentType为固定写法
	private TfileService tFileService;
	private UserService userService;
	
	@JSON(serialize = false)
	public UserService getUserService() {
		return userService;
	}
	@Resource(name = "userService")
	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	@Resource(name = "fileService")
	public void settFileService(TfileService tFileService) {
		this.tFileService = tFileService;
	}

	@JSON(serialize = false)
	public TfileService gettFileService() {
		return tFileService;
	}

	public String UploadFile() {

		return null;
	}

	public String imageUpload() throws Exception {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			message = "您的会话已过期，请重新登录后再次尝试";
		}
		else if (file == null) {
			message = "您没有选择要上传的图片！！";
		} else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName()))) {
			message = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
		} else {

			tFile = new Tfile();
			String newFIleName = this.getFileFileName();
			String filePath = FileUtils.getInstance().fileUpload(
					this.getFile(), this.getFileFileName(),
					"upload_iamge_Path", newFIleName);
			tFile.setFileType("头像");
			tFile.setFilePath(FileUtils.getInstance().getProperty(
					"upload_iamge_Path"));
			tFile.setOwnner(userInfo.getId());
			tFile.setCreateTime(new Timestamp(System.currentTimeMillis()));
			tFile.setFileName(newFIleName);
			tFileService.addFile(tFile);
			message = "上传成功！！";
		}

		return "upload";
	}
	
	public String photoUpload() throws Exception {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			message = "您的会话已过期，请重新登录后再次尝试";
		}
		else if (file == null) {
			message = "您没有选择要上传的图片！！";
		} else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName()))) {
			message = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
		} else {
			User user =userService.getUserByPhone(userInfo.getPhone());
			user.setPhoto(FileUtils.getInstance().getProperty(
					"upload_iamge_Path")+this.getFileFileName());
			userService.updateUser(user);
			message = "上传成功！！";
		}

		return "upload";
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public Tfile gettFile() {
		return tFile;
	}

	public void settFile(Tfile tFile) {
		this.tFile = tFile;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
