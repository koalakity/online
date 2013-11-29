<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery-validation/1.9.0/jquery.validate.min.js" type="text/javascript"></script>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
Number.prototype.toFixed = function(s)
{
    return (parseInt(this * Math.pow( 10, s ) + 0.5)/ Math.pow( 10, s )).toString();
}
</script>
<script  type="text/javascript">
//充值计算
function calRecharge(free){
	var s = $("#amount").val();
    var amount = 0;
    if (s != "" && s != null) {
        amount = parseFloat(s);
        if(amount>1000000)
        {
        	amount=1000000;
			$("#amount").val(1000000);
        	//alert(amount);
        	//$("#amount").html("1000000");
        }
    }
	var rechargeRate=$("#rechargeRate").val() ;
	if(rechargeRate)rechargeRate= rechargeRate.toString().replace(',',''); 
	var currentBanlance=$("#_currentBanlance").val(); 
	if(currentBanlance)currentBanlance=currentBanlance.toString().replace(',','');
	var fee;
	var rate=$("#rate").val();
	if(rate)rate=rate.toString().replace(',','');
    var v = parseFloat(amount) > 1 && parseFloat(amount) <= 1000000 &&
    /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(parseFloat(amount));
    /**if(!v){
        $("#forAmount").show();
    }else{
        $("#forAmount").hide();
    }  */ 
	//alert(rechargeRate);
    //$("#fee").html(formatNum(parseFloat(amount)*parseFloat(rechargeRate))+"元");//手续费
    /**if(amount<20000){
    	fee=parseFloat(1);
    }
    if(amount<50000&&amount>=20000){
    	fee=parseFloat(3);
    }
    if(amount>=50000&&amount<=1000000){
    	fee=parseFloat(5);
    }    */
    
if(!v){
	alert("输入金额不正确！请重新输入！");$("#amount").val("");
	return;
}
	if (s != "" && s != null&&amount>0) {
		fee=amount*rate; 
		if(fee)fee=fee.toString().replace(',','');  
       $("#afterBanlance").html(formatNum(parseFloat(amount)-parseFloat(fee)+parseFloat(currentBanlance))+"元");
       $("#fee").html(formatNum(parseFloat(fee))+"元");
	   $("#realAmount").html(formatNum(parseFloat(amount)-parseFloat(fee))+"元");
    }else{
       $("#fee").html("0.00元");
       $("#afterBanlance").html("0.00元");
    }
}
// 格式化数字，转化成带千分符，并且四舍五入保留两位小数
function formatNum(num)
{ 
    num=String(num.toFixed(2));
    var re=/(\d+)(\d{3})/;
    while(re.test(num)) {
        num=num.replace(re,"$1,$2");
    }
    return num;
}
function recharge()
{
	var s = $("#amount").val();
	if (s != "" && s != null) {
	    var amount = parseFloat(s);
		var doURL ="${ctx}/pay/pay/recharge?bank=AAA&amount="+amount;
    $.ajax({
        url: doURL,
        type: 'post',
        dataType: 'html',
        error: function(){
        	alert('error');
        },
        success: function(response){
            alert(response.msg);
            /**var fee;
		    if(amount<20000){
		    	fee=parseFloat(1);
		    }
		    if(amount<50000&&amount>=20000){
		    	fee=parseFloat(3);
		    }
		    if(amount>=50000&&amount<=1000000){
		    	fee=parseFloat(5);
		    }*/
           
            //$("#_currentBanlance").val(parseFloat(response.realAmount));
            //var banlance=$("#_currentBanlance").val();
            //banlance=parseFloat(banlance)+parseFloat(s)-fee;
            //$("#_currentBanlance").val(parseFloat(response.realAmount));
            $("#currentBanlance").html(parseFloat(response.realAmount)+"元");
            $("#amount").val("");
            $("#fee").html("0.00元");
            $("#realAmount").html("0.00元");
            $("#afterBanlance").html("0.00元");
        }
    });
	}else{
		alert("请输入充值金额");
	}
}
</script>
<script type="text/javascript">
	$(document).ready(function() {
	//验证年利率输入是否合法
	jQuery.validator.addMethod("isYearRate", function(value, element){ 
	  	var reg = /^([1-2]\d{1})$|^[1-2]\d{1}(\.{1}\d{0,2})$/;
	  	return this.optional(element) || (reg.test(value)); 
	}, "利率精确到小数点后两位，范围为10%-24.4%之间");
			 $("#payForm").validate({
		    rules : {
					amount: {
		            required: true,
		            number:true,
		            range:[1.001,1000000]
		            
		            }
		        },
		    messages: {
				amount: {
					required: '单笔充值金额应大于1元且小于100万元!',
					number:'必须为数字',
					range:'单笔充值金额应大于1元且小于100万元!'
				}
			}	
	    
	    });
			 
			 $("#shengpayBank").hide();
			 $("#epayBank").hide();
			 $("#gopayBank").find("input:first").attr("checked",true)
});
	
	function changeBank(obj){ 
		$("input[name=bank][checked]").attr("checked",false);
		var value = obj.value;
			
		if(value=='01'){ 
			$("#shengpayBank").hide()
			$("#gopayBank").hide()
			$("#epayBank").show()
			$("#epayBank").find("input:first").attr("checked",true)
		}
		if(value=='02'){
			$("#shengpayBank").hide()
			$("#epayBank").hide()
			$("#gopayBank").show()
			$("#gopayBank").find("input:first").attr("checked",true)
		}
	} 
</script>
<form:form id="payForm" modelAttribute="" action="${ctx}/pay/pay/recharge" method="post" >
						<input type="hidden"  id="_currentBanlance" value="${payRechargeVO.real_amount}"/>
                        <input type="hidden" id="rate" value="${payRechargeVO.rate}"/>
						<div class="prompt5">
							<p>1.所有投标保证金将由第三方平台托管。</p>
							<p>2.即时充值所产生的转账费用为0.5%，第三方平台将在转账时直接扣除。</p>
							<p>3.请注意您的银行卡充值限制，以免造成不便。</p>
							<p>4.如果充值金额没有及时到账，请和客服联系。</p> 
							<p class="col1">5.请勿使用信用卡进行充值。</p> 
						</div>
						<table class="table10">
							<tr><th colspan="8">请选择支付平台：</th></tr>
							<tr>
							
							<td class="td12"></td>
								<td></td>
								
								
								
								
							<td class="td12"> <input checked type="radio"  name="payType" value="02" onclick="changeBank(this)"/></td>
								<td><img src="${ctx}/static/images/gfb.gif" /></td>
							<td class="td12"><input type="radio"  name="payType" value="01" onclick="changeBank(this)"/></td>
						<td><img src="${ctx}/static/images/yeepay.gif" />
							</td>
						<td width="200" colspan="2">&nbsp;</td>
						</tr>						</table>
						<%-- 易宝 --%>
                        <table class="table10" id="epayBank" style="display:none">
                         <tr><th colspan="8">可选支付银行：</th></tr>
                         <tr>
                               <td class="td12"><input  type="radio" name="bank" id="PINGANBANK-NET" value="PINGANBANK-NET" checked="checked" /></td>
                               <td><img src="${ctx}/static/images/bank/bandpingan.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="ABC-NET" value="ABC-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankabc.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="BOC-NET" value="BOC-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbc.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="BOCO-NET" value="BOCO-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbcc.gif" /></td>
                          </tr>
                          <tr>
                               <td class="td12"><input  type="radio" name="bank" id="CBHB-NET" value="CBHB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbh.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="BCCB-NET" value="BCCB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbj.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="CCB-NET" value="CCB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankccb.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="CIB-NET" value="CIB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankcib.gif" /></td>
                          </tr>
                          <tr>
                               <td class="td12"><input  type="radio" name="bank" id="CMBCHINA-NET" value="CMBCHINA-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankcmb.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="CMBC-NET" value="CMBC-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankcmbc.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="HKBEA-NET" value="HKBEA-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankdy.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="GDB-NET" value="GDB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankgdb.gif" /></td>
                          </tr>
                          <tr>
                               <td class="td12"><input  type="radio" name="bank" id="POST-NET" value="POST-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankpost.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="SDB-NET" value="SDB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/banksdb.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="SPDB-NET" value="SPDB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankshpd.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="ICBC-NET" value="ICBC-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankicbc.gif" /></td>
                          </tr>
                          
                          <tr>
                               <%-- <td class="td12"><input  type="radio" name="bank" id="SHRCB-NET-B2C" value="SHRCB-NET-B2C" /></td>
                               <td><img src="${ctx}/static/images/bank/banksrccu.gif" /></td> --%>

                               <td class="td12"><input  type="radio" name="bank" id="ECITIC-NET" value="ECITIC-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/bankzx.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="CEB-NET" value="CEB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/guangda.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="NJCB-NET" value="NJCB-NET" /></td>
                               <td><img src="${ctx}/static/images/bank/nanjing.gif" /></td>
                          </tr>                                                                                                        
						</table>
						<%-- 国付宝 --%>
							<table class="table10" id="gopayBank">
							                         <tr><th colspan="8">可选支付银行：</th></tr>
							<tr>
                               <td class="td12"><input  type="radio" name="bank" id="CCB-NET" value="CCB" /></td>
                               <td><img src="${ctx}/static/images/bank/bankccb.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="CMBCHINA-NET" value="CMB" /></td>
                               <td><img src="${ctx}/static/images/bank/bankcmb.gif" /></td>
                                
                               <td class="td12"><input  type="radio" name="bank" id="ICBC-NET" value="ICBC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankicbc.gif" /></td>
                              
								<td class="td12"><input  type="radio" name="bank" id="BOC-NET" value="BOC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbc.gif" /></td> 
                               
                          </tr>
                          <tr>
                              <td class="td12"><input  type="radio" name="bank" id="ABC-NET" value="ABC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankabc.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="BOCO-NET" value="BOCOM" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbcc.gif" /></td>

                               <td class="td12"><input  type="radio" name="bank" id="CMBC-NET" value="CMBC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankcmbc.gif" /></td>
                               
                                <td class="td12"><input  type="radio" name="bank" id="BOCO-NET" value="HXBC" /></td>
                               <td><img src="${ctx}/static/images/bank/huaxia.gif" /></td>
                          </tr>
                          <tr>
                               
                               <td class="td12"><input  type="radio" name="bank" id="PAB-NET" value="PAB" /></td>
                               <td><img src="${ctx}/static/images/bank/pingan.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="SPDB-NET" value="SPDB" /></td>
                               <td><img src="${ctx}/static/images/bank/bankshpd.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="GDB-NET" value="GDB" /></td>
                               <td><img src="${ctx}/static/images/bank/bankgdb.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="ECITIC-NET" value="CITIC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankzx.gif" /></td>
                               
                          </tr>
                          <tr>
                               <td class="td12"><input  type="radio" name="bank" id="CEB-NET" value="CEB" /></td>
                               <td><img src="${ctx}/static/images/bank/guangda.gif" /></td>
                               
                               <td class="td12"><input  type="radio" name="bank" id="POST-NET" value="PSBC" /></td>
                               <td><img src="${ctx}/static/images/bank/bankpost.gif" /></td>

						       <td class="td12"><input  type="radio" name="bank" id="BOBJ-NET" value="BOBJ" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbj.gif" /></td>
								
                               <td class="td12"><input  type="radio" name="bank" id="BOS-NET" value="BOS" /></td>
                               <td><img src="${ctx}/static/images/bank/shanghaibank.gif" /></td>
                              
                          </tr>
 
                               	<!-- 
						       <td class="td12"><input  type="radio" name="bank" id="TCCB-NET" value="TCCB" /></td>
                               <td><img src="${ctx}/static/images/bank/bankbj.gif" /></td>
                                -->
                                
                          </table>
                          <table>
							<tr><th colspan="2">请输入您的充值金额：</th></tr>
							<tr><td colspan="2" class="tc font12 col1" height="50">借款用户请注意：如果您的借款是在10月8日之前在旧版平台上完成的，那么请您至旧版平台上进行充值和还款操作，谢谢您的理解和支持。</td></tr>
							<tr>
								<td class="td7">* 充值金额：</td>
								<td width="610">
										<input type="text" class="required fl"  name="amount" id="amount" 
        								  onblur="calRecharge()" 
										 /><span class="fl">&nbsp;元</span>
								&nbsp;
								<label style="display: none;" id="forAmount" class="col1">单笔充值金额应大于1元且小于100万元</label>
								
								</td>
							</tr>
							<tr><td>&nbsp;</td><td class="col1">注：请勿使用信用卡充值 </td></tr>
							<tr><td class="td7">充值手续费：</td><td><span id="fee">0.00元</span></td></tr>
							<tr><td class="td7">实际到账金额：</td><td><span id="realAmount">0.00元</span></td></tr>
							<tr><td class="td7">目前账户可用余额：</td><td><span id="currentBanlance">${payRechargeVO.real_amount}元</span></td></tr>
							<tr><td class="td7">充值后账户可用余额：</td><td><span id="afterBanlance">0.00元</span></td></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						</table>
						<!-- <a class="btn4 mar10" href="#" onclick="recharge()">充值</a> -->

						<input class="btn4 mar10"  type="submit" name="sb" value="充值"/>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
</form:form>
						
						