<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head>
 <script type="text/javascript">
  $(function(){
   initCheckBox();
   init();
  });
   function initCheckBox(){
     $("input[name='message']").each(function(i){
          this.checked=true;
          this.disabled=true;
      })
   }
   function saveSet(){
     var checkId = "";
       $("input:checkbox").each(function(i){
        if(this.checked==true){
           checkId =checkId+this.id+";";
        }
     })
     $.post(
		"${ctx}/personal/personal/saveUserMessageSet?checkId="+checkId,
		 function (result){
		 	if(result==true){
		 		alert("保存成功!");
		 	}else{
		 		alert("保存失败!");
		 	}
		},"json");
   }
   
   function init(){
	 $.post('${ctx}/personal/personal/initNotifySet',function(result) {
	 		var ids = result.split(';');
	 		for(var i=0;i<ids.length;i++){
	 			$('#'+ids[i]).attr("checked",'true'); 
	 		}
		}, 'text');
   }
 </script>
</head>
<div class="account_r">
			<div class="account_r_c account_r_c_add">
				<div class="account_r_c_t">
					<h3 class="on">通知设置</h3>
				</div>
				<div class="account_r_c_3c">
					<table class="table1 tc">
						<tr><th colspan="4">我是借入者</th></tr>
						<tr class="even"><td class="td10">&nbsp;</td><td>站内信</td><td>电子邮件</td><td>手机短信</td></tr>
						<tr><td class="tl">有人对我的借款列表提问</td><td><input id="11" type="checkbox" name="message" /></td><td><input id="12" type="checkbox"/></td><td><input id="13" type="checkbox" /></td></tr>
						<tr><td class="tl">有人向我的借款列表投标</td><td><input id="21" type="checkbox" name="message"/></td><td><input id="22" type="checkbox"/></td><td><input id="23" type="checkbox" /></td></tr>
						<tr><td class="tl">我的借款列表流标</td><td><input id="31" type="checkbox" name="message" /></td><td><input id="32" type="checkbox"/></td><td><input id="33" type="checkbox" /></td></tr>
						<tr><td class="tl">我的借款列表完成度超过50%</td><td><input id="41" type="checkbox" name="message"/></td><td><input id="42" type="checkbox"/></td><td><input id="43" type="checkbox" /></td></tr>
					</table>
					<table class="table1 tc">
						<tr><th colspan="4">我是借出者</th></tr>
						<tr class="even"><td class="td10">&nbsp;</td><td>站内信</td><td>电子邮件</td><td>手机短信</td></tr>
						<tr><td class="tl">我的投标成功</td><td><input id="51" type="checkbox" name="message" /></td><td><input id="52" type="checkbox"/></td><td><input id="53" type="checkbox" /></td></tr>
						<tr><td class="tl">我的投标流标</td><td><input id="61" type="checkbox" name="message" /></td><td><input id="62" type="checkbox"/></td><td><input id="63" type="checkbox" /></td></tr>
						<tr><td class="tl">我收到一笔还款</td><td><input id="71" type="checkbox" name="message" /></td><td><input id="72" type="checkbox"/></td><td><input id="73" type="checkbox" /></td></tr>
						<tr><td class="tl">借入者回答了我对借款列表的提问</td><td><input id="81" type="checkbox" name="message" /></td><td><input id="82" type="checkbox"/></td><td><input id="83" type="checkbox" /></td></tr>
					</table>
					<a href="#" class="btn2 mar10" onclick="javascript:saveSet()">保存设置</a>
				</div>
			</div>
=</div>
