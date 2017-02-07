package com.sea.action;

import java.io.File;
import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONObject;

import com.sea.model.Area;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.util.FileUtils;

public class AreaAction extends BaseAction{

	private static final long serialVersionUID = 1L;
	private AreaService areaService;
	private Area area;
	private List<Area> list;
	private String areaId;
	private String draw ;
	//数据起始位置
    private int startIndex;
    //数据长度
    private int pageSize;
    private String fuzzy;
    private String fuzzySearch;
    private String total = "0";
	private String returnMessage;
	private File file = null; // 注意file名与表单name对应
	private String fileFileName; // file+FileName为固定写法,否则取不到
	private String fileContentType; // file+ContentType为固定写法
	
	public String delete() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		String strs[] = areaId.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != "") {
				areaService.deleteArea(Integer.parseInt(strs[i]));
			}
		}
		return "deleteArea";
	}

	public String list() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		int totalRows = 0;
		if(!("".equals(fuzzy))&&fuzzy!=null){
			list = areaService.listAreaByPage(startIndex, pageSize,fuzzy);
			totalRows = list.size();
		}else {
			list = areaService.listAreasByPage(startIndex, pageSize);
			totalRows=areaService.getAreaCount();
		}
		
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("pageData", list);
		Alltempobj.put("total", totalRows);
		Alltempobj.put("draw", draw);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "ListArea";
	}

	/**
	 * 后台用户管理
	 * 用户修改编辑
	 * */
	public String edit() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null) return "timeOut";
		area = areaService.getAreaById(Integer.parseInt(areaId.trim()));
		returnMessage="success";
		return "editArea";
	}
	/**
	 * 个人信息修改编辑
	 * 个人设置
	 * 通过session 获取账号信息
	 * **/
	public String update() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){ 
			returnMessage = "超时已过期，请重新登录后再次尝试";
			return "timeOut";
		}
			areaService.updateArea(area);
			returnMessage="success";
			return "updateArea";
	}

	public String add() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			returnMessage = "超时已过期，请重新登录后再次尝试";
			return "timeOut";
		} else if ("".equals(area.getAreaName())||area.getAreaName()==null) {
			returnMessage = "请输入信息";
			return "fail";
		}else if (areaService.exists(area.getAreaName())) {
			returnMessage = "信息已存在";
			return "areaexist";
		}
			areaService.addArea(area);
			
			return "addArea";
	}

	public String UploadLogo() {
		User userInfo = (User) this.session.get("loginUser");
		if(userInfo==null){
			returnMessage = "超时已过期，请重新登录后再次尝试";
			return "timeOut";
		}else if (file == null) {
			returnMessage = "您没有选择要上传的图片！！";
		} 
		else if (!(FileUtils.getInstance().checkIsImage(this.getFileFileName()))) {
			returnMessage = "您上传的图片有误，请选择jpg、gif、jpeg、png格式的图片！！";
			return "upload";
		}
		String newFIleName = this.getFileFileName();
		String filePath = FileUtils.getInstance().fileUpload(this.getFile(), this.getFileFileName(),"AREA_LOGO", newFIleName);
		Area area = userInfo.getArea();
		area.setLogo(FileUtils.getInstance().getProperty("AREA_LOGO")+this.getFileFileName());
		 areaService.updateArea(area);
		 returnMessage = "上传成功";
		 return "upload";
	}
	//geters and setters

	@Resource(name="areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public Area getArea() {
		return area;
	}

	public void setArea(Area area) {
		this.area = area;
	}

	public String getAreaId() {
		return areaId;
	}

	public void setAreaId(String areaId) {
		this.areaId = areaId;
	}

	public AreaService getAreaService() {
		return areaService;
	}

	public List<Area> getList() {
		return list;
	}

	public String getDraw() {
		return draw;
	}

	public int getStartIndex() {
		return startIndex;
	}

	public int getPageSize() {
		return pageSize;
	}

	public String getFuzzy() {
		return fuzzy;
	}

	public String getFuzzySearch() {
		return fuzzySearch;
	}

	public String getTotal() {
		return total;
	}

	public String getReturnMessage() {
		return returnMessage;
	}


	public void setList(List<Area> list) {
		this.list = list;
	}

	public void setDraw(String draw) {
		this.draw = draw;
	}

	public void setStartIndex(int startIndex) {
		this.startIndex = startIndex;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public void setFuzzy(String fuzzy) {
		this.fuzzy = fuzzy;
	}

	public void setFuzzySearch(String fuzzySearch) {
		this.fuzzySearch = fuzzySearch;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	public File getFile() {
		return file;
	}

	public String getFileFileName() {
		return fileFileName;
	}

	public String getFileContentType() {
		return fileContentType;
	}

	public void setFile(File file) {
		this.file = file;
	}

	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

}
