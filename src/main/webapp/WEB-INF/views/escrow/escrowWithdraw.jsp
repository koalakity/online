<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript">
	var realAmount=parseFloat($("#real_amount").val()); //当前账户余额
	var rate=parseFloat($("#rate_withdraw").val());//提现率费
	var maxAmount=parseFloat((realAmount*(1-rate)).toFixed(2));
	$("#real_amount_hmtl").html(formatNum(realAmount)+"元");//设置提现显示td
	function calWithdraw()
	{
			var withdrawAmount=parseFloat($("#amount").val());//提现金额
			if(withdrawAmount>maxAmount)
			{
				$("#amount").val(maxAmount);
				withdrawAmount=maxAmount;
			}
				$("#fee").html(formatNum(withdrawAmount*rate)+"元");
				$("#pay_amount").html(formatNum(withdrawAmount*rate+withdrawAmount)+"元");
				$("#after_banlance").html(formatNum(realAmount-(withdrawAmount*rate+withdrawAmount))+"元");
	}
	function doSbmit()
	{
	     var urlStr="${ctx}/escrow/escrow/withdraw";
	     
		 $.ajax({
	        data: $("#withdrawForm").serialize(),
	        type: "POST",
	        url: urlStr,
	        dataType: 'json',
	        error: function(){
	           alert("提现成功！");
	        },
	        success: function(response){
	            alert(response.real_amount);
	            $("#card_num2").val("");
	            $("#card_num").val("");
	            $("#bank_name").val("");
	            $("#customer_name").val("");
	      		$("#real_amount_hmtl").html(response.real_amount+"元");//设置提现显示td
	     		$("#fee").html("0.00元");
				$("#pay_amount").html("0.00元");
				$("#after_banlance").html("0.00元");
	        },
	        beforeSend: function(){
	           //alert("beforeSend");
	        }
    	});
	}
</script>
<form:form id="withdrawForm" modelAttribute="escrowExtractNote" action="${ctx}/escrow/escrow/withdraw" method="post" >
<input type="hidden" id="rate_withdraw" name="rate_withdraw" value="${escrowWithdrawVO.rate_withdraw}">
<input type="hidden" id="real_amount" name="real_amount" value="${escrowWithdrawVO.real_amount}">


						<div class="prompt5">
							<p>1.请输入你要提现的金额，以及正确无误的银行账号信息。</p>
							<p>2.我们将在三个工作日（双休日和节假日除外）之内，将钱转入您指定的银行账号。</p>
							<p>3.在双休日和法定节假日期间，用户可申请取现，但我们暂不处理，需要等恢复正常上班之后才进行处理。不便之处，请多多谅解！</p>
							<p class="col1">4.重要！！如果您填写的开户行支行不正确，提现交易将无法成功，提现费用不予返还。如果您不确定开户行支行名称，可以打电话到当地所在银行得营业网点询问或者上网查询。</p> 
							<p>5.提现手续费：5万以下每笔5元，5万-20万每笔10元，20万-100万每笔15元。</p>
						</div>
						<table>
							<tr><th colspan="2">请输入您的充值金额：</th></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>

							<tr><td class="td7">可提取金额：</td><td id="real_amount_hmtl"></td></tr>
							<tr><td class="td7">*提取金额：</td><td><input type="text" id="amount" name="amount" value="${escrowWithdrawVO.amount}" onkeyup="calWithdraw()" /></td></tr>
							<tr><td class="td7">提现费用：</td><td id="fee">0.00元</td></tr>
							<tr><td class="td7">实付金额：</td><td id="pay_amount">0.00元</td></tr>
							<tr><td class="td7">提现后账户可用余额：</td><td id="after_banlance">0.00元</td></tr>
							<tr><td class="td7">开户名：</td><td><input type="text" id="customer_name" name="customer_name" value="${escrowWithdrawVO.customer_name}"/></td></tr>
							<tr>
								<td colspan="2">
									<table class="sub_table">
										<tr><th>请选择</th><th>账号</th><th>户名</th><th>收款人开户行名</th><th>备注</th></tr>
										<tr><td><input type="checkbox" /></td><td>6222021001081389654</td><td>上海证大</td><td>工商银行</td><td>&nbsp;</td></tr>
										<tr><td><input type="checkbox" /></td><td>6222021001081389654</td><td>上海证大</td><td>工商银行</td><td>&nbsp;</td></tr>
										<tr><td><input type="checkbox" /></td><td>6222021001081389654</td><td>上海证大</td><td>工商银行</td><td>&nbsp;</td></tr>
									</table>
								</td>
							</tr>
							<tr><td class="td7">*支行名称：</td><td><input type="text" id="bank_name" name="bank_name" value="${escrowWithdrawVO.bank_name}" /></td></tr>
							<tr><td class="td7">*银行卡号：</td><td><input type="text" id="card_num" name="card_num" value="${escrowWithdrawVO.card_num}"/></td></tr>
							<tr><td class="td7">*再次填写银行卡号：</td><td><input type="text" id="card_num2"/></td></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						</table>
						<a class="btn4 mar10" href="#" onclick="doSbmit()">申请提现</a>	
</form:form>
