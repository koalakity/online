package com.zendaimoney.online.admin.dao;

import java.util.List;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;

public interface ChannelSelfDAO {

	/**
	 * 检查某个渠道信息是否被使用，如果存在返回渠道代码，不存在空字符串 2013-1-7 下午3:08:55 by HuYaHui
	 * 
	 * @param channelId
	 *            渠道ID
	 * @return 渠道代码|空字符串
	 */
	public String checkChannelInfoIsBeUse(Long channelId);

	/**
	 * 删除没有关联的一级渠道记录 2013-1-6 下午12:00:32 by HuYaHui
	 */
	public void deleteDirtyData();

	/**
	 * 根据一级渠道ID，查询所属的二级渠道集合 2013-1-5 上午11:06:16 by HuYaHui
	 * 
	 * @return 集合
	 */
	public List<ChannelInfoVO> findChildListByParentId(Long id);

	/**
	 * 根据某个ID，查询所属的二级渠道集合 2013-1-5 上午11:06:16 by HuYaHui
	 * 
	 * @return 集合
	 */
	public List<ChannelInfoVO> findChildListById(Long id);

	/**
	 * 获取所有一级渠道信息,注册页面级联使用 2013-1-5 上午10:09:42 by HuYaHui
	 * 
	 * @return 获取所有一级渠道信息
	 */
	public List<ChannelInfoVO> findAllParentInfo();

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-13 下午1:56:13
	 * @return description:获取需要显示在前台的一级渠道信息
	 */
	public List<ChannelInfoVO> findParentInfoByCond();

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
	public List<ChannelInfoVO> findByConditionOrderByNameAndCreateTime(String code, String name1, String name2, int page, int rows);

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
	public int findByConditionOrderByNameAndCreateTimeCount(String code, String name1, String name2);

	public List<ChannelInfoVO> findByCreateTime(int page, int rows);

}
