jQuery(document).ready(

function (){
	var $wrapper = $('#div-loglist');
	var $table = $('#loglist');
	$('#searchTime').daterangepicker(
	{
		// startDate: moment().startOf('day'),
		//endDate: moment(),
		//minDate: '01/01/2012',    //最小时间
		maxDate : moment(), //最大时间
		
		showDropdowns : true,
		showWeekNumbers : false, //是否显示第几周
		timePicker : true, //是否显示小时和分钟
		timePickerIncrement : 60, //时间的增量，单位为分钟
		timePicker12Hour : false, //是否使用12小时制来显示时间
		ranges : {
			//'最近1小时': [moment().subtract('hours',1), moment()],
			'今日': [moment().startOf('day'), moment()],
			//'昨日': [moment().subtract('days', 1).startOf('day'), moment().subtract('days', 1).endOf('day')],
			'最近7日': [moment().subtract('days', 6), moment()],
			//'最近30日': [moment().subtract('days', 29), moment()]
		},
		opens : 'right', //日期选择框的弹出位置
		buttonClasses : [ 'btn btn-default' ],
		applyClass : 'btn-sm btn-primary blue',
		cancelClass : 'btn-sm',
		format : 'YYYY-MM-DD HH:mm:ss', //控件中from和to 显示的日期格式
		separator : ' to ',
		locale : {
			applyLabel : '确定',
			cancelLabel : '取消',
			fromLabel : '起始时间',
			toLabel : '结束时间',
			customRangeLabel : '其他时间',
			daysOfWeek : [ '日', '一', '二', '三', '四', '五', '六' ],
			monthNames : [ '一月', '二月', '三月', '四月', '五月', '六月',
					'七月', '八月', '九月', '十月', '十一月', '十二月' ],
			firstDay : 1
		}
	}, function(start, end, label) {//格式化日期显示框
		$('#searchTime').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
   });
   /**
 * 时间对象的格式化
 */
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
			//手动控制遮罩
			//$wrapper.spinModal();
			//封装请求参数
			var param = userManage.getQueryCondition(data);

			$.ajax({
		            type: "post",
		            url: "admin/Log_list",
		            cache : false,	//禁用缓存
		            data: param,	//传入已封装的参数
		            dataType: "json",
		            success: function(result) {
							var member = eval("("+result+")");
		                    //封装返回数据，这里仅演示了修改属性名
		            		var returnData = {};
			            	returnData.draw = member.draw;//这里直接自行返回了draw计数器,应该由后台返回
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
            {
            	className : "ellipsis",	
				width : "20%",
            	mDataProp: "moduleName",
            	render : CONSTANT.DATA_TABLES.RENDER.ELLIPSIS,
				
            	//固定列宽，但至少留下一个活动列不要固定宽度，让表格自行调整。不要将所有列都指定列宽，否则页面伸缩时所有列都会随之按比例伸缩。
				//切记设置table样式为table-layout:fixed; 否则列宽不会强制为指定宽度，也不会出现省略号。		
            },
			{
				mDataProp : "action",
				
				render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?data:"空");
				}
			},
			{
				mDataProp :"user.phone",
				width : "10%",
				render : function(data,type, row, meta) {
					return '<i class="fa fa-male"></i> '+(data?data:"空");
				}
			},
			
			{
				mDataProp : "createTime",
				width : "20%",
				render : function(data,type, row, meta) {
					
					return '<i class="fa fa-male"></i> '+(data?userManage.utcToDate(data):"空");
				}
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
            $('td', row).eq(7).append($btnEdit).append($btnDel);  
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
		window.location.href="User_add.html";
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
		window.location.href="User_edit.html?userId="+item.id;
	})
	.on("click",".btn-del",function() {
		//点击删除按钮
		var item = _table.row($(this).closest('tr')).data();
		$(this).closest('tr').addClass("active").siblings().removeClass("active");
		userManage.deleteItem([item]);
		_table.draw(false);
	});

	$("#btn-info-content-collapse").click(function(){
		$("i",this).toggleClass("fa-minus fa-plus");
		$("span",this).toggle();
		$("#user-view .info-content").slideToggle("fast");
	});

	
	$('#importFile').on('click', function(e) {
		
		
		$('#importFile').change(function(e) { 
			
			$("#loading")
				.ajaxStart(function(){
					$(this).show();
				})//开始上传文件时显示一个图片
				.ajaxComplete(function(){
					$(this).hide();
				});//文件上传完成将图片隐藏起来
				
				$.ajaxFileUpload
				({
					url:'../admin/userImport',//用于文件上传的服务器端请求地址
					secureuri:false,//一般设置为false
					fileElementId:'importFile',//文件上传空间的id属性  <input type="file" id="file" name="file" />
					dataType: 'json',//返回值类型 一般设置为json
					success: function (data, status)  //服务器成功响应处理函数
					{
						//从服务器返回的json中取出message中的数据,其中message为在struts2中定义的成员变量
						alert(data.message);
						_table.draw(false);
					},
					error: function (data, status, e)//服务器响应失败处理函数
					{
						alert(e);
					}
				})
		})
		   });
	
	$('#dropdown-export').on('click', function() {
		var searchTime=$('#searchTime').val();
		var arrs= new Array();
		arrs=searchTime.split("to");
		var startTime=arrs[0];
		var endTime=arrs[1];
		var searchText = $("#searchText").val();
		searchText = encodeURI(encodeURI(searchText));
		alert(searchText);
		window.location.href="../admin/User_exportUsers"+"?searchText="+searchText +"&startTime="+startTime+"&endTime="+endTime;
		//$.post("../admin/User_exportUsers",{"searchText":searchText,"startTime":startTime,"endTime":endTime}); 
	});
	
});

var userManage = {
	currentItem : null,
	fuzzySearch : true,
	getQueryCondition : function(data) {
		var param = {};
		
		//组装查询参数
		param.fuzzySearch = userManage.fuzzySearch;
		if (userManage.fuzzySearch) {
			var searchTime=$('#searchTime').val();
			var arrs= new Array();
			arrs=searchTime.split("to");
			var startTime=arrs[0];
			var endTime=arrs[1];
			param.searchText = $("#searchText").val();
			param.startTime = startTime;
			param.endTime = endTime;
		}
		//组装分页参数
		param.startIndex = data.start;
		param.pageSize = data.length;
		
		param.draw = data.draw;
		
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

};