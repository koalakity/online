//package com.zendaimoney.online.admin.service;
//
//import java.math.BigDecimal;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//import com.zendaimoney.online.admin.dao.loan.InvestManagerDao;
//import com.zendaimoney.online.admin.entity.loan.InvestInfoAdmin;
//import com.zendaimoney.online.common.MemcacheCacheHelper;
//import com.zendaimoney.online.common.MemcacheCacheHelperConstants;
//import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
//
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={
//	"classpath*:/applicationContext.xml",
//	"classpath*:/applicationContext-security.xml",
//	"classpath*:/applicationContext-shiro.xml",
//	"classpath*:/applicationContext-task.xml"})
//public class LoanManagerServiceTest {
//
//	@Autowired
//	private InvestManagerDao investManagerDao;
//	
//	@Test
//	public void testMemecache(){
//		System.out.println("开始");
//		InvestInfoAdmin adminObject=investManagerDao.findOne(new BigDecimal(758));
//		for(int i=0;i<9000000;i++){
//			String key=new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())+i;
//			//保存到缓存,过期时间30分钟
//			MemcacheCacheHelper.set(key, adminObject, MemcacheCacheHelperConstants.TIME30M);
//		}
//		System.out.println("结束");
//	}
//	
//	public void seachInverstInfo(BigDecimal loanId){
//		List<InvestInfoAdmin> iiaList= investManagerDao.findByLoanInfo_LoanId(loanId);
//		System.out.println(iiaList!=null?iiaList.size():"空");
//	}
//
//	/**
//	 * 查询借款信息投资列表
//	 * 
//	 * @param loanId
//	 * @return
//	 */
//	@Transactional(readOnly = true)
//	public void lan() {
//		List<InvestInfoAdmin> iiaList = investManagerDao
//				.findByLoanInfo_LoanId(BigDecimal.valueOf(2251));
//		System.out.println(iiaList != null ? iiaList.size() : "空");
//	}
//	
//	
//  	
//}
