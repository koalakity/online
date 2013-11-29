<%@page language="java" pageEncoding="utf-8"%>
<%response.setStatus(200);%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>证大e贷微金融服务平台</title>
<style type="text/css">
body, dl, dt, dd, ul, ol, li, h1, h2, h3, h4, h5, h6, hr, form, input, p, th, td { margin:0; padding:0;}
body { color:#444; background:#fff; font:12px/2 Tahoma, Helvetica, Arial, sans-serif;}
a:link, a:visited { color:#226cc0; text-decoration:none;}
a:hover, a:active { color:#226cc0; text-decoration:underline;}
.error_top_box, .error_header, .error_content { width:1000px; margin:0 auto;}
.error_top { height:27px; line-height:27px; border-bottom:1px solid #ececec; background:#f9f9f9;}
.error_top_box { text-align:right;}
.error_top_box a:link, .error_top_box a:visited { color:#444;}
.error_top_box a:hover, .error_top_box a:active { color:#444;}
.error_header { height:111px;}
.error_header h1 { float:left; width:164px; height:46px; margin:30px 0; padding-left:25px; overflow:hidden;}
.error_header h1 a { display:block; width:164px; height:46px; background: url(${ctx}/static/images/logo.jpg) no-repeat; text-indent:-9999px;}
.error_content { background:url(${context}/static/images/error.jpg) no-repeat center 50px; width:575px; height:380px; padding:120px 0 0 425px; border-top:1px solid #ececec;}
.content_404 { background:url(${context}/static/images/404.jpg) no-repeat center 50px; width:545px; padding-left:455px;}
.error_footer { width:100%; text-align:center; line-height:30px; padding:20px 0; border-top:1px solid #ececec; position:absolute; bottom:0;}
</style>
</head>

<body>

<div class="error_header"><h1><a href="#">logo</a></h1></div>
<div class="error_content">
	<p style="font-size:14px;">客服服务热线：400-821-6888转4</p>
	<p>请勿重复提交</p>
	<a href="${context}">·返回证大e贷首页（www.onlinecredit.cn）</a><br />
	<a href="${context}/myAccount/myAccount/showMyAccount?strUrlType=fundDetail">·返回我的账户首页</a>
</div>
<div class="error_footer">copyright &copy; 2012 证大财富 All rights reserved.</div>
</body>

</body>
</html>
