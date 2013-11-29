<%@ page contentType="text/html;charset=utf-8"%>
<html>
<head>
<title>提示</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />

</head>
<body>
<div class="msg_box width5 height6">
	<div class="msg_t">
		<a onclick="closefancy()" class="msg_close"></a>
		<img src="${ctx}/images/img94.gif" />
		提示
	</div>
	<div class="msg_c">
		<p class="set_delete">您当前的可用余额不足5元，<br />无法进行身份验证，请先充值！</p>
		<input type="button" class="btn3 btn3_msg" value="取消" onclick="closefancy()" /><input type="button" class="btn3 btn3_msg" value="确定" onclick="redirectfancy()" />
	</div>
</div>
</body>
</html>