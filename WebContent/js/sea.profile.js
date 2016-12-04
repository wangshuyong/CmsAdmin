// JavaScript Document
$(document).ready(function(){ 
	
	$(function(){ 
	
		$.ajax({ 
			url:"admin/User_profile", 
			type:"post", 
			dataType:"json", 
			
			 success:function(data){
					$("#phone").val(data.phone);
					$("#realName").val(data.realName);
					$("#age").val(data.age);
					$("#sex").val(data.sex);
					$("#email").val(data.email);
					$("#address").val(data.address);
					$("#nativePlace").val(data.nativePlace);
					$("#hobby").val(data.hobby);
					if(data.photo=='' || null==data.photo){
						$("#photo").attr("src","../img/photo.jpg");
					}else {
						$("#photo").attr("src",'../'+data.photo);
					}
				},
				error:function(){
					alert("系统异常，请稍后重试！");
				}
		}); 
		
		
	});

});