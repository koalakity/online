<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#juzhudi'),"true");
});
function submintAuditjuzhudi() {
    if ($.mValidate($('#juzhudi'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#juzhudi").serialize(),
                url: "${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApprove",
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
    } else {
        return false;
    };
}
function closeAdd(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/juzhudi_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.可上传用您姓名登记的水、电、煤气、固定电话等最近三期缴费单；</p>
			<p>2.可上传您的自有房产证明；</p>
			<p>3.可上传您父母的房产证明，及证明您和父母关系的证明材料。</p>	
		</div>
		<form id="juzhudi" name="juzhudi" method="post">
		<table class="upload_c">
				<c:choose>
						<c:when test="${way=='0'}">
									<tr>
										<td class="td1">居住地址</td>
										<td class="td2"><input maxlength="100" id="liveAddress" name="liveAddress" value="${userInfo.liveAddress}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">居住地电话</td>
										<td class="td2">
											<input id="livePhoneArea" name="livePhoneArea" value="${userInfo.livePhoneArea}" type="text" class="input_text1 wd_r1" disabled="disabled"/>
											 - 
											 <input id="livePhoneNo" name="livePhoneNo" value="${userInfo.livePhoneNo}" type="text" class="input_text1 wd_r2" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">居住地邮编</td>
										<td class="td2"><input maxlength="10" id="postCode" name="postCode" value="${userInfo.postCode}" type="text" class="input_text1" disabled="disabled"/></td>
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
								            <input type="button" onclick="closeAdd()" class="btn_r1 fr" value="关闭"/>
								        </td>
								        <td>
								            &nbsp;
								        </td>
								    </tr>	
							</c:when>
							<c:otherwise>
								<tr>
									<td class="td1"><span class="col_r4">*</span>居住地址</td>
									<td class="td2"><input maxlength="100" id="liveAddress" name="liveAddress" value="${userInfo.liveAddress}" type="text" class="input_text1 chars100 required" /></td>
									<td><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1"><span class="col_r4">*</span>居住地电话</td>
									<td class="td2">
										<input id="livePhoneArea" name="livePhoneArea" value="${userInfo.livePhoneArea}" type="text" class="input_text1 wd_r1 phone1 required" />
										 - 
										 <input id="livePhoneNo" name="livePhoneNo" value="${userInfo.livePhoneNo}" type="text" class="input_text1 wd_r2 phone2 required" /></td>
									<td><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1"><span class="col_r4">*</span>居住地邮编</td>
									<td class="td2"><input maxlength="10" id="postCode" name="postCode" value="${userInfo.postCode}" type="text" class="input_text1 zipcode required" /></td>
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
										    <input type="button" onclick="closeAdd()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
										    <input type="button" onclick="submintAuditjuzhudi()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>
