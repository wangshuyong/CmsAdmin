// JavaScript Document
	$(document).ready(function(){ 

					$(function(){ 
						var Request = new Object();  
						//获取url中的参数  
						Request = GetRequest();  
				
						var id=Request['noticeId'];   
						
						$.ajax({ 
							url:"admin/Notice_edit", 
							data: {noticeId:id},
							type:"post", 
							dataType:"json", 
							async:false,
							 success:function(data){
								 $("#noticeid").val(data.id);
								 $("#noticetitle").val(data.title);
								 $("#noticecontent").val(data.content);
								},
								error:function(data){
									alert("登录超时，请重新登录！");
								}
						}); 

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