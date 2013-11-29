<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#shouji'),"true");
});
function submintAuditshouji() {
    if ($.mValidate($('#shouji'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#shouji").serialize(),
                url: "${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApprove",
                type: "POST",
                dataType: 'text',
                success: function(data) {
                    if (data == "true") {
                        alert("保存成功.");
                        closefancy();
                        window.top.location.reload();
                        //window.location.href='${pageContext.request.contextPath}/borrowing/borrowing/showApprove';
                    } else if (data == "noUpFile") {
                        alert("未提交附件，请提交附件后再上传！");
                    }else{
	                   window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                }
                }
            });
        }
    } else {
        return false;
    };
}
function closePhone(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/shouji_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请上传您绑定的手机号码最近3个月的话费详情原件照片（需清晰显示机主手机号码）；</p>
			<p>2.如话费详情记录较多可分月打印并上传。</p>
		</div>
		<form id="shouji" name="shouji" method="post">
		<table class="upload_c">
	          <c:choose>
						<c:when test="${way=='0'}">
									<tr>
										<td class="td1">姓名</td>
										<td class="td2"><input maxlength="10" id="phoneApproveName" name="phoneApproveName" value="${userInfo.phoneApproveName}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">手机号码</td>
										<td class="td2"><input maxlength="11" id="phoneApproveNo" name="phoneApproveNo" value="${userInfo.phoneApproveNo}" type="text" class="input_text1" disabled="disabled"/></td>
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
										    <input type="button" onclick="closePhone()" class="btn_r1 fr" value="关闭"/>
										</td>
										<td>
										  &nbsp;
										</td>
									</tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="td1"><span class="col_r4">*</span>姓名</td>
									<td class="td2"><input maxlength="10" id="phoneApproveName" name="phoneApproveName" value="${userInfo.phoneApproveName}" type="text" class="input_text1 realname required" /></td>
									<td><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1"><span class="col_r4">*</span>手机号码</td>
									<td class="td2"><input maxlength="11" id="phoneApproveNo" name="phoneApproveNo" value="${userInfo.phoneApproveNo}" type="text" class="input_text1 mobile required" /></td>
									<td><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td colspan="2" class="td3">
											<span class="sp1 fr"><span class="col_r4">*</span>每张图片最大限制为1.5MB，图片格式为JPG,GIF,PNG&nbsp;</span>
											<div class="fr"><jsp:include page="../../../fileUpload.jsp"/></div>
									</td>
									<td>&nbsp;</td>
								</tr>	
									<tr>
										<td colspan="2">
										   <input type="button" onclick="closePhone()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
										   <input type="button" onclick="submintAuditshouji()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>