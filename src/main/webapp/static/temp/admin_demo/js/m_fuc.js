jQuery.mFuc = {         
	backpic:function(img,wd,hg) { //ͼƬ��ʽ��
		if(wd&&hg){
			return '<img src="css/m_images/' + img + '" width="' + wd + '" height="' + hg + '" />'; 
		}else{
			return '<img src="css/m_images/' + img + '" />';
		};      
	},
	backa:function(url,tgt,val) { //����a��ʽ��      
		return '<a href="' + url + '" class="text" target="' + tgt + '">' + val + '</a>';         
	},
	winW:function(){ //��ȡ��ʾ���Ŀ��
		return window.screen.width;
	},
	winH:function(){ //��ȡ��ʾ���ĸ߶�
		return window.screen.height;
	},
	dateS:function(val){ // ��ʽ�����ڣ��룩
		var date = new Date(val);
		var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var hour = date.getHours()< 10 ? "0" + date.getHours() : date.getHours();
		var mints = date.getMinutes()< 10 ? "0" + date.getMinutes() : date.getMinutes();
		var sec = date.getSeconds()< 10 ? "0" + date.getSeconds() : date.getSeconds();
		return date.getFullYear() + "-" + month + "-" + day + " " + hour + ":" + mints + ":" + sec;
	},
	dateM:function(val){ // ��ʽ�����ڣ��֣�
		var _dateM = $.mFuc.dateS(val);
		return _dateM.substring(0,_dateM.lastIndexOf(":"));
	},
	dateD:function(val){ // ��ʽ�����ڣ��죩
		var _dateD = $.mFuc.dateS(val);
		return _dateD.substring(0,_dateD.indexOf(" "));
	},
	newW:function(url,val){ // ��ʽ������a��������a�򿪹̶���С���´���δ����ʾ������  
		return '<a href="#" class="text" onclick="window.open(\''+url+'\',\'\',\'scrollbars=yes,width=800,height=600,left='+($.mFuc.winW()-800)/2+',top='+($.mFuc.winH()-700)/2+'\')">' + val + '</a>';         
	},
	backUrl:function(url){ //��ǰ�����ص�url��ַ 
		window.location.href=url;
	}	
};