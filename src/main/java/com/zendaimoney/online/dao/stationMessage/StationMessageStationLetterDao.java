package com.zendaimoney.online.dao.stationMessage;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter;

public interface StationMessageStationLetterDao  extends PagingAndSortingRepository<StationMessageStationLetter, Long>{
	
	@Query("select a from StationMessageStationLetter a where a.loanId = ?1 and a.isDel = 0 and a.msgKind = 2 order by childId,senderTime")
	public List<StationMessageStationLetter> findByIsDelOrderByMsgPath(BigDecimal loanId);
	
	public StationMessageStationLetter findByLetterId(BigDecimal letterId);
}
