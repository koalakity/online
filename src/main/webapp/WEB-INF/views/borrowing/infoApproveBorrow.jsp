<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<!--[if lte IE 6]>
<script type="text/javascript" src="/online_main/static/js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix(".loan_r img");
</script>
<![endif]-->
<script type="text/javascript">
$(function(){
//顶部菜单导航切换
	    $(".nav").find("a").hover(function(){
			var m = $(this).parent().index();
			$(".sub_nav").find("span").eq(m).show().siblings().hide();
		});
	//table斑马线
	$(".cer_optional_c").find("tr:odd").css("background","#fcfbfb");
	//灯泡
	var imgsrc;
	$(".loan_r_person_r").find("img").hover(
	 		function(){
	 		imgsrc = $(this).attr("src");
			$(this).attr("src","${ctx}/static/images/bulb_hover.png");
			if(this.id=='identityImg'){
			  showTip($(this),"身份认证","${userProStatus.shenfenzheng}");
			}else if(this.id=='workImg'){
			  showTip($(this),"工作认证","${userProStatus.gongzuozheng}");
			}else if(this.id=='creditImg'){
			 showTip($(this),"信用认证","${userProStatus.xinyong}");
			}else if(this.id=='incomeImg'){
			 showTip($(this),"收入认证","${userProStatus.shouru}");
			}else if(this.id=='houseImg'){
			 showTip($(this),"房产认证","${userProStatus.fangchan}");
			}else if(this.id=='carImg'){
			  showTip($(this),"汽车认证","${userProStatus.gouche}");
			}else if(this.id=='marrayImg'){
			 showTip($(this),"结婚认证","${userProStatus.jiehun}");
			}else if(this.id=='educateImg'){
			 showTip($(this),"学历认证","${userProStatus.xueli}");
			}else if(this.id=='addressImg'){
			 showTip($(this),"居住地认证","${userProStatus.juzhudi}");
			}else if(this.id=='technologyImg'){
			  showTip($(this),"技术认证","${userProStatus.jishu}");
			}else if(this.id=='vedioImg'){
			 showTip($(this),"视频认证","${userProStatus.shipin}");
			}else if(this.id=='phoneImg'){
			 showTip($(this),"手机实名认证","${userProStatus.shoujishiming}");
			}else if(this.id=='blogImg'){
			 showTip($(this),"微博认证","${userProStatus.weibo}");
			}else if(this.id=='fieldImg'){
			 showTip($(this),"实地考察认证","${userProStatus.shidi}");
			}
		},
		function(){
			$(this).attr("src",imgsrc);
			$(".bulbtip").remove();
		}
	);
});
//灯泡显示方式
var showTip = function(obj,msg1,msg2){
	var tipBox = $('<div class="bulbtip"><div class="bulbtip-c"><p class="bulbtip-c-l"><strong>'+msg1+'</strong><br />'+msg2+'</p></div><div class="bulbtip-t"></div></div>');
	if(msg1!=""){
		tipBox.appendTo($("body")).css({
			"left" : obj.offset().left,
			"top" : obj.offset().top - 50
		});		
	};
};
//调用弹出层
function showfancyApprove(gourl,wd,hg){
    gourl=gourl+'&isFrame=true';
	//未进行身份证验证
	if((gourl.indexOf("infoApproveNew/showIdentity")!=-1) && ${!empty checkIdCard} && ${!checkIdCard}){
		//账户余额小于5元
		if((gourl.indexOf("infoApproveNew/showIdentity")!=-1) && ${!empty accountBanance} && ${accountBanance==true}){
			alert("需要进行身份证验证。您的可用余额不足${idFee}元，请先充值！");
			window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=pay";
		}else{
			alert("请先进行身份证验证！");
			//大于5
			window.location.href="${ctx}/financial/beLender/getLoginInfo?channel=1";
		}
	}else if((gourl.indexOf("infoApproveNew/showIdentity")!=-1) && ${!empty checkUserPhoneByUserId} && ${!checkUserPhoneByUserId}){
		//手机未验证
		alert("请先进行手机号码绑定！");
		window.location.href="${ctx}/financial/beLender/getLoginInfo?channel=1";
	}else{
		var random = Math.random();
		gourl = gourl +'&random='+random;
		$.ajax({
		    url: gourl,
			wd: wd,
	        hg: hg,
			dataType: "html",
			success: function(html) {	
				if(html&&html=='redirectLogin'){
			      location.href='${ctx}/accountLogin/login/redirectLogin';
			    }else{
			    	var titleValue=document.title;
					$.fancybox(html,
						{
						modal:true,
						autoDimensions:false,
						width:wd,
						height:hg,
						padding:0,
						margin:0,
						centerOnScroll:true}
					);
					setTimeout("document.title='证大e贷 微金融服务平台';",0)
			     	
			    }
			    
			}
		})
	}
};
//关闭弹出层
function closefancy(){
    $.fancybox.close();
    location.href='${ctx}/borrowing/borrowing/showApprove';
};

function loadPage(gourl){
window.location.href=gourl;
}
</script>
<div class="content">
	<div class="loan_l">
		<ul>
			<li class="on"><a onclick="loadPage('${ctx}/borrowing/borrowing/showApprove')">上传资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>
			<li><a onclick="loadPage('${ctx}/borrowing/borrowing/publishLoanJsp')">发布借款</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>	
		</ul>
	</div>
	<div class="loan_r">
		<div class="loan_r_person">
			<div class="loan_r_person_l">
				<div class="person_img_bg">
					<c:if test="${empty userInfos.userInfoPerson.headPath}" >
                      <img src="${ctx}/static/images/nophoto.jpg" id="person" class="person" width="73" height="80"  />  
                     </c:if> 
                     <c:if test="${not empty  userInfos.userInfoPerson.headPath}" >
                       <img src="/pic/${userInfos.userInfoPerson.headPath}" class="person" width="73" height="80" />
                     </c:if> 
				</div>
				<p>昵称：${userInfos.loginName}</p>
				<p>邮箱：${userInfos.email}</p>
				<p>手机号码：${userInfos.userInfoPerson.phoneNo}</p>
				<p>注册时间： ${userInfos.regDateTime}</p>
			</div>
			<div class="loan_r_person_r">
				<table>
					<tr class="tr1">
						<td>身份</td>
						<td>工作</td>
						<td>信用</td>
						<td>收入</td>
						<td>房产</td>
						<td>购车</td>
						<td>结婚</td>
						<td>学历</td>
						<td>居住地</td>
						<td>技术职称</td>
						<td>视频</td>
						<td>手机实名</td>
						<td>微博</td>
						<td>实地</td>
					</tr>
					<tr>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shenfenzheng=='true'}">
		                         <img id ="identityImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="identityImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.gongzuozheng=='true'}">
		                         <img id ="workImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="workImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.xinyong=='true'}">
		                         <img id ="creditImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="creditImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shouru=='true'}">
		                         <img id ="incomeImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="incomeImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.fangchan=='true'}">
		                         <img id ="houseImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="houseImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.gouche=='true'}">
		                         <img id ="carImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="carImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.jiehun=='true'}">
		                         <img id ="marrayImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="marrayImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.xueli=='true'}">
		                         <img id ="educateImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="educateImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.juzhudi=='true'}">
		                         <img id ="addressImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="addressImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.jishu=='true'}">
		                         <img id ="technologyImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="technologyImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shipin=='true'}">
		                         <img id ="vedioImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="vedioImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shoujishiming=='true'}">
		                         <img id ="phoneImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="phoneImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.weibo=='true'}">
		                         <img id ="blogImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="blogImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shidi=='true'}">
		                         <img id ="fieldImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="fieldImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<div class="cer_necessary">
			<div class="cer_necessary_t"></div>
			<ul class="cer_necessary_c">
				<li> 
				   <img src="${ctx}/static/images/revision_img2.jpg" class="img_r1" />
				   <c:if test="${appro.shenfenzheng.proStatus==0}">
						<p><strong>身份证</strong></p>
					    <p><span class="col_r1">待上传</span></p>
					    <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=1','725','450')">上传资料</a></p>
					</c:if>
				    <c:if test="${appro.shenfenzheng.proStatus==1 && appro.shenfenzheng.reviewStatus==2}">
						<img src="${ctx}/static/images/ing.png" class="img_r2" />
						<p><strong>身份证</strong>（${userInfos.identityStr}）</p>
					   <p><span class="col_r1">审核中</span></p>
					   <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.shenfenzheng.reviewStatus==1}">
					  <img src="${ctx}/static/images/pass.png" class="img_r2" />
					  <p><strong>身份证</strong>（${userInfos.identityStr}）</p>
					  <p><span class="col_r1">审核通过</span> <span class="col_r2">+${appro.shenfenzheng.creditScore}</span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.shenfenzheng.proStatus==1 && appro.shenfenzheng.reviewStatus==0}">
					  <p><strong>身份证</strong>（${userInfos.identityStr}）</p>
					  <p><span class="col_r1">审核未通过</span> <span class="col_r2"></span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=1','725','450')">补充上传</a></p>
					</c:if>
				</li>
				<li>
			    	<img src="${ctx}/static/images/revision_img1.jpg" class="img_r1" />
				   <c:if test="${appro.gongzuozheng.proStatus==0}">
						<p><strong>工作认证</strong></p>
					    <p><span class="col_r1">待上传</span></p>
					    <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=1','725','450')">上传资料</a></p>
					</c:if>
				    <c:if test="${appro.gongzuozheng.proStatus==1 && appro.gongzuozheng.reviewStatus==2}">
						<img src="${ctx}/static/images/ing.png" class="img_r2" />
						<p><strong>工作认证</strong>（${jobInfo.workAddStr}）</p>
					   <p><span class="col_r1">审核中</span></p>
					   <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.gongzuozheng.reviewStatus==1}">
					  <img src="${ctx}/static/images/pass.png" class="img_r2" />
					  <p><strong>工作认证</strong>（${jobInfo.workAddStr}）</p>
					  <p><span class="col_r1">审核通过</span> <span class="col_r2">+${appro.gongzuozheng.creditScore}</span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.gongzuozheng.proStatus==1 &&appro.gongzuozheng.reviewStatus==0}">
					  <p><strong>工作认证</strong>（${jobInfo.workAddStr}）</p>
					  <p><span class="col_r1">审核未通过</span> <span class="col_r2"></span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
				</li>
				<li>
					<img src="${ctx}/static/images/revision_img3.jpg" class="img_r1" />
				   <c:if test="${appro.xinyong.proStatus==0}">
						<p><strong>信用报告</strong></p>
					    <p><span class="col_r1">待上传</span></p>
					    <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=1','725','450')">上传资料</a></p>
					</c:if>
				    <c:if test="${appro.xinyong.proStatus==1 && appro.xinyong.reviewStatus==2}">
						<img src="${ctx}/static/images/ing.png" class="img_r2" />
						<p><strong>信用报告</strong></p>
					   <p><span class="col_r1">审核中</span></p>
					    <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.xinyong.reviewStatus==1}">
					  <img src="${ctx}/static/images/pass.png" class="img_r2" />
					 <p><strong>信用报告</strong></p>
					  <p><span class="col_r1">审核通过</span> <span class="col_r2">+${appro.xinyong.creditScore}</span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.xinyong.proStatus==1 &&appro.xinyong.reviewStatus==0}">
					 <p><strong>信用报告</strong></p>
					  <p><span class="col_r1">审核未通过</span> <span class="col_r2"></span></p>
					  <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
				</li>
				<li>
					<img src="${ctx}/static/images/revision_img4.jpg" class="img_r1" />
				   <c:if test="${appro.shouru.proStatus==0}">
						<p><strong>收入认证</strong></p>
					    <p><span class="col_r1">待上传</span></p>
					     <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=1','725','450')">上传资料</a></p>
					</c:if>
				    <c:if test="${appro.shouru.proStatus==1 && appro.shouru.reviewStatus==2}">
						<img src="${ctx}/static/images/ing.png" class="img_r2" />
						<p><strong>收入认证</strong></p>
					   <p><span class="col_r1">审核中</span></p>
					    <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.shouru.reviewStatus==1}">
					  <img src="${ctx}/static/images/pass.png" class="img_r2" />
					 <p><strong>收入认证</strong></p>
					  <p><span class="col_r1">审核通过</span> <span class="col_r2">+${appro.shouru.creditScore}</span></p>
					 <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
					<c:if test="${appro.shouru.proStatus==1 &&appro.shouru.reviewStatus==0}">
					 <p><strong>收入认证</strong></p>
					  <p><span class="col_r1">审核未通过</span> <span class="col_r2"></span></p>
					 <p><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=1','725','450')">补充上传</a></p>
					</c:if>
				</li>
			</ul>
		</div>
		<div class="cer_optional">
			<div class="cer_optional_t"></div>
			<table class="cer_optional_c">
				<tr>
					<td width="30%" class="bold1">房产认证</td>
					<c:if test="${appro.fangchan.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.fangchan.proStatus==1 && appro.fangchan.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.fangchan.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.fangchan.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.fangchan.proStatus==1 &&appro.fangchan.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.fangchan.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">购车认证</td>
					<c:if test="${appro.gouche.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gouche.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.gouche.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.gouche.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">结婚认证</td>
					<c:if test="${appro.jiehun.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.jiehun.proStatus==1 && appro.jiehun.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jiehun.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.jiehun.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jiehun.proStatus==1 &&appro.jiehun.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.jiehun.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">学历认证</td>
					<c:if test="${appro.xueli.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.xueli.proStatus==1 && appro.xueli.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xueli.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.xueli.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xueli.proStatus==1 &&appro.xueli.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.xueli.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">居住地认证</td>
					<c:if test="${appro.juzhudi.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.proStatus==1 && appro.juzhudi.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.juzhudi.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.proStatus==1 &&appro.juzhudi.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.juzhudi.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">技术职称认证</td>
					<c:if test="${appro.jishu.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.jishu.proStatus==1 && appro.jishu.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jishu.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.jishu.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jishu.proStatus==1 &&appro.jishu.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.jishu.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">视频认证</td>
					<c:if test="${appro.shipin.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shipin.proStatus==1 && appro.shipin.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shipin.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shipin.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shipin.proStatus==1 &&appro.shipin.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shipin.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">手机实名认证</td>
					<c:if test="${appro.shoujishiming.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shoujishiming.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shoujishiming.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">微博认证</td>
					<c:if test="${appro.weibo.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.weibo.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.weibo.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.weibo.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">实地认证</td>
					<c:if test="${appro.shidi.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=1','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shidi.proStatus==1 && appro.shidi.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shidi.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shidi.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shidi.proStatus==1 &&appro.shidi.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shidi.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=0','725','450')">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=1','725','450')">补充上传</a></td>
					</c:if>
				</tr>
			</table>
		</div>
	</div>
</div>
