package com.sea.service;

import java.util.ArrayList;
import java.util.List;

import com.sea.model.Area;
import com.sea.model.Notice;
import com.sea.model.User;

public interface NoticeService {

	public String addNotice(Notice n);

	public void deleteNotice(int id);

	public List<Notice> listNoticesByPage(User user, int length, int offset);

	public Notice getNoticeById(int id);

	public Notice getNoticeByTitle(String title);

	public void updateNotice(Notice notice);

	public boolean exists(String param);

	public List<Notice> listNoticesByPage(User user, int startRows,
			int pageSize, String param);

	public int getNoticeCount();

	public List<Notice> listNoticeByUser(User user);

	public List<Notice> listNewNotices(User user, String lastGetTime);
}
