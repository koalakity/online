<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript">
$(function(){
	//还款按钮下内容显示隐藏切换
	$(".repay").click(function(){
		var m = $(this).parents("table").next(".account_r_c_3c");
		var m1 = m.find(".account_r_c_3c1");
		var m2 = m.find(".account_r_c_3c2");
		if(m1.is(":hidden")){
			m.show();
			m2.hide();
			m1.show();
			m.parents("li").siblings().find(".account_r_c_3c").hide();
		}else{
			if(m2.is(":hidden")){
				m.show();
				m2.hide();
				m1.show();
				m.parents("li").siblings().find(".account_r_c_3c").hide();
			}else{
			    m1.hide();
				m.hide();
			}
		};
	});
	//提前还款按钮下内容显示隐藏切换
	$(".repay_ahead").click(function(){
   		var m = $(this).parents("table").next(".account_r_c_3c");
   		var m1 = m.find(".account_r_c_3c1");
   		var m2 = m.find(".account_r_c_3c2");
		var loanId = $(this).parents("li").find("input").val();
		$.ajaxSetup ({
	   		cache: false //关闭AJAX相应的缓存 
			});
		$.ajax({
      		 url: "${ctx}/loanManage/loanManage/checkIsOverdue?loanId=" + loanId,
      	 	 type: "POST",
      		 dataType: 'json',
      		 timeout: 10000,
       	 error: function(){
	        },
	       	success: function(data){
		       	if(data.success==true){
		    		if(m2.is(":hidden")){
		    			m.show();
		    			m1.hide();
		    			m2.show();
		    			m.parents("li").siblings().find(".account_r_c_3c").hide();
		    		}else{
		    			if(m1.is(":hidden")){
		    				m.show();
		    				m1.hide();
		    				m2.show();
		    				m.parents("li").siblings().find(".account_r_c_3c").hide();
		    			}else{
		    			    m2.hide();
		    				m.hide();
		    			}
		    		}
			    return  true;
			   }else{
				alert(data.msg);				
		       		return false;
				}
				
	       	},
	        beforeSend: function(){
	        }
	     	});

		return false;
	});
});
/**
 *获取还款详细
 */
function getRepayLoanInfo(loanId,divId){
	var m = "#arc3c_"+divId;
		$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
		});
    $.ajax({
        url: "${ctx}/loanManage/loanManage/getRepayLoanDetail?loanId=" + loanId,
        type: "POST",
        dataType: 'html',
        timeout: 1000000,
        error: function(data){
        },
        success: function(data){
        $(m).html(data);
        
        },
        beforeSend: function(){
        }
    });
}
/**
 *获取提前还款详细
 */
function getInRepayLoanInfo(loanId,divId){
	var m = "#arc3c_"+divId;
		$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
		});
		
		$.ajax({
			url: "${ctx}/loanManage/loanManage/checkIsOverdue?loanId=" + loanId,
			type: "POST",
			dataType: 'json',
			timeout: 10000,		 
		    error: function(){},
		    success: function(data){
			    if(data.success==true){
			        $.ajax({
			            url: "${ctx}/loanManage/loanManage/getInRepayLoanDetail?loanId=" + loanId,
			            type: "POST",
			            dataType: 'html',
			            timeout: 1000000,
			            error: function(data){},
			            success: function(data){
			            	$(m).html(data);
			            },
			            beforeSend: function(){
			            }
			        });
				}else{}
		   },
		   beforeSend: function(){}
		});
 }
</script>

<c:if test="${empty loanInfoVO.repayLoanInfoList}">
		没有需要的还款！
</c:if>
<c:if test="${not empty loanInfoVO.repayLoanInfoList}">

						<ul>
<pg:pager
url="${ctx}/loanManage/loanManage/findMyRepaymentList"
   items="${fn:length(loanInfoVO.repayLoanInfoList)}"
   index="center"
   maxPageItems="4"
   maxIndexPages="10"
   export="offset,currentPageNumber=pageNumber"
   scope="request">
  <pg:param name="index"/>
  <pg:param name="maxPageItems"/>
  <pg:param name="maxIndexPages"/>
	<ex:searchresults>
	<c:forEach items="${loanInfoVO.repayLoanInfoList}" var="repaymentLoanInfo" varStatus="status">
	<pg:item>
		<c:choose>
			<c:when test="${status.count %2 ==1 }">
				<li>
								 <input type="hidden"  value="${repaymentLoanInfo.loanId}" name="repaymentLoanId1" id="repaymentLoanId1"/>
								<table>
									<tr><th colspan="3" class="tl">${repaymentLoanInfo.loanTitle} 借款编号： ${repaymentLoanInfo.loanId}</th><th class="tr"><a href="${ctx}/myAccount/myAccount/showContract?loanId=${repaymentLoanInfo.loanId}" target="_blank">查看借款协议</a> <a href="${ctx}/myAccount/myAccount/showRiskContract?loanId=${repaymentLoanInfo.loanId}" target="_blank">查看风险基金协议</a></th></tr>
									<tr><td>借款金额：<span class="col1">${repaymentLoanInfo.loanAmount}</span></td><td>年利率：<span class="col1">${repaymentLoanInfo.yearRate}</span></td><td>期限：<span class="col1">${repaymentLoanInfo.loanDuration}</span></td><td rowspan="3" class="td9"><a href="#" class="btn1 repay_ahead" onclick="getInRepayLoanInfo(${repaymentLoanInfo.loanId},${status.count})">提前还款</a><a href="#" class="btn1 repay" onclick="getRepayLoanInfo(${repaymentLoanInfo.loanId},${status.count})">还款</a></td></tr>
									<tr><td>下一还款日：<span class="col1">${repaymentLoanInfo.nextPaymentDate}</span></td><td>月还款额：<span class="col1">${repaymentLoanInfo.repaymentAmountByMonth}</span></td><td>月缴管理费：<span class="col1">${repaymentLoanInfo.managementFeeByMonth}</span></td></tr>
									<tr><td>逾期天数：<span class="col1">${repaymentLoanInfo.overdueDays}</span></td><td>逾期罚息：<span class="col1">${repaymentLoanInfo.overdueInterest}</span></td><td>逾期违约金：<span class="col1">${repaymentLoanInfo.overdueFines}</span></td></tr>
								</table>								
								<div class="account_r_c_3c" style="display:none;" id="arc3c_${status.count}">
										<%@ include file="/WEB-INF/views/loanManage/repayLoanDetail.jsp"%>
										<%@ include file="/WEB-INF/views/loanManage/earlyRepayLoanDetail.jsp"%> 
								</div>
				</li>
			</c:when >
			<c:otherwise>
				<li  class="even">
								<input type="hidden"  value="${repaymentLoanInfo.loanId}" name="repaymentLoanId2" id="repaymentLoanId2"/>
								<table>
									<tr><th colspan="3" class="tl">${repaymentLoanInfo.loanTitle} 借款编号：${repaymentLoanInfo.loanId}</th><th class="tr"><a href="${ctx}/myAccount/myAccount/showContract?loanId=${repaymentLoanInfo.loanId}" target="_blank">查看借款协议</a> <a href="${ctx}/myAccount/myAccount/showRiskContract?loanId=${repaymentLoanInfo.loanId}" target="_blank">查看风险基金协议</a></th></tr>
									<tr><td>借款金额：<span class="col1">${repaymentLoanInfo.loanAmount}</span></td><td>年利率：<span class="col1">${repaymentLoanInfo.yearRate}</span></td><td>期限：<span class="col1">${repaymentLoanInfo.loanDuration}</span></td><td rowspan="3" class="td9"><a href="#" class="btn1 repay_ahead" onclick="getInRepayLoanInfo(${repaymentLoanInfo.loanId},${status.count})">提前还款</a><a href="#" class="btn1 repay" onclick="getRepayLoanInfo(${repaymentLoanInfo.loanId},${status.count})">还款</a></td></tr>
									<tr><td>下一还款日：<span class="col1">${repaymentLoanInfo.nextPaymentDate}</span></td><td>月还款额：<span class="col1">${repaymentLoanInfo.repaymentAmountByMonth}</span></td><td>月缴管理费：<span class="col1">${repaymentLoanInfo.managementFeeByMonth}</span></td></tr>
									<tr><td>逾期天数：<span class="col1">${repaymentLoanInfo.overdueDays}</span></td><td>逾期罚息：<span class="col1">${repaymentLoanInfo.overdueInterest}</span></td><td>逾期违约金：<span class="col1">${repaymentLoanInfo.overdueFines}</span></td></tr>
								</table>
								<div class="account_r_c_3c" style="display:none;" id="arc3c_${status.count}">
										<%@ include file="/WEB-INF/views/loanManage/repayLoanDetail.jsp"%>
										<%@ include file="/WEB-INF/views/loanManage/earlyRepayLoanDetail.jsp"%> 
								</div>
				</li>
			</c:otherwise>
		</c:choose>
		</pg:item>
	</c:forEach>
		</ex:searchresults>
				<table border=0 cellpadding=0 width=10% cellspacing=0>
				<tr align=center valign=top>
				<td valign=bottom>
				  <pg:index export="total=itemCount">
				    <font face=arial,sans-serif size=-1 class="pagelist">
				    <pg:first>
				    	  共<%= total %>条&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">首页</a>
				    </pg:first>
				      <pg:prev>&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">上一页</a></pg:prev>
				    <pg:pages><%
				      if (pageNumber.intValue() < 10) { 
				        %>&nbsp;<%
				      }
				      if (pageNumber == currentPageNumber) { 
				        %><b><%= pageNumber %></b><%
				      } else { 
				        %><a href="#" onclick="fenye('<%=pageUrl%>')"><%= pageNumber %></a><%
				      }
				    %>
				    </pg:pages>
				    <pg:next>&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">下一页</a></pg:next>
				    
					<pg:last>
				      <a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%= pageNumber %>页
				    </pg:last>
				    
				    <br></font>
				  </pg:index>
				</td>
				</tr>
				</table>
		    </pg:pager>
						</ul>
</c:if>