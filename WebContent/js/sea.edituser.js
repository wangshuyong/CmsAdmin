// JavaScript Document
$(document).ready(function(){ 
	App.init();
	Gallery.init();
	
	$(function(){ 
		var Request = new Object();  
		//获取url中的参数  
		Request = GetRequest();  

        var id=Request['userId'];   
		$("#userId").val(id);
		$.ajax({ 
			url:"admin/User_edit", 
			data: {userId:id},
			type:"post", 
			dataType:"json", 
			async:false,
			 success:function(data){
				 $("#userId").val(id);
				$("#userFormId").val(data.id);
				$("#phone").val(data.phone);
				$("#realName").val(data.realName);
				
				$("#carID").val(data.carID);
				$("#address").val(data.address);

				if(data.idPhoto=='' || null==data.idPhoto){
					$("#photo").attr("src","../img/photo.jpg");
					$("#photoHref").attr("href","../img/photo.jpg");
				}else { 
					$("#photo").attr("src",'..'+data.idPhoto);
					$("#photoHref").attr("href",'..'+data.idPhoto);
				}
			},
			error:function(){
				alert("登录超时，请重新登录！");
			}
		}); 
		
		
	});
	$("#return").click(function(){
		window.location.href="User_list.html";
	});

});

function GetRequest() {  
    //url例子：XXX.aspx?ID=" + ID + "&Name=" + Name；  
    var url = location.search; //获取url中"?"符以及其后的字串  
    var theRequest = new Object();  
    if(url.indexOf("?") != -1)//url中存在问号，也就说有参数。  
    {   
      var str = url.substr(1);  
        strs = str.split("&");  
      for(var i = 0; i < strs.length; i ++)  
        {   
         theRequest[strs[i].split("=")[0]]=unescape(strs[i].split("=")[1]);  
        }  
    }  
    return theRequest;  
}  

 function ajaxFileUpload() { 
	var Request = new Object();  ajaxFileUpload
		//获取url中的参数  
	Request = GetRequest();  
   	
	$("#loading")
		.ajaxStart(function(){
			$(this).show();
		})//开始上传文件时显示一个图片
		.ajaxComplete(function(){
			$(this).hide();
		});//文件上传完成将图片隐藏起来
		
		$.ajaxFileUpload
		({
			url:'../admin/fileUpload_UploaIdImage',//用于文件上传的服务器端请求地址
			
			secureuri:false,//一般设置为false
			data:{"userId":Request['userId'],"uploadType":"USER_IDPhoto_PATH"},
			fileElementId:'file',//文件上传空间的id属性  <input type="file" id="file" name="file" />
			dataType: 'json',//返回值类型 一般设置为json
			success: function (data, status)  //服务器成功响应处理函数
			{
				//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量
				alert(data.message);
			},
			error: function (data, status, e)//服务器响应失败处理函数
			{
				alert(data);
				alert(e);
			}
		})
		return false;
}