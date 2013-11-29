<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<link rel="stylesheet" href="${ctx}/static/css/revision.css" type="text/css">
<link rel="stylesheet" href="${ctx}/static/css/jquery.fancybox-1.3.4.css" type="text/css"/>
<script type="text/javascript" src="${ctx}/static/js/jquery.fancybox-1.3.4.js"></script>
		<script type="text/javascript">
$(function() {
	//右侧内容tab切换
	$(".account_r_c_t").find("h3").click(function() {
		var m = $(this).index() + 1;
		var m1 = ".account_r_c_c" + m;
		$(this).addClass("on").siblings().removeClass("on");
		$(m1).show().siblings().hide();
	});

	var msgFlg = "";
	msgFlg = "${showMsg}";
	if (msgFlg == "true") {
		alert("您的账号被锁定/被举报，请联系客服400-821-6888");
	}
});
	//调用弹出层
function showfancyMyApprove(gourl,wd,hg){
    var userStatus ="${userStatus}";
    if(userStatus=="lock"){
       alert("您的账号被锁定，请联系客服400-821-6888");
       return;
    }else if(userStatus=="report"){
       alert("您的账号被举报，请联系客服400-821-6888");
        return;
    }else{
		$.ajax({
		    url: gourl,
			wd: wd,
	        hg: hg,
			dataType: "html",
			success: function(html) {	
				 $.fancybox(html,{modal:true,autoDimensions:false,width:wd,height:hg,padding:0,margin:0,centerOnScroll:true});
			}
		})
	}
	
};
//关闭弹出层
function closefancy(){
    $.fancybox.close();
};
</script>

		<div class="account_r">
			<div class="my_info">
				<span>我的信用分数:${creditNote.creditScoreSum}
				<c:if test="${empty creditNote.creditScoreSum}">
						0
					</c:if>
				</span>
				<span>信用等级： <c:if test="${creditNote.creditGrade == 1}">
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
					</c:if></span>
				<br />
				<span>我的信用额度:${creditNote.creditAmount}
				<c:if test="${empty creditNote.creditAmount}">
						0
					</c:if>
				</span><span>可用额度：${kyed}</span>
			</div>
			<table class="account_table">
				<tr>
					<th>
						认证类别
					</th>
					<th>
						认证项目
					</th>
					<th>
						状态
					</th>
					<th>
						信用分数
					</th>
					<th>
						操作
					</th>
				</tr>

<tr>
					<td class="td_t">
						基本信息认证
					</td>
					<td>
						个人详细信息，工作信息
					</td>
					<td>
					   <c:if test="${appro.jiben.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
                       <c:if test="${appro.jiben.proStatus==1 && appro.jiben.reviewStatus==0}">
						<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
					  </c:if>
					  <c:if test="${appro.jiben.reviewStatus==1}">
					   <img src="${ctx}/static/images/img5.jpg" title="通过审核"
						alt="通过审核" />
					  </c:if>
					</td>
					<td>
                      <span class="col1">${appro.jiben.creditScore}</span>分
					</td>
					<td>
						<a href="${ctx}/myAccount/myAccount/managePersonal?strUrlType=myAccount" class="a_m">查看</a>
					</td>
				</tr>
				<tr>
					<td class="td_t" rowspan="4">
						必要信息认证
					</td>
					<td>
						身份证认证
					</td>
					<td>
						
								<c:if test="${appro.shenfenzheng.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.shenfenzheng.proStatus==1 && appro.shenfenzheng.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shenfenzheng.proStatus==1 && appro.shenfenzheng.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shenfenzheng.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
						
					</td>
					<td>
						
						
									<span class="col1">${appro.shenfenzheng.creditScore}</span>分
						
					</td>
					<td>
						     <c:if test="${appro.shenfenzheng.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.shenfenzheng.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/infoApproveNew/showIdentity?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>


				<tr>
					<td>
						工作认证
					</td>
					<td>
								<c:if test="${appro.gongzuozheng.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.gongzuozheng.proStatus==1 && appro.gongzuozheng.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
									<c:if test="${appro.gongzuozheng.proStatus==1 && appro.gongzuozheng.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.gongzuozheng.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
									<span class="col1">${appro.gongzuozheng.creditScore}</span>分
						
					</td>
					<td>
						<c:if test="${appro.gongzuozheng.proStatus==0}">
							 <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
						</c:if>
						<c:if test="${appro.gongzuozheng.proStatus==1}">
							<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/infoApproveNew/showWorkAuthJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
						</c:if>
					</td>
				</tr>
				<tr>
					<td>
						收入认证
					</td>
					<td>
								<c:if test="${appro.shouru.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.shouru.proStatus==1 && appro.shouru.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shouru.proStatus==1 && appro.shouru.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shouru.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
						
					</td>
					<td>
						
									<span class="col1">${appro.shouru.creditScore}</span>分
						
					</td>
					<td>
						<c:if test="${appro.shouru.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.shouru.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/infoApproveNew/showIncomeAuthJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>



				<tr>
					<td>
						信用报告认证
					</td>
					<td>
								<c:if test="${appro.xinyong.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.xinyong.proStatus==1 && appro.xinyong.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.xinyong.proStatus==1 && appro.xinyong.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.xinyong.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
						
					</td>
					<td>
						
									<span class="col1">${appro.xinyong.creditScore}</span>分
						
					</td>
					<td>
						        <c:if test="${appro.xinyong.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.xinyong.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/infoApproveNew/showCreditAuthJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_t" rowspan="10">
						可选信用认证
					</td>
					<td>
						房产认证
					</td>
					<td>
								<c:set var="proIdCount5" value="1"></c:set>
								<c:if test="${appro.fangchan.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.fangchan.proStatus==1 && appro.fangchan.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.fangchan.proStatus==1 && appro.fangchan.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.fangchan.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
						
					</td>
					<td>
						
									<span class="col1">${appro.fangchan.creditScore}</span>分
						
					</td>
					<td>
						<c:set var="proIdCount5" value="1"></c:set>
								<c:if test="${appro.fangchan.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.fangchan.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHousePropertyJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						技术职称认证
					</td>
					<td>
								<c:if test="${appro.jishu.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.jishu.proStatus==1 && appro.jishu.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.jishu.proStatus==1 && appro.jishu.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.jishu.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
						
					</td>
					<td>
						
									<span class="col1">${appro.jishu.creditScore}</span>分
						
					</td>
					<td>
						<c:if test="${appro.jishu.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.jishu.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updatajobTitleApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						购车证明
					</td>
					<td>
								<c:if test="${appro.gouche.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.gouche.proStatus==1 && appro.gouche.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.gouche.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.gouche.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.gouche.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.gouche.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataHaveCarJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
					
				</tr>
				<tr>
					<td>
						结婚认证
					</td>
					<td>
								<c:if test="${appro.jiehun.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.jiehun.proStatus==1 && appro.jiehun.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.jiehun.proStatus==1 && appro.jiehun.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.jiehun.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.jiehun.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.jiehun.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.jiehun.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataMarryApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						居住地证明
					</td>
					<td>
								<c:if test="${appro.juzhudi.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.juzhudi.proStatus==1 && appro.juzhudi.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.juzhudi.proStatus==1 && appro.juzhudi.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.juzhudi.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.juzhudi.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.juzhudi.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.juzhudi.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataLiveAddressApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						视频认证
					</td>
					<td>
								<c:if test="${appro.shipin.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.shipin.proStatus==1 && appro.shipin.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shipin.proStatus==1 && appro.shipin.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shipin.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.shipin.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.shipin.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.shipin.proStatus==1}">
								<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataVideoApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						实地认证
					</td>
					<td>
								<c:if test="${appro.shidi.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.shidi.proStatus==1 && appro.shidi.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shidi.proStatus==1 && appro.shidi.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shidi.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.shidi.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.shidi.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.shidi.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataRealInspectApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						学历认证（10分）
					</td>
					<td>
								<c:if test="${appro.xueli.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.xueli.proStatus==1 && appro.xueli.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.xueli.proStatus==1 && appro.xueli.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.xueli.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.xueli.creditScore}</span>分
					</td>
					<td>
						<c:if test="${appro.xueli.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.xueli.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataDegeeApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						手机实名认证（10分）
					</td>
					<td>
								<c:if test="${appro.shoujishiming.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shoujishiming.proStatus==1 && appro.shoujishiming.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.shoujishiming.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.shoujishiming.creditScore}</span>分
					</td>
					<td>
						
						
						<c:if test="${appro.shoujishiming.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.shoujishiming.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataPhoneApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td>
						微博认证（2分）
					</td>
					<td>
								<c:if test="${appro.weibo.proStatus==0}">
									<img src="${ctx}/static/images/img6.jpg" title="待上传" alt="待上传" />
								</c:if>
								<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==2}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.weibo.proStatus==1 && appro.weibo.reviewStatus==0}">
									<img src="${ctx}/static/images/img4.jpg" title="审核中" alt="审核中" />
								</c:if>
								<c:if test="${appro.weibo.reviewStatus==1}">
									<img src="${ctx}/static/images/img5.jpg" title="通过审核"
										alt="通过审核" />
								</c:if>
					</td>
					<td>
						
									<span class="col1">${appro.weibo.creditScore}</span>分
					</td>
					<td>
						
						<c:if test="${appro.weibo.proStatus==0}">
									<a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">上传资料</a>
								</c:if>
								<c:if test="${appro.weibo.proStatus==1}">
									<a href="#" onclick="showfancyMyApprove('${pageContext.request.contextPath}/borrowing/borrowing/updataWeiBoApproveJsp?way=0','725','450')" class="a_m">查看</a> | <a href="${ctx}/borrowing/borrowing/showApprove" class="a_m">补充上传</a>
								</c:if>
					</td>
				</tr>
				<tr>
					<td class="td_t" rowspan="3">
						e贷记录
					</td>
					<td>
						还清笔数（+1分/笔，加分间隔28天，总分上限20分）
					</td>
					<td></td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						逾期次数（-1分/次）
					</td>
					<td></td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>
				<tr>
					<td>
						严重逾期次数（-30分/次）
					</td>
					<td></td>
					<td>
						&nbsp;
					</td>
					<td>
						&nbsp;
					</td>
				</tr>


			</table>
			<table class="account_table">
				<tr>
					<th colspan="8" class="tl">
						&nbsp;&nbsp;&nbsp;&nbsp;信用等级及对应分数
					</th>
				</tr>
				<tr>
					<td class="td_t">
						信用等级
					</td>
					<td>
						<img src="${ctx}/static/images/img22.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img23.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img24.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img25.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img26.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img27.gif" />
					</td>
					<td>
						<img src="${ctx}/static/images/img28.gif" />
					</td>
				</tr>
				<tr>
					<td class="td_t">
						信用分数
					</td>
					<td>
						160+分
					</td>
					<td>
						145-159分
					</td>
					<td>
						130-144分
					</td>
					<td>
						120-129分
					</td>
					<td>
						110-119分
					</td>
					<td>
						100-109分
					</td>
					<td>
						0-99分
					</td>
				</tr>
			</table>
		</div>


