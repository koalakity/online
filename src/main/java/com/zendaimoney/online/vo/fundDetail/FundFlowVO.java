package com.zendaimoney.online.vo.fundDetail;

import java.util.Collection;
import java.util.List;


public class FundFlowVO {
	
	public static final String type_fund_01="10001";
	public static final String type_fund_02="10002";
	public static final String type_fund_03="10003";
	public static final String type_fund_04="10004";
	public static final String type_fund_05="10005";
	public static final String type_fund_06="10006";
	public static final String type_fund_07="10007";
	public static final String type_fund_08="10008";
	public static final String type_fund_09="10009";
	public static final String type_fund_10="10010";
	public static final String type_fund_11="10011";
	public static final String type_fund_12="10012";
	public static final String type_fund_13="10013";
	public static final String type_fund_14="10014";
	public static final String type_fund_15="10015";
	public static final String type_fund_16="10016";

	private long pageCount;
	private String date_start;
	private String date_end;
	private String type_fund;
	private Collection flowList;
	
	
	public long getPageCount() {
		return pageCount;
	}
	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}
	public Collection getFlowList() {
		return flowList;
	}
	public void setFlowList(Collection flowList) {
		this.flowList = flowList;
	}
	public String getDate_start() {
		return date_start;
	}
	public void setDate_start(String date_start) {
		this.date_start = date_start;
	}
	public String getDate_end() {
		return date_end;
	}
	public void setDate_end(String date_end) {
		this.date_end = date_end;
	}
	public String getType_fund() {
		return type_fund;
	}
	public void setType_fund(String type_fund) {
		this.type_fund = type_fund;
	}
	
	
}
