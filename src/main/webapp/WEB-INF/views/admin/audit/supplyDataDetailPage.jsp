<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="../adminHeader.jsp"%>
<%
String path = request.getContextPath();
String basePath = "";
%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/m_fuc.js" charset="utf-8"></script>
<script language="javascript">
<!-- 禁用右键: -->
function stop(){
   return false;
}
document.oncontextmenu=stop;
</script>
<script type="text/javascript">
	function oMemoDlg(){
		$('#memoDlg').dialog('open');
		var obj = $('.panel-title').parent().parent('.window');
		var obj3 = obj.next('.window-shadow');
		var obj2 = obj.next().next('.window-mask');
		var d_scr_h = $(document).height();
		var lay_h = $(window).height();
		var scr_h = $(document).scrollTop();
		var obj_h = obj.height();
		var h = (lay_h-obj_h)/2+scr_h;
		obj.css("top" ,h);
		obj3.css("top" ,h);
		obj2.css("height" ,d_scr_h);
	};
	function getByteLen(val) { 
			var len = 0; 
			for (var i = 0; i < val.length; i++) {
			 var c = val.charCodeAt(i); 
			 
			if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f))  //全角 
			len += 1; 
			else 
			len += 2; 
			} 
			return len; 
	}
	function memoSubmit(){
			var memo = $('#memoTxt').val();
			var memoLength = getByteLen($.trim($('#memoTxt').val()));
			if(memo=='不超过500个汉字！'){
				$.messager.alert('错误','不能为空！');
				return false;  
			};
			if(memoLength>1000){
				$.messager.alert('错误','字数不能超过五百！');
				return false;  
			};
			var memo = $('#memoTxt').val();
			  $.post("${ctx}/admin/audit/addAuditMemoNote", 
				     { 
				     userId: "${userAuditInfo.userId}", 
				     memo:memo
				     },
					function (data, textStatus){
					}, "json");
				$('#memoTxt').val('不超过500个汉字！');
				$('#memoDlg').dialog('close');
				$('#memoTbl').datagrid('reload');
		}
		
	function reset(){
	   location.reload();
	}
	
  	function memoFormatt(val,row){
		if(val.length>26){
			var innerValue = val.substring(0, 25)+'......'; 
			return '<a id="tip" title="'+val+'">'+innerValue+'</a>';
		}else {
			return val;
		}
	}
		   var count = 0;
   var picCount=0;
   function openPic1(path,name,type){
         $.post(
					"${ctx}/admin/audit/openFile?openFilePath="+path+"&openFileName="+name+"&userId=${userAuditInfo.userId}",
					function(result) {
					  count++;
					  picCount++;
					  if(count>10){
					  $.messager.alert('警告','同时最多打开10张图片！','warning');
					     count--;
					     picCount--;
					     return false;
					  }
			          $('#tt').tabs('add',{
				            title:type,
				            content:'<img src="<%=basePath%>/pic1/${userAuditInfo.email}/'+path+'/'+name+'"/>',
				            closable:true,
				            selected: false
			           });
					},"json");
   }
$(function(){
	$('#tt').tabs({
          onBeforeClose: function(title){
            count--;
         }
      });
});
function showPic(proId, imgPath, email, userId, typeName) {
    var url = "${ctx}/admin/audit/showImgJsp?proId=" + proId + "&imgPath=" + imgPath + "&email=" + email + "&userId=" + userId + "&typeName=" + typeName;
    window.open(url);
    return false;
}
</script>
</head>
<body>  
<div id="tt" class="easyui-tabs">
 <div title="审核信息"> 
 <div style="margin-bottom:10px;">信用额度：<input id="creditAmountTxt" type="text" value="${userAuditInfo.creditAmount}" disabled="disabled"/>元</div>
 <div style="margin-bottom:10px;">信用评分总和：<input id="creditTotle" type="text" value="${userAuditInfo.creditGradeTotle}" disabled="disabled"/></div>
 <table id="infoApprove" title="信息认证"   class="m_defined" width="100%">
		<tr>
		  <th width="100">认证类型</th>
		  <th width="150">认证项目</th>
		  <th width="200">附件</th>
		  <th width="100">信用分数</th>
		  <th width="100">审核</th>
		  <th width="150">审核时间</th>
		  <th>&nbsp;</th>
		</tr>
		<tr bgcolor="#FFFFFF">
		  <td align="center">基本信息</td>
		  <td align="center"><a href="${ctx}/admin/user/userInfoPersonPageRdJsp?userId=${userAuditInfo.userId}"  target="view_window">个人设置相关基本信息</a></td>
		  <td align="center">&nbsp;</td>
		  <td align="center">
		      <input id="16" name="creditGrade" type="text" value="${userAuditInfo.basicInfoApprove.creditScore}"/>
		  </td>
		  <td align="center">
		     <select id="sel16" name="cardSel">
		       <option value="0" <c:if test="${userAuditInfo.basicInfoApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		       <option value="1" <c:if test="${userAuditInfo.basicInfoApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		     </select>
		  </td>
		  <td align="center">${userAuditInfo.basicInfoApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center" rowspan="4">必要认证</td>
		  <td align="center">身份证认证</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.cardUserApprove.cardPath}" delims="," varStatus="status">
		         <div id="idCardCertificate${fn:substringBefore(name,";")}"> 
		         <!-- <a href="javascript:void(0)" onclick="openPic1('idCardCertificate','${fn:substringBefore(name,";")}','身份证${status.index+1}')">身份认证${status.index+1}</a> --> 
		      <a href="javascript:void(0)" onclick="showPic('1','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','idCardCertificate')" >身份认证${status.index+1}</a> 
		      <span>${fn:substringAfter(name,";")}</span>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		          <input id="1" name="creditGrade" type="text" value="${userAuditInfo.cardUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		       <td align="center">
		         <select id="sel1" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.cardUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.cardUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		         </select>
		        </td>
		 
		  <td align="center">${userAuditInfo.cardUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">工作认证</td>
		  <td align="center">
		   <c:forTokens var="name" items="${userAuditInfo.workUserApprove.cardPath}" delims="," varStatus="status">
		        <div id="userWorkImg${fn:substringBefore(name,";")}">
		           <a href="javascript:void(0)" onclick="showPic('2','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','userWorkImg')" >工作证${status.index+1}</a>
		     <!-- <a href="javascript:void(0)" onclick="openPic1('userWorkImg','${fn:substringBefore(name,";")}','工作证${status.index+1}')">工作证${status.index+1}</a> --> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		      <td align="center" >
		        <input id="2" name="creditGrade" type="text" value="${userAuditInfo.workUserApprove.creditScore}" disabled="disabled"/>
		      </td>
		      <td align="center">
		         <select id="sel2" name="cardSel" disabled="disabled">
		             <option value="0" <c:if test="${userAuditInfo.workUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		             <option value="1" <c:if test="${userAuditInfo.workUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		         </select>
		      </td>
		  <td align="center">${userAuditInfo.workUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">信用报告</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.creditReportUserApprove.cardPath}" delims="," varStatus="status">
		        <div id="userCreditRepZxImg${fn:substringBefore(name,";")}">
		        <!--   <a href="javascript:void(0)" onclick="openPic1('userCreditRepZxImg','${fn:substringBefore(name,";")}','信用报告${status.index+1}')">信用报告认证${status.index+1}</a> --> 
		         <a href="javascript:void(0)" onclick="showPic('4','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','userCreditRepZxImg')" >信用报告认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="4" name="creditGrade" type="text" value="${userAuditInfo.creditReportUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel4" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.creditReportUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.creditReportUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.creditReportUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">收入认证</td>
		  <td align="center">
		   <c:forTokens var="name" items="${userAuditInfo.earnUserApprove.cardPath}" delims="," varStatus="status">
		        <div id="userInComImg${fn:substringBefore(name,";")}"> 
		       <!-- <a href="javascript:void(0)" onclick="openPic1('userInComImg','${fn:substringBefore(name,";")}','收入认证${status.index+1}')">收入认证${status.index+1}</a> --> 
		        <a href="javascript:void(0)" onclick="showPic('3','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','userInComImg')" >收入认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="3" name="creditGrade" type="text" value="${userAuditInfo.earnUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel3" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.earnUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.earnUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.earnUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center" rowspan="10">可选认证</td>
		  <td align="center">房产认证</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.houseUserApprove.cardPath}" delims="," varStatus="status">
		       <div id="houseProperty${fn:substringBefore(name,";")}">
		        <!-- <a href="javascript:void(0)" onclick="openPic1('houseProperty','${fn:substringBefore(name,";")}','房产认证${status.index+1}')">房产认证${status.index+1}</a> -->
		         <a href="javascript:void(0)" onclick="showPic('5','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','houseProperty')" >房产认证${status.index+1}</a>
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="5" name="creditGrade" type="text" value="${userAuditInfo.houseUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel5" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.houseUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.houseUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.houseUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">购车认证</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.carUserApprove.cardPath}" delims="," varStatus="status">
		        <div id="haveCar${fn:substringBefore(name,";")}">
		          <!--   <a href="javascript:void(0)" onclick="openPic1('haveCar','${fn:substringBefore(name,";")}','购车认证${status.index+1}')">购车认证${status.index+1}</a> --> 
		         <a href="javascript:void(0)" onclick="showPic('7','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','haveCar')" >购车认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="7" name="creditGrade" type="text" value="${userAuditInfo.carUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel7" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.carUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.carUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.carUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">结婚认证</td>
		  <td align="center">
		   <c:forTokens var="name" items="${userAuditInfo.marryUserApprove.cardPath}" delims="," varStatus="status">
		       <div id="approveMarry${fn:substringBefore(name,";")}">
		        <!--  <a href="javascript:void(0)" onclick="openPic1('approveMarry','${fn:substringBefore(name,";")}','结婚认证${status.index+1}')">结婚认证${status.index+1}</a> --> 
		         <a href="javascript:void(0)" onclick="showPic('8','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','approveMarry')" >结婚认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="8" name="creditGrade" type="text" value="${userAuditInfo.marryUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel8" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.marryUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.marryUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.marryUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">学历认证</td>
		  <td align="center">
		      <div>
		       <c:choose>
		     <c:when test="${userAuditInfo.userInfoPerson.maxDegree!=null}">
		     <table  class="tbCss">
		      <tr><td><span>学历验证码:</span></td><td><span>${userAuditInfo.userInfoPerson.degreeNo}</span></td></tr>
		      <tr><td><span>最高学历:</span></td><td><span>${codeName}</span></td></tr>
		      <tr><td><span>学校:</span></td><td><span>${userAuditInfo.userInfoPerson.graduatSchool}</span></td></tr>
		      <tr><td><span>入学年份:</span></td><td><span>${userAuditInfo.userInfoPerson.inschoolTime}</span></td></tr>
		     </table>
		     </c:when>
		     </c:choose>
		    </div>
		  </td>
		       <td align="center" >
		         <input id="12" name="creditGrade" type="text" value="${userAuditInfo.educationUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel12" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.educationUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.educationUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.educationUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">居住地证明</td>
		  <td align="center">
		   <c:forTokens var="name" items="${userAuditInfo.addressUserApprove.cardPath}" delims="," varStatus="status">
		       <div id="liveAddress${fn:substringBefore(name,";")}">
		         <!-- <a href="javascript:void(0)" onclick="openPic1('liveAddress','${fn:substringBefore(name,";")}','居住地认证${status.index+1}')">居住地认证${status.index+1}</a> --> 
		        <a href="javascript:void(0)" onclick="showPic('9','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','liveAddress')" >居住地认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="9" name="creditGrade" type="text" value="${userAuditInfo.addressUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel9" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.addressUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.addressUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.addressUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">技术职称认证</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.techUserApprove.cardPath}" delims="," varStatus="status">
		       <div id="jobTitle${fn:substringBefore(name,";")}"> 
		        <!-- <a href="javascript:void(0)" onclick="openPic1('jobTitle','${fn:substringBefore(name,";")}','技术职称认证${status.index+1}')">技术职称认证${status.index+1}</a> --> 
		        <a href="javascript:void(0)" onclick="showPic('6','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','jobTitle')" >技术职称认证${status.index+1}</a>
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="6" name="creditGrade" type="text" value="${userAuditInfo.techUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel6" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.techUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.techUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.techUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">视频认证</td>
		  <td align="center">&nbsp;</td>
		       <td align="center" >
		         <input id="10" name="creditGrade" type="text" value="${userAuditInfo.videoUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel10" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.videoUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.videoUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.videoUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">手机实名认证</td>
		  <td align="center">
		    <c:forTokens var="name" items="${userAuditInfo.phoneUserApprove.cardPath}" delims="," varStatus="status">
		       <div id="phoneApprove${fn:substringBefore(name,";")}">
		        <!--  <a href="javascript:void(0)" onclick="openPic1('phoneApprove','${fn:substringBefore(name,";")}','手机实名认证${status.index+1}')">手机实名认证${status.index+1}</a> --> 
		         <a href="javascript:void(0)" onclick="showPic('13','${fn:substringBefore(name,";")}','${userAuditInfo.email}','${userAuditInfo.userId}','phoneApprove')" >手机实名认证${status.index+1}</a> 
		         <span>${fn:substringAfter(name,";")}</span>
		        </div>
		    </c:forTokens>
		  </td>
		       <td align="center" >
		         <input id="13" name="creditGrade" type="text" value="${userAuditInfo.phoneUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel13" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.phoneUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.phoneUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.phoneUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">微博认证</td>
		  <td align="center">${userAuditInfo.userInfoPerson.sinaWeiboAccount}</td>
		       <td align="center" >
		         <input id="14" name="creditGrade" type="text" value="${userAuditInfo.blogUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel14" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.blogUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.blogUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.blogUserApprove.auditTime}</td>
		</tr>
		<tr  bgcolor="#FFFFFF">
		  <td align="center">实地认证</td>
		  <td align="center">&nbsp;</td>
		       <td align="center" >
		         <input id="11" name="creditGrade" type="text" value="${userAuditInfo.fieldVisitUserApprove.creditScore}" disabled="disabled"/>
		       </td>
		        <td align="center">
		         <select id="sel11" name="cardSel" disabled="disabled">
		           <option value="0" <c:if test="${userAuditInfo.fieldVisitUserApprove.reviewStatus==0}">selected</c:if>>未通过</option>
		           <option value="1" <c:if test="${userAuditInfo.fieldVisitUserApprove.reviewStatus==1}">selected</c:if>>已通过</option>
		          </select>
		       </td>
		  <td align="center">${userAuditInfo.fieldVisitUserApprove.auditTime}</td>
		</tr>
	</table>
	<table nowrap="false" id="memoTbl"  class="easyui-datagrid"  title="备注"  toolbar="#toolbar" url="${ctx}/admin/audit/searchAuditMemoNote?userId=${userAuditInfo.userId}"
	  pagination="true" rownumbers="true"   fitColumns="true"  singleSelect="false" width="10" height="10">
		<thead>
			<tr>
				<th field="memo" width="100">备注内容</th>
				<th field="opearateUser" width="10">操作人</th>
				<th field="operateTimeFormatt" width="20">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="toolbar">
		<a class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="oMemoDlg();">添加备注</a>
	</div>
	<div style="height:15px; overflow:hidden;"></div>
	<table nowrap="false" id="returnTbl" class="easyui-datagrid" title="退回"  url="${ctx}/admin/audit/searchReturnMemoNote?userId=${userAuditInfo.userId}"
	  fitColumns="true" pagination="true" rownumbers="true"   singleSelect="false" width="10" height="10">
		<thead>
			<tr>
				<th field="operateNode" width="20">操作节点</th>
				<th field="cause" width="200">退回原因</th>
				<th field="opearateUser" width="30">操作人</th>
				<th field="operateTimeFormatt" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div style="height:15px; overflow:hidden;"></div>
	<table nowrap="false" id="rejectTbl" class="easyui-datagrid"  title="驳回"  url="${ctx}/admin/audit/searchRejectMemoNote?userId=${userAuditInfo.userId}"
	  fitColumns="true" pagination="true" rownumbers="true"   singleSelect="false" width="10" height="10">
		<thead>
			<tr>
				<th field="operateNode" width="20">操作节点</th>
				<th field="cause" width="200">驳回原因</th>
				<th field="opearateUser" width="30">操作人</th>
				<th field="operateTimeFormatt" width="80">操作时间</th>
			</tr>
		</thead>
	</table>
	<div id="memoDlg" modal="true" class="easyui-dialog" title="添加备注" closed="true" style="padding:5px; width:400px; height:200px;" data-options="buttons:'#dlg-buttons'">
		<textarea id="memoTxt" style="width:370px; height:110px; font-size:12px;" onfocus="if(this.value=='不超过500个汉字！')this.value=''" onblur="if(this.value=='')this.value='不超过500个汉字！'">不超过500个汉字！</textarea>
	</div>
	<div id="dlg-buttons">
		<a class="easyui-linkbutton" onclick="javascript:memoSubmit()">提交</a>
		<a class="easyui-linkbutton" onclick="javascript:$('#memoDlg').dialog('close')">取消</a>
	</div>
 </div>
 </div>
</body>
</html>
