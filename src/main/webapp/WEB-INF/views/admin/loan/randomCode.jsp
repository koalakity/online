<%@ page contentType="text/html;charset=UTF-8"%> 
<%@ page autoFlush="false"  import="com.zendaimoney.online.admin.util.ValidateCode" %>
<%
request.setCharacterEncoding("UTF-8");
response.setCharacterEncoding("UTF-8");
response.setContentType("text/html; charset=UTF-8");
ValidateCode vCode = new ValidateCode();
vCode.createCode(request,response,"randomCode");
vCode.getImage();
out.clear();
out=pageContext.pushBody();
out.close();
%>