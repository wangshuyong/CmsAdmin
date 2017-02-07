package com.sea.dao;

import java.util.List;

import com.sea.model.Notice;
import com.sea.model.User;

public interface NoticeDao extends BaseDao<Notice> {

	public Notice getNoticeById(int id);

	public List<Notice> getNoticeByUser(User user);

	public void delete(int id);

	public List<Notice> getNotices();

	public void updateNotice(Notice notice);

	public List<Notice> getNoticeByTitle(String title);

	public List<Notice> listNoticeByPage(int areaId, int startRows, int pageSize);

	List<Notice> listNoticeByPage(int areaId, int startRows, int pageSize,
			String param);

	public List<Notice> listNoticeByPage(int startRows, int pageSize,
			String param);

	public List<Notice> listNoticeByPage(int startRows, int pageSize);

	public int getNoticeCount();

	public List<Notice> listNewNotices(int id, String lastGetTime);
}
