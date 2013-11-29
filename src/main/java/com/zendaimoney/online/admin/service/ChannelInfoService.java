package com.zendaimoney.online.admin.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.annotation.LogInfo;
import com.zendaimoney.online.admin.annotation.OperateKind;
import com.zendaimoney.online.admin.dao.ChannelDAO;
import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.web.DTO.ChannelInfoDTO;
import com.zendaimoney.online.dao.common.CommonDao;

/**
 * 渠道信息业务逻辑类
 * 
 * @author HuYaHui
 * 
 */
@Component
public class ChannelInfoService {
	private static Logger logger = LoggerFactory.getLogger(ChannelInfoService.class);

	@Autowired
	private ChannelDAO channelInfoDAO;
	@Autowired
	private StaffService staffService;
	@Autowired
	private CommonDao commonDao;

	/**
	 * 检查某个渠道信息是否被使用 2013-1-7 下午3:08:55 by HuYaHui
	 * 
	 * @param channelId
	 *            渠道ID
	 * @return true被使用|false未使用
	 */
	public String checkChannelInfoIsBeUse(Long channelId) {
		return channelInfoDAO.checkChannelInfoIsBeUse(channelId);
	}

	/**
	 * 根据某个ID，查询所属的二级渠道集合 2013-1-5 上午11:06:16 by HuYaHui
	 * 
	 * @return 集合
	 */
	public List<ChannelInfoVO> findChildListById(Long id) {
		return channelInfoDAO.findChildListById(id);
	}

	/**
	 * 获取所有一级渠道信息,注册页面级联使用 2013-1-5 上午10:09:42 by HuYaHui
	 * 
	 * @return 获取所有一级渠道信息
	 */
	public List<ChannelInfoVO> findAllParentInfo() {
		return channelInfoDAO.findAllParentInfo();
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-13 下午1:55:31
	 * @return description:获取需要显示在前台的一级渠道信息
	 */
	public List<ChannelInfoVO> findParentInfoByCond() {
		return channelInfoDAO.findParentInfoByCond();
	}

	/**
	 * 根据一级渠道ID查询二级渠道集合,按创建时间降序 2013-1-5 上午10:17:38 by HuYaHui
	 * 
	 * @param parentId
	 *            一级渠道ID
	 * @return 集合
	 */
	public List<ChannelInfoVO> findByParentIdOrderByCreateTimeDesc(Long parentId) {
		return channelInfoDAO.findByParentIdOrderByCreateTimeDesc(parentId);
	}

	public List<ChannelInfoVO> findByParentIdAndIsShowFrontOrderByCreateTimeDesc(Long parentId, Long mode) {
		return channelInfoDAO.findByParentIdAndIsShowFrontOrderByCreateTimeDesc(parentId, mode);
	}

	/**
	 * 根据渠道ID查询记录 2012-12-27 上午9:58:07 by HuYaHui
	 * 
	 * @param code
	 * @return
	 */
	public List<ChannelInfoVO> findByCode(String code) {
		return channelInfoDAO.findByCode(code);
	}

	/**
	 * 批量删除 2012-12-26 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delChannel(Long[] ids) {
		logger.info("进入批量删除方法id:" + ids);
		for (Long id : ids) {
			channelInfoDAO.delete(id);
			parentMap.remove(findById(id).getParentId());
		}
		deleteDirtyData();
	}

	/**
	 * 删除没有关联的一级渠道记录 2013-1-6 下午12:00:32 by HuYaHui
	 */
	@Transactional(readOnly = false)
	public void deleteDirtyData() {
		channelInfoDAO.deleteDirtyData();
	}

	/**
	 * 删除 2012-12-26 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public void delChannel(Long id) {
		logger.info("进入删除方法id:" + id);
		channelInfoDAO.delete(id);
		parentMap.remove(findById(id).getParentId());
	}

	/**
	 * 修改 //根据name1查询，如果数据库存在记录，只插入name2一条记录， //如果name1不存在，插入2条记录 2012-12-26
	 * 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	@Transactional(readOnly = false)
	public ChannelInfoVO updateChannel(String id, String code, String name1, String name2, String desc, Long mode, List<ChannelInfoVO> list) {
		logger.info("进入修改方法:id:" + id + " code:" + code + " name1:" + name1 + " name2:" + name2 + " desc:" + desc);
		String staffName = staffService.getCurrentStaff().getName();
		Long _parentId = null;
		Date createTime = new Date();
		if (list == null || list.size() == 0) {
			logger.info("一级渠道信息不存在,保存一级渠道信息！");
			// 一级信息为空，保存
			ChannelInfoVO _info = new ChannelInfoVO();
			_parentId = commonDao.getSequenceByName("CHANNEL_INFO_SEQ");
			_info.setId(_parentId);
			_info.setName(name1);
			_info.setDescription(desc);
			_info.setStaffName(staffName);
			_info.setCreateTime(new Date());
			_info.setUpdateTime(new Date());
			channelInfoDAO.save(_info);
		} else {
			// 一级渠道信息存在，关联
			_parentId = list.get(0).getId();
			createTime = list.get(0).getCreateTime();

		}
		// 二级信息
		ChannelInfoVO info = new ChannelInfoVO();
		// 保存新ID
		if (id == null || id.equals("")) {
			Long seq = commonDao.getSequenceByName("CHANNEL_INFO_SEQ");
			info.setId(seq);
		} else {
			// 使用已经存在的记录
			info.setId(Long.valueOf(id));
		}
		info.setName(name2);
		info.setCode(code);
		info.setDescription(desc);
		info.setStaffName(staffName);
		info.setParentId(_parentId);
		info.setCreateTime(createTime);
		info.setUpdateTime(new Date());
		info.setIsShowFront(mode);
		info.setRateID(1L);// 新建渠道绑定默认费率
		channelInfoDAO.save(info);
		logger.info("进入修改方法,保存二级渠道！");
		return info;
	}

	/**
	 * 查询渠道ID查询 2012-12-26 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	public ChannelInfoDTO findById(Long id) {
		ChannelInfoDTO dto = new ChannelInfoDTO();
		try {
			ChannelInfoVO obj = channelInfoDAO.findOne(id);
			if (obj != null) {
				BeanUtils.copyProperties(dto, obj);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dto;
	}

	/**
	 * 查询渠道名字查询 2012-12-26 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	public List<ChannelInfoVO> findByName(String name) {
		return channelInfoDAO.findByName(name);
	}

	/**
	 * 查询条件查询,带分页 2012-12-27 上午10:19:23 by HuYaHui
	 * 
	 * @param code
	 *            渠道id
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
	public List<ChannelInfoVO> findByCondition(String code, String name1, String name2, int page, int rows) {
		return channelInfoDAO.findByConditionOrderByNameAndCreateTime(code, name1, name2, (page - 1) * rows, rows);
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
	 * @return 总数
	 */
	public int findByConditionCount(String code, String name1, String name2) {
		return channelInfoDAO.findByConditionOrderByNameAndCreateTimeCount(code, name1, name2);
	}

	// 获取所有第一级渠道
	public static Map<Long, String> parentMap = new HashMap<Long, String>();

	/**
	 * TODO 增加到缓存 2012-12-26 下午6:01:20 by HuYaHui
	 * 
	 * @param id
	 *            一级缓存ID
	 * @return 一级渠道名称
	 */
	public String cacheMapForParentData(Long id) {
		String value = parentMap.get(id);
		if (value != null && !value.equals("")) {
			return value;
		} else {
			ChannelInfoVO vo = channelInfoDAO.findOne(id);
			parentMap.put(id, vo.getName());
		}
		return parentMap.get(id);
	}

	/**
	 * 查询所有渠道信息，按照name和创建时间排序 2012-12-26 下午2:07:33 by HuYaHui
	 * 
	 * @return
	 */
	public Iterable<ChannelInfoVO> findAll() {
		Iterable<ChannelInfoVO> channelList = channelInfoDAO.findAll(new Sort(Direction.DESC, "name"));
		return channelList;
	}

	/**
	 * 
	 * @author jihui
	 * @date 2013-3-7 下午4:12:58
	 * @param rateId
	 * @return description:根据费率ID查询渠道信息
	 */
	public List<ChannelInfoVO> findByRateID(Long rateId) {
		List<ChannelInfoVO> channelList = channelInfoDAO.findByRateID(rateId);
		return channelList;
	}

	@LogInfo(operateKind = OperateKind.修改, operateContent = "渠道费率设定：修改费率")
	public void saveModify(Long id, Long rateId) {
		ChannelInfoVO channel = channelInfoDAO.findOne(id);
		channel.setRateID(rateId);
		channelInfoDAO.save(channel);
	}
}
