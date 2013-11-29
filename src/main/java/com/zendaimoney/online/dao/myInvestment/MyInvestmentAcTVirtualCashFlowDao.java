package com.zendaimoney.online.dao.myInvestment;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zendaimoney.online.entity.myInvestment.MyInvestmentAcTVirtualCashFlow;

public interface MyInvestmentAcTVirtualCashFlowDao extends PagingAndSortingRepository<MyInvestmentAcTVirtualCashFlow, Long>{

	public MyInvestmentAcTVirtualCashFlow findByLoanIdAndCurrNum(Long loanId, Short currNum);
	
	public List<MyInvestmentAcTVirtualCashFlow> findByLoanId(Long loanId);
	
	/**
	 * 待回收
	 * @param loanId
	 * @param date1
	 * @param date2
	 * @return
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayDay between :date1 and :date2 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow> findNormalNote(@Param("loanId")Long loanId,@Param("date1")Date date1, @Param("date2")Date date2);
	
	
	/**
	 * @author Ray
	 * @date 2012-12-25 下午2:47:18
	 * @param loanId
	 * @param date1
	 * @param date2
	 * @return
	 * description:通过loanID和时间段中状态为0的未还款金额，和findNormalNote相比，增加过滤提前还款的还款数
	 * （修复bug1014添加）
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayStatus = 0 and a.repayDay between :date1 and :date2 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow> findNormalNotebyRepayStatus(@Param("loanId")Long loanId,@Param("date1")Date date1, @Param("date2")Date date2);
	
	/**
	 * 逾期记录
	 * @param loanId
	 * @param date1
	 * @param date2
	 * @return
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayDay < :date1 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow> findNote(@Param("loanId")Long loanId,@Param("date1")Date date1);
	/**
	 * 根据loanId 日期 以及状态查询 逾期未还的借款
	 * @param loanId
	 * 				借款id
	 * @param date1
	 * 				当前日期
	 * @return
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayDay < :date1 and a.repayStatus=0 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow>findNoteByStatus(@Param("loanId")Long loanId,@Param("date1")Date date1);
	
	/**
	 * 所有待回收
	 * @param loanId
	 * @param date1
	 * @return
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayDay > :date1 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow> findFutureAllNote(@Param("loanId")Long loanId,@Param("date1")Date date1);
	
	/**
	 * @author Ray
	 * @date 2012-12-25 下午2:45:14
	 * @param loanId 借款ID
	 * @param date1 日期
	 * @return
	 * description: 通过loanID和日期查询状态为0的未还款金额，和findFutureAllNote相比，增加过滤提前还款的还款数
	 * （修复bug1014添加）
	 */
	@Query("select a from MyInvestmentAcTVirtualCashFlow a where a.loanId = :loanId and a.repayStatus = 0 and a.repayDay > :date1 order by a.repayDay desc")
	public List<MyInvestmentAcTVirtualCashFlow> findFutureAllNotebyRepayStatus(@Param("loanId")Long loanId,@Param("date1")Date date1);
	
}
