<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>借款信息查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/admin/js/utils/jquery.poshytip.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/popBox.js"></script>
<link rel="stylesheet" href="${ctx}/static/admin/css/popBox.css" type="text/css" />
<script type="text/javascript">
	function oMemoDlg(){
		$('#d1').dialog('open');
		var obj = $('.panel-title').parent().parent('.window');
		var obj3 = obj.next('.window-shadow');
		var obj2 = obj.next().next('.window-mask');
		var lay_h = $(window).height();
		var d_scr_h = $(document).height()>lay_h?$(document).height():lay_h;
		var scr_h = $(document).scrollTop();
		var obj_h = obj.height();
		var h = (lay_h-obj_h)/2+scr_h;
		obj.css("top" ,h);
		obj3.css("top" ,h);
		obj2.css("height" ,d_scr_h);
	};
		$(function(){
			$('#tip').poshytip({
				className: 'tip-skyblue',
				bgImageFrameSize: 9,
				offsetX: 0,
				offsetY: 20
			});
			$("#memoText").keyup(function(){
    			if($(this).val().length>150){
        			$(this).val($(this).val().substring(0,150));
        			$.messager.alert('消息','字数不能超过150！');   
				}
				});
			
				
		});
		function passAudit(status){
		
			$.post("${ctx}/admin/loan/alertLoanStatus", 
				  { loanId: "${loanInfo.loanId}", status:status},
					function (result){
						
						if (result.success) {
							$.messager.confirm('消息','修改成功返回列表?',function(r){  
						    if (r){  
						        window.close();  
						    }  
							}); 
							} else {
								alert(result.msg);
							}
					}, "json");
		}
		function successBack(){
			$.messager.confirm('消息','修改成功返回列表?',function(r){  
		    if (r){  
		        window.close();  
		    }  
			});  
		}
		
		
		function noteSubmit(){
			var memo = $('#memoText').val();
			var memoLength = memo.length;
			if(memo=='不超过150个汉字！'){
				$.messager.alert('错误','不能为空！');
				return false;  
			};
			if(memoLength>150){
				$.messager.alert('Message','字数不能超过150！');
				return;  
			}
			var memo = $('#memoText').val();
			$.post("${ctx}/admin/loan/saveLoanNote", 
				  { loanId: "${loanInfo.loanId}", memoText:memo},
					function (result){
						if (result.success) {
								$('#memoText').val('不超过150个汉字！');
								$('#d1').dialog('close');
								$('#noteContext').datagrid('reload');
							} else {
								$.messager.show({ // show error message
									title : 'Error',
									msg : result.msg
								});
							}
					}, "json");
		}
		
		function noteReset(){
			$('#memoText').val('不超过150个汉字！');
			$('#d1').dialog('close')
		}
		function memoFormatt(val,row){
			if(val.length>26){
				var innerValue = val.substring(0, 25)+'......'; 
				return '<a id="tip" title="'+val+'">'+innerValue+'</a>';
			}else {
				return val;
			}
		}
</script>
</head>
<body>
	<div id="tab" class="easyui-tabs" style="width:1000px;">  
		<div title="基本信息" style="padding:20px;">  
			<table>
				<tr>
					<td nowrap >借款编号:</td>
					<td>${loanInfo.loanId}</td>
				</tr>
				<tr>
					<td>借&nbsp;款&nbsp;人:</td>
					<td><a href="${ctx}/admin/user/userInfoPersonPageRdJsp?userId=${loanInfo.accountUsers.userId}">${loanInfo.realName}</a></td>
				</tr>
				<tr>
					<td>渠&nbsp;&nbsp;&nbsp;&nbsp;道:</td>
					<td>${channelInfo}</td>
				</tr>
				<tr>
					<td>借款手续费费率:</td>
					<td>${rateInfo.formatLoan}</td>
				</tr>
				<tr>
					<td>风险准备金费率:</td>
					<td>${rateInfo.formatResFoud}</td>
				</tr>
				<tr>
					<td>月缴管理费费率:</td>
					<td>${rateInfo.formatMgmtFee}</td>
				</tr>
				<tr>
					<td>逾期违约金费率:</td>
					<td>${rateInfo.formatOveFin}</td>
				</tr>
				<tr>
					<td>逾期罚息(<=30天)费率 :</td>
					<td>${rateInfo.formatOveInt}</td>
				</tr>
				<tr>
					<td>逾期罚息(>30天)费率:</td>
					<td>${rateInfo.formatOveSerInt}</td>
				</tr>
				<tr>
					<td>一次性提前还款违约金费率:</td>
					<td>${rateInfo.formatEarlyFine}</td>
				</tr>
				<tr>
					<td>图&nbsp;&nbsp;&nbsp;&nbsp;片:</td>
					<td><img src="${ctx}/static/images/${loanInfo.loanUseImage}" width=60px height=60px /></td>
				</tr>
				<tr>
					<td>标&nbsp;&nbsp;题:</td>
					<td>${loanInfo.loanTitle}</td>
				</tr>
				<tr>
					<td>借款用途:</td>
					<td>${loanInfo.loanUseFormatt}</td>
				</tr>
				<tr>
					<td>借款金额:</td>
					<td>${loanInfo.formattLoanAmount}</td>
				</tr>
				<tr>
					<td>借款期限:</td>
					<td>${loanInfo.loanDuration}个月</td>
				</tr>
				<tr>
					<td>年&nbsp;利&nbsp;率:</td>
					<td>${loanInfo.yearRateFormatt}</td>
				</tr>
				<tr>
					<td>还款方式:</td>
					<td>${loanInfo.paymentMethodFormatt}</td>
				</tr>
				<tr>
					<td>月还本息:</td>
					<td>${loanInfo.formattMonthReturnPrincipalandinter}元 </td>
				</tr>
				<tr>
					<td>月缴管理费:</td>
					<td>${loanInfo.formattMonthManageCost}元</td>
				</tr>
				<tr>
					<td>借款描述:</td>
					<td>${loanInfo.description}</td>
				</tr>
			</table> <br/>
			
			
			<a class="easyui-linkbutton" onclick="popupDiv('pop-div',3);">不通过，流标处理</a>	
		</div>  
		<div title="投标列表" style="padding:20px;">
		投标进度
		<div id="progress" class="easyui-progressbar"  value="${loanInfo.progress}" style="width:400px;"></div>  <br/>   
			<table class="easyui-datagrid" url="${ctx}/admin/loan/seachInvestinfo?loanId=${loanInfo.loanId}" singleSelect="true">
				<thead>
					<tr>
						<th field="realName" width="80">投标人</th>
						<th field="investAmountFormatt" width="80" >投标金额</th>
						<th field="monthRepayPrincipal" width="80">月收本息</th>
						<th field="inverestRemainderPrincipal" width="80">剩余本金</th>
					</tr>
				</thead>
			</table> 
		</div>  
		
	</div>
	
	<div style="height:15px; overflow:hidden;"></div>
	<table nowrap="false" id="noteContext" name="noteContext" class="easyui-datagrid" url="${ctx}/admin/loan/seachLoanNote?loanId=${loanInfo.loanId}" title="备注" fitColumns="true" toolbar="#toolbar" style="width:1000px;">
		<thead>
			<tr>
				<th field="memoText" width="100"  >备注内容</th>
				<th field="opearateUser" width="10">操作人</th>
				<th field="operateTimeFormatt" width="20">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true"  onclick="oMemoDlg();">添加备注</a>
	</div>
	<div id="d1" modal="true" class="easyui-dialog" title="添加备注" closed="true" style="padding:5px; width:400px; height:200px;" data-options="buttons:'#dlg-buttons'">
		<textarea id="memoText" name="memoText" style="width:370px; height:110px; font-size:12px;" onfocus="if(this.value=='不超过150个汉字！')this.value=''" onblur="if(this.value=='')this.value='不超过150个汉字！'">不超过150个汉字！</textarea>
	</div>
	<div id="dlg-buttons">
		<a class="easyui-linkbutton" onclick="javascript:noteSubmit()">提交</a>
		<a  class="easyui-linkbutton" onclick="javascript:noteReset()">取消</a>
	</div>
	<!-- 弹出层 -->
	
	<div id='pop-div' style="width: 300px;" class="pop-box">  
            <div class="pop-box-body" >
        <p id=popBoxTitle></p> <br/><br/>
				<center>
				输入验证码：<input type="text" id="validateCode" style="width:50px;"></input>
				<img id="imgObj" align="top" alt="验证码图片" src="${ctx}/admin/loan/getValidatorImg" onclick="changeImg()" width="80"/><a href="#" onclick="changeImg()">换一张</a><br/><br/><br/>
					<a  id ="AuditEnsure" class="easyui-linkbutton">确认</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<a class="easyui-linkbutton" onclick="hideDiv('pop-div');">取消</a>
                 </center>
            </div>  
        </div>
</body>
</html>