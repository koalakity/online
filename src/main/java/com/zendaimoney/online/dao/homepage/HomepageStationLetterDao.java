package com.zendaimoney.online.dao.homepage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageStationLetter;

public interface HomepageStationLetterDao extends PagingAndSortingRepository<HomepageStationLetter, Long>, HomepageCustomDao {
	List<HomepageStationLetter> findBySenderIdAndMsgKindOrderBySenderTimeDesc(BigDecimal userId, String msgKind);

	List<HomepageStationLetter> findByLoanIdLoanIdAndMsgKindOrderBySenderTimeDesc(BigDecimal loanId, String msgKind);

	@Query("select distinct h.loanId.loanId from HomepageStationLetter h where h.senderId =? and h.msgKind=?")
	List getLetter(BigDecimal senderId, String msgKind);

	List<HomepageStationLetter> findBySenderIdAndLoanIdLoanIdAndMsgKindOrderBySenderTimeDesc(BigDecimal userId, BigDecimal loanId, String msgKind);
}
