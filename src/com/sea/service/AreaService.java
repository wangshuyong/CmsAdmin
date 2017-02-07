package com.sea.service;

import java.util.ArrayList;
import java.util.List;

import com.sea.model.Area;
import com.sea.model.User;

public interface AreaService {
	
	public void addArea(Area u);
	public void deleteArea(int id);
	public List<Area> listAreasByPage(int length,int offset);
	public int getAreaCount();
	public Area getAreaById(int id);
	public Area getAreaByName(String name);
	public void updateArea(Area area);
	public boolean exists(String param);
	public String importAreas(ArrayList<ArrayList<String>> list);
	public List<Area> listAreaByPage(int startRows, int pageSize,String param);
	public List<Area> getAreaGroup();
}
