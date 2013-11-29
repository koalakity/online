<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script type="text/javascript">
function creShiOption()
{
	
	var sheng=$("#jobProvince").val();
			$.ajax( {
				url : "${ctx}/personal/personal/queryArea?code="+sheng,
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
	$("#btn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $("#jobinputForm").serialize(),
		    		 url: "${ctx}/personal/personal/saveJobInfo",
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
<form:form id="jobinputForm" modelAttribute="userInfoJob"
	action="${ctx}/personal/personal/saveJobInfo" method="post">
<div class="prompt6">
	<p>
		请填写以下工作信息：（注：带*为必填）
	</p>
</div>
<table>
	<tr>
		<td class="td8">
			公司名称：
		</td>
		<td>
			<input class="chars50"  type="text" id="corporationName" name="corporationName" value="${job.corporationName }"/>
		</td>
	</tr>
	<tr>
		<td class="td8">
			公司类别：
		</td>
		<td>
			<select id="corporationKind" name="corporationKind">
				<option value="1" <c:if test="${job.corporationKind==1}" >selected="selected"</c:if>>国家机关</option>
				<option value="2" <c:if test="${job.corporationKind==2}" >selected="selected"</c:if>>事业单位</option>
				<option value="3" <c:if test="${job.corporationKind==3}" >selected="selected"</c:if>>央企（包含下级单位）</option>
				<option value="4" <c:if test="${job.corporationKind==4}" >selected="selected"</c:if>>地方国资委直属企业</option>
				<option value="5" <c:if test="${job.corporationKind==5}" >selected="selected"</c:if>>世界500强（包含合资企业及下级单位）</option>
				<option value="6" <c:if test="${job.corporationKind==6}" >selected="selected"</c:if>>外资企业（包含合资企业）</option>
				<option value="7" <c:if test="${job.corporationKind==7}" >selected="selected"</c:if>>上市公司（包含国外上市）</option>
				<option value="8" <c:if test="${job.corporationKind==8}" >selected="selected"</c:if>>民营企业</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td8">
			公司行业：
		</td>
		<td>
			<select id="corporationIndustry" name="corporationIndustry">
				<option value="11" <c:if test="${job.corporationIndustry==11}" >selected="selected"</c:if>>农</option>
				<option value="12" <c:if test="${job.corporationIndustry==12}" >selected="selected"</c:if>>林</option>
				<option value="13" <c:if test="${job.corporationIndustry==13}" >selected="selected"</c:if>>牧</option>
				<option value="14" <c:if test="${job.corporationIndustry==14}" >selected="selected"</c:if>>渔业</option>
				<option value="15" <c:if test="${job.corporationIndustry==15}" >selected="selected"</c:if>>制造业</option>
				<option value="16" <c:if test="${job.corporationIndustry==16}" >selected="selected"</c:if>>电力</option>
				<option value="17" <c:if test="${job.corporationIndustry==17}" >selected="selected"</c:if>>燃气及水的生产和供应业</option>
				<option value="18" <c:if test="${job.corporationIndustry==18}" >selected="selected"</c:if>>建筑业</option>
				<option value="19" <c:if test="${job.corporationIndustry==19}" >selected="selected"</c:if>>交通运输</option>
				<option value="20" <c:if test="${job.corporationIndustry==20}" >selected="selected"</c:if>>仓储和邮政业</option>
				<option value="21" <c:if test="${job.corporationIndustry==21}" >selected="selected"</c:if>>信息传输</option>
				<option value="22" <c:if test="${job.corporationIndustry==22}" >selected="selected"</c:if>>计算机服务和软件业</option>
				<option value="23" <c:if test="${job.corporationIndustry==23}" >selected="selected"</c:if>>批发零售业</option>
				<option value="24" <c:if test="${job.corporationIndustry==24}" >selected="selected"</c:if>>金融业</option>
				<option value="25" <c:if test="${job.corporationIndustry==25}" >selected="selected"</c:if>>房地产业</option>
				<option value="26" <c:if test="${job.corporationIndustry==26}" >selected="selected"</c:if>>采矿业</option>
				<option value="27" <c:if test="${job.corporationIndustry==27}" >selected="selected"</c:if>>租赁和商务服务业</option>
				<option value="28" <c:if test="${job.corporationIndustry==28}" >selected="selected"</c:if>>科学研究</option>
				<option value="29" <c:if test="${job.corporationIndustry==29}" >selected="selected"</c:if>>技术服务和地址勘查业</option>
				<option value="30" <c:if test="${job.corporationIndustry==30}" >selected="selected"</c:if>>水利</option>
				<option value="31" <c:if test="${job.corporationIndustry==31}" >selected="selected"</c:if>>环境和公共设施管理业</option>
				<option value="32" <c:if test="${job.corporationIndustry==32}" >selected="selected"</c:if>>居民服务和其他服务业</option>
				<option value="33" <c:if test="${job.corporationIndustry==33}" >selected="selected"</c:if>>教育</option>
				<option value="34" <c:if test="${job.corporationIndustry==34}" >selected="selected"</c:if>>卫生</option>
				<option value="35" <c:if test="${job.corporationIndustry==35}" >selected="selected"</c:if>>社会保障和社会福利业</option>
				<option value="36" <c:if test="${job.corporationIndustry==36}" >selected="selected"</c:if>>文化</option>
				<option value="37" <c:if test="${job.corporationIndustry==37}" >selected="selected"</c:if>>体育和娱乐业</option>
				<option value="38" <c:if test="${job.corporationIndustry==38}" >selected="selected"</c:if>>公共管理和社会组织</option>
				<option value="39" <c:if test="${job.corporationIndustry==39}" >selected="selected"</c:if>>国际组织</option>
				
			</select>
		</td>
	</tr>
	<tr>
		<td class="td8">
			公司规模：
		</td>
		<td>
			<select id="corporationScale" name="corporationScale">
				<option value="40" <c:if test="${job.corporationScale==40}" >selected="selected"</c:if>>
					10人以下
				</option>
				<option value="41" <c:if test="${job.corporationScale==41}" >selected="selected"</c:if>>
					10-100人
				</option>
				<option value="42" <c:if test="${job.corporationScale==42}" >selected="selected"</c:if>>
					100-500人
				</option>
				<option value="43" <c:if test="${job.corporationScale==43}" >selected="selected"</c:if>>
					500人以上
				</option>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td8">
			职位：
		</td>
		<td>
			<input class="chars50"  type="text" id="position" name="position" value="${job.position}"/>
		</td>
	</tr>
	<tr>
		<td class="td8">
			工作城市：
		</td>
		<td>
			<select id="jobProvince" name="jobProvince" onchange="creShiOption()">
				<c:forEach var="sheng" items="${shengList}">
                 	<option value="${sheng.id}"  <c:if test="${job.jobProvince==sheng.id}" >selected="selected"</c:if>>${sheng.name}</option>
                 </c:forEach>
			</select>
			<select id="jobCity" name="jobCity">
					<c:forEach var="shi" items="${shiList}">
                     	<option value="${shi.id}" <c:if test="${job.jobCity==shi.id}" >selected="selected"</c:if> >${shi.name}</option>
                     </c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td class="td8">
			现单位工作年限：
		</td>
		<td>
			<select id="presentCorporationWorkTime" name="presentCorporationWorkTime">
				<option value="72" <c:if test="${job.presentCorporationWorkTime==72}" >selected="selected"</c:if>>
					1年以内
				</option>
				<option value="73" <c:if test="${job.presentCorporationWorkTime==73}" >selected="selected"</c:if>>
					1到3年
				</option>
				<option value="74" <c:if test="${job.presentCorporationWorkTime==74}" >selected="selected"</c:if>>
					3到5年
				</option>
				<option value="75" <c:if test="${job.presentCorporationWorkTime==75}" >selected="selected"</c:if>>
					5到10年
				</option>
				<option value="76" <c:if test="${job.presentCorporationWorkTime==76}" >selected="selected"</c:if>>
					10年以上
				</option>
			</select>
			<!--<input type="text" id="presentCorporationWorkTime" name="presentCorporationWorkTime" value="${job.presentCorporationWorkTime}" class="num"/>
			<span>&nbsp;年</span>-->
		</td>
	</tr>
	<tr>
		<td class="td8">
			固定电话：
		</td>
		<td>
			<input class="input_40 required_db3" type="text" id="corporationPhoneArea" name="corporationPhoneArea" value="${job.corporationPhoneArea}">
			-
			<input class="input_120 required_db4" type="text" id="corporationPhone" name="corporationPhone" value="${job.corporationPhone}"/>
		</td>
	</tr>
	<tr>
		<td class="td8">
			工作邮箱：
		</td>
		<td>
			<input type="text" id="jobEmail" name="jobEmail" value="${job.jobEmail}" class="email"/>
		</td>
	</tr>
	<tr>
		<td class="td8">
			公司地址：
		</td>
		<td>
			<input class="chars100" type="text" id="corporationAddress" name="corporationAddress" value="${job.corporationAddress}"/>
		</td>
	</tr>
	<tr>
		<td class="td8">
			公司网址：
		</td>
		<td>
			<input class="chars100" type="text" id="corporationWeb" name="corporationWeb" value="${job.corporationWeb}"/>
		</td>
	</tr>
</table>
<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
<input type="button" class="btn3 mar10" value="保存" id="btn"/>
</form:form>