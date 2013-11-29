package com.zendaimoney.online.dao.common;

import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;

import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.upload.FileUploadVO;

/**
 * VO工具类
 * 
 * @author HuYaHui
 */
public class BeanTools {
	private final String GET="get";
	private static BeanTools beanTools;
	private Map<String, VO> voMap = new HashMap<String, VO>();

	private Map<String, List<Method>> voGetMethodMap = new HashMap<String, List<Method>>();
	
	private BeanTools(){
	}

	public static void main(String[] args) throws Exception {
		FileUploadVO f = new FileUploadVO();
		f.setUpdateTime(new Date());
		
		VO vo=BeanTools.getInstance().getVOColumnMap(f);
		System.out.println(vo.getTableName());
		
		Map<String,Object> fieldValueMap=vo.getMap();
		for(String key:fieldValueMap.keySet()){
			System.out.println("key:"+key+" value:"+fieldValueMap.get(key));
		}

		/*FileUploadVO f = new FileUploadVO();
		f.setId(1l);
		f.setFilePath("sssxx");
		f.setIsDel("s");
		VO vo=BeanTools.getInstance().getVOFieldMap(f);
		System.out.println(vo.getTableName());
		Map<String,Object> fieldValueMap=vo.getMap();
		for(String key:fieldValueMap.keySet()){
			System.out.println("key:"+key+" value:"+fieldValueMap.get(key));
		}*/
	}
	private VO getVO(Object obj) {
		String obj_name=obj.getClass().getName();
		VO vo = voMap.get(obj_name);
		if (vo == null) {
			vo=new VO();
			Map<String,Object> map=new HashMap<String,Object>();
			Class<?> cls = obj.getClass();
			Table table = cls.getAnnotation(Table.class);
			String table_name=table.name();
			vo.setTableName(table_name);
			Method[] methods = cls.getDeclaredMethods();
			List<Method> method_list=new ArrayList<Method>();
			for (Method method : methods) {
				String method_name = method.getName();
				if (method_name.indexOf(GET) != 0) {
					//如果不是get方法，下一个
					continue;
				}
				method_list.add(method);
				String column_name = "";
				Column column = method.getAnnotation(Column.class);
				if (column == null) {
					JoinColumn j=method.getAnnotation(JoinColumn.class);
					if(j==null){
						continue;
					}
					column_name = j.name();
				} else {
					column_name = column.name();
				}
				String field_name = method_name.substring(3);
				field_name = field_name.substring(0, 1).toLowerCase()
						+ field_name.substring(1);
				map.put(field_name, column_name);
			}
			voGetMethodMap.put(obj_name, method_list);
			vo.setMap(map);
			voMap.put(obj_name, vo);
		}
		return vo;
	}

	/**
	 * 获取工具对象
	 * 2013-1-23 上午10:45:18 by HuYaHui 
	 * @return
	 */
	public static BeanTools getInstance(){
		if(beanTools==null){
			Lock lock = new ReentrantLock();  
		    lock.lock();
		    if(beanTools==null){
		    	beanTools=new BeanTools();
		    }
		    lock.unlock();
		}
		return beanTools;
	}
	
	/**
	 * 返回某个对象，属性和对应值集合
	 * 2013-1-23 下午1:15:47 by HuYaHui 
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	public VO getVOFieldMap(Object obj){
		try {
			String obj_name=obj.getClass().getName();
			VO cacheVO=getVO(obj);
			Map<String,Object> fieldValueMap=new HashMap<String,Object>();
			List<Method> method_list=voGetMethodMap.get(obj_name);
			for(Method method:method_list){
				String method_name=method.getName();
				String field_name = method_name.substring(3);
				field_name = field_name.substring(0, 1).toLowerCase()
						+ field_name.substring(1);
				Object method_value=getIdValueFromObject(obj, method);
				if(method_value==null){
					continue;
				}
				fieldValueMap.put(field_name,method_value);
			}
			VO fieldMapVO=new VO();
			fieldMapVO.setTableName(cacheVO.getTableName());
			fieldMapVO.setMap(fieldValueMap);
			return fieldMapVO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("啊啊啊，报错啦getVOFieldMap");
		}
		return null;
	}
	
	/**
	 * 返回某个对象的,列名和对应值集合
	 * 2013-1-23 下午1:14:25 by HuYaHui 
	 * @param obj
	 * 			某个对象
	 * @return
	 * 			VO对象
	 */
	public VO getVOColumnMap(Object obj){
		try {
			String obj_name=obj.getClass().getName();
			VO cacheVO=getVO(obj);
			Map<String,Object> fieldValueMap=new HashMap<String,Object>();
			Map<String,Object> cacheFieldToColumn=cacheVO.getMap();
			List<Method> method_list=voGetMethodMap.get(obj_name);
			for(Method method:method_list){
				String method_name=method.getName();
				String field_name = method_name.substring(3);
				field_name = field_name.substring(0, 1).toLowerCase()
						+ field_name.substring(1);
				Object method_value=getIdValueFromObject(obj,method);
				if(method_value==null){
					continue;
				}
				fieldValueMap.put(cacheFieldToColumn.get(field_name)+"",method_value);
			}
			VO fieldMapVO=new VO();
			fieldMapVO.setTableName(cacheVO.getTableName());
			fieldMapVO.setMap(fieldValueMap);
			return fieldMapVO;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("啊啊啊，报错啦getVOColumnMap");
		}
		return null;
	}
	
	private Object getIdValueFromObject(Object obj,Method method){
		try {
			Object method_value=method.invoke(obj, method.getParameterTypes());
			if(method_value==null){
				return null;
			}
			JoinColumn joinColumn=method.getAnnotation(JoinColumn.class);
			if(joinColumn!=null){
				Method[] methods=method_value.getClass().getDeclaredMethods();
				for(Method _method:methods){
					if(_method.getAnnotation(Id.class)!=null){
						return _method.invoke(method_value,_method.getParameterTypes());
					}
				}
			}
			return method_value;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

}
