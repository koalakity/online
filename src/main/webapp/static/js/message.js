function ChkAllClick(sonName, cbAllId) {
	var arrSon = document.getElementsByName(sonName);
	var cbAll = document.getElementById(cbAllId);
	var tempState = cbAll.checked;
	for (i = 0; i < arrSon.length; i++) {
		if (arrSon[i].checked != tempState)
			arrSon[i].click();
	}
}
function del() {
	if (confirm("是否确定删除? ")) {
		var checkAll = document.getElementsByName("chkAll1");
		var checkbox = document.getElementsByName("chkSon1");
		var id = "";
		if (checkbox.length > 0 || checkAll[0].checked == true) {
			for ( var i = 0; i < checkbox.length; i++) {
				if (checkbox[i].checked == true) {
					if (id != '')
						id = id + ",";
					id = id + "" + checkbox[i].value + "";
				}
			}

		}
		if (id == '') {
			alert('请选择!');
			return;
		}
		subaction("0",id);
	}
}

	function delSingle(check,myid){
		if (confirm("是否确定删除 ?")) {	
		var checkbox = document.getElementsByName(check);
		var id = "";
		if(checkbox.length>0)
		{
		  for(var i=0;i<checkbox.length;i++)
		  {
		    if(checkbox[i].checked==true)
		    {
		      if (id != '')
		      	  id = id + ",";
			      id = id + "" + checkbox[i].value + "";
		      if(checkbox[i].value!=myid)
		      {
		      alert('请选择本条信息删除');
		     return;
		      }
		    }
		  }
		}
		return false;
		if (id == '') 
		{
		  alert('请选择本条信息删除!');
		  return false;
		}
		  return;
		  }
	}

function ChkAllClick2(sonName, cbAllId2) {
	var arrSon = document.getElementsByName(sonName);
	var cbAll = document.getElementById(cbAllId2);
	var tempState = cbAll.checked;
	for (i = 0; i < arrSon.length; i++) {
		if (arrSon[i].checked != tempState)
			arrSon[i].click();
	}
}
function del2() {
	if (confirm("是否确定删除? ")) {
		var checkAll2 = document.getElementsByName("chkAll2");
		var checkbox2 = document.getElementsByName("chkSon2");
		var id = "";
		if (checkbox2.length > 0 || checkAll2[0].checked == true) {

			for ( var i = 0; i < checkbox2.length; i++) {
				
				if (checkbox2[i].checked == true) {
					if (id != '')
						id = id + ",";
					id = id + "" + checkbox2[i].value + "";
					
				}
			}

		}
			if (id == '') {
			alert('请选择!');
			return;
		}
		subaction("1",id);
	}
}


function ChkAllClick3(sonName, cbAllId3) {
	
	var arrSon = document.getElementsByName(sonName);
	var cbAll = document.getElementById(cbAllId3);
	var tempState = cbAll.checked;
	for (i = 0; i < arrSon.length; i++) {
		if (arrSon[i].checked != tempState)
			arrSon[i].click();
	}
}
function del3() {
	if (confirm("是否确定删除? ")) {
		var checkAll3 = document.getElementsByName("chkAll3");
		var checkbox3 = document.getElementsByName("chkSon3");
		var id = "";
		if (checkbox3.length > 0 || checkAll3[0].checked == true) {
			for ( var i = 0; i < checkbox3.length; i++) {
				if (checkbox3[i].checked == true) {
					if (id != '')
						id = id + ",";
					id = id + "" + checkbox3[i].value + "";
				}
			}

		}
		if (id == '') {
			alert('请选择!');
			return;
		}
			subaction("2",id);
	}
}