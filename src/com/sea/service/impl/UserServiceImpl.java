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
import com.sea.model.AreaUser;
import com.sea.model.User;
import com.sea.service.UserService;
import com.sea.util.BussAnnotation;
import com.sea.util.MD5Util;

@Component("userService")
public class UserServiceImpl implements UserService {
	private String operateResult = null;
	private UserDao userDao;
	private AreaDao areaDao;
	private AreaUserDao areaUserDao;
	private String importMessage;

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
	public boolean exists(String phone) {
		List<User> list = userDao.getUserByPhone(phone);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="用户登录")
	public User login(String phone, String pwd) {
		List<User> list = userDao.userLogin(phone, MD5Util.getPassMD5(pwd));
		
		if (list.size() <= 0) {
			return null;
		} else {
			User user = (User) list.get(0);
			user.setLastLoginTime(new Timestamp(System.currentTimeMillis()));
			userDao.update(user);
			return user;
		}

	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="添加用户")
	public String addUser(User u) {
		if (exists(u.getPhone()) || "".equals(u.getPhone())) {
			return "fail";
		} else if (!(areaDao.existArea(u.getAreaId()))){
			return "noArea";
		}else {
			u.setPassword(MD5Util.getPassMD5("123456"));
			u.setCreateTime(new Timestamp(System.currentTimeMillis()));
			AreaUser au = new AreaUser();
			au.setAreaId(u.getAreaId());
			au.setUserId(u.getId());
			areaUserDao.save(au);
			userDao.save(u);
		}
		return "add";
	}
	
	@Override
	@BussAnnotation(moduleName="用户管理",option="修改密码")
	public void updatePassword (User user,String pwd){
		user.setPassword(MD5Util.getPassMD5(pwd));
		userDao.updateUser(user);
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="批量导入用户")
	public String importUsers(ArrayList<ArrayList<String>> list) {
		
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
					if (this.exists(firstColumn)) {
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
			user.setNativePlace(seventhColumn);

			// 第八列：爱好校验
			String eighthColumn = list.get(i).get(7);
			user.setHobby(eighthColumn);
			// 默认密码是 123456
			user.setPassword(MD5Util.getPassMD5("123456"));

			// 设置权限，默认用户为普通用户
			user.setPower("0");
			// 创建时间
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.userDao.save(user);

			importMessage = "导入成功";
		}
		return importMessage;
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="删除用户")
	public void deleteUser(int id) {
		User user = this.getUserById(id);
		areaUserDao.delete(user.getId());
		userDao.delete(id);
	}

	@Override
	public List<User> listUsersByPage(int startRows, int pageSize) {
		List<User> list = userDao.listUserByPage(startRows, pageSize);
		return list;
	}

	public int getUserCount() {
		return userDao.getUsers().size();
	}

	@Override
	public User getUserById(int id) {
		User user = userDao.load(id);
		return user;
	}

	@Override
	@BussAnnotation(moduleName="用户管理",option="修改用户")
	public void updateUser(User user) {
		User users = (User)this.getUserByPhone(user.getPhone());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		user.setId(users.getId());
		 if (user != null) {  
		        // 为了不与spring的BeanUtils冲突, 这里用全名调用自定义的BeanUtils  
		        // 这样userModel中为null的属性将不会复制到user中, user中没有修改的属性还保持原来的, 不会被复制为null了  
		        com.sea.util.BeanUtils.copyProperties(user, users);  
		        // 更新用户  
		        userDao.updateUser(users);
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

	
}
