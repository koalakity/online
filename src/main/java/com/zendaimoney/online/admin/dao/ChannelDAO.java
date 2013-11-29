package com.zendaimoney.online.admin.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;

public interface ChannelDAO extends PagingAndSortingRepository<ChannelInfoVO, Long>, ChannelSelfDAO {

	/**
	 * 根据一级渠道ID查询二级渠道集合,按创建时间降序 2013-1-5 上午10:17:38 by HuYaHui
	 * 
	 * @param parentId
	 *            一级渠道ID
	 * @return 集合
	 */
	public List<ChannelInfoVO> findByParentIdOrderByCreateTimeDesc(Long parentId);

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-13 下午1:58:53
	 * @param parentId
	 * @param mode
	 * @return description:获取需要显示在前台的二级渠道信息
	 */
	public List<ChannelInfoVO> findByParentIdAndIsShowFrontOrderByCreateTimeDesc(Long parentId, Long mode);

	/**
	 * 根据渠道名称查询记录 2012-12-27 上午9:58:22 by HuYaHui
	 * 
	 * @param name
	 * @return
	 */
	public List<ChannelInfoVO> findByName(String name);

	/**
	 * 根据渠道ID查询记录 2012-12-27 上午9:58:07 by HuYaHui
	 * 
	 * @param code
	 * @return
	 */
	public List<ChannelInfoVO> findByCode(String code);

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-7 下午4:12:28
	 * @param rateId
	 * @return description:根据费率ID查询渠道信息
	 */
	public List<ChannelInfoVO> findByRateID(Long rateId);

	public ChannelInfoVO findById(Long id);
}
