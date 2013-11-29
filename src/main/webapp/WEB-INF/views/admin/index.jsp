<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<title>index</title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<%@ include file="adminHeader.jsp"%>
<script>
function open1(url){
	$('#cc').attr('src',url);
}
</script>
</head>
<body class="easyui-layout">
	<div region="north" border="false" style="height:80px;background:#e4edfe;padding:20px">
		<img src="${ctx}/static/admin/css/images/logo.jpg" style="float:left;" />
		<a href="${ctx}/admin/logout" style="float:right; height:16px; line-height:16px; background:url(${ctx}/static/admin/css/images/m3.gif) no-repeat; padding-left:20px;">退出</a><a href="#" onclick="open1('${ctx}/admin/profileJsp')" style="float:right; height:16px; line-height:16px; width:65px; background:url(${ctx}/static/admin/css/images/m2.gif) no-repeat; padding-left:20px;">个人设置</a><a href="${ctx}" style="float:right; height:16px; line-height:16px; width:40px; background:url(${ctx}/static/admin/css/images/m1.gif) no-repeat; padding-left:20px;">前台</a><br /><br />
		<span style="float:right;">欢迎您，<sec:authentication property="principal.loginName"/></span>
	</div>
	<div region="west" split="true" title="菜单" style="width:180px;padding:5px;">
		<ul class="easyui-tree" animate="true">
		  <sec:authorize access="hasAuthority('01000000')">
			<li state="open">
					<span>会员管理</span>
				<ul>
				  <sec:authorize access="hasAuthority('01010000')">
					<li state="open">
							<span>会员管理</span>
						
						<ul>
							<sec:authorize access="hasAuthority('01010100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/user/userPageJsp')">会员查询</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01010200')">
							<li><span><a href="#" onclick="open1('${ctx}/admin/user/enteredAuditUserPageJsp')">已进入审核</a></span></li>
							</sec:authorize>
						</ul>
					 </li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('01020000')">
					  <li state="closed">
							<span>资料审核</span>
						<ul>
							<sec:authorize access="hasAuthority('01020100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/audit/waitForAuditingUserPageJsp')">待审列表</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01020200')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/audit/firstForAuditingUserPageJsp')">初定列表</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01020300')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/audit/finalForAuditingUserPageJsp')">终定列表</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01020400')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/audit/refuseForAuditingUserPageJsp')">驳回列表</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01020500')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/audit/supplyDataPageJsp')">补充资料</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('01020600')">	
							    <li><span><a href="#" onclick="open1('${ctx}/admin/audit/picShowUserJsp')">图片查看</a></span></li>
							 </sec:authorize>
						</ul>
					  </li>
					</sec:authorize>
				</ul>
			</li>
			</sec:authorize>	
			<sec:authorize access="hasAuthority('02000000')">
			<li state="closed">
					<span>借款管理</span>
				<ul>
					<sec:authorize access="hasAuthority('02000100')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/waitingForAuditloanPageJsp')">待审核借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000200')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanTenderingPageJsp')">招标中借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000300')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanTenderOverPageJsp')">已满标借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000400')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanRepayingPageJsp')">还款中借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000500')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanRepayOverPageJsp')">已还清借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000600')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanAbortiveTenderPageJsp')">流标借款列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000700')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanJuniorHastenPageJsp')">初级催收列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('02000800')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/loan/loanSeniorHastenPageJsp')">高级催收列表</a></span></li>
					</sec:authorize>
				</ul>
			 </li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('03000000')">
			 <li state="closed">
					<span>提现管理</span>
				<ul>
					<sec:authorize access="hasAuthority('03000100')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/extract/extractCashRecordPageJsp?verifyStatus=0')">新申请提现列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('03000200')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/extract/extractCashRecordPageJsp?verifyStatus=1')">处理中提现列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('03000300')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/extract/extractCashRecordPageJsp?verifyStatus=2')">成功提现列表</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('03000400')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/extract/extractCashRecordPageJsp?verifyStatus=3')">失败提现列表</a></span></li>
					</sec:authorize>
				</ul>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('04000000')">
			<li state="closed">
					<span>资金管理</span>
				<ul>
					<sec:authorize access="hasAuthority('04000100')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/ledger/siteMoneyJsp')">网站资金账户</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('04000200')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/ledger/riskMoneyJsp')">风险金资金账户</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('04000300')">
					<li><span><a href="#" onclick="open1('${ctx}/admin/fundsMigrate/')">历史资金迁移</a></span></li>
					</sec:authorize>
				</ul>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('05000000')">
			  <li state="closed">
					<span>内容管理</span>
				<ul>
					<sec:authorize access="hasAuthority('05000100')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=19')">最新公告</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('05010000')">
					 <li state="closed">
							<span>新闻中心</span>
						<ul>
							<sec:authorize access="hasAuthority('05010100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=20')">行业新闻</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05010200')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=21')">媒体报道</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05010300')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=22')">微金融故事</a></span></li>
							</sec:authorize>
						</ul>
				 	</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('05020000')">
					<li state="closed">
							<span>使用帮助</span>
						<ul>
							<sec:authorize access="hasAuthority('05020100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=1')">常见问题</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05020200')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=2')">平台原理</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05020300')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=3')">政策法规</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05020400')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=4')">如何借款</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05020500')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=5')">如何理财</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05020600')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=6')">使用技巧</a></span></li>
							</sec:authorize>
						</ul>
					</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('05030000')">	
					<li state="closed">
							<span>安全保障</span>
						<ul>
							<sec:authorize access="hasAuthority('05030100')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=7')">风险金代偿</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05030200')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=8')">风险管控</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05030300')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=9')">交易安全保障</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05030400')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=10')">网络安全保障</a></span></li>
							</sec:authorize>	
						</ul>
					</li>
					</sec:authorize>
				    <sec:authorize access="hasAuthority('05040000')">	
					<li state="closed">
							<span>资费说明</span>
						<ul>
							<sec:authorize access="hasAuthority('05040100')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=11')">收费标准</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05040200')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=12')">提现标准</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05040300')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=13')">代理收费</a></span></li>
							</sec:authorize>	
						</ul>
					</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('05050000')">
					<li state="closed">
							<span>关于我们</span>
						<ul>
							<sec:authorize access="hasAuthority('05050100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=14')">公司简介</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05050200')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=15')">专家顾问</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05050300')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=16')">合作伙伴</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05050400')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=17')">联系我们</a></span></li>
							</sec:authorize>	
						</ul>
					</li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('05060000')">
					<li state="closed">
							<span>工具箱</span>
						<ul>
							<sec:authorize access="hasAuthority('05060100')">
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=18')">借款协议范本</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05060200')">	
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=23')">隐私条款</a></span></li>
							</sec:authorize>
							<sec:authorize access="hasAuthority('05060300')">		
								<li><span><a href="#" onclick="open1('${ctx}/admin/site/noticeJsp?type=24')">使用条款</a></span></li>
							</sec:authorize>	
						</ul>
					</li>
					</sec:authorize>
				</ul>
			</li>
				</sec:authorize>
			<sec:authorize access="hasAuthority('06000000')">
			<li state="closed">
			 <span>系统管理</span>
				<ul>
					<sec:authorize access="hasAuthority('06000100')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/staffPageJsp')">账号管理</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('06000200')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/rolePageJsp')">角色管理</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('06000300')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/logPageJsp')">操作日志</a></span></li>
					</sec:authorize>
					<sec:authorize access="hasAuthority('06000400')">	
						<li><span><a href="#" onclick="open1('${ctx}/admin/profileJsp')">个人设置</a></span></li>
					</sec:authorize>	
					</ul>
		 	</li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('07000000')">
			<li state="closed">
			   <span>统计管理</span>
			   <ul>
			   <sec:authorize access="hasAuthority('07010000')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/static/staticAdminJsp')">统计管理</a></span></li>
				</sec:authorize>			
			    </ul>
			</li>
			</sec:authorize>
			
			
			<sec:authorize access="hasAuthority('08000000')">
			<li state="closed">
			   <span>渠道管理</span>
			   <ul>
			   <sec:authorize access="hasAuthority('08010000')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/findById')">渠道管理</a></span></li>
				</sec:authorize>			
			    </ul>
			</li>
			</sec:authorize>
			<sec:authorize access="hasAuthority('09000000')">
			<li state="closed">
			 <span>费率管理</span>
			  <ul>
			   <sec:authorize access="hasAuthority('09010000')">
			    <li><span><a href="#" onclick="open1('${ctx}/admin/rate/rateMaintainJsp')">费率维护</a></span></li>
			    </sec:authorize>
			     <sec:authorize access="hasAuthority('09020000')">
			    <li><span><a href="#" onclick="open1('${ctx}/admin/rate/rateDesginJsp')">渠道费率设定</a></span></li>
			    </sec:authorize>
			  </ul>
			</li>
			</sec:authorize>
			
			
			<sec:authorize access="hasAuthority('10000000')">
				<li state="closed">
				   <span>报表导出</span>
				   <ul>
				   <sec:authorize access="hasAuthority('10010000')">
						<li><span><a href="#" onclick="open1('${ctx}/admin/report/forWord?pagePath=admin/loan/repayFlowDetailRep')">还款明细导出</a></span></li>
					</sec:authorize>			
				    </ul>
				</li>
			</sec:authorize>
			
			
		</ul>
	</div>
	<div region="south" border="false" style="height:50px; background:#e4edfe; line-height:30px; padding:10px;">© 2012证大财富 All rights reserved</div>
	<div region="center" style="padding:10px;">
		<iframe id="cc" frameborder="0" scrolling="auto" style="width:100%;height:100%" src="${ctx}/admin/welcome"></iframe>
	</div>
</body>
</html>