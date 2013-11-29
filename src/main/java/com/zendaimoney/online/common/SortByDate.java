package com.zendaimoney.online.common;

import java.util.Comparator;

import com.zendaimoney.online.admin.entity.fundDetail.AcTFlowAdmin;

public class SortByDate implements Comparator{
	public int compare(Object o1, Object o2){
		AcTFlowAdmin f1 = (AcTFlowAdmin)o1;
		AcTFlowAdmin f2 = (AcTFlowAdmin)o2;
		return f1.getTradeDate().compareTo(f2.getTradeDate());
	}
}
