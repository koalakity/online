<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#goucheForm'),"true");
	
	isapproveCarMortgageOnchange();
	
});
function submintAuditgouche() {
    if ($.mValidate($('#goucheForm'), "", "true")) {
        if (parseInt($("#uploadCount").val()) != 0) {
            alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
        } else {
            $.ajax({
                data: $("#goucheForm").serialize(),
                url: "${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCar",
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

function isapproveCarMortgageOnchange(){
	var _val=$("#isapproveCarMortgage").val();
	//按揭时必须输入金额
	if(_val==1){
		$("#carMonthMortgage").attr("disabled",false);
		//购买完毕，文本不能编辑
		$("#carMonthMortgage").attr("class","input_text1 money required");
	}else{
		//购买完毕，文本不能编辑
		$("#carMonthMortgage").attr("class","input_text1");
		//文本不能用
		$("#carMonthMortgage").attr("disabled",true);
		//文本为0
		$("#carMonthMortgage").val("0");
	}
}
function closeCar(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/gouche_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请上传车辆行驶证原件照片；</p>
			<p>2.请上传您和您购买车辆的合影（须体现车牌号码）。</p>
		</div>
		<form id="goucheForm" method="post">
		<table class="upload_c">
				<c:choose>
						<c:when test="${way=='0'}">
									<tr>
										<td class="td1">汽车品牌</td>
										<td class="td2"><input maxlength="20" id="carBrand" name="carBrand" value="${userInfo.carBrand}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">购车年份</td>
										<td class="td2">
											<div class="select1_border">
												<div class="select1_con">
													<select id="carYears" name="carYears" disabled="disabled">
													<c:forEach var="x" begin="0" end="12" varStatus="z">
														<option value="${2000+z.index}" <c:if test="${userInfo.carYears==(2000+z.index)}">selected="selected"</c:if> >
															 <c:out value="${2000+z.index}"></c:out>
														</option>
													</c:forEach>
													</select>
												</div>
											</div>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">车牌号码</td>
										<td class="td2"><input maxlength="10" id="carNo" name="carNo" value="${userInfo.carNo}" type="text" class="input_text1" disabled="disabled"/></td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">自有汽车</td>
										<td class="td2">
											<div class="select1_border">
												<div class="select1_con">
													<select id="isapproveCarMortgage" name="isapproveCarMortgage" class="select1" disabled="disabled">
														<option value="0" <c:if test="${userInfo.isapproveCarMortgage==0}" >selected="selected"</c:if>>
															购买完毕
														</option>
														<option value="1" <c:if test="${userInfo.isapproveCarMortgage==1}" >selected="selected"</c:if>>
															仍在按揭
														</option>
													</select>
												</div>
											</div>
										</td>
										<td>&nbsp;</td>
									</tr>
									<tr>
										<td class="td1">每月按揭金额</td>
										<td class="td2"><input maxlength="10" id="carMonthMortgage" name="carMonthMortgage" value="${userInfo.carMonthMortgage}" type="text" class="input_text1" disabled="disabled"/></td>
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
						                <input type="button" onclick="closeCar()" class="btn_r1 fr" value="关闭"/>
						            </td>
						            <td>
						                &nbsp;
						            </td>
						        </tr>
							</c:when>
							<c:otherwise>
								<tr>
									<td class="td1"><span class="col_r4">*</span>汽车品牌</td>
									<td class="td2"><input maxlength="20" id="carBrand" name="carBrand" value="${userInfo.carBrand}" type="text" class="input_text1 chars50 required" /></td>
									<td colspan="2"><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1"><span class="col_r4">*</span>购车年份</td>
									<td class="td2">
										<div class="select1_border">
											<div class="select1_con">
												<select id="carYears" name="carYears">
												<c:forEach var="x" begin="0" end="12" varStatus="z">
													<option value="${2000+z.index}" <c:if test="${userInfo.carYears==(2000+z.index)}">selected="selected"</c:if> >
														 <c:out value="${2000+z.index}"></c:out>
													</option>
												</c:forEach>
												</select>
											</div>
										</div>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="td1"><span class="col_r4">*</span>车牌号码</td>
									<td class="td2"><input maxlength="10" id="carNo" name="carNo" value="${userInfo.carNo}" type="text" class="input_text1 chars20 required" /></td>
									<td colspan="2"><label class="mvalidatemsg"></label></td>
								</tr>
								<tr>
									<td class="td1">自有汽车</td>
									<td class="td2">
										<div class="select1_border">
											<div class="select1_con">
												<select onchange="isapproveCarMortgageOnchange()" id="isapproveCarMortgage" name="isapproveCarMortgage" class="select1">
													<option value="0" <c:if test="${userInfo.isapproveCarMortgage==0}" >selected="selected"</c:if>>
														购买完毕
													</option>
													<option value="1" <c:if test="${userInfo.isapproveCarMortgage==1}" >selected="selected"</c:if>>
														仍在按揭
													</option>
												</select>
											</div>
										</div>
									</td>
									<td>&nbsp;</td>
								</tr>
								<tr>
									<td class="td1">每月按揭金额</td>
									<td class="td2"><input maxlength="10" id="carMonthMortgage" name="carMonthMortgage" value="${userInfo.carMonthMortgage}" type="text" class="input_text1 money" /></td>
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
										    <input type="button" onclick="closeCar()" class="btn_r1 fr" value="取消" style="margin-left:10px;" />
										   <input type="button" onclick="submintAuditgouche()" class="btn_r1 fr" value="提交审核" />
										</td>
										<td>&nbsp;</td>
								</tr>
							</c:otherwise>
					</c:choose>
		</table>
		</form>
	</div>
</div>