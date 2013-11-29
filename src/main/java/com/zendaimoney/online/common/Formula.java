package com.zendaimoney.online.common;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Formula {
	private static final int SCALE_RS = 2;
	private static final int date_repayment_fix_1 = 1;// 每月固定还款日(1号)
	private static final int date_repayment_fix_16 = 16;// 每月固定还款日(16号)

	private Calendar calendar;
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	// System.out.println(format.format(calendar.getTime()));

	private double amount_loan;// 借款金额
	private int term_loan;// 借款期限(总期数)
	private int term_current;// 当前期号
	private Date date_loan;// 借款日期(放款日)
	private Date[] date_repayment;// 还款日(每月1日/16日)
	private Date date_repayment_next;// 下一期还款日
	private Date date_repayment_current;// 当期还款日
	private double rate_year;// 年利率
	private double rate_month;// 月利率
	private double rate_early_repayment;// 一次性提前还款违约费率
	private double day_overdue;// 逾期天数
	private double rate_overdue_interest;// 逾期罚息比例
	private double rate_overdue_fines;// 逾期违约金比例
	private double[] principal_interest_month;// 月还本息
	private double principal_interest_month_first;// 月还本息-第一期
	private double[] principal_month;// 月还本金
	private double[] interest_month;// 月还利息
	private double[] principal_surplus;// 剩余本金
	private double[] principal_interest_balance;// 本息余额
	private double principal_interest_balance_total;// 总本息余额
	private double management_fee_month;// 月缴管理费
	private double rate_management_fee_month;// 月缴管理费比例
	private double[] early_repayment;// 一次性提前还款
	private double advance_payment;// 风险管理金偿付
	private double overdue_interest;// 逾期罚息
	private double overdue_fines;// 逾期违约金

	private int overdue_day;// 逾期天数
	private double overdue_payable; // 逾期后应还金额
	private List rsList;// 计算结果列表(元素为Map)

	/**
	 * 
	 * @param amount_loan
	 *            贷款总额
	 * @param term_loan
	 *            贷款期数
	 * @param rate_year
	 *            年利率
	 * @param rate_management_fee_month
	 *            月管理费费率
	 * @param rate_early_repayment
	 *            一次性提前还款费率
	 * @param rate_overdue_interest
	 *            逾期罚息比例
	 * @param rate_overdue_fines
	 *            逾期违约金比例
	 */
	public Formula(double amount_loan, int term_loan, Date date_loan, double rate_year, double rate_management_fee_month, double rate_early_repayment, double rate_overdue_interest, double rate_overdue_fines) {
		this.amount_loan = amount_loan;
		this.date_loan = date_loan;
		this.term_loan = term_loan;
		this.rate_year = rate_year;
		this.rate_management_fee_month = rate_management_fee_month;
		this.rate_early_repayment = rate_early_repayment;
		this.rate_overdue_interest = rate_overdue_interest;
		this.rate_overdue_fines = rate_overdue_fines;
		this.cal();
	}

	/**
	 * 计算逾期天数
	 * 
	 * @param date
	 */
	public void calOverdue(Date date) {
		if (parseDate(date_repayment_current) >= parseDate(date)) {
			// 没有逾期
			overdue_day = 0;
			overdue_interest = 0;
			overdue_fines = 0;
		} else {
			overdue_day = DateUtil.getDays(date_repayment_current, date);
			// 逾期罚息=剩余未还期数*每期应还本息*天数*比例
			overdue_interest = Calculator.mul((term_loan - term_current + 1), principal_interest_month_first);
			overdue_interest = Calculator.mul(overdue_interest, overdue_day);
			overdue_interest = Calculator.mul(overdue_interest, rate_overdue_interest);
			overdue_interest = Calculator.round(overdue_interest, SCALE_RS);
			// 逾期违约金=剩余未还期数*每期管理费*天数*比例
			overdue_fines = Calculator.mul((term_loan - term_current + 1), management_fee_month);
			overdue_fines = Calculator.mul(overdue_fines, overdue_day);
			overdue_fines = Calculator.mul(overdue_fines, rate_overdue_fines);
			overdue_fines = Calculator.round(overdue_fines, SCALE_RS);
		}
	}

	/**
	 * 
	 * @param overdue_day
	 *            逾期天数
	 * @param term
	 *            剩余未还期数
	 * @param principal_interest_month
	 *            每期应还本息
	 * @param rate
	 *            罚息比例
	 * @return
	 */
	public static double calOverdueInterest(int overdue_day, int term, double principal_interest_month, double rate) {
		// 逾期罚息=剩余未还期数*每期应还本息*天数*比例
		double overdue_interest = 0;
		overdue_interest = Calculator.mul(term, principal_interest_month);
		overdue_interest = Calculator.mul(overdue_interest, overdue_day);
		overdue_interest = Calculator.mul(overdue_interest, rate);
		overdue_interest = Calculator.round(overdue_interest, 2);
		return overdue_interest;
	}

	/**
	 * 计算当前期、上期期号、下期期号，当前还款日、下期还款日、上期还款日。
	 */
	public void calTermAndDateRepayment() {
		if (date_repayment == null || date_repayment.length == 0) {
			throw new RuntimeException("还款日期列表未初始化！");
		}
		calendar = Calendar.getInstance();// 当前时间
		// 当前时间超出借款期限！
		if (parseDate(calendar.getTime()) > parseDate(date_repayment[date_repayment.length - 1])) {
			throw new RuntimeException("当前时间超出借款期限！");
		}

		// double
		// date_value_now=Double.parseDouble(dateFormat.format(calendar.getTime()));
		// double
		// date_value_now=Double.parseDouble(dateFormat.format(calendar.getTime()));

		if (parseDate(calendar.getTime()) <= parseDate(date_repayment[0]) && (parseDate(calendar.getTime()) >= parseDate(date_loan))) {
			// 当前日期位于第一期还款日之前&&放贷之后
			term_current = 1;// 当期期号
			date_repayment_current = date_repayment[0];// 当期还款日
			if (term_loan > 1) {
				date_repayment_next = date_repayment[1];// 下期还款日
			}
		} else {
			// 判断当前时间位于贷款列表的哪个区间
			for (int i = 0; i < date_repayment.length - 1; i++) {
				if (parseDate(calendar.getTime()) >= parseDate(date_repayment[i]) && parseDate(calendar.getTime()) <= parseDate(date_repayment[i + 1])) {
					term_current = i + 1;// 当期期号
					date_repayment_current = date_repayment[i + 1];
					if (i == date_repayment.length - 2) {
						date_repayment_next = null;
					} else {
						date_repayment_next = date_repayment[i + 2];
					}
					break;
				}
			}
		}
	}

	/**
	 * 计算还款日期列表
	 * 
	 * @param term_loan
	 */
	public void calDateRepaymentArray() {
		// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		// try {
		// date_loan = format.parse("2012-07-29");
		// } catch (ParseException e) {
		// e.printStackTrace();
		// }
		// System.out.println(calendar.get(Calendar.MONTH));
		if (date_loan != null) {
			date_repayment = new Date[term_loan];
			calendar = Calendar.getInstance();
			calendar.setTime(date_loan);
			int day = calendar.get(Calendar.DAY_OF_MONTH);// 算出几号
			// int month=calendar.get(Calendar.MONTH);
			// int year=calendar.get(Calendar.YEAR);
			calendar.add(Calendar.MONTH, 1);// 下月
			if (day >= 1 && day <= 15) {
				// 1日-15日,下月1号还款
				calendar.set(Calendar.DAY_OF_MONTH, date_repayment_fix_1);
			} else {
				// 16日-31日,下月16号还款
				calendar.set(Calendar.DAY_OF_MONTH, date_repayment_fix_16);
			}
			for (int i = 0; i < date_repayment.length; i++) {
				// dateFormat=new SimpleDateFormat("yyyy-MM-dd");
				date_repayment[i] = calendar.getTime();
				calendar.add(Calendar.MONTH, 1);// 下月
			}
		}
	}

	private void cal() {
		this.calRateMonth();// 计算月利率
		this.calManagementFeeMonth();// 计算月缴管理费
		this.calDateRepaymentArray();// 计算还款日期列表
		this.calTermAndDateRepayment();// 计算当前期、下期期号，当前还款日、下期还款日、上期还款日。

		// ========================初始化==========================
		principal_interest_month = new double[term_loan];// 月还本息
		principal_month = new double[term_loan];// 月还本金
		interest_month = new double[term_loan];// 月还利息
		principal_surplus = new double[term_loan];// 剩余本金
		principal_interest_balance = new double[term_loan];// 本息余额
		early_repayment = new double[term_loan];// 每次提前还款对应金额
		// ========================================================
		principal_interest_month_first = calAverageCapitalPlusInterest();// 计算第一期本息余额
		for (int i = 0; i < term_loan; i++) {
			if (i == 0) {
				principal_interest_month[i] = Calculator.forScale(principal_interest_month_first, SCALE_RS);// 月还本息
				interest_month[i] = Calculator.mul(amount_loan, rate_month);// 月还利息
				principal_month[i] = Calculator.sub(principal_interest_month[i], interest_month[i]);// 月还本金
				principal_surplus[i] = Calculator.sub(amount_loan, principal_month[i]);// 剩余本金
			} else if (i == term_loan - 1) {
				// 最后一期
				principal_interest_month[i] = Calculator.forScale(principal_interest_month_first, SCALE_RS);// 月还本息
				interest_month[i] = Calculator.mul(principal_surplus[i - 1], rate_month);// 月还利息
				principal_month[i] = principal_surplus[i - 1];// 月还本金
				principal_surplus[i] = Calculator.sub(principal_surplus[i - 1], principal_month[i]);// 剩余本金
			} else {
				principal_interest_month[i] = Calculator.forScale(principal_interest_month_first, SCALE_RS);// 月还本息
				interest_month[i] = Calculator.mul(principal_surplus[i - 1], rate_month);// 月还利息
				principal_month[i] = Calculator.sub(principal_interest_month[i], interest_month[i]);// 月还本金
				principal_surplus[i] = Calculator.sub(principal_surplus[i - 1], principal_month[i]);// 剩余本金
			}
			// =================4舍5入=================
			// principal_interest_month[i]=Calculator.forScale(principal_interest_month[i],SCALE_RS);
			// interest_month[i]=Calculator.forScale(interest_month[i],SCALE_RS);
			// principal_month[i]=Calculator.forScale(principal_month[i],SCALE_RS);
			// principal_surplus[i]=Calculator.forScale(principal_surplus[i],SCALE_RS);
			// principal_interest_balance[i]=Calculator.forScale(principal_interest_balance[i],SCALE_RS);
			// ========================================
			// System.out.println("期数："+(i+1)
			// +",月还本息："+principal_interest_month[i]
			// +",月还利息："+interest_month[i]
			// +",月还本金："+principal_month[i]
			// +",剩余本金："+principal_surplus[i]);
			// principal_interest_balance_total=Calculator.add(principal_interest_balance_total,
			// principal_interest_month[i]);
		}

		// ========================================
		// =================4舍5入=================
		principal_interest_month = Calculator.forScale(principal_interest_month, SCALE_RS);
		interest_month = Calculator.forScale(interest_month, SCALE_RS);
		principal_month = Calculator.forScale(principal_month, SCALE_RS);
		principal_surplus = Calculator.forScale(principal_surplus, SCALE_RS);
		// 处理最后一期月还本息
		principal_interest_month[term_loan - 1] = Calculator.forScale(Calculator.add(interest_month[term_loan - 1], principal_month[term_loan - 1]), SCALE_RS);// 月还本息
		// 计算本息余额
		for (int j = 0; j < term_loan; j++) {
			principal_interest_balance_total = principal_interest_balance_total + principal_interest_month[j];
		}
		principal_interest_balance_total = Calculator.forScale(principal_interest_balance_total, SCALE_RS);

		// 计算本息余额(返推);计算提前还款
		for (int i = 0; i < term_loan; i++) {
			if (i == 0) {
				principal_interest_balance[i] = Calculator.sub(principal_interest_balance_total, principal_interest_month[i]);
			} else {
				principal_interest_balance[i] = Calculator.sub(principal_interest_balance[i - 1], principal_interest_month[i]);
			}
			// 4舍5入
			principal_interest_balance[i] = Calculator.forScale(principal_interest_balance[i], SCALE_RS);

			// 当期提前还款额=剩余本金+月还本息+月管理费+剩余本金*违约费率
			early_repayment[i] = Calculator.add(principal_surplus[i], principal_interest_month[i]);
			early_repayment[i] = Calculator.add(early_repayment[i], management_fee_month);
			early_repayment[i] = Calculator.add(early_repayment[i], Calculator.mul(principal_surplus[i], this.rate_early_repayment));
			// 计算罚息
			// System.out.println("期数：" + (i + 1) + ",月还本息：" +
			// principal_interest_month[i] + ",月还利息：" + interest_month[i] +
			// ",月还本金：" + principal_month[i] + ",剩余本金：" + principal_surplus[i] +
			// ",本息余额：" + principal_interest_balance[i] + ",管理费:" +
			// management_fee_month + ",一次性提前还款：" + early_repayment[i]);
		}
		// System.out.println("principal_interest_balance_total:" +
		// principal_interest_balance_total + "");
		this.creRsList();// 创建计算结果列表
	}

	/**
	 * 创建结果列表
	 */
	private void creRsList() {
		rsList = new ArrayList();
		Map map;
		for (int i = 0; i < term_loan; i++) {
			map = new HashMap();
			map.put("date_repayment", date_repayment[i]);// 还款日期
			map.put("yhbx", new Double(principal_interest_month[i]));// 月还本息
			map.put("yhbj", new Double(principal_month[i]));// 月还本金
			map.put("yhlx", new Double(interest_month[i]));// 月还利息
			map.put("sybj", new Double(principal_surplus[i]));// 剩余本金
			map.put("bxye", new Double(principal_interest_balance[i]));// 本息余额
			map.put("yjglf", new Double(management_fee_month));// 月缴管理费
			map.put("ycxtqhk", new Double(early_repayment[i]));// 一次性提前还款
			rsList.add(map);
		}
	}

	/**
	 * 计算月利率
	 */
	private void calRateMonth() {
		rate_month = Calculator.div(rate_year, 12);
	}

	/**
	 * 等额本息法：每月还本息= [贷款本金×月利率×（1+月利率）^还款月数]÷[（1+月利率）^还款月数－1]
	 */
	private double calAverageCapitalPlusInterest() {
		double tmp_1 = Math.pow(Calculator.add(1, rate_month), term_loan);// （1+月利率）^还款月数
		double tmp_2 = Calculator.mul(amount_loan, rate_month);// 贷款本金×月利率
		double tmp_3 = Calculator.mul(tmp_1, tmp_2);// [贷款本金×月利率×（1+月利率）^还款月数]
		double tmp_4 = Calculator.sub(Math.pow(ArithUtil.add(1, rate_month), term_loan), 1);// （1+月利率）^还款月数－1
		return Calculator.div(tmp_3, tmp_4);
		// return (amount_loan*rate_month*Math.pow(1+rate_month, term_loan))
		// /(Math.pow((1+rate_month),term_loan)-1);
	}

	/**
	 * 计算月缴管理费
	 */
	private void calManagementFeeMonth() {
		management_fee_month = Calculator.mul(amount_loan, rate_management_fee_month);
		management_fee_month = Calculator.forScale(management_fee_month, SCALE_RS);
	}

	/**
	 * 将date解析为double类型(格式：yyyymmdd)
	 * 
	 * @param date
	 * @return
	 */
	private int parseDate(Date date) {
		if (date == null) {
			throw new RuntimeException("被解析的日期不能为空！");
		}
		dateFormat = new SimpleDateFormat("yyyyMMdd");
		// System.out.println(dateFormat.format(date));
		return Integer.parseInt(dateFormat.format(date));
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) {
		// test t=new test(10000,12,0.12,0.005,0.01);
		// System.out.println(t.calAverageCapitalPlusInterest());
		// System.out.println(forScale(t.calAverageCapitalPlusInterest(),2));
		// java.util.Date date=new Date();

		// dtBeg.getDate();
		// SimpleDateFormat format;
		// Calendar c1;
		// Calendar c2=Calendar.getInstance();
		// c1.set(2012, 07,30);
		// c2.set(2012, 07,30);
		// System.out.println(c1.equals(c2));

		// for(int i=0;i<10;i++){
		// format=new SimpleDateFormat("yyyy-mm-dd");
		// c1=Calendar.getInstance();
		// System.out.println(format.format(c1.getTime()));
		// }

		// Date d=new Date();
		// Date d1=Calendar.getInstance().getT(2012, 07, 30);
		Formula f = new Formula(10000, 12, Calendar.getInstance().getTime(), 0.12, 0.005, 0.01, 0.0005, 0.005);
		Calendar c1 = Calendar.getInstance();
		c1.setTime(f.getDate_repayment_current());

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println(format.format(c1.getTime()));

		c1.add(Calendar.DAY_OF_MONTH, 0);
		System.out.println(format.format(c1.getTime()));

		f.calOverdue(c1.getTime());
		System.out.println("逾期天数:" + f.getOverdue_day());
		System.out.println("逾期罚息:" + f.getOverdue_interest());
		System.out.println("逾期罚款:" + f.getOverdue_fines());
		// double d=_parseDate(Calendar.getInstance().getTime());
		// System.out.println(d);
	}

	public int getTerm_current() {
		return term_current;
	}

	public void setTerm_current(int term_current) {
		this.term_current = term_current;
	}

	public Date[] getDate_repayment() {
		return date_repayment;
	}

	public void setDate_repayment(Date[] date_repayment) {
		this.date_repayment = date_repayment;
	}

	public Date getDate_repayment_next() {
		return date_repayment_next;
	}

	public void setDate_repayment_next(Date date_repayment_next) {
		this.date_repayment_next = date_repayment_next;
	}

	public Date getDate_repayment_current() {
		return date_repayment_current;
	}

	public void setDate_repayment_current(Date date_repayment_current) {
		this.date_repayment_current = date_repayment_current;
	}

	public double getRate_month() {
		return rate_month;
	}

	public void setRate_month(double rate_month) {
		this.rate_month = rate_month;
	}

	public double[] getPrincipal_interest_month() {
		return principal_interest_month;
	}

	public void setPrincipal_interest_month(double[] principal_interest_month) {
		this.principal_interest_month = principal_interest_month;
	}

	public double[] getPrincipal_month() {
		return principal_month;
	}

	public void setPrincipal_month(double[] principal_month) {
		this.principal_month = principal_month;
	}

	public double[] getInterest_month() {
		return interest_month;
	}

	public void setInterest_month(double[] interest_month) {
		this.interest_month = interest_month;
	}

	public double[] getPrincipal_surplus() {
		return principal_surplus;
	}

	public void setPrincipal_surplus(double[] principal_surplus) {
		this.principal_surplus = principal_surplus;
	}

	public double[] getPrincipal_interest_balance() {
		return principal_interest_balance;
	}

	public void setPrincipal_interest_balance(double[] principal_interest_balance) {
		this.principal_interest_balance = principal_interest_balance;
	}

	public List getRsList() {
		return rsList;
	}

	public void setRsList(List rsList) {
		this.rsList = rsList;
	}

	public Calendar getCalendar() {
		return calendar;
	}

	public void setCalendar(Calendar calendar) {
		this.calendar = calendar;
	}

	public SimpleDateFormat getDateFormat() {
		return dateFormat;
	}

	public void setDateFormat(SimpleDateFormat dateFormat) {
		this.dateFormat = dateFormat;
	}

	public double getAmount_loan() {
		return amount_loan;
	}

	public void setAmount_loan(double amountLoan) {
		amount_loan = amountLoan;
	}

	public int getTerm_loan() {
		return term_loan;
	}

	public void setTerm_loan(int termLoan) {
		term_loan = termLoan;
	}

	public Date getDate_loan() {
		return date_loan;
	}

	public void setDate_loan(Date dateLoan) {
		date_loan = dateLoan;
	}

	public double getRate_year() {
		return rate_year;
	}

	public void setRate_year(double rateYear) {
		rate_year = rateYear;
	}

	public double getRate_early_repayment() {
		return rate_early_repayment;
	}

	public void setRate_early_repayment(double rateEarlyRepayment) {
		rate_early_repayment = rateEarlyRepayment;
	}

	public double getDay_overdue() {
		return day_overdue;
	}

	public void setDay_overdue(double dayOverdue) {
		day_overdue = dayOverdue;
	}

	public double getRate_overdue_interest() {
		return rate_overdue_interest;
	}

	public void setRate_overdue_interest(double rateOverdueInterest) {
		rate_overdue_interest = rateOverdueInterest;
	}

	public double getRate_overdue_fines() {
		return rate_overdue_fines;
	}

	public void setRate_overdue_fines(double rateOverdueFines) {
		rate_overdue_fines = rateOverdueFines;
	}

	public double getPrincipal_interest_month_first() {
		return principal_interest_month_first;
	}

	public void setPrincipal_interest_month_first(double principalInterestMonthFirst) {
		principal_interest_month_first = principalInterestMonthFirst;
	}

	public double getPrincipal_interest_balance_total() {
		return principal_interest_balance_total;
	}

	public void setPrincipal_interest_balance_total(double principalInterestBalanceTotal) {
		principal_interest_balance_total = principalInterestBalanceTotal;
	}

	public double getManagement_fee_month() {
		return management_fee_month;
	}

	public void setManagement_fee_month(double managementFeeMonth) {
		management_fee_month = managementFeeMonth;
	}

	public double getRate_management_fee_month() {
		return rate_management_fee_month;
	}

	public void setRate_management_fee_month(double rateManagementFeeMonth) {
		rate_management_fee_month = rateManagementFeeMonth;
	}

	public double[] getEarly_repayment() {
		return early_repayment;
	}

	public void setEarly_repayment(double[] earlyRepayment) {
		early_repayment = earlyRepayment;
	}

	public double getAdvance_payment() {
		return advance_payment;
	}

	public void setAdvance_payment(double advancePayment) {
		advance_payment = advancePayment;
	}

	public double getOverdue_payable() {
		return overdue_payable;
	}

	public void setOverdue_payable(double overduePayable) {
		overdue_payable = overduePayable;
	}

	public double getOverdue_fines() {
		return overdue_fines;
	}

	public void setOverdue_fines(double overdue_fines) {
		this.overdue_fines = overdue_fines;
	}

	public double getOverdue_interest() {
		return overdue_interest;
	}

	public void setOverdue_interest(double overdue_interest) {
		this.overdue_interest = overdue_interest;
	}

	public int getOverdue_day() {
		return overdue_day;
	}

	public void setOverdue_day(int overdue_day) {
		this.overdue_day = overdue_day;
	}
}
