package com.zendaimoney.online.dao.index;

import java.math.BigDecimal;
import java.util.List;

import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.notice.IndexNotice;

/**
 * 首页的扩展Dao interface.
 * 
 * @author yijc
 */
public interface IndexNoticeCustomeDao {

	List<IndexNotice> findAllNotice();
	List<BorrowingLoanInfo> findAllLoanInfo(BigDecimal status);

	/**
	 * 网站公告，只显示4个分页方法
	 * 2012-12-5 下午1:08:40 by HuYaHui
	 * @param offset
	 * 			从第几行开始
	 * @param pagesize
	 * 			每页显示的数量
	 * @param type
	 * @param isDel
	 * @param isCommend
	 * @return
	 */
	List<IndexNotice> findByTypeAndIsDelAndIsCommend(int offset,int pagesize,Integer type, BigDecimal isDel, BigDecimal isCommend);

}
