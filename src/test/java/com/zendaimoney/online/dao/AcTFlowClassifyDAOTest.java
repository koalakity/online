package com.zendaimoney.online.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.zendaimoney.online.common.TradeTypeConstants;
import com.zendaimoney.online.dao.common.SqlStatement;
import com.zendaimoney.online.entity.AcTFlowClassifyVO;


/**
 * AcTFlowClassifyDAO测试类
 * @author HuYaHui
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={
	"classpath*:/applicationContext.xml"})
public class AcTFlowClassifyDAOTest {
		
    @Autowired
    private AcTFlowClassifyDAO acTFlowClassifyDAO;

	
    @Test
    public void findTypeAndUserIdTest(){
    	List list=acTFlowClassifyDAO.findTypeAndUserId("2009-4-1","2013-4-1",0, 5305150L, 0, 10);
    	System.out.println(list.size()
    			);
    }
    
    @Test
    public void countTypeAndUserIdTest(){
    	long count=acTFlowClassifyDAO.countTypeAndUserId("2009-4-1","2013-4-1",0, 5305150L);
    	System.out.println(count);
    }
    
	
	/**
	 * 根据用户ID，时间,类型,用trade_code分组按照时间倒叙，统计数量
	 * 2013-2-28 上午9:48:12 by HuYaHui
	 * @param userId
	 * 			用户ID
	 * @param start
	 * 			开始时间
	 * @param end
	 * 			结束时间
	 * @param type
	 * 			类型
	 * @param page
	 * 			页数
	 * @param rows
	 * 			每页显示数量
	 * @return
	 */
    @Test
	public void groupbyTradeCodeByDateAndTypeAndUserIdCount(){
    	System.out.println(this.acTFlowClassifyDAO.groupbyTradeCodeByDateAndTypeAndUserIdCount(5309850l, "2013-02-27","2013-02-27",0));
    }
    
    @Test
    public void findTypeAndUserIdAndTradeCodeTest(){
    	List<AcTFlowClassifyVO> list=this.acTFlowClassifyDAO.findTypeAndUserIdAndTradeCode(null,5310000l,false);
    	System.out.println(list.size());
    }
    @Test
    public void groupbyTradeCodeByDateAndTypeAndUserId(){
    	Date dt=new Date(2013-1900,1,27);
    	List<Object[]> actList=acTFlowClassifyDAO.groupbyTradeCodeByDateAndTypeAndUserId(5309850l, "2013-02-27","2013-02-27",0,3, 2);
    	for(Object[] obj:actList){
    		System.out.println(obj[0]);
    	}
    	System.out.println(actList.size());
    }

    @Test
    public void sumByConditionTest(){
    	
    	List<SqlStatement> param=new ArrayList<SqlStatement>();
    	
    	List<String> paramList=new ArrayList<String>();
    	paramList.add(TradeTypeConstants.GJYQCHYQFX);
    	paramList.add(TradeTypeConstants.GJYQCHYQFX);
    	param.add(new SqlStatement("or","tradeType","=",paramList));
    	
    	
    	
    	
    	//借款手续费
    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.FKFEE));
//    	//充值手续费
//    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.CZSXF));
//    	//ID5验证
//    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.ID5));
//    	//提现手续费(成功)
//    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.TXSXF));
//    	//提现手续费(失败)
//    	param.add(new SqlStatement("and","tradeType","=",TradeTypeConstants.TXSXFSB));
//    	//月缴管理费
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.ZCHKCHGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.CJYQCHGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.GJYQCHGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.YCXDQGLF));
//    	//逾期违约金
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.CJYQCHYQGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.GJYQCHYQGLF));
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.DCGJYQCHYQGLF));
//    	//逾期罚息
//    	param.add(new SqlStatement("or","tradeType","=",TradeTypeConstants.GJYQCHYQFX));
//		
    	
    	
    	System.out.println("总收入:"+acTFlowClassifyDAO.sumByConditionIn(param));
    	System.out.println("总支出:"+acTFlowClassifyDAO.sumByConditionOut(param));
    	
    	param=new ArrayList<SqlStatement>();
    	param.add(new SqlStatement("and","tradeType","=","5"));
    	param.add(new SqlStatement("or","tradeType","=","29"));
    	param.add(new SqlStatement("or","tradeType","=","30"));
    	param.add(new SqlStatement("or","tradeType","=","43"));
    	param.add(new SqlStatement("or","tradeType","=","42"));
    	param.add(new SqlStatement("or","tradeType","=","9"));
    	System.out.println("总数据条数："+acTFlowClassifyDAO.countByCondition(param));
    }
    
    @Test
    public void findByCondition(){
    	List<SqlStatement> sqlList=new ArrayList<SqlStatement>();
    	sqlList.add(new SqlStatement("and","tradeType","=","43"));
    	sqlList.add(new SqlStatement("or","tradeType","=","42"));
    	sqlList.add(new SqlStatement("or","tradeType","=","9"));
    	sqlList.add(new SqlStatement("or","tradeType","=","5"));
    	sqlList.add(new SqlStatement("or","tradeType","=","29"));
    	sqlList.add(new SqlStatement("or","tradeType","=","30"));
    	/*
			操作科目：风险金代偿（利息）tradeType：43 出
			操作科目：风险金代偿（本金）tradeType：42出
			操作科目：调账  tradeType：9出
			操作科目：交易手续费 tradeType：5入
			操作科目：偿还本金/回收本金tradeType：29入
			操作科目：偿还利息/回收利息tradeType：30入
    	 */
    	List<AcTFlowClassifyVO> list=acTFlowClassifyDAO.findByCondition(sqlList,1,5);
    	for(AcTFlowClassifyVO vo:list){
    		System.out.println("id:"+vo.getId()+" tradeType:"+vo.getTradeType()+" tradeAmount:"+vo.getTradeAmount()+" tradeCode:"+vo.getTradeCode());
    	}
    	System.out.println(list.size());
    }

	
}
