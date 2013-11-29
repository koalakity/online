<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
$(function() {
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	//关闭收件箱回复框
	$("a.close").click(function() {
		$(this).parent(".msg_area").slideUp();
		return false;
	});
	//收件箱回复框显示隐藏切换
	$(".td6").find(".hf").click(function() {
		$(this).parents("tr").next("tr").find(".msg_area").slideToggle();
		return false;
	});
	
	//全选
	$("#checkAll").click(function() {
		if($("#checkAll").attr("checked")){
			$(".msgCheck").attr("checked","checked");
		}else{
			$(".msgCheck").removeAttr("checked","checked");
		}
	});
	
	//多选删除
	$(".delCheck").click(function() {
		var del = confirm("是否确定删除？");
		if(del){
		var n = $(".msgCheck:checked");
		var msgids= "";
        for ( var i=0; i< n.length ; i++ )
        {
            msgids = msgids+n[i].value+",";
        }
		if(msgids!=""){
			$(".account_r_c_2c").load(
					"${ctx}/message/message/delCheck?msgIds="+msgids,
					function(responseText, textStatus, XMLHttpRequest) {
						$.ajaxSetup( {
							cache : false
							//关闭AJAX相应的缓存 
						});
						$(".account_r_c_2c").html(responseText);
						$.ajaxSetup( {
							cache : false
							//关闭AJAX相应的缓存 
						});
					});
			
		}else{
			alert("请您选择要删除的信息.");
		}
	}
	});
	
	//设置头部数量
	//var sxx = document.getElementById("sxx");
	//var fxx = document.getElementById("fxx"); 
	$("#xtxx").html("系统消息(${returnVo.systemItems})");
	$("#sxx").html("收件箱(${returnVo.inboxItems})");
	$("#fxx").html("发件箱(${returnVo.items})");
	
});
//删除一个
function delOne(letterId){
		var del = confirm("是否确定删除？");
		if(del){
			$(".account_r_c_2c").load(
					"${ctx}/message/message/delOne?letterId="+letterId,
					function(responseText, textStatus, XMLHttpRequest) {
						$.ajaxSetup( {
							cache : false
							//关闭AJAX相应的缓存 
						});
						$(".account_r_c_2c").html(responseText);
						$.ajaxSetup( {
							cache : false
							//关闭AJAX相应的缓存 
						});
					});
	}

}
//马上发表
function sendMsg(index){
var msg = document.getElementById("message"+index);
var senderId = document.getElementById("senderId"+index);
var receiverId = document.getElementById("receiverId"+index);
/*alert(msg.value);
alert(senderId.value);
alert(receiverId.value);*/
if(msg.value==null || msg.value==""){
alert("回复内容不能为空.");
}else{
	if(msg.value.length>500){
		alert("回复内容最多500字.");
	}else{
	
		$(".account_r_c_2c").load(
			"${ctx}/message/message/sendMsg?msg="+encodeURI(encodeURI(msg.value))+"&senderId="+senderId.value+"&receiverId="+receiverId.value,
			function(responseText, textStatus, XMLHttpRequest) {
				$(".account_r_c_2c").html(responseText);
			});
	}
}

}
</script>
<div class="account_r_c_2c2">
	<table>
	   <c:if test="${msgNum=='0'}">
	     <div style="text-align:center;padding-top:25px;">没有短消息。</div>
	   </c:if>
		<pg:pager url="${ctx}/message/message/showSxx"
			items="${fn:length(msgList)}" index="center" maxPageItems="10"
			maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
			scope="request">
			<pg:param name="index" />
			<pg:param name="maxPageItems" />
			<pg:param name="maxIndexPages" />
		<ex:searchresults>
		 <c:if test="${msgNum!='0'}">
	       <tr>
			<th>
				<input type="checkbox" id="checkAll" value="ffff"/>
			</th>
			<th class="tl" colspan="2">
				全选
			</th>
			<th>
				<a href="#" class="delCheck">删除</a>
			</th>
		</tr>
		
		<c:forEach items="${msgList}" var="msg" varStatus="s">
		<pg:item>
			<tr>
				<td class="td4">
					<input type="checkbox" id="msgId" name="msgId" class="msgCheck" value="${msg.letterId}"/>
				</td>
				<td class="td5">
					<!-- <img src="${ctx}/static/images/${msg.senderId.userInfoPerson.headPath}" class="person" />-->
					 <c:if test="${empty msg.senderId.userInfoPerson.headPath}" >
                         <img src="${ctx}/static/images/nophoto.jpg" id="person" class="person" />  
                      </c:if> 
                     <c:if test="${not empty  msg.senderId.userInfoPerson.headPath}" >
                          <img src="/pic/${msg.senderId.userInfoPerson.headPath}" class="person"/>
                     </c:if> 
				</td>
				<td class="tl">
					<!-- <a href="online_account2.html">-->${msg.senderId.loginName}</a><span
						class="col3 mar2">${msg.sendDateStr}</span>
					<br />
					${msg.message}
				</td>
				<td class="td6">
					<a href="#" class="hf">回复</a>&nbsp;&nbsp;
					<a href="#" onclick="delOne('${msg.letterId}')">删除</a>
				</td>
			</tr>
			<tr>
				<td class="msg" colspan="4">
					<div class="msg_area">
						<input type="hidden" id="senderId${s.index}" value="${msg.receiverId.userId}">
						<input type="hidden" id="receiverId${s.index}" value="${msg.senderId.userId}">
						<a href="#" class="close">×</a><span>(不超过500字)</span>
						<textarea id="message${s.index}"></textarea>
						<input type="button" class="btn1" value="马上发表" onclick="sendMsg('${s.index}')"/>
					
					</div>
				</td>
			</tr>
		</pg:item>
		</c:forEach>
		</c:if>	
		</ex:searchresults>
			<table border=0 cellpadding=0 width=10% cellspacing=0>
				<tr align=center valign=top>
					<td valign=bottom>
						<pg:index export="total=itemCount">
							<font face=arial,sans-serif size=-1 class="pagelist"> <pg:first>
				    	  共<%=total%>条&nbsp;<a href="#"
										onclick="fenye('<%=pageUrl%>')">首页</a>
								</pg:first> <pg:prev>&nbsp;<a href="#"
										onclick="fenye('<%=pageUrl%>')">上一页</a>
								</pg:prev> <pg:pages>
									<%
										if (pageNumber.intValue() < 10) {
									%>&nbsp;<%
										}
													if (pageNumber == currentPageNumber) {
									%><b><%=pageNumber%></b>
									<%
										} else {
									%><a href="#" onclick="fenye('<%=pageUrl%>')"><%=pageNumber%></a>
									<%
										}
									%>
								</pg:pages> <pg:next>&nbsp;<a href="#"
										onclick="fenye('<%=pageUrl%>')">下一页</a>
								</pg:next> <pg:last>
									<a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    </pg:last> <br>
							</font>
						</pg:index>
					</td>
				</tr>
			</table>
		
		</pg:pager>
	</table>
</div>




<div class="clear"></div>