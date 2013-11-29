<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<title>huoqu</title>
<script type="text/javascript">
$(function(){
    	//顶部菜单导航切换
	$(".nav").find("a").hover(function() {
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
	if((gourl.indexOf("infoApproveNew/showIdentity")!=-1) && ${!empty checkIdCard} && ${!checkIdCard}){
		alert("请先进行身份证验证！");
		window.location.href="${ctx}/financial/beLender/getLoginInfo";
	}else{
		$.ajax({
		    url: gourl,
			wd: wd,
	        hg: hg,
			dataType: "html",
			success: function(html) {	
			
				 $.fancybox(html,{modal:true,autoDimensions:false,width:wd,height:hg,padding:0,margin:0,centerOnScroll:true});
			}
		})
	}
	
};
//关闭弹出层
function closefancy(){
    $.fancybox.close();
};
function loadPage(gourl){
window.location.href=gourl;
}
</script>
<div class="content">
	<div class="loan_l">
		<ul>
			<li><a onclick="loadPage('${ctx}/borrowing/borrowing/showApprove')">上传资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>
			<li class="on"><a onclick="loadPage('${ctx}/borrowing/borrowing/getCreditLimitJsp')">获取额度</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>
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
		<div class="pro_cre">
			恭喜，您获得的信用等级为 <c:if test="${creditNote.creditGrade == 1}">
						<img src="${ctx}/static/images/img28.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 2}">
						<img src="${ctx}/static/images/img27.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 3}">
						<img src="${ctx}/static/images/img26.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 4}">
						<img src="${ctx}/static/images/img25.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 5}">
						<img src="${ctx}/static/images/img24.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 6}">
						<img src="${ctx}/static/images/img23.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 7}">
						<img src="${ctx}/static/images/img22.gif" />
					</c:if> <c:if test="${empty creditNote.creditGrade}">
						0
					</c:if> 级，信用额度为 <span class="col_r2">
					             ${creditNote.creditAmount}
				               <c:if test="${empty creditNote.creditAmount}">
						          0
					           </c:if>
					           </span> 元！
		</div>
		<div class="cer_optional">
			<div class="cer_optional_t attest_cre_t"></div>
			<table class="cer_optional_c">
			  <tr>
					<td width="30%" class="bold1">身份认证</td>
					<c:if test="${appro.shenfenzheng.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shenfenzheng.proStatus==1 && appro.shenfenzheng.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shenfenzheng.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shenfenzheng.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shenfenzheng.proStatus==1 &&appro.shenfenzheng.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shenfenzheng.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				 <tr>
					<td width="30%" class="bold1">工作认证</td>
					<c:if test="${appro.gongzuozheng.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.gongzuozheng.proStatus==1 && appro.gongzuozheng.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gongzuozheng.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.gongzuozheng.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gongzuozheng.proStatus==1 &&appro.gongzuozheng.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.gongzuozheng.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				 <tr>
					<td width="30%" class="bold1">信用报告认证</td>
					<c:if test="${appro.xinyong.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.xinyong.proStatus==1 && appro.xinyong.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xinyong.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.xinyong.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xinyong.proStatus==1 &&appro.xinyong.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.xinyong.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				 <tr>
					<td width="30%" class="bold1">收入认证</td>
					<c:if test="${appro.shouru.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shouru.proStatus==1 && appro.shouru.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shouru.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shouru.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shouru.proStatus==1 &&appro.shouru.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shouru.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">房产认证</td>
					<c:if test="${appro.fangchan.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.fangchan.proStatus==1 && appro.fangchan.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.fangchan.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.fangchan.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.fangchan.proStatus==1 &&appro.fangchan.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.fangchan.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">购车认证</td>
					<c:if test="${appro.gouche.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gouche.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.gouche.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.gouche.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">结婚认证</td>
					<c:if test="${appro.jiehun.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.jiehun.proStatus==1 && appro.jiehun.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jiehun.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.jiehun.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jiehun.proStatus==1 &&appro.jiehun.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.jiehun.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">学历认证</td>
					<c:if test="${appro.xueli.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.xueli.proStatus==1 && appro.xueli.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xueli.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.xueli.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.xueli.proStatus==1 &&appro.xueli.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.xueli.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">居住地认证</td>
					<c:if test="${appro.juzhudi.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.proStatus==1 && appro.juzhudi.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.juzhudi.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.juzhudi.proStatus==1 &&appro.juzhudi.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.juzhudi.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">技术职称认证</td>
					<c:if test="${appro.jishu.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.jishu.proStatus==1 && appro.jishu.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jishu.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.jishu.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.jishu.proStatus==1 &&appro.jishu.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.jishu.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">视频认证</td>
					<c:if test="${appro.shipin.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shipin.proStatus==1 && appro.shipin.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shipin.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shipin.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shipin.proStatus==1 &&appro.shipin.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shipin.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">手机实名认证</td>
					<c:if test="${appro.shoujishiming.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shoujishiming.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shoujishiming.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">微博认证</td>
					<c:if test="${appro.weibo.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.weibo.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.weibo.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.weibo.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
				<tr>
					<td width="30%" class="bold1">实地认证</td>
					<c:if test="${appro.shidi.proStatus==0}">
						<td width="22%" class="bold1 col_r1">5分</td>
					    <td width="28%">待上传</td>
					    <td width="15%" class="tc"><a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp','725','450')">上传资料</a></td>
					</c:if>
					<c:if test="${appro.shidi.proStatus==1 && appro.shidi.reviewStatus==2}">
						<td width="22%" class="bold1 col_r1">5分</td>
						<td width="28%">审核中</td>
						<td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shidi.reviewStatus==1}">
						<td width="22%" class="bold1 col_r3">${appro.shidi.creditScore}分</td>
					    <td width="28%">审核通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp','725','450')">补充上传</a></td>
					</c:if>
					<c:if test="${appro.shidi.proStatus==1 &&appro.shidi.reviewStatus==0}">
						<td width="22%" class="bold1 col_r3">${appro.shidi.creditScore}分</td>
					    <td width="28%">审核未通过</td>
					    <td width="15%" class="tc"><a href="#">查看</a> | <a href="#" onclick="showfancyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp','725','450')">补充上传</a></td>
					</c:if>
				</tr>
			</table>
		</div>
	</div>
</div>
