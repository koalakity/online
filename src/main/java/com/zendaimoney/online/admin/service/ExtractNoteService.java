/**
 * 
 */
package com.zendaimoney.online.admin.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.extract.ExtractNoteDao;
import com.zendaimoney.online.admin.entity.extract.ExtractNoteAdmin;
import com.zendaimoney.online.admin.vo.ExtractNoteForm;
import com.zendaimoney.online.dao.financial.FinancialSysMsgDao;
import com.zendaimoney.online.entity.financial.FinancialSysMsg;
import com.zendaimoney.online.service.pay.PayManager;

/**
 * @author Administrator
 * 后台提现管理
 */

@Service
@Transactional
public class ExtractNoteService {
	private static Logger logger = LoggerFactory.getLogger(ExtractNoteService.class);
	@Autowired
	private ExtractNoteDao extractNoteDao;
	
	@Autowired
	private PayManager payManager;
	
	@Autowired
	FinancialSysMsgDao sysMsgDao;
    /**
     * 提现信息查询
     * @param extractNoteForm
     * @param pageable
     * @return
     */
	public Page<ExtractNoteAdmin> findExtractPage(final ExtractNoteForm  extractNoteForm, Pageable pageable ){
		return extractNoteDao.findAll(new Specification<ExtractNoteAdmin>() {
			@Override
			public Predicate toPredicate(Root<ExtractNoteAdmin> root,
					CriteriaQuery<?> query, CriteriaBuilder cb) {
				List<Predicate> predicates=new ArrayList<Predicate>();
				if(null!=extractNoteForm.getVerifyStatus()){
					predicates.add(cb.equal(cb.lower(root.<String>get("verifyStatus")), extractNoteForm.getVerifyStatus()));
				}
				if(extractNoteForm.getExtractId()!= null && StringUtils.isNotEmpty(extractNoteForm.getExtractId().toString())){
					predicates.add(cb.like(root.<String>get("extractId").as(String.class), "%"+extractNoteForm.getExtractId().toString()+"%"));
				}
				if(StringUtils.isNotEmpty(extractNoteForm.getRealName())){
					predicates.add(cb.like(cb.lower(root.<String>get("accountUser").<String>get("userInfoPerson").<String>get("realName")), "%"+extractNoteForm.getRealName()+"%"));
				}
				if(StringUtils.isNotEmpty(extractNoteForm.getExtractAmountFrom()) && StringUtils.isNotEmpty(extractNoteForm.getExtractAmountTo())){
					predicates.add(cb.greaterThanOrEqualTo(root.<String>get("extractAmount"), extractNoteForm.getExtractAmountFrom()));
					predicates.add(cb.lessThanOrEqualTo(root.<String>get("extractAmount"), extractNoteForm.getExtractAmountTo()));
				}
				if(StringUtils.isNotEmpty(extractNoteForm.getExtractAmountFrom()) && StringUtils.isEmpty(extractNoteForm.getExtractAmountTo())){
					predicates.add(cb.greaterThanOrEqualTo(root.<String>get("extractAmount"), extractNoteForm.getExtractAmountFrom()));
				}
				if(StringUtils.isEmpty(extractNoteForm.getExtractAmountFrom()) && StringUtils.isNotEmpty(extractNoteForm.getExtractAmountTo())){
					predicates.add(cb.lessThanOrEqualTo(root.<String>get("extractAmount"), extractNoteForm.getExtractAmountTo()));
				}
			query.where(predicates.toArray(new Predicate[predicates.size()]));
				return null;
			}
		}, pageable);
		
	}
	/**
	 * 根据id查询提现记录
	 * @param extractId
	 * @return
	 */
	public ExtractNoteAdmin findOne(java.math.BigDecimal extractId){
		return extractNoteDao.findOne(extractId);
	}
	
	/**
	 * 更改提现记录状态
	 * @param extractNoteForm
	 */
	@LogInfo(operateKind=OperateKind.修改,operateContent="提现管理中成功修改提现记录状态")
	public String saveExtractNote(ExtractNoteForm extractNoteForm){
		if((extractNoteForm.getDescription().getBytes().length)>200){
			return "备注信息过长！";
		}
		ExtractNoteAdmin extractNote = findOne(extractNoteForm.getExtractId());
		if(null != extractNoteForm.getVerifyStatus()&&StringUtils.isNotEmpty(extractNoteForm.getVerifyStatus().toString())){
			if((extractNote.getVerifyStatus().equals(new BigDecimal(0))
					||extractNote.getVerifyStatus().equals(new BigDecimal(1)))
					&&extractNoteForm.getVerifyStatus().intValue()>extractNote.getVerifyStatus().intValue()){
				extractNote.setVerifyStatus(extractNoteForm.getVerifyStatus());
				FinancialSysMsg sysMsg = new FinancialSysMsg();
				if("2".equals(extractNoteForm.getVerifyStatus().toString())){
					payManager.withdrawSucc(extractNote.getAccountUser().getUserId(), extractNoteForm.getExtractId());
					   //发送系统消息
					sysMsg.setUserId(extractNote.getAccountUser().getUserId());
					sysMsg.setWordId(BigDecimal.valueOf(17));
					sysMsg.setParameter1(extractNote.getAccountUser().getLoginName());
					sysMsg.setParameter2(extractNote.getExtractAmount().toString());
					sysMsg.setParameter3(extractNote.getExtractCost().toString());
					sysMsg.setParameter4(extractNote.getExtractId().toString());
					sysMsg.setHappenTime(new Date());
					sysMsg.setIsDel("0");
					logger.info("SAVE:  系统消息表   SYS_MSG||  UserId="+extractNote.getAccountUser().getUserId()+" WordId=17"+" tParameter1="+extractNote.getAccountUser().getLoginName()
							+" Parameter2="+extractNote.getExtractAmount()+" tParameter3="+extractNote.getExtractCost()+" Parameter4="+extractNote.getExtractId());
					sysMsgDao.save(sysMsg);
					
				}
				if("3".equals(extractNoteForm.getVerifyStatus().toString())){
					payManager.withdrawFail(extractNote.getAccountUser().getUserId(), extractNoteForm.getExtractId());
				}
			}else{
				return "不可重复操作，请按F5键刷新页面！";
			}
		}
		
		
		extractNote.setDescription(extractNoteForm.getDescription());
		extractNote = extractNoteDao.save(extractNote);
		return null;
	}
	
}
