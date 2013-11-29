<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script language="javascript" type="text/javascript" src="${ctx}/static/datePicker/WdatePicker.js"></script>
<script language="javascript" type="text/javascript">



$(function(){
	$("#btn3").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $("#inputForm").serialize(),
		    		 url: "${ctx}/personal/personal/saveSyyz",
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
<form:form id="inputForm" modelAttribute="privateProprietor"
	action="${ctx}/personal/personal/saveSyyz" method="post">

	<div class="prompt6">
		<p>
			请填写以下私营业主资料：（注：带*为必填）
		</p>
	</div>
	<table>
		<tr>
			<td class="td8">
				私营企业类型：
			</td>
			<td>
				<select id="enterpriseKind" name="enterpriseKind">
					<c:if test="${pp.enterpriseKind == 11}">
					<option value="11">农</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 12}">
					<option value="12">林</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 13}">
					<option value="13">牧</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 14}">
					<option value="14">渔业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 15}">
					<option value="15">制造业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 16}">
					<option value="16">电力</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 17}">
					<option value="17">燃气及水的生产和供应业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 18}">
						<option value="18">建筑业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind ==19}">
					 <option value="19">交通运输</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 20}">
					<option value="20">仓储和邮政业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 21}">
					<option value="21">信息传输</option>
					</c:if>
					<c:if test="${pp.enterpriseKind ==22}">
					<option value="22">计算机服务和软件业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 23}">
						<option value="23">批发零售业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 24}">
					<option value="24">金融业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 25}">
					<option value="25">房地产业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 26}">
					<option value="26">采矿业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 27}">
					<option value="27">租赁和商务服务业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 28}">
					<option value="28">科学研究</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 29}">
					<option value="29">技术服务和地址堪查业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 30}">
						<option value="30">水利</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 31}">
						<option value="31">环境好公共设施管理业</option>
					</c:if>
					
					<c:if test="${pp.enterpriseKind == 32}">
						<option value="32">居民服务和其他服务业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 33}">
							<option value="33">教育</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 34}">
						<option value="34">卫生</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 35}">
						<option value="35">社会保障和社会福利业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 36}">
						<option value="36">文化</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 37}">
						<option value="37">体育和娱乐业</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 38}">
						<option value="38">公共管理和社会组织</option>
					</c:if>
					<c:if test="${pp.enterpriseKind == 39}">
						<option value="39">国际组织</option>
					</c:if>
					<option value="11">农</option>
					<option value="12">林</option>
					<option value="13">牧</option>
					<option value="14">渔业</option>
					<option value="15">制造业</option>
					<option value="16">电力</option>
					<option value="17">燃气及水的生产和供应业</option>
					<option value="18">建筑业</option>
					<option value="19">交通运输</option>
					<option value="20">仓储和邮政业</option>
					<option value="21">信息传输</option>
					<option value="22">计算机服务和软件业</option>
					<option value="23">批发零售业</option>
					<option value="24">金融业</option>
					<option value="25">房地产业</option>
					<option value="26">采矿业</option>
					<option value="27">租赁和商务服务业</option>
					<option value="28">科学研究</option>
					<option value="29">技术服务和地址堪查业</option>
					<option value="30">水利</option>
					<option value="31">环境好公共设施管理业</option>
					<option value="32">居民服务和其他服务业</option>
					<option value="33">教育</option>
					<option value="34">卫生</option>
					<option value="35">社会保障和社会福利业</option>
					<option value="36">文化</option>
					<option value="37">体育和娱乐业</option>
					<option value="38">公共管理和社会组织</option>
					<option value="39">国际组织</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td8">
				成立日期：
			</td>
			<td>
				<input class="chars20" type="text" id="establishDate" name="establishDate" onClick="WdatePicker({dateFmt:'yyyy/MM/dd'})" value="${pp.dateStr}"/>
			</td>
		</tr>
		<tr>
			<td class="td8">
				经营场所：
			</td>
			<td>
				<select id="enterpriseAddress" name="enterpriseAddress">
					<c:if test="${pp.enterpriseAddress == 44}">
					<option value="44">自有</option>
					</c:if>
					<c:if test="${pp.enterpriseAddress == 45}">
					<option value="45">租赁</option>
					</c:if>
					<c:if test="${pp.enterpriseAddress == 46}">
					<option value="46">按揭</option>
					</c:if>
					<option value="44">自有</option>
					<option value="45">租赁</option>
					<option value="46">按揭</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="td8">
				租金：
			</td>
			<td>
				<input type="text"  id="rent" name="rent" value="${pp.rent}" class="money"/>
				<span>&nbsp;元</span>
			</td>
		</tr>
		<tr>
			<td class="td8">
				租期：
			</td>
			<td>
				<input type="text"  id="lease" name="lease" value="${pp.lease}" class="num"/>
				<span>&nbsp;个月</span>
			</td>
		</tr>
		<tr>
			<td class="td8">
				税务编号：
			</td>
			<td>
				<input class="chars50" type="text" id="taxationNo" name="taxationNo" value="${pp.taxationNo}"/>
			</td>
		</tr>
		<tr>
			<td class="td8">
				工商登记号：
			</td>
			<td>
				<input class="chars50" type="text" id="gsBookNo" name="gsBookNo" value="${pp.gsBookNo}"/>
			</td>
		</tr>
		<tr>
			<td class="td8">
				全年盈利/亏损额：
			</td>
			<td>
				<input type="text" id="profitOrLossAmount" name="profitOrLossAmount" value="${pp.profitOrLossAmount}" class="money"/>
				<span>&nbsp;元/年</span>
			</td>
		</tr>
		<tr>
			<td class="td8">
				雇员人数：
			</td>
			<td>
				<input type="text" id="employeeAmount" name="employeeAmount" value="${pp.employeeAmount}" class="num2"/>
				<span>&nbsp;人</span>
			</td>
		</tr>
	</table>
	<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
	<input type="button" class="btn3 mar10" value="保存" id="btn3"/>
</form:form>