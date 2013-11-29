package com.zendaimoney.online.dao.common;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.common.Area;
import com.zendaimoney.online.entity.common.Rate;
import com.zendaimoney.online.entity.common.SysCode;

/**
 * 公共的Dao interface.
 * 
 * @author yijc
 */
public interface CommonDao extends PagingAndSortingRepository<SysCode, Long>,CommonCustomDao{
    
    SysCode findByCodeId(BigDecimal codeId);
    
    @Query("select a from Area a where id=?1")
    Area  getAreaInfoById(BigDecimal id);
    
    @Query("select r from Rate r where id=?1")
    Rate  getRate(Long id);
    
}
