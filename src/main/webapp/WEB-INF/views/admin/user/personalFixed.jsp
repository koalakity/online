<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script language="javascript" type="text/javascript">
$(function(){
	$('#fixBtn').unbind('click');
	$("#fixBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".personalFixedForm").serialize(),
		    		 url: "${ctx}/admin/user/savePersonalFixed",
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


<form:form class="personalFixedForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;">
	<table>
		<tr>
			<td align="right" width="190" >是否有房:</td>
			<td align="left">
					<input type="radio" name="userInfoPerson.isapproveHaveHouse" id="isapproveHaveHouse"  value="1" <c:if test="${userbasic.userInfoPerson.isapproveHaveHouse==1}">checked="checked"</c:if>  />有
					<input type="radio" name="userInfoPerson.isapproveHaveHouse" id="isapproveHaveHouse" value="0" <c:if test="${userbasic.userInfoPerson.isapproveHaveHouse==0}">checked="checked"</c:if>  />无
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>房产地址:</td>
			<td align="left">
				<input id="housePropertyAddress"	name="userInfoPerson.housePropertyAddress" type="text"	value="${userbasic.userInfoPerson.housePropertyAddress}"	onkeyup="check(this)" />
			</td>
		</tr>

		<tr>
			<td align="right" nowrap>自有房产:</td>
			<td>
				<select id="isapproveHouseMortgage"	name="userInfoPerson.isapproveHouseMortgage">
					<option value="1" <c:if test="${userbasic.userInfoPerson.isapproveHouseMortgage==1}" >selected="selected"</c:if>>购买完毕</option>
					<option value="0" <c:if test="${userbasic.userInfoPerson.isapproveHouseMortgage==0}" >selected="selected"</c:if>>仍在按揭</option>
				</select>
			</td>
		</tr>

		<tr>
			<td align="right" nowrap>每月按揭金额(房产):</td>
			<td align="left">
				<input id="houseMonthMortgage"	name="userInfoPerson.houseMonthMortgage" type="text"	value="${userbasic.userInfoPerson.houseMonthMortgage}"	onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>是否有车:</td>
			<td>
					<input type="radio" name="userInfoPerson.isapproveHaveCar" id="isapproveHaveCar" value="1" <c:if test="${userbasic.userInfoPerson.isapproveHaveCar==1}"> checked="checked"</c:if> />有
					<input type="radio"	name="userInfoPerson.isapproveHaveCar" id="isapproveHaveCar" value="0" <c:if test="${userbasic.userInfoPerson.isapproveHaveCar==0}"> checked="checked"</c:if> />无
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>汽车品牌:</td>
			<td align="left">
				<input id="carBrand" name="userInfoPerson.carBrand"	type="text" value="${userbasic.userInfoPerson.carBrand}"	onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>购车年份:</td>
			<td align="left"><select name="userInfoPerson.carYears">
					<option value="${userbasic.userInfoPerson.carYears}">${userbasic.userInfoPerson.carYears}</option>
					<c:forEach var="i" begin="1985" end="2015" step="1">
						<option value="${i}">${i}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td align="right" nowrap>车牌号码:</td>
			<td align="left">
				<input id="carNo" name="userInfoPerson.carNo" type="text"	value="${userbasic.userInfoPerson.carNo}" onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>自有汽车:</td>
			<td>
				<select id="isapproveCarMortgage"	name="userInfoPerson.isapproveCarMortgage">
					<option value="1" <c:if test="${userbasic.userInfoPerson.isapproveCarMortgage==1}" >selected="selected"</c:if>>购买完毕</option>
					<option value="0" <c:if test="${userbasic.userInfoPerson.isapproveCarMortgage==0}" >selected="selected"</c:if>>仍在按揭</option>
			</select></td>
		</tr>
		<tr>
			<td align="right" nowrap>每月按揭金额(汽车):</td>
			<td align="left">
				<input id="carMonthMortgage" name="userInfoPerson.carMonthMortgage" type="text" value="${userbasic.userInfoPerson.carMonthMortgage}"	onkeyup="check(this)" />
			</td>
		</tr>

	</table>
	<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
	<input id="infoId" name="userInfoPerson.infoId" type="hidden" value="${userbasic.userInfoPerson.infoId}" />
	<div style="margin:0 auto; width:80px;"><a id="fixBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a></div>
</form:form>