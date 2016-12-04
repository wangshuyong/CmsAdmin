package com.sea.dao;

import java.util.List;
import com.sea.model.Area;

public interface AreaDao extends BaseDao<Area> {

	public Area getAreaById(int id);
	public List<Area> getAreaByAreaName(String name);
	public void delete(int id);
	public List<Area> getAreas();
	public List<Area> listAreaByPage(int startRows,int pageSize);
	public void updateArea(Area area);
	public boolean existArea(int areaId);
}
