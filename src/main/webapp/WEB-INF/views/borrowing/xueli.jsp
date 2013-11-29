<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#educateForm'),"true");
});
function submintAuditeducate() {
    if ($.mValidate($('#educateForm'), "", "true")) {
        $.ajax({
            data: $("#educateForm").serialize(),
            url: "${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApprove",
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
    } else {
        return false;
    };
}
function closeEducate(){
   closefancy();
}
</script>

<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/xueli_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请至http://www.chsi.com.cn/xlcx/ 查询并获取12位在线验证码；</p>
			<p>2.请将12位在线验证码输入下面的文本框。</p>
		</div>
		<form id="educateForm" method="post">
		<input type="hidden" name="degreeToken" id="degreeToken" value="${sessionScope.token}"/>
										
		<table class="upload_c">
					<c:choose>
					    <c:when test="${way=='0'}">
							<tr>
								<td class="td1">12位在线验证码</td>
								<td class="td2"><input maxlength="12" id="degreeNo" name="degreeNo" value="${userInfo.degreeNo}" type="text" class="input_text1" disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">最高学历</td>
								<td class="td2">
									<div class="select1_border">
										<div class="select1_con">
											<select name="maxDegree" disabled="disabled">
												<option value="47" ${userInfo.maxDegree=='47'?"selected":""}>小学</option>
												<option value="48" ${userInfo.maxDegree=='48'?"selected":""}>初中</option>
												<option value="49" ${userInfo.maxDegree=='49'?"selected":""}>高中</option>
												<option value="50" ${userInfo.maxDegree=='50'?"selected":""}>大专</option>
												<option value="51" ${userInfo.maxDegree=='51'?"selected":""}>本科</option>
												<option value="52" ${userInfo.maxDegree=='52'?"selected":""}>硕士</option>
												<option value="53" ${userInfo.maxDegree=='53'?"selected":""}>博士</option>
												<option value="54" ${userInfo.maxDegree=='54'?"selected":""}>博士后</option>
												<option value="55" ${userInfo.maxDegree=='55'?"selected":""}>其他</option>
											</select>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">毕业院校</td>
								<td class="td2"><input maxlength="50" id="graduatSchool" name="graduatSchool" value="${userInfo.graduatSchool}" type="text" class="input_text1" disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">入学年份</td>
								<td class="td2">
									<div class="select1_border">
										<div class="select1_con">
											<select id="inschoolTime" name="inschoolTime" disabled="disabled">
											<c:forEach begin="1" end="122" var="y" >
												<option value="${2014-y}" <c:if test="${userInfo.inschoolTime==(2014-y)}">selected="selected"</c:if>>
													<c:out value="${2014-y}"></c:out>
												</option>
											</c:forEach>
											</select>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
							    <td colspan="2">
							        <input type="button" onclick="closeEducate()" class="btn_r1 fr" value="关闭"/>
							    </td>
							    <td>
							        &nbsp;
							    </td>
							</tr>
					    </c:when>
					    <c:otherwise>
					    	<tr>
								<td class="td1">12位在线验证码</td>
								<td class="td2"><input maxlength="12" id="degreeNo" name="degreeNo" value="${userInfo.degreeNo}" type="text" class="input_text1 education" /></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>最高学历</td>
								<td class="td2">
									<div class="select1_border">
										<div class="select1_con">
											
											<select name="maxDegree">
												<option value="47" ${userInfo.maxDegree=='47'?"selected":""}>小学</option>
												<option value="48" ${userInfo.maxDegree=='48'?"selected":""}>初中</option>
												<option value="49" ${userInfo.maxDegree=='49'?"selected":""}>高中</option>
												<option value="50" ${userInfo.maxDegree=='50'?"selected":""}>大专</option>
												<option value="51" ${userInfo.maxDegree=='51'?"selected":""}>本科</option>
												<option value="52" ${userInfo.maxDegree=='52'?"selected":""}>硕士</option>
												<option value="53" ${userInfo.maxDegree=='53'?"selected":""}>博士</option>
												<option value="54" ${userInfo.maxDegree=='54'?"selected":""}>博士后</option>
												<option value="55" ${userInfo.maxDegree=='55'?"selected":""}>其他</option>
											</select>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>毕业院校</td>
								<td class="td2"><input maxlength="50" id="graduatSchool" name="graduatSchool" value="${userInfo.graduatSchool}" type="text" class="input_text1 chars100 required" /></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>入学年份</td>
								<td class="td2">
									<div class="select1_border">
										<div class="select1_con">
											<select id="inschoolTime" name="inschoolTime">
											<c:forEach begin="1" end="122" var="y" >
												<option value="${2014-y}" <c:if test="${userInfo.inschoolTime==(2014-y)}">selected="selected"</c:if>>
													<c:out value="${2014-y}"></c:out>
												</option>
											</c:forEach>
											</select>
										</div>
									</div>
								</td>
								<td>&nbsp;</td>
							</tr>
					        <tr>
					            <td colspan="2">
					                <input type="button" onclick="closeEducate()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
					                <input type="button" onclick="submintAuditeducate()" class="btn_r1 fr" value="提交审核" />
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
