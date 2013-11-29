package com.zendaimoney.online.service.register;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.zendaimoney.online.common.CipherUtil;
import com.zendaimoney.online.common.MimeMailService;
import com.zendaimoney.online.dao.common.CommonDao;
import com.zendaimoney.online.dao.register.AcTCustomerDao;
import com.zendaimoney.online.dao.register.UserDao;
import com.zendaimoney.online.dao.register.UserInfoPersonDao;
import com.zendaimoney.online.entity.register.RegisterAcTCustomer;
import com.zendaimoney.online.entity.register.RegisterAcTLedger;
import com.zendaimoney.online.entity.register.RegisterUserInfoPerson;
import com.zendaimoney.online.entity.register.RegisterUsers;

//Spring Bean的标识.
@Component
//默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class RegisterManager {
	private static Logger logger = LoggerFactory.getLogger(RegisterManager.class);
	private HttpSession session;
	private String vcodeStr;
	
	@Autowired
	UserDao userDao;
	
	@Autowired
	UserInfoPersonDao userInfoPersonDao;
	
	@Autowired
	private MimeMailService mimeMailService;
	
	@Autowired
	private AcTCustomerDao acTCustomerDao;
	@Autowired
	private CommonDao commonDao;

	@Transactional(readOnly = false)
	public void save(RegisterUsers users, String serverPath){
		Date date = new Date();
		users.setRegTime(new Timestamp(date.getTime()));
		users.setUserStatus(BigDecimal.ONE);
		int i=(int)Math.round(Math.random()*100000000);
		String email = users.getEmail();
		users.setEmail(users.getEmail());
		users.setEmailacCode(String.valueOf(i));
		users.setIsapproveEmail(BigDecimal.ZERO);
		users.setIsApproveCard(BigDecimal.ZERO);
		users.setIsApproveCard(BigDecimal.ZERO);
		users.setDelStatus(BigDecimal.ZERO);
		/*RegisterUserInfoPerson ruip = new RegisterUserInfoPerson();
		ruip.setUser(users);*/
		RegisterAcTCustomer rac = new RegisterAcTCustomer();
//		String racl = acTCustomerDao.findByCustomerNoMaxVal();
//		BigDecimal customerNo = null;
		/* 2013-1-16 Ray 修复总账号重复，采用获取seq*/
		Long customerNo=commonDao.getSequenceByName("CUSTOMER_NO_SEQ");
		logger.info("获取customer_NO："+customerNo+" email:"+users.getEmail()+" userName:"+users.getLoginName());
		rac.setCustomerNo(customerNo.toString());
//		if(null == racl){
//			customerNo = BigDecimal.valueOf(Long.parseLong("5000001"));
//			rac.setCustomerNo("5000001");
//		}else {
//			customerNo = BigDecimal.valueOf(Long.parseLong(racl)).add(BigDecimal.ONE);
//			rac.setCustomerNo(customerNo.toString());
//		}
		rac.setType("3");
		rac.setName(users.getLoginName());
		rac.setPassword1(CipherUtil.generatePassword(users.getLoginPassword()));
		rac.setPwdDate(date);
		rac.setTotalAcct("3000000000001"+customerNo.toString());
		rac.setAcctStatus("1");
		rac.setOpenacctOrgan("3000000000001");
		rac.setOpenacctDate(date);
		rac.setUser(users);
		//分账信息表
		Set<RegisterAcTLedger> ledger = new HashSet<RegisterAcTLedger>();
		//1:理财分账
		RegisterAcTLedger acTLedger = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号1为 总账号+0001 ******/
	//	int r = (int)(Math.random()*9000+1000);
		String account = rac.getTotalAcct() + "0001";
		acTLedger.setAccount(account);
		acTLedger.setTotalAccountId(rac);
		acTLedger.setOpenacctDate(new Date());
		acTLedger.setBusiType("1");
		//现金
		acTLedger.setAmount(0d);
		//当前投资金额
		acTLedger.setDebtAmount(0d);
		//投资金额
		acTLedger.setInvestAmount(0d);
		//应收利息
		acTLedger.setInterestReceivable(0d);
		//其他应收款
		acTLedger.setOtherReceivale(0d);
		//贷款本金
		acTLedger.setLoanAmount(0d);
		//应付利息
		acTLedger.setInterestPayable(0d);
		//其他应付款
		acTLedger.setToherPayable(0d);
		//利息收入
		acTLedger.setInterestIncome(0d);
		//其他收入
		acTLedger.setOtherIncome(0d);
		//营业外收入
		acTLedger.setNonoperatIncome(0d);
		//利息支出
		acTLedger.setInterestExpenditure(0d);
		//其他支出
		acTLedger.setOtherExpenditure(0d);
		//营业外支出
		acTLedger.setNonoperatExpenditure(0d);
		//待回收金额
		acTLedger.setPayBackAmt(0d);
		//2:借款分账
		RegisterAcTLedger acTLedger2 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号2为 总账号+0002 ******/
		//int r2 = (int)(Math.random()*9000+1000);
		String account2 = rac.getTotalAcct() + "0002";
		acTLedger2.setAccount(account2);
		acTLedger2.setTotalAccountId(rac);
		acTLedger2.setOpenacctDate(new Date());
		acTLedger2.setBusiType("2");
		//现金
		acTLedger2.setAmount(0d);
		//当前投资金额
		acTLedger2.setDebtAmount(0d);
		//投资金额
		acTLedger2.setInvestAmount(0d);
		//应收利息
		acTLedger2.setInterestReceivable(0d);
		//其他应收款
		acTLedger2.setOtherReceivale(0d);
		//贷款本金
		acTLedger2.setLoanAmount(0d);
		//应付利息
		acTLedger2.setInterestPayable(0d);
		//其他应付款
		acTLedger2.setToherPayable(0d);
		//利息收入
		acTLedger2.setInterestIncome(0d);
		//其他收入
		acTLedger2.setOtherIncome(0d);
		//营业外收入
		acTLedger2.setNonoperatIncome(0d);
		//利息支出
		acTLedger2.setInterestExpenditure(0d);
		//其他支出
		acTLedger2.setOtherExpenditure(0d);
		//营业外支出
		acTLedger2.setNonoperatExpenditure(0d);
		//待回收金额
		acTLedger2.setPayBackAmt(0d);
		//4:现金分账
		RegisterAcTLedger acTLedger3 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号4为 总账号+0004 ******/
//		int r3 = (int)(Math.random()*9000+1000);
		String account3 = rac.getTotalAcct() + "0004";
		acTLedger3.setAccount(account3);
		acTLedger3.setTotalAccountId(rac);
		acTLedger3.setOpenacctDate(new Date());
		acTLedger3.setBusiType("4");
		//现金
		acTLedger3.setAmount(0d);
		//当前投资金额
		acTLedger3.setDebtAmount(0d);
		//投资金额
		acTLedger3.setInvestAmount(0d);
		//应收利息
		acTLedger3.setInterestReceivable(0d);
		//其他应收款
		acTLedger3.setOtherReceivale(0d);
		//贷款本金
		acTLedger3.setLoanAmount(0d);
		//应付利息
		acTLedger3.setInterestPayable(0d);
		//其他应付款
		acTLedger3.setToherPayable(0d);
		//利息收入
		acTLedger3.setInterestIncome(0d);
		//其他收入
		acTLedger3.setOtherIncome(0d);
		//营业外收入
		acTLedger3.setNonoperatIncome(0d);
		//利息支出
		acTLedger3.setInterestExpenditure(0d);
		//其他支出
		acTLedger3.setOtherExpenditure(0d);
		//营业外支出
		acTLedger3.setNonoperatExpenditure(0d);
		//待回收金额
		acTLedger3.setPayBackAmt(0d);
		//5:冻结分账
		RegisterAcTLedger acTLedger4 = new RegisterAcTLedger();
		/***** 2013-1-24 修正分账号5为 总账号+0005 ******/
//		int r4 = (int)(Math.random()*9000+1000);
		String account4 = rac.getTotalAcct() + "0005";
		acTLedger4.setAccount(account4);
		acTLedger4.setTotalAccountId(rac);
		acTLedger4.setOpenacctDate(new Date());
		acTLedger4.setBusiType("5");
		//现金
		acTLedger4.setAmount(0d);
		//当前投资金额
		acTLedger4.setDebtAmount(0d);
		//投资金额
		acTLedger4.setInvestAmount(0d);
		//应收利息
		acTLedger4.setInterestReceivable(0d);
		//其他应收款
		acTLedger4.setOtherReceivale(0d);
		//贷款本金
		acTLedger4.setLoanAmount(0d);
		//应付利息
		acTLedger4.setInterestPayable(0d);
		//其他应付款
		acTLedger4.setToherPayable(0d);
		//利息收入
		acTLedger4.setInterestIncome(0d);
		//其他收入
		acTLedger4.setOtherIncome(0d);
		//营业外收入
		acTLedger4.setNonoperatIncome(0d);
		//利息支出
		acTLedger4.setInterestExpenditure(0d);
		//其他支出
		acTLedger4.setOtherExpenditure(0d);
		//营业外支出
		acTLedger4.setNonoperatExpenditure(0d);
		//待回收金额
		acTLedger4.setPayBackAmt(0d);
		//1:理财分账
		ledger.add(acTLedger);
		//2:借款分账
		ledger.add(acTLedger2);
		//4:现金分账
		ledger.add(acTLedger3);
		//5:冻结分账
		ledger.add(acTLedger4);
		rac.setAcTLedger(ledger);
		
		
		users.settCustomerId(rac);
		acTCustomerDao.save(rac);
		logger.info("SAVE:USERS || email="+users.getEmail()+" lName"+users.getLoginName()+" IP="+users.getRegIp()+" time="+users.getRegTime());
		userDao.save(users);
		//userInfoPersonDao.save(ruip);  //新用户注册的时候，无需更改到user_info_person表
		String url = serverPath + "/register/register/activationEmail?username="+users.getLoginName()+"&activationId="+i;
		mimeMailService.sendNotificationMail(url,email,"邮箱激活");
	}
	
	public RegisterUsers findByLoginName(String loginName){
		return userDao.findByLoginName(loginName);
	}
	
	public List<RegisterUsers> findByEmail(String email){
		List<RegisterUsers> user = userDao.findByEmail(email);
		
		return user;
	}
	
	public boolean findByEmailAndLoginName(String email,String loginName){
		RegisterUsers user = userDao.findByLoginNameAndEmail(loginName, email);
		if(user != null){
			return true;
		}else{
			return false;
		}
	}
	
	@Transactional(readOnly = false)
	public RegisterUsers againSendEmail(String loginName, String serverPath ,String email) throws Exception {
		RegisterUsers users = userDao.findByLoginName(loginName);
		int i=(int)Math.round(Math.random()*100000000);
//		String[] email = users.getEmail().split(" ");
		users.setEmail(email);
		users.setEmailacCode(String.valueOf(i));
		userDao.save(users);
		String url = serverPath + "/register/register/activationEmail?username="+users.getLoginName()+"&activationId="+i;
		mimeMailService.sendNotificationMail(url,email.trim(),"邮箱激活");
//		String[] emailArr = users.getEmail().split(" ");
//		users.setEmail(email[0]);
		return users;
	}

	// 邮箱激活
	@Transactional(readOnly = false)
	public String activationEmail(String username, String activationId) {
		RegisterUsers u = userDao.findByLoginName(username);
		if (u == null) {
			//跳转到激活失败,
			return "noUser";
		} else {
//			String aemail = u.getEmail();
//			String[] arrstr = aemail.split(" ");
			if(u.getIsapproveEmail().equals(BigDecimal.ZERO)){
				if ((u.getEmailacCode()).equals(activationId)) {
//					u.setEmail(arrstr[0]);
					u.setIsapproveEmail(BigDecimal.ONE);
					logger.info("SAVE: USERS || IsapproveEmail=1"+" userId="+u.getUserId());
					userDao.save(u);
					//激活成功
					return "registerSucc";
				} else {
					//验证码错误
					return "vCodeWrong";
				}
			}else{
				//已经验证过了
				return "activEd";
			}
		}
	}
	
	
	/**
	 * @author Ray
	 * @date 2012-11-1 上午11:24:56
	 * @param inputVcodeStr
	 * @param req
	 * @param rep
	 * @return
	 * @throws IOException
	 * description:验证码校验
	 */
	public String CheckValidatorImg(String inputVcodeStr,HttpServletRequest req,HttpServletResponse rep) throws IOException{
			//读取session信息
			session = req.getSession();
			//验证码存在时,读取session中的验证码
			if(session.getAttribute("validateCode")!=null){
				vcodeStr = session.getAttribute("validateCode").toString();
			}
			if(vcodeStr.equals(inputVcodeStr.toUpperCase())){
				return "true";
			}else{
				return "false";
			}
		}
	
//	//保存用户身份信息
//	@Transactional(readOnly = false)
//	public void saveUserInfo(RegisterUserInfoPerson userInfoPerson){
//		userInfoPersonDao.save(userInfoPerson);
//		
//	}
}
