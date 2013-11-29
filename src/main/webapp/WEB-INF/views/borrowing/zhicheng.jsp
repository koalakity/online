<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#zhicheng'),"true");
});
	function submintAuditzhicheng(){
	 	if($.mValidate($('#zhicheng'),"","true")){
			if (parseInt($("#uploadCount").val()) != 0) {
			    alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
			} else {
			    $.ajax({
			        data: $("#zhicheng").serialize(),
			        url: "${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApprove",
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
		}else{
			return false;
		};
	}
function closeTech(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/jishu_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请上传您的技术职称证书原件照片；</p>
			<p>2.技术职称证书包含国家承认的二级及以上等级证书，如律师证、会计证、工程师证等。</p>
		</div>
		<form id="zhicheng" name="zhicheng" method="post">
		<table class="upload_c">
				<c:choose>
						<c:when test="${way=='0'}">
									<tr>
										<td class="td1">职称一</td>
										<td class="td2"><input maxlength="50" id="jobTitleOne" name="jobTitleOne" value="${userInfo.jobTitleOne}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">职称二</td>
										<td class="td2"><input maxlength="50" id="jobTitleTwo" name="jobTitleTwo" value="${userInfo.jobTitleTwo}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
							     <c:forTokens var="filePath" items="${sb}" delims=";" varStatus="status" >
									<tr>
									<td class="td1">附件${status.index+1}：</td>
									 <td>
		                              <span>${filePath}</span> 
		                             </td>
								    </tr>
		                            </c:forTokens>
								<tr>
								    <td colspan="2">
								        <input type="button" onclick="closeTech()" class="btn_r1 fr" value="关闭"/>
								    </td>
								    <td>
								        &nbsp;
								    </td>
								</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="td1">职称一</td>
									<td class="td2"><input maxlength="50" id="jobTitleOne" name="jobTitleOne" value="${userInfo.jobTitleOne}" type="text" class="input_text1 chars100" /></td>
								</tr>
								<tr>
									<td class="td1">职称二</td>
									<td class="td2"><input maxlength="50" id="jobTitleTwo" name="jobTitleTwo" value="${userInfo.jobTitleTwo}" type="text" class="input_text1 chars100" /></td>
								</tr>
								<tr>
									<td colspan="2" class="td3">
											<span class="sp1 fr">每张图片最大限制为1.5MB，图片格式为JPG,GIF,PNG&nbsp;</span>
											<div class="fr"><jsp:include page="../../../fileUpload.jsp"/></div>
									</td>
									<td>&nbsp;</td>
								</tr>	
									<tr>
										<td colspan="2">
										    <input type="button" onclick="closeTech()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
										    <input type="button" onclick="submintAuditzhicheng()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>
