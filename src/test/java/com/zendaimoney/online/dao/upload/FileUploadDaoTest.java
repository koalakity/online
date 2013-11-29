package com.zendaimoney.online.dao.upload;

import java.math.BigDecimal;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.Constants;
import com.zendaimoney.online.common.DateUtil;
import com.zendaimoney.online.entity.borrowing.BorrowingUserApprove;
import com.zendaimoney.online.entity.upload.FileUploadVO;

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
public class FileUploadDaoTest {

    @Autowired
    private FileUploadDao fileUploadDao;
   
	/**
	 * 删除记录
	 * 2012-12-20 上午9:45:58 by HuYaHui
	 * @param userId
	 * 			用户ID
	 * @param type
	 * 			认证类型
	 * @param filePath
	 * 			文件路径
	 * @param isDel
	 * 			是否删除
	 * @param status
	 * 			提交状态
	 * @param uploadDate
	 * 			提交时间
	 * @param remark
	 * 			备注
	 * @return
	 */
    @Test
    @Transactional(readOnly=false)
	public void deleteByCondition(){
		//1删除当天,某用户，类型，已提交，未关联，已删除的记录，和文件
		FileUploadVO whereObj=new FileUploadVO();
		whereObj.setUploadDate(DateUtil.getCurrentDate("yyyy-MM-dd"));
		whereObj.setUserId("5305300");
		whereObj.setType(Constants.FILEUPLOAD_TYPE_SFZ);
		whereObj.setStatus(Constants.FILEUPLOAD_STATUS_YSC);
		whereObj.setIsDel(Constants.FILEUPLOAD_ISDEL_YSC);
		int count=fileUploadDao.deleteByCondition(whereObj);
		System.out.println(count);
    }
    
	/**
	 * 修改记录
	 * 2012-12-20 上午10:05:13 by HuYaHui 
	 * @param _fileUpload
	 * 			更新对象
	 * @param whereObj
	 * 			条件对象
	 * @return
	 * @throws Exception 
	 */
    @Test
    @Transactional(readOnly=false)
	public void update(){
    	//1修改当天，某用户，类型，已提交，未关联记录,未删除，改成已删除
    	FileUploadVO whereObj=new FileUploadVO();
    	whereObj.setId(850l);
		whereObj.setUserId("5305300");
		whereObj.setType("1");
		whereObj.setStatus("1");
		
		FileUploadVO setObj=new FileUploadVO();
		setObj.setIsDel("1");
		
		int count=fileUploadDao.update(setObj, whereObj);
		System.out.println(count);
		
	}
    
    @Test
    public void findRecordByCondition(){
		FileUploadVO whereObj=new FileUploadVO();
		whereObj.setUserId("5305300");
		//whereObj.setId(850);
		List<FileUploadVO> executeCount = fileUploadDao.findRecordByCondition(whereObj);
		System.out.println(executeCount.get(0).getIsDel());
    }
    	
}
