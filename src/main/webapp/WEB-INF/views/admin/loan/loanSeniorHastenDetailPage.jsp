<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>借款信息查看</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/admin/js/datagrid-detailview.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/utils/formatterUtil.js"></script>
<script type="text/javascript" src="${ctx}/static/admin/js/utils/jquery.poshytip.js"></script>
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
				
			$('#table1').datagrid({
				view: detailview,
				detailFormatter:function(index,row){
					return '<div style="padding:5px"><table id="ddv-' + index + '"></table></div>';
				},
				onExpandRow: function(index,row){
					$('#ddv-'+index).datagrid({
						url:'${ctx}/admin/loan/seachInvestinfoByNum?loanId=${loanInfo.loanId}&num='+row.currNum,
						fitColumns:true,
						singleSelect:true,
						rownumbers:true,
						columns:[[
							{field:'realName',title:'投标人'},
							{field:'investAmount',title:'投标金额'},
							{field:'haveScaleFormatt',title:'投标占比'},
							{field:'monthRepayPrincipal',title:'当期未收本息', formatter:function(value,rowData){return formatCurrency(rowData.havaScale*(row.principalAmt+row.interestAmt),2);}},
							{field:'principalAmt',title:'当期未收本金', formatter:function(value,rowData){return formatCurrency(rowData.havaScale*row.principalAmt,2);}},
							{field:'remainderPrincipal',title:'剩余本金', formatter:function(value,rowData){return formatCurrency(rowData.havaScale*row.inverestRemainderPrincipal,2);}},
							{field:'pastDueDays',title:'逾期天数', formatter:function(){return row.pastDueDays+'天';}},
							{field:'overduePenaltyInterest',title:'当期未收逾期罚息',formatter:function(value,rowData){return formatCurrency(rowData.havaScale*row.interestOverduePenalty,2);}},
							{field:'advancedAmount',title:'垫付金额'},
							{field:'advanceStatus',title:'还款状态',formatter:function(value,rowData){
																							if(rowData.advanceStatus==1){
																								return '未还款';
																							}else if(rowData.advanceStatus==2){
																								return '已代偿';
																							}else if(rowData.advanceStatus==3){
																								return '逾期还款'
																							}else{
																								return row.isRepayed;
																							}
																						}},
							{field:'operate',title:'操作',width:80,formatter:function(value,rowData){
								url='${ctx}/admin/loan/claimsAcquisition';
								if(row.pastDueDays>30){
									if(rowData.advanceStatus==1){
										return '<sec:authorize access="hasAuthority('02000800')"><a href="#"  onclick="OperateFormate(url,'+rowData.investId+','+row.currNum+','+row.id+')">垫付</a></sec:authorize>';
									}else if(rowData.advanceStatus==2){
										return '<a  disabled="disabled" href="#">垫付</a>';
									}
									
								}else 
									return;
								}}
						]],
						onResize:function(){
							$('#table1').datagrid('fixDetailRowHeight',index);
						},
						onLoadSuccess:function(){
							setTimeout(function(){
								$('#table1').datagrid('fixDetailRowHeight',index);
							},0);
						}
					});
					$('#table1').datagrid('fixDetailRowHeight',index);
				}
			});
		});

		function OperateFormate(url,investId,currNum,acTVirtualCashFlowId){
			$.messager.confirm('消息','确定要垫付当前理财人的本期应收本息吗？',function(r){  
			    if (r){  
			    	$.post(url, 
							  { investId: investId, num:currNum,acTVirtualCashFlowId:acTVirtualCashFlowId},
								function (result){
									if (result.success) {
											alert("收购成功");
											$('#table1').datagrid('reload');
										} else {
											alert(result.msg);
										}
								}, "json");  
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
			if(memoLength>1000){
				$.messager.alert('Message','字数不能超过五百！');
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
		
		function fromattPastDueDays(val,row){
			return row.pastDueDays+'天';
		}
		function formattOverduePenaltyInterest(val,row){
			return row.overduePenaltyInterest+'元';
		}
		function formattOverdueBreachPenalty(val,row){
			return row.overdueBreachPenalty+'元';
		}
		function formattMonthManageCost(val,row){
			return row.monthManageCost+'元';
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
					<td>${loanInfo.loanId} <a href="${ctx}/admin/loan/showContract?loanId=${loanInfo.loanId}"target="_blank">借款协议</a>   <a href="${ctx}/admin/loan/showRiskContract?loanId=${loanInfo.loanId}"target="_blank">借款风险基金协议</a></td>
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
			
		</div>  
		<div title="投标列表" style="padding:20px;">
			投标进度
			<div id="progress" class="easyui-progressbar" style="width:400px;" value="${loanInfo.progress}"></div>  
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
		<div title="还款信息" style="padding:20px;">  
			<table id="table1" class="easyui-datagrid" url="${ctx}/admin/loan/seachLoanRepayList?loanId=${loanInfo.ledgerLoanId}" striped="true" fitColumns="true" style="width:980px; height:527px;" singleSelect="true">
				<thead>
					<tr>
						<th field="currNum" width="20">编号</th>
						<th field="repayDay" width="40">计划还款日期</th>
						<th field="editDate" width="40">实际还款日期</th>
						<th field="eachRepayment" width="50">当期待还本息</th>
						<th field="principalAmtFormat" width="50">当期待还本金</th>
						<th field="remainderPrincipal" width="50">剩余本金</th>
						<th field="pastDueDays" width="40" formatter="fromattPastDueDays">逾期天数</th>
						<th field="overduePenaltyInterest" width="40" formatter="formattOverduePenaltyInterest">逾期罚息</th>
						<th field="overdueBreachPenalty" width="40" formatter="formattOverdueBreachPenalty">逾期违约金</th>
                        <th field="monthManageCost" width="50" formatter="formattMonthManageCost">月缴管理费</th>
                        <th field="rePayTotalAmount" width="50">当期应还总额</th>
						<th field="advancedAmount" width="50">垫付金额</th>
						<th field="notAdvancedAmount" width="50">未垫付金额</th>
						<th field="isRepayed" width="40">状态</th>
					</tr>
				</thead>
			</table><br/>
			<table class="easyui-datagrid" fitColumns="true">  
			    <thead>  
			        <tr>  
			            <th data-options="field:'principalAndinterest',width:120">所有逾期统计</th>  
			            <th data-options="field:'overDueInterestAmount' ,width:50"></th> 
			            <th data-options="field:'overDueFineAmount' ,width:120"></th>  
			            <th data-options="field:'monthManageCost' ,width:50"></th> 
			            <th data-options="field:'totalPayBack' ,width:120"></th>  
			            <th data-options="field:'advancedAmt' ,width:50"></th> 
			            <th data-options="field:'totalPayBack1' ,width:120"></th>  
			            <th data-options="field:'advancedAmt2' ,width:50"></th> 
			               
			        </tr>  
			    </thead>  
			    <tbody>  
			        <tr>
			        	<td>所有逾期应还本息和</td>  
			            <td>${loanInfoStatistics.principalAndinterest}</td>
			            <td>所有逾期应还罚息和</td>   
			            <td>${loanInfoStatistics.overDueInterestAmount}</td>
			            <td>所有逾期应还违约金和</td>   
			            <td>${loanInfoStatistics.overDueFineAmount}</td> 
			           	<td>所有逾期月缴管理费和</td>  
			        	<td>${loanInfoStatistics.monthManageCost}</td>
			             
			        </tr>
			        <tr>
			        	
			        	<td>所有逾期应还总额</td>  
			            <td>${loanInfoStatistics.totalPayBack}</td>
			            <td>所有逾期垫付金额和</td>  
			            <td>${loanInfoStatistics.advancedAmt}</td>
			            <td>所有逾期未垫付金额和</td>  
			            <td>${loanInfoStatistics.notAdvancedAmt}</td>
			        </tr>     
			    </tbody>  
</table>  
		</div>  
	</div>
	
	<div style="height:15px; overflow:hidden;"></div>
	<table nowrap="false" id="noteContext" name="noteContext" class="easyui-datagrid" url="${ctx}/admin/loan/seachLoanNote?loanId=${loanInfo.loanId}" title="备注" fitColumns="true" toolbar="#toolbar" style="width:1000px;">
		<thead>
			<tr>
				<th field="memoText" width="100" >备注内容</th>
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
</body>
</html>