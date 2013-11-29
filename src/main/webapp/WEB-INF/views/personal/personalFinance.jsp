<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript">
$(function(){
	$("#financeBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $("#baseForm").serialize(),
		    		 url: "${ctx}/personal/personal/savePersonalFinance",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		//alert('error');
		      },
			   	success: function(data){
			   			//alert('success');
			    		$(".account_r_c_4c").html(data);
			    		alert("保存成功.");
			   	}
		});
	});
});
</script>
<form:form id="baseForm" modelAttribute="entity"   method="post">
						<div class="prompt6">
							<p>请填写以下财务状况信息：（注：带*为必填）</p>
						</div>
						<table>
							<tr><td class="td8">每月收入：</td><td><input type="text" name="monthIncome" id="monthIncome" value="${entity.monthIncome}" class="fl money" /><span class="fl">&nbsp;元</span></td></tr>
							<tr><td class="td8">每月还房贷：</td><td><input type="text" name="monthHousingLoan" id="monthHousingLoan" value="${entity.monthHousingLoan}" class="fl money" /><span class="fl">&nbsp;元</span></td></tr>
							<tr><td class="td8">每月还车贷：</td><td><input type="text" name="monthCarLoan" id="monthCarLoan" value="${entity.monthCarLoan}" class="fl money" /><span class="fl">&nbsp;元</span></td></tr>
							<tr><td class="td8">每月信用卡还款金额：</td><td><input type="text" name="monthCreditCard" id="monthCreditCard" value="${entity.monthCreditCard}" class="fl money" /><span class="fl">&nbsp;元</span></td></tr>
							<tr><td class="td8">每月无抵押贷款还款金额：</td><td><input type="text" name="monthMortgage" id="monthMortgage" value="${entity.monthMortgage}" class="fl money" /><span class="fl">&nbsp;元</span></td></tr>
						</table>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
						<input type="button" class="btn3 mar10" id="financeBtn" value="保存" />
</form:form>
