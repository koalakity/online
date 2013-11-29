package com.zendaimoney.online.dao.index;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import com.zendaimoney.online.common.PageSplitConstants;
import com.zendaimoney.online.entity.borrowing.BorrowingLoanInfo;
import com.zendaimoney.online.entity.notice.IndexNotice;


/**
 * 首页的Dao interface实现类.
 * 
 * @author yijc
 */
public class IndexNoticeDaoImpl implements IndexNoticeCustomeDao{
	
	@PersistenceContext
	private EntityManager em;
	private String QUERY_ALL_NOTICE_SQL = "SELECT n.id,n.title,n.content,n.orders,n.type,n.isCommend,n.isDel,n.creDate,n.staff FROM IndexNotice n WHERE  n.type <=18 order by n.id";
	private String QUERY_ALL_LOANINFO_SQL = "SELECT b FROM BorrowingLoanInfo b where b.status=:status ";
	
	public List<IndexNotice> findAllNotice(){
		return  em.createQuery(QUERY_ALL_NOTICE_SQL).getResultList();
	}
	
	public List<BorrowingLoanInfo> findAllLoanInfo(BigDecimal status){
		List<BorrowingLoanInfo> rtnList=em.createQuery(QUERY_ALL_LOANINFO_SQL).
				setParameter("status", status)
				.setMaxResults(10)
				.getResultList();
		return rtnList;
	}


	/**
	 * 
	 * 2012-12-5 下午1:08:40 by HuYaHui
	 * @param offset
	 * 			从第几行开始
	 * @param pagesize
	 * 			每页显示的数量
	 * @param type
	 * @param isDel
	 * @param isCommend
	 * @return
	 * 
	 * select * from notice indexnotic0_ 
		where indexnotic0_.type=19 
		and indexnotic0_.is_del=1 
		and indexnotic0_.is_commend=1
		order by cre_date desc
	 */
	public List<IndexNotice> findByTypeAndIsDelAndIsCommend(int offset,
			int pagesize, Integer type, BigDecimal isDel, BigDecimal isCommend) {
		String hql="SELECT i FROM IndexNotice i WHERE i.type=? and i.isDel=? and i.isCommend=? order by creDate desc";
		javax.persistence.Query query=em.createQuery(hql);
		query.setParameter(1, type);
		query.setParameter(2, isDel);
		query.setParameter(3, isCommend);
		query.setMaxResults(pagesize);
		query.setFirstResult(offset);
		return query.getResultList();
	}
}
