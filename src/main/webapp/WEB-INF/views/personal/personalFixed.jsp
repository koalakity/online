<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){
	$("#fixBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
			$.ajax({
			 	data: $("#inputForm").serialize(),
		   		 url: "${ctx}/personal/personal/saveFixedAssets",
		   	 	 type: "POST",
		   		 dataType: 'json',
		   		 timeout: 10000,
		    	 error: function(){
		        	 //alert("保存失败！");
		        },
		       	success: function(data){
		       		if(data.success){
						alert("保存成功！");
						return;
		       		}else{
			       		alert(data.msg);
			       		return;
			       		}
		       	},
		        beforeSend: function(){
			        
		        }
			});
	});
});
</script>
						<div class="prompt6">
							<p>请填写以下固定资产信息：（注：带*为必填）</p>
						</div>
						<form:form id="inputForm"   modelAttribute="userInfoPerson"  name="inputForm"  method="post" action="${ctx}/personal/personal/saveFixedAssets" >
						<table>
							<tr><td class="td8">是否有房：</td>
								<td><c:if test="${fixedAssets.isapproveHaveHouse==1}"><input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="1" checked="checked"/>有<input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="0"/>无</c:if>
									<c:if test="${fixedAssets.isapproveHaveHouse==0}"><input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="1"/>有<input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="0" checked="checked"/>无</c:if>
									<c:if test="${fixedAssets.isapproveHaveHouse==''}"><input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="1"/>有<input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="0" />无</c:if>
									<c:if test="${fixedAssets.isapproveHaveHouse==null}"><input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="1"/>有<input type="radio" name="isapproveHaveHouse" id="isapproveHaveHouse" value="0" />无</c:if>
								</td>
							</tr>
							<tr><td class="td8">房产地址：</td><td><input class="chars100" type="text" name="housePropertyAddress" id="housePropertyAddress"  value="${fixedAssets.housePropertyAddress}"/></td></tr>
							<tr><td class="td8">自有房产：</td><td><select name="isapproveHouseMortgage" id="isapproveHouseMortgage"><option value="0" <c:if test="${fixedAssets.isapproveHouseMortgage=='0'}">selected</c:if>>购买完毕</option><option value="1" <c:if test="${fixedAssets.isapproveHouseMortgage=='1'}">selected</c:if>>仍在按揭</option></select></td></tr>
							<tr><td class="td8">每月按揭金额(房产)：</td><td><input type="text" name="houseMonthMortgage" id="houseMonthMortgage"value="${fixedAssets.houseMonthMortgage}" class="fl money"/><span class="fl">&nbsp;元</span></td></tr>
							<tr><td class="td8">是否有车：</td>
								<td><c:if test="${fixedAssets.isapproveHaveCar==1}"><input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="1" checked="checked"/>有<input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="0"/>无</c:if>
									<c:if test="${fixedAssets.isapproveHaveCar==0}"><input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="1"/>有<input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="0" checked="checked"/>无</c:if>
									<c:if test="${fixedAssets.isapproveHaveCar==''}"><input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="1"/>有<input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="0" />无</c:if>
									<c:if test="${fixedAssets.isapproveHaveCar==null}"><input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="1"/>有<input type="radio" name="isapproveHaveCar" id="isapproveHaveCar" value="0" />无</c:if>
								</td>
							</tr>
							<tr><td class="td8">汽车品牌：</td><td><input class="chars50" type="text" name="carBrand" id="carBrand" value="${fixedAssets.carBrand}"/></td></tr>
							<tr><td class="td8">购车年份：</td><td><select name="carYears" id="carYears"><option  value="${fixedAssets.carYears}" >${fixedAssets.carYears}</option><c:forEach var="i" begin="1980" end="2015" step="1"><option  value="${i}" >${i}</option></c:forEach> 
																  </select></td></tr>
							<tr><td class="td8">车牌号码：</td><td><input class="chars20" type="text" id="carNo" name="carNo" value="${fixedAssets.carNo}"/></td></tr>
							<tr><td class="td8">自有汽车：</td><td><select name="isapproveCarMortgage" id="isapproveCarMortgage"><option value="0" <c:if test="${fixedAssets.isapproveCarMortgage=='0'}">selected</c:if>>购买完毕</option><option value="1" <c:if test="${fixedAssets.isapproveCarMortgage=='1'}">selected</c:if>>仍在按揭</option></select></td></tr>
							<tr><td class="td8">每月按揭金额(汽车)：</td><td><input type="text" name="carMonthMortgage" id="carMonthMortgage" value="${fixedAssets.carMonthMortgage}" class="fl money"/><span class="fl">&nbsp;元</span></td></tr>
						</table>
						<input type="hidden" name="infoId" value="${fixedAssets.infoId }" />
						<input type="hidden" name="userId" value="${fixedAssets.userId }" />
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
						<input type="button" class="btn3 mar10" id="fixBtn" value="保存"/>
						</form:form>