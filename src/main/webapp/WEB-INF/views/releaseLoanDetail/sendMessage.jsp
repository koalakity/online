<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<div class="msg_box width1 height2">
	<div class="msg_t">
		<a onclick="closefancy()" class="msg_close"></a>
		<img src="${ctx}/static/images/img96.gif" />
		发送消息
	</div>
	<div class="msg_c">
		<table>
			<tr><td class="tr">接收用户：</td><td>${realName}</td></tr>
			<tr><td class="tr vt" >内容：</td><td><textarea  id="sendMessageId" class="textarea2"></textarea></td></tr>
		</table>
		<input type="button" class="btn5 btn5_msg" value="发送" onclick="sendMessage()" />
	</div>
</div>