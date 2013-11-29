function popupDiv(div_id,status) {
			if(status==1){
				$('#popBoxTitle').html("确认审核通过");
			}else if(status==3){
				$('#popBoxTitle').html("确认流标");
			}else if(status==4){
				$('#popBoxTitle').html("确认放款");
			}
			else{
				alert("操作失败！");
				return;
			}
			$('#AuditEnsure').click(function() {
				  checkImg(status);
				  $('#AuditEnsure').unbind('click');
				});
			$('#validateCode').val("");
	        var div_obj = $("#"+div_id);  
	        var windowWidth = document.body.clientWidth;       
	        var windowHeight = document.body.clientHeight;  
	        var popupHeight = div_obj.height();       
	        var popupWidth = div_obj.width();    
	        //添加并显示遮罩层   
	        $("<div id='mask'></div>").addClass("mask")   
	                                  .width(windowWidth + document.body.scrollWidth)   
	                                  .height(windowHeight + document.body.scrollHeight)   
	                                  .click(function() {hideDiv(div_id); })   
	                                  .appendTo("body")   
	                                  .fadeIn(200);   
	        div_obj.css({"position": "absolute"})   
	               .animate({left: windowWidth/2-popupWidth/2,    
	                         top: windowHeight/2-popupHeight/2, opacity: "show" }, "normal");   
	                        
	    }   
	      
	    function hideDiv(div_id) {   
	        $("#mask").remove();   
	        $("#" + div_id).animate({left: 0, top: 0, opacity: "hide" }, "slow");
	        $('#AuditEnsure').unbind('click');
	    }
	    
	    function changeImg(){  
			var obj = document.getElementById("imgObj");
			//获取当前的时间作为参数，无具体意义   
			var timenow = new Date().getTime();
			//每次请求需要一个不同的参数，否则可能会返回同样的验证码   
			//这和浏览器的缓存机制有关系，也可以把页面设置为不缓存，这样就不用这个参数了。   
			obj.src = "getValidatorImg?id=" + timenow;		
		} 
	    
	    function checkImg(status){
	    	var validateCode = $('#validateCode').val();
	    	$.post("imgValidate", 
					  { validateCode:validateCode },
						function (result){
							if (result.success) {
								changeImg();
								passAudit(status);
								hideDiv('pop-div');
								} else {
									alert("验证码错误");
									$('#AuditEnsure').click(function() {
										  checkImg(status);
										  $('#AuditEnsure').unbind('click');
										});
								}
						}, "json");
	    }