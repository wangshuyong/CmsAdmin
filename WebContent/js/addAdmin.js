// JavaScript Document

$(document).ready(function(){ 
	App.init();
	Gallery.init();
	 $("#addAdmin").click(function(){
	
		 var options = {
			url: 'admin/User_addAdmin',
			type: 'post',
			dataType: 'text',
			data: $("#adminform").serialize(),
			success: function (data) {
				alert(data);
				if(data.toString()=='"添加成功"'){
					window.location.href="Area_list.html"
				}
			}
		};
		$.ajax(options);
		return false;
	});
});

			
			