<%@page import="java.net.URLEncoder"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.zendaimoney.online.entity.account.AccountUsers"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<script type="text/javascript">
//加入收藏夹
function AddFavorite(sURL, sTitle){
    try{
        window.external.addFavorite(sURL, sTitle);
    }catch(e){
		try{
            window.sidebar.addPanel(sTitle, sURL, "");
        }catch(e){
            alert("加入收藏失败，请使用Ctrl+D进行添加");
        }
    }
}


function checkBalanceAmount(){

	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	
	$.ajax( {
			url : "${ctx}/financial/searchLoan/checkUserLogin",
			 type: "POST",
			dataType : "text",
			error: function(data){
				//window.location.href = "${ctx}/accountLogin/login/show";
	        },
			success : function(data) {
				if(data == "notLogin"){
					alert("请先登录！");
					window.location.href = "${ctx}/accountLogin/login/show";
				}else if(data == "becomeFinancial"){
					$.ajax({
				   		url:"${ctx}/financial/beLender/checkBalanceAmount",
				   		type:"POST",
				   		dataType: "json",
				   		success: function(response){
			   			if(response.status=="true"){
			   				// 跳转到成为理财人页面
							alert("您还不是理财人，跳转到成为理财人页面");
			   				window.location.href="${ctx}/financial/beLender/getLoginInfo";
			   			}else{
			   				alert("余额不足"+response.id5Fee+"元，请先充值。");
			   				window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay";
			   			}
			  		}
			  		 	
				});
				}else if(data == "showInvesment"){
					window.location.href="${ctx}/financial/beLender/getLoginInfo";
				}else{
					alert("当前您已被举报或锁定，请邮件或电话客服咨询");
				}
			}
		});

}

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
			if(data=='true'){
				  showfancy(gourl, wd, hg);
			}else{
				alert("两次投标要间隔一分钟以上！");
			}
		}
	});
}
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
					alert("请先登录！");
					window.location.href = "${ctx}/accountLogin/login/show";
				}else if(data == "becomeFinancial"){
					$.ajax({
				   		url:"${ctx}/financial/beLender/checkBalanceAmount",
				   		type:"POST",
				   		dataType: "json",
				   		success: function(response){
			   			if(response.status=="true"){
			   				// 跳转到成为理财人页面
							alert("您还不是理财人，跳转到成为理财人页面");
			   				window.location.href="${ctx}/financial/beLender/getLoginInfo";
			   			}else{
			   				alert("余额不足"+response.id5Fee+"元，请先充值。");
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
</script>
			<div class="top">
			<span class="fl">&nbsp;&nbsp;</span><a href="http://old.onlinecredit.cn/" class="fl">返回旧版</a>
			<span class="fl">&nbsp;&nbsp;|&nbsp;&nbsp;</span><a href="http://weibo.com/onlinecredit" class="fl" target="_blank">新浪官方微博</a>
			<%if(null != session.getAttribute("curr_login_user")){
			   AccountUsers user = (AccountUsers) session.getAttribute("curr_login_user");
			   String loginName = user.getLoginName();
			%>
	<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount"><%=loginName%></a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=message">消息</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="${ctx}/accountLogin/login/login_out">退出登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="javascript:AddFavorite('http://www.onlinecredit.cn','证大e贷');">加入收藏</a>&nbsp;&nbsp;
				<%}else{%>
	<a href="${ctx}/accountLogin/login/show">登录</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="${ctx}/register/register/creatUser">免费注册</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=1&sort=使用帮助&c_sort=常见问题">帮助中心</a>&nbsp;&nbsp;|&nbsp;&nbsp;
	<a href="javascript:AddFavorite('http://www.onlinecredit.cn','证大e贷');">加入收藏</a>&nbsp;&nbsp;

						<%}%>
						</div>
<div class="header">
	<h1>
		<a href="${ctx}">logo</a>
	</h1>
	<div class="nav_box">
		<ul class="nav">
			<li>
				<a href="${ctx}" class="home"></a>
			</li>
			<li>
				<a href="${ctx}/financial/searchLoan/showLoan" class="invest"></a>
			</li>
			<li>
				<a href="${ctx}/borrowing/releaseLoan/show" class="loan"></a>
			</li>
			<li>
				<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount&token=${sessionScope.token}" class="account"></a>
			</li>
			<li>
				<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=7&category=首页&sort=安全保障&c_sort=风险金代偿" class="security"></a>
			</li>
			<li>
				<a href="${ctx}/footerDetail/footerDetail/showBbs" class="bbs" target="_blank"></a>
			</li>
		</ul>
		<div class="clear"></div>
		<div class="sub_nav">
			
		
			<span class="sub_home">
				<a href='${ctx}/footerDetail/footerDetail/showTwoGradePage?id=2&category=首页&sort=使用帮助&c_sort=平台原理'>平台原理</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=3&category=首页&sort=使用帮助&c_sort=政策法规">政策法规</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=11&category=首页&sort=咨询说明&c_sort=费用标准">费用说明</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=6&category=首页&sort=使用帮助&c_sort=使用技巧">使用技巧</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/footerDetail/footerDetail/showStoryList?type=22">微金融故事</a>
			</span>
			<span class="sub_invest">
				<a href="${ctx}/financial/searchLoan/showLoan">查找借款</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="#" onclick="checkBalanceAmount()">成为理财人</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/toolsBox/calculator/show">工具箱</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=5&category=首页&sort=使用帮助&c_sort=如何理财">如何理财</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay">充值</a>
			</span>
			<span class="sub_loan"><a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=4&category=首页&sort=使用帮助&c_sort=如何借款">如何借款</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/borrowing/borrowing/showUpdataInfo">e贷认证</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/borrowing/releaseLoan/show">发布借款</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/toolsBox/calculator/show">工具箱</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/footerDetail/footerDetail/showStoryList?type=22">成功故事</a>
			</span>
			<span class="sub_account"><a
				href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=myAccount&token=${sessionScope.token}">我的账户</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=fundDetail">资金管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=repay">借款管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=investment">理财管理</a>&nbsp;&nbsp;|&nbsp;&nbsp;
				<a href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=person">个人设置</a>
			</span>
			<span class="sub_security"><a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=7&category=首页&sort=安全保障&c_sort=风险金代偿">风险金代偿</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=8&category=首页&sort=安全保障&c_sort=风险管控">风险管控</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=9">交易安全保障</a>&nbsp;&nbsp;|&nbsp;&nbsp;<a
				href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=10&category=首页&sort=安全保障&c_sort=网络安全保障">网络安全保障</a>
			</span>
		</div>
	</div>
</div>