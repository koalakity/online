package com.zendaimoney.online.dao.pay;

import java.math.BigDecimal;
import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.upload.FileUploadVO;

/**
 * PayDao测试类
 * @author HuYaHui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath*:/applicationContext.xml"})
public class PayDaoTest {

    @Autowired
    private PayDao payDao;

    @Test
    @Transactional(readOnly=true)
    public void update(){
    	FileUploadVO setparam=new FileUploadVO();
    	setparam.setIsDel("1");//测试字符串
    	setparam.setUpdateTime(new Date());//日期类型
    	FileUploadVO whereparam=new FileUploadVO();
    	whereparam.setId(671l);//数字类型
    	BorrowingUserApprove userApproveId=new BorrowingUserApprove();
		userApproveId.setUserApproveId(BigDecimal.valueOf(1));
		whereparam.setUserApproveId(userApproveId);//自定义类型
    	payDao.update(setparam, whereparam);
    }
    
    
    @Transactional(readOnly=true)
    @Test
    public void checkExistsByUserId(){
    	long count=payDao.checkExistsByUserId(new BigDecimal("5305300"));
    	System.out.println(count);
    }
    
    @Test
    public void findByUserIdTest(){
    	Long extractNoteCount=payDao.findByUserId(new BigDecimal(5305300));
    	Assert.assertNotNull(extractNoteCount);
    	System.out.println("extract_note:"+extractNoteCount);

    }
	/**
	 * 根据用户ID查询手机是否绑定
	 * 2013-1-5 下午5:21:02 by HuYaHui 
	 * @param userId
	 * 			用户ID
	 * @return
	 * 			0未绑定，1已绑定
	 */
    @Test
	public void checkUserPhoneByUserId(){
		Object obj=payDao.checkUserPhoneByUserId(BigDecimal.valueOf(5307250));
		System.out.println(obj);
	}
        	
}
