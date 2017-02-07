package com.sea.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

import com.sea.dao.NoticeDao;
import com.sea.dao.NoticeDao;
import com.sea.model.Notice;
import com.sea.model.User;

@Component("noticeDao")
public class NoticeDaoImpl extends BaseDaoImpl<Notice> implements NoticeDao {

	@Override
	public void delete(int id) {
		Notice notice = this.load(id);
		this.delete(notice);
	}

	@Override
	public Notice getNoticeById(int id) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where n.id=?";
		List<Notice> list = this.list(hql, id);
		Notice notice = null;
		for (int i = 0; i < list.size(); i++) {
			notice = (Notice) list.get(i);
		}
		return notice;
	}

	@Override
	public List<Notice> getNoticeByUser(User user) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where n.user.area.id=? order by n.id desc";
		return this.list(hql, user.getArea().getId());
	}

	@Override
	public List<Notice> getNotices() {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area order by n.id";
		return this.list(hql);
	}

	public int getNoticeCount() {
		String hql = "from Notice";

		return super.getAllRowCount(hql);

	}

	@Override
	public List<Notice> listNoticeByPage(int areaId, int startRows, int pageSize) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where  n.area.id="
				+ areaId + " order by n.id desc";
		List<Notice> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<Notice> listNoticeByPage(int areaId, int startRows,
			int pageSize, String param) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where n.title like '%"
				+ param
				+ "%' or n.content like '%"
				+ param
				+ "%'  and n.area.id=" + areaId + " order by n.id";
		List<Notice> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<Notice> listNoticeByPage(int startRows, int pageSize) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area order by n.id desc";
		List<Notice> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public List<Notice> listNoticeByPage(int startRows, int pageSize,
			String param) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where n.title like '%"
				+ param
				+ "%' or n.content like '%"
				+ param
				+ "%'  order by n.id";
		List<Notice> list = super.queryForPage(hql, startRows, pageSize);
		return list;
	}

	@Override
	public void updateNotice(Notice notice) {
		this.getHibernateTemplate().merge(notice);
	}

	@Override
	public List<Notice> getNoticeByTitle(String title) {
		String hql = "from Notice n inner join fetch n.user inner join fetch n.area where n.title=?";
		return this.list(hql, title);
	}

	@Override
	public List<Notice> listNewNotices(int id, String lastGetTime) {
		String hql = "from Notice n inner join fetch n.user  inner join fetch n.area  where n.area.id="
				+ id
				+ " and n.createTime>= '"
				+ lastGetTime
				+ "' order by n.createTime desc";
		return this.list(hql);
	}

}
