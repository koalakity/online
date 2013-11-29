<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib uri="http://jsptags.com/tags/navigation/pager" prefix="pg"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="css/account.css" type="text/css" /> 

<script type="text/javascript">
$(function() {
	$(".account_r_c_t")
			.find("h3")
			.click(
					function() {

						$.ajaxSetup( {
							cache : false
						//关闭AJAX相应的缓存 
								});
						var m = $(this).index() + 1;

						if (m == 1) {

							$(".account_r_c_c").load(
									"${ctx}/homepage/homepage/newInfo?isFrame=true"+"&random=${random}",
									function(responseText, textStatus,
											XMLHttpRequest) {
										if(responseText&&responseText=='redirectLogin'){ 
								    		location.href='${ctx}/accountLogin/login/redirectLogin';
								    	}else{
								    		$(".account_r_c_c").html(responseText);
								    	}
										
									});
						}
						if (m == 2) {

							$(".account_r_c_c")
									.load(
											"${ctx}/homepage/homepage/showLoanNote?isFrame=true"+"&random=${random}",
											function(responseText, textStatus,
													XMLHttpRequest) {
												if(responseText&&responseText=='redirectLogin'){ 
										    		location.href='${ctx}/accountLogin/login/redirectLogin';
										    	}else{
										    		$(".account_r_c_c").html(
															responseText);
										    	}
												
											});
						}
						if (m == 3) {
							$(".account_r_c_c")
									.load(
											"${ctx}/homepage/homepage/showInvestNote?isFrame=true"+"&random=${random}",
											function(responseText, textStatus,
													XMLHttpRequest) {
												if(responseText&&responseText=='redirectLogin'){ 
										    		location.href='${ctx}/accountLogin/login/redirectLogin';
										    	}else{
										    		$(".account_r_c_c").html(
															responseText);
										    	}
												
											});
						}
						if (m == 4) {
							$(".account_r_c_c")
									.load(
											"${ctx}/homepage/homepage/showLeaveMsg?isFrame=true"+"&random=${random}",
											function(responseText, textStatus,
													XMLHttpRequest) {
												if(responseText&&responseText=='redirectLogin'){ 
										    		location.href='${ctx}/accountLogin/login/redirectLogin';
										    	}else{
										    		$(".account_r_c_c").html(
															responseText);
										    	}
											});
						}

						var m1 = ".account_r_c_c" + m;
						$(this).addClass("on").siblings().removeClass("on");
						$(m1).show().siblings().hide();
					});

});

function loanPage(loadUrl) {
	$.ajaxSetup( {
		cache : false
	//关闭AJAX相应的缓存 
			});
	$(".account_r").load(loadUrl,
			function(responseText, textStatus, XMLHttpRequest) {
				$(".account_r").html(responseText);

			});
}

function fenye(url) {
	$.ajaxSetup( {
		cache : false
	//关闭AJAX相应的缓存 
			});
	$(".account_r_c_c").load(url,
			function(responseText, textStatus, XMLHttpRequest) {
				$(".account_r_c_c").html(responseText);
			});
};

</script>

<div class="my_info2">
   <c:if test="${empty returnPageInfoVO.userInfoPersion.headPath}" >
     <img src="${ctx}/static/images/nophoto.jpg" id="person" class="person" />  
    </c:if> 
    <c:if test="${not empty  returnPageInfoVO.userInfoPersion.headPath}" >
      <img src="/pic/${returnPageInfoVO.userInfoPersion.headPath}" class="person"/>
   </c:if> 
	<div class="div2 bor_r">
		<p>
			<strong class="font_14">我的个人信息</strong><a href="${ctx}/myAccount/myAccount/managePersonal?strUrlType=myAccount" class="a_m">修改</a>
		</p>
		<p>
			用户昵称：${returnPageInfoVO.loginName}
		</p>
		<p>
			注册时间：${returnPageInfoVO.zcdate}
		</p>
		<p>
			现所在地：${returnPageInfoVO.userInfoPersion.liveAddress}
		</p>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 7}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img22.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 6}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img23.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 5}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img24.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 4}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img25.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 3}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img26.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 2}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img27.gif" />
			</p>
		</c:if>
		<c:if test="${returnPageInfoVO.userCreditNote.creditGrade == 1}">
			<p>
				信用等级：
				<img src="${ctx}/static/images/img28.gif" />
			</p>
		</c:if>
	</div>
	<div class="div2">
		<p>
			<strong class="font_14">我的账户信息</strong>
		</p>
		<p>
			账户余额:
			<span class="col1">${returnPageInfoVO.accountBalance}</span>
		</p>
		<p>
			冻结金额:
			<span class="col1">${returnPageInfoVO.freezeAmount}</span>
		</p>
		<p>
			成功借入笔数:${returnPageInfoVO.loanItems}笔
		</p>
		<p>
			成功借出笔数:${returnPageInfoVO.tenderItems}笔
		</p>
	</div>
</div>
<div class="account_r_c">
	<div class="account_r_c_t">
		<h3 class="on">
			最新动态
		</h3>
		<h3>
			借款记录
		</h3>
		<h3>
			投标记录
		</h3>
		<h3>
			留言板
		</h3>
	</div>
	<div class="account_r_c_c">
		<div class="account_r_c_c1">
			<ul>

				<pg:pager url="${ctx}/homepage/homepage/newInfo"
					items="${fn:length(mvList)}" index="center" maxPageItems="10"
					maxIndexPages="10" export="offset,currentPageNumber=pageNumber"
					scope="request">
					<pg:param name="index" />
					<pg:param name="maxPageItems" />
					<pg:param name="maxIndexPages" />
					<ex:searchresults>

						<c:forEach items="${mvList}" var="mw">
							<pg:item>
								<li>
									<span>${mw.rDate}</span>${mw.wordContext1}
								</li>
							</pg:item>
						</c:forEach>
					</ex:searchresults>
					<table border=0 cellpadding=0 width=10% cellspacing=0>
						<tr align=center valign=top>
							<td valign=bottom>
								<pg:index export="total=itemCount">
									<font face=arial,sans-serif size=-1 class="pagelist"> <pg:first>
				    	  共<%=total%>条&nbsp;<a href="#" onclick="fenye('<%=pageUrl%>')">首页</a>
										</pg:first> <pg:prev>&nbsp;<a href="#"
												onclick="fenye('<%=pageUrl%>')">上一页</a>
										</pg:prev> <pg:pages>
											<%
												if (pageNumber.intValue() < 10) {
											%>&nbsp;<%
												}
															if (pageNumber == currentPageNumber) {
											%><b><%=pageNumber%></b>
											<%
												} else {
											%><a href="#" onclick="fenye('<%=pageUrl%>')"><%=pageNumber%></a>
											<%
												}
											%>
										</pg:pages> <pg:next>&nbsp;<a href="#"
												onclick="fenye('<%=pageUrl%>')">下一页</a>
										</pg:next> <pg:last>
											<a href="#" onclick="fenye('<%=pageUrl%>')">末页 </a>&nbsp;&nbsp;共<%=pageNumber%>页
				    </pg:last> <br> </font>
								</pg:index>
							</td>
						</tr>
					</table>
				</pg:pager>


			</ul>
		</div>
	</div>
</div>
<div class="clear"></div>

