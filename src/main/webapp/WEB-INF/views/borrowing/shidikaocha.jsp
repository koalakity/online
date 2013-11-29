<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#shidikaocha'),"true");
});
function submintAuditshidikaocha() {
    if ($.mValidate($('#shidikaocha'), "", "true")) {
        $.ajax({
            data: $("#shidikaocha").serialize(),
            url: "${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApprove",
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
function closeField(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${ctx}/static/images/shidi_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.目前实地审核申请仅对经营类客户开放；</p>
			<p>2.申请人名下有固定资产，经营时间满一年；</p>
			<p>3.借款申请额在3万以上，且申请人必须已经提交四项必要信用认证资料。</p>	
		</div>
		<form id="shidikaocha" name="shidikaocha" method="post">
		<input type="hidden" name="realApproveToken" id="realApproveToken" value="${sessionScope.token}"/>
		<table class="upload_c">
				<c:choose>
						<c:when test="${way=='0'}">
							<tr>
								<td class="td1">申请人姓名</td>
								<td class="td2"><input maxlength="10" id="realApproveName" name="realApproveName" value="${userInfo.realApproveName}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">联系电话</td>
								<td class="td2"><input maxlength="11" id="realApprovePhoneArea" name="realApprovePhoneArea" value="${userInfo.realApprovePhoneArea}" type="text" class="input_text1 wd_r1"  disabled="disabled"/> - <input id="realApprovePhone" name="realApprovePhone" value="${userInfo.realApprovePhone}" type="text" class="input_text1 wd_r2"  disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">公司地址</td>
								<td class="td2"><input maxlength="100" id="realApproveCorporationAdd" name="realApproveCorporationAdd" value="${userInfo.realApproveCorporationAdd}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">家庭地址</td>
								<td class="td2"><input maxlength="100" id="realApproveHomeAddress" name="realApproveHomeAddress" value="${userInfo.realApproveHomeAddress}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td>&nbsp;</td>
							</tr>
							<tr>
								<td class="td1">资产情况</td>
								<td class="td2"><input maxlength="16" id="realApproveAssets" name="realApproveAssets" value="${userInfo.realApproveAssets}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td><span class="font_14">元</span></td>
							</tr>
							<tr>
								<td class="td1">经营时间</td>
								<td class="td2"><input maxlength="4" id="realApproveOperateTime" name="realApproveOperateTime" value="${userInfo.realApproveOperateTime}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td><span class="font_14">年</span></td>
							</tr>
							<tr>
								<td class="td1">月均流水</td>
								<td class="td2"><input maxlength="16" id="realApproveMonthWater" name="realApproveMonthWater" value="${userInfo.realApproveMonthWater}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td><span class="font_14">元</span></td>
							</tr>
							<tr>
								<td class="td1">申请金额</td>
								<td class="td2"><input maxlength="16" id="realApproveApplyMoney" name="realApproveApplyMoney" value="${userInfo.realApproveApplyMoney}" type="text" class="input_text1"  disabled="disabled"/></td>
								<td><span class="font_14">元</span></td>
							</tr>
							 <tr>
								<td colspan="2">
								    <input type="button" onclick="closeField()" class="btn_r1 fr" value="关闭"/>
								</td>
								<td>
								  &nbsp;
								</td>
							</tr>
						</c:when>
						<c:otherwise>
							<tr>
								<td class="td1"><span class="col_r4">*</span>申请人姓名</td>
								<td class="td2"><input maxlength="10" id="realApproveName" name="realApproveName" value="${userInfo.realApproveName}" type="text" class="input_text1 realname required" /></td>
								<td colspan="2"><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>联系电话</td>
								<td class="td2"><input maxlength="11" id="realApprovePhoneArea" name="realApprovePhoneArea" value="${userInfo.realApprovePhoneArea}" type="text" class="input_text1 wd_r1 phone1 required" /> - <input id="realApprovePhone" name="realApprovePhone" value="${userInfo.realApprovePhone}" type="text" class="input_text1 wd_r2 phone2 required" /></td>
								<td colspan="2"><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>公司地址</td>
								<td class="td2"><input maxlength="100" id="realApproveCorporationAdd" name="realApproveCorporationAdd" value="${userInfo.realApproveCorporationAdd}" type="text" class="input_text1 chars100 required" /></td>
								<td colspan="2"><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>家庭地址</td>
								<td class="td2"><input maxlength="100" id="realApproveHomeAddress" name="realApproveHomeAddress" value="${userInfo.realApproveHomeAddress}" type="text" class="input_text1 chars100 required" /></td>
								<td colspan="2"><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>资产情况</td>
								<td class="td2"><input maxlength="16" id="realApproveAssets" name="realApproveAssets" value="${userInfo.realApproveAssets}" type="text" class="input_text1 money required" /></td>
								<td><span class="font_14">元</span></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>经营时间</td>
								<td class="td2"><input maxlength="4" id="realApproveOperateTime" name="realApproveOperateTime" value="${userInfo.realApproveOperateTime}" type="text" class="input_text1 number4 required" /></td>
								<td><span class="font_14">年</span></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>月均流水</td>
								<td class="td2"><input maxlength="16" id="realApproveMonthWater" name="realApproveMonthWater" value="${userInfo.realApproveMonthWater}" type="text" class="input_text1 money required" /></td>
								<td><span class="font_14">元</span></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
							<tr>
								<td class="td1"><span class="col_r4">*</span>申请金额</td>
								<td class="td2"><input maxlength="16" id="realApproveApplyMoney" name="realApproveApplyMoney" value="${userInfo.realApproveApplyMoney}" type="text" class="input_text1 money required" /></td>
								<td><span class="font_14">元</span></td>
								<td><label class="mvalidatemsg"></label></td>
							</tr>
                           <tr>
				             <td colspan="2">
				                 <input type="button" onclick="closeField()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
				                 <input type="button" onclick="submintAuditshidikaocha()" class="btn_r1 fr" value="提交审核" />
				             </td>
				             <td>&nbsp;</td>
			               </tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>
