<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript">
$(function(){
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function(){
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_2c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(m1).show().siblings().hide();
	});
	//关闭收件箱回复框
	$("a.close").click(function(){
		$(this).parent(".msg_area").slideUp();
		return false;
	});
	//收件箱回复框显示隐藏切换
	$(".td6").find(".hf").click(function(){
		alert("1");
		$(this).parents("tr").next("tr").find(".msg_area").slideToggle();
		return false;
	})
	
	//加载不同的内容
	$(".account_r_c_t").find("h3").click(function(){
		$.ajaxSetup( {
			cache : false
			//关闭AJAX相应的缓存 
		});
		var m = $(this).index() + 1;

		if (m == 1) {
			$(".account_r_c_2c").load(
					"${ctx}/message/message/showXtxx?pager.offset=0",
					function(responseText, textStatus, XMLHttpRequest) {
						$(".account_r_c_2c").html(responseText);
					});
		}
		if (m == 2) {
			
			$(".account_r_c_2c").load(
					"${ctx}/message/message/showSxx?pager.offset=0",
					function(responseText, textStatus, XMLHttpRequest) {
						$(".account_r_c_2c").html(responseText);
					});
		}
		if (m == 3) {
			$(".account_r_c_2c").load(
					"${ctx}/message/message/showFxx?pager.offset=0",
					function(responseText, textStatus, XMLHttpRequest) {
						$(".account_r_c_2c").html(responseText);
					});
		}
		
		
		var m1 = ".account_r_c_2c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(m1).show().siblings().hide();
	});
	
	/*var xtxx = document.getElementById("xtxx");
	var sxx = document.getElementById("sxx");
	var fxx = document.getElementById("fxx"); 
	xtxx.innerText = "系统消息(${returnVo.systemItems})";
	sxx.innerText = "收件箱(${returnVo.inboxItems})";
	fxx.innerText = "发件箱(${returnVo.items})";*/
	$("#xtxx").html("系统消息(${returnVo.systemItems})");
	$("#sxx").html("收件箱(${returnVo.inboxItems})");
	$("#fxx").html("发件箱(${returnVo.items})");
	
});

//分页
function fenye(url){
	$.ajaxSetup( {
		cache : false
		//关闭AJAX相应的缓存 
	});
	$(".account_r_c_2c").load(
	url,
	function(responseText, textStatus, XMLHttpRequest) {
		$(".account_r_c_2c").html(responseText);
	});
};
</script>
<script type="text/javascript">
<!--
	//全选
	$("#sysCheckAll").click(function() {
		if($("#sysCheckAll").attr("checked")){
			$(".sysMsgCheck").attr("checked","checked");
		}else{
			$(".sysMsgCheck").removeAttr("checked","checked");
		}
	});
	
	//多选删除
	$(".sysDelCheck").click(function() {
		var del = confirm("是否确定删除？");
		if(del){
		var n = $(".sysMsgCheck:checked");
		var msgids= "";
        for ( var i=0; i< n.length ; i++ )
        {
            msgids = msgids+n[i].value+",";
        }

		if(msgids!=""){
			$(".account_r_c_2c").load(
					"${ctx}/message/message/sysDelCheck?msgIds="+msgids,
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
	
//删除一个
function sysDelOne(letterId){
		var del = confirm("是否确定删除？");
		if(del){
			$(".account_r_c_2c").load(
					"${ctx}/message/message/sysDelOne?msgId="+letterId,
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
	
//-->
</script>


			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on" id="xtxx">系统消息()</h3>
					<h3 id="sxx">收件箱()</h3>
					<h3 id="fxx">发件箱()</h3>
					<a href="${ctx}/myAccount/myAccount/notifySetRedirect?strUrlType=myAccount" class="fr">设置通知提醒</a>
				</div>
				
				<div class="account_r_c_2c">
				
	<table>
	<c:if test="${sysMsgNum=='0'}">
	  <div style="text-align:center;padding-top:25px;">没有系统消息。</div>
	</c:if>
		<pg:pager url="${ctx}/message/message/showXtxx"
			items="${fn:length(sysMsgList)}" index="center" maxPageItems="10"
			maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
			scope="request">
			<pg:param name="index" />
			<pg:param name="maxPageItems" />
			<pg:param name="maxIndexPages" />
			<ex:searchresults>
	    <c:if test="${sysMsgNum!='0'}">
	       <tr>
			<th>
				<input type="checkbox" id="sysCheckAll"/>
			</th>
			<th class="tl" colspan="2">
				全选
			</th>
			<th>
				<a href="#" class="sysDelCheck">删除</a>
			</th>
		</tr>
	   
		<c:forEach items="${sysMsgList}" var="sysMsg">
		<pg:item>
			<tr>
				<td class="td4">
					<input type="checkbox" class="sysMsgCheck" value="${sysMsg.msgId}"/>
				</td>
				<td class="td5">
					<img src="${ctx}/static/images/message.jpg" class="person" />
					<br />
					系统消息
				</td>
				<td class="tl">
					${sysMsg.msgContent}
					<br />
					消息时间：${sysMsg.sendTime}
				</td>
				<td class="td6">
					<a href="#" onclick="sysDelOne('${sysMsg.msgId}')">删除</a>
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
				
			</div>
