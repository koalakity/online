package com.zendaimoney.online.dao.message;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.message.MessageStationLetter;
import com.zendaimoney.online.entity.message.MessageUsers;

public interface MessageStationLetterDao extends PagingAndSortingRepository<MessageStationLetter,Long>{

	List<MessageStationLetter> findBySenderIdAndMsgKindAndSenderDelStatus(MessageUsers sendId, String msgKind, BigDecimal receiverDelStatus);
	List<MessageStationLetter> findByReceiverIdAndMsgKindAndReceiverDelStatus(MessageUsers receiverId, String msgKind, BigDecimal receiverDelStatus);
	MessageStationLetter findByLetterId(BigDecimal letterId);
}
