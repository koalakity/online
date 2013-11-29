package com.zendaimoney.online.dao.personal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PersonalDAO extends BaseDAO{
	@PersistenceContext
	private EntityManager em;
	private String sql;
//	public List queryUsers(BigDecimal userId){
//		String sql="select user from PersonalUsers user where user.userId="+userId;
//		return query(sql);
//	}
//	public PersonalUserInfoPerson queryPersonalInfo(BigDecimal userId){
//		String sql="select personal from PersonalUserInfoPerson personal where personal.userId="+userId;
//		List userPersonalInfoList=query(sql);
//		if(userPersonalInfoList==null){
//			return null;
//		}
//		if(userPersonalInfoList.size()==0){
//			return null;
//		}
//		if(userPersonalInfoList.get(0)==null){
//			return null;
//		}
//		PersonalUserInfoPerson person=(PersonalUserInfoPerson)userPersonalInfoList.get(0);
//		return person;
//	}
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
//	public void savePersonalBase(PersonalUserInfoPerson person){
//		if(person==null){
//			throw new RuntimeException("参数错误!");
//		}
//	}
}
