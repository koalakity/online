<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<style type="text/css">
			.overlay{position:fixed;top:0;right:0;bottom:0;left:0;z-index:998;width:100%;height:100%;_padding:0 20px 0 0;background:#f6f4f5;display:none;}
			
			.showbox{position:fixed;top:0;left:50%;z-index:9999;opacity:0;filter:alpha(opacity=0);margin-left:-80px;}
			#AjaxLoading{border:1px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;}
			
			#AjaxLoading div.loadingWord{width:180px;height:50px;line-height:50px;border:2px solid #D6E7F2;background:#fff;}
			
			#AjaxLoading img{margin:10px 15px;float:left;display:inline;}
		</style>
<script type="text/javascript">

function confirmPay(loanId,count,paymentTotalDouble,currentTermLoan){
	var m = "waitPayRadio_"+loanId;
	var radio = document.getElementById(m);
	if(!radio.checked){
 		alert("请先选择要等待还款的纪录!");
	 	return false;
	}
	 $.ajax({
	        url: "${ctx}/loanManage/loanManage/getMyAvailableBalance?paymentTotalDouble="+paymentTotalDouble,
	        type: "POST",
	        dataType: 'json',
	        timeout: 100000,
	        error: function(reponse){
	            alert("error");
	           	return ;
	        },
	        success: function(response){
				if(response.success){
					var b = window.confirm("确定要进行本期还款吗？");
		     		if(b){
		     			$(".overlay").css({'display':'block','opacity':'0.8'});
						$(".showbox").stop(true).animate({'margin-top':'350px','opacity':'1'},200);
		    			pay(count,paymentTotalDouble,loanId,currentTermLoan);
						$("#confirmPay").removeAttr("disabled");
					}else{
						$("#confirmPay").removeAttr("disabled");
			           	return ;
		     		}
				}else{
					var tflg = window.confirm("您的可用余额不足，请先充值");
					if(tflg){
					//定向到充值页面
					window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay"	
					}else{
					$("#confirmPay").removeAttr("disabled");
					}
				}
	        }
	 })
}

function pay(count,paymentTotalDouble,paymentLoanId,currentTermLoan){
	var payType = "Normal";
   $.ajax({
       url: "${ctx}/loanManage/loanManage/payCurrentLoan?paymentTotalDouble=" + paymentTotalDouble + "&paymentLoanId=" + paymentLoanId + "&currentTermLoan=" + currentTermLoan +"&payType="+payType,
       type: "POST",
       dataType: 'json',
       error: function(response){
       		$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
			$(".overlay").css({'display':'none','opacity':'0'});
       },
       success: function(response){
    	   $(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
		   $(".overlay").css({'display':'none','opacity':'0'});
    	   if(response.payResult.length>0){
    		   alert("本期还款已还清，请刷新页面重试！");
               }else{
   		       		alert("本期已成功还款！");
   		       		window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=repay";
   		       		/*if(count==currentTermLoan){
   		    	   		window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=repay";
   			   		}else{
   			          $(".account_r_c_3c1").load("${ctx}/loanManage/loanManage/getRepayLoanDetail?loanId=" + paymentLoanId);
   			  	 	}*/
                   }
       			
       }
   });
}
</script>
		<div class="account_r_c_3c1">
			<strong class="col2">还款记录</strong>
			<table class="table1 mar" id="payLoanTab">
				<tr><th>选择</th><th>计划还款日期</th><th>实际还款日期</th><th>月还本息</th><th>逾期天数</th><th>逾期罚息</th><th>逾期违约金</th><th>管理费</th><th>状态</th></tr>
			<c:set var="count" value="1" />
			<c:forEach items="${repayLoanDetail.repayLoanDetailList}"  var="returnDetail" varStatus="status">
				<tr><td>
				   <c:choose>
				      <c:when test="${returnDetail.repayLoanStatus=='wait'}">
				        <input type="radio" class="radio" name="waitPayRadio" id="waitPayRadio_${repayLoanDetail.currentloanId}"/>待还</c:when>
				  <c:otherwise>&nbsp;</c:otherwise></c:choose></td>
				<td>${returnDetail.repayPlanDate}</td><td>${returnDetail.repayActDate}</td><td>${returnDetail.principanInterestMonth}</td>
				<td>${returnDetail.overdueDays}</td><td>${returnDetail.overdueInterest}</td>
				<td>${returnDetail.overdueFines }</td><td>${returnDetail.managementFeeByMonth}</td>
				<c:choose><c:when test="${returnDetail.repayLoanStatus=='yes'}"><td>已还款</td></c:when><c:otherwise>
				<td class="col1">未还款</td></c:otherwise></c:choose></tr>
			</c:forEach>
			</table>
			<input type="hidden" name="paymentTotal" value="${repayLoanDetail.currentPaymentTotalDouble}" id="paymentTotal"/>
			<input type="hidden" name="paymentLoanId" value="${repayLoanDetail.currentloanId}" id="paymentLoanId"/>
			<input type="hidden" name="paymentTermLoan" value="${repayLoanDetail.currentTermLoan}" id="currentTermLoan"/>
			<p class="tc mar6"><strong>本期共计还款：</strong><span class="col1">${repayLoanDetail.currentPaymentTotal}</span> <strong>我的余额：</strong><span class="col1">${repayLoanDetail.myAvailableBalance}</span></p>
			<a href="#" class="btn4 mar0" id="confirmPay" onclick="confirmPay('${repayLoanDetail.currentloanId}','${repayLoanDetail.currentTermLoanTotal}','${repayLoanDetail.currentPaymentTotalDouble}','${repayLoanDetail.currentTermLoan}')">确认还款</a>
		</div>
		<div class="overlay">&nbsp;</div>

		<div class="showbox" id="AjaxLoading">
		
			<div class="loadingWord"><img src="${ctx}/static/images/loading/waiting.gif" alt="" />还款进行中...</div>

		</div>
