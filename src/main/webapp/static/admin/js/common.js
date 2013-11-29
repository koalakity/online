/*
$(function(){
    var currentUrl = (location.href).replace("http://", "");
    var currentUrlArr = currentUrl.split("/");
    var folderLen = currentUrlArr.length - 4;
    var urlRightStr = "";
    for (var i = 0; i < folderLen; i++){
        urlRightStr += "../";
    }
	$("*").ajaxError(function(){
		if (window != top){
			top.location.href = urlRightStr+"login";
		}
	});
});
*/
$(function(){
	$("*").ajaxError(function(){
		if (window != top){
			var _location = top.location.href;
			_location = _location.substring(0,_location.lastIndexOf('online'));
			top.location.href = _location + "online/admin/login";
		}
	});
});