package com.zendaimoney.online.dao.homepage;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.homepage.HomepageUserCreditNote;

public interface HomepageUserCreditNoteDAO extends PagingAndSortingRepository<HomepageUserCreditNote, Long> {
//	HomepageUserCreditNote findByUserId(BigDecimal userId);
}
