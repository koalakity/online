<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript">

$(function(){
	$('#privatePropritorBtn').unbind('click');
	$("#privatePropritorBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".privatePropritorForm").serialize(),
		    		 url: "${ctx}/admin/user/savePrivatePropritor",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		//alert('error');
		      },
			   	success: function(data){
			   			alert("保存成功.");
			   			return;
			   	}
		});
		
	});
});


	
</script>

<form:form class="privatePropritorForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;">
	<table>
				<tr>
					<td  width="190" align="right">私营企业类型:</td>
					<td>
						<select id="sel8" name="privatePropritor.enterpriseKind">
		          			<option value="11" <c:if test="${userbasic.privatePropritor.enterpriseKind==11}">selected</c:if>>农</option>
		           			<option value="12" <c:if test="${userbasic.privatePropritor.enterpriseKind==12}">selected</c:if>>林</option>
		           			<option value="13" <c:if test="${userbasic.privatePropritor.enterpriseKind==13}">selected</c:if>>牧</option>
		           			<option value="14" <c:if test="${userbasic.privatePropritor.enterpriseKind==14}">selected</c:if>>渔业</option>
		           			<option value="15" <c:if test="${userbasic.privatePropritor.enterpriseKind==15}">selected</c:if>>制造业</option>
		           			<option value="16" <c:if test="${userbasic.privatePropritor.enterpriseKind==16}">selected</c:if>>电力</option>
		           			<option value="17" <c:if test="${userbasic.privatePropritor.enterpriseKind==17}">selected</c:if>>燃气及水的生产和供应业</option>
		           			<option value="18" <c:if test="${userbasic.privatePropritor.enterpriseKind==18}">selected</c:if>>建筑业</option>
		           			<option value="19" <c:if test="${userbasic.privatePropritor.enterpriseKind==19}">selected</c:if>>交通运输</option>
		           			<option value="20" <c:if test="${userbasic.privatePropritor.enterpriseKind==20}">selected</c:if>>仓储和邮政业</option>
		           			<option value="21" <c:if test="${userbasic.privatePropritor.enterpriseKind==21}">selected</c:if>>信息传输</option>
		           			<option value="22" <c:if test="${userbasic.privatePropritor.enterpriseKind==22}">selected</c:if>>计算机服务和软件业</option>
		           			<option value="23" <c:if test="${userbasic.privatePropritor.enterpriseKind==23}">selected</c:if>>批发零售业</option>
		           			<option value="24" <c:if test="${userbasic.privatePropritor.enterpriseKind==24}">selected</c:if>>金融业</option>
		           			<option value="25" <c:if test="${userbasic.privatePropritor.enterpriseKind==25}">selected</c:if>>房地产业</option>
		           			<option value="26" <c:if test="${userbasic.privatePropritor.enterpriseKind==26}">selected</c:if>>采矿业</option>
		           			<option value="27" <c:if test="${userbasic.privatePropritor.enterpriseKind==27}">selected</c:if>>租赁和商务服务业</option>
		           			<option value="28" <c:if test="${userbasic.privatePropritor.enterpriseKind==28}">selected</c:if>>科学研究</option>
		           			<option value="29" <c:if test="${userbasic.privatePropritor.enterpriseKind==29}">selected</c:if>>技术服务和地址堪查业</option>
		           			<option value="30" <c:if test="${userbasic.privatePropritor.enterpriseKind==30}">selected</c:if>>水利</option>
		           			<option value="31" <c:if test="${userbasic.privatePropritor.enterpriseKind==31}">selected</c:if>>环境好公共设施管理业</option>
		           			<option value="32" <c:if test="${userbasic.privatePropritor.enterpriseKind==32}">selected</c:if>>居民服务和其他服务业</option>
		           			<option value="33" <c:if test="${userbasic.privatePropritor.enterpriseKind==33}">selected</c:if>>教育</option>
		           			<option value="34" <c:if test="${userbasic.privatePropritor.enterpriseKind==34}">selected</c:if>>卫生</option>
		           			<option value="35" <c:if test="${userbasic.privatePropritor.enterpriseKind==35}">selected</c:if>>社会保障和社会福利业</option>
		           			<option value="36" <c:if test="${userbasic.privatePropritor.enterpriseKind==36}">selected</c:if>>文化</option>
		           			<option value="37" <c:if test="${userbasic.privatePropritor.enterpriseKind==37}">selected</c:if>>体育和娱乐业</option>
		           			<option value="38" <c:if test="${userbasic.privatePropritor.enterpriseKind==38}">selected</c:if>>公共管理和社会组织</option>
		           			<option value="39" <c:if test="${userbasic.privatePropritor.enterpriseKind==39}">selected</c:if>>国际组织</option>
		          		</select>
					</td>
				</tr>
				<tr>
					<td align="right" nowrap >成立日期:</td>
					<td align="left">
						<input class="easyui-datebox" id="establishDate" name="privatePropritor.establishDate"   value="${fn:substring(userbasic.privatePropritor.establishDate,0,10)}">
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >经营场所:</td>
					<td>
						<select id="enterpriseAddress" name="privatePropritor.enterpriseAddress">
		          			<option value="44" <c:if test="${userbasic.privatePropritor.enterpriseAddress==44}">selected</c:if>>自有</option>
		           			<option value="45" <c:if test="${userbasic.privatePropritor.enterpriseAddress==45}">selected</c:if>>租赁</option>
		           			<option value="46" <c:if test="${userbasic.privatePropritor.enterpriseAddress==46}">selected</c:if>>按揭</option>
		          		</select>
					</td>
				</tr>
				<tr>
					<td align="right" nowrap >租金:</td>
					<td align="left">
		      			<input id="rent" name="privatePropritor.rent" type="text" value="${userbasic.privatePropritor.rent}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >租期:</td>
					<td align="left">
		      			<input id="lease" name="privatePropritor.lease" type="text" value="${userbasic.privatePropritor.lease}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >税务编号:</td>
					<td align="left">
		      			<input id="taxationNo" name="privatePropritor.taxationNo" type="text" value="${userbasic.privatePropritor.taxationNo}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >工商登记号:</td>
					<td align="left">
		      			<input id="gsBookNo" name="privatePropritor.gsBookNo" type="text" value="${userbasic.privatePropritor.gsBookNo}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >全年盈利/亏损额:</td>
					<td align="left">
		      			<input id="profitOrLossAmount" name="privatePropritor.profitOrLossAmount" type="text" value="${userbasic.privatePropritor.profitOrLossAmount}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<tr>
					<td align="right" nowrap >雇员人数:</td>
					<td align="left">
		      			<input id="employeeAmount" name="privatePropritor.employeeAmount" type="text" value="${userbasic.privatePropritor.employeeAmount}" onkeyup="check(this)"/>
		  			</td>
				</tr>
				<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
			</table>
			<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
			<input id="id" name="privatePropritor.id" type="hidden" value="${userbasic.privatePropritor.id}" />
	        <div style="margin:0 auto; width:80px;"><a id="privatePropritorBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a></div>
</form:form>