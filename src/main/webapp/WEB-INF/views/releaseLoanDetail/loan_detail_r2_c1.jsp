<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" /> 
<div class="div1">e贷信用等级：<c:if test="${creditInfo.creditGrade==1}"><img src="${ctx}/static/images/img28.gif" /></c:if>
		<c:if test="${creditInfo.creditGrade==2}"><img src="${ctx}/static/images/img27.gif" /></c:if>
		<c:if test="${creditInfo.creditGrade==3}"><img src="${ctx}/static/images/img26.gif" /></c:if>
		<c:if test="${creditInfo.creditGrade==4}"><img src="${ctx}/static/images/img25.gif" /></c:if>
		<c:if test="${creditInfo.creditGrade==5}"><img src="${ctx}/static/images/img24.gif" /></c:if>
		<c:if test="${creditInfo.creditGrade==6}"><img src="${ctx}/static/images/img23.gif" /></c:if>
		
		<c:if test="${creditInfo.creditGrade==7}"><img src="${ctx}/static/images/img22.gif" /></c:if>
		（${creditInfo.creditScoreSum}分）
		信用额度:<fmt:formatNumber value="${creditInfo.creditAmount}" pattern="################.##"/>
	  <!-- （借款后的可用额度:<fmt:formatNumber value="${creditInfo.laonCreditAmount}" pattern="################.##"/>) -->
	</div>
<p class="p1"><strong class="font_14 mar5">基本信息</strong>*以下基本信息资料，经用户同意披露。</p>
<table>
	<tr>
		<td>性别：<c:if test="${loanMessage.isShowSex==1}">${creditInfo.sex}</c:if>
				  <c:if test="${loanMessage.isShowSex==0}">保密</c:if>
		</td>
		<td>年龄：<c:if test="${loanMessage.isShowAge==1}">${creditInfo.age}</c:if>
				  <c:if test="${loanMessage.isShowAge==0}">保密</c:if>
		</td>
		<td>是否结婚：<c:if test="${loanMessage.isShowMarry==1}">${creditInfo.isMarried}</c:if>
					 <c:if test="${loanMessage.isShowMarry==0}">保密</c:if>
		</td>
		<td>工作城市：<c:if test="${loanMessage.isShowWorkCity==1}">${creditInfo.workCity}</c:if>
					 <c:if test="${loanMessage.isShowWorkCity==0}">保密</c:if>
		</td>
	</tr>
	<tr>
		<td>公司行业：<c:if test="${loanMessage.isShowVocation==1}">${creditInfo.companyIndustry}</c:if>
					 <c:if test="${loanMessage.isShowVocation==0}">保密</c:if>
		</td>
		<td>公司规模：<c:if test="${loanMessage.isShowCompanyScale==1}">${creditInfo.companyScale}</c:if>
					 <c:if test="${loanMessage.isShowCompanyScale==0}">保密</c:if>
		</td>
		<td>职位：<c:if test="${loanMessage.isShowOffice==1}">${creditInfo.position}</c:if>
					 <c:if test="${loanMessage.isShowOffice==0}">保密</c:if>
		</td>
		<td>现单位工作时间：<c:if test="${loanMessage.isShowWorkYear==1}">${creditInfo.nowWorkCompanyYear}</c:if>
						    <c:if test="${loanMessage.isShowWorkYear==0}">保密</c:if>
		</td>
	</tr>
	<tr>
		<td>毕业学校：<c:if test="${loanMessage.isShowSchool==1}">${creditInfo.graduationSchool}</c:if>
					 <c:if test="${loanMessage.isShowSchool==0}">保密</c:if>
		</td>
		<td>学历：<c:if test="${loanMessage.isShowDegree==1}">${creditInfo.education}</c:if>
				  <c:if test="${loanMessage.isShowDegree==0}">保密</c:if>
		</td>
		<td>入学年份：<c:if test="${loanMessage.isShowEntranceYear==1}">${creditInfo.entranceYear}</c:if>
				     <c:if test="${loanMessage.isShowEntranceYear==0}">保密</c:if>
		</td>
		<td>有无购房：<c:if test="${loanMessage.isShowHaveHouse==1}">${creditInfo.isBuyHouse}</c:if>
					  <c:if test="${loanMessage.isShowHaveHouse==0}">保密</c:if>
		</td>
	</tr>
	<tr>
		
		<td>有无购车： <c:if test="${loanMessage.isShowHaveCar==1}">${creditInfo.isBuyCar}</c:if>
				      <c:if test="${loanMessage.isShowHaveCar==0}">保密</c:if>
		</td>
		<td>汽车品牌： <c:if test="${loanMessage.isShowHaveCar==1}">${creditInfo.carBrand}</c:if>
					  <c:if test="${loanMessage.isShowHaveCar==0}">保密</c:if>
		</td>
		<td>有无车贷：<c:if test="${loanMessage.isShowCarLoan==1}"> ${creditInfo.isHasCarLoan}</c:if>
					 <c:if test="${loanMessage.isShowCarLoan==0}">保密</c:if>
		</td>
		<td>有无房贷：<c:if test="${loanMessage.isShowHouseLoan==1}">${creditInfo.isHasMortgage}</c:if>
					 <c:if test="${loanMessage.isShowHouseLoan==0}">保密</c:if>
		</td>
		<!-- <td>购车年份：<c:if test="${loanMessage.isShowHaveCar==1&&loanMessage.isShowHaveCar==1}">${creditInfo.buyCarYear}</c:if>
					 <c:if test="${loanMessage.isShowHaveCar==0}">保密</c:if>
		</td> -->
	</tr>
	<tr>
		
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
</table>
<p class="p1">e贷借款记录</p> 
<table>
	<tr><td>发布借款笔数：${creditInfo.releaseLoanNumber}</td><td>成功借款笔数：${creditInfo.successLoanNumber}</td><td>还清笔数：${creditInfo.payOffLoanNumber}</td><td><!--逾期次数：${creditInfo.overdueCount}--></td></tr>
	<tr><td><!--严重逾期次数：${creditInfo.seriousOverdueCount}-->共计借入：${creditInfo.loanTotal}</td><td>待还本息：${creditInfo.dhAmount}</td><td>共计借出：${creditInfo.lendTotalAmount}</td><td>待收本息：${creditInfo.waitRecoverAmount}<!--逾期金额：${creditInfo.overdueAmount}--></td></tr>
	<!--<tr><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td><td>&nbsp;</td></tr>-->
</table>
