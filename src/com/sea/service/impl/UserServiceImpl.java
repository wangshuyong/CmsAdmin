package com.sea.service.impl;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Component;

import com.sea.dao.AreaDao;
import com.sea.dao.AreaUserDao;
import com.sea.dao.UserDao;
import com.sea.model.Area;
import com.sea.model.AreaUser;
import com.sea.model.User;
import com.sea.service.UserService;
import com.sea.util.BussAnnotation;
import com.sea.util.ExcelUtils;
import com.sea.util.MD5Util;

@Component("userService")
public class UserServiceImpl implements UserService {
	private String operateResult = null;
	private UserDao userDao;
	private AreaDao areaDao;
	private AreaUserDao areaUserDao;
	private String importMessage;
	private String exportMessage;

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	public AreaDao getAreaDao() {
		return areaDao;
	}

	@Resource(name = "areaDao")
	public void setAreaDao(AreaDao areaDao) {
		this.areaDao = areaDao;
	}

	public AreaUserDao getAreaUserDao() {
		return areaUserDao;
	}

	@Resource(name = "areaUserDao")
	public void setAreaUserDao(AreaUserDao areaUserDao) {
		this.areaUserDao = areaUserDao;
	}

	@Override
	public boolean isExists(String phone) {
		List<User> list = userDao.getUserByPhone(phone);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}
	
	@Override
	public boolean isExistsUserName(String userName) {
		List<User> list = userDao.getUserByUserName(userName);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "用户登录")
	public User login(String phone, String pwd) {
		List<User> list = userDao.userLogin(phone, MD5Util.getPassMD5(pwd));
		if (list.size() <= 0) {
			return null;
		} else {
			User user = (User) list.get(0);
			
			return user;
		}
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "添加用户")
	public void addUser(User u) {
		u.setCreateTime(new Timestamp(System.currentTimeMillis()));
		userDao.save(u);
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "修改密码")
	public void updatePassword(User user, String pwd) {
		user.setPassword(MD5Util.getPassMD5(pwd));
		userDao.updateUser(user);
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "批量导入用户")
	public String importUsers(ArrayList<ArrayList<String>> list, Area area) {

		for (int i = 0; i < list.size(); ++i) {
			int index = i + 1;
			User user = new User();
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
					if (this.isExists(firstColumn)) {
						importMessage = "第" + index + "行，第1列" + firstColumn
								+ "已存在，导入失败,请修正后重新导入！！！";
						break;
					}
					user.setPhone(firstColumn);
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
				user.setRealName(secondColumn);
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
				user.setSex(thirdColumn);
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
				user.setAge(Integer.parseInt(forthColumn));
			}
			// 第五列：邮箱校验
			String fifthColumn = list.get(i).get(4);
			if (fifthColumn.equals("")) {
				importMessage = "第" + index + "行，第5列“邮箱”为空，导入失败,请修正后重新导入！！！";
				break;
			} else {
				Pattern p = Pattern.compile("(^([\\w-.]+)@)");
				Matcher m = p.matcher(fifthColumn);
				if (!m.find()) {
					importMessage = "第" + index
							+ "行，第5列“邮箱”不符合标准，导入失败,请修正后重新导入！！！";
					break;
				}
				user.setEmail(fifthColumn);
			}

			// 第六列：地址校验
			String sixthColumn = list.get(i).get(5);
			user.setAddress(sixthColumn);
			// 第七列：籍贯校验
			String seventhColumn = list.get(i).get(6);
			user.setCarID(seventhColumn);
			// 第八列：爱好校验
			user.setPassword(MD5Util.getPassMD5("123456"));
			// 设置权限，默认用户为普通用户
			user.setPower("3");
			user.setArea(area);
			// 创建时间
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.userDao.save(user);
			importMessage = "导入成功，密码默认为：123456，请自行修改密码";
		}
		return importMessage;
	}
	@Override
	@BussAnnotation(moduleName = "用户管理", option = "批量导出用户")
	public List<User> exportUsers(int areaId, String power, String searchText,
			String startTime, String endTime) {

		List<User> list = null;
		if (("".equals(searchText) || searchText == null)
				&& ("".equals(startTime) || startTime == null)) {
			list = userDao.getUsersByAreaIdPower(areaId, power);
		} else if (!("".equals(searchText)) && searchText != null) {
			list = userDao.getUsersByText(searchText, areaId);
		} else if (!("".equals(startTime)) && startTime != null) {
			list = userDao.getUserByTime(startTime, endTime, areaId);
		} else {
			list = userDao.getUserByTextAndTime(searchText, startTime, endTime,
					areaId);
		}
		return list;
	}
	@Override
	@BussAnnotation(moduleName = "用户管理", option = "批量导出用户")
	public List<User> exportUsers(String power, String searchText,
			String startTime, String endTime) {

		List<User> list = null;
		if (("".equals(searchText) || searchText == null)
				&& ("".equals(startTime) || startTime == null)) {
			list = userDao.getUsersByPower(power);
		} else if (!("".equals(searchText)) && searchText != null) {
			list = userDao.getUsersByText(searchText);
		} else if (!("".equals(startTime)) && startTime != null) {
			list = userDao.getUserByTime(startTime, endTime);
		} else {
			list = userDao.getUserByTextAndTime(searchText, startTime, endTime);
		}
		return list;
	}

	@Override
	public List<User> getUsersByPage(Area area, String searchText,
			String startTime, String endTime, int startRows, int pageSize) {
		List<User> list = null;
		if (area == null) {
			if (("".equals(startTime) || startTime == null)
					&& ("".equals(searchText) || searchText == null)) {
				list = userDao.listUserByPage(startRows, pageSize);
			} else if ("".equals(searchText) || searchText == null) {
				list = userDao.getUserByTime(startTime, endTime, startRows,
						pageSize);
			} else if ("".equals(startTime) || startTime == null) {
				list = userDao.getUsersByText(searchText, startRows, pageSize);
			} else
				list = userDao.getUserByTextAndTime(searchText, startTime,
						endTime, startRows, pageSize);
		} else {
			if (("".equals(startTime) || startTime == null)
					&& ("".equals(searchText) || searchText == null)) {
				list = userDao
						.listUserByPage(startRows, pageSize, area.getId());
			} else if ("".equals(searchText) || searchText == null) {
				list = userDao.getUserByTime(startTime, endTime, area.getId(),
						startRows, pageSize);
			} else if ("".equals(startTime) || startTime == null) {
				list = userDao.getUsersByText(searchText, area.getId(),
						startRows, pageSize);
			} else
				list = userDao.getUserByTextAndTime(searchText, startTime,
						endTime, area.getId(), startRows, pageSize);
		}

		return list;
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "删除用户")
	public void deleteUser(int id) {
		// areaUserDao.delete(id);
		userDao.delete(id);
	}

	@Override
	public List<User> getAdminUsersByPage(Area area, String type,
			int startIndex, int pageSize) {
		return userDao.listUserByPage(startIndex, pageSize, type, area.getId());
	}

	public int getUserCount(Area area, String type, String searchText,
			String startTime, String endTime) {
		int count = 0;
		if (area == null) {
			if (("".equals(startTime) || startTime == null)
					&& ("".equals(searchText) || searchText == null)) {
				count = userDao.getUsersByPower(type)
						.size();
			} else if ("".equals(searchText) || searchText == null) {
				count = userDao.getUserByTime(startTime, endTime)
						.size();
			} else if ("".equals(startTime) || startTime == null) {
				count = userDao.getUsersByText(searchText).size();
			} else
				count = userDao.getUserByTextAndTime(searchText, startTime,
						endTime).size();
		} else {
			if (("".equals(startTime) || startTime == null)
					&& ("".equals(searchText) || searchText == null)) {
				count = userDao.getUsersByAreaIdPower(area.getId(), type)
						.size();
			} else if ("".equals(searchText) || searchText == null) {
				count = userDao.getUserByTime(startTime, endTime, area.getId())
						.size();
			} else if ("".equals(startTime) || startTime == null) {
				count = userDao.getUsersByText(searchText, area.getId()).size();
			} else
				count = userDao.getUserByTextAndTime(searchText, startTime,
						endTime, area.getId()).size();
		}

		return count;
	}

	@Override
	public User getUserById(int id) {
		User user = userDao.getUserById(id);
		return user;
	}

	@Override
	@BussAnnotation(moduleName = "用户管理", option = "修改用户")
	public void updateUser(User user) {
		
		if (user != null) {
			userDao.updateUser(user);
		}
	}

	// all attribute getters and setters method
	public String getImportMessage() {
		return importMessage;
	}

	public void setImportMessage(String importMessage) {
		this.importMessage = importMessage;
	}

	@Override
	public User getUserByPhone(String phone) {
		List<User> list = userDao.getUserByPhone(phone);
		User user = (User) list.get(0);
		if (list.size() <= 0) {
			return null;
		} else {
			return user;
		}
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getExportMessage() {
		return exportMessage;
	}

	public void setExportMessage(String exportMessage) {
		this.exportMessage = exportMessage;
	}

}
