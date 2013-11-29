<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<meta http-equiv="pragma" content="no-cache" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script src="${ctx}/static/jquery/1.7.1/jquery.min.js" type="text/javascript"></script>
<script src="${ctx}/static/jquery/jquery.form.js"></script>
<style type="text/css">
.red{color:red;}
.promt-error{color:red;}
</style>
<script type="text/javascript">
function creShiOptionBase()
{
	var sheng=$("#hometownArea").val();
			$.ajax( {
				url : "${ctx}/personal/personal/queryArea?code="+sheng,
				type : "POST",
				dataType : 'JSON',
				success : function(strFlg) {
					$("#hometownCity").empty();	
					for(var i=0;i<strFlg.length;i++)
					{
						$("#hometownCity").append("<option value="+strFlg[i].id+">"+strFlg[i].name+"</option>");
					}
				}
			});
}
</script>
<script language="javascript" type="text/javascript">
$(function(){
	$('#btn1').unbind('click');
	$("#btn1").click(function(){
		var m_flg = checkInput($(this));
		if(m_flg==false){return m_flg};
		$.ajax({
					 data: $(".baseForm").serialize(),
		    		 url: "${ctx}/personal/personal/savePersonalBase",
		    	 	 type: "POST",
		    		 dataType: 'html',
		    		 timeout: 10000,
		     	 error: function(){
		     	 		alert('error');
		      },
			   	success: function(data){
			   			alert("保存成功.");
			   			return;
			   	}
		});
		
	});
	//上传头像验证
	$("input[type='submit'], #btn1").click(function(){
		var file_flg = $("#upload").val();
		if(!file_flg == "" && !/(.jpg|.png|.gif|.jpeg|.jpe|.bmp|.JPG|.PNG|.GIF|.JPEG|.BMP)$/.test(file_flg)){
			$("#hidden_frame").next("label").remove().end().after("<label class='col1 font_12'>上传的图片格式错误(支持jpg、png、gif、bmp)</label>");
			return false;
		}else{
			$("#hidden_frame").next("label").remove();
		};
	});
	    var options = {   
        success: mainform2Response,
        timeout:20000,
        error:error
    };   
    $('#headForm').ajaxForm(options);

});
function error(){
   alert("请求超时！");
}
function mainform2Response(req){
	//var re= eval('('+req+')');
	var m = req.indexOf(true);
	if(m!="-1"){
	   window.hidden_frame.location.reload();   
		alert("已经提交成功");	
	}else{
	   var s = req.indexOf(false);
	   if(s!="-1"){
	     alert("提交失败");
	   }else{
	    alert("提交失败，上传的文件超过大小(1.5M)");
	   }
	}
}
</script>
<form:form class="baseForm" modelAttribute="baseVO"  enctype="multipart/form-data" method="post">

						<div class="prompt6">
							<p>请填写以下基本资料：（注：带*为必填）</p>
						</div>
						<table>
							<tr><td class="td8">昵称：</td>
							   <td>
							      <input type="hidden" name="loginName" id="loginName" value="${baseVO.loginName}"  readonly="readonly" />
							      <span>${baseVO.loginName}</span>
							   </td>
							</tr>
							<tr>
							<td class="td8">邮箱：</td>
							   <td>
							      <input type="hidden" name="email" id="email" value="${baseVO.email}" readonly="readonly"/>
							      <span>${baseVO.email}</span>
							   </td>
							</tr>
							<tr>
							<td class="td8">注册来源：</td>
							   <td>
							      <span>${baseVO.channelFName  }</span>
							      <span>${baseVO.channelCName  }</span>
							   </td>
							</tr>
							
							
							</table>
</form:form>
<form:form id="headForm" action="${ctx}/borrowing/borrowing/updataHeadPhoto" enctype="multipart/form-data" method="post" target="hidden_frame">
						<table>
							 <tr>
                                <td class="td8">上传头像：</td>
                                <td>
								<iframe name="hidden_frame" style="float:left;" id="hidden_frame"  src="${ctx}/head/showheadImg" width="105" height="104" marginwidth="0" marginheight="0" frameborder="0" ></iframe> 
                                 </td>
                             </tr>
                             <tr>
                             	<td>&nbsp;</td>
                             	<td><input type="file" id="upload" size="10"  name="upload" /><input type="submit"  value="上传头像"/> </td>
                             </tr>
						</table>
</form:form>
<form:form class="baseForm">
							<table>
							<tr><td class="td8">*真实姓名：</td><td>
							   <c:if test="${empty baseVO.name}">
                               <input type="text" name="name" id="name" value="${baseVO.name}" class="required name" />
                               </c:if>
                               <c:if test="${not empty baseVO.name}">
                                   <input type="hidden" name="name" id="name" value="${baseVO.name}" class="required name" />
                                   <span>${baseVO.name}</span>
                               </c:if>
                            </td></tr>
							<tr><td class="td8"> *性别：</td>
                            	<td>
                                	<c:if test="${empty baseVO.sex}">
                                    <input type="radio" name="sex" id="sex_male" value="1" checked="checked"/>男
                                    <input type="radio" name="sex" id="sex_female" value="2" />女
                                    </c:if>                              
                                    <c:if test="${not empty baseVO.sex}">
                                    <c:if test="${baseVO.sex=='1'}">男</c:if>
                                    <c:if test="${baseVO.sex=='2'}">女</c:if>
                                    <input type="hidden" name="sex" value="${baseVO.sex}"/>
                                    </c:if>                                
                                </td>
                            </tr>
							<tr><td class="td8">*证件类型：</td>
                            	<td>
                                	<c:if test="${empty baseVO.credentialType}">
                                        <select name="credentialType" id="credentialType" >
                                            <option value="1" <c:if test="${baseVO.credentialType=='1'}" >selected="selected"</c:if> >身份证</option>
                                            <option value="2" <c:if test="${baseVO.credentialType=='2'}" >selected="selected"</c:if> >军官证</option>
                                        </select>
                                    </c:if>
                                	<c:if test="${not empty baseVO.credentialType}">
                                   		 <c:if test="${baseVO.credentialType=='1'}" >身份证</c:if>
                                   		 <c:if test="${baseVO.credentialType=='2'}" >军官证</c:if>
                                         <input type="hidden" name="credentialType" value="${baseVO.credentialType}" />
                                    </c:if>                                    

                                </td>
                            </tr>
							<tr><td class="td8">*证件号码：</td>
							   <td>
							      <c:if test="${empty baseVO.credentialNum}">
							         <input type="text" name="credentialNum" id="credentialNum" value="${baseVO.credentialNum}" class="required identity"/>
							      </c:if>
							      <c:if test="${not empty baseVO.credentialNum}">
							         <input type="hidden" name="credentialNum" id="credentialNum" value="${baseVO.credentialNum}" class="required identity"/>
							         <span>${baseVO.credentialNum}</span>
							      </c:if>
							   </td>
							</tr>
							<tr><td class="td8">*手机号码：</td>
							    <td>
							     <c:if test="${empty baseVO.mobile}"> 
							       <input type="text" name="mobile" id="mobile" value="${baseVO.mobile}" class="required phone"/>
							     </c:if>
							    <c:if test="${not empty baseVO.mobile}"> 
							       <input type="hidden" name="mobile" id="mobile" value="${baseVO.mobile}" class="required phone"/>
							       <span>${baseVO.mobile}</span>
							    </c:if>
							</td></tr>
							<tr><td class="td8">籍贯：</td>
                            	<td>                                
                                <select name="hometownArea" id="hometownArea" onchange="creShiOptionBase()">
                                	<c:forEach var="sheng" items="${shengList}">
                                    	<option value="${sheng.id}"  <c:if test="${baseVO.hometownArea==sheng.id}" >selected="selected"</c:if>>${sheng.name}</option>
                                    </c:forEach>
                                </select>
                                <select name="hometownCity" id="hometownCity">
                                	<c:forEach var="shi" items="${shiList}">
                                    	<option value="${shi.id}" <c:if test="${baseVO.hometownCity==shi.id}" >selected="selected"</c:if> >${shi.name}</option>
                                    </c:forEach>
                                </select>
                                </td>
                            </tr>
							<tr><td class="td8">*现居住地址：</td><td>
                                <c:if test="${empty baseVO.currAddr}">  
                                 <input type="text" name="currAddr" id="currAddr" value="${baseVO.currAddr}" class="required chars100"/>
                               </c:if>
                               <c:if test="${not empty baseVO.currAddr}">  
                                 <input type="hidden" name="currAddr" id="currAddr" value="${baseVO.currAddr}" class="required"/>
                                 <span>${baseVO.currAddr}</span>
                               </c:if>
                            </td></tr>
							<tr><td class="td8">居住地电话：</td>
                            	<td><input class="input_40 required_db3" type="text" name="liveAreaCode" id="liveAreaCode" value="${baseVO.liveAreaCode}">-<input class="input_120 required_db4" type="text" name="liveTel" id="liveTel" value="${baseVO.liveTel}"/></td>
                            </tr>
							<tr><td class="td8">居住地邮编：</td><td><input class="postal" type="text" name="livePost" id="livePost" value="${baseVO.livePost}" /></td></tr>
							<tr><td class="td8">*婚姻状况：</td><td>
                            <c:if test="${not empty baseVO.marriage}">
                            <c:if test="${baseVO.marriage=='56'}">未婚</c:if>
                            <c:if test="${baseVO.marriage=='57'}">已婚</c:if>
                            <c:if test="${baseVO.marriage=='59'}">离异</c:if>
                            <c:if test="${baseVO.marriage=='58'}">丧偶</c:if>
                            <input  type="hidden" name="marriage" value="${baseVO.marriage}" />
                            </c:if>
                            <c:if test="${empty baseVO.marriage}">
                              	<select name="marriage" id="marriage">
                                	<option value="56" <c:if test="${baseVO.marriage==56}" >selected="selected"</c:if>>未婚</option>
                                    <option value="57" <c:if test="${baseVO.marriage==57}" >selected="selected"</c:if>>已婚</option>
                                    <option value="59" <c:if test="${baseVO.marriage==59}" >selected="selected"</c:if>>离异</option>
                                    <option value="58" <c:if test="${baseVO.marriage==58}" >selected="selected"</c:if>>丧偶</option>                                                                   
                                </select>                          
                            </c:if>                   
                            </tr>
							<tr><td class="td8">子女：</td><td>
                            	<select name="child" id="child">
                            		 <option value="0" <c:if test="${baseVO.child=='0'}">selected</c:if>>无</option>
                                     <option value="1" <c:if test="${baseVO.child=='1'}">selected</c:if>>有</option>
                                </select>
                            </tr>
							<tr><td class="td8">*月收入：</td><td>
							  <c:if test="${empty baseVO.monIncome}">
							    <input type="text" name="monIncome"  id="monIncome" value="${baseVO.monIncome}" class="required money"/>
							  </c:if>
							  <c:if test="${not empty baseVO.monIncome}">
							    <input type="hidden" name="monIncome"  id="monIncome" value="${baseVO.monIncome}" class="required money"/>
							    <span>${baseVO.monIncome}</span>
							  </c:if>
                              </td></tr>
							<tr><td class="td8">*直系亲属姓名：</td><td>
							  <c:if test="${empty baseVO.immFamilyName}">
							    <input type="text" name="immFamilyName" id="immFamilyName" value="${baseVO.immFamilyName}" class="required name" />
							  </c:if>
							  <c:if test="${not empty baseVO.immFamilyName}">
							    <input type="hidden" name="immFamilyName" id="immFamilyName" value="${baseVO.immFamilyName}" class="required name" />
							    <span>${baseVO.immFamilyName}</span>
							  </c:if>
                            </td></tr>
							<tr><td class="td8">*关系：</td><td>
                              <c:if test="${empty baseVO.immFamilyRela}">
                               <input type="text" name="immFamilyRela" id="immFamilyRela" value="${baseVO.immFamilyRela}" class="required chars50"/>
                              </c:if> 
                              <c:if test="${not empty baseVO.immFamilyRela}">
                               <input type="hidden" name="immFamilyRela" id="immFamilyRela" value="${baseVO.immFamilyRela}" class="required"/>
                               <span>${baseVO.immFamilyRela}</span>
                              </c:if> 
                             </td></tr>
							<tr><td class="td8">*手机：</td><td>
                            <c:if test="${empty baseVO.immFamilyMobile}">
                              <input type="text" name="immFamilyMobile" id="immFamilyMobile" value="${baseVO.immFamilyMobile}" class="required phone"/>
                            </c:if> 
                            <c:if test="${not empty baseVO.immFamilyMobile}">
                              <input type="hidden" name="immFamilyMobile" id="immFamilyMobile" value="${baseVO.immFamilyMobile}" class="required phone"/>
                              <span>${baseVO.immFamilyMobile}</span>
                            </c:if> 
                             </td></tr>
							<tr><td class="td8">*其他联系人姓名：</td><td>
                               <c:if test="${empty baseVO.otherContactName}">
                                 <input type="text" name="otherContactName" id="otherContactName" value="${baseVO.otherContactName}" class="required name" />
                               </c:if>
                               <c:if test="${not empty baseVO.otherContactName}">
                                 <input type="hidden" name="otherContactName" id="otherContactName" value="${baseVO.otherContactName}" class="required name" />
                                 <span>${baseVO.otherContactName}</span>
                               </c:if>
                             </td></tr>
							<tr><td class="td8">*关系：</td><td>
                              <c:if test="${empty baseVO.otherContactRela}">
                                <input type="text" name="otherContactRela" id="otherContactRela" value="${baseVO.otherContactRela}" class="required chars50" />
                              </c:if> 
                              <c:if test="${not empty baseVO.otherContactRela}">
                                <input type="hidden" name="otherContactRela" id="otherContactRela" value="${baseVO.otherContactRela}" class="required" />
                                <span>${baseVO.otherContactRela}</span>
                             </c:if> 
                             </td></tr>
							<tr><td class="td8">*手机：</td><td>
                               <c:if test="${empty baseVO.otherContactMobile}">
                                  <input type="text" name="otherContactMobile" id="otherContactMobile" value="${baseVO.otherContactMobile}" class="required phone"  />
                               </c:if>
                               <c:if test="${not empty baseVO.otherContactMobile}">
                                  <input type="hidden" name="otherContactMobile" id="otherContactMobile" value="${baseVO.otherContactMobile}" class="required phone"  />
                                  <span>${baseVO.otherContactMobile}</span>
                               </c:if>
                             </td></tr>
                             	<tr><td class="td8">*联系人三姓名：</td><td>
                               <c:if test="${empty baseVO.threeContactName}">
                                 <input type="text" name="threeContactName" id="threeContactName" value="${baseVO.threeContactName}"  class="required name"/>
                               </c:if>
                               <c:if test="${not empty baseVO.threeContactName}">
                                 <input type="hidden" name="threeContactName" id="threeContactName" value="${baseVO.threeContactName}"  />
                                 <span>${baseVO.threeContactName}</span>
                               </c:if>
                             </td></tr>
							<tr><td class="td8">*关系：</td><td>
                              <c:if test="${empty baseVO.threeContactRelation}">
                                <input type="text" name="threeContactRelation" id="threeContactRelation" value="${baseVO.threeContactRelation}"  class="required chars50"/>
                              </c:if> 
                              <c:if test="${not empty baseVO.threeContactRelation}">
                                <input type="hidden" name="threeContactRelation" id="threeContactRelation" value="${baseVO.threeContactRelation}" />
                                <span>${baseVO.threeContactRelation}</span>
                             </c:if> 
                             </td></tr>
							<tr><td class="td8">*手机：</td><td>
                               <c:if test="${empty baseVO.threeContactPhone}">
                                  <input type="text" name="threeContactPhone" id="threeContactPhone" value="${baseVO.threeContactPhone}" class="required phone"  />
                               </c:if>
                               <c:if test="${not empty baseVO.threeContactPhone}">
                                  <input type="hidden" name="threeContactPhone" id="threeContactPhone" value="${baseVO.threeContactPhone}" class="required phone"  />
                                  <span>${baseVO.threeContactPhone}</span>
                               </c:if>
                             </td></tr>
                               	<tr><td class="td8">*联系人四姓名：</td><td>
                               <c:if test="${empty baseVO.fourthContactName}">
                                 <input type="text" name="fourthContactName" id="fourthContactName" value="${baseVO.fourthContactName}" class="required name" />
                               </c:if>
                               <c:if test="${not empty baseVO.fourthContactName}">
                                 <input type="hidden" name="fourthContactName" id="fourthContactName" value="${baseVO.fourthContactName}"  />
                                 <span>${baseVO.fourthContactName}</span>
                               </c:if>
                             </td></tr>
							<tr><td class="td8">*关系：</td><td>
                              <c:if test="${empty baseVO.fourthContactRelation}">
                                <input type="text" name="fourthContactRelation" id="fourthContactRelation" value="${baseVO.fourthContactRelation}" class="required chars50" />
                              </c:if> 
                              <c:if test="${not empty baseVO.fourthContactRelation}">
                                <input type="hidden" name="fourthContactRelation" id="fourthContactRelation" value="${baseVO.fourthContactRelation}"  />
                                <span>${baseVO.fourthContactRelation}</span>
                             </c:if> 
                             </td></tr>
							<tr><td class="td8">*手机：</td><td>
                               <c:if test="${empty baseVO.fourthContactPhone}">
                                  <input type="text" name="fourthContactPhone" id="fourthContactPhone" value="${baseVO.fourthContactPhone}" class="required phone"  />
                               </c:if>
                               <c:if test="${not empty baseVO.fourthContactPhone}">
                                  <input type="hidden" name="fourthContactPhone" id="fourthContactPhone" value="${baseVO.fourthContactPhone}" class="required phone"  />
                                  <span>${baseVO.fourthContactPhone}</span>
                               </c:if>
                             </td></tr>
							<tr><td class="td8">QQ：</td><td><input type="text" name="qq" id="qq" value="${baseVO.qq}" /></td></tr>
							<tr><td class="td8">MSN：</td><td><input type="text" name="msn" id="msn" value="${baseVO.msn}" class="chars50"/></td></tr>
							<tr><td class="td8">新浪微博账号：</td><td><input type="text" name="weibo" id="weibo" value="${baseVO.weibo}" class="chars100" /></td></tr>
						</table>
						<input type="hidden" name="token" id="token" value="${sessionScope.token}"/>
                        <input  type="button" id="btn1" class="btn3 mar10" value="保存"/>
</form:form>