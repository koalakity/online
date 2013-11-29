<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
function mysubmit(){
	$.ajax({
	 	data: $("#inputForm").serialize(),
   		 url: "${ctx}/personal/personal/saveEduInfo",
   	 	 type: "POST",
   		 dataType: 'json',
   		 timeout: 10000,
    	 error: function(){
        	 alert("保存失败！");
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
}
</script>
						<div class="prompt6">
							<p>请填写以下教育职称信息：（注：带*为必填）</p>
						</div>
						<form:form id="inputForm"   modelAttribute="userInfoPerson"  name="inputForm"  method="post" action="${ctx}/personal/personal/saveEduInfo" >
						<table>
							<tr>
								<td class="td8">最高学历：</td>
									<td>
										<select name="maxDegree">
											<option value="47" <c:if test="${eduInfo.maxDegree=='47'}">selected</c:if>>小学</option>
											<option value="48" <c:if test="${eduInfo.maxDegree=='48'}">selected</c:if>>初中</option>
											<option value="49" <c:if test="${eduInfo.maxDegree=='49'}">selected</c:if>>高中</option>
											<option value="50" <c:if test="${eduInfo.maxDegree=='50'}">selected</c:if>>大专</option>
											<option value="51" <c:if test="${eduInfo.maxDegree=='51'}">selected</c:if>>本科</option>
											<option value="52" <c:if test="${eduInfo.maxDegree=='52'}">selected</c:if>>硕士</option>
											<option value="53" <c:if test="${eduInfo.maxDegree=='53'}">selected</c:if>>博士</option>
											<option value="54" <c:if test="${eduInfo.maxDegree=='54'}">selected</c:if>>博士后</option>
											<option value="55" <c:if test="${eduInfo.maxDegree=='55'}">selected</c:if>>其他</option>
										</select>
									</td>
								</tr>
								<tr>
									<td class="td8">毕业院校：</td>
									<td><input type="text" name="graduatSchool" value="${eduInfo.graduatSchool}" class="chars100"/></td>
								</tr>
								<tr>
									<td class="td8">入学年份：</td>
									<td>
										<select name="inschoolTime">
											<option  value="${eduInfo.inschoolTime}" >${eduInfo.inschoolTime}</option><c:forEach var="i" begin="1960" end="2015" step="1"><option  value="${i}" >${i}</option></c:forEach> 
										</select>
									</td>
								</tr>
								<tr>
									<td class="td8">职称一：</td>
									<td><input type="text" name="jobTitleOne" value="${eduInfo.jobTitleOne}" class="chars100"/></td>
								</tr>
								<tr>
									<td class="td8">职称二：</td>
									<td><input type="text" name="jobTitleTwo" value="${eduInfo.jobTitleTwo}" class="chars100"/></td>
								</tr>
								<tr>
									<td class="td8">职称三：</td>
									<td><input type="text" name="jobTitleThr" value="${eduInfo.jobTitleThr}" class="chars100"/></td>
								</tr>
						</table>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
								<input type="button" class="btn3 mar10" value="保存" onclick="mysubmit()" />
					   </form:form>