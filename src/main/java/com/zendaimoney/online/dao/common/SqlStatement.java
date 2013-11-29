package com.zendaimoney.online.dao.common;

import java.util.List;

public class SqlStatement {
	
	public SqlStatement(){}
	public SqlStatement(String join, String name, String condition, Object value) {
		super();
		this.join = join;
		this.name = name;
		this.condition = condition;
		this.value = value;
	}
	
	/**
	 * 设置属性名
	 * @param name
	 * 			字段名
	 * @param value
	 * 			值
	 */
	public SqlStatement(String name, Object value) {
		super();
		this.name = name;
		this.value = value;
	}
	/**
	 * 设置属性名
	 * @param join
	 * 			符号
	 * @param name
	 * 			字段名
	 * @param value
	 * 			值
	 */
	public SqlStatement(String join, String name,List<Object> value) {
		super();
		this.name = name;
		this.value = value;
	}
	
	//and|or
	private String join="and";
	//字段名字
	private String name;
	//字段条件等于或者大于或者小于
	private String condition="=";
	//值
	private Object value;
	public String getJoin() {
		return join;
	}
	public SqlStatement setJoin(String join) {
		this.join = join;
		return this;
	}
	public String getName() {
		return name;
	}
	public SqlStatement setName(String name) {
		this.name = name;
		return this;
	}
	public String getCondition() {
		return condition;
	}
	public SqlStatement setCondition(String condition) {
		this.condition = condition;
		return this;
	}
	public Object getValue() {
		return value;
	}
	public SqlStatement setValue(Object value) {
		this.value = value;
		return this;
	}
	
}
