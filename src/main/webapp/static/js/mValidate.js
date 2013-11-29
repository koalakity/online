/**
 * 表单校验
 * fo   获取表单
 * param1 为true时，绑定焦点进入离开，可为空。
 * param2 为true时，绑定提交按钮，可为空。
 * submit 获取按钮，绑定焦点及提交按钮，可为空。
 */
jQuery.mValidate = function(fo,param1,param2,submit){
	//获取name
	var name = {
		loginname:"loginname",
		realname:"realname",
		chinese:"chinese",
		letter:"letter",
		number4:"number4",
		idcard:"idcard",
		mobile:"mobile",
		rar:"rar",
		qq:"qq",
		picture:"picture",
		zipcode:"zipcode",
		money:"money",
		email:"email",
		education:"education",
		password:"password",
		rpassword:"rpassword",
		chars20:"chars20",
		chars50:"chars50",
		chars100:"chars100",
		phone1:"phone1",
		phone2:"phone2"
	};
	//提示语句
	var message = {
		loginname:{
			a:"请输入用户名", // submit or blur: null
			b:"4-20个字符，不可使用中文", // focus: null or success 
			c:"不能使用中文",	//error 1
			d:"字符数控制在4-20"	//error 2
		},
		realname:{
			a:"必填项,不能为空",
			b:"",
			c:"不能使用非中文",
			d:"字数控制在1-10"
		},
		chinese:"必填项",
		letter:"必填项",
		number:{
			a:"必填项,不能为空",
			b:"",
			c:"数字输入有误",
			d:"不能超过4位数"
		},
		idcard:{
			a:"必填项,不能为空",
			b:"",
			c:"身份证号码输入有误"
		},
		mobile:{
			a:"必填项,不能为空",
			b:"",
			c:"手机号码输入有误"
		},
		rar:"必填项",
		qq:"必填项",
		picture:"必填项",
		zipcode:{
			a:"必填项,不能为空",
			b:"",
			c:"邮编输入有误"		
		},
		money:{
			a:"必填项,不能为空",
			b:"请不要输入逗号和空格,正确的格式为123456.78",
			c:"金额输入有误",
			d:"金额不能超过20个位",
			e:"金额最多保留小数点后两位"
		},
		email:{
			a:"必填项,不能为空",
			b:"",
			c:"邮箱输入有误"
		},
		education:{
			a:"必填项,不能为空",
			b:"",
			c:"验证码输入有误"
		},
		password:{
			a:"必填项,不能为空",
			b:"6-16个字符",
			c:"请输入6-16个字符"
		},
		rpassword:{
			a:"必填项,不能为空",
			b:"",
			c:"两次输入密码不一致"
		},
		chars20:{
			a:"必填项,不能为空",
			b:"",
			c:"不能超过20个字符"
		},
		chars50:{
			a:"必填项,不能为空",
			b:"",
			c:"不能超过50个字符"
		},
		chars100:{
			a:"必填项,不能为空",
			b:"",
			c:"不能超过100个字符"
		},
		phone:{
			a1:"区号不能为空",
			a2:"电话号码不能为空",
			b:"区号+电话号码，区号请输入3位或4位数字，电话号码请输入7位或8位数字",
			c:"区号输入有误",
			d:"电话号码输入有误"
		}
	};
	//验证规则
	var rules = {
		required:function(val){ return $.trim(val)!="";},
		loginname:{
			c:function(val){ return /^[^\u4E00-\u9FA5]*$/.test($.trim(val));},
			d:function(val){ return /^[^\u4E00-\u9FA5]{4,20}$/.test($.trim(val));}
		},
		realname:{
			c:function(val){ return /^[\u4E00-\u9FA5•·]*$/.test($.trim(val));},
			d:function(val){ return /^[\u4E00-\u9FA5•·]{2,10}$/.test($.trim(val));}
		},
		chinese:"必填项",
		letter:"必填项",
		number:{
			c:function(val){ return /^[1-9]\d*$/.test($.trim(val));},
			d:function(val){ return val.replace(/[^\x00-\xff]/g,"xx").length <= 4;}
		},
		rar:"必填项",
		qq:"必填项",
		idcard:{
			c:function(val){ return /^(\d{15}|(\d{17}(\d|x|X)))$/.test($.trim(val));}
		},
		mobile:{
			c:function(val){ return /^0?(13|15|18|14)[0-9]{9}$/.test($.trim(val));}
		},
		picture:"必填项",
		zipcode:{
			c:function(val){ return /^\d{6}$/.test($.trim(val));}
		},
		money:{
			c:function(val){ return /^([1-9]\d*)(\.\d{1,})?$/.test($.trim(val));},
			d:function(val){ return val.replace(/[^\x00-\xff]/g,"xx").length <= 20;},
			e:function(val){ return /^([1-9]\d*)(\.\d{1,2})?$/.test($.trim(val));}
		},
		email:{
			c:function(val){ return /^[\w-]+(\.[\w-]+)*@[\w-]+(\.[\w-]+)+$/.test($.trim(val));}
		},
		education:{
			c:function(val){ return /^\d{12}$/.test($.trim(val));}
		},
		password:{
			c:function(val){ return /^[A-Za-z0-9-_\~\!\@\#\$\%\^\&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]{6,16}$/.test(val);}
		},
		rpassword:{
			c:function(val1,val2){ return val1==val2;}
		},
		chars20:{
			c:function(val){ return val.replace(/[^\x00-\xff]/g,"xx").length <= 20;}
		},
		chars50:{
			c:function(val){ return val.replace(/[^\x00-\xff]/g,"xx").length <= 50;}
		},
		chars100:{
			c:function(val){ return val.replace(/[^\x00-\xff]/g,"xx").length <= 100;}
		},
		phone:{
			c:function(val){ return /^0\d{2,3}$/.test($.trim(val));},
			d:function(val){ return /^\d{7,8}$/.test($.trim(val));}
		}
	};
	//显示
	var showMsg = function(obj,msg,err){
		var msgbox = obj.parent("td").nextAll("td").find(".mvalidatemsg");
		if(err){
			msgbox.addClass("mvalidateerror");
		}
		msgbox.html(msg);
	};
	//隐藏
	var hideMsg = function(obj){
		var msgbox = obj.parent("td").nextAll("td").find(".mvalidatemsg");
		msgbox.html("").removeClass("mvalidateerror");
	};
	//焦点进入离开方法
	var fobl = function(name,msg0_b,msg0_f,msg1,rules1,msg2,rules2,msg3,rules3){
		$("."+name,fo).live("focus blur",function(event){
			var obj = $(this);
			var val = obj.val();
			var val_psd = $(".password",fo).val();
			var ru0 = rules.required(val);
			var ru1,
				ru2,
				ru3;
			if(msg1){
				if(obj.hasClass("rpassword")){
					ru1 = rules1(val,val_psd);
				}else{
					ru1 = rules1(val);
				};
			};
			if(msg2){
				ru2 = rules2(val);			
			};
			if(msg3){
				ru3 = rules3(val);			
			};
			hideMsg(obj);
			if(msg0_b && !ru0 && obj.hasClass("required") && event.type == "blur"){
				showMsg(obj,msg0_b,"error");
				obj.addClass("mvalidatewarn");
			}else if(msg1 && ru0 && !ru1){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg1,"error");
			}else if(msg2 && ru0 && !ru2){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg2,"error");
			}else if(msg3 && ru0 && !ru3){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg3,"error");
			}else{
				obj.removeClass("mvalidatewarn");
				if(msg0_f && event.type == "focus"){
					showMsg(obj,msg0_f);
				}else if(obj.hasClass("phone2") && event.type == "blur"){
					var phone1 = obj.siblings(".phone1");
					var val = phone1.val();
					if(message.phone.a1 && phone1.hasClass("required") && !rules.required(val)){
						showMsg(obj,message.phone.a1,"error");
						phone1.addClass("mvalidatewarn");
					}else if(message.phone.c && rules.required(phone1) && !rules.phone.c(val)){
						showMsg(obj,message.phone.c,"error");
						phone1.addClass("mvalidatewarn");
					};					
				}else if(obj.hasClass("phone1") && event.type == "blur"){
					var phone2 = obj.siblings(".phone2");
					var val = phone2.val();
					if(message.phone.a2 && phone2.hasClass("required") && !rules.required(val)){
						showMsg(obj,message.phone.a2,"error");
						phone2.addClass("mvalidatewarn");
					}else if(message.phone.d && rules.required(phone2) && !rules.phone.d(val)){
						showMsg(obj,message.phone.d,"error");
						phone2.addClass("mvalidatewarn");
					};					
				};
			};
		});
	};
	//表单提交按钮临时变量
	var flag;
	//表单提交方法
	var sumt = function(name,msg0,msg1,rules1,msg2,rules2,msg3,rules3){
		$("."+name,fo).each(function(){
			var obj = $(this);
			var val = obj.val();
			var val_psd = $(".password",fo).val();
			var ru0 = rules.required(val);
			var ru1,
				ru2,
				ru3;
			if(msg1){
				if(obj.hasClass("rpassword")){
					ru1 = rules1(val,val_psd);
				}else{
					ru1 = rules1(val);
				};
			};
			if(msg2){
				ru2 = rules2(val);			
			};
			if(msg3){
				ru3 = rules3(val);			
			};
			if(msg0 && !ru0 && obj.hasClass("required")){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg0,"error");
				flag = false;			
			}else if(msg1 && ru0 && !ru1){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg1,"error");
				flag = false;
			}else if(msg2 && ru0 && !ru2){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg2,"error");
				flag = false;
			}else if(msg3 && ru0 && !ru3){
				obj.addClass("mvalidatewarn");
				showMsg(obj,msg3,"error");
				flag = false;
			};
		});
	};
	//聚合所有验证
	var foblall = function(){
		fobl(name.loginname,message.loginname.a,message.loginname.b,message.loginname.c,rules.loginname.c,message.loginname.d,rules.loginname.d);
		fobl(name.email,message.email.a,message.email.b,message.email.c,rules.email.c);
		fobl(name.password,message.password.a,message.password.b,message.password.c,rules.password.c);
		fobl(name.rpassword,message.rpassword.a,message.rpassword.b,message.rpassword.c,rules.rpassword.c);
		fobl(name.realname,message.realname.a,message.realname.b,message.realname.c,rules.realname.c,message.realname.d,rules.realname.d);
		fobl(name.mobile,message.mobile.a,message.mobile.b,message.mobile.c,rules.mobile.c);
		fobl(name.idcard,message.idcard.a,message.idcard.b,message.idcard.c,rules.idcard.c);
		fobl(name.chars20,message.chars20.a,message.chars20.b,message.chars20.c,rules.chars20.c);
		fobl(name.chars50,message.chars50.a,message.chars50.b,message.chars50.c,rules.chars50.c);
		fobl(name.chars100,message.chars100.a,message.chars100.b,message.chars100.c,rules.chars100.c);
		fobl(name.money,message.money.a,message.money.b,message.money.c,rules.money.c,message.money.d,rules.money.d,message.money.e,rules.money.e);
		fobl(name.education,message.education.a,message.education.b,message.education.c,rules.education.c);
		fobl(name.zipcode,message.zipcode.a,message.zipcode.b,message.zipcode.c,rules.zipcode.c);
		fobl(name.number4,message.number.a,message.number.b,message.number.c,rules.number.c,message.number.d,rules.number.d);
		fobl(name.phone1,message.phone.a1,message.phone.b,message.phone.c,rules.phone.c);
		fobl(name.phone2,message.phone.a2,message.phone.b,message.phone.d,rules.phone.d);
	};
	var sumtall = function(){
		flag = true;
		fo.find(".mvalidatemsg").html("").end().find(".mvalidatewarn").removeClass("mvalidatewarn");
		sumt(name.loginname,message.loginname.a,message.loginname.c,rules.loginname.c,message.loginname.d,rules.loginname.d);
		sumt(name.email,message.email.a,message.email.c,rules.email.c);
		sumt(name.password,message.password.a,message.password.c,rules.password.c);
		sumt(name.rpassword,message.rpassword.a,message.rpassword.c,rules.rpassword.c);
		sumt(name.realname,message.realname.a,message.realname.c,rules.realname.c,message.realname.d,rules.realname.d);
		sumt(name.mobile,message.mobile.a,message.mobile.c,rules.mobile.c);
		sumt(name.idcard,message.idcard.a,message.idcard.c,rules.idcard.c);
		sumt(name.chars20,message.chars20.a,message.chars20.c,rules.chars20.c);
		sumt(name.chars50,message.chars50.a,message.chars50.c,rules.chars50.c);
		sumt(name.chars100,message.chars100.a,message.chars100.c,rules.chars100.c);
		sumt(name.money,message.money.a,message.money.c,rules.money.c,message.money.d,rules.money.d,message.money.e,rules.money.e);
		sumt(name.education,message.education.a,message.education.c,rules.education.c);
		sumt(name.zipcode,message.zipcode.a,message.zipcode.c,rules.zipcode.c);
		sumt(name.number4,message.number.a,message.number.c,rules.number.c,message.number.d,rules.number.d);
		sumt(name.phone1,message.phone.a1,message.phone.c,rules.phone.c);
		sumt(name.phone2,message.phone.a2,message.phone.d,rules.phone.d);
		return flag;
	};
	//执行
	$("input[type='text']",fo).live("focus blur",function(event){  //输入框获得焦点后样式提示
		if(event.type == "focus"){
			$(this).addClass("mvalidatefocus");
		}else{
			$(this).removeClass("mvalidatefocus");
		};
	});
	if(param1){			//焦点绑定
		foblall(); 
	}else if(param2){	//提交绑定并反会值
		sumtall();
		return flag;
	}else{				//焦点绑定,提交绑定并反会值
		foblall();
		if(submit){
			submit.live("click",sumtall);	
		}else{
			$(".validateSubmit",fo).live("click",sumtall);	
		};
	};
};	