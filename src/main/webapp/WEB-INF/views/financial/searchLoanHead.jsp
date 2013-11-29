<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/invest.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/message.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<script type="text/javascript">
var tabFlag = 0;
$(function() {
	//顶部菜单导航切换
	$(".nav").find("a").hover(function() {
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	//借款列表tab切换
	$(".invest_t").find("h3").click(
			function() {
				$.ajaxSetup( {
					cache : false
				//关闭AJAX相应的缓存 
						});
				var m = $(this).index() + 1;
				tabFlag = m;
				if (m == 1) {
					$(".invest_c").load("${ctx}/financial/searchLoan/allLoan?column=none&seq=none&p=none",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
							});
				}
				if (m == 2) {

					$(".invest_c").load("${ctx}/financial/searchLoan/loanIng?column=none&seq=none&p=none",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
							});
				}
				/*if (m == 3) {
					$(".invest_c").load("${ctx}/financial/searchLoan/futureLoan?column=none&seq=none&p=none",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
							});
				}*/
				if (m == 3) {
					$(".invest_c").load("${ctx}/financial/searchLoan/loanEd?column=none&seq=none&p=none",
							function(responseText, textStatus, XMLHttpRequest) {
								$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
							});
				}

				var m1 = ".invest_c" + m;
				$(this).addClass("on").siblings().removeClass("on");
				$(m1).show().siblings().hide();
			});
	//理财计算器显示隐藏切换
	$(".invest_t").find("a").click(function() {
		var m = $(".invest_counter");
		var m1 = $(this);
		if (m.is(":hidden")) {
			m1.addClass("on");
			m.slideDown("800");
		} else {
			m1.removeClass("on");
			m.slideUp("800");
		}
		;
	});
	//初始化表格斑马线
	$(".invest_c").find("tr:even").css("background","#F6F6F6");
		//跳转已完成列表
	if(${tabCFlag}==3){
		$(".invest_t").find("h3").eq(2).click();
	};
});

// 马上投标
function bidImmediately(gourl, wd, hg){
	$.ajax( {
		url : "${ctx}/financial/searchLoan/checkInvestTime",
		 type: "POST",
		dataType : "text",
		error: function(data){
			//window.location.href = "${ctx}/accountLogin/login/show";
        },
		success : function(data) {
			if(data=="true"){
				  showfancy(gourl, wd, hg);
			}else{
				alert("两次投标要间隔一分钟以上！");
			}
		}
	});
}

//调用弹出层
function showfancy(gourl, wd, hg) {
$.ajaxSetup( {
	cache : false
	//关闭AJAX相应的缓存 
});
	var flg = false;
	$.ajax( {
		url : "${ctx}/financial/searchLoan/checkUserLogin",
		 type: "POST",
		dataType : "text",
		error: function(data){
			//window.location.href = "${ctx}/accountLogin/login/show";
        },
		success : function(data) {
			if(data == "notLogin"){
				window.location.href = "${ctx}/accountLogin/login/show";
			}else if(data == "becomeFinancial"){
				$.ajax({
				   		url:"${ctx}/financial/beLender/checkBalanceAmount",
				   		type:"POST",
				   		dataType: "json",
				   		success: function(response){
			   			if(response){
			   				// 跳转到成为理财人页面
							alert("您还不是理财人，跳转到成为理财人页面");
			   				window.location.href="${ctx}/financial/beLender/getLoginInfo";
			   			}else{
			   				alert("余额不足五元，请先充值。");
			   				window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay";
			   			}
			  		}
			  		 	
					});
			}else if(data == "lockUser"){
				alert("当前您已被举报或锁定，请邮件或电话客服咨询");
			}else{
				
					$.ajax( {
					url : gourl,
					wd : wd,
					hg : hg,
					dataType : "html",
					success : function(html) {
						$.fancybox(html, {
							modal : true,
							autoDimensions : false,
							width : wd,
							height : hg,
							padding : 0,
							margin : 0,
							centerOnScroll : true
						});
					}
					});
				
			}
		}
	});
	
}
//关闭出层
function closefancy() {
	$.fancybox.close();
};

//搜索
function searLoan() {
	var cg = document.getElementById("creditGrade").value;
	var yr = document.getElementById("yearRate").value;
	var lt = document.getElementById("loanTime").value;
	$(".invest_c").load(
			"${ctx}/financial/searchLoan/searchLoanList?pager.offset=0&creditGrade=" + cg
					+ "&yearRate=" + yr + "&loanTime=" + lt+"&tabFlag="+tabFlag+"&column=none&seq=none&p=none",
			function(responseText, textStatus, XMLHttpRequest) {
				$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
			});
	var m1 = ".invest_c" + 1;
	if(tabFlag==1){
		$(".invest_t").find("h3:first").addClass("on").siblings().removeClass("on");
	}else if(tabFlag==2){
		$(".invest_t").find("h3:first").next().addClass("on").siblings().removeClass("on");
	}else if(tabFlag==3){
		$(".invest_t").find("h3:first").next().next().addClass("on").siblings().removeClass("on");
	}else if(tabFlag ==4 ){
		$(".invest_t").find("h3:first").next().next().next().addClass("on").siblings().removeClass("on");
	}else{
	}
}

function confirmInvest(){
	
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	var loanId = document.getElementById("loanId").value;
	var investAmount = document.getElementById("investAmount").value;
	var tokenValue = document.getElementById("requestToken").value;
	if(!isNaN(investAmount)){
		
		if(investAmount >= 50 && investAmount%50 == 0){
			$.ajax( {
				url : "${ctx}/financial/searchLoan/checkUserAomount?investAmount="+investAmount+"&loanId="+loanId,
				dataType : "text",
				success : function(data) {
					
					if(data == "true"){
						//$('#confirmInvest').attr("disabled","disabled").addClass("btn4_disabled");
						//$(".overlay").css({'display':'block','opacity':'0.8'});
						//$(".showbox").stop(true).animate({'margin-top':'350px','opacity':'1'},200);
						var abc = $("<div class='m-overlay'></div><div class='m-outer'><br/><img src='${ctx}/static/images/m-loading.gif' height='15' /><br/>投标进行中......</div>");
						var lay_h = $("#fancybox-wrap").height();
						var lay_w = $("#fancybox-wrap").width();
						abc.appendTo("#fancybox-wrap");
						var outer_h = $(".m-outer").height();
						var outer_w = $(".m-outer").width();
						$(".m-overlay").height(lay_h);
						$(".m-outer").css({"top":lay_h/2-outer_h/2,"left":lay_w/2-outer_w/2});
						//进入投标操作
						$.ajax( {
							url : "${ctx}/financial/searchLoan/invest?investAmount="+investAmount+"&loanId="+loanId+"&requestToken="+tokenValue,
							dataType : "text",
							success : function(data) {
								//$(".showbox").stop(true).animate({'margin-top':'250px','opacity':'0'},400);
								//$(".overlay").css({'display':'none','opacity':'0'});
								if(data == "cantInvest"){
									alert("你不能对自己发布的借款进行投标");
									closefancy();
								}else{
									if(data == "true"){
										alert("投标成功");
										closefancy()
										window.location.href = "${ctx}/myAccount/myAccount/showMyAccount?strUrlType=investment";
									}else{
										alert("投标失败");
									}
								}
								$(".m-overlay, .m-outer").remove();
							}
						});
						
					}else if(data == "false"){
						//TODO      跳转到充值页面
						if(confirm("您的可用余额不足，请充值!")){
							window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay";
						}
						
					}else if(data == "full"){
						//投资金额大于  投资剩余金额
						alert("你的投资金额大于剩余投标金额");
						
					}
				}
			});
		}else{
			alert("最低投标金额为50元，并且是50的倍数");
		}
	}else {
		alert("请输入数字");
	}
}

//理财计算器
/*function calculate(){
	var investAmount = document.getElementById("cinvestAmount").value;
	var yearRate = document.getElementById("yearRate").value;
	var loanDuration = document.getElementById("loanDuration").value;
	if(checkValue()){
		$.ajax({
			url : "${ctx}/financial/searchLoan/investCalculate?investAmount="+investAmount+"&yearRate="+yearRate+"&loanDuration="+loanDuration,
			type : "POST",
			dataType : "text",
			success : function (data){
				alert(data);
			}
		});
	}

}*/

function checkValue(){
	var investAmount = document.getElementById("investAmount").value;
	var yearRate = document.getElementById("yearRate").value;
	var loanDuration = document.getElementById("loanDuration").value;
    var reg = /^[0-9]*[1-9][0-9]*$/;
    var regF = /^([1-2]\d{1})$|^[1-2]\d{1}(\.{1}\d{1,2})$/;
    
    if (!reg.test(investAmount) || investAmount == '') {
        alert("投资金额必须为正整数!");
        return false;
    }else if(!(investAmount%50==0)){
       alert("投资金额必须是50的倍数!");
        return false;
    }
    else if (!(yearRate>=10&&yearRate<=24.4) || yearRate == '') {
       alert("年利率在10%和24.4%之间!");
        return false;
    }else if(!regF.test(yearRate)){
       alert("年利率最多两位小数!");
        return false;
    }
    else if (!reg.test(loanDuration) || loanDuration == '') {
        alert("月份格必须为数字类型!");
        return false;
    }else{
    	return true;
    }
        
}

//分页
function fenye(url){
	url = url+"&column=none&seq=none&p=none";
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".invest_c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
	});
}

//搜索分页
function searchFenye(url){
	var cg = document.getElementById("creditGrade").value;
	var yr = document.getElementById("yearRate").value;
	var lt = document.getElementById("loanTime").value;
	url = url+"&creditGrade=" + cg
					+ "&yearRate=" + yr + "&loanTime=" + lt+"&tabFlag="+tabFlag+"&column=none&seq=none&p=none";

	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".invest_c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
	});
}
//排序
/**
col 排序的列
seq 顺序  升降
p 那个页面
*/
function jineSequence(column,seq,p){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	var m = p;
	if (m == 1) {
		$(".invest_c").load("${ctx}/financial/searchLoan/allLoan?column="+column+"&seq="+seq+"&p="+p,
				function(responseText, textStatus, XMLHttpRequest) {
					$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
				});
	}
	if (m == 2) {

		$(".invest_c").load("${ctx}/financial/searchLoan/loanIng?column="+column+"&seq="+seq+"&p="+p,
				function(responseText, textStatus, XMLHttpRequest) {
					$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
				});
	}
	if (m == 3) {
		$(".invest_c").load(
				"${ctx}/financial/searchLoan/futureLoan?column="+column+"&seq="+seq+"&p="+p,
				function(responseText, textStatus, XMLHttpRequest) {
					$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
				});
	}
	if (m == 4) {
		$(".invest_c").load("${ctx}/financial/searchLoan/loanEd?column="+column+"&seq="+seq+"&p="+p,
				function(responseText, textStatus, XMLHttpRequest) {
					$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
				});
	}
	if (m == 5) {
		$(".invest_c").load(
				"${ctx}/financial/searchLoan/searchLoanList?column="+column+"&seq="+seq+"&p="+p,
				function(responseText, textStatus, XMLHttpRequest) {
					$(".invest_c").html(responseText).find("tr:even").css("background","#F6F6F6");
				});
	}
	$(".invest_t").find("h3").eq(p-1).addClass("on").siblings().removeClass("on");
}

function calculate(){
	var investAmount = document.getElementById("cinvestAmount").value;
	var yearRate = document.getElementById("cyearRate").value;
	var loanDuration = document.getElementById("loanDuration").value;
	var receiveAmount = document.getElementById("receiveAmount");
    var reg = /^[0-9]*[1-9][0-9]*$/;
    var regF = /^([1-2]\d{1})$|^[1-2]\d{1}(\.{1}\d{1,2})$/;
    if (!reg.test(investAmount) || investAmount == '') {
        alert("借款金额必须为正整数!");
        return false;
    }else if(!(investAmount%50==0&&investAmount>=50&&investAmount<=500000)){
        alert("借款金额必须为50-500000之间,且为50的倍数!");
        return false;
    }
    else if (!(yearRate>=10&&yearRate<=24.4) || yearRate == '') {
       alert("年利率在10%和24.4%之间!");
        return false;
    }else if(!regF.test(yearRate)){
       alert("年利率最多两位小数!");
        return false;
    }
    else if (!reg.test(loanDuration) || loanDuration == '') {
        alert("月份格必须为数字类型!");
        return false;
    }
    else if (parseFloat(loanDuration) - 120 > 0) {
        alert("月份必须在120以内!");
        return false;
    }else {
			$.ajax({
				url : "${ctx}/financial/searchLoan/investCalculate?investAmount="+investAmount+"&yearRate="+yearRate+"&loanDuration="+loanDuration,
				type : "POST",
				dataType : "json",
				success : function (data){
					receiveAmount.value = data;
				}
			});
	}
}



</script>
<div class="wrapper">
<div class="content">
	<div class="invest_search">
		<form:form id="searchForm"
			action="${ctx}/financial/searchLoan/searchLoanList" method="post">
			<input type="button" class="btn1 fr" value="搜索" onclick="searLoan()" />
		信用等级：
		<select id="creditGrade" name="creditGrade">
				<option value="0">
					全部
				</option>
				<option value="7">
					AA级
				</option>
				<option value="6">
					A级
				</option>
				<option value="5">
					B级
				</option>
				<option value="4">
					C级
				</option>
				<option value="3">
					D级
				</option>
				<option value="2">
					E级
				</option>
				<option value="1">
					HR级
				</option>
			</select>
		&nbsp;&nbsp;&nbsp;&nbsp;年利率：
		<select id="yearRate" name="yearRate">
				<option value="0">
					全部
				</option>
				<option value="1">
					&lt;=15%
				</option>
				<option value="2">
					15%-20%
				</option>
				<option value="3">
					>=20%
				</option>
			</select>
		&nbsp;&nbsp;&nbsp;&nbsp;借款期限：
		<select id="loanTime" name="loanTime">
				<option value="0">
					全部
				</option>
				<option value="3">
					3个月
				</option>
				<option value="6">
					6个月
				</option>
				<option value="9">
					9个月
				</option>
				<option value="12">
					12个月
				</option>
				<option value="18">
					18个月
				</option>
				<option value="24">
					24个月
				</option>
			</select>
		</form:form>
	</div>
	<div class="invest_t">
		<h3 class="on" id="cAllLoan">
			全部借款列表[${count.allLoanCount}]
		</h3>
		<h3 id="cLoanIng">
			进行中的借款[${count.ingLoanCount}]
		</h3>
		<!-- <h3 id="cfutureLoan">
			即将开始的借款[${count.futureLoanCount}]
		</h3> -->
		<h3 id="cLoanEd">
			已完成的借款[${count.oldLoanCount}]
		</h3>
		<a href="#">理财计算器</a>
	</div>
	<div class="invest_counter">
		初始投资：
		<input type="text" id="cinvestAmount" name="cinvestAmount"/>
		&nbsp;元&nbsp;&nbsp;&nbsp;&nbsp;年利率：
		<input type="text"  id="cyearRate" name="cyearRate"/>
		&nbsp;%&nbsp;&nbsp;&nbsp;&nbsp;借款期限：
		<input type="text"  id="loanDuration" name="loanDuration"/>
		&nbsp;月&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" class="btn1 inline" onclick="calculate()" value="计算" />
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;到期价值：
		<input type="text"  id="receiveAmount" name="receiveAmount" disabled="true"/>
		&nbsp;元&nbsp;&nbsp;<span style="color: red" id="error"></span>
	</div>
	<div class="invest_c" style="padding-bottom:0;">
	
<div class="invest_c1">
	<table>
		<tr>
			<th>
				借款类型
			</th>
			<th>
				标题/借款人
			</th>
			<th>金额
				<!--<c:if test="${empty loanAmountseq}">
					<a href="#" onclick="jineSequence('loanAmount','asc',1)">金额↓↑</a>
				</c:if>
				<c:if test="${loanAmountseq=='none'}">
					<a href="#" onclick="jineSequence('loanAmount','asc',1)">金额↓↑</a>
				</c:if>
				<c:if test="${loanAmountseq=='asc'}">
					<a href="#" onclick="jineSequence('loanAmount','desc',1)">金额↓</a>
				</c:if>
				<c:if test="${loanAmountseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">金额↑</a>
				</c:if>-->
			</th>
			<th>年利率
				<!--<c:if test="${empty yearRateseq}">
					<a href="#" onclick="jineSequence('yearRate','asc',1)">年利率↓↑</a>
				</c:if>
				<c:if test="${yearRateseq=='none'}">
					<a href="#" onclick="jineSequence('yearRate','asc',1)">年利率↓↑</a>
				</c:if>
				<c:if test="${yearRateseq=='asc'}">
					<a href="#" onclick="jineSequence('yearRate','desc',1)">年利率↓</a>
				</c:if>
				<c:if test="${yearRateseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">年利率↑</a>
				</c:if>-->
			</th>
			<th>
				期限
			</th>
			<th>
				认证
			</th>
			<th>信用等级
				<!--<c:if test="${empty creditGradeseq}">
					<a href="#" onclick="jineSequence('creditGrade','asc',1)">信用等级↓↑</a>
				</c:if>
				<c:if test="${creditGradeseq=='none'}">
					<a href="#" onclick="jineSequence('creditGrade','asc',1)">信用等级↓↑</a>
				</c:if>
				<c:if test="${creditGradeseq=='asc'}">
					<a href="#" onclick="jineSequence('creditGrade','desc',1)">信用等级↓</a>
				</c:if>
				<c:if test="${creditGradeseq=='desc'}">
					<a href="#" onclick="jineSequence('none','none',1)">信用等级↑</a>
				</c:if>-->
			</th>
			<th>
				进度/剩余时间
			</th>
			<th>
				操作
			</th>
		</tr>
		
		<pg:pager url="${ctx}/financial/searchLoan/allLoan"
			items="${count.allLoanCount}" index="center" maxPageItems="10"
			maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
			scope="request">
			<pg:param name="index" />
			<pg:param name="maxPageItems" />
			<pg:param name="maxIndexPages" />
			<ex:searchresults>
				<c:forEach items="${loanList}" var="loanInfo">
					<tr>
						<td class="td2">
							<c:if test="${loanInfo.loanUse == 1}">
								<img src="${ctx}/static/images/img31.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 2}">
								<img src="${ctx}/static/images/img001.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 3}">
								<img src="${ctx}/static/images/img30.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 4}">
								<img src="${ctx}/static/images/img32.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 5}">
								<img src="${ctx}/static/images/img35.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 6}">
								<img src="${ctx}/static/images/img34.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 7}">
								<img src="${ctx}/static/images/img36.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 8}">
								<img src="${ctx}/static/images/img33.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 9}">
								<img src="${ctx}/static/images/img37.jpg" width="58" height="56" />
							</c:if>
							<c:if test="${loanInfo.loanUse == 10}">
								<img src="${ctx}/static/images/img38.jpg" width="58" height="56" />
							</c:if>
						</td>
						<td class="td1 tl">
							<a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}">${loanInfo.loanTitleStr}</a>
							<br />
							${loanInfo.userId.loginName}
						</td>
						<td>
							￥${loanInfo.loanAmount}
						</td>
						<td>
							${loanInfo.yearRateStr}
						</td>
						<td>
							${loanInfo.loanDuration }个月
						</td>
						<td>
								<c:forEach items="${loanInfo.userApproveList}" var="approve" varStatus="s">
									<c:if test="${approve.proId == 1}">
										
										<img src="${ctx}/static/images/img8.png" class="img1" alt="身份证认证"
											title="身份证认证" />
									</c:if>
									<c:if test="${approve.proId == 2}">
										<img src="${ctx}/static/images/img9.png" class="img1" alt="工作认证"
											title="工作认证" />
									</c:if>
									<c:if test="${approve.proId == 3}">
										<img src="${ctx}/static/images/img10.png" class="img1" alt="收入认证"
											title="收入认证" />
									</c:if>
									<c:if test="${approve.proId == 4}">
										<img src="${ctx}/static/images/img11.png" class="img1"
											alt="信用报告认证" title="信用报告认证" />
									</c:if>
									<c:if test="${approve.proId == 5}">
										<img src="${ctx}/static/images/img12.png" class="img1" alt="房产认证"
											title="房产认证" />
									</c:if>
									
									<c:if test="${approve.proId == 6}">
										<img src="${ctx}/static/images/img20.png" class="img1" alt="技术职称认证"
											title="技术职称认证" />
									</c:if>
									
									<c:if test="${approve.proId == 7}">
										<img src="${ctx}/static/images/img13.png" class="img1" alt="购车证明"
											title="购车证明" />
									</c:if>
									<c:if test="${approve.proId == 8}">
										<img src="${ctx}/static/images/img21.png" class="img1" alt="结婚认证"
											title="结婚认证" />
									</c:if>
									<c:if test="${approve.proId == 9}">
										<img src="${ctx}/static/images/img14.png" class="img1"
											alt="居住地证明" title="居住地证明" />
									</c:if>
					
									<c:if test="${approve.proId == 10}">
										<img src="${ctx}/static/images/img15.png" class="img1" alt="视频认证"
											title="视频认证" />
									</c:if>
									<c:if test="${approve.proId ==11 }">
										<img src="${ctx}/static/images/img16.png" class="img1" alt="实地考察认证"
											title="实地考察认证" />
									</c:if>
									<c:if test="${approve.proId ==12 }">
										<img src="${ctx}/static/images/img17.png" class="img1" alt="学历认证"
											title="学历认证" />
									</c:if>
									<c:if test="${approve.proId ==13 }">
										<img src="${ctx}/static/images/img18.png" class="img1" alt="手机实名认证"
											title="手机实名认证" />
									</c:if>
									<c:if test="${approve.proId ==14 }">
										<img src="${ctx}/static/images/img19.png" class="img1" alt="微博认证"
											title="微博认证" />
									</c:if>
									<c:if test="${s.count%5==0}">
										<br/>
									</c:if>
									<c:if test="${approve.proId ==15 }">
										<img src="${ctx}/static/images/img18.png" class="img1" alt="手机认证"
											title="手机认证" />
									</c:if>
								</c:forEach>
							</td>
						<td>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==1}">
								<img src="${ctx}/static/images/img28.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==2}">
								<img src="${ctx}/static/images/img27.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==3}">
								<img src="${ctx}/static/images/img26.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==4}">
								<img src="${ctx}/static/images/img25.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==5}">
								<img src="${ctx}/static/images/img24.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==6}">
								<img src="${ctx}/static/images/img23.gif" />
							</c:if>
							<c:if test="${loanInfo.userId.userCreditNote.creditGrade==7}">
								<img src="${ctx}/static/images/img22.gif" />
							</c:if>
						</td>
						<td>
							<div class="raise_s4">
								<span style="width: ${loanInfo.schedule};"></span><em>${loanInfo.schedule}</em>
							</div>
							${loanInfo.investmentCount}笔投标，还需${loanInfo.leavingMoney}
							<br />
							<c:if test="${loanInfo.status == 1 || loanInfo.status == 8}">
								剩余时间：${loanInfo.leavingTime}
							</c:if>
							<c:if test="${loanInfo.status != 1 && loanInfo.status != 8}">
								剩余时间：0 天
							</c:if>
							
						</td>
						<td>
							<c:if test="${loanInfo.status == 1}">
								<a onclick="bidImmediately('${ctx}/financial/searchLoan/confirmInvest?loanId=${loanInfo.loanId}','602','419')" class="btn1 mar0">马上投标</a>
							</c:if>
							<c:if test="${loanInfo.status == 2}">
								<a href="#" class="btn6 mar0">已经满标</a>
							</c:if>
							<c:if test="${loanInfo.status == 3}">
								<a href="#" class="btn6 mar0">流标</a>
							</c:if>
							<c:if test="${loanInfo.status == 4}">
								<a href="#" class="btn6 mar0">还款中</a>
							</c:if>
							<c:if test="${loanInfo.status == 5}">
								<a href="#" class="btn6 mar0">成功</a>
							</c:if>
							<c:if test="${loanInfo.status == 6}">
								<a href="#" class="btn6 mar0">逾期</a>
							</c:if>
							<c:if test="${loanInfo.status == 7}">
								<a href="#" class="btn6 mar0">高级逾期</a>
							</c:if>
							<c:if test="${loanInfo.status == 8}">
								<a href="#" class="btn6 mar0">等待资料</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
		
		</ex:searchresults>
			</table>
			<pg:index export="total=itemCount">
				<p class="pagelist" style="margin-bottom:15px;"> 
					<pg:first>
	    	  			共<%=total%>条&nbsp;
	    	  			<a href="#" onclick="fenye('<%=pageUrl%>')">首页</a>
					</pg:first> 
					<pg:prev>&nbsp;
						<a href="#" onclick="fenye('<%=pageUrl%>')">上一页</a>
					</pg:prev> 
					<pg:pages>
						<%
							if (pageNumber.intValue() < 10) {
						%>
						&nbsp;
						<%
							}
						if (pageNumber == currentPageNumber) {
						%>
						<b><%=pageNumber%></b>
						<%
							} else {
						%>
						<a href="#" onclick="fenye('<%=pageUrl%>')"><%=pageNumber%></a>
						<%
							}
						%>
					</pg:pages> 
					<pg:next>&nbsp;<a href="#"
							onclick="fenye('<%=pageUrl%>')">下一页</a>
					</pg:next> 
					
					<pg:last>
						<a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
	    			</pg:last> 
		    		<br>
				</p>
			</pg:index>
		</pg:pager>
</div>
	</div>
</div>
</div>