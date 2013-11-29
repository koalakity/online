<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#incomeForm'),"true");
});
function submitAuditIncome() {
    if ($.mValidate($('#incomeForm'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#incomeForm").serialize(),
                url: "${ctx}/infoApproveNew/updataIncome",
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
}
function closeIncome(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${ctx}/static/images/shouru_t.jpg" />
	<a   class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.可上传个人或企业银行卡近半年流水单的照片或扫描件（须有银行盖章或网银电脑截屏）；</p>
			<p>2.可上传近半年的个人或企业所得税完税凭证；</p>
			<p>3.可上传个人社保卡正反面原件照片或近半年淘宝店支付宝账户明细的网银截图。</p>
		</div>
		<form id="incomeForm" method="post">
		<table class="upload_c">
				<c:choose>
				 <c:when test="${way=='0'}">
								<tr>
								    <td class="td1">
								        月收入
								    </td>
								    <td class="td2">
								        <input type="text" id="monthIncome" name="monthIncome" value="${userInfo.monthIncome}" class="input_text1"  disabled="disabled" />
								    </td>
								    <td>
								        <span class="font_14">
								            元
								        </span>
								    </td>
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
										    <input type="button" onclick="closeIncome()" class="btn_r1 fr" value="关闭" />
										</td>
										<td>&nbsp;</td>
								    </tr>
							</c:when>
							<c:otherwise>
								<tr>
								    <td class="td1">
								        <span class="col_r4">*</span>月收入
								    </td>
								    <td class="td2">
								        <input type="text" id="monthIncome" name="monthIncome" value="${userInfo.monthIncome}" class="input_text1 money required" />
								    </td>
								    <td>
								        <span class="font_14">
								            元
								        </span>
								    </td>
								    <td><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td colspan="2" class="td3">
											<span class="sp1 fr">*每张图片最大限制为1.5MB，图片格式为JPG,GIF,PNG&nbsp;</span>
											<div class="fr"><jsp:include page="../../../fileUpload.jsp"/></div>
									</td>
									<td>&nbsp;</td>
								</tr>
									<tr>
										<td colspan="2">
										   <input type="button" onclick="closeIncome()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
										   <input type="button" onclick="submitAuditIncome()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>
