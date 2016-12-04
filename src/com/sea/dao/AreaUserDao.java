package com.sea.dao;

import java.util.List;
import com.sea.model.AreaUser;

public interface AreaUserDao extends BaseDao<AreaUser> {

	public AreaUser getAreaByUserId(int userId);
	public List<AreaUser> getAreaByLocation(String location);
	public void delete(int userId);
	public List<AreaUser> getUserByAreaId(int areaId);
	public List<AreaUser> listAreaUserByPage(int startRows,int pageSize);
	public void updateAreaUser(AreaUser area);
	public boolean existAreaUser(int areaId);
}
