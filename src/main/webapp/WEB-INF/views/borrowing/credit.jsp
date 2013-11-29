<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
function submitAuditCredit() {
    if (parseInt($("#uploadCount").val()) != 0) {
        alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
    } else {
        $.ajax({
            data: $("#creditForm").serialize(),
            url: "${ctx}/infoApproveNew/updataCredit",
            type: "POST",
            dataType: 'text',
            success: function(data) {
                if (data == "true") {
                    alert("保存成功.");
                    closefancy();
                    window.top.location.reload();
                } else if (data == "noUpFile") {
                    alert("未提交附件，请提交附件后再上传！");
                }else{
	                   window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                }
            }
        });
    }
}
function closeCredit(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${ctx}/static/images/xinyong_t.jpg" />
	<a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.个人信用报告是由中央人民银行出据，全面记录个人信用活动，反映个人信用基本状况；</p>
			<p>2.请上传个人信用报告原件照片，每页需独立拍照，并将整份信用报告按页码先后顺序完整上传。</p>
		</div>
		<form id="creditForm" method="post">
		<table class="upload_c">
			<tr>
				<td class="td1"></td>
				<td class="td2"></td>
				<td></td>
			</tr>
				<c:choose>
				    <c:when test="${way=='0'}">
				        <c:forTokens var="filePath" items="${sb}" delims=";" varStatus="status">
				            <tr>
				                <td class="td1">
				                    附件${status.index+1}：
				                </td>
				                <td>
				                    <span>
				                        ${filePath}
				                    </span>
				                </td>
				            </tr>
				        </c:forTokens>
				        <tr>
				            <td colspan="2">
				                <input type="button" onclick="closeCredit()" class="btn_r1 fr" value="关闭"/>
				            </td>
				            <td>
				                &nbsp;
				            </td>
				        </tr>
				    </c:when>
				    <c:otherwise>
				        <tr>
				            <td colspan="2" class="td3">
				                <span class="sp1 fr">
				                    <span class="col_r4">*</span>每张图片最大限制为1.5MB，图片格式为JPG,GIF,PNG&nbsp;
				                </span>
				                <div class="fr">
				                    <jsp:include page="../../../fileUpload.jsp" />
				                </div>
				            </td>
				            <td>
				                &nbsp;
				            </td>
				        </tr>
				        <tr>
				            <td colspan="2">
				                <input type="button" onclick="closeCredit()" class="btn_r1 fr" value="取消" style="margin-left:10px;" />
				                <input type="button" onclick="submitAuditCredit()" class="btn_r1 fr" value="提交审核"/>
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
