<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#weibo'),"true");
});
function submintAuditweibo() {
    if ($.mValidate($('#weibo'), "", "true")) {
        $.ajax({
            data: $("#weibo").serialize(),
            url: "${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApprove",
            type: "POST",
            dataType: 'text',
            success: function(data) {
                if (data == "true") {
                    alert("保存成功.");
                    closefancy();
                    window.top.location.reload();
                }else{
	                   window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                }
            }
        });
    } else {
        return false;
    };
}
function closeBlog(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/weibo_t.jpg" /><a class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请访问http://e.weibo.com/onlinecredit，把证大e贷加为关注；</p>
			<p>2.在微博上，转发一条最新的证大e贷的微博。</p>
		</div>
		<form id="weibo" name="weibo" method="post">
		<input type="hidden" name="sinaWeiboToken" id="sinaWeiboToken" value="${sessionScope.token}"/>
		<table class="upload_c">

				<c:choose>
				    <c:when test="${way=='0'}">
				    	<tr>
							<td class="td1">您的新浪微博账号</td>
							<td class="td2">
								<input maxlength="50" id="sinaWeiboAccount" name="sinaWeiboAccount" value="${userInfo.sinaWeiboAccount}" type="text" class="input_text1" disabled="disabled"/>
							</td>
							<td>&nbsp;</td>
						</tr>
						<tr>
						    <td colspan="2">
						        <input type="button" onclick="closeBlog()" class="btn_r1 fr" value="关闭"/>
						    </td>
						    <td>
						        &nbsp;
						    </td>
						</tr>
				    </c:when>
				    <c:otherwise>
						<tr>
							<td class="td1"><span class="col_r4">*</span>您的新浪微博账号</td>
							<td class="td2">
								<input maxlength="50" id="sinaWeiboAccount" name="sinaWeiboAccount" value="${userInfo.sinaWeiboAccount}" type="text" class="input_text1 chars100 required" />
							</td>
							<td><label class="mvalidatemsg"></label></td>
						</tr>
				        <tr>
				            <td colspan="2">
				                <input type="button" onclick="closeBlog()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
				                <input type="button" onclick="submintAuditweibo()" class="btn_r1 fr" value="提交审核"/>
				            </td>
				            <td>
				                &nbsp;
				            </td>
				        </tr>
				    </c:otherwise>
				</c:choose>
		</table>
		</form>
	</div>
</div>
