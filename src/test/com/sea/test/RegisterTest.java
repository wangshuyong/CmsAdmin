package test.com.sea.test;

import static org.junit.Assert.*;

import java.awt.List;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;

import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.sea.dao.UserDao;
import com.sea.dao.impl.UserDaoImpl;
import com.sea.model.Log;
import com.sea.model.User;
import com.sea.service.LogService;
import com.sea.service.UserService;
import com.sea.util.ExcelUtils;

public class RegisterTest {

	ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
	@Test
	public void testAdd() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		UserService user =new UserServiceImpl();
		UserService userService =(UserService) ctx.getBean("userService");
		User u = new User();
		u.setPhone("13426371080");
		u.setPassword("123456");
		u.setEmail("wangshuyong@163.com");
		u.setSex("0");
		u.setPower("1");
		if(!userService.exists(u.getPhone())){		
			userService.addUser(u);
		}else {
			System.out.println("exists");
		}
//		assertEquals(true, userService.exists(u));
	}
	@Test
	public void testAddLog() {
//		ClassPathXmlApplicationContext ctx = new ClassPathXmlApplicationContext("applicationContext.xml");
//		UserService user =new UserServiceImpl();
		LogService logService =(LogService) ctx.getBean("logService");
		Log log = new Log();
		log.setCreateTime(new Date());
		log.setOperator_id(26);
		logService.addLog(log);
//		assertEquals(true, userService.exists(u));
	}
	@Test
	public void testdelete() {
		UserService userService =(UserService) ctx.getBean("userService");
		userService.deleteUser(42);
	}
	
	@Test
	public void testDaoAdd() {
		
		UserDao user =(UserDao) ctx.getBean("u");
//		UserDao user = new UserDaoImpl();
		User u = new User();
		u.setPhone("13426370014");
		u.setPassword("123456");
		u.setEmail("wangshuyong@163.com");
		u.setSex("0");
		user.save(u);
//		assertEquals(true, user.findUserByPhone("13426370014"));
	}
	@Test
	public void testDaofind() {
		UserDao user = (UserDao) ctx.getBean("u");
//		assertEquals(true, user.getUserByParam("13426370015","E10ADC3949BA59ABBE56E057F20F883E"));
	}
	
	@Test
	public void testDaodelete() {
		UserDao user = (UserDao) ctx.getBean("u");
		user.delete(24);
	}
	
	@Test
	public void testImportUser(){
		ExcelUtils exc = new ExcelUtils();
		String filePath="e://users.xlsx";
		File excelFile = new File(filePath); //创建文件对象
        FileInputStream is = null;
        try {
			is = new FileInputStream(excelFile);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		//用于存储用户信息
		ArrayList<ArrayList<String>> list;
		UserService userService =(UserService) ctx.getBean("userService");
			try {
				list = exc.readExcel(is);
				userService.importUsers(list);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	/*
	@Test
	public void testDaolist() {
		UserDao user = (UserDao) ctx.getBean("u");
		java.util.List list = user.list("from User",null);
		System.out.println(list.size());
//		assertEquals(true, user.findUserByPhone("13212345678"));
	}
*/

}
