<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ page import="java.util.List,com.zendaimoney.online.entity.stationMessage.StationMessageStationLetter,java.math.BigDecimal;"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
	<head>
		<link rel="stylesheet" href="${ctx}/static/css/loan.css"
			type="text/css" />
		<title></title>

		
		<script type="text/javascript">
$(function() {
	$(".nav").find("a").hover(function() {
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	$(".loan_detail_l").find("dd").click(
			function() {
				var m = $(this).index();
				var m1 = ".loan_detail_r2_c" + m;
				$(this).addClass("on").siblings("dd").removeClass("on");
				$(".loan_detail_r2_t").find("h3").eq(m - 1).addClass("on")
						.siblings().removeClass("on");
				$(m1).show().siblings().hide();
			});
	$(".loan_detail_r2_t").find("h3").click(
			function() {
				var m = $(this).index() + 1;
				var m1 = ".loan_detail_r2_c" + m;
				$(this).addClass("on").siblings().removeClass("on");
				$(".loan_detail_l").find("dd").eq(m - "1").addClass("on")
						.siblings("dd").removeClass("on");
				$(m1).show().siblings().hide();
			});
	$("a.close").click(function() {
		$(this).parent(".msg_area").hide();
		return false;
	});
	$(".msg").find(".hf").click(function() {
		$.ajax( {
			url : "${ctx}/borrowing/releaseLoan/checkPower?loanId="+${loanId},
			 type: "POST",
			dataType : "text",
			error: function(data){
				//window.location.href = "${ctx}/accountLogin/login/show";
	        },
			success : function(data) {
				if(data == "true"){
				}else {
					alert("通过实名绑定的借款人和理财人才能留言和回复。");
					return;
				}
			}
		});
			
		$(this).next(".msg_area").toggle();
		return false;
	})
});

</script>
	</head>
	<body>
		<div class="wrapper">
			<div class="content">
				<div class="loan_detail_r">
					<div class="loan_detail_r3">
						<div class="loan_detail_r3_t">
							留言板
						</div>
						<div class="loan_detail_r3_c">
						<form:form id="form1" name="form1">
							
							<textarea id="message" name="message" class="textarea1"></textarea>
							<input type="hidden" id="copymessage" name="copymessage">
							<br>
							<div id="errordiv" style="display:none">
								<label id="errorinfolabel" class="error" for="message" generated="true">
									通过实名绑定的借款人和理财人才能留言。
								</label>
							</div>
							
							（字数限制：500字以内）
							<input type="button"   onclick="leaveMessage()" class="btn5" value="留言" />
						</form:form>
							<ul>
								<%
								String path = request.getContextPath();
								List<StationMessageStationLetter> msgList = (List<StationMessageStationLetter>)request.getAttribute("msgs");
								StringBuffer strHtml = new StringBuffer();
								for(int i=0;i<msgList.size();i++){
									StationMessageStationLetter msg = msgList.get(i);
									if(msg.getParentId().equals(new BigDecimal("0"))){
										strHtml.append("<li>");
										strHtml.append("<img class=\"person fl\" width=\"60\" height=\"60\" src=\"/pic/"+msg.getSenderId().getStationMessageUserInfoPerson().getHeadPath()+"\">");
										strHtml.append("<div class=\"msg\">");
										strHtml.append("<p><span class=\"fr col3\">"+new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(msg.getSenderTime())+"</span>"+msg.getSenderId().getLoginName()+"</p>");
										strHtml.append("<div class=\"meg_c\">"+msg.getMessage()+"</div>");
										//TODO
										strHtml.append("<form id=\"formmsg"+i+"\">");
										//strHtml.append("<input type=\"hidden\" id=\"loanId\" name=\"loanId\" value=\"973\" />");
										strHtml.append("<input type=\"hidden\" id=\"receiverId\" name=\"receiverId\" value=\""+msg.getSenderId().getUserId()+"\" />");
										strHtml.append("<input type=\"hidden\" id=\"letterId\" name=\"letterId\" value=\""+msg.getLetterId()+"\" />");
										strHtml.append("<a href=\"#\" class=\"hf\">回复</a>");
										strHtml.append("<div class=\"msg_area\"><a href=\"#\" class=\"close\">×</a><span class=\"mar8\">(不超过500字)</span><textarea id=\"message"+i+"\" name=\"message\" class=\"textarea1\"></textarea><input onclick=\"reply("+i+","+msg.getLetterId()+","+msg.getSenderId().getUserId()+")\" type='button' class=\"btn1 mar8\" value=\"马上发表\" /></div>");
										strHtml.append("</form>");
										for(int k=i+1; k<msgList.size(); k++){
											if(k < msgList.size()){
												StationMessageStationLetter slc = msgList.get(i);
												StationMessageStationLetter slc2 = msgList.get(k);
												if(!slc2.getParentId().equals(new BigDecimal("0"))){
													if(slc.getLetterId().equals(slc2.getParentId())){
														strHtml.append("<div class=\"msg_box\">");
														strHtml.append("<p><span class=\"fr col3\">"+slc2.getSenderTime()+"</span>"+slc2.getSenderId().getLoginName()+"</p>");
														strHtml.append("<div class=\"meg_c\">@:"+slc2.getReceiverId().getLoginName()+":"+slc2.getMessage()+"</div>");
														strHtml.append("</div>");
														strHtml.append("</br>");
													}else {
														i = k-1;
														break;
													}
												}else{
													i = k-1;
													break;
												}
											}else {
												i = k-1;
												break;
											}
										}
										
										strHtml.append("</div>");
										strHtml.append("</li>");
										//out.println("<h1>"+sl.getParentId()+"</h1>");
									}else{
										break;
									}
								}
								out.println(strHtml.toString());
								 %>
							</ul>
							<div class="clear"></div>
							<p class="pagelist">
								
							</p>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>