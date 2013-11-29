<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<script language="javascript" type="text/javascript">
$(function(){
	$('#eduBtn').unbind('click');
	$("#eduBtn").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".personalEduForm").serialize(),
		    		 url: "${ctx}/admin/user/savePersonalEdu",
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

<form:form class="personalEduForm"  enctype="multipart/form-data" method="post" style="margin-bottom:0;">
	<table>
		<tr>
			<td align="right"  width="190" >最高学历:</td>
			<td align="left"><select id="maxDegree" name="userInfoPerson.maxDegree">
					<option value="47"
						<c:if test="${userbasic.userInfoPerson.maxDegree==47}" >selected="selected"</c:if>>小学</option>
					<option value="48"
						<c:if test="${userbasic.userInfoPerson.maxDegree==48}" >selected="selected"</c:if>>初中</option>
					<option value="49"
						<c:if test="${userbasic.userInfoPerson.maxDegree==49}" >selected="selected"</c:if>>高中</option>
					<option value="50"
						<c:if test="${userbasic.userInfoPerson.maxDegree==50}" >selected="selected"</c:if>>大专</option>
					<option value="51"
						<c:if test="${userbasic.userInfoPerson.maxDegree==51}" >selected="selected"</c:if>>本科</option>
					<option value="52"
						<c:if test="${userbasic.userInfoPerson.maxDegree==52}" >selected="selected"</c:if>>硕士</option>
					<option value="53"
						<c:if test="${userbasic.userInfoPerson.maxDegree==53}" >selected="selected"</c:if>>博士</option>
					<option value="54"
						<c:if test="${userbasic.userInfoPerson.maxDegree==54}" >selected="selected"</c:if>>博士后</option>
					<option value="55"
						<c:if test="${userbasic.userInfoPerson.maxDegree==55}" >selected="selected"</c:if>>其他</option>
			</select></td>
		</tr>
		<tr>
			<td align="right" nowrap>毕业院校:</td>
			<td align="left">
				<input id="graduatSchool" name="userInfoPerson.graduatSchool"	type="text" value="${userbasic.userInfoPerson.graduatSchool}" onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>入学年份:</td>
			<td align="left">
				<select name="userInfoPerson.inschoolTime">
					<option value="${userbasic.userInfoPerson.inschoolTime}">${userbasic.userInfoPerson.inschoolTime}</option>
					<c:forEach var="i" begin="1960" end="2015" step="1">
						<option value="${i}">${i}</option>
					</c:forEach>
			</select></td>
		</tr>
		<tr>
			<td align="right" nowrap>职称一:</td>
			<td align="left">
				<input id="jobTitleOne" name="userInfoPerson.jobTitleOne"	type="text" value="${userbasic.userInfoPerson.jobTitleOne}"	onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>职称二:</td>
			<td align="left">
				<input id="jobTitleTwo" name="userInfoPerson.jobTitleTwo" type="text" value="${userbasic.userInfoPerson.jobTitleTwo}" onkeyup="check(this)" />
			</td>
		</tr>
		<tr>
			<td align="right" nowrap>职称三:</td>
			<td align="left">
				<input id="jobTitleThr" name="userInfoPerson.jobTitleThr"	type="text" value="${userbasic.userInfoPerson.jobTitleThr}"	onkeyup="check(this)" />
			</td>
		</tr>

	</table>
	<input id="userId" name="userId" type="hidden" value="${userbasic.userId}" />
	<input id="infoId" name="userInfoPerson.infoId" type="hidden" value="${userbasic.userInfoPerson.infoId}" />
	<div style="margin:0 auto; width:80px;"><a id="eduBtn" class="easyui-linkbutton" iconCls="icon-ok">保存</a></div>
</form:form>