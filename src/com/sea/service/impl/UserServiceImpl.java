package com.sea.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.sea.dao.UserDao;
import com.sea.model.User;
import com.sea.service.UserService;
import com.sea.util.BussAnnotation;
import com.sea.util.MD5Util;

@Component("userService")
public class UserServiceImpl implements UserService {
	private String operateResult = null;
	private UserDao userDao;
	private String importMessage;

	public UserDao getUserDao() {
		return userDao;
	}

	@Resource(name = "userDao")
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
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
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="鐢ㄦ埛鐧诲綍")
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
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="娣诲姞鐢ㄦ埛")
	public String addUser(User u) {
		if (exists(u.getPhone()) || "".equals(u.getPhone())) {
			return "fail";
		} else
			u.setPassword(MD5Util.getPassMD5("123456"));
			u.setCreateTime(new Timestamp(System.currentTimeMillis()));
			userDao.save(u);
		return "add";

	}
	
	@Override
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="淇敼瀵嗙爜")
	public void updatePassword (User user,String pwd){
		user.setPassword(MD5Util.getPassMD5(pwd));
		userDao.updateUser(user);
	}

	@Override
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="鎵归噺瀵煎叆鐢ㄦ埛")
	public String importUsers(ArrayList<ArrayList<String>> list) {
		
		for (int i = 0; i < list.size(); ++i) {
			int index = i + 1;
			User user = new User();
			// 绗竴鍒楋細鎵嬫満鍙峰鐞�
			String firstColumn = list.get(i).get(0);

			if (firstColumn.equals("")) {
				importMessage = "绗�" + index + "琛岋紝绗�1鍒椾负绌猴紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
				break;
			} else {
				if (firstColumn.trim().length() > 11) {
					importMessage = "绗�" + index
							+ "琛岋紝绗�1鍒椻�滄墜鏈衡�濆彿鐮佹牸寮忛敊璇紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
					break;
				} else {
					if (this.exists(firstColumn)) {
						importMessage = "绗�" + index + "琛岋紝绗�1鍒�" + firstColumn
								+ "宸插瓨鍦紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
						break;
					}
					user.setPhone(firstColumn);
				}
			}
			// 绗簩鍒楋細濮撳悕澶勭悊
			String secondColumn = list.get(i).get(1);
			if (secondColumn.equals("")) {
				importMessage = "绗�" + index + "琛岋紝绗�2鍒椾负绌猴紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
				break;
			} else {
				if (secondColumn.length() > 30) { // 闀垮害杩囧ぇ
					importMessage = "绗�" + index
							+ "琛岋紝绗�2鍒椻�滃鍚嶁�濋暱搴﹀ぇ浜�20锛屽鍏ュけ璐�,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
					break;
				}
				user.setRealName(secondColumn);
			}

			// 绗笁鍒楋細鎬у埆澶勭悊
			String thirdColumn = list.get(i).get(2);
			if (thirdColumn.equals("")) {
				importMessage = "绗�" + index + "琛岋紝绗�3鍒椾负绌猴紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
				break;
			} else {
				if ((!thirdColumn.equals("鐢�")) && (!thirdColumn.equals("濂�"))) { // 闀垮害杩囧ぇ
					importMessage = "绗�" + index
							+ "琛岋紝绗�3鍒椻�滄�у埆鈥濆～鍐欓敊璇紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
					break;
				}
				user.setSex(thirdColumn);
			}

			// 绗洓鍒楋細骞撮緞鏍￠獙
			String forthColumn = list.get(i).get(3);
			if (forthColumn.equals("")) {
				importMessage = "绗�" + index + "琛岋紝绗�4鍒椾负绌猴紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
				break;
			} else {
				if (Integer.parseInt(forthColumn) < 0) { // 闀垮害杩囧ぇ
					importMessage = "绗�" + index
							+ "琛岋紝绗�4鍒椻�滃勾榫勨�濆～鍐欓敊璇紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
					break;
				}
				user.setAge(Integer.parseInt(forthColumn));
			}
			// 绗簲鍒楋細閭鏍￠獙
			String fifthColumn = list.get(i).get(4);
			if (fifthColumn.equals("")) {
				importMessage = "绗�" + index + "琛岋紝绗�5鍒椻�滈偖绠扁�濅负绌猴紝瀵煎叆澶辫触,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
				break;
			} else {
				Pattern p = Pattern.compile("(^([\\w-.]+)@)");
				Matcher m = p.matcher(fifthColumn);
				if (!m.find()) {
					importMessage = "绗�" + index
							+ "琛岋紝绗�5鍒椻�滈偖绠扁�濅笉绗﹀悎鏍囧噯锛屽鍏ュけ璐�,璇蜂慨姝ｅ悗閲嶆柊瀵煎叆锛侊紒锛�";
					break;
				}
				user.setEmail(fifthColumn);
			}

			// 绗叚鍒楋細鍦板潃鏍￠獙
			String sixthColumn = list.get(i).get(5);
			user.setAddress(sixthColumn);

			// 绗竷鍒楋細绫嶈疮鏍￠獙
			String seventhColumn = list.get(i).get(6);
			user.setNativePlace(seventhColumn);

			// 绗叓鍒楋細鐖卞ソ鏍￠獙
			String eighthColumn = list.get(i).get(7);
			user.setHobby(eighthColumn);
			// 榛樿瀵嗙爜鏄� 123456
			user.setPassword(MD5Util.getPassMD5("123456"));

			// 璁剧疆鏉冮檺锛岄粯璁ょ敤鎴蜂负鏅�氱敤鎴�
			user.setPower("0");
			// 鍒涘缓鏃堕棿
			user.setCreateTime(new Timestamp(System.currentTimeMillis()));
			this.userDao.save(user);

			importMessage = "瀵煎叆鎴愬姛";
		}
		return importMessage;
	}

	@Override
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="鍒犻櫎鐢ㄦ埛")
	public void deleteUser(int id) {
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
	@BussAnnotation(moduleName="鐢ㄦ埛绠＄悊",option="淇敼鐢ㄦ埛")
	public void updateUser(User user) {
		User users = (User)this.getUserByPhone(user.getPhone());
		user.setUpdateTime(new Timestamp(System.currentTimeMillis()));
		user.setId(users.getId());
		 if (user != null) {  
		        // 涓轰簡涓嶄笌spring鐨凚eanUtils鍐茬獊, 杩欓噷鐢ㄥ叏鍚嶈皟鐢ㄨ嚜瀹氫箟鐨凚eanUtils  
		        // 杩欐牱userModel涓负null鐨勫睘鎬у皢涓嶄細澶嶅埗鍒皍ser涓�, user涓病鏈変慨鏀圭殑灞炴�ц繕淇濇寔鍘熸潵鐨�, 涓嶄細琚鍒朵负null浜�  
		        com.sea.util.BeanUtils.copyProperties(user, users);  
		        // 鏇存柊鐢ㄦ埛  
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
