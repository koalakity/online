<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<script language="javascript">
	var realAmount=parseFloat($("#real_amount").val()); //当前账户余额
	var rate=parseFloat($("#rate_withdraw").val());//提现率费
	//var maxAmount=parseFloat((realAmount*(1-rate)).toFixed(2));
	$("#real_amount_hmtl").html(formatNum(realAmount)+"元");//设置提现显示td
	function calAmount(obj)
	{
		var fee;
			//var withdrawAmount=parseFloat($("#amount").val());//提现金额
			var withdrawAmount=$.trim($("#amount").val());
			var test=$("#amount").val();
			var maxAmount=50000;
			var v = /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(withdrawAmount);
			$("#amountError").remove();
			if(withdrawAmount==""){
				return;
			}else if(v&&withdrawAmount!=0)
			{
			    withdrawAmount = parseFloat(withdrawAmount);
				if(withdrawAmount>maxAmount)
				{
					withdrawAmount=maxAmount;
					$("#amount").val(withdrawAmount);
				}
				if(withdrawAmount<20000){ 
    				fee=parseFloat(1);
  				}else if(withdrawAmount<50000&&withdrawAmount>=20000){
   					fee=parseFloat(3);
  				}else{
  					fee=parseFloat(5);
  				} 
				if(withdrawAmount>(realAmount-fee)){
					$("#amount").after('<span id="amountError" class="col1">存在非法字符，提取金额只能输入0~9的数字!</span>');return;
				}
				
  				//通过验证
				$("#amount").val(withdrawAmount);
				$("#fee").html(formatNum(fee)+"元");
				$("#pay_amount").html(formatNum(fee+withdrawAmount)+"元");
				$("#after_banlance").html(formatNum(realAmount-(fee+withdrawAmount))+"元");
			}
			else
			{
				$("#amount").after('<span id="amountError" class="col1">存在非法字符，提取金额只能输入0~9的数字!</span>');
			}
			//alert(v);
			//if(withdrawAmount>maxAmount)
			//{
				//$("#amount").val(maxAmount);
				//withdrawAmount=maxAmount;
			//}
	}
</script>
<script type="text/javascript">
$(document).ready(function() {
		$("input=[type=submit]").click(function(){
			if($("#amountError").html()=="存在非法字符，提取金额只能输入0~9的数字!"){
				return false;
			};
		});
		$("#withdrawForm").validate({
		rules:{
		    card_num: {
		         required: true,
				number:true
			},
	    	card_num2:{
		      equalTo:'#card_num',
		      number:true
		    }
		},
		messages: {
			amount: {
				required: '请填写提现金额!'
			},
			bank_name: {
				required: '请填写开户行!'
			},
			card_num: {
				required: '请填写银行卡号!',
				number:'请输入合法的数字'
			},
			customer_name: {
				required: '请填写开户名!'
			},
			card_num2: {
				required: '请再次填写银行卡号!',
				equalTo:'请输入相同的银行卡号',
				number:'请输入合法的数字'
			}		
		}	
	});
	//提现中银行列表显示隐藏切换
	$(".select_bank").click(function(){
		$(".sub_table").slideToggle();
		return false;
	});
	$('input:radio').click(function(){
           //$('input:checkbox').not(this).attr('checked',!$(this).attr('checked'));
           $("#customer_name").val($("#kai"+this.id).text());
           $("#bank_name").val($("#bankName"+this.id).text());
           $("#card_num").val($("#bankNo"+this.id).text());
     });
});
</script>
 

<form:form id="withdrawForm" modelAttribute="payExtractNote" action="${ctx}/pay/pay/withdraw" method="post" >
<input type="hidden" id="rate_withdraw" name="rate_withdraw" value="${payWithdrawVO.rate_withdraw}">
<input type="hidden" id="real_amount" name="real_amount" value="${payWithdrawVO.real_amount}">
						<div class="prompt5">
							<p>1.请输入你要提现的金额，以及正确无误的银行账号信息。</p>
							<p>2.我们将在三个工作日（双休日和节假日除外）之内，将钱转入您指定的银行账号。</p>
							<p>3.在双休日和法定节假日期间，用户可申请取现，但我们暂不处理，需要等恢复正常上班之后才进行处理。不便之处，请多多谅解！</p>
							<p class="col1">4.重要！！如果您填写的开户行支行不正确，提现交易将无法成功，提现费用不予返还。如果您不确定开户行支行名称，可以打电话到当地所在银行的营业网点询问或者上网查询。</p> 
							<p>5.提现手续费：2万以下1元/笔，2万（含）－5万3元/笔，5万5元/笔，单笔提现上限额度为5万元，超过5万元的提现金额分笔提现。</p>
						</div>
						<c:if test="${!isCharged}">
						<table>
							<tr><th colspan="2">请输入您的提现金额：</th></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>

							<tr><td class="td7">可提取金额：</td><td id="real_amount_hmtl"></td></tr>
							<tr><td class="td7">*提取金额：</td><td><input type="text" class="required" id="amount" name="amount" value="${payWithdrawVO.amount}" onkeyup="calAmount(this)" /></td></tr>
							<tr><td class="td7">提现费用：</td><td id="fee">0.00元</td></tr>
							<tr><td class="td7">实付金额：</td><td id="pay_amount">0.00元</td></tr>
							<tr><td class="td7">提现后账户可用余额：</td><td id="after_banlance">0.00元</td></tr>
							 <c:if test="${flag==true}">
							  <tr><td class="td7">* 开户名：</td><td><span>${payWithdrawVO.realName}</span><input type="hidden"  id="customer_name" name="customer_name" value="${payWithdrawVO.realName}" /></td></tr>   
							 </c:if>
							<c:if test="${flag==false}">
							  <tr><td class="td7">* 开户名：</td><td><input type="text" class="required" id="customer_name" name="customer_name" value="${payWithdrawVO.customer_name}"/></td></tr>
							</c:if>
							<tr><td class="td7">*开户行：</td><td><input type="text" class="required" id="bank_name" name="bank_name" value="${payWithdrawVO.bank_name}" />&nbsp;&nbsp;<a href="#" class="select_bank">从银行账户列表中选择</a></td></tr>
							<tr>
								<td colspan="2">
									<table class="sub_table">
										<tr><th width="50">请选择</th><th>银行卡号</th><th>开户名</th><th>开户行</th></tr>
										<c:forEach items="${payWithdrawList}" var="payList" varStatus="status">
										  <tr><td><input type="radio"  name="radio" id="${status.index}"/></td><td id="bankNo${status.index}">${payList.bankCardNo}</td><td id="kai${status.index}">${payList.kaihuName}</td><td id="bankName${status.index}">${payList.bankName}</td></tr>
										</c:forEach>
									</table>
								</td>
							</tr>
							<tr><td>&nbsp;</td><td class="col1">注：请完整填写您的银行卡开户银行名称（如：中国工商银行北京分行海淀支行）。</td></tr>
							<tr><td class="td7">*银行卡号：</td><td><input type="text" class="required" id="card_num" name="card_num" value="${payWithdrawVO.card_num}"/></td></tr>
							<tr><td class="td7">*再次填写银行卡号：</td><td><input type="text" class="required" id="card_num2" name="card_num2"/></td></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						</table>
						
						<!--   <a class="btn4 mar10" href="#" onclick="doSbmit()">申请提现</a>	-->
						<input class="btn4 mar10" type="submit" name="sb" value="申请提现"> 
						</c:if>
						<c:if test="${isCharged}">
						<table>
							<tr><th colspan="2">提现提醒：</th></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>

							<tr><td class="" colspan="2" align="center"><p>您当前已有一笔提交的提现申请正在处理中，请耐心等待处理结果！</p></td></tr>
							 
						</table>
						</c:if>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
</form:form>
 

