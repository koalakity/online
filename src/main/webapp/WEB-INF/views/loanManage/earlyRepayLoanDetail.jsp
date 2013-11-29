<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" /> 
<style type="text/css">
			.overlay{position:fixed;top:0;right:0;bottom:0;left:0;z-index:998;width:100%;height:100%;_padding:0 20px 0 0;background:#f6f4f5;display:none;}
			
			.showbox{position:fixed;top:0;left:50%;z-index:9999;opacity:0;filter:alpha(opacity=0);margin-left:-80px;}
			#AjaxLoading{border:1px solid #8CBEDA;color:#37a;font-size:12px;font-weight:bold;}
			
			#AjaxLoading div.loadingWord{width:180px;height:50px;line-height:50px;border:2px solid #D6E7F2;background:#fff;}
			
			#AjaxLoading img{margin:10px 15px;float:left;display:inline;}
		</style>
<script type="text/javascript">
function confirmPay(loanId,paymentTotalDouble,currentTermLoan){
	var m = "aheadConfirmPay_"+loanId;
	var radio = document.getElementById(m);
	/* if(!radio.checked){
 		alert("请先选择要等待还款的纪录!");
	 	return ;
	} */
	
	 $.ajax({
	        url: "${ctx}/loanManage/loanManage/getMyAvailableBalance?paymentTotalDouble="+paymentTotalDouble,
	        type: "POST",
	        dataType: 'json',
	        timeout: 100000,
	        error: function(reponse){
	           	return ;
	        },
	        success: function(response){
				if(response.success){
					var b = window.confirm("确定要进行提前还款吗？");
		     		if(b){
		     			$(".overlay").css({'display':'block','opacity':'0.8'});
						$(".showbox").stop(true).animate({'margin-top':'350px','opacity':'1'},200);
		     			payAhead(paymentTotalDouble,loanId,currentTermLoan);
		    			$("#aheadConfirmPay").removeAttr("disabled");
		    		}else{
		    			$("#aheadConfirmPay").removeAttr("disabled");
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

function payAhead(paymentTotalDouble,paymentLoanId,currentTermLoan){
	var payType = "ahead";
   $.ajax({
       url: "${ctx}/loanManage/loanManage/payCurrentLoan?paymentTotalDouble=" + paymentTotalDouble + "&paymentLoanId=" + paymentLoanId + "&currentTermLoan=" + currentTermLoan +"&payType="+payType,
       type: "POST",
       dataType: 'json',
       error: function(response){
       		$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
			$(".overlay").css({'display':'none','opacity':'0'});
           alert("还款失败！");
           return false;
       },
       success: function(response){
    	   $(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
		   $(".overlay").css({'display':'none','opacity':'0'});
           if(response.payResult.length>0){
        	   alert("本期还款已还清，请刷新页面重试！");
               }else{
		       alert("提前还款成功！");
		       window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=repay";
               }
       			
       }
   });
}
</script>
	<div class="account_r_c_3c2">
		<strong class="col2">借款记录</strong>
		<table class="table1 mar">
			<tr> <th>还款日期</th> <th>月还本息</th><th>逾期天数</th><th>逾期罚息</th><th>逾期违约金</th><th>管理费</th><th>状态</th></tr>
			<c:forEach items="${aheadRepayLoanDetail.repayLoanDetailVOList}"  var="aheadReturnDetail" varStatus="status">
			<input type="hidden"  name="aheadWaitPayRadio" id="aheadConfirmPay_${aheadRepayLoanDetail.currentloanId}" />
			<tr><%--<td><c:choose><c:when test="${aheadReturnDetail.repayLoanStatus=='wait'}"> <input type="radio" class="radio" name="aheadWaitPayRadio" id="aheadConfirmPay_${aheadRepayLoanDetail.currentloanId}" checked="checked"/>待还 </c:when><c:otherwise>&nbsp;</c:otherwise></c:choose></td>--%><td>${aheadReturnDetail.repayLoanDate}</td><td>${aheadReturnDetail.principanInterestMonth}</td><td>${aheadReturnDetail.overdueDays}天</td><td>${aheadReturnDetail.overdueInterest}</td><td>${aheadReturnDetail.overdueFines}</td><td>${aheadReturnDetail.managementFeeByMonth}</td><c:choose><c:when test="${aheadReturnDetail.repayLoanStatus=='yes'}"><td>已还款</td></c:when><c:otherwise><td class="col1">未还款</td></c:otherwise></c:choose></tr>
			</c:forEach>
		</table>
			<input type="hidden" name="paymentTotal" value="${aheadRepayLoanDetail.currentPaymentTotalDouble}" id="paymentTotal"/>
			<input type="hidden" name="paymentLoanId" value="${aheadRepayLoanDetail.currentloanId}" id="paymentLoanId"/>
			<input type="hidden" name="paymentTermLoan" value="${aheadRepayLoanDetail.currentTermLoan}" id="currentTermLoan"/>
		<strong class="col2">提前还款详情</strong>
		<p class="prompt7">
			<span>已还本息：${xxoo}</span><span class="col1 tl">${aheadRepayLoanDetail.payOffprincipalInterest}</span><span>本期应还本息：</span><span class="col1 tl">${aheadRepayLoanDetail.currentShouldPayprincipalInterest}</span><span>月缴管理费：</span><span class="col1 tl">${aheadRepayLoanDetail.managementFeeByMonth}</span>
			<span>剩余本金：</span><span class="col1 tl">${aheadRepayLoanDetail.surplusPrincipal}</span><span>提前还款违约金：</span><span class="col1 tl">${aheadRepayLoanDetail.aheadOverdueFines}</span><span>提前还款应还金额：</span><span class="col1 tl">${aheadRepayLoanDetail.aheadShouldRepayAmount}</span>
		</p>
		<p class="tc mar6"><strong>本期共计还款：</strong><span class="col1">${aheadRepayLoanDetail.currentPaymentTotal}</span> <strong>我的余额：</strong><span class="col1">${aheadRepayLoanDetail.myAvailableBalance}</span></p>
		<a href="#" class="btn4 mar0" id="aheadConfirmPay" onclick="confirmPay('${aheadRepayLoanDetail.currentloanId}','${aheadRepayLoanDetail.currentPaymentTotalDouble}','${aheadRepayLoanDetail.currentTermLoan}')">确认还款</a>
	</div>
	<div class="overlay">&nbsp;</div>

		<div class="showbox" id="AjaxLoading">
		
			<div class="loadingWord"><img src="${ctx}/static/images/loading/waiting.gif" alt="" />还款进行中...</div>

		</div>
