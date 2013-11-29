<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#fangchan'),"true");
	
	isapproveHouseMortgageOnchange();
});
function submintAuditfangchan() {
    if ($.mValidate($('#fangchan'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#fangchan").serialize(),
                url: "${pageContext.request.contextPath}/borrowing/borrowing/updataHouseProperty",
                type: "POST",
                dataType: 'text',
                success: function(data) {
                    if (data == "true") {
                        alert("保存成功.");
                        closefancy();
                        window.top.location.reload();
                    } else if (data == "noUpFile") {
                        alert("请上传文件后在提交审核！");
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

function isapproveHouseMortgageOnchange(){
	var _val=$("#isapproveHouseMortgage").val();
	//按揭时必须输入金额
	if(_val==1){
		$("#houseMonthMortgage").attr("disabled",false);
		//购买完毕，文本不能编辑
		$("#houseMonthMortgage").attr("class","input_text1 money required");
	}else{
		//购买完毕，文本不能编辑
		$("#houseMonthMortgage").attr("class","input_text1");
		//文本不能用
		$("#houseMonthMortgage").attr("disabled",true);
		//文本为0
		$("#houseMonthMortgage").val("0");
	}
}
function closeHouse(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/fangchan_t.jpg" />
	<a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<form id="fangchan" name="fangchan" method="post">
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请上传商品房房产购房合同；</p>
			<p>2.请上传银行按揭贷款合同；</p>
			<p>3.请上传房产局产调单及收据。</p>
		</div>
		<table class="upload_c">
			<c:choose>
						<c:when test="${way=='0'}">
								<tr>
									<td class="td1">房产地址</td>
									<td class="td2"><input maxlength="70" value="${userInfo.housePropertyAddress }" type="text" class="input_text1" id="housePropertyAddress" name="housePropertyAddress"  disabled="disabled"/></td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="td1">自有房产</td>
									<td class="td2">
										<div class="select1_border">
											<div class="select1_con">
												<select onchange="isapproveHouseMortgageOnchange()" class="select1" id="isapproveHouseMortgage" name="isapproveHouseMortgage"  disabled="disabled">
													<option ${userInfo.isapproveHouseMortgage==0?"selected='selected'":"" } value="0"> 购买完毕 </option>
													<option ${userInfo.isapproveHouseMortgage==1?"selected='selected'":"" } value="1"> 仍在按揭 </option>
												</select>
											</div>
										</div>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="td1">每月按揭金额</td>
									<td class="td2"><input maxlength="16" value="${userInfo.houseMonthMortgage }" id="houseMonthMortgage" name="houseMonthMortgage" type="text" class="input_text1"  disabled="disabled"/></td>
									<td><span class="font_14">元</span></td>
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
						                <input type="button" onclick="closeHouse()" class="btn_r1 fr" value="关闭"/>
						            </td>
						            <td>
						                &nbsp;
						            </td>
						        </tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="td1"><span class="col_r4">*</span>房产地址</td>
									<td class="td2"><input maxlength="70" value="${userInfo.housePropertyAddress }" type="text" class="input_text1 chars100 required" id="housePropertyAddress" name="housePropertyAddress"/></td>
									<td colspan="2"><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1">自有房产</td>
									<td class="td2">
										<div class="select1_border">
											<div class="select1_con">
												<select  onchange="isapproveHouseMortgageOnchange()" class="select1" id="isapproveHouseMortgage" name="isapproveHouseMortgage">
													<option ${userInfo.isapproveHouseMortgage==0?"selected='selected'":"" } value="0"> 购买完毕 </option>
													<option ${userInfo.isapproveHouseMortgage==1?"selected='selected'":"" } value="1"> 仍在按揭 </option>
												</select>
											</div>
										</div>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="td1">每月按揭金额</td>
									<td class="td2"><input maxlength="16" value="${userInfo.houseMonthMortgage }" id="houseMonthMortgage" name="houseMonthMortgage" type="text" class="input_text1 money required" /></td>
									<td><span class="font_14">元</span></td>
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
										  <input type="button" onclick="closeHouse()" class="btn_r1 fr" value="取消" style="margin-left:10px;" />
										  <input type="button" onclick="submintAuditfangchan()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
	</div>
	</form>
</div>
