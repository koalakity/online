package com.zendaimoney.online.dao.common;

import java.math.BigDecimal;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


/**
 * PayDao测试类
 * @author HuYaHui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath*:/applicationContext.xml",
	"classpath*:/applicationContext-security.xml",
	"classpath*:/applicationContext-shiro.xml",
	"classpath*:/applicationContext-task.xml"})
public class CommonCustomDaoTest {
    
	@Autowired
	CommonDao commonDao;
	
	@Test
    public void getSequenceByNameTest(){
		Long seqVal=commonDao.getSequenceByName("FILE_UPLOAD_SEQ");
    	Assert.assertNotNull(seqVal);
    	System.out.println("extract_note:"+seqVal);

    }    
    
}
