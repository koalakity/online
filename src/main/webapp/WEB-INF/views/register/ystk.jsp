<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<link rel="stylesheet" href="${ctx}/static/css/news.css" type="text/css" />
<link rel="stylesheet" href="${ctx}/static/css/account.css" type="text/css" />
<title>隐私条款</title>
<script type="text/javascript">
$(function(){
    $(".nav").find("a").hover(function(){
		var m = $(this).parent().index();
		$(".sub_nav").find("span").eq(m).show().siblings().hide();
	});
	$(".account_l").find("dt").click(function(){
		var m = $(this).siblings("dd");
		var m1 = $(this).find("img");
		if(m.is(":hidden")){
			m1.attr("src","images/account_l_dt_a1.gif");
	        m.slideDown("800");
		}else{
			m1.attr("src","images/account_l_dt_a2.gif");
		    m.slideUp("800");
		};
	});
});
</script>
</head>
<body>
<div class="wrapper">
	<div class="content">
		<div class="account_r_c account_r_c_add">
			<div class="news_t"><a href="index.html">首页</a> > <a href="2级页面列表1_1.html">文字列表</a> > 正文</div>
			<div class="news_c2">
				<h3>温州金改对小微企业的利好</h3>
				<div class="news_detail">
					<p>《浙江省温州市金融综合改革试验区总体方案》（以下简称《方案》）于2012年3月28日获国务院常务会议批准实施，我国民间金融规范化、阳光化发展改革正式启动。</p>
					<p>《方案》鼓励民间资金介入村镇银行等新型金融组织，鼓励商业保理和民营第三方支付机构发展，从政策层面降低了小微金融机构准入门槛，旨在探索民间资金服务实体经济的可行路径。</p>
					<p>《方案》引导民间资金依法设立创业投资企业、股权投资企业及相关投资管理机构，进一步拓宽了民间资金介入小微金融市场的通道。</p>
					<p>设立小企业信贷专营机构，解决小微企业和“三农”融资困境。</p>
					<p>培育发展地方资本市场，拓宽小微企业融资渠道。</p>
					<p>积极发展各类债券产品、推动更多企业、尤其是小微企业通过债券市场融资。</p>
					<p>拓宽保险服务领域、探索保险资金支持地方经济社会发展的有效途径，是缓解小微企业和“三农”融资困境的辅助政策路径。</p>
					<p>小微企业融资难、融资贵问题的重要影响因素之一是其缺乏完整的信用记录，影响金融机构正常的授信评级流程。而在民间金融市场，小微企业往往凭借的是个人信誉。此次温州金融改革特别提出信用体系建设，不但是缓解小微金融市场融资困境的有针对性的制度设计，也是国家优化地方金融生态环境的实质举措。</p>
				</div>
				<p class="pagelist">
				</p>
			</div>
		</div>
	</div>
	<div class="clear"></div>
</div>
</body>
</html>