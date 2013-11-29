package com.zendaimoney.online.dao.borrowing;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingUserInfoPerson;

public interface BorrowingUserInfoPersonCustomDao{

	public List<BorrowingUserInfoPerson> findUserId(BigDecimal userId);
}
