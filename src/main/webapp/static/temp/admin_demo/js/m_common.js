$(function(){
	$(window).resize(function(){ // �޸�datagridĬ��Ϊfit������ȣ�
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