<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/admin/css/icon.css">
<script type="text/javascript" src="${ctx}/static/admin/js/jquery-1.7.2.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/easyui-lang-zh_CN.js"></script>

<script type="text/javascript" src="${ctx}/static/js/checkInput.js"></script>
<script type="text/javascript">

//处理【会员管理】>>【资料审核】共用的JS方法 add by zy
function formatterUserDetailForInfoApp(val, row) {
	url='${ctx}/admin/user/userInfoPersonPageJsp?userId='+row.userId
	return '<a href="'+url+'" target="_blank" >'+val+'</a>';
}

//处理【提现管理】共用的JS方法 add by zy
function formatterUserDetailForTakeMoney(val, row) {
	url='${ctx}/admin/user/userInfoPersonPageRdJsp?userId='+row.userId
	return '<a href="'+url+'" target="_blank" >'+val+'</a>';
}

//处理【借款管理】共用的JS方法 add by zy
function formatterUserDetailForLoan(val, row) {
	url='${ctx}/admin/user/userInfoPersonPageRdJsp?userId='+row.loanUserId
	return '<a href="'+url+'" target="_blank" >'+val+'</a>';
}
</script>
