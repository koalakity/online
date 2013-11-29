package com.zendaimoney.online.admin.dao.account;

import java.util.List;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.account.AccountUsersAdmin;
import com.zendaimoney.online.admin.entity.account.MemoNoteAdmin;

public interface MemoNoteAdminDao extends PagingAndSortingRepository<MemoNoteAdmin, Long>,JpaSpecificationExecutor<MemoNoteAdmin>{
	List<MemoNoteAdmin> findByAccountUsersAdmin(AccountUsersAdmin accountUsersAdmin);
}
