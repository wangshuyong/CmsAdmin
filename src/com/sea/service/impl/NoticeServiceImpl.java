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
import com.sea.dao.NoticeDao;
import com.sea.dao.UserDao;
import com.sea.model.Area;
import com.sea.model.AreaUser;
import com.sea.model.Notice;
import com.sea.model.User;
import com.sea.service.AreaService;
import com.sea.service.NoticeService;
import com.sea.service.UserService;
import com.sea.util.BussAnnotation;
import com.sea.util.MD5Util;

@Component("noticeService")
public class NoticeServiceImpl implements NoticeService {
	private String operateResult = null;
	private NoticeDao noticeDao;

	@Override
	public boolean exists(String name) {
		List<Notice> list = noticeDao.getNoticeByTitle(name);
		if (list.size() > 0) {
			return true;
		} else
			return false;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	@Override
	@BussAnnotation(moduleName = "通知管理", option = "发布标题为:”+'notice.getTitle()'+“通知")
	public String addNotice(Notice n) {
		// TODO Auto-generated method stub
		if (n.getTitle() == null || "".equals(n.getTitle())) {
			return "fail";
		} else {
			n.setCreateTime(new Timestamp(System.currentTimeMillis()));
			n.setType("2");
			noticeDao.save(n);
		}
		return "addnotice";
	}

	public int getNoticeCount() {

		return noticeDao.getNoticeCount();

	}

	@Override
	@BussAnnotation(moduleName = "通知管理", option = "删除名为：" + "的通知")
	public void deleteNotice(int id) {
		noticeDao.delete(id);
	}

	@Override
	public List<Notice> listNoticeByUser(User user) {

		return noticeDao.getNoticeByUser(user);

	}
	@Override
	public List<Notice> listNewNotices(User user,String lastGetTime) {
		List<Notice> list = null;
			list = noticeDao.listNewNotices(user.getArea().getId(), lastGetTime);
		return list;
	}

	@Override
	public List<Notice> listNoticesByPage(User user, int startRows,
			int pageSize, String param) {
		List<Notice> list = null;
		if ("0".equals(user.getPower())) {

			list = noticeDao.listNoticeByPage(startRows, pageSize, param);

		} else if ("1".equals(user.getPower())) {

			list = noticeDao.listNoticeByPage(user.getArea().getId(),
					startRows, pageSize, param);
		}

		return list;
	}

	@Override
	public List<Notice> listNoticesByPage(User user, int length, int offset) {
		List<Notice> list = null;
		if ("0".equals(user.getPower())) {
			list = noticeDao.listNoticeByPage(length, offset);
		} else
			list = noticeDao.listNoticeByPage(user.getArea().getId(), length,
					offset);
		return list;
	}

	@Override
	public Notice getNoticeById(int id) {
		Notice notice = noticeDao.getNoticeById(id);
		return notice;
	}

	@Override
	@BussAnnotation(moduleName = "通知管理", option = "修改名为：”+'notice.getTitle()'+“的信息")
	public void updateNotice(Notice notice) {
		if (notice != null) {
			noticeDao.update(notice);
		}
	}

	public NoticeDao getNoticeDao() {
		return noticeDao;
	}

	@Resource(name = "noticeDao")
	public void setNoticeDao(NoticeDao noticeDao) {
		this.noticeDao = noticeDao;
	}

	@Override
	public Notice getNoticeByTitle(String title) {
		List<Notice> list = noticeDao.getNoticeByTitle(title);
		Notice notice = (Notice) list.get(0);
		if (list.size() <= 0) {
			return null;
		} else {
			return notice;
		}
	}

}
