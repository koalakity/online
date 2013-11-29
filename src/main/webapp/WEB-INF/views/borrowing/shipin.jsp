<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	function submintAuditshipin(){
		$.ajax({
              data:$("#shipin").serialize(),
	       url: "${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApprove",
	       type: "POST",
	       dataType: 'text',
		   success: function(data){
		     if(data=="true"){
		   			alert("保存成功.");
		   			closefancy();
					window.top.location.reload();
		     }else{
	                   window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                }
		   	}
		});
	}
function closeVedio(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/shipin_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.只有通过视频认证您才能获得贷款；</p>
			<p>2.您可将视频认证文件（格式RMVB、WMV或 AVI）发送至edaiservice@zendaimoney.com；</p>
			<p>3.您可以联系右侧的在线客服进行视频确认。</p>
		</div>
		<form id="shipin" name="shipin" method="post">
		<input type="hidden" name="VideoToken" id="VideoToken" value="${sessionScope.token}"/>
		<table class="upload_c">
			<tr height="150">
				<td class="tc font_14"><input id="VideoApprove" name="VideoApprove" value="1" checked="checked" type="checkbox" /> 视频认证文件已发送</td>
			</tr>
					<c:choose>
						<c:when test="${way=='0'}">
						   <tr>
								<td colspan="2">
								    <input type="button" onclick="closeVedio()" class="btn_r1 fr" value="关闭"/>
								</td>
								<td>
								  &nbsp;
								</td>
							</tr>
						</c:when>
						<c:otherwise>
                          <tr>
				             <td class="tc">
				                <input type="button" onclick="closeVedio()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
				                <input type="button" onclick="submintAuditshipin()" class="btn_r1" value="提交审核" />
				             </td>
			               </tr>
						</c:otherwise>
					</c:choose>

		</table>
		</form>
	</div>
</div>
