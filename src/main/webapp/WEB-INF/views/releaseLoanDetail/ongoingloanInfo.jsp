<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /><html>
<title>登录</title>
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css">
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
</script>
<script type="text/javascript">
function notice(){
	alert("您有一笔借款正在进行中，暂时不能进行发布新的借款和上传资料！");
}
</script>
	<div class="content">
	<div class="loan_l">
		<ul>
			<li><a onclick="notice()">上传资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>
			<li class="on"><a onclick="notice()">发布借款</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>	
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
	<div class="loaning_prompt">提示：您当前已有一笔正在处理中的借款，待处理完成后才可发布第二笔借款！</div>
		<table class="loaning_table">
			<tr><th colspan="5">正在进行中的借款</th></tr>
			<c:forEach items="${loanInfoList.loanInfoList}"var="loanInfo" >
			<tr>
				<td width="100" class="tc">
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
				<td width="350"><a href="${ctx}/borrowing/releaseLoan/redirectLoanInfo?loanId=${loanInfo.loanId}"><span class="col2 font_14">${loanInfo.loanTitle }</span></a><br />借款金额：<span class="col1">${loanInfo.amount }</span>   年利率：<span class="col1">${loanInfo.rate }</span>   借款期限： <span class="col1">${loanInfo.loanDuration}个月</span></td>
				<td width="150" class="tc"><div class="raise_s4"><span style="width: ${loanInfo.speedProgress};"><em style="width:${loanInfo.speedProgress};"></em></span></div>${loanInfo.speedProgress}已完成，${loanInfo.bidNumber}笔投标</td>
				<td width="150">${loanInfo.releaseDateStr} ${loanInfo.releaseTimeStr}</td>
				<td width="50">
				 <c:if test="${loanInfo.status==1}">投标中</c:if>
								<c:if test="${loanInfo.status==2}">已满标</c:if>
								<c:if test="${loanInfo.status==3}">流标</c:if>
								<c:if test="${loanInfo.status==4}">还款中</c:if>
								<c:if test="${loanInfo.status==5}">成功</c:if>
								<c:if test="${loanInfo.status==6}">逾期</c:if>
								<c:if test="${loanInfo.status==7}">高级逾期</c:if>
								<c:if test="${loanInfo.status==8}">待审核</c:if>
				</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	
	</div>