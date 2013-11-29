package com.zendaimoney.online.admin.dao.account;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.zendaimoney.online.dao.personal.BaseDAO;

/**
 * Copyright (c) 2012 ZENDAI. All  Rights Reserved.
 * This software is published under the terms of the ZENDAI  
 * Software
 * @author Ray
 * @date: 2012-11-9 上午10:27:12
 * operation by:
 * description:
 */
@Component
public class AreaDao extends BaseDAO{

	public List queryAeraList(String code){
		if(StringUtils.isEmpty(code)){
			return null;
		}
		String sql="";
		if(code.equals("2")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/2/%'";//北京区级列表
		}else if(code.equals("23")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/23/%'";//天津区级列表
		}else if(code.equals("")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/863/%'";//上海区级列表
		}else if(code.equals("2475")){
			sql="select t from Area t where  t.treeLevel=4 and t.treePath like '1/2475/%'";//重庆区级列表
		}else{
			sql="select t from Area t where t.parentId = "+code;
		}
		return query(sql);
	}
}
