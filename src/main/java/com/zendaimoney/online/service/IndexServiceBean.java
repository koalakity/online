package com.zendaimoney.online.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.ArithUtil;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.common.ObjectFormatUtil;
import com.zendaimoney.online.common.PageSplitConstants;
import com.zendaimoney.online.dao.index.IndexNoticeDao;
import com.zendaimoney.online.entity.borrowing.BorrowingInvestInfo;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.notice.IndexNotice;
import com.zendaimoney.online.vo.index.IndexLoanListVO;
import com.zendaimoney.online.vo.index.IndexLoanVO;
import com.zendaimoney.online.vo.index.IndexNoticeListVO;
import com.zendaimoney.online.vo.index.IndexPageVO;

/**
 * 首页管理
 * 
 * @author yjch
 */
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class IndexServiceBean {

	@Autowired
	private IndexNoticeDao noticeDao;

	// 前台进行中的借贷列表
	public void findLoanInfoList() {

	}

	// 初始化方法
	public IndexPageVO init() {
		IndexPageVO indexPage = new IndexPageVO();
		// 页脚信息
		indexPage.setFooterNoticeList(noticeDao.findByTypeBetweenOrderByIdAsc(new Integer(1), new Integer(18)));
		// 网站公告
		indexPage.setWebsiteList(noticeDao.findByTypeAndIsDelAndIsCommend(0,PageSplitConstants.INDEX_MAXRESULTS,new Integer(19), BigDecimal.ONE, BigDecimal.ONE));
		// 行业新闻
		indexPage.setIndustryNews(noticeDao.findByTypeAndIsDelAndIsCommend(0,PageSplitConstants.INDEX_MAXRESULTS,new Integer(20), BigDecimal.ONE, BigDecimal.ONE));
		// 媒体报道
		indexPage.setMediaReports(noticeDao.findByTypeAndIsDelAndIsCommend(0,PageSplitConstants.INDEX_MAXRESULTS,new Integer(21), BigDecimal.ONE, BigDecimal.ONE));
		// 微金融故事
		indexPage.setMicroFinancialStory(noticeDao.findByTypeAndIsDelAndIsCommend(0,PageSplitConstants.INDEX_MAXRESULTS,new Integer(22), BigDecimal.ONE, BigDecimal.ONE));
		return indexPage;
	}

	// 页脚信息
	public IndexNoticeListVO getFooter() {
		IndexNoticeListVO indexVO = new IndexNoticeListVO();
		List<IndexNotice> noticeList = noticeDao.findByTypeBetweenOrderByIdAsc(new Integer(1), new Integer(18));
		indexVO.setFooterNoticeList(noticeList);
		return indexVO;
	}

	// 二级页面A
	public IndexNotice getTwoGradePageA(String idStr) {
		Integer id = Integer.parseInt(idStr);
		return noticeDao.findByType(id);
	}

	// 二级页面B
	public IndexNotice getTwoGradePageB(String idStr) {
		Long id = Long.parseLong(idStr);
		return noticeDao.findById(id);
	}

	/**
	 * 前台公告内容 ，按时间倒序显示且后台删除的内容不显示，Modified by JiHui 2012-11-22 am
	 * 
	 * IsDel 为 1表示未删除
	 * 
	 **/
	// 文字列表
	public IndexNoticeListVO getNoticeList(String typeStr) {
		Integer type = Integer.parseInt(typeStr);
		IndexNoticeListVO indexVO = new IndexNoticeListVO();
		List<IndexNotice> noticeList = noticeDao.findByTypeAndIsDelOrderByCreDateDesc(type, BigDecimal.ONE);
		indexVO.setFooterNoticeList(noticeList);
		return indexVO;
	}

	// 图文列表
	public IndexNoticeListVO getStoryList() {
		IndexNoticeListVO indexVO = new IndexNoticeListVO();
		List<IndexNotice> noticeList = noticeDao.findByTypeAndIsDelOrderByCreDateDesc(new Integer(22), BigDecimal.ONE);
		indexVO.setFooterNoticeList(noticeList);
		return indexVO;
	}

	// 借款列表
	public IndexLoanListVO getLoanInfoList(String statusStr) {
		BigDecimal status = new BigDecimal(statusStr);
		List<BorrowingLoanInfo> loanInfoList = noticeDao.findAllLoanInfo(status);
		List<IndexLoanVO> indexLoanList = new ArrayList<IndexLoanVO>();
		IndexLoanListVO loanVO = new IndexLoanListVO();
		for (BorrowingLoanInfo loanInfo : loanInfoList) {
			IndexLoanVO vo = new IndexLoanVO();
			int creditGrade = 0;
			String speedProgress = "";
			double alsoNeedAmount = 0;
			double bidAmountTotal = 0;
			int bidNumber = 0;
			if (loanInfo.getUser().getUserCreditNote() != null) {
				creditGrade = loanInfo.getUser().getUserCreditNote().getCreditGrade().intValue();

			}
			double speedProgressDouble = 0;
			List<BorrowingInvestInfo> investInfoList = loanInfo.getInvestInfoList();
			for (BorrowingInvestInfo investInfo : investInfoList) {
				speedProgressDouble += investInfo.getHavaScale();
				bidAmountTotal = ArithUtil.add(bidAmountTotal, investInfo.getInvestAmount());
				bidNumber += 1;
			}
			alsoNeedAmount = ArithUtil.sub(loanInfo.getLoanAmount(), bidAmountTotal);
			speedProgress = ObjectFormatUtil.formatPercent(speedProgressDouble, "###0.00%");
			vo.setLoanId(loanInfo.getLoanId());
			vo.setLoanAmount(ObjectFormatUtil.formatCurrency(loanInfo.getLoanAmount()));
			vo.setLoanPeriod(loanInfo.getLoanDuration() + "个月");
			vo.setYearRate(ObjectFormatUtil.formatPercent(loanInfo.getYearRate(), "###0.00%"));
			vo.setLoanTitle(loanInfo.getLoanTitle());
			vo.setCreditGrade(creditGrade);
			vo.setSpeedProgress(speedProgress);
			vo.setAlsoNeedAmount(ObjectFormatUtil.formatCurrency(alsoNeedAmount));
			vo.setBidNumber(bidNumber);
			vo.setReleaseYMD(DateUtil.getYMDTime(loanInfo.getReleaseTime()));
			indexLoanList.add(vo);
		}
		loanVO.setIndexLoanList(indexLoanList);
		return loanVO;
	}
}
