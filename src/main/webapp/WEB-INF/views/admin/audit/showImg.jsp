<%@page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String basePath = "";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<title>图片浏览</title>
<link rel="stylesheet" type="text/css" href="${ctx}/static/temp/admin_demo/css/easyui.css">
<link rel="stylesheet" type="text/css" href="${ctx}/static/temp/admin_demo/css/icon.css">
<link rel="stylesheet" href="${ctx}/static/temp/admin_demo/css/m_style.css" type="text/css">
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/jquery-1.8.0.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/jquery.cookie.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/jquery.easyui.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/easyui-lang-zh_CN.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/m_common.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/m_fuc.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/showImg/CJL.0.1.min.js" charset="utf-8"></script>
<script type="text/javascript" src="${ctx}/static/temp/admin_demo/js/showImg/ImageTrans.js" charset="utf-8"></script>
<script type="text/javascript" charset="utf-8">
$(function(){	
	var container = $$("idContainer"),
		options = {
			onPreLoad: function(){container.style.backgroundImage = "url('${ctx}/static/temp/admin_demo/js/showImg/loading.gif')";},
			onLoad: function(){ container.style.backgroundImage = "";},
			mouseRotate: false
		},
		it = new ImageTrans( container, options );
	//左旋转
	$$("idLeft").onclick = function(){ it.left(); };
	//右旋转
	$$("idRight").onclick = function(){ it.right(); };
	//上一张
	$$("idPrev").onclick = function(){
		$(".tree-node").each(function(){
			var prevli = $(this).parent("li").prev("li");
			if($(this).attr("node-id")==$("#idContainer").find("img").attr("img-id") && prevli.length>0){
				prevli.children(".tree-node").click();
			};
		});
	};
	//下一张
	$$("idNext").onclick = function(){
		var treenode = $(".tree-node");
		for(var i=treenode.length;i>0;i--){
			var nextli = treenode.eq(i-1).parent("li").next("li");
			if(treenode.eq(i-1).attr("node-id")==$("#idContainer").find("img").attr("img-id") && nextli.length>0){
				nextli.children(".tree-node").click();
			};
		};
	};
	//禁止跳转
	$('a').click(function(){
		return false;
	});
	   
	  function loadSuccess(){
	     var nodeTree=$('#showTree').tree('getRoot');
	     if(nodeTree!=null&&${flag}==true){
	     it.load("<%=basePath%>/pic1/${email}/${typeName}/${imgPath}"); //初始图片路径(src)
			$("#idContainer").css({"overflow":"scroll"}).find("img").attr("img-id","${imgPath}");//初始图片id(id)
			$('.tree-node').each(function(){
				if($(this).attr("node-id")=="${imgPath}"){
					$(this).addClass("tree-node-selected"); //初始图片样式(id)
				};
			});
	     }
			
		} 
	$('#showTree').tree({
		url : '${ctx}/admin/audit/showImgJson?userId=${userId}&proId=${proId}&imgPath=${imgPath}&email=${email}',
		onClick : function(node){
			if(node.attributes && node.attributes.src){
				it.load(node.attributes.src);
				$("#idContainer").find("img").attr("img-id",node.id)
			};
		},
		onLoadSuccess :loadSuccess
	});
	//图片拖动
	var img  = $("#idContainer").find("img");
	var isClick=false; //记录鼠标是否按下
	var defaultX; //按下鼠标时候的坐标
	var defaultY; //移动的时候的坐标
	var mouseX;	//移动的时候的X坐标
	var mouseY;	//移动的时候的Y坐标
	var imgTop;  //IMG离上边的距离
	var imgLeft; //IMG离左边的距离
	$("#idContainer").mousedown(function(e){ //鼠标按下
		isClick=true; 
		defaultX=e.pageX;
		defaultY=e.pageY;
		imgTop=parseFloat(img.css("top"));
		imgLeft=parseFloat(img.css("left"));
		return false;
    }).mousemove(function(e) { //鼠标移动
		mouseX=e.pageX;
		mouseY=e.pageY;
		if(isClick) { 
			var newTop=parseFloat(mouseY-defaultY);
			var newLeft=parseFloat(mouseX-defaultX);
			img.css({"top":newTop+imgTop});
			img.css({"left":newLeft+imgLeft});
		};
		return false;
	}).mouseup(function(){ //鼠标松开
		isClick=false; 
	}).mouseleave(function(){ //鼠标离开
		isClick=false; 
	});
});
</script>
</head>
<body>
	<div class="easyui-panel" title="附件浏览" fit="true">
		<div class="easyui-layout" fit="true">
			<div data-options="region:'west'" style="width:200px;">
				<ul id="showTree"></ul>
			</div>
			<div data-options="region:'center'">
				<div id="idContainer" style="height:100%; width:100%"></div>
			</div>
			<div data-options="region:'south',border:false" style="height:40px; line-height:40px; text-align:center;">
				<a id="idLeft" href="#" class="easyui-linkbutton">左旋转</a>
				<a id="idRight" href="#" class="easyui-linkbutton">右旋转</a>
				<a id="idPrev" href="#" class="easyui-linkbutton">上一张</a>
				<a id="idNext" href="#" class="easyui-linkbutton">下一张</a>
			</div>
		</div>
	</div>
</div>
</body>
</html>
