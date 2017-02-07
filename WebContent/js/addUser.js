// JavaScript Document
$(document).ready(function(){ 
App.init();
	Gallery.init();
	// Form Validation
	$("#useradd").validate({
		submitHandler:function() {
			ajaxSubmitForm();
		},
		rules:{
			"user.phone":{
				required:true,
				number:true,
				minlength:11,
				maxlength:15
			},
			
			"user.email":{
				email: true
			},
			"user.age":{
				number: true
			}
		},
		errorClass: "help-inline",
		errorElement: "span",
		highlight:function(element, errorClass, validClass) {
			$(element).parents('.control-group').addClass('error');
		},
		unhighlight: function(element, errorClass, validClass) {
			$(element).parents('.control-group').removeClass('error');
			$(element).parents('.control-group').addClass('success');
		},
		 submitHandler: function(form) {
			jQuery(commentform).ajaxSubmit({
				url: 'admin/User_addAdmin',
				type: 'post',
				dataType: 'text',
				data: $("#useradd").serialize(),
				success: function (data) {
					alert(data);
					if(data.toString()=='"添加成功"'){
						window.location.href="Area_list.html"
					}
				}
			});
			return false;
		 }
	});
	 $("#userAdd").click(function(){
		 var options = {
			url: 'admin/User_addAdmin',
			type: 'post',
			dataType: 'text',
			data: $("#useradd").serialize(),
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