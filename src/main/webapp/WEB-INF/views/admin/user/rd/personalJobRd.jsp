<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function creShiOption()
{
	
	var sheng=$("#jobProvince").val();
			$.ajax( {
				url : "${ctx}//admin/user/queryArea?code="+sheng,
				type : "POST",
				dataType : 'JSON',
				success : function(strFlg) {
					$("#jobCity").empty();	
					for(var i=0;i<strFlg.length;i++)
					{
						$("#jobCity").append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
			});
}



$(function(){
	$('#jobBtn').unbind('click');
	$("#jobBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".jobinputForm").serialize(),
		    		 url: "${ctx}/admin/user/savePersonalJob",
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


<form:form class="jobinputForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;">
	<table>
		<tr>
			<td align="right"  width="190" >公司名称:</td>
			<td align="left">
				<input id="corporationName"	name="userInfoJob.corporationName" type="text"	value="${userbasic.userInfoJob.corporationName}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>公司类别:</td>
			<td>
				<select id="corporationKind" name="userInfoJob.corporationKind" disabled="disabled" >
					<option value="1"
						<c:if test="${userbasic.userInfoJob.corporationName==1}" >selected="selected"</c:if>>国家机关</option>
					<option value="2"
						<c:if test="${userbasic.userInfoJob.corporationName==2}" >selected="selected"</c:if>>事业单位</option>
					<option value="3"
						<c:if test="${userbasic.userInfoJob.corporationName==3}" >selected="selected"</c:if>>央企（包含下级单位）</option>
					<option value="4"
						<c:if test="${userbasic.userInfoJob.corporationName==4}" >selected="selected"</c:if>>地方国资委直属企业</option>
					<option value="5"
						<c:if test="${userbasic.userInfoJob.corporationName==5}" >selected="selected"</c:if>>世界500强（包含合资企业及下级单位）</option>
					<option value="6"
						<c:if test="${userbasic.userInfoJob.corporationName==6}" >selected="selected"</c:if>>外资企业（包含合资企业）</option>
					<option value="7"
						<c:if test="${userbasic.userInfoJob.corporationName==7}" >selected="selected"</c:if>>上市公司（包含国外上市）</option>
					<option value="8"
						<c:if test="${userbasic.userInfoJob.corporationName==8}" >selected="selected"</c:if>>民营企业</option>
			</select></td>

		</tr>
		<tr>
			<td align="right" nowrap>公司行业:</td>
			<td align="left">
				<select id="corporationIndustry" name="userInfoJob.corporationIndustry" disabled="disabled" >
					<option value="11"	<c:if test="${userbasic.userInfoJob.corporationIndustry==11}">selected</c:if>>农</option>
					<option value="12"	<c:if test="${userbasic.userInfoJob.corporationIndustry==12}">selected</c:if>>林</option>
					<option value="13"	<c:if test="${userbasic.userInfoJob.corporationIndustry==13}">selected</c:if>>牧</option>
					<option value="14"  <c:if test="${userbasic.userInfoJob.corporationIndustry==14}">selected</c:if>>渔业</option>
					<option value="15"	<c:if test="${userbasic.userInfoJob.corporationIndustry==15}">selected</c:if>>制造业</option>
					<option value="16"	<c:if test="${userbasic.userInfoJob.corporationIndustry==16}">selected</c:if>>电力</option>
					<option value="17"	<c:if test="${userbasic.userInfoJob.corporationIndustry==17}">selected</c:if>>燃气及水的生产和供应业</option>
					<option value="18"	<c:if test="${userbasic.userInfoJob.corporationIndustry==18}">selected</c:if>>建筑业</option>
					<option value="19"	<c:if test="${userbasic.userInfoJob.corporationIndustry==19}">selected</c:if>>交通运输</option>
					<option value="20"	<c:if test="${userbasic.userInfoJob.corporationIndustry==20}">selected</c:if>>仓储和邮政业</option>
					<option value="21"	<c:if test="${userbasic.userInfoJob.corporationIndustry==21}">selected</c:if>>信息传输</option>
					<option value="22"	<c:if test="${userbasic.userInfoJob.corporationIndustry==22}">selected</c:if>>计算机服务和软件业</option>
					<option value="23"	<c:if test="${userbasic.userInfoJob.corporationIndustry==23}">selected</c:if>>批发零售业</option>
					<option value="24"	<c:if test="${userbasic.userInfoJob.corporationIndustry==24}">selected</c:if>>金融业</option>
					<option value="25"	<c:if test="${userbasic.userInfoJob.corporationIndustry==25}">selected</c:if>>房地产业</option>
					<option value="26"	<c:if test="${userbasic.userInfoJob.corporationIndustry==26}">selected</c:if>>采矿业</option>
					<option value="27"	<c:if test="${userbasic.userInfoJob.corporationIndustry==27}">selected</c:if>>租赁和商务服务业</option>
					<option value="28"	<c:if test="${userbasic.userInfoJob.corporationIndustry==28}">selected</c:if>>科学研究</option>
					<option value="29"	<c:if test="${userbasic.userInfoJob.corporationIndustry==29}">selected</c:if>>技术服务和地址堪查业</option>
					<option value="30"	<c:if test="${userbasic.userInfoJob.corporationIndustry==30}">selected</c:if>>水利</option>
					<option value="31"	<c:if test="${userbasic.userInfoJob.corporationIndustry==31}">selected</c:if>>环境好公共设施管理业</option>
					<option value="32"	<c:if test="${userbasic.userInfoJob.corporationIndustry==32}">selected</c:if>>居民服务和其他服务业</option>
					<option value="33"	<c:if test="${userbasic.userInfoJob.corporationIndustry==33}">selected</c:if>>教育</option>
					<option value="34"	<c:if test="${userbasic.userInfoJob.corporationIndustry==34}">selected</c:if>>卫生</option>
					<option value="35"	<c:if test="${userbasic.userInfoJob.corporationIndustry==35}">selected</c:if>>社会保障和社会福利业</option>
					<option value="36"	<c:if test="${userbasic.userInfoJob.corporationIndustry==36}">selected</c:if>>文化</option>
					<option value="37"	<c:if test="${userbasic.userInfoJob.corporationIndustry==37}">selected</c:if>>体育和娱乐业</option>
					<option value="38"	<c:if test="${userbasic.userInfoJob.corporationIndustry==38}">selected</c:if>>公共管理和社会组织</option>
					<option value="39"	<c:if test="${userbasic.userInfoJob.corporationIndustry==39}">selected</c:if>>国际组织</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>公司规模:</td>
			<td align="left">
				<select id="corporationScale" name="userInfoJob.corporationScale" disabled="disabled" >
					<option value="40" <c:if test="${userbasic.userInfoJob.corporationScale==40}">selected</c:if>>10人以下</option>
					<option value="41" <c:if test="${userbasic.userInfoJob.corporationScale==41}">selected</c:if>>10-100人</option>
					<option value="42" <c:if test="${userbasic.userInfoJob.corporationScale==42}">selected</c:if>>100-500人</option>
					<option value="43" <c:if test="${userbasic.userInfoJob.corporationScale==43}">selected</c:if>>500人以上</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>职位:</td>
			<td align="left">
				<input id="position" name="userInfoJob.position"type="text" value="${userbasic.userInfoJob.position}"	onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>工作城市:</td>
			<td align="left">
					<select name="userInfoJob.jobProvince" id="jobProvince" onchange="creShiOption()" disabled="disabled" >
                             <c:forEach var="sheng" items="${shengList}">
                               	<option value="${sheng.id}"  <c:if test="${userbasic.userInfoJob.jobProvince==sheng.id}" >selected="selected"</c:if>>${sheng.name}</option>
                             </c:forEach>
                       </select>
                       <select name="userInfoJob.jobCity" id="jobCity" disabled="disabled" >
                       		<c:forEach var="jobShi" items="${jobShiList}">
                            	<option value="${jobShi.id}" <c:if test="${userbasic.userInfoJob.jobCity==jobShi.id}" >selected="selected"</c:if> >${jobShi.name}</option>
                             </c:forEach>
                       </select>
				
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>现单位工作年限:</td>
			<td align="left">
				<select id="presentCorporationWorkTime" name="userInfoJob.presentCorporationWorkTime" disabled="disabled" >
					<option value="72" <c:if test="${userbasic.userInfoJob.presentCorporationWorkTime==72}">selected</c:if>>1年以内</option>
					<option value="73" <c:if test="${userbasic.userInfoJob.presentCorporationWorkTime==73}">selected</c:if>>1到3年</option>
					<option value="74" <c:if test="${userbasic.userInfoJob.presentCorporationWorkTime==74}">selected</c:if>>3到5年</option>
					<option value="75" <c:if test="${userbasic.userInfoJob.presentCorporationWorkTime==75}">selected</c:if>>5到10年</option>
					<option value="76" <c:if test="${userbasic.userInfoJob.presentCorporationWorkTime==76}">selected</c:if>>10年以上</option>
				</select>
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>固定电话:</td>
			<td align="left">
				<input id=corporationPhoneArea	name="userInfoJob.corporationPhoneArea" type="text"	value="${userbasic.userInfoJob.corporationPhoneArea}" onkeyup="check(this)" disabled="disabled" /> 
				<input id=corporationPhone	name="userInfoJob.corporationPhone" type="text"	value="${userbasic.userInfoJob.corporationPhone}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>工作邮箱:</td>
			<td align="left">
				<input id="jobEmail" name="userInfoJob.jobEmail" type="text" value="${userbasic.userInfoJob.jobEmail}"	onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>公司地址:</td>
			<td align="left">
				<input id="corporationAddress"	name="userInfoJob.corporationAddress" type="text" value="${userbasic.userInfoJob.corporationAddress}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>公司网址:</td>
			<td align="left">
				<input id="corporationWeb"	name="userInfoJob.corporationWeb" type="text" value="${userbasic.userInfoJob.corporationWeb}" onkeyup="check(this)" disabled="disabled" />
			</td>
		</tr>

	</table>
	<br>
	<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" disabled="disabled" />
	<input id="infoId" name="userInfoJob.workInfoId" type="hidden" value="${userbasic.userInfoJob.workInfoId}" disabled="disabled" />
</form:form>