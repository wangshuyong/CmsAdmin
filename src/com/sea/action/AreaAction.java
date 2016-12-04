package com.sea.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.opensymphony.xwork2.ModelDriven;
import com.sea.form.AreaForm;
import com.sea.form.UserForm;
import com.sea.model.Area;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.service.UserService;
import com.sea.util.MD5Util;

@SuppressWarnings("rawtypes")
public class AreaAction extends BaseAction implements ModelDriven {

	private static final long serialVersionUID = 1L;
	private AreaService areaService;
	private AreaForm areaForm = null;
	private Area area;
	private List<Area> list;

	private String areaId;
	private String sEcho; // 包含表格的一些信息，需要不变的传回去
	private String iDisplayStart; // 当你点击下一页或者页数的时候会传到后台的值
	private String iDisplayLength;
	private String returnMessage;
	private String statName = null;
	private String aoData;
	User userInfo = (User) this.session.get("loginUser");
	
	public String delete() {
		String strs[] = areaId.split(",");
		for (int i = 0; i < strs.length; i++) {
			if (strs[i] != "") {
				areaService.deleteArea(Integer.parseInt(strs[i]));
			}
		}
		return "delete";
	}

	public String list() {
		JSONArray jsonArray = JSONArray.fromObject(aoData);
		for (int i = 0; i < jsonArray.size(); i++) {
			try {
				JSONObject jsonObject = (JSONObject) jsonArray.get(i);
				if (jsonObject.get("name").equals("sEcho"))
					sEcho = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("iDisplayStart"))
					iDisplayStart = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("iDisplayLength"))
					iDisplayLength = jsonObject.get("value").toString();
				else if (jsonObject.get("name").equals("statId"))
					statName = jsonObject.get("value").toString();
			} catch (Exception e) {
				break;
			}
		}
		int initEcho = Integer.parseInt(sEcho) + 1;
		int displayLength = Integer.parseInt(iDisplayLength);
		int displayStart = Integer.parseInt(iDisplayStart);
		int totalRows = 0;
		totalRows = areaService.getAreaCount();
		list = areaService.listAreasByPage(displayStart, displayLength);
		JSONObject Alltempobj = JSONObject.fromObject("{}");
		Alltempobj.put("aaData", list);
		Alltempobj.put("iTotalRecords", totalRows);
		Alltempobj.put("iTotalDisplayRecords", totalRows);
		Alltempobj.put("sEcho", initEcho);
		returnMessage = JSONObject.fromObject(Alltempobj).toString();
		return "list";
	}

	/**
	 * 后台用户管理
	 * 用户修改编辑
	 * */
	public String edit() {
		areaService.updateArea(area);
		returnMessage="success";
		return "edit";
	}
	/**
	 * 个人信息修改编辑
	 * 个人设置
	 * 通过session 获取账号信息
	 * **/
	public String update() {
		areaService.updateArea(area);
		returnMessage="success";
		session.put("loginUser",area);
		return "update";
	}

	public String add() {
		return areaService.addArea(area);
	}

	@Override
	public Object getModel() {
		if (areaForm == null)
			areaForm = new AreaForm();
		return areaForm;
	}


	public String getsEcho() {
		return sEcho;
	}

	public void setsEcho(String sEcho) {
		this.sEcho = sEcho;
	}

	public String getStatName() {
		return statName;
	}

	public void setStatName(String statName) {
		this.statName = statName;
	}

	public String getiDisplayLength() {
		return iDisplayLength;
	}

	public void setiDisplayLength(String iDisplayLength) {
		this.iDisplayLength = iDisplayLength;
	}

	public String getiDisplayStart() {
		return iDisplayStart;
	}

	public void setiDisplayStart(String iDisplayStart) {
		this.iDisplayStart = iDisplayStart;
	}

	public String getAoData() {
		return aoData;
	}

	public void setAoData(String aoData) {
		this.aoData = aoData;
	}

	public String getReturnMessage() {
		return returnMessage;
	}

	public void setReturnMessage(String returnMessage) {
		this.returnMessage = returnMessage;
	}

	@Resource(name="areaService")
	public void setAreaService(AreaService areaService) {
		this.areaService = areaService;
	}

	public AreaForm getAreaForm() {
		return areaForm;
	}

	public void setAreaForm(AreaForm areaForm) {
		this.areaForm = areaForm;
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

	public void setList(List<Area> list) {
		this.list = list;
	}


}
