package com.zendaimoney.online.dao.personal;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.entity.personal.PersonalUserMessageSet;

public interface PersonalUserMessageSetDao extends PagingAndSortingRepository<PersonalUserMessageSet,BigDecimal>{
   List<PersonalUserMessageSet> findByUserId(BigDecimal userId); 
   List<PersonalUserMessageSet> findByUserIdAndKindId(BigDecimal userId,BigDecimal kindId);
}
