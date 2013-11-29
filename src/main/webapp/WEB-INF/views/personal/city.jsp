<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title></title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

  </head>
  
  <body>
  <script type="text/javascript">
$(document).ready(function(){
	$("#pro_id").change(function(){
	 	var id = $("#pro_id").val();
	 	if(id==0){
	 		return;
	 	}
	    $.ajax({
        url: "/online/personal/personal/queryCity?id="+id,
        type: "POST",
        dataType: 'html',
        success: function(data){
	    	     $("#hometown").html(data);
        },
        error: function(response){
           
        }
    });
	});
});
</script>
  <select id="pro_id">
							<c:forEach items="${provinceList}" var="pro">
							  <option value ="${pro[1]}" <c:if test="${Provinceid==pro[1]}">selected </c:if>>${pro[0]}</option>
							 </c:forEach>  
							 </select>
  <select id="city_id">
							<c:forEach items="${citylist}" var="city">
							  <option value ="${city[1]}">${city[0]}   </option>
							 </c:forEach>  
							 </select>

  </body>
</html>