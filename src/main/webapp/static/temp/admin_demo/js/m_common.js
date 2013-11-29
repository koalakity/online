$(function(){
	$(window).resize(function(){ // 修改datagrid默认为fit（仅宽度）
		$('table').each(function(){
			if($.data(this,'datagrid')){
				var datagrid = $(this);
				setTimeout(function(){
					datagrid.datagrid('resize');	
				},200);
			};
		});
    });
});