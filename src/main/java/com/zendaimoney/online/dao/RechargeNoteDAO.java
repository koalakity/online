package com.zendaimoney.online.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.RechargeNoteVO;

/**
 * DAO
 * @author HuYaHui
 *
 */
public interface RechargeNoteDAO extends PagingAndSortingRepository<RechargeNoteVO, Long>{
	public List<RechargeNoteVO> findByUserId(Long userId);
	public RechargeNoteVO findByOrderno(String orderno);
}
