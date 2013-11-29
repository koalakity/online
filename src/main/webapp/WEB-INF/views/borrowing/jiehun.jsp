<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
	function submintAuditjiehun(){
	     if(parseInt($("#uploadCount").val())!=0){
	     	alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
	     }else{
		     $.ajax({
		               data:$("#jiehunForm").serialize(),
				       url: "${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApprove",
				       type: "POST",
				       dataType: 'text',
					   success: function(data){
					     if(data=="true"){
					   			alert("保存成功.");
					   			closefancy();
						   		window.top.location.reload();
					     }else{
	                       window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                     }
					   }
				});
		}
	}
function closeMarry(){
   closefancy();
}
</script>
<div class="autlayer">
	<div class="autlayer_t"><img src="${pageContext.request.contextPath}/static/images/jiehun_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
	<div class="autlayer_c">
		<div class="upload_t">
			<p>1.请上传结婚证书原件照片；</p>
			<p>2.请上传您配偶的身份证原件正反面照片；</p>
			<p>3.请上传您和配偶的近照合影一张。</p>
		</div>
		<form id="jiehunForm" method="post">
		<table class="upload_c">
			<c:choose>
					<c:when test="${way=='0'}">
					    <tr>
					        <td class="td1">
					            婚姻状况
					        </td>
					        <td class="td2">
					            <div class="select1_border">
					                <div class="select1_con">
					                    <select id="isapproveMarry" name="isapproveMarry" disabled="disabled">
					                        <option value="57" ${userInfo.isapproveMarry==57?"selected='selected'": ""}>
					                            已婚
					                        </option>
					                        <option value="56" ${userInfo.isapproveMarry==56?"selected='selected'": ""}>
					                            未婚
					                        </option>
					                        <option value="59" ${userInfo.isapproveMarry==59?"selected='selected'": ""}>
					                            离异
					                        </option>
					                        <option value="58" ${userInfo.isapproveMarry==58?"selected='selected'": ""}>
					                            丧偶
					                        </option>
					                    </select>
					                </div>
					            </div>
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
					            <input type="button" onclick="closeMarry()" class="btn_r1 fr" value="关闭"/>
					        </td>
					        <td>
					            &nbsp;
					        </td>
					    </tr>
					</c:when>
					<c:otherwise>
					    <tr>
					        <td class="td1">
					            <span class="col_r4">*</span>婚姻状况
					        </td>
					        <td class="td2">
					            <div class="select1_border">
					                <div class="select1_con">
					                    <select id="isapproveMarry" name="isapproveMarry">
					                        <option value="57" ${userInfo.isapproveMarry==57?"selected='selected'": ""}>
					                            已婚
					                        </option>
					                        <option value="56" ${userInfo.isapproveMarry==56?"selected='selected'": ""}>
					                            未婚
					                        </option>
					                        <option value="59" ${userInfo.isapproveMarry==59?"selected='selected'": ""}>
					                            离异
					                        </option>
					                        <option value="58" ${userInfo.isapproveMarry==58?"selected='selected'": ""}>
					                            丧偶
					                        </option>
					                    </select>
					                </div>
					            </div>
					        </td>
					        <td>
					            &nbsp;
					        </td>
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
					            <input type="button" onclick="closeMarry()" class="btn_r1 fr" value="取消" style="margin-left:10px;" />
					            <input type="button" onclick="submintAuditjiehun()" class="btn_r1 fr" value="提交审核" />
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
