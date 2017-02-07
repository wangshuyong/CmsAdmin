package com.sea.action;

import java.io.File;
import java.sql.Timestamp;

import javax.annotation.Resource;

import org.apache.struts2.json.annotations.JSON;

import com.sea.model.Area;
import com.sea.model.Tfile;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.service.TfileService;
import com.sea.service.UserService;
import com.sea.util.FileUtils;

public class FileAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	private File file = null; // 注意file名与表单name对应
	private String fileFileName; // file+FileName为固定写法,否则取不到
	private String fileContentType; // file+ContentType为固定写法
	private String message = "";
	private Tfile tFile;
	private String userId;
	private String areaId;
	private TfileService tFileService;
	private UserService userService;
	private AreaService areaService;
	private String uploadType;

	public String UploadFile() {

		return null;
	}

	public String imageUpload() throws Exception {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			message = "您的会话已过期，请重新登录后再次尝试";
			return "timeOut";
		}
		else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName().toLowerCase()))) {
			message = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
			return "error";
		} else {

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
			return "upload";
		}
	}
	
	public String photoUpload() throws Exception {
		
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			message = "您的会话已过期，请重新登录后再次尝试";
			return "timeOut";
		}
		else if (file == null) {
			message = "您没有选择要上传的图片！！";
			return "error";
		} else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName()))) {
			message = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
			return "error";
		} else {
			String newFIleName = this.getFileFileName();
			String filePath = FileUtils.getInstance().fileUpload(this.getFile(), this.getFileFileName(),uploadType, newFIleName);
			
//			User user =userService.getUserById(Integer.parseInt(userId));
			if("USER_PHOTO_PATH".equals(uploadType)){
				userInfo.setPhoto(FileUtils.getInstance().getProperty(uploadType)+this.getFileFileName());
			}else if("USER_IDPhoto_PATH".equals(uploadType)) {
				userInfo.setIdPhoto(FileUtils.getInstance().getProperty(uploadType)+this.getFileFileName());
			}
			
			userInfo.setUpdateTime(new Timestamp(System.currentTimeMillis()));
			userService.updateUser(userInfo);
			message = "上传成功！！";
			return "upload";
		}
	}
	
	public String UploaIdImage() throws Exception {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			message = "超时已过期，请重新登录后再次尝试";
			return "timeOut";
		} else if (file == null) {
			message = "您没有选择要上传的图片！！";
			return "error";
		}
		else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName()))) {
			message = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
			return "error";
		} else {
			String newFIleName = this.getFileFileName();
			String filePath = FileUtils.getInstance().fileUpload(this.getFile(), this.getFileFileName(),"USER_IDPhoto_PATH", newFIleName);
			User user = userService.getUserById(Integer.parseInt(userId));
			user.setIdPhoto(FileUtils.getInstance().getProperty(uploadType)+this.getFileFileName());
			userService.updateUser(user);
			message = "上传成功！！";
			return "upload";
		}
		
	}
	
	

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
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
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	
	@Resource(name = "areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}
	
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
}
