package com.zendaimoney.online.admin.dao;

import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.admin.entity.ChannelInfoVO;
import com.zendaimoney.online.admin.web.DTO.ChannelInfoDTO;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.dao.common.CommonDao;


/**
 * 渠道信息数据持久类
 * @author HuYaHui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
			"classpath*:/applicationContext.xml",
			"classpath*:/applicationContext-security.xml",
			"classpath*:/applicationContext-shiro.xml",
			"classpath*:/applicationContext-task.xml"})
public class ChannelInfoDAOTest {
	 
	@Autowired
	ChannelDAO channelInfoDAO;
	
	@Autowired
	private CommonDao commonDao;
	

	/**
	 * 检查某个渠道信息是否被使用，如果存在返回渠道代码，不存在空字符串
	 * 2013-1-7 下午3:08:55 by HuYaHui
	 * @param channelId
	 * 			渠道ID
	 * @return
	 * 			渠道代码|空字符串
	 */
	@Test
	public void checkChannelInfoIsBeUseTest(){
		System.out.println(channelInfoDAO.checkChannelInfoIsBeUse(2l));
	}
	
	/**
	 * 删除没有关联的一级渠道记录
	 * 2013-1-6 下午12:00:32 by HuYaHui 
	 * select * from channel_info cvo where 
		cvo.code is  null 
		and cvo.parent_id is  null 
		and not exists(
			select * from channel_info c 
				where code is not null 
				and parent_id is not null 
				and cvo.id=c.parent_id
		)
	 */
	@Test
    @Transactional(readOnly=false)
	public void deleteDirtyData(){
		channelInfoDAO.deleteDirtyData();
	}
	
	/**
	 * 根据某个ID，查询所属的二级渠道集合
	 * 2013-1-5 上午11:06:16 by HuYaHui 
	 * @return
	 * 		集合
	 */
	@Test
	public void findChildListByIdTest(){
		System.out.println(channelInfoDAO.findChildListById(2l));
	}
	
	@Test
	public void findByParentIdOrderByCreateTimeDescTest(){
		System.out.println(channelInfoDAO.findByParentIdOrderByCreateTimeDesc(61l).size());
	}
	
	@Test
	public void findAllParentInfoTest(){
		System.out.println(channelInfoDAO.findAllParentInfo());
	}
	
		
	@Test
	public void findByConditionOrderByNameAndCreateTimeTest(){
		String code="1001";
		String name1="xxx";
		String name2="";
		int page=1;
		int rows=3;
		List list=channelInfoDAO.findByConditionOrderByNameAndCreateTime(code, name1, name2,page,rows);
	}
	
	@Test
	public void findByNameTest(){
		List list=channelInfoDAO.findByName("媒体合作");
		System.out.println(list.size());

		List list1=channelInfoDAO.findByName("媒ss体合作");
		System.out.println(list1.size());
	}
	
	@Test
	public void findTest(){
		Iterable<ChannelInfoVO> channelList=channelInfoDAO.findAll(new Sort(Direction.DESC, "name","createTime"));
 		JSONArray jsonAry=new JSONArray();
		for(ChannelInfoVO vo:channelList){
			try {
				ChannelInfoDTO dto=new ChannelInfoDTO();
				BeanUtils.copyProperties(dto, vo);
				dto.setCreateDate(DateUtil.getYMDTime(vo.getCreateTime()));
				dto.setUpdateDate(DateUtil.getYMDTime(vo.getUpdateTime()));
				JSONObject jsonObj=JSONObject.fromObject(dto);
				jsonAry.add(jsonObj);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		JSONObject json=new JSONObject();
		json.put("total", jsonAry.size());
		json.put("rows", jsonAry);
		System.out.println(json);
		
	}
	
	
	@Test
    @Transactional(readOnly=false)
	public void saveTest(){
		 ChannelInfoVO info=new ChannelInfoVO();
		 Long seq=commonDao.getSequenceByName("CHANNEL_INFO_SEQ");
		 info.setId(seq);
		 info.setCode("1001");
		 info.setName("根渠道");
		 info.setDescription("测试描述");
		 channelInfoDAO.save(info);
		 
    }

}
