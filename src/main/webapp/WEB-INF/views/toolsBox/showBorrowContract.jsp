<%@ page contentType="text/html;charset=utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" /> 
<title>证大e贷 微金融服务平台-借款协议-${borrowContractVO.loanUserRealName}</title>
<style  type="text/css">
* { margin:0; padding:0;}
html { background:#999999;}
body {width:730px; margin:15px auto; padding:25px; background:#fff; font-size:12px; line-height:1.5em;}
table { border-collapse:collapse;}
table tr td { border:1px solid #ccc; padding:3px 8px;}
</style>
</head>

<body>
<p style="text-align:right;">编号：${borrowContractVO.loanId}</p>
<br />
<h3 style="font-size:16px; text-align:center;">借款协议</h3>
<br />
<p style="font-weight:bold;">甲方（出借人）：</p>
<table style="width:95%; text-align:center;">
		<tr>
			<td>
				证大e贷用户名
			</td>
			<td>
				借出金额
			</td>
			<td>
				借款期限
			</td>
			<td>
				每月应收本息
			</td>
		</tr>

		<c:forEach items="${borrowContractVO.investList}" var="invest">
						<tr>
							<td style="color:#0000ee;">
								${invest.investUserLoginName }
							</td>
							<td style="color:#FF0000;">
								${invest.ivestAmount }
							</td>
							<td>
								${borrowContractVO.loanDuration }
							</td>
							<td style="color:#FF0000;">
								${invest.monthPrincipalInterest }
							</td>
						</tr>
			</c:forEach>
	</table>
<br />
<p style="font-weight:bold;">乙方（借款人）：${borrowContractVO.loanUserRealName}</p>
<p style="font-weight:bold;">证大e贷用户名：${borrowContractVO.loanUserLoginName}</p>
<br />
<p style="font-weight:bold;">丙方（信息提供方）：上海证大投资咨询有限公司</p>
<p style="font-weight:bold;">联系方式：edaiservice@zendaimoney.com</p>
<br />
<p style="font-weight:bold;">鉴于：</p>
<p>1、丙方是一家在上海浦东区合法成立并有效存续的有限责任公司，拥有www.onlinecredit.cn网站（以下简称“该网站”）的经营权，提供信用咨询，为交易提供信息服务；</p>
<p>2、乙方已在该网站注册，并承诺其提供给丙方的信息是完全真实的；</p>
<p>3、甲方承诺对本协议涉及的借款具有完全的支配能力，是其自有闲散资金，为其合法所得；并承诺其提供给丙方的信息是完全真实的；</p>
<p>4、乙方有借款需求，甲方亦同意借款，双方有意成立借贷关系。</p>
<br />
<p style="font-weight:bold;">各方经协商一致，于${borrowContractVO.loanPross}签订如下协议，共同遵照履行：</p>
<br />
<p style="font-weight:bold;">第一条借款基本信息</p>
<table style="width:95%;">
		<tr>
			<td style="width:110px;">
				借款详细用途
			</td>
			<td>
				${borrowContractVO.laonUse}
			</td>
			
		</tr>
		<tr>
			<td>
				借款本金数额
			</td>
			<td style="color:#FF0000;">
				${borrowContractVO.loanAmount}
			</td>
			
		</tr>
		<tr>
			<td>
				月偿还本息数额
			</td>
			<td style="color:#FF0000;">
				${borrowContractVO.monthRepayAmount}
			</td>
			
		</tr>
		<tr>
			<td>
				还款分期月数
			</td>
			<td>
				${borrowContractVO.loanDuration}
			</td>
			
		</tr>
		<tr>
			<td>
				还款日
			</td>
			<td>
				${borrowContractVO.monthRepayTime}
			</td>
			
		</tr>
		<tr>
			<td>
				还款起止日期
			</td>
			<td>
				${borrowContractVO.repayTimeStartAndEnd}
			</td>
			
		</tr>
</table>
<br />
<p style="font-weight:bold;">第二条各方权利和义务</p>
<p style="font-weight:bold;">甲方的权利和义务</p>
<p>1、甲方应按合同约定的借款日将足额的借款本金支付给乙方。</p>
<p>2、甲方享有其所出借款项所带来的利息收益。</p>
<p>3、如乙方违约，甲方有权要求丙方提供其已获得的乙方信息。</p>
<p>4、无须通知乙方，甲方可以根据自己的意愿进行本协议下其对乙方债权的转让。在甲方的债权转让后，乙方需对债权受让人继续履行本协议下其对甲方的还款义务，不得以未接到债权转让通知为由拒绝履行还款义务。</p>
<p>5、甲方应主动缴纳由利息所得带来的可能的税费。</p>
<p>6、如乙方还款不足约定本金、利息及违约金的，甲方同意各自按照其借出款项比例收取还款。</p>
<p style="font-weight:bold;">乙方权利和义务</p>
<p>1、乙方必须按期足额向甲方偿还本金和利息。</p>
<p>2、乙方必须按期足额向丙方支付借款管理费用。</p>
<p>3、乙方承诺所借款项不用于任何违法用途。</p>
<p>4、乙方应确保其提供的信息和资料的真实性，不得提供虚假信息或隐瞒重要事实。</p>
<p>5、乙方有权了解其在丙方的信用评审进度及结果。</p>
<p>6、乙方不得将本协议项下的任何权利义务转让给任何其他方。</p>
<p style="font-weight:bold;">丙方的权利和义务</p>
<p>1、甲乙双方同意丙方有权代甲方每期收取甲方出借款项所对应的乙方每期偿还的本息，代收后按照甲方的要求进行处置。</p>
<p>2、甲方同意向乙方出借相应款项时，已委托丙方在本协议生效时将该笔借款直接划付至乙方账户。</p>
<p>3、甲乙双方同意丙方有权代甲方在有必要时对乙方进行贷款的违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。甲方在此确认明确委托丙 方为其进行以上工作，并授权丙方可以将此工作委托给其他方进行。乙方对前述委托的提醒、催收事项已明确知晓并应积极配合。</p>
<p>4、丙方有权按月向乙方收取双方约定的借款管理费，并在有必要时对乙方进行违约提醒及催收工作，包括但不限于电话通知、发律师函、对乙方提起诉讼等。丙方有权将此违约提醒及催收工作委托给其他方进行。</p>
<p>5、丙方接受甲乙双方的委托行为所产生的法律后果由相应委托方承担。如因乙方或甲方或其他方（包括但不限于技术问题）造成的延误或错误，丙方不承担任何责任。</p>
<p>6、丙方应对甲方和乙方的信息及本协议内容保密；如任何一方违约，或因相关权力部门要求（包括但不限于法院、仲裁机构、金融监管机构等），丙方有权披露。</p>
<br />
<p style="font-weight:bold;">第三条借款管理费及居间服务费</p>
<p>1、在本协议中，“借款管理费”和“居间服务费”是指因丙方为乙方提供信用咨询、评估、还款提醒、账户管理、还款特殊情况沟通等系列信用相关服务而由乙方支付给丙方的报酬。</p>
<p>2、对于丙方向乙方提供的一系列信用服务，乙方同意在借款成功时向丙方支付本次借款本金的${borrowContractVO.loan}(人民币)作为居间服务费，此笔费用乙方委托丙方在借款成功时从 借款本金中扣除缴纳。在还款期间，乙方每月向丙方支付借款本金的${borrowContractVO.mgmtFee}，作为借款管理费用，共需支付${borrowContractVO.duration}期，借款管理费的缴纳时间与乙方向甲方支付本息 的还款日一致。</p>
<p>3、如乙方和丙方协商一致调整借款管理费时，无需经过甲方同意。</p>
<br />
<p style="font-weight:bold;">第四条违约责任</p>
<p>1、合同各方均应严格履行合同义务，非经各方协商一致或依照本协议约定，任何一方不得解除本协议。</p>
<p>2、任何一方违约，违约方应承担因违约使得其他各方产生的费用和损失，包括但不限于调查、诉讼费、律师费等，应由违约方承担。如违约方为乙方的，甲方有权立即 解除本协议，并要求乙方立即偿还未偿还的本金、利息、罚息、违约金。此时，乙方还应向丙方支付所有应付的借款管理费。如本协议提前解除时，乙方在网站的账 户里有任何余款，丙方有权按照本协议第四条第3项的清偿顺序将乙方的余款用于清偿，并要求乙方支付因此产生的相关费用。</p>
<p>3、乙方的每期还款均应按照如下顺序清偿：</p>
<p>（1）根据本协议产生的其他全部费用；</p>
<p>（2）罚息；</p>
<p>（3）逾期管理费；</p>
<p>（4）拖欠的利息；</p>
<p>（5）拖欠的本金；</p>
<p>（6）拖欠丙方的借款管理费；</p>
<p>（7）正常的利息；</p>
<p>（8）正常的本金；</p>
<p>（9）丙方的借款管理费；</p>
<p>4、乙方应严格履行还款义务，如乙方逾期还款，则应按照下述条款向甲方支付逾期罚息，自逾期开始之后，逾期本金的正常利息停止计算。</p>
<p>罚息总额 = 剩余未还期数×每期应还本息×天数×比例；</p>
<table>
	<tr>
			<td style="width:100px;">
				逾期天数
			</td>
			<td style="width:100px;">
				1—30天
			</td>
			
			<td style="width:100px;">
				31天及以上
			</td>
	</tr>
	<tr>
			<td>
				罚息利率
			</td>
			<td>
			${borrowContractVO.overdueInterest}
			</td>
			
			<td>
			${borrowContractVO.overdueSeriouInterest}
			</td>
			
	</tr>
</table>

<p>5、乙方应严格履行还款义务，如乙方逾期还款，则应按照下述条款向丙方支付逾期管理费：逾期管理费总额 = 剩余未还期数×每期管理费×天数×比例；</p>
<table>
	<tr>
			<td style="width:100px;">
				逾期管理费费率 
			</td>
			<td style="width:100px;">
			${borrowContractVO.overdueFines}
			</td>
	</tr>
</table>
<p>6、如果乙方逾期支付任何一期还款超过30天，或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，本协议项下的全部借款本息及借款管理费提前到期，乙方应立即清偿本协议下尚未偿付的全部本金、利息、罚息、借款管理费及根据本协议产生的其他全部费用。</p>
<p>7、如果乙方逾期支付任何一期还款超过30天，或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丙方有权将乙方的“逾期记录”记入公民征信系统，丙方不承担任何法律责任。</p>
<p>8、如果乙方逾期支付任何一期还款超过30天，或乙方在逾期后出现逃避、拒绝沟通或拒绝承认欠款事实等恶意行为，丙方有权将乙方违约失信的相关信息及乙方其他信息向媒体、用人单位、公安机关、检查机关、法律机关披露，丙方不承担任何责任。</p>
<p>9、在乙方还清全部本金、利息、借款管理费、罚息、逾期管理费之前，罚息及逾期管理费的计算不停止。</p>
<p>10、本借款协议中的所有甲方与乙方之间的借款均是相互独立的，一旦乙方逾期未归还借款本息，任何一个甲方有权单独向乙方追索或者提起诉讼。如乙方逾期支付借款管理费或提供虚假信息的，丙方亦可单独向乙方追索或者提起诉讼。</p>
<br />
<p style="font-weight:bold;">第五条提前还款</p>
<p>1、乙方可在借款期间任何时候提前偿还剩余借款。</p>
<p>2、提前偿还全部剩余借款</p>
<p>1）乙方提前清偿全部剩余借款时，应向甲方支付当期应还本息，剩余本金及提前还款补偿（补偿金额为剩余本金的${borrowContractVO.earlyFines}）。</p>
<p>2）乙方提前清偿全部剩余借款时，应向丙方支付当期借款管理费，乙方无需支付剩余还款期的借款管理费。</p>
<p>3、提前偿还部分借款</p>
<p>1）乙方提前偿还部分借款，仍应向甲方支付全部借款利息。</p>
<p>2）乙方提前偿还部分借款，仍应向丙方支付全部应付的借款管理费。</p>
<p>4、任何形式的提前还款不影响丙方向乙方收取在本协议第三条中说明的居间服务费。</p>
<br />
<p style="font-weight:bold;">第六条法律及争议解决</p>
<p>本协议的签订、履行、终止、解释均适用中华人民共和国法律，并由丙方所在地法院管辖。</p>
<br />
<p style="font-weight:bold;">第七条附则</p>
<p>1、本协议采用电子文本形式制成，并永久保存在丙方为此设立的专用服务器上备查，各方均认可该形式的协议效力。</p>
<p>2、本协议自文本最终生成之日生效。</p>
<p>3、本协议签订之日起至借款全部清偿之日止，乙方或甲方有义务在下列信息变更三日内提供更新后的信息给丙方：本人、本人的家庭联系人及紧急联系人、工作单位、 居住地址、住所电话、手机号码、电子邮箱、银行账户的变更。若因任何一方不及时提供上述变更信息而带来的损失或额外费用应由该方承担。</p>
<p>4、如果本协议中的任何一条或多条违反适用的法律法规，则该条将被视为无效，但该无效条款并不影响本协议其他条款的效力。</p>
<br />
<p style="text-align:right;">${borrowContractVO.loanPross}</p>
</body>
</html>