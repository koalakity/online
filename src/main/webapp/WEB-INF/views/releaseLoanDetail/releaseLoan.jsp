<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" /><html>
<head>
<title>登录</title>
<!--[if lte IE 6]>
<script type="text/javascript" src="${ctx}/static/js/belatedPNG.js"></script>
<script type="text/javascript">
	DD_belatedPNG.fix("ul.nav li a, h3, .other_login img, .p_login_btn input, .p_login_btn a, .c_service");
</script>
<![endif]-->
<link rel="stylesheet" href="${ctx}/static/css/loan.css" type="text/css" />
<script type="text/javascript">
$(function(){
//顶部菜单导航切换
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
});
</script>
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
		if(!$("#releaseForm").validate().element($("#loanAmount"))){
	 		$("#monthReturnPrincipalandinter").val("");
	 		$("#monthManageCost").val("");
		return;
		 };
		if(!$("#releaseForm").validate().element($("#yearRate"))){
	 		$("#monthReturnPrincipalandinter").val("");
	 		$("#monthManageCost").val("");
		return;
		 }
			
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

function mysubmit(i){
	var isRelease = document.getElementById("releaseStatus");
	var cbxIsAgree = document.getElementById('isAgree');
	var cbxIsDisclosure = document.getElementById('isDisclosure');
	isRelease.value = i;
	var  cb = $("#releaseForm").validate().form();
	$("input[type=checkbox]").each(function(){
	if(this.checked){ 
		$(this).val(1);
	}
	});
	if(cb){
		if(!cbxIsAgree.checked||!cbxIsDisclosure.checked){
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
							window.location.href="${ctx}/borrowing/borrowing/showInfo";
				       	}					       	
				       	if(data.releaseStatus=="lockOrReport"){
							alert("您的账户已被举报或锁定，暂时不能发布借款！");
							return;
				       	}				       	
				       	if(data.releaseStatus=="isVerify"){
							alert("您当前的可用信用额度为0，请先上传资料获取信用额度！");
							window.location.href="${ctx}/borrowing/borrowing/showInfo";
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
				        },
				       	success: function(data){
				       		if(data.saveStatus){
								alert("保存成功！");
								window.location.href="${ctx}/borrowing/releaseLoan/getShow";
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
	var cbxIsDisclosure = document.getElementById('isDisclosure');
	isRelease.value = i;
	var  cb = $("#releaseForm").validate().form();
	$("input[type=checkbox]").each(function(){

	if(this.checked){
		$(this).val(1);
	}
	});
	if(cb){
		if(!cbxIsAgree.checked||!cbxIsDisclosure.checked){
			alert("请同意并勾选我们的条款");
			 return;
		}else{
			 $("#releaseForm").attr("action", "${ctx}/borrowing/releaseLoan/reViewLoanInfo");
			 $("#releaseForm").submit();
		}
	}else{
	return;
	}
}
</script>
</head>
<body>
<div class="wrapper">
	<div class="content">
	<form:form id="releaseForm"  modelAttribute="loanInfo"  name="releaseForm"  method="post" action="${ctx}/borrowing/releaseLoan/saveReleaseInfo" >
		<div class="my_info">
			<span>我的信用分数:${currUser.userCreditNote.creditScoreSum}</span><span>信用等级：<c:if test="${currUser.userCreditNote.creditGrade==1}"><img src="${ctx}/static/images/img28.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==2}"><img src="${ctx}/static/images/img27.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==3}"><img src="${ctx}/static/images/img26.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==4}"><img src="${ctx}/static/images/img25.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==5}"><img src="${ctx}/static/images/img24.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==6}"><img src="${ctx}/static/images/img23.gif" /></c:if>
		<c:if test="${currUser.userCreditNote.creditGrade==7}"><img src="${ctx}/static/images/img22.gif" /></c:if></span><br />
			<span>我的信用额度:${creditAmount}</span><span>可用额度：${availableAmount}</span>
		</div>
		<div class="loan_title">
			    <h2 class="upload"><a href="${ctx}/borrowing/borrowing/showInfo">上传资料</a></h2>
				<h2 class="obtain"><a href="${ctx}/borrowing/borrowing/showLimit">获取额度</a></h2>
			<h2 class="publish_h"><a href="${ctx}/borrowing/releaseLoan/getShow">发布借款</a></h2>
		</div>
		<div class="publish_c">
			<div class="clear"></div>
			<p class="p_t">请填写以下借款信息：（注：带*为必填）</p>
			
			<div class="publish_c1">
				<p><span>*借款标题：</span><input type="text" name="loanTitle" id="loanTitle" value="${borrowingLoanInfo.loanTitle}"  /></p><div id="txtNameTip"></div>
				<p><span>借款用途：</span><select name="loanUse">
					<option value="1" selected="selected">短期周转</option>
					<option value="2">教育培训</option>
					<option value="3">购房借款</option>
					<option value="4">购车借款</option>
					<option value="5">装修基金</option>
					<option value="6">婚礼筹备</option>
					<option value="7">投资创业</option>
					<option value="8">医疗支出</option>
					<option value="9">个人消费</option>
					<option value="10">其他借款</option>
					</select></p>
				<p><span>*借款金额：</span><input type="text" name="loanAmount" id="loanAmount" class="loanAmount" value="${borrowingLoanInfo.loanAmount}"/><strong class="fl bold2">&nbsp;元&nbsp;</strong></p>
				<p><span>借款期限：</span><select name="loanDuration" id="loanDuration">
					<option value="3" <c:if test="${borrowingLoanInfo.loanDuration==3}">selected</c:if>>3个月</option>
					<option value="6" <c:if test="${borrowingLoanInfo.loanDuration==6}">selected</c:if>>6个月</option>
		           	<option value="9" <c:if test="${borrowingLoanInfo.loanDuration==9}">selected</c:if>>9个月</option>
		           	<option value="12"<c:if test="${borrowingLoanInfo.loanDuration==12}">selected</c:if>>12个月</option>
		          	<option value="18"<c:if test="${borrowingLoanInfo.loanDuration==18}">selected</c:if>>18个月</option>
		          	<option value="24"<c:if test="${borrowingLoanInfo.loanDuration==24}">selected</c:if>>24个月</option>
		          	</select>
	           </p>
				<p><span>*年利率：</span><c:if test="${empty borrowingLoanInfo}"><input type="text" name="yearRate" id="yearRate" class="num-pallets-input"/></c:if><c:if test="${not empty borrowingLoanInfo}"><input type="text" name="yearRate" id="yearRate" class="num-pallets-input" value="${borrowingLoanInfo.yearRateStr}"/></c:if><strong class="fl bold2">&nbsp;%&nbsp;</strong></p>
				<p class="col1"><span>&nbsp;</span>温馨提示：借款最低利率由您的借款期限确定，一般来说借款利率越高，借款速度越快。</p>
				<p><span>还款周期：</span>按月还款</p>
				<p><span>还款方式：</span>等额本息</p>
				<p><span>筹款期限：</span>7天</p>
				<p><span>每月还本息：</span><input type="text" class="monthPay" id="monthReturnPrincipalandinter" name="monthReturnPrincipalandinter"  value="${borrowingLoanInfo.monthReturnPrincipalandinter}" readOnly="readonly"/><strong class="fl bold2">&nbsp;元&nbsp;</strong></p>
				<p><span>每月交借款管理费：</span><input type="text" name="monthManageCost" id="monthManageCost" value="${borrowingLoanInfo.monthManageCost}" readOnly="readonly"/><strong class="fl bold2">&nbsp;元&nbsp;</strong></p>
				<p class="p_area"><span>借款描述：</span><textarea name="description" >${borrowingLoanInfo.description}</textarea>&nbsp;&nbsp;&nbsp;&nbsp;</p>
			</div>
			<p class="p_t">请勾选以下信息披露：（注：发布借款后，您勾选的信息将会公开给借出者，作为借出者投资的参考依据。）</p>
					<div class="publish_c2">
						<span><input type="checkbox" name="isShowAge"
							id="isShowAge" checked="checked" value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />年龄</span><span><input
							type="checkbox" name="isShowSex" checked="checked"
							value="${loanInfo.isShowSex}"
							onclick="this.value=this.checked?1:0" />性别</span><span><input
							type="checkbox" name="isShowDegree" checked="checked"
							value="${loanInfo.isShowDegree}"
							onclick="this.value=this.checked?1:0" />学历</span><span><input
							type="checkbox" name="isShowSchool" checked="checked"
							value="${loanInfo.isShowSchool}"
							onclick="this.value=this.checked?1:0" />毕业学校</span><span><input
							type="checkbox" name="isShowEntranceYear" checked="checked"
							value="${loanInfo.isShowEntranceYear}"
							onclick="this.value=this.checked?1:0" />入学年份</span> <span><input
							type="checkbox" name="isShowWorkCity" checked="checked"
							value="${loanInfo.isShowWorkCity}"
							onclick="this.value=this.checked?1:0" />工作城市</span><span><input
							type="checkbox" name="isShowVocation" checked="checked"
							value="${loanInfo.isShowVocation}"
							onclick="this.value=this.checked?1:0" />公司行业</span><span><input
							type="checkbox" name="isShowCompanyScale" checked="checked"
							value="${loanInfo.isShowCompanyScale}"
							onclick="this.value=this.checked?1:0" />公司规模</span><span><input
							type="checkbox" name="isShowOffice" checked="checked"
							value="${loanInfo.isShowOffice}"
							onclick="this.value=this.checked?1:0" />职位</span><span><input
							type="checkbox" name="isShowWorkYear" checked="checked"
							value="${loanInfo.isShowWorkYear}"
							onclick="this.value=this.checked?1:0" />现单位工作时间</span> <span><input
							type="checkbox" name="isShowMarry" checked="checked"
							value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />是否结婚</span><span><input
							type="checkbox" name="isShowHaveHouse" checked="checked"
							value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />有无购房</span><span><input
							type="checkbox" name="isShowHouseLoan" checked="checked"
							value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />有无房贷</span><span><input
							type="checkbox" name="isShowHaveCar" checked="checked"
							value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />有无购车</span><span><input
							type="checkbox" name="isShowCarLoan" checked="checked"
							value="${loanInfo.isShowAge}"
							onclick="this.value=this.checked?1:0" />有无车贷</span> <span
							class="span_db"><input type="checkbox" id="isAgree"
							checked="checked" />我同意<a
							href="${ctx}/footerDetail/footerDetail/showTwoGradePage?id=11&category=首页&sort=资费说明&c_sort=收费标准"
							class="agree">证大e贷收费条款</a></span><span class="span_db 11"><input
							type="checkbox" id="isDisclosure" checked="checked" />我同意以上信息披露</span>
						<input type="hidden" id="releaseStatus" name="releaseStatus"
							value="${borrowingLoanInfo.releaseStatus}" /> <input
							type="hidden" name="userId"
							value="${borrowingLoanInfo.user.userId}" /> <input type="hidden"
							name="loanId" id="loanId" value="${borrowingLoanInfo.loanId}" />
						<div class="clear"></div>
						<input type="hidden" name="token" id="token"
							value="${sessionScope.token}" />
						<div class="div_tp">
							<a href="javascript:mysubmit(1);" class="btn2"">确认发布</a><a
								href="javascript:mysubmit(2);" class="btn3">保存</a><a
								href="javascript:review(0)" class="btn3">预览</a>
						</div>
					</div>
				</div>
	</form:form>
	</div>
<div class="clear"></div>
</div>
</body>
</html>

