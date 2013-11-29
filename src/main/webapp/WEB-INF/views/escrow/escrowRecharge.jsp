<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript">
//充值计算
function calRecharge(free){
	var s = $("#amount").val();
    var amount = 0;
    if (s != "" && s != null) {
        amount = parseFloat(s);
        if(amount>1000000)
        {
        	amount=1000000;
        	//alert(amount);
        	//$("#amount").html("1000000");
        }
    }
	var rechargeRate=$("#rechargeRate").val();
	var currentBanlance=$("#_currentBanlance").val();
	var fee;
    var v = parseFloat(amount) > 1 && parseFloat(amount) < 1000000 &&
    /^(([1-9]{1}\d*)|([0]{1}))(\.(\d){1,2})?$/.test(parseFloat(amount));
    if(!v){
        $("#forAmount").show();
    }else{
        $("#forAmount").hide();
    }   
	//alert(rechargeRate);
    //$("#fee").html(formatNum(parseFloat(amount)*parseFloat(rechargeRate))+"元");//手续费
    if(amount<20000){
    	fee=parseFloat(1);
    }
    if(amount<50000&&amount>=20000){
    	fee=parseFloat(3);
    }
    if(amount>=50000&&amount<=1000000){
    	fee=parseFloat(5);
    }    
    

	if (s != "" && s != null) {
       $("#afterBanlance").html(formatNum(parseFloat(amount)-fee+parseFloat(currentBanlance))+"元");
       $("#fee").html(fee+"元");
	   $("#realAmount").html(formatNum(parseFloat(amount)-fee)+"元");
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
		var doURL ="${ctx}/escrow/escrow/recharge?amount="+amount;
    $.ajax({
        url: doURL,
        type: 'post',
        dataType: 'json',
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
<input type="hidden"  id="_currentBanlance" value="${escrowRechargeVO.real_amount}"/>
						<div class="prompt5">
							<p>1.所有投标保证金将由第三方平台托管。</p>
							<p>2.即时充值所产生的转账费用为0.5%，第三方平台将在转账时直接扣除。</p>
							<p>3.请注意您的银行卡充值限制，以免造成不便。</p>
							<p>4.如果充值金额没有及时到账，请和客服联系。</p> 
						</div>
						<table>
							<tr><th>您将会以下面的方式进行账户充值：</th></tr>
							<tr><td class="td8"><input type="checkbox" /><img src="${ctx}/static/images/img97.jpg" /></td></tr>
						</table>
						<table>
							<tr><th colspan="2">请输入您的充值金额：</th></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
							<tr>
								<td class="td7">充值金额：</td>
								<td>
										<input type="text" id="amount"
        								 onpropertychange="calRecharge()" 
		 								 onblur="calRecharge()" 
										 onkeyup="calRecharge()"/>
								&nbsp;&nbsp;
								<label style="display: none;" id="forAmount" class="col3">单笔充值金额应大于1元且小于100万元</label>
								
								</td>
							</tr>
							<tr><td class="td7">充值费用：</td><td><span id="fee">0.00元</span></td></tr>
							<tr><td class="td7">实际到账金额：</td><td><span id="realAmount">0.00元</span></td></tr>
							<tr><td class="td7">目前账户可用余额：</td><td><span id="currentBanlance">${escrowRechargeVO.real_amount}元</span></td></tr>
							<tr><td class="td7">充值后账户可用余额：</td><td><span id="afterBanlance">0.00元</span></td></tr>
							<tr><td>&nbsp;</td><td>&nbsp;</td></tr>
						</table>
						<a class="btn4 mar10" href="#" onclick="recharge()">充值</a>