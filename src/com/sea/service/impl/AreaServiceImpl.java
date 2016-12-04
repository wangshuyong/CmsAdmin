package com.sea.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sea.dao.AreaDao;
import com.sea.dao.AreaUserDao;
import com.sea.dao.UserDao;
import com.sea.model.Area;
import com.sea.model.AreaUser;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.service.UserService;
import com.sea.util.BussAnnotation;
import com.sea.util.MD5Util;

@Component("areaService")
public class AreaServiceImpl implements AreaService {
	private String operateResult = null;
	private AreaDao areaDao;
	private String importMessage;

	public AreaDao getAreaDao() {
		return areaDao;
	}
	@Resource(name = "areaDao")
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	@Override
	public boolean exists(String name) {
		List<Area> list = areaDao.getAreaByAreaName(name);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}


	// all attribute getters and setters method
	public String getImportMessage() {
		return importMessage;
	}

	public void setImportMessage(String importMessage) {
		this.importMessage = importMessage;
	}


	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	@Override
	@BussAnnotation(moduleName="区域管理",option="添加名为:”+“小区")
	public String addArea(Area u) {
		// TODO Auto-generated method stub
		if (exists(u.getAreaName()) || "".equals(u.getAreaName())) {
			return "fail";
		}else {
			u.setCreateTime(new Timestamp(System.currentTimeMillis()));
			areaDao.save(u);
		}
		return "add";
	}

	@Override
	@BussAnnotation(moduleName="区域管理",option="删除名为："+"的小区")
	public void deleteArea(int id) {
		// TODO Auto-generated method stub
		
		areaDao.delete(id);;
	}

	@Override
	public List<Area> listAreasByPage(int startRows, int pageSize) {
		List<Area> list = areaDao.listAreaByPage(startRows, pageSize);
		return list;
	}

	@Override
	public int getAreaCount() {
		List<Area> areas=areaDao.getAreas();
		return areas.size();
	}

	@Override
	public Area getAreaById(int id) {
		Area area = areaDao.getAreaById(id);
		return area;
	}

	@Override
	public Area getAreaByName(String name) {
		List<Area> list = areaDao.getAreaByAreaName(name);
		Area user = (Area) list.get(0);
		if (list.size() <= 0) {
			return null;
		} else {
			return user;
		}
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="修改名为：”+area.getAreaName()+“的小区")
	public void updateArea(Area area) {
		Area areas = (Area)this.getAreaByName(area.getAreaName());
		area.setId(areas.getId());
		 if (area != null) {  
		        // 为了不与spring的BeanUtils冲突, 这里用全名调用自定义的BeanUtils  
		        // 这样userModel中为null的属性将不会复制到user中, user中没有修改的属性还保持原来的, 不会被复制为null了  
		        com.sea.util.BeanUtils.copyProperties(area, areas);  
		        // 更新用户  
		        areaDao.updateArea(areas);
		    } 		
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="批量导入用户")
	public String importAreas(ArrayList<ArrayList<String>> list) {
		for (int i = 0; i < list.size(); ++i) {
			int index = i + 1;
			Area area = new Area();
			// 第一列：手机号处理
			String firstColumn = list.get(i).get(0);

			if (firstColumn.equals("")) {
				importMessage = "第" + index + "行，第1列为空，导入失败,请修正后重新导入！！！";
				break;
			} else {
				if (firstColumn.trim().length() > 11) {
					importMessage = "第" + index
							+ "行，第1列“手机”号码格式错误，导入失败,请修正后重新导入！！！";
					break;
				} else {
					if (this.exists(firstColumn)) {
						importMessage = "第" + index + "行，第1列" + firstColumn
								+ "已存在，导入失败,请修正后重新导入！！！";
						break;
					}
					area.setAreaName(firstColumn);
				}
			}
			// 第二列：姓名处理
			String secondColumn = list.get(i).get(1);
			if (secondColumn.equals("")) {
				importMessage = "第" + index + "行，第2列为空，导入失败,请修正后重新导入！！！";
				break;
			} else {
				if (secondColumn.length() > 30) { // 长度过大
					importMessage = "第" + index
							+ "行，第2列“姓名”长度大于20，导入失败,请修正后重新导入！！！";
					break;
				}
				area.setArea_description(secondColumn);
			}

			// 第三列：性别处理
			String thirdColumn = list.get(i).get(2);
			if (thirdColumn.equals("")) {
				importMessage = "第" + index + "行，第3列为空，导入失败,请修正后重新导入！！！";
				break;
			} else {
				if ((!thirdColumn.equals("男")) && (!thirdColumn.equals("女"))) { // 长度过大
					importMessage = "第" + index
							+ "行，第3列“性别”填写错误，导入失败,请修正后重新导入！！！";
					break;
				}
				area.setAddress(thirdColumn);
			}

			// 第四列：年龄校验
			String forthColumn = list.get(i).get(3);
			if (forthColumn.equals("")) {
				importMessage = "第" + index + "行，第4列为空，导入失败,请修正后重新导入！！！";
				break;
			} else {
				if (Integer.parseInt(forthColumn) < 0) { // 长度过大
					importMessage = "第" + index
							+ "行，第4列“年龄”填写错误，导入失败,请修正后重新导入！！！";
					break;
				}
				area.setFullName(forthColumn);
			}

			// 创建时间
			area.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.areaDao.save(area);

			importMessage = "导入成功";
		}
		return importMessage;
	}

	
}
