/**
 * 
 */
package com.zendaimoney.online.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.ExtractNoteVO;

public interface ExtractNoteDAO extends PagingAndSortingRepository<ExtractNoteVO, Long>{
	public ExtractNoteVO findByExtractId(Long extractId);

}
