<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>证大e贷 微金融服务平台-风险基金协议-${borrowContractVO.loanUserRealName}</title>
<style  type="text/css">
* { margin:0; padding:0;}
html { background:#999999;}
body {width:730px; margin:15px auto; padding:25px; background:#fff; font-size:12px; line-height:1.5em;}
</style>
</head>

<body>
<p style="text-align:right;">编号：${borrowContractVO.loanId}</p>
<br />
<h3 style="font-size:16px; text-align:center;">借款协议风险基金协议</h3>
<br />
<p>本协议由以下双方于${borrowContractVO.loanPross}签署并履行：</p>
<br />
<p style="font-weight:bold;"><table border="0"><tr><td style="width:40%;font-weight:bold;">甲方（借款人）：${borrowContractVO.loanUserRealName}</td><td style="width:40%;font-weight:bold;">乙方：上海证大投资咨询有限公司</td>  </tr>                                
<tr><td>身份证号码：${borrowContractVO.identifyNo} </td> <td>地址：上海市浦东新区丁香路1208号</td></tr>
<tr><td>现住址：${borrowContractVO.address}</td><td>邮编：200135</td></tr>
<tr><td>电子邮箱：${borrowContractVO.email}</td></tr></table>  </p>
<br />
<br />
<br />
<p>为保障出借人权益，降低出借风险，经平等协商，双方达成如下协议：</p>
<p><span style="font-weight:bold;">第一条</span> 甲方除按照《借款协议》的约定向乙方支付其为实现甲方借款而提供的各项服务费用（“借款管理费”和“服务费”）外，另需支付本次借款本金的  ${borrowContractVO.reserveFund}，共计人民币<span style="color:#FF0000;">${borrowContractVO.fundMoney}</span>元（<span style="color:#FF0000;">￥${borrowContractVO.fundMoney}</span>）作为风险基金，此笔款项由甲方委托乙方从债权人支付的出借款项中直接扣除，并由乙方代债权人管理，转入乙方指定风险基金账户。</p>
<br />
<p style="font-weight:bold;">第二条 风险基金的处置方式</p>
<p>风险基金代偿后，借款人所偿还的逾期款本息划入上述风险基金账户，同时逾期罚息、违约金等相关权益将作为债权人支付给乙方的催收服务费。</p>
<br/>
<p><span style="font-weight:bold;">第三条</span> 乙方应审慎管理风险基金账户。</p>
<br/>
<p style="font-weight:bold;">第四条 保密条款</p>
<p>双方共同确认：本协议所涉及的各项合同交易信息均属于保密信息，各方均有责任对前述所指的保密信息实行保密。</p>
<p>任何一方违反本协议保密规定，则应对给对方造成的实际损失承担赔偿责任。</p>
<br/>
<p style="font-weight:bold;">第五条 违约责任</p>
<p>任何一方违反本协议的约定，导致本协议的全部或部分不能履行，均应承担违约责任，并赔偿对方因此遭受的损失（包括由此产生的诉讼费和律师代理费等其他费用）；如双方均违约，根据实际情况各自承担相应的责任。</p>
<br/>
<p style="font-weight:bold;">第六条 其他</p>
<p>6.1甲乙双方签署本协议后，本协议于文首所载日期成立。自甲方将约定的金额支付到乙方指定账号之日起生效。</p>
<p>6.2本协议的任何修改、补充均须以书面形式作出。</p>
<p>6.3本协议的传真件、复印件等有效复本的效力与本协议原件效力一致。</p>
<p>6.4如果甲乙双方在本协议履行过程中发生任何争议，应友好协商解决；如果协商不成，则须提交本协议乙方所在地人民法院进行诉讼。</p>
<p>6.5本协议一式两份，甲乙双方各保留壹份。</p>
<p>（以下无合同正文）</p>
<br/>
<p style="text-align:left;font-weight:bold;"><table><tr><td style="width:40%;font-weight:bold;">甲方(借款人)：${borrowContractVO.loanUserRealName}</td><td style="width:40%;font-weight:bold;">乙方（盖章）：上海证大投资咨询有限公司</td></tr></table></p>
<p style="text-align:left;font-weight:bold;">证大e贷用户名：${borrowContractVO.loanUserLoginName}</p>
<p style="text-align:right;font-weight:bold;">本协议生成日期：${borrowContractVO.loanPross}</p>
</body>
</html>