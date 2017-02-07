jQuery(document).ready(

function (){
	var $wrapper = $('#div-userlist');
	var $table = $('#userlist');
Date.prototype.format = function(format) {
	 /*
	  * format="yyyy-MM-dd hh:mm:ss";
	  */
		 var o = {
		  "M+" : this.getMonth() + 1,
		  "d+" : this.getDate(),
		  "h+" : this.getHours(),
		  "m+" : this.getMinutes(),
		  "s+" : this.getSeconds(),
		  "q+" : Math.floor((this.getMonth() + 3) / 3),
		  "S" : this.getMilliseconds()
		 }
		 if (/(y+)/.test(format)) {
		  format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4
			  - RegExp.$1.length));
		 }
		 for (var k in o) {
		  if (new RegExp("(" + k + ")").test(format)) {
		   format = format.replace(RegExp.$1, RegExp.$1.length == 1
			   ? o[k]: ("00" + o[k]).substr(("" + o[k]).length));
		  }
		 }
		 return format;
	} ;
	var _table = $table.dataTable(
	
	$.extend(true,{},CONSTANT.DATA_TABLES.DEFAULT_OPTION, {
		ajax : function(data, callback, settings) {//ajax配置为function,手动调用异步查询
			
			var param = userManage.getQueryCondition(data);
			$.ajax({
		            type: "post",
		            url: "admin/User_adminUserList",
		            cache : false,	//禁用缓存
		            data: param,	//传入已封装的参数
		            dataType: "json",
		            success: function(result) {
						var member = eval("("+result+")");
						//封装返回数据，这里仅演示了修改属性名
						var returnData = {};
						//returnData.draw = member.draw;//这里直接自行返回了draw计数器,应该由后台返回
						returnData.recordsTotal = member.total;
						returnData.recordsFiltered = member.total;//后台不实现过滤功能，每次查询均视作全部结果
						returnData.data = member.pageData;
						//关闭遮罩
						//$wrapper.spinModal(false);
						//调用DataTables提供的callback方法，代表数据已封装完成并传回DataTables进行渲染
						//此时的数据需确保正确无误，异常判断应在执行此回调前自行处理完毕

						callback(returnData);
		           },
		           error: function(XMLHttpRequest, textStatus, errorThrown) {
		                alert("登录会话超时，请重新登录");
		               // $wrapper.spinModal(false);
		            }
		        });
		},
		
        columns: [
            CONSTANT.DATA_TABLES.COLUMN.CHECKBOX,
            { mDataProp: "id", width : "3%","sClass":"center","bVisible": false},
			{
            	className : "ellipsis",	//文字过长时用省略号显示，CSS实现
            	mDataProp: "phone",
				width : "15%",
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,//会显示省略号的列，需要用title属性实现划过时显示全部文本的效果
            },
            {
            	className : "ellipsis",	
            	mDataProp: "realName",
            	render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?data:"空");
				}
				
            	//固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
				//切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。		
            },
			{
				mDataProp : "userName",
				render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?data:"空");
				}
			},
			
			 {
				//className : "td-operation",
				mDataProp: "area.areaName",
				defaultContent:"",
				//orderable : false,
			},
			
			{
				mDataProp : "createTime",
				render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?userManage.utcToDate(data):"空");
				}
			},

			 {
				className : "td-operation",
				mDataProp: null,
				defaultContent:"",
				orderable : false,
				width : "15%",
			}
        ],
        "createdRow": function ( row, mDataProp, index ) {
        	//行渲染回调,在这里可以对该行dom元素进行任何操作
        	//给当前行加样式
        	if (mDataProp.role) {
        		$(row).addClass("info");
			}
        	//给当前行某列加样式
        	$('td', row).eq(3).addClass(mDataProp.status?"text-success":"text-error");
        	//不使用render，改用jquery文档操作呈现单元格
			
            var $btnEdit = $('<button type="button" class="btn btn-default btn-xs btn-primary btn-edit">修改</button>');
            var $btnDel = $('<button type="button" class="btn btn-default btn-xs btn-danger btn-del">删除</button>');
            $('td', row).eq(6).append($btnEdit).append($btnDel);  
        },
        "drawCallback": function( settings ) {
        	//渲染完毕后的回调
        	//清空全选状态
			$(":checkbox[name='cb-check-all']",$wrapper).prop('checked', false);
        	//默认选中第一行
        	$("tbody tr",$table).eq(0).click();
        }
	})).api();//此处需调用api()方法,否则返回的是JQuery对象而不是DataTables的API对象

	$("#userAdd").click(function(){
		var Request = new Object();  
						//获取url中的参数  
			 Request = userManage.GetRequest();  
			 var id=Request['areaId']; 
		window.location.href="Super_admin_add.html?areaId="+id;
	});
	
	$("#userDel").click(function(){
		var arrItemId = [];
        $("tbody :checkbox:checked",$table).each(function(i) {
        	var item = _table.row($(this).closest('tr')).data();
        	arrItemId.push(item);
        });
		userManage.deleteItem(arrItemId);
		_table.draw(false);
		
	});
	
	$("#searchbtn").click(function(){
		userManage.fuzzySearch = true;
		
		//reload效果与draw(true)或者draw()类似,draw(false)则可在获取新数据的同时停留在当前页码,可自行试验
//		_table.ajax.reload();
//		_table.draw(false);
		_table.draw(false);
	});
	
	//行点击事件
	$("tbody",$table).on("click","tr",function(event) {
		$(this).addClass("active").siblings().removeClass("active");
		//获取该行对应的数据
		var item = _table.row($(this).closest('tr')).data();
		userManage.currentItem = item;
		
    });
	
	$table.on("change",":checkbox",function() {
		if ($(this).is("[name='cb-check-all']")) {
			//全选
			$(":checkbox",$table).prop("checked",$(this).prop("checked"));
		}else{
			//一般复选
			var checkbox = $("tbody :checkbox",$table);
			$(":checkbox[name='cb-check-all']",$table).prop('checked', checkbox.length == checkbox.filter(':checked').length);
		}
    }).on("click",".td-checkbox",function(event) {
    	//点击单元格即点击复选框
    	!$(event.target).is(":checkbox") && $(":checkbox",this).trigger("click");
    }).on("click",".btn-edit",function() {
    	//点击编辑按钮
        var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.currentItem = item;
		window.location.href="Super_admin_edit.html?userId="+item.id;
	})
	.on("click",".btn-del",function() {
		//点击删除按钮
		var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.deleteItem([item]);
		_table.draw(false);
	}).on("click",".btn-add",function() {
		//点击删除按钮
		var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.currentItem = item;
		window.location.href="Super_admin_add.html?areaId="+item.area.id;
	});
	
});

var userManage = {
	currentItem : null,
getQueryCondition : function(data) {
		var param = {};
		param.fuzzySearch = userManage.fuzzySearch;
		if (userManage.fuzzySearch) {
			param.fuzzy = $("#admin-search").val();
		}
		//组装分页参数
		param.startIndex = data.start;
		param.pageSize = data.length;
		
		param.draw = data.draw;
		var Request =new Object();
		Request = userManage.GetRequest(); 
		param.areaId=Request['areaId']; 
		return param;
	},
	deleteItem : function(selectedItems) {
		var message;
		var ids="";
		if (selectedItems&&selectedItems.length) {
			if (selectedItems.length == 1) {
				message = "确定要删除 '"+selectedItems[0].phone+"' 吗?";
				//alert(selectedItems[0].id);
				ids=selectedItems[0].id
				$.post("../admin/User_delete",{userId:ids}); 

			}else{
				message = "确定要删除选中的"+selectedItems.length+"项记录吗?";
				for (var i=0;i<selectedItems.length;i++)
				{
					ids+=selectedItems[i].id+',';
				}
				$.post("../admin/User_delete",{userId:ids}); 
				
			}
			alert(message, function(){
				alert('执行删除操作');
			});
		}else{
			alert('请先选中要操作的行');
		}
	},
	
	utcToDate:function (obj) {
         var date = new Date();
		 date.setTime(obj.time);
		 date.setHours(obj.hours);
		 date.setMinutes(obj.minutes);
		 date.setSeconds(obj.seconds);
		 return date.format("yyyy-MM-dd hh:mm:ss"); 
        },
 	GetRequest:function() {  
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
	}  ,
	FormatHtml:function (str){
	  if(str.length == 0) return '';
	  var HTML = $(str);
	 
	  /* 去除所有样式 */
	  HTML.find('*').removeAttr('style').removeAttr('class');
	 
	  HTML.find('div').replaceWith(function(){
		return  $(this).contents();
		});
	 
	  /* 过滤回车BR */
	  HTML.find('br').replaceWith(function(){
		return '<br />';
	  });
	 
	  /* 过滤样式 */
	  HTML.find('p').replaceWith(function(){
		return '<p>'+$(this).contents()+'</p>';
		});
	 
	  /* 连接过滤[不要图片之类的] 
	  HTML.find('a').replaceWith(function(){
		if($(this).attr('href').substring(0,4) == 'http'){
		  return '<a href="'+$(this).attr('href')+'" target="_blank">'+$(this).text()+'</a>';
		}else{
		  return '';
		}
	  });*/
	 
	  /* 这里是只返回“职位职能: ”这四个字  */
	  return  HTML.html();
	},
};