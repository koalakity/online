<%@ page contentType="text/html;charset=utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#identyForm'),"true");
});
function submitAuditIdentity() {
    if ($.mValidate($('#identyForm'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#identyForm").serialize(),
                url: "${ctx}/infoApproveNew/updataIndenty",
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
function closeIdentity(){
   closefancy();
}
</script>
            <div class="autlayer">
                <div class="autlayer_t">
                    <img src="${ctx}/static/images/shenfen_t.jpg" />
                    <a  class="autlayer_close fr" onclick="parent.closefancy();">
                    </a>
                </div>
                <div class="autlayer_c">
                    <div class="upload_t">
                        <p>
                            1.请上传本人身份证原件正反面照片；
                        </p>
                        <p>
                            2.您上传的身份证照片需和您绑定的身份证一致，否则将无法通过认证；
                        </p>
                        <p>
                            3.提交审核前，请先进行身份证验证和手机号码绑定。
                        </p>
                    </div>
                    <form id="identyForm" method="post">
                        <table class="upload_c">
                            <c:choose>
                                <c:when test="${way=='0'}">
                                     <tr>
                                        <td class="td1">
                                            真实姓名
                                        </td>
                                        <td class="td2">
                                                <input type="text" value="${userInfo.realName}" disabled="disabled" class="input_text1" />
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td1">
                                            身份证号码
                                        </td>
                                        <td>
                                              <input type="text" value="${userInfo.identityNo}" disabled="disabled" class="input_text1" />
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
                                    <tr>
                                        <td class="td1">
                                            手机号码
                                        </td>
                                        <td>
                                             <input type="text" value="${userInfo.phoneNo}" disabled="disabled" class="input_text1"/>
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
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
                                            <input type="button" onclick="closeIdentity()" class="btn_r1 fr" value="关闭"/>
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
                                </c:when>
                                <c:otherwise>
                                    <tr>
                                        <td class="td1">
                                            <span class="col_r4">*</span>真实姓名
                                        </td>
                                        <td class="td2">
                                            <c:if test="${not empty userInfo.user.isApproveCard}">
                                                <c:if test="${userInfo.user.isApproveCard=='1'}">
                                                    <input type="text" value="${userInfo.realName}" disabled="disabled" class="input_text1 required name"
                                                    />
                                                    <input type="hidden" name="realName" value="${userInfo.realName}" id="realName"
                                                    class="input_text1"/>
                                                </c:if>
                                                <c:if test="${userInfo.user.isApproveCard=='0'}">
                                                    <input type="text" name="realName" value="${userInfo.realName}" id="realName"
                                                    class="input_text1 realname required"/>
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty userInfo.user.isApproveCard}">
                                                <input type="text" name="realName" value="${userInfo.realName}" id="realName"
                                                class="input_text1 realname required" />
                                            </c:if>
                                        </td>
                                        <td><label class="mvalidatemsg"></label></td>
                                    </tr>
                                    <tr>
                                        <td class="td1">
                                            <span class="col_r4">*</span>身份证号码
                                        </td>
                                        <td>
                                            <c:if test="${not empty userInfo.user.isApproveCard}">
                                                <c:if test="${userInfo.user.isApproveCard=='1'}">
                                                    <input type="text" value="${userInfo.identityNo}" disabled="disabled"
                                                    class="input_text1" />
                                                    <input id="identityNo" type="hidden" name="identityNo" value="${userInfo.identityNo}"
                                                    class="input_text1" />
                                                </c:if>
                                                <c:if test="${userInfo.user.isApproveCard=='0'}">
                                                    <input id="identityNo" type="text" name="identityNo" value="${userInfo.identityNo}"
                                                    class="input_text1 idcard required" />
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty userInfo.user.isApproveCard}">
                                                <input id="identityNo" type="text" name="identityNo" value="${userInfo.identityNo}"
                                                class="input_text1 idcard required" />
                                            </c:if>
                                        </td>
                                        <td><label class="mvalidatemsg"></label></td>
                                    </tr>
                                    <tr>
                                        <td class="td1">
                                            <span class="col_r4">*</span>手机号码
                                        </td>
                                        <td>
                                            <c:if test="${not empty userInfo.user.isApprovePhone}">
                                                <c:if test="${userInfo.user.isApprovePhone=='1'}">
                                                    <input type="text" value="${userInfo.phoneNo}" disabled="disabled" class="input_text1"
                                                    />
                                                    <input id="phoneNo" type="hidden" name="phoneNo" value="${userInfo.phoneNo}"
                                                    class="input_text1" />
                                                </c:if>
                                                <c:if test="${userInfo.user.isApprovePhone=='0'}">
                                                    <input id="phoneNo" type="text" name="phoneNo" value="${userInfo.phoneNo}"
                                                    class="input_text1  mobile required" />
                                                </c:if>
                                            </c:if>
                                            <c:if test="${empty userInfo.user.isApprovePhone}">
                                                <input id="phoneNo" type="text" name="phoneNo" value="${userInfo.phoneNo}"
                                                class="input_text1  mobile required" />
                                            </c:if>
                                        </td>
                                        <td><label class="mvalidatemsg"></label></td>
                                    </tr>
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
                                            <input type="button" onclick="closeIdentity()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
                                            <input type="button" onclick="submitAuditIdentity()" class="btn_r1 fr" value="提交审核" />
                                        </td>
                                        <td>
                                            &nbsp;
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </table>
                </div>
                </form>
            </div>