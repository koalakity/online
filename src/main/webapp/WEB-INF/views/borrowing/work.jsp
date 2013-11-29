<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<script type="text/javascript" src="${ctx}/static/js/mValidate.js"></script>
<script type="text/javascript">
$(function(){
	//焦点验证
	$.mValidate($('#workForm'),"true");
});
function submitAuditWork(){
	//提交验证，通过则关闭弹出层
	if ($.mValidate($('#workForm'), "", "true")) {
	    if (parseInt($("#uploadCount").val()) != 0) {
	        alert("文件正在上传，这可能需要花费一点时间，请耐心等待！");
	    } else {
	        $.ajax({
	            data: $("#workForm").serialize(),
	            url: "${ctx}/infoApproveNew/updataWork",
	            type: "POST",
	            dataType: 'text',
	            success: function(data) {
	                if (data == "true") {
	                    alert("保存成功.");
	                    closefancy();
	                    window.top.location.reload();
	                } else if (data == "noUpFile") {
	                    alert("未提交附件，请提交附件后再上传！");
	                }else{
	                   window.location.href='${ctx}/accountLogin/login/redirectLogin';
	                }
	            }
	        });
	    }
	} else {
	    return false;
	};
}
function closeWork(){
   closefancy();
}
</script>
<div class="autlayer">
    <div class="autlayer_t">
        <img src="${ctx}/static/images/gongzuo_t.jpg" />
        <a class="autlayer_close fr" onclick="parent.closefancy();">
        </a>
    </div>
    <div class="autlayer_c">
        <div class="upload_t">
            <p>
                1.工薪阶层：可上传带有姓名及照片的工作证、劳动合同、在职证明（加盖公章）；
            </p>
            <p>
                2.私营企业主：可上传营业执照、税务登记证、店面照片（含营业执照）；
            </p>
            <p>
                3.淘宝网商：可上传网店主页和后台的截屏(含网址）、最近3个月的成功交易流水记录
            </p>
        </div>
        <form id="workForm" method="post">
            <table class="upload_c">
                <c:choose>
                    <c:when test="${way=='0'}">
                            <tr>
                                <td class="td1">
                                    公司名称
                                </td>
                                <td class="td2">
                                    <input type="text" name="corporationName" value="${jobInfo.corporationName}" class="input_text1" disabled="disabled" />
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    公司类别
                                </td>
                                <td class="td2">
                                    <div class="select1_border">
                                        <div class="select1_con">
                                            <select name="corporationKind" class="select1" disabled="disabled">
                                                <option value="1" <c:if test="${jobInfo.corporationKind==1}">selected="selected"</c:if> > 国家机关</option>
                                                <option value="2" <c:if test="${jobInfo.corporationKind==2}">selected="selected"</c:if>> 事业单位</option>
                                                <option value="3" <c:if test="${jobInfo.corporationKind==3}">selected="selected"</c:if>> 央企(包括下级单位)</option>
                                                <option value="4" <c:if test="${jobInfo.corporationKind==4}">selected="selected"</c:if> > 地方国资委直属企业</option>
                                                <option value="5" <c:if test="${jobInfo.corporationKind==5}">selected="selected"</c:if>> 世界500强（包含合资企业及下级单位）</option>
                                                <option value="6" <c:if test="${jobInfo.corporationKind==6}">selected="selected"</c:if> > 外资企业（包含合资企业）</option>
                                                <option value="7" <c:if test="${jobInfo.corporationKind==7}">selected="selected" </c:if>> 上市公司（包含国外上市</option>
                                                <option value="8" <c:if test="${jobInfo.corporationKind==8}">selected="selected"</c:if>> 民营企业</option>
                                            </select>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    公司行业
                                </td>
                                <td class="td2">
                                    <div class="select1_border">
                                        <div class="select1_con">
                                            <select name="corporationIndustry" class="select1" disabled="disabled">
													<option value="11" <c:if test="${jobInfo.corporationIndustry==11}" >selected="selected"</c:if>>农</option>
													<option value="12" <c:if test="${jobInfo.corporationIndustry==12}" >selected="selected"</c:if>>林</option>
													<option value="13" <c:if test="${jobInfo.corporationIndustry==13}" >selected="selected"</c:if>>牧</option>
													<option value="14" <c:if test="${jobInfo.corporationIndustry==14}" >selected="selected"</c:if>>渔业</option>
													<option value="15" <c:if test="${jobInfo.corporationIndustry==15}" >selected="selected"</c:if>>制造业</option>
													<option value="16" <c:if test="${jobInfo.corporationIndustry==16}" >selected="selected"</c:if>>电力</option>
													<option value="17" <c:if test="${jobInfo.corporationIndustry==17}" >selected="selected"</c:if>>燃气及水的生产和供应业</option>
													<option value="18" <c:if test="${jobInfo.corporationIndustry==18}" >selected="selected"</c:if>>建筑业</option>
													<option value="19" <c:if test="${jobInfo.corporationIndustry==19}" >selected="selected"</c:if>>交通运输</option>
													<option value="20" <c:if test="${jobInfo.corporationIndustry==20}" >selected="selected"</c:if>>仓储和邮政业</option>
													<option value="21" <c:if test="${jobInfo.corporationIndustry==21}" >selected="selected"</c:if>>信息传输</option>
													<option value="22" <c:if test="${jobInfo.corporationIndustry==22}" >selected="selected"</c:if>>计算机服务和软件业</option>
													<option value="23" <c:if test="${jobInfo.corporationIndustry==23}" >selected="selected"</c:if>>批发零售业</option>
													<option value="24" <c:if test="${jobInfo.corporationIndustry==24}" >selected="selected"</c:if>>金融业</option>
													<option value="25" <c:if test="${jobInfo.corporationIndustry==25}" >selected="selected"</c:if>>房地产业</option>
													<option value="26" <c:if test="${jobInfo.corporationIndustry==26}" >selected="selected"</c:if>>采矿业</option>
													<option value="27" <c:if test="${jobInfo.corporationIndustry==27}" >selected="selected"</c:if>>租赁和商务服务业</option>
													<option value="28" <c:if test="${jobInfo.corporationIndustry==28}" >selected="selected"</c:if>>科学研究</option>
													<option value="29" <c:if test="${jobInfo.corporationIndustry==29}" >selected="selected"</c:if>>技术服务和地址勘查业</option>
													<option value="30" <c:if test="${jobInfo.corporationIndustry==30}" >selected="selected"</c:if>>水利</option>
													<option value="31" <c:if test="${jobInfo.corporationIndustry==31}" >selected="selected"</c:if>>环境和公共设施管理业</option>
													<option value="32" <c:if test="${jobInfo.corporationIndustry==32}" >selected="selected"</c:if>>居民服务和其他服务业</option>
													<option value="33" <c:if test="${jobInfo.corporationIndustry==33}" >selected="selected"</c:if>>教育</option>
													<option value="34" <c:if test="${jobInfo.corporationIndustry==34}" >selected="selected"</c:if>>卫生</option>
													<option value="35" <c:if test="${jobInfo.corporationIndustry==35}" >selected="selected"</c:if>>社会保障和社会福利业</option>
													<option value="36" <c:if test="${jobInfo.corporationIndustry==36}" >selected="selected"</c:if>>文化</option>
													<option value="37" <c:if test="${jobInfo.corporationIndustry==37}" >selected="selected"</c:if>>体育和娱乐业</option>
													<option value="38" <c:if test="${jobInfo.corporationIndustry==38}" >selected="selected"</c:if>>公共管理和社会组织</option>
													<option value="39" <c:if test="${jobInfo.corporationIndustry==39}" >selected="selected"</c:if>>国际组织</option>
                                            </select>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    公司规模
                                </td>
                                <td class="td2">
                                    <div class="select1_border">
                                        <div class="select1_con">
                                            <select name="corporationScale" class="select1" disabled="disabled">
 													<option value="40" <c:if test="${jobInfo.corporationScale==40}" >selected="selected"</c:if>>
														10人以下
													</option>
													<option value="41" <c:if test="${jobInfo.corporationScale==41}" >selected="selected"</c:if>>
														10-100人
													</option>
													<option value="42" <c:if test="${jobInfo.corporationScale==42}" >selected="selected"</c:if>>
														100-500人
													</option>
													<option value="43" <c:if test="${jobInfo.corporationScale==43}" >selected="selected"</c:if>>
														500人以上
													</option>
                                            </select>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    职位
                                </td>
                                <td class="td2">
                                    <input type="text" name="position" value="${jobInfo.position}" class="input_text1" disabled="disabled" />
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    在现单位工作年限
                                </td>
                                <td class="td2">
                                    <div class="select1_border">
                                        <div class="select1_con">
                                            <select id="presentCorporationWorkTime" name="presentCorporationWorkTime"class="select1" disabled="disabled">
		                                            <option value="72" <c:if test="${jobInfo.presentCorporationWorkTime==72}">selected="selected"</c:if>> 1年以内</option>
		                                            <option value="73" <c:if test="${jobInfo.presentCorporationWorkTime==73}">selected="selected"</c:if>> 1到3年</option>
		                                            <option value="74" <c:if test="${jobInfo.presentCorporationWorkTime==74}">selected="selected"</c:if>> 3到5年</option>
		                                            <option value="75" <c:if test="${jobInfo.presentCorporationWorkTime==75}">selected="selected" </c:if>>5到10年</option>
		                                            <option value="76" <c:if test="${jobInfo.presentCorporationWorkTime==76}">selected="selected"</c:if> >10年以上</option>
                                            </select>
                                        </div>
                                    </div>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    工作电话
                                </td>
                                <td class="td2">
                                    <input type="text" name="corporationPhoneArea" value="${jobInfo.corporationPhoneArea}" class="input_text1 wd_r1" disabled="disabled" />
                                    -
                                    <input type="text" name="corporationPhone" value="${jobInfo.corporationPhone}"class="input_text1 wd_r2" disabled="disabled" c/>
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    工作邮箱
                                </td>
                                <td class="td2">
                                    <input type="text" name="jobEmail" value="${jobInfo.jobEmail}" class="input_text1" disabled="disabled" />
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                            <tr>
                                <td class="td1">
                                    公司地址
                                </td>
                                <td class="td2">
                                    <input type="text" name="corporationAddress" value="${jobInfo.corporationAddress}" class="input_text1" disabled="disabled" />
                                </td>
                                <td>
                                    &nbsp;
                                </td>
                            </tr>
                          <c:forTokens var="filePath" items="${sb}" delims=";" varStatus="status">
                            <tr>
                                <td class="td1">
                                    附件${status.index+1}：
                                </td>
                                <td>
                                    <span>
                                        ${filePath}
                                    </span>
                                </td>
                            </tr>
                        </c:forTokens>
							<tr>
							    <td colspan="2">
							        <input type="button" onclick="closeWork()" class="btn_r1 fr" value="关闭"/>
							    </td>
							    <td>
							        &nbsp;
							    </td>
							</tr>
                    </c:when>
                    <c:otherwise>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>公司名称
                            </td>
                            <td class="td2">
                                <input type="text" name="corporationName" value="${jobInfo.corporationName}"
                                class="input_text1 chars50 required" />
                            </td>
                            <td><label class="mvalidatemsg"></label></td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>公司类别
                            </td>
                            <td class="td2">
                                <div class="select1_border">
                                    <div class="select1_con">
                                        <select name="corporationKind" class="select1">
                                                <option value="1" <c:if test="${jobInfo.corporationKind==1}">selected="selected"</c:if> > 国家机关</option>
                                                <option value="2" <c:if test="${jobInfo.corporationKind==2}">selected="selected"</c:if>> 事业单位</option>
                                                <option value="3" <c:if test="${jobInfo.corporationKind==3}">selected="selected"</c:if>> 央企(包括下级单位)</option>
                                                <option value="4" <c:if test="${jobInfo.corporationKind==4}">selected="selected"</c:if> > 地方国资委直属企业</option>
                                                <option value="5" <c:if test="${jobInfo.corporationKind==5}">selected="selected"</c:if>> 世界500强（包含合资企业及下级单位）</option>
                                                <option value="6" <c:if test="${jobInfo.corporationKind==6}">selected="selected"</c:if> > 外资企业（包含合资企业）</option>
                                                <option value="7" <c:if test="${jobInfo.corporationKind==7}">selected="selected" </c:if>> 上市公司（包含国外上市</option>
                                                <option value="8" <c:if test="${jobInfo.corporationKind==8}">selected="selected"</c:if>> 民营企业</option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="td1">
                               <span class="col_r4">*</span>公司行业
                            </td>
                            <td class="td2">
                                <div class="select1_border">
                                    <div class="select1_con">
                                        <select name="corporationIndustry" class="select1">
													<option value="11" <c:if test="${jobInfo.corporationIndustry==11}" >selected="selected"</c:if>>农</option>
													<option value="12" <c:if test="${jobInfo.corporationIndustry==12}" >selected="selected"</c:if>>林</option>
													<option value="13" <c:if test="${jobInfo.corporationIndustry==13}" >selected="selected"</c:if>>牧</option>
													<option value="14" <c:if test="${jobInfo.corporationIndustry==14}" >selected="selected"</c:if>>渔业</option>
													<option value="15" <c:if test="${jobInfo.corporationIndustry==15}" >selected="selected"</c:if>>制造业</option>
													<option value="16" <c:if test="${jobInfo.corporationIndustry==16}" >selected="selected"</c:if>>电力</option>
													<option value="17" <c:if test="${jobInfo.corporationIndustry==17}" >selected="selected"</c:if>>燃气及水的生产和供应业</option>
													<option value="18" <c:if test="${jobInfo.corporationIndustry==18}" >selected="selected"</c:if>>建筑业</option>
													<option value="19" <c:if test="${jobInfo.corporationIndustry==19}" >selected="selected"</c:if>>交通运输</option>
													<option value="20" <c:if test="${jobInfo.corporationIndustry==20}" >selected="selected"</c:if>>仓储和邮政业</option>
													<option value="21" <c:if test="${jobInfo.corporationIndustry==21}" >selected="selected"</c:if>>信息传输</option>
													<option value="22" <c:if test="${jobInfo.corporationIndustry==22}" >selected="selected"</c:if>>计算机服务和软件业</option>
													<option value="23" <c:if test="${jobInfo.corporationIndustry==23}" >selected="selected"</c:if>>批发零售业</option>
													<option value="24" <c:if test="${jobInfo.corporationIndustry==24}" >selected="selected"</c:if>>金融业</option>
													<option value="25" <c:if test="${jobInfo.corporationIndustry==25}" >selected="selected"</c:if>>房地产业</option>
													<option value="26" <c:if test="${jobInfo.corporationIndustry==26}" >selected="selected"</c:if>>采矿业</option>
													<option value="27" <c:if test="${jobInfo.corporationIndustry==27}" >selected="selected"</c:if>>租赁和商务服务业</option>
													<option value="28" <c:if test="${jobInfo.corporationIndustry==28}" >selected="selected"</c:if>>科学研究</option>
													<option value="29" <c:if test="${jobInfo.corporationIndustry==29}" >selected="selected"</c:if>>技术服务和地址勘查业</option>
													<option value="30" <c:if test="${jobInfo.corporationIndustry==30}" >selected="selected"</c:if>>水利</option>
													<option value="31" <c:if test="${jobInfo.corporationIndustry==31}" >selected="selected"</c:if>>环境和公共设施管理业</option>
													<option value="32" <c:if test="${jobInfo.corporationIndustry==32}" >selected="selected"</c:if>>居民服务和其他服务业</option>
													<option value="33" <c:if test="${jobInfo.corporationIndustry==33}" >selected="selected"</c:if>>教育</option>
													<option value="34" <c:if test="${jobInfo.corporationIndustry==34}" >selected="selected"</c:if>>卫生</option>
													<option value="35" <c:if test="${jobInfo.corporationIndustry==35}" >selected="selected"</c:if>>社会保障和社会福利业</option>
													<option value="36" <c:if test="${jobInfo.corporationIndustry==36}" >selected="selected"</c:if>>文化</option>
													<option value="37" <c:if test="${jobInfo.corporationIndustry==37}" >selected="selected"</c:if>>体育和娱乐业</option>
													<option value="38" <c:if test="${jobInfo.corporationIndustry==38}" >selected="selected"</c:if>>公共管理和社会组织</option>
													<option value="39" <c:if test="${jobInfo.corporationIndustry==39}" >selected="selected"</c:if>>国际组织</option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>公司规模
                            </td>
                            <td class="td2">
                                <div class="select1_border">
                                    <div class="select1_con">
                                        <select name="corporationScale" class="select1">
													<option value="40" <c:if test="${jobInfo.corporationScale==40}" >selected="selected"</c:if>>
														10人以下
													</option>
													<option value="41" <c:if test="${jobInfo.corporationScale==41}" >selected="selected"</c:if>>
														10-100人
													</option>
													<option value="42" <c:if test="${jobInfo.corporationScale==42}" >selected="selected"</c:if>>
														100-500人
													</option>
													<option value="43" <c:if test="${jobInfo.corporationScale==43}" >selected="selected"</c:if>>
														500人以上
													</option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>职位
                            </td>
                            <td class="td2">
                                <input type="text" name="position" value="${jobInfo.position}" class="input_text1 chars50 required"/>
                            </td>
                            <td><label class="mvalidatemsg"></label></td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>在现单位工作年限
                            </td>
                            <td class="td2">
                                <div class="select1_border">
                                    <div class="select1_con">
                                        <select id="presentCorporationWorkTime" name="presentCorporationWorkTime"
                                        class="select1">
                                            <option value="72" <c:if test="${jobInfo.presentCorporationWorkTime==72}">selected="selected"</c:if>> 1年以内</option>
                                            <option value="73" <c:if test="${jobInfo.presentCorporationWorkTime==73}">selected="selected"</c:if>> 1到3年</option>
                                            <option value="74" <c:if test="${jobInfo.presentCorporationWorkTime==74}">selected="selected"</c:if>> 3到5年</option>
                                            <option value="75" <c:if test="${jobInfo.presentCorporationWorkTime==75}">selected="selected" </c:if>>5到10年</option>
                                            <option value="76" <c:if test="${jobInfo.presentCorporationWorkTime==76}">selected="selected"</c:if> >10年以上</option>
                                        </select>
                                    </div>
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>工作电话
                            </td>
                            <td class="td2">
                                <input type="text" name="corporationPhoneArea" value="${jobInfo.corporationPhoneArea}" class="input_text1 wd_r1 phone1 required" />
                                -
                                <input type="text" name="corporationPhone" value="${jobInfo.corporationPhone}" class="input_text1 wd_r2 phone2 required" />
                            </td>
                            <td><label class="mvalidatemsg"></label></td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>工作邮箱
                            </td>
                            <td class="td2">
                                <input type="text" name="jobEmail" value="${jobInfo.jobEmail}" class="input_text1 email required" />
                            </td>
                            <td><label class="mvalidatemsg"></label></td>
                        </tr>
                        <tr>
                            <td class="td1">
                                <span class="col_r4">*</span>公司地址
                            </td>
                            <td class="td2">
                                <input type="text" name="corporationAddress" value="${jobInfo.corporationAddress}" class="input_text1 chars100 required" />
                            </td>
                            <td><label class="mvalidatemsg"></label></td>
                        </tr>
                        <tr>
                            <td colspan="2" class="td3">
                                <span class="sp1 fr">
                                    <span class="col_r4">*</span>每张图片最大限制为1.5MB，图片格式为JPG,GIF,PNG&nbsp;
                                </span>
                                <div class="fr">
                                    <jsp:include page="../../../fileUpload.jsp" />
                                </div>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                        <tr>
                            <td colspan="2">
                                <input type="button" onclick="closeWork()" class="btn_r1 fr" value="取消" style="margin-left:10px;"/>
                                <input type="button" onclick="submitAuditWork()" class="btn_r1 fr" value="提交审核"/>
                            </td>
                            <td>
                                &nbsp;
                            </td>
                        </tr>
                    </c:otherwise>
                </c:choose>
            </table>
        </form>
    </div>
</div>