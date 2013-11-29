<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" /> 
<link rel="stylesheet" href="${ctx}/static/css/message.css" type="text/css" />
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<link rel="stylesheet" type="text/css" href="${ctx}/static/css/jquery.fancybox-1.3.4.css"/>
<script>
$(document).ready(function() {

	//聚焦第一个输入框
		//$("#message").focus();
		//为inputForm注册validate函数
		$("#form1").validate( {
			rules : {
				message : {
					remote : "${ctx}/borrowing/releaseLoan/checkPower?userId="+$("#userId").val(),
					rangelength : [ 1, 500 ]
				}
				
			},
			messages : {
				message : {
					remote : "通过实名绑定的借款人和理财人才能留言。",
					rangelength : "字数限制：20-500字"
				}
			}

		});
	});

function tijiao(i){
	var formid = "formmsg"+i;
	document.getElementById(formid).submit();
}
</script>

<script type="text/javascript">
$(function(){
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	$(".loan_detail_l").find("dd").click(function(){
		var m = $(this).index();
		var m1 = ".loan_detail_r2_c" + m;
		$(this).addClass("on").siblings("dd").removeClass("on");
		$(".loan_detail_r2_t").find("h3").eq(m-1).addClass("on").siblings().removeClass("on");
		$.ajaxSetup ({ 
      		cache: false //关闭AJAX相应的缓存 
		});
		var loadUrl ="";
		if(m==1){
			loadUrl = "${ctx}/borrowing/releaseLoan/getCreditInfo?userId="+${loanInfo.user.userId};
		}
		if(m==2){
		loadUrl = "${ctx}/borrowing/releaseLoan/getZendaiAuditRecord?userId="+${loanInfo.user.userId};
		}
		if(m==3){
		//loadUrl = "${ctx}/borrowing/releaseLoan/getLoanInfoDetail?loanId="+${loanInfo.loanId}+"&isFrame=true"+"&random=${random}";
		}
		if(m==4){
		loadUrl = "${ctx}/borrowing/releaseLoan/getInvestLoanInfo";
		}
		
	 	$.ajax({
       		 url: loadUrl,
       	 	 type: "POST",
       		 dataType: 'html',
       		 timeout: 10000,
        	 error: function(){
        },
       	success: function(data){
        	
        	$(m1).html(data);
       	},
        beforeSend: function(){
        }
     	});
		
		
		$(m1).show().siblings().hide();
	});
	$(".loan_detail_r2_t").find("h3").click(function(){
		var m = $(this).index() + 1;
		var m1 = ".loan_detail_r2_c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(".loan_detail_l").find("dd").eq(m-"1").addClass("on").siblings("dd").removeClass("on");
		$(m1).show().siblings().hide();
		//关闭AJAX相应的缓存 
		$.ajaxSetup ({ 
      		cache: false 
		});
		var loadUrl ="";
		if(m==1){
			var loanId ='${loanInfo.loanId}';
			loadUrl = "${ctx}/borrowing/releaseLoan/getCreditInfo?userId="+${loanInfo.user.userId};
			if(loadUrl.length>=1){
				loadUrl = loadUrl+"&loanId="+loanId;
				}
		}
		if(m==2){
		loadUrl = "${ctx}/borrowing/releaseLoan/getZendaiAuditRecord?userId="+${loanInfo.user.userId};
		}
		if(m==3){
		//loadUrl = "${ctx}/borrowing/releaseLoan/getLoanInfoDetail?loanId="+${loanInfo.loanId}+"&isFrame=true"+"&random=${random}";
		}
		if(m==4){
		loadUrl = "${ctx}/borrowing/releaseLoan/getInvestLoanInfo";
		}
	 	$.ajax({
       		 url: loadUrl,
       	 	 type: "POST",
       		 dataType: 'html',
       		 timeout: 10000,
        	 error: function(){
	        },
	       	success: function(data){
	        	$(m1).html(data);
	       	},
	        beforeSend: function(){
	        }
		});
	});	
	$("a.close").click(function(){
		$(this).parent(".msg_area").hide();
		return false;
	});
	
	$(".msg").find(".hf").click(function(){
		$(this).next(".msg_area").toggle();
		return false;
	})
});

//调用弹出层
function showfancy(gourl,wd,hg){
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
					//TODO   跳转到成为理财人页面
					alert("跳转到成为理财人页面");
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
//关闭弹出层
function closefancy(){
    $.fancybox.close();
}
function confirmInvest(){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	var loanId = document.getElementById("loanId").value;
	var investAmount = document.getElementById("investAmount").value;
	if(!isNaN(investAmount)){
		
		if(investAmount >= 50 && investAmount%50 == 0){
			$.ajax( {
				url : "${ctx}/financial/searchLoan/checkUserAomount?investAmount="+investAmount+"&loanId="+loanId,
				dataType : "text",
				success : function(data) {
					
					if(data == "true"){
						//进入投标操作
						$.ajax( {
							url : "${ctx}/financial/searchLoan/invest?investAmount="+investAmount+"&loanId="+loanId,
							dataType : "text",
							success : function(data) {
								
								if(data == "cantInvest"){
									alert("你不能对自己发布的借款进行投标");
									closefancy();
								}else{
									if(data == "true"){
										alert("投标成功");
										closefancy()
										window.location.href = "${ctx}";
									}else{
										alert("投标失败");
									}
								}
								
							}
						});
						
					}else if(data == "false"){
						//TODO      跳转到充值页面
						if(confirm("您的余额不足， 请充值！")){
							window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount";
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
</script>
	<div class="content">
		<div class="loan_detail_t">
			<a href="javascript:history.back();">返回发布借款</a>
			<h2>借款详情</h2>
		</div>
		<div class="loan_detail_l">
			<div class="person_doc">
				<p>借款人档案</p>
				<c:if test="${empty loanInfo.user.userInfoPerson.headPath}"><img src="${ctx}/static/images/nophoto.jpg" /></c:if><c:if test="${not empty loanInfo.user.userInfoPerson.headPath}"><img src="/pic/${loanInfo.user.userInfoPerson.headPath}"width="100" height="100" class="person" /></c:if><br />
				<strong>${loanInfo.user.userInfoPerson.user.loginName}</strong><br />
				<span>户籍所在地：${loanInfo.user.userInfoPerson.hkAddress}</span><br />
				<span>信用等级：</span><c:if test="${loanInfo.user.userCreditNote.creditGrade==1}"><img src="${ctx}/static/images/img28.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==2}"><img src="${ctx}/static/images/img27.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==3}"><img src="${ctx}/static/images/img26.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==4}"><img src="${ctx}/static/images/img25.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==5}"><img src="${ctx}/static/images/img24.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==6}"><img src="${ctx}/static/images/img23.gif" /></c:if>
		<c:if test="${loanInfo.user.userCreditNote.creditGrade==7}"><img src="${ctx}/static/images/img22.gif" /></c:if><br />
				<div class="mar3"><img src="${ctx }/static/images/img99.png" /><a href="#">发送消息</a><img src="${ctx}/static/images/img98.png" /><a href="#">举报此人</a></div>
			</div>
			<dl>
				<dt>借款详情分类</dt>
				<dd class="dd1 on">借入者信用信息</dd>
				<dd class="dd2">证大e贷审核记录</dd>
				<dd class="dd3">借款详情介绍</dd>
				<dd class="dd4">投标记录</dd>
				<dd class="dd5">留言板</dd>
			</dl>
		</div>
		<div class="loan_detail_r">
			<div class="loan_detail_r1">
						<c:if test="${loanInfo.loanUse==1}"><img src="${ctx}/static/images/img31.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==2}"><img src="${ctx}/static/images/img001.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==3}"><img src="${ctx}/static/images/img30.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==4}"><img src="${ctx}/static/images/img32.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==5}"><img src="${ctx}/static/images/img35.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==6}"><img src="${ctx}/static/images/img34.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==7}"><img src="${ctx}/static/images/img36.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==8}"><img src="${ctx}/static/images/img33.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==9}"><img src="${ctx}/static/images/img37.jpg" class="fl mar4"  /></c:if>
						<c:if test="${loanInfo.loanUse==10}"><img src="${ctx}/static/images/img38.jpg" class="fl mar4"  /></c:if>
				<table>
					<tr><th colspan="3">${loanInfo.loanTitle}<span>（借贷编号：${loanInfo.loanId}）</span></th></tr>
					<tr><td>借款金额：<span class="col1">${loanInfo.loanAmount}</span></td><td>借款利率：<span class="col1">${loanInfo.yearRate}</span></td><td>借款期限：<span class="col1">${loanInfo.loanPeriod}</span>个月</td></tr>
					<tr><td>还款周期：按月还款</td><td>还款方式：等额本息</td><td>月还本息：<span class="col1">${loanInfo.principanInterestMonth}</span></td></tr>
					<tr><td><span class="fl">投标进度：</span><div class="raise_s mar9 fl"><span style="width:${loanInfo.speedProgress}%;"></span><em>${loanInfo.speedProgress}%</em></div><span class="fl">已完成</span></td><td colspan="2">投标:<span class="col1">${loanInfo.bidNumber}</span>笔，还需：<span class="col1">${loanInfo.surplusAmount}元</span></td></tr>
					<tr><td colspan="2">借款用途：${loanInfo.loanUseStr}</td><td rowspan="2"><a  onclick=""  class="btn6 mar0">马上投标</a><span class="font_12 col3">（最低投标金额：￥50）</span></td></tr>
					<tr><td colspan="2">
									
									剩余时间： 6天23小时59分
					</td></tr>
				</table>
			<input type="hidden" id="timer" name="timer" value="${loanInfo.surplusTime}"/>
			</div>
			<div class="loan_detail_r2">
				<div class="loan_detail_r2_t">
					<h3 class="on">借入者信用信息</h3>
					<h3>e贷审核记录</h3>
					<h3>借款详情介绍</h3>
					<h3>投标记录</h3>
				</div>
				<div class="loan_detail_r2_c" id="loan_detail_r2_c">
						<div class="loan_detail_r2_c1" id="loan_detail_r2_c1">
						<%@ include file="/WEB-INF/views/releaseLoanDetail/loan_detail_r2_c1.jsp"%>
					</div>
					<div class="loan_detail_r2_c2" style="display:none;" id="loan_detail_r2_c2">
						<%@ include file="/WEB-INF/views/releaseLoanDetail/loan_detail_r2_c2.jsp"%>
					</div>
					<div class="loan_detail_r2_c3" style="display:none;">
						<%@ include file="/WEB-INF/views/releaseLoanDetail/loan_detail_r2_c3.jsp"%>
					</div>
					<div class="loan_detail_r2_c4" style="display:none;">
						<%@ include file="/WEB-INF/views/releaseLoanDetail/loan_detail_r2_c4.jsp"%>
					</div>
				</div>
				</div>
				<div>
				留言内容为空
				</div>
			</div>
		</div>
		
	<div class="clear"></div>