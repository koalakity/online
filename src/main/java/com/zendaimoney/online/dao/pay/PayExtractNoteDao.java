package com.zendaimoney.online.dao.pay;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.pay.PayExtractNote;

public interface PayExtractNoteDao extends PagingAndSortingRepository<PayExtractNote, Long> {
	PayExtractNote findByExtractId(BigDecimal extractId);

	List<PayExtractNote> findByUserIdAndVerifyStatusIn(BigDecimal userId, List verifyStatus);

	List<PayExtractNote> findByUserIdOrderByExtractTimeDesc(BigDecimal userId);

	List<PayExtractNote> findByUserIdAndKaihuNameOrderByExtractTimeDesc(BigDecimal userId, String kaihuName);
}
