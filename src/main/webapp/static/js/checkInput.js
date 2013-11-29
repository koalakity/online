function getStrActualLen(sChars)
{
    return sChars.replace(/[^\x00-\xff]/g,"xx").length;
};
function checkInput(obj){
	var m = true;
	var m0 = obj.parents("form");
	var m16 = true;
	var required = m0.find(".required");
	var required_db1 = m0.find(".required_db1");
	var required_db2 = m0.find(".required_db2");
	var phone = m0.find(".phone");
	var identity= m0.find(".identity");
	var money= m0.find(".money");
	var email= m0.find(".email");
	var postal= m0.find(".postal");
	var education= m0.find(".education");
	var name= m0.find(".name");
	var file= m0.find(".file");
	var num= m0.find(".num");
	var required_db3 = m0.find(".required_db3");
	var required_db4 = m0.find(".required_db4");
	var num2= m0.find(".num2");
	var chars20 = m0.find(".chars20");
	var chars50 = m0.find(".chars50");
	var chars100 = m0.find(".chars100");
	var chars200 = m0.find(".chars200");
	var i;
	//去掉元素前后空格
	m0.find("input[type='text']").each(function(){
		$(this).val($.trim($(this).val()));
	});
	//不能为空
	for(i = 0;i < required.length; i++){
		var m1 = required.eq(i);
		m1.nextAll("label").remove();
		if(m1.val()== ""){
			m1.siblings().andSelf().last().siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;必填项，不能为空</label>");
			m = false;
		};
	};
	//电话号码验证
	for(i = 0;i < required_db2.length; i++){
		var m2 = required_db1.eq(i);
		var m3 = required_db2.eq(i);
		m3.nextAll("label").remove();
		if(m2.val()=="" && m3.val()==""){
			m3.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;必填项，不能为空</label>");
			m = false;
		}else if(!/^0\d{2,3}$/.test(m2.val()) || !/^\d{7,8}$/.test(m3.val())){
			m3.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;电话号码输入不正确</label>");
			m = false;
		};
	};
	//手机号码验证
	for(i = 0;i < phone.length; i++){
		var m4 = phone.eq(i);
		if(m4.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m4.nextAll("label").remove();
		};
		if(m4.val()!="" && !/^\d{11}$/.test(m4.val())){
			m4.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;手机号码输入不正确</label>");
			m = false;	
		};
	};
	//身份证号码验证
	for(i = 0;i < identity.length; i++){
		var m5 = identity.eq(i);
		if(m5.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m5.nextAll("label").remove();
		};
		if(m5.val()!="" && !/^(\d{15}|(\d{17}(\d|x|X)))$/.test(m5.val())){
			m5.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;身份证号码输入不正确</label>");
			m = false;	
		};
	};
	//金额数验证
	for(i = 0;i < money.length; i++){
		var m6 = money.eq(i);
		if(m6.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m6.nextAll("label").remove();
		};
		if(m6.val().length>20){
			m6.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过20个！</label>");
			m = false;
		}else if(m6.val()!="" && !/^(([1-9]\d*)|([1-9]\d{0,2}(\,\d{3})*))(\.\d{1,2})?$/.test(m6.val())){
			if(/^0(\.\d{1,2})?$/.test(m6.val())){
				break;
			};
			if(/(\.\d{3,})$/.test(m6.val())){
				m6.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;金额最多保留小数点后两位</label>");
			}else{
				m6.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;金额输入不正确</label>");
			};
			m = false;	
		};
	};
	//邮箱验证
	for(i = 0;i < email.length; i++){
		var m7 = email.eq(i);
		if(m7.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m7.nextAll("label").remove();
		};
		if(m7.val()!="" && !/^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test(m7.val())){
			m7.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;邮箱输入不正确</label>");
			m = false;	
		};
	};
	//邮编验证
	for(i = 0;i < postal.length; i++){
		var m8 = postal.eq(i);
		if(m8.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m8.nextAll("label").remove();
		};
		if(m8.val()!="" && !/^\d{6}$/.test(m8.val())){
			m8.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;邮政编码输入不正确</label>");
			m = false;	
		};
	};
	//学历12位在线验证码验证
	for(i = 0;i < education.length; i++){
		var m9 = education.eq(i);
		if(m9.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m9.nextAll("label").remove();
		};
		if(m9.val()!="" && !/^\d{12}$/.test(m9.val())){
			m9.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;12位在线验证码输入不正确</label>");
			m = false;	
		};
	};
	//姓名验证
	for(i = 0;i < name.length; i++){
		var m10 = name.eq(i);
		if(m10.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m10.nextAll("label").remove();
		};
		if(m10.val()!="" && !/^[\u4E00-\u9FA5•·]{1,10}$/.test(m10.val())){
			m10.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;姓名输入不正确</label>");
			m = false;	
		};
	};
	//上传附件验证
	for(i = 0;i < file.length; i++){
		var m11 = file.eq(i);
		if(m11.val()==""){
			m11.parents(".upimg").next("p").find("a").next("label").remove().end().after("<label class='col1 font_12'>&nbsp;&nbsp;至少上传一个附件(且请把为空的上传删除)</label>");
			m = false;
			m16 = false;
		}else if(!/(.jpg|.png|.gif|.jpeg|.jpe|.bmp|.JPG|.PNG|.GIF|.JPEG|.BMP)$/.test(m11.val())){
			m11.parents(".upimg").next("p").find("a").next("label").remove().end().after("<label class='col1 font_12'>&nbsp;&nbsp;上传的图片格式错误(支持jpg、png、gif、bmp)</label>");
			m = false;
			m16 = false;
		};
	};
	if(m16 == true){
		$(".upimg").next("p").find("a").next("label").remove();
	}
	//数字验证
	for(i = 0;i < num.length; i++){
		var m12 = num.eq(i);
		if(m12.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m12.nextAll("label").remove();
		};
		if(m12.val().length>20){
			m12.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过20个！</label>");
			m = false;
		}else if(m12.val()!="" && !/^[1-9]\d*(\.\d)?$/.test(m12.val())){
			m12.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;请输入正确的数字</label>");
			m = false;	
		};
	};
	//电话号码验证2
	for(i = 0;i < required_db4.length; i++){
		var m13 = required_db3.eq(i);
		var m14 = required_db4.eq(i);
		m14.nextAll("label").remove();
		if( (m13.val()!="" || m14.val()!="") && (!/^0\d{2,3}$/.test(m13.val()) || !/^\d{7,8}$/.test(m14.val()))){
			m14.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;电话号码输入不正确</label>");
			m = false;
		};
	};	
	//数字验证2
	for(i = 0;i < num2.length; i++){
		var m15 = num2.eq(i);
		if(m15.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m15.nextAll("label").remove();
		};
		if(m15.val().length>20){
			m15.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过20个！</label>");
			m = false;
		}else if(m15.val()!="" && !/^[1-9]\d*$/.test(m15.val())){
			m15.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;请输入正确的数字</label>");
			m = false;	
		};
	};
	//字符长度50
	for(i = 0;i < chars50.length; i++){
		var m16 = chars50.eq(i);
		if(m16.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m16.nextAll("label").remove();
		};
		if(m16.val()!="" && getStrActualLen(m16.val())>50){
			m16.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过50个(25个汉字)</label>");
			m = false;	
		};
	};
	//字符长度100
	for(i = 0;i < chars100.length; i++){
		var m17 = chars100.eq(i);
		if(m17.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m17.nextAll("label").remove();
		};
		if(m17.val()!="" && getStrActualLen(m17.val())>100){
			m17.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过100个(50个汉字)</label>");
			m = false;	
		};
	};
	//字符长度200
	for(i = 0;i < chars200.length; i++){
		var m18 = chars200.eq(i);
		if(m18.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m18.nextAll("label").remove();
		};
		if(m18.val()!="" && getStrActualLen(m18.val())>200){
			m18.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过200个(100个汉字)</label>");
			m = false;	
		};
	};
	//字符长度20
	for(i = 0;i < chars20.length; i++){
		var m19 = chars20.eq(i);
		if(m19.nextAll("label").html()!="&nbsp;&nbsp;必填项，不能为空"){
			m19.nextAll("label").remove();
		};
		if(m19.val()!="" && getStrActualLen(m19.val())>20){
			m19.siblings().andSelf().last().after("<label class='col1 font_12'>&nbsp;&nbsp;字符数不超过20个(10个汉字)</label>");
			m = false;	
		};
	};
	return m;
};