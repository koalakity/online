<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" />
<title>利息计算器</title>
<script type="text/javascript">
$(function(){

	$(".table7").hide();
	$(".tc").hide();
	$('#calculator').click(function(){
		if(calculate()==false){
			return false()
		}
		$(".table7").show();
    	if ($($("[name='isShowLoanTime']")).attr("checked")) {
			$(".tc").show();
    	} else {
    		$(".tc").hide();
          	}
	});
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	$(".account_l").find("dt").click(function(){
		var m = $(this).siblings("dd");
		var m1 = $(this).find("img");
		if(m.is(":hidden")){
			m1.attr("src","${ctx}/static/images/account_l_dt_a1.gif");
	        m.slideDown("800");
		}else{
			m1.attr("src","${ctx}/static/images/account_l_dt_a2.gif");
		    m.slideUp("800");
		};
	});
});

function loadPage(loadUrl){
	$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
	});
	 $.ajax({
        url: loadUrl,
        type: "POST",
        dataType: 'html',
        timeout: 10000,
        error: function(){
         },
        success: function(data){
        	$(".account_r_c_6c").html(data);
         },
        beforeSend: function(){
        }
     });
}

function calculate(){
    var borrowAmount, yearRate, repayTime, isShowLoanTime;
    borrowAmount = $("#borrowAmount").val();
    yearRate = parseFloat($("#yearRate").val());
    repayTime = $("#repayTime").val();
    var reg = /^[0-9]*[1-9][0-9]*$/;
    var regF = /^([1-2]\d{1})$|^[1-2]\d{1}(\.{1}\d{1,2})$/;
    if (!reg.test(borrowAmount) || borrowAmount == '') {
        $("#error").html("借款金额必须为正整数!");
        return false;
    }else if(!(borrowAmount%50==0&&borrowAmount>=3000&&borrowAmount<=500000)){
        $("#error").html("借款金额必须为3000-500000之间,且为50的倍数!");
        return false;
    }
    else if (!(yearRate>=10&&yearRate<=24.4) || yearRate == '') {
       $("#error").html("年利率在10%和24.4%之间!");
        return false;
    }else if(!regF.test(yearRate)){
       $("#error").html("年利率输入不正确，最多一位小数!");
        return false;
    }
    else if (!reg.test(repayTime) || repayTime == '') {
        $("#error").html("月份格必须为数字类型!");
        return false;
    }
    else if (parseFloat(repayTime) - 120 > 0) {
        $("#error").html("月份必须在120以内!");
        return false;
    }
    else {
        $("#error").html("");
		$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
		});
        isShowLoanTime = document.getElementById("isShowLoanTime").checked;
        $.ajax({
		  url: '${ctx}/toolsBox/calculator/getResultData?borrowAmount='+(isNaN(borrowAmount) || borrowAmount == '' ? 0 : parseFloat(borrowAmount))+ "&yearRate=" + (isNaN(yearRate) ? 0 : parseFloat(yearRate)) + "&repayTime=" + (isNaN(repayTime) ? 0 : parseInt(repayTime)) + "&isShowLoanTime=" + isShowLoanTime,
		  type: "POST",
          dataType: 'json',
          timeout: 10000,
          	error: function(data){
		    $('#error').html("载入数据出错！");
        	},
            success: function(data){
        		$('#repayBxMonth').html(data.principalInterestMonth);
        		$('#repayLoanMonthCount').html(data.termLoan);
        		$('#repayMonthRate').html(data.monthRate);
        		$('#repayBxAmountTotal').html(data.principalInterestBalanceTotal);
        		init_repay_plan_list(data);
                $("#error").html("");
            }
   	 });
        return true;
	}
}


function init_repay_plan_list(data){
	var _table=document.getElementById("tc");
	var _newbody = document.getElementById("newbody");
	while(_newbody.hasChildNodes()){
		_newbody.removeChild(_newbody.lastChild);
	}
	var repaymentItem = new Array("本息偿还时间表");	
	var cell_th=document.createElement("th");
	cell_th.appendChild(document.createTextNode("本息偿还时间表"));
	document.getElementById("newbody").appendChild(cell_th); 
	cell_th.setAttribute("colSpan", 6); 
	var _ItemTh = new Array("月份","月还本息","月还本金","月还利息","借款管理费","本息余额");	
	var _rowTh = document.createElement("tr");
	_rowTh.className="col2 even";
	for(var i=0;i<6;i++){
		var cell_td=document.createElement("td");
		cell_td.appendChild(document.createTextNode(_ItemTh[i]));
		_rowTh.appendChild(cell_td); 
 	}
  	document.getElementById("newbody").appendChild(_rowTh);  
    		
    var _total = new Array(" "," "," "," "," ",data.principalInterestBalanceTotal.toString());
    var _row = document.createElement("tr");
    for(var k=0;k<_total.length;k++){
  		var cell_td=document.createElement("td");
	    cell_td.appendChild(document.createTextNode(_total[k]));
	   _row.appendChild(cell_td);
   	}
	document.getElementById("newbody").appendChild(_row);  
	   var x = data.repaymentList.length;
	   var y = 6;
	   var detail = new Array(x);
	   for(var i=1;i<=x;i++){
	   	 detail[i] = new Array(y);
		 detail[i][1] = (i+"月");
		 detail[i][2] = data.repaymentList[i-1].principanInterestMonth;
		 detail[i][3] = data.repaymentList[i-1].principalMonth;
		 detail[i][4] = data.repaymentList[i-1].interestMonth;
		 detail[i][5] = data.repaymentList[i-1].managementFeeMonth;
		 detail[i][6] = data.repaymentList[i-1].principalInterestBalance;
	    }
	for(var i=1;i<=x;i++){
		 var row=document.createElement("tr");
		 if(i%2!=0){
			 row.className = "even";
		 }
		 for(var j=1;j<=y;j++){
			 var cell=document.createElement("td");  
			 cell.appendChild(document.createTextNode(detail[i][j]));  
			 row.appendChild(cell);  
		 }
		 document.getElementById("newbody").appendChild(row); 
	 } 
	var infoDiv2 = document.getElementById("account_r_c_6c");
	infoDiv2.appendChild(_table);
	$('#repayBxAmountTotal').html(data.principalInterestBalanceTotal);

}
</script>

</head>
<body>
<div class="wrapper">
	<div class="content">
		<div class="account_l">
			<dl class="dl_f">
				<dt><img src="${ctx }/static/images/account_l_dt_a1.gif" />工具箱</dt>
				<dd><a href="#" class="a1 on"  onclick="loadPage('${ctx}/toolsBox/calculator/showCalculator')">利息计算器</a></dd>
				<dd><a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=18" class="a2">借款协议范本</a></dd>
				<dd><a href="#" class="a3" onclick="loadPage('${ctx}/toolsBox/calculator/showMobileLocation')">手机号码查询</a></dd>
				<dd><a href="#" class="a4" onclick="loadPage('${ctx}/toolsBox/calculator/findIpAddress')">IP地址查询</a></dd>
			</dl>
		</div>
		<div class="account_r">
			<div class="account_r_c_6c">
				<div class="prompt5">
					<strong class="col2 font_14">利息计算器</strong><br />
					证大e贷采用的是通用的"等额本息还款法"，即借款人每月以相等的金额偿还贷款本息，也是银行房贷等采用的方法。使用利息计算器，能帮您计算每月需要偿还的本息情况；同时，一份完整的本息偿还时间表，让您能更直观地了解还款本息详情。因计算中存在四舍五入，最后一期还款金额与之前略有不同。
				</div>
				<table class="table6">
					<tr><th colspan="4">借款设置</th></tr>
					<tr><td>借款金额：<input type="text" id="borrowAmount" name="borrowAmount" class="input_80" /></td><td>年利率：<input type="text" id="yearRate" class="input_80" />&nbsp;%</td><td>借款期限：<input type="text" id="repayTime" class="input_80" />&nbsp;个月</td><td>还款方式：<select><option>每月还款</option></select></td></tr>
					<tr class="tr1"><td colspan="4"><input type="checkbox" id="isShowLoanTime" name="isShowLoanTime" class="fl mar11" checked="checked" /><span class="fl">&nbsp;显示还款时间表&nbsp;&nbsp;</span><input type="button" id="calculator" class="btn1 fl" value="开始计算" />&nbsp;&nbsp;&nbsp;&nbsp;<span style="color: red" id="error"></span></td></tr>
				</table>
				<table class="table7">
					<tr><th colspan="2">贷款描述</th></tr>
					<tr><td>每月需还本息：<span class="col1" id="repayBxMonth"></span></td><td>您将在<span class="col1" id="repayLoanMonthCount"></span>个月后还清贷款</td></tr>
					<tr class="tr1"><td>月利率：<span class="col1" id="repayMonthRate"></span></td><td>您需还本息共<span class="col1" id="repayBxAmountTotal"></span></td></tr>
				</table>
				<table class="tc">
					<tbody id="newbody" ></tbody>
				</table>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>