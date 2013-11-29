package com.zendaimoney.online.dao.common;

import java.util.HashMap;
import java.util.Map;

public class VO {
	private String tableName;
	private Map<String,Object> map=new HashMap<String,Object>();
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public Map<String, Object> getMap() {
		return map;
	}
	public void setMap(Map<String, Object> map) {
		this.map = map;
	}
}
