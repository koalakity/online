<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
<title>fabu</title>
<script type="text/javascript">
$(document).ready(function(){
	//聚焦第一个输入框
	$("#loanTitle").focus();
    jQuery.validator.addMethod("isAmout", function(value, element){ 
	  	return this.optional(element) || (value%50==0&&value>=3000&&value<=500000); 
	}, "借款金额为3000元-500,000元且需为50的倍数");  
   //验证年利率输入是否合法
	jQuery.validator.addMethod("isYearRate", function(value, element){ 
	  	var reg = /^([1-9]\d{0,1})$|^[1-9]\d{0,1}(\.{1}\d{2})$/;
	  	return this.optional(element) || (value>=6&&value<=24.4&&reg.test(value)); 
	}, "利率精确到小数点后两位，范围为6%-24.40%之间");
	jQuery.validator.addMethod("checkUserAvailableAmount", function(value, element){
	  	if(value>${availableAmount}){
	  		return this.optional(element) || false;
	  	}else{
	  		return true;
	  	}
	  	//return this.optional(element) || (value>=10&&value<=24.40&&reg.test(value)); 
	}, "您当前申请借款金额大于可用信用额度，系统不允许提交");
		$("#releaseForm").validate( {
			rules : {
				loanTitle : {
					required :　true,
					rangelength : [ 4, 20 ]
				},
				loanAmount:{
					number: true,
					required :　true,
					isAmout  :  true,
					checkUserAvailableAmount:true
				},
				yearRate:{
					required :true,
					isYearRate:true
				},
				description:{
					required :true,
					rangelength : [ 30, 400 ]
				}
			},
			messages : {
				loanTitle : {
					required :"必填项",
					rangelength : "请输入4到20个字符"
				},
				loanAmount:{
					number : "请输入合法数字",
					required :"必填项",
					isAmout  :"(借款金额为3000元-500,000元且需为50的倍数)",
					checkUserAvailableAmount:"您当前申请借款金额大于可用信用额度！"
				},
				yearRate:{
					required :"必填项",
					isYearRate :"(利率精确到小数点后两位，范围为6%-24.40%之间)"
				},
				description:{
					required :"必填项",
					rangelength : "(汉字限制在30-400个之间)"
				}
			}
		}); 
	$('.num-pallets-input').blur(function(){
 		calMonthPay();
    });
 	$('.loanAmount').blur(function(){
 		calMonthPay();
    });
    $('#loanDuration').change(function(){
 		calMonthPay();
    });   
});

function calMonthPay(){
	$.ajaxSetup ({
   		cache: false //关闭AJAX相应的缓存 
		});
	//本金
	var bj = $("#loanAmount").val();
	//年利率
	var yearRate = ($("#yearRate").val())/100;
	//借款期限
	var loanPeriod = jQuery("#loanDuration  option:selected").val();
	if(IsNumeric(bj)&&IsNumeric(yearRate)){
		$.ajax({
     		 url: "${ctx}/borrowing/releaseLoan/caculator?yearRate="+yearRate+"&loanAmount="+bj+"&loanPeriod="+loanPeriod,
     	 	 type: "POST",
     		 dataType: 'json',
     		 timeout: 10000,
	      	 error: function(){
	       	 },
      		 success: function(data){
		 		$("#monthReturnPrincipalandinter").val(data.principanInterestMonth.toFixed(2));
		 		$("#monthManageCost").val(data.managementFeeEveryMonth.toFixed(2));
	      	 },
	        beforeSend: function(){
	        
	        }
		});
 		
	}else{
 		$("#monthReturnPrincipalandinter").val("");
 		$("#monthManageCost").val("");
	}
};

function IsNumeric(sText)
{
   var ValidChars = "0123456789.";
   var IsNumber=true;
   var Char;
   if(sText==0){
   	return false;
   }
   for (i = 0; i < sText.length && IsNumber == true; i++) 
      { 
      Char = sText.charAt(i); 
      if (ValidChars.indexOf(Char) == -1) 
         {
         IsNumber = false;
         }
      }
   return IsNumber;
   
};
</script>
<script type="text/javascript">
$(function(){
//顶部菜单导航切换
	    $(".nav").find("a").hover(function(){
			var m = $(this).parent().index();
			$(".sub_nav").find("span").eq(m).show().siblings().hide();
		});
	//table斑马线
	$(".cer_optional_c").find("tr:odd").css("background","#fcfbfb");
	//灯泡
	var imgsrc;
	$(".loan_r_person_r").find("img").hover(
	 		function(){
	 		imgsrc = $(this).attr("src");
			$(this).attr("src","${ctx}/static/images/bulb_hover.png");
			if(this.id=='identityImg'){
			  showTip($(this),"身份认证","${userProStatus.shenfenzheng}");
			}else if(this.id=='workImg'){
			  showTip($(this),"工作认证","${userProStatus.gongzuozheng}");
			}else if(this.id=='creditImg'){
			 showTip($(this),"信用认证","${userProStatus.xinyong}");
			}else if(this.id=='incomeImg'){
			 showTip($(this),"收入认证","${userProStatus.shouru}");
			}else if(this.id=='houseImg'){
			 showTip($(this),"房产认证","${userProStatus.fangchan}");
			}else if(this.id=='carImg'){
			  showTip($(this),"汽车认证","${userProStatus.gouche}");
			}else if(this.id=='marrayImg'){
			 showTip($(this),"结婚认证","${userProStatus.jiehun}");
			}else if(this.id=='educateImg'){
			 showTip($(this),"学历认证","${userProStatus.xueli}");
			}else if(this.id=='addressImg'){
			 showTip($(this),"居住地认证","${userProStatus.juzhudi}");
			}else if(this.id=='technologyImg'){
			  showTip($(this),"技术认证","${userProStatus.jishu}");
			}else if(this.id=='vedioImg'){
			 showTip($(this),"视频认证","${userProStatus.shipin}");
			}else if(this.id=='phoneImg'){
			 showTip($(this),"手机实名认证","${userProStatus.shoujishiming}");
			}else if(this.id=='blogImg'){
			 showTip($(this),"微博认证","${userProStatus.weibo}");
			}else if(this.id=='fieldImg'){
			 showTip($(this),"实地考察认证","${userProStatus.shidi}");
			}
		},
		function(){
			$(this).attr("src",imgsrc);
			$(".bulbtip").remove();
		}
	);
	//调用弹出层	
	$("#a_pilu").fancybox({modal:true,autoDimensions:false,width:645,height:375,padding:0,margin:0,centerOnScroll:true});
	
		var msgFlg = "";
	msgFlg = "${showMsg}";
	 if(msgFlg=="true"){
	   alert("您当前提交的资料正在审核中，暂时不能发布借款！");
	   window.location.href='${pageContext.request.contextPath}/borrowing/borrowing/showApprove';
	}else if(msgFlg=="noLimit"){
	    alert("您当前的可用额度为0，暂时不能发布借款！");
	   window.location.href='${pageContext.request.contextPath}/borrowing/borrowing/showApprove';
	}
});

//关闭弹出层
function closefancy(){
    $.fancybox.close();
};
//灯泡显示方式
var showTip = function(obj,msg1,msg2){
	var tipBox = $('<div class="bulbtip"><div class="bulbtip-c"><p class="bulbtip-c-l"><strong>'+msg1+'</strong><br />'+msg2+'</p></div><div class="bulbtip-t"></div></div>');
	if(msg1!=""){
		tipBox.appendTo($("body")).css({
			"left" : obj.offset().left,
			"top" : obj.offset().top - 50
		});		
	};
};
//提现中银行列表显示隐藏切换
	$(".select_bank").click(function(){
		$(".sub_table").slideToggle();
		return false;
	});
function loadPage(gourl){
window.location.href=gourl;
};

function release(i){
 var isRelease = document.getElementById("releaseStatus");
	var cbxIsAgree = document.getElementById('isAgree');
	var cbxIsDisclosure = document.getElementById('isDisclosure');
	isRelease.value = i;
	//var  cb = $("#releaseForm").validate().form();
	var  cb = $("#releaseForm").validate().form();
	$("input[type=checkbox]").each(function(){
	if(this.checked){ 
		$(this).val(1);
	}
	});
	if(cb){
		if(!cbxIsAgree.checked){
			 alert("请同意并勾选我们的条款!");
			 return;
		}else{
			if(i==1){
				var formData = $("#releaseForm").serialize();
		 	 		$.ajax({
		 	 		data: formData,
		       		 url: "${ctx}/borrowing/releaseLoan/getReleaseStatus",
		       	 	 type: "POST",
		       		 dataType: 'json',
		       		 timeout: 10000,
		        	 error: function(){
		        	    location.href='${ctx}/accountLogin/login/redirectLogin';
			        },
			       	success: function(data){
				       	if(data.releaseStatus=="release"){
				       		if(window.confirm("发布借款列表后，将不能撤销和更改，是否继续发布？")){
				       			saveInfo();
					        }else{
								return;
					        }
				       	}				       	
				       	if(data.releaseStatus=="finish"){
				       		if(window.confirm("您当前申请借款金额大于可用额度，系统不允许提交！")){
					       		return;
						        }else{
									return;
						        }
				       	}				       	
				       	if(data.releaseStatus=="waitUpdate"){
							alert("您当前的可用信用额度为0，请先上传资料获取信用额度！");
							window.location.href="${ctx}/borrowing/borrowing/showApprove";
				       	}					       	
				       	if(data.releaseStatus=="lockOrReport"){
							alert("您的账户已被举报或锁定，暂时不能发布借款！");
							return;
				       	}				       	
				       	if(data.releaseStatus=="isVerify"){
							alert("您当前的可用信用额度为0，请先上传资料获取信用额度！");
							window.location.href="${ctx}/borrowing/borrowing/showApprove";
							return;
				       	}
				       	if(data.releaseStatus=="review"){
							if(window.confirm("发布借款列表后，将不能撤销和更改，是否继续发布？")){
					 	 		$.ajax({
						 	 		data: $("#releaseForm").serialize(),
						       		 url: "${ctx}/borrowing/releaseLoan/saveReleaseInfo",
						       	 	 type: "POST",
						       		 dataType: 'json',
						       		 timeout: 10000,
						        	 error: function(){
							        },
							       	success: function(data){
								       	if(data.saveStatus){
								       		alert("发布成功,后台审核通过后将提供给理财人进行投标！");
								       		window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=loan";
								       		return;
								       	}else{
								       		alert("发布失败！");
								       		return;
									       	}
							       	}
									});
							}else{
								return;
								}
				       	}
				       		
			       	},
			        beforeSend: function(){
			        }
					})
				}else{
		 	 		$.ajax({
			 	 		data: $("#releaseForm").serialize(),
			       		 url: "${ctx}/borrowing/releaseLoan/saveReleaseInfo",
			       	 	 type: "POST",
			       		 dataType: 'json',
			       		 timeout: 10000,
			        	 error: function(){
			        	  alert("保存失败,请稍后重试！");
			        	   location.href='${ctx}/accountLogin/login/redirectLogin';
				        },
				       	success: function(data){
				       		if(data.saveStatus){
								alert("保存成功！");
								window.location.href="${ctx}/borrowing/borrowing/publishLoanJsp";
								return;
				       		}else{
					       		alert("保存失败！");
					       		return;
					       		}
				       	},	
				        beforeSend: function(){
					        
				        }
						});
					}
	
		}
	}else{
		return;
	}
};
function saveInfo(){
	$.ajaxSetup ({ 
  		cache: false 
	});
		$.ajax({
 	 		data: $("#releaseForm").serialize(),
       		 url: "${ctx}/borrowing/releaseLoan/saveReleaseInfo",
       	 	 type: "POST",
       		 dataType: 'json',
       		 timeout: 10000,
        	 error: function(){
				 alert("发布失败,请稍后重试！");
				 location.href='${ctx}/accountLogin/login/redirectLogin';
	        },
	       	success: function(result){
			 if(result.saveStatus=="saveSuccess"){
				 alert("发布成功！");
				 window.location.href="${ctx}/myAccount/myAccount/showMyAccount?strUrlType=loan";
			}else{
				alert("发布失败,请稍后重试！");
				return ;
				}
	       	}
			});
}
function review(i){
	var isRelease = document.getElementById("releaseStatus");
	var cbxIsAgree = document.getElementById('isAgree');
	isRelease.value = i;
	//var  cb = $("#releaseForm").validate().form();
	var cb=true;
	$("input[type=checkbox]").each(function(){
	if(this.checked){
		$(this).val(1);
	}
	});
	if(cb){
		if(!cbxIsAgree.checked){
			alert("请同意并勾选我们的条款");
			 return;
		}else{
			 $("#releaseForm").attr("action", "${ctx}/borrowing/releaseLoan/reViewLoanInfo");
			 $("#releaseForm").submit();
		}
	}else{
	return;
	}
};
</script>
<div class="content">
	<div class="loan_l">
		<ul>
			<li><a onclick="loadPage('${ctx}/borrowing/borrowing/showApprove')">上传资料</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>
			<li class="on"><a onclick="loadPage('${ctx}/borrowing/borrowing/publishLoanJsp')">发布借款</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;></li>	
		</ul>
	</div>
	<div class="loan_r">
		<div class="loan_r_person">
			<div class="loan_r_person_l">
				<div class="person_img_bg">
					<c:if test="${empty userInfos.userInfoPerson.headPath}" >
                      <img src="${ctx}/static/images/nophoto.jpg" id="person" class="person" width="73" height="80"  />  
                     </c:if> 
                     <c:if test="${not empty  userInfos.userInfoPerson.headPath}" >
                       <img src="/pic/${userInfos.userInfoPerson.headPath}" class="person" width="73" height="80" />
                      </c:if> 
				</div>
			     <p>昵称：${userInfos.loginName}</p>
				 <p>邮箱：${userInfos.email}</p>
				 <p>手机号码：${userInfos.userInfoPerson.phoneNo}</p>
				 <p>注册时间： ${userInfos.regDateTime}</p>
			</div>
			<div class="loan_r_person_r">
				<table>
					<tr class="tr1">
						<td>身份</td>
						<td>工作</td>
						<td>信用</td>
						<td>收入</td>
						<td>房产</td>
						<td>购车</td>
						<td>结婚</td>
						<td>学历</td>
						<td>居住地</td>
						<td>技术职称</td>
						<td>视频</td>
						<td>手机实名</td>
						<td>微博</td>
						<td>实地</td>
					</tr>
					<tr>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shenfenzheng=='true'}">
		                         <img id ="identityImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="identityImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.gongzuozheng=='true'}">
		                         <img id ="workImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="workImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.xinyong=='true'}">
		                         <img id ="creditImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="creditImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shouru=='true'}">
		                         <img id ="incomeImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="incomeImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.fangchan=='true'}">
		                         <img id ="houseImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="houseImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.gouche=='true'}">
		                         <img id ="carImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="carImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.jiehun=='true'}">
		                         <img id ="marrayImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="marrayImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.xueli=='true'}">
		                         <img id ="educateImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="educateImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.juzhudi=='true'}">
		                         <img id ="addressImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="addressImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.jishu=='true'}">
		                         <img id ="technologyImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="technologyImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shipin=='true'}">
		                         <img id ="vedioImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="vedioImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shoujishiming=='true'}">
		                         <img id ="phoneImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="phoneImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.weibo=='true'}">
		                         <img id ="blogImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="blogImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
						<td>
						   <c:choose>
		                     <c:when test="${userPro.shidi=='true'}">
		                         <img id ="fieldImg" src="${ctx}/static/images/bulb_success.png" />
		                     </c:when>
		                     <c:otherwise>
		                         <img id ="fieldImg" src="${ctx}/static/images/bulb_fail.png" />
		                     </c:otherwise>
		                   </c:choose>
						</td>
					</tr>
				</table>
			</div>
		</div>
				<div class="pro_cre">
			恭喜，您获得的信用等级为 <c:if test="${creditNote.creditGrade == 1}">
						<img src="${ctx}/static/images/img28.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 2}">
						<img src="${ctx}/static/images/img27.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 3}">
						<img src="${ctx}/static/images/img26.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 4}">
						<img src="${ctx}/static/images/img25.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 5}">
						<img src="${ctx}/static/images/img24.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 6}">
						<img src="${ctx}/static/images/img23.gif" />
					</c:if> <c:if test="${creditNote.creditGrade == 7}">
						<img src="${ctx}/static/images/img22.gif" />
					</c:if> <c:if test="${empty creditNote.creditGrade}">
						0
					</c:if> 级，信用额度为 <span class="col_r2">
					             ${creditNote.creditAmount}
				               <c:if test="${empty creditNote.creditAmount}">
						          0
					           </c:if>
					           </span> 元！ 可用额度：<span class="col_r2">${availableAmount}</span>
		</div>
		<div class="loaninfo">
		<form id="releaseForm"  modelAttribute="loanInfo"  name="releaseForm"  method="post" action="${ctx}/borrowing/releaseLoan/saveReleaseInfo" >
			<div class="loaninfo_t"></div>
			<table class="loaninfo_c">
				<tr>
					<td class="td1"><span class="col_r4">*</span> 借款标题</td>
					<td class="td2"><input type="text" name="loanTitle" id="loanTitle" value="${borrowingLoanInfo.loanTitle}"  class="input_text1"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">借款用途</td>
					<td class="td2">
						<div class="select1_border">
							<div class="select1_con">
								<select name="loanUse" class="select1">
					<option value="1" <c:if test="${borrowingLoanInfo.loanUse==1}">selected</c:if>>短期周转</option>
					<option value="2"  <c:if test="${borrowingLoanInfo.loanUse==2}">selected</c:if>>教育培训</option>
					<option value="3"  <c:if test="${borrowingLoanInfo.loanUse==3}">selected</c:if>>购房借款</option>
					<option value="4"  <c:if test="${borrowingLoanInfo.loanUse==4}">selected</c:if>>购车借款</option>
					<option value="5"  <c:if test="${borrowingLoanInfo.loanUse==5}">selected</c:if>>装修基金</option>
					<option value="6"  <c:if test="${borrowingLoanInfo.loanUse==6}">selected</c:if>>婚礼筹备</option>
					<option value="7"  <c:if test="${borrowingLoanInfo.loanUse==7}">selected</c:if>>投资创业</option>
					<option value="8"  <c:if test="${borrowingLoanInfo.loanUse==8}">selected</c:if>>医疗支出</option>
					<option value="9"  <c:if test="${borrowingLoanInfo.loanUse==9}">selected</c:if>>个人消费</option>
					<option value="10"  <c:if test="${borrowingLoanInfo.loanUse==10}">selected</c:if>>其他借款</option>
					</select>
							</div>
						</div>
					</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1"><span class="col_r4">*</span> 借款金额</td>
					<td class="td2"><input type="text" name="loanAmount" id="loanAmount"  value="${borrowingLoanInfo.loanAmount}" class="input_text1 loanAmount"/></td>
					<td class="td4">元 </td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">借款期限</td>
					<td class="td2">
						<div class="select1_border">
							<div class="select1_con">
							  <select name="loanDuration" id="loanDuration" class="select1">
					             <option value="3" <c:if test="${borrowingLoanInfo.loanDuration==3}">selected</c:if>>3个月</option>
					             <option value="6" <c:if test="${borrowingLoanInfo.loanDuration==6}">selected</c:if>>6个月</option>
		                       	 <option value="9" <c:if test="${borrowingLoanInfo.loanDuration==9}">selected</c:if>>9个月</option>
		                    	 <option value="12"<c:if test="${borrowingLoanInfo.loanDuration==12}">selected</c:if>>12个月</option>
		                       	 <option value="18"<c:if test="${borrowingLoanInfo.loanDuration==18}">selected</c:if>>18个月</option>
		          	             <option value="24"<c:if test="${borrowingLoanInfo.loanDuration==24}">selected</c:if>>24个月</option>
		          	          </select>
							</div>
						</div>
					</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1"><span class="col_r4">*</span> 年利率</td>
					<td class="td2">
					  <c:if test="${empty borrowingLoanInfo}">
					     <input type="text" name="yearRate" id="yearRate" class="input_text1 num-pallets-input"/>
					   </c:if>
					  <c:if test="${not empty borrowingLoanInfo}">
					     <input type="text" name="yearRate" id="yearRate" class="input_text1 num-pallets-input" value="${borrowingLoanInfo.yearRateStr}"/>
					   </c:if></td>
					<td class="td4">%</td>
					<td>&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">&nbsp;</td>
					<td class="td2" colspan="3">温馨提示：借款最低利率由您的借款期限确定，一般来说借款利率越高，借款速度越快。</td>
				</tr>
				<tr>
					<td class="td1">还款周期</td>
					<td class="td2 font_14">按月还款</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">还款方式</td>
					<td class="td2 font_14">等额本息</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">筹标期限</td>
					<td class="td2 font_14">7天</td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">每月还本息</td>
					<td class="td2"><input type="text" class="input_text1" id="monthReturnPrincipalandinter" name="monthReturnPrincipalandinter"  value="${borrowingLoanInfo.monthReturnPrincipalandinter}" readOnly="readonly"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">每月交借款管理费</td>
					<td class="td2"><input type="text" name="monthManageCost" id="monthManageCost" value="${borrowingLoanInfo.monthManageCost}" readOnly="readonly" class="input_text1"/></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1" valign="top"><span class="col_r4">*</span> 借款描述</td>
					<td class="td2"><textarea name="description" class="textarea_1">${borrowingLoanInfo.description}</textarea></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td class="td1">&nbsp;</td>
					<td class="td2 font_14"><input type="checkbox" id="isAgree" checked="checked"/> 我同意 <a href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=11&category=首页&sort=资费说明&c_sort=收费标准" class="agree">证大e贷收费条款</a>和<a id="a_pilu" href="#pilu">信息披露要求</a></td>
					<td colspan="2">&nbsp;</td>
				</tr>
				<tr>
					<td colspan="4">
						<input type="hidden" id="releaseStatus" name="releaseStatus" value="${borrowingLoanInfo.releaseStatus}"/>
						<input type="hidden" name="userId"  value="${borrowingLoanInfo.user.userId}"/>
						<input type="hidden" name="loanId" id="loanId" value="${borrowingLoanInfo.loanId}"/>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
					</td>
				</tr>
				<tr>
					<td class="td1">&nbsp;</td>
					<td class="td2"><input type="button" class="btn_r5" onclick="release(1)" value="确认发布" /><input type="button" class="btn_r4" onclick="release(2)" value="保存" /><input type="button" class="btn_r4" onclick="review(0)" value="预览" /></td>
					<td colspan="2">&nbsp;</td>
				</tr>
			</table>
			<div  style="display: none;">
	           <div class="autlayer autlayer2" id="pilu">
		         <div class="autlayer_t"><img src="${ctx}/static/images/pilu_t.jpg" /><a  class="autlayer_close fr"  onclick="parent.closefancy();"></a></div>
		            <div class="autlayer_c2">
			          <div class="autlayer_c2_t">
				         <p>发布借款后，您勾选的信息将会公开给借出者，作为借出者投资的参考依据</p>
			           </div>
			          <div class="autlayer_c2_c">
			            <span><input type="checkbox" name="isShowAge" id="isShowAge" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>年龄</span>
			            <span><input type="checkbox" name="isShowSex" checked="checked" value="${borrowingLoanInfo.isShowSex}" onclick="this.value=this.checked?1:0"/>性别</span>
			            <span><input type="checkbox" name="isShowDegree" checked="checked" value="${borrowingLoanInfo.isShowDegree}" onclick="this.value=this.checked?1:0"/>学历</span>
			            <span><input type="checkbox" name="isShowSchool" checked="checked" value="${borrowingLoanInfo.isShowSchool}" onclick="this.value=this.checked?1:0"/>毕业学校</span>
			            <span><input type="checkbox" name="isShowEntranceYear" checked="checked" value="${borrowingLoanInfo.isShowEntranceYear}" onclick="this.value=this.checked?1:0"/>入学年份</span>
			            <span><input type="checkbox" name="isShowWorkCity" checked="checked" value="${borrowingLoanInfo.isShowWorkCity}" onclick="this.value=this.checked?1:0"/>工作城市</span>
			            <span><input type="checkbox" name="isShowVocation" checked="checked" value="${borrowingLoanInfo.isShowVocation}" onclick="this.value=this.checked?1:0"/>公司行业</span>
			            <span><input type="checkbox" name="isShowCompanyScale" checked="checked" value="${borrowingLoanInfo.isShowCompanyScale}" onclick="this.value=this.checked?1:0"/>公司规模</span>
			            <span><input type="checkbox" name="isShowOffice" checked="checked" value="${borrowingLoanInfo.isShowOffice}" onclick="this.value=this.checked?1:0"/>职位</span>
			            <span><input type="checkbox" name="isShowWorkYear" checked="checked" value="${borrowingLoanInfo.isShowWorkYear}" onclick="this.value=this.checked?1:0"/>现单位工作时间</span>
			            <span><input type="checkbox" name="isShowMarry" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>是否结婚</span>
			            <span><input type="checkbox" name="isShowHaveHouse" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>有无购房</span>
			            <span><input type="checkbox" name="isShowHouseLoan" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>有无房贷</span>
			            <span><input type="checkbox" name="isShowHaveCar" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>有无购车</span>
			            <span><input type="checkbox"  name="isShowCarLoan" checked="checked" value="${borrowingLoanInfo.isShowAge}" onclick="this.value=this.checked?1:0"/>有无车贷</span>
			          </div>
			          <input type="button" class="btn_r4" value="提交" onclick="parent.closefancy();" style="margin:40px auto 0; display:block;" />
		           </div>
	             </div>
              </div>	
			</form>
		</div>
	</div>
</div>