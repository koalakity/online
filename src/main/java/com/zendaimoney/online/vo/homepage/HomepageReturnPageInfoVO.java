package com.zendaimoney.online.vo.homepage;

import java.util.List;

import com.zendaimoney.online.entity.homepage.HomepageUsers;




public class HomepageReturnPageInfoVO extends HomepageUsers{

	private static final long serialVersionUID = 2495116253641109422L;
	/**用户表*/
	//private HomepageUsers  CurrUser;
	/**用户昵称*/
//	private String loginName;
//	/**现所在地 user_info_person*/
	private String liveAddress;
	/**用户注册时间*/
	private String zcdate;
	//客户信息表里的客户编号 AcTCustomer 与 AcTLedger 分账号account 同属于 usersId 查询出  账户余额
//	private String customerNo;
	/**信用等级  user_credit_note 用户信用记录表 userid关联*/
	private String credit_grade;
	/**账户余额 */
	private String accountBalance;
	/**冻结金额*/
	private String freezeAmount;
	/**借款笔数*/
	private String loanItems;
	/**投标笔数*/
	private String tenderItems;
	/**封装投标记录信息 */
//	private List<HomepageInvestInfoVO> tenderVo;
	/** 封装借款记录信息*/
//	private List<HomepageLoanInfo>  loanInfoList;
	/** 借款信息、进度、笔数 */
//	private List retLoanInfoList;
	/** 我的最新动态*/
	private List<HomepageUserMovementVO> usermovInfo;
	/**留言板信息 */ 
//	private List<HomepageLoanInfoTempVO> messageInfo;
	/**个人头像 */
	private String headPath;
	
	
	public String getHeadPath() {
		return headPath;
	}
	public void setHeadPath(String headPath) {
		this.headPath = headPath;
	}
//	public List<HomepageLoanInfoTempVO> getMessageInfo() {
//		return messageInfo;
//	}
//	public void setMessageInfo(List<HomepageLoanInfoTempVO> messageInfo) {
//		this.messageInfo = messageInfo;
//	}
	public List<HomepageUserMovementVO> getUsermovInfo() {
		return usermovInfo;
	}
	public void setUsermovInfo(List<HomepageUserMovementVO> usermovInfo) {
		this.usermovInfo = usermovInfo;
	}
//	public List<HomepageInvestInfoVO> getTenderVo() {
//		return tenderVo;
//	}
//	public void setTenderVo(List<HomepageInvestInfoVO> tenderVo) {
//		this.tenderVo = tenderVo;
//	}
//	public List getRetLoanInfoList() {
//		return retLoanInfoList;
//	}
//	public void setRetLoanInfoList(List retLoanInfoList) {
//		this.retLoanInfoList = retLoanInfoList;
//	}
	
//	public List<HomepageLoanInfo> getLoanInfoList() {
//		return loanInfoList;
//	}
//	public void setLoanInfoList(List<HomepageLoanInfo> loanInfoList) {
//		this.loanInfoList = loanInfoList;
//	}
	/**public HomepageUsers getCurrUser() {
		return CurrUser;
	}
	public void setCurrUser(HomepageUsers currUser) {
		CurrUser = currUser;
	}*/
//	public String getLoginName() {
//		return loginName;
//	}
//	public void setLoginName(String loginName) {
//		this.loginName = loginName;
//	}
	public String getZcdate() {
		return zcdate;
	}
	public void setZcdate(String zcdate) {
		this.zcdate = zcdate;
	}
	public String getCredit_grade() {
		return credit_grade;
	}
	public void setCredit_grade(String creditGrade) {
		credit_grade = creditGrade;
	}
	
	public String getLiveAddress() {
		return liveAddress;
	}
	public void setLiveAddress(String liveAddress) {
		this.liveAddress = liveAddress;
	}
	public String getAccountBalance() {
		return accountBalance;
	}
	public void setAccountBalance(String accountBalance) {
		this.accountBalance = accountBalance;
	}
	
	public String getFreezeAmount() {
		return freezeAmount;
	}
	public void setFreezeAmount(String freezeAmount) {
		this.freezeAmount = freezeAmount;
	}
	public String getLoanItems() {
		return loanItems;
	}
	public void setLoanItems(String loanItems) {
		this.loanItems = loanItems;
	}
	public String getTenderItems() {
		return tenderItems;
	}
	public void setTenderItems(String tenderItems) {
		this.tenderItems = tenderItems;
	}


}
