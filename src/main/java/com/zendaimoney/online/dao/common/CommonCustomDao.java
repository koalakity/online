package com.zendaimoney.online.dao.common;


/**
 * 公共的Dao interface.
 * 
 * @author yijc
 */
public interface CommonCustomDao{
    
    String getFlowSeq();
    
    Long getSequenceByName(String seqName);
}
