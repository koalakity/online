function popMask(imgSrc){
	var abc = $("<div class='m-overlay'></div><div class='m-outer'><br/><img src="+imgSrc+" height='15' /><br/>处理中......</div>");
	var lay_h = $(window).height();
	var d_scr_h = $(document).height();
	var lay_w = $(window).width();
	var scr_h = $(document).scrollTop();
	abc.appendTo("body");
	var outer_h = $(".m-outer").height();
	var outer_w = $(".m-outer").width();
	$(".m-overlay").height(d_scr_h);
	$(".m-outer").css({"top":(lay_h)/2-outer_h/2+scr_h,"left":lay_w/2-outer_w/2});
}

function cancleMask(){
	$(".m-overlay, .m-outer").remove();
}