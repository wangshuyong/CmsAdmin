/**
 * Unicorn Admin Template
 * Diablo9983 -> diablo9983@gmail.com
**/
$(document).ready(function(){

	var login = $('#loginform');
	var recover = $('#recoverform');
	var speed = 400;
	
	$('input[type=checkbox],input[type=radio],input[type=file]').uniform();
	
	$('select').select2();
	
	// Form Validation
    $("#loginform").validate({
		rules:{
			phone:{
				required:true
			},
			password:{
				required:true
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
		}
	});
	
	
	$('#to-recover').click(function(){
		login.fadeTo(speed,0.01).css('z-index','100');
		recover.fadeTo(speed,1).css('z-index','200');
	});

	$('#to-login').click(function(){
		recover.fadeTo(speed,0.01).css('z-index','100');
		login.fadeTo(speed,1).css('z-index','200');
	});
    
    if($.browser.msie == true && $.browser.version.slice(0,3) < 10) {
        $('input[placeholder]').each(function(){      
	        var input = $(this);       
	        $(input).val(input.attr('placeholder'));
	               
	        $(input).focus(function(){
	             if (input.val() == input.attr('placeholder')) {
	                 input.val('');
	             }
	        });  
	        $(input).blur(function(){
	            if (input.val() == '' || input.val() == input.attr('placeholder')) {
	                input.val(input.attr('placeholder'));
	            }
	        });
	    });
	}
 
	
});