	function searchcheck(){
		if(document.search_form.condition.value==''){
			alert('对不起，请输入查询条件！');
			return false;
			document.search_form.condition.focus();
		}else{
			var tempurl = document.search_form.condition.value;
			search_form.action = "searchinfo.shtml?urlcondition="+tempurl;
			search_form.submit();
		}
	}
	function getcondition(){
		
		document.wirte(tempurl);
	}
	function bookmarkit(){
	window.external.addfavorite("/","专业的WEB开发网站推广技术")
	}
	function rundemo(k){
		var democode=document.getElementById(k).value
		var demowin=window.open()
		
		demowin.document.open();
		demowin.document.write(democode)
		demowin.document.close();
	}
