<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script language="javascript" type="text/javascript">
$(function(){
	$('#financeBtn').unbind('click');
	$("#financeBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".personalFinanceForm").serialize(),
		    		 url: "${ctx}/admin/user/savePersonalFinance",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		//alert('error');
		      },
			   	success: function(data){
			   			alert("保存成功.");
			   			return;
			   	}
		});
		
	});
});

</script>

<form:form class="personalFinanceForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;">
	<table>
		<tr>
			<td align="right"  width="190" >每月收入:</td>
			<td align="left">
				<input id="monthIncome" name="userInfoPerson.monthIncome"	type="text" value="${userbasic.userInfoPerson.monthIncome}"	onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>每月还房贷:</td>
			<td align="left">
				<input id="monthHousingLoan"	name="userInfoPerson.monthHousingLoan" type="text"	value="${userbasic.userInfoPerson.monthHousingLoan}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>每月还车贷:</td>
			<td align="left">
				<input id=monthCarLoan name="userInfoPerson.monthCarLoan"	type="text" value="${userbasic.userInfoPerson.monthCarLoan}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>每月信用卡还款金额:</td>
			<td align="left">
				<input id="monthCreditCard" name="userInfoPerson.monthCreditCard" type="text"	value="${userbasic.userInfoPerson.monthCreditCard}"	onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>每月无抵押贷款还款金额:</td>
			<td align="left">
				<input id="monthMortgage" name="userInfoPerson.monthMortgage"	type="text" value="${userbasic.userInfoPerson.monthMortgage}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>

	</table>
	<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" disabled="disabled" />
	<input id="infoId" name="userInfoPerson.infoId" type="hidden" value="${userbasic.userInfoPerson.infoId}" disabled="disabled" />
</form:form>
