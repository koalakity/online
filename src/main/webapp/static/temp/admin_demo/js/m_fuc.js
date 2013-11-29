jQuery.mFuc = {         
	backpic:function(img,wd,hg) { //图片格式化
		if(wd&&hg){
			return '<img src="css/m_images/' + img + '" width="' + wd + '" height="' + hg + '" />'; 
		}else{
			return '<img src="css/m_images/' + img + '" />';
		};      
	},
	backa:function(url,tgt,val) { //链接a格式化      
		return '<a href="' + url + '" class="text" target="' + tgt + '">' + val + '</a>';         
	},
	winW:function(){ //获取显示器的宽度
		return window.screen.width;
	},
	winH:function(){ //获取显示器的高度
		return window.screen.height;
	},
	dateS:function(val){ // 格式化日期（秒）
		var date = new Date(val);
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hour = date.getHours()< 10 ? "0" + date.getHours() : date.getHours();
		var mints = date.getMinutes()< 10 ? "0" + date.getMinutes() : date.getMinutes();
		var sec = date.getSeconds()< 10 ? "0" + date.getSeconds() : date.getSeconds();
		return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + mints + ":" + sec;
	},
	dateM:function(val){ // 格式化日期（分）
		var _dateM = $.mFuc.dateS(val);
		return _dateM.substring(0,_dateM.lastIndexOf(":"));
	},
	dateD:function(val){ // 格式化日期（天）
		var _dateD = $.mFuc.dateS(val);
		return _dateD.substring(0,_dateD.indexOf(" "));
	},
	newW:function(url,val){ // 格式化链接a，此链接a打开固定大小的新窗并未与显示器中央  
		return '<a href="#" class="text" onclick="window.open(\''+url+'\',\'\',\'scrollbars=yes,width=800,height=600,left='+($.mFuc.winW()-800)/2+',top='+($.mFuc.winH()-700)/2+'\')">' + val + '</a>';         
	},
	backUrl:function(url){ //当前窗返回到url地址 
		window.location.href=url;
	}	
};