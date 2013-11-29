package com.zendaimoney.online.admin.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;

public class ChannelDAOImpl implements ChannelSelfDAO {

	@PersistenceContext
	private EntityManager em;

	/**
	 * 检查某个渠道信息是否被使用，如果存在返回渠道代码，不存在空字符串 2013-1-7 下午3:08:55 by HuYaHui
	 * 
	 * @param channelId
	 *            渠道ID
	 * @return 渠道代码|空字符串
	 */
	public String checkChannelInfoIsBeUse(Long channelId) {
		List<String> objList = em.createNativeQuery("select code from channel_info c where c.id in (select channelinfo_id from users where channelinfo_id=?)").setParameter(1, channelId).getResultList();
		if (objList != null && objList.size() > 0) {
			return objList.get(0);
		}
		return "";

	}

	/**
	 * 删除没有关联的一级渠道记录 2013-1-6 下午12:00:32 by HuYaHui select * from channel_info
	 * cvo where cvo.code is null and cvo.parent_id is null and not exists(
	 * select * from channel_info c where code is not null and parent_id is not
	 * null and cvo.id=c.parent_id )
	 */
	public void deleteDirtyData() {
		String delSql = "delete from channel_info cvo where cvo.code is  null and cvo.parent_id is  null and not exists(select id from channel_info c where code is not null and parent_id is not null and cvo.id=c.parent_id)";
		em.createNativeQuery(delSql).executeUpdate();
	}

	/**
	 * 根据一级渠道ID，查询所属的二级渠道集合 2013-1-5 上午11:06:16 by HuYaHui
	 * 
	 * @return 集合
	 */
	public List<ChannelInfoVO> findChildListByParentId(Long id) {
		String sql = "select c from ChannelInfoVO c where c.parentId=?";
		return em.createQuery(sql).setParameter(1, id).getResultList();
	}

	/**
	 * 根据某个ID，查询所属的二级渠道集合 2013-1-5 上午11:06:16 by HuYaHui
	 * 
	 * @return 集合
	 */
	public List<ChannelInfoVO> findChildListById(Long id) {
		String sql = "select c from ChannelInfoVO c where c.parentId=(select cvo.parentId from ChannelInfoVO cvo where cvo.id=?)";
		return em.createQuery(sql).setParameter(1, id).getResultList();
	}

	/**
	 * 获取所有一级渠道信息,注册页面级联使用 2013-1-5 上午10:09:42 by HuYaHui
	 * 
	 * @return 获取所有一级渠道信息
	 */
	public List<ChannelInfoVO> findAllParentInfo() {
		return em.createQuery("select c from ChannelInfoVO c where (c.code=null or c.code='') and (c.parentId=null or c.parentId='') order by createTime asc").getResultList();
	}

	/**
	 * @return 获取需要显示在前台的一级渠道信息
	 */
	public List<ChannelInfoVO> findParentInfoByCond() {
		return em.createQuery("select cc from ChannelInfoVO cc where cc.id in(select c.parentId from ChannelInfoVO c where (c.code !=null or c.code!='') and (c.parentId!=null or c.parentId!='') and c.isShowFront=1) order by createTime asc").getResultList();
	}

	/**
	 * 根据条件查询，按照名称和时间降序 2012-12-27 上午10:42:15 by HuYaHui
	 * 
	 * @param code
	 *            渠道ID
	 * @param name1
	 *            一级渠道名称
	 * @param name2
	 *            二级渠道名称
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页显示几条
	 * @return
	 */
	public List<ChannelInfoVO> findByConditionOrderByNameAndCreateTime(String code, String name1, String name2, int page, int rows) {
		StringBuilder hqlString = new StringBuilder("select c from ChannelInfoVO c where c.code!=null and c.parentId!=null");
		// CODE
		if (code != null && !code.equals("")) {
			hqlString.append(" and c.code='").append(code).append("'");
		}
		// 二级渠道
		if (name2 != null && !name2.equals("")) {
			hqlString.append(" and c.name='").append(name2).append("'");
		}
		// 一级渠道
		if (name1 != null && !name1.equals("")) {
			hqlString.append(" and c.parentId =(select vo.id from ChannelInfoVO vo where (vo.code='' or vo.code=null) and (vo.parentId='' or vo.parentId=null) and vo.name='").append(name1).append("')");
		}
		hqlString.append(" order by c.parentId ,c.createTime desc");
		Query query = em.createQuery(hqlString.toString());
		query.setFirstResult(page);
		query.setMaxResults(rows);
		return query.getResultList();
	}

	/**
	 * 根据条件查询获取总记录 2012-12-27 上午10:42:15 by HuYaHui
	 * 
	 * @param code
	 *            渠道ID
	 * @param name1
	 *            一级渠道名称
	 * @param name2
	 *            二级渠道名称
	 * @param page
	 *            第几页
	 * @param rows
	 *            每页显示几条
	 * @return
	 */
	public int findByConditionOrderByNameAndCreateTimeCount(String code, String name1, String name2) {
		StringBuilder hqlString = new StringBuilder("select count(c) from ChannelInfoVO c where c.code!=null and c.parentId!=null");
		// CODE
		if (code != null && !code.equals("")) {
			hqlString.append(" and c.code='").append(code).append("'");
		}
		// 二级渠道
		if (name2 != null && !name2.equals("")) {
			hqlString.append(" and c.name='").append(name2).append("'");
		}
		// 一级渠道
		if (name1 != null && !name1.equals("")) {
			hqlString.append(" and c.parentId =(select vo.id from ChannelInfoVO vo where (vo.code='' or vo.code=null) and (vo.parentId='' or vo.parentId=null) and vo.name='").append(name1).append("')");
		}
		hqlString.append(" order by c.name,c.createTime desc");
		Query query = em.createQuery(hqlString.toString());
		return Integer.valueOf(query.getSingleResult() + "");
	}

	public List<ChannelInfoVO> findByCreateTime(int page, int rows) {
		StringBuilder hqlString = new StringBuilder("select c from ChannelInfoVO c where c.code!=null and c.parentId!=null");
		hqlString.append(" order by c.parentId ,c.createTime desc");
		Query query = em.createQuery(hqlString.toString());
		query.setFirstResult(page);
		query.setMaxResults(rows);
		return query.getResultList();
	}
}
