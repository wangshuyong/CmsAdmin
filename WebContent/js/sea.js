jQuery(document).ready(

function () { 
	/*$('#userlist tfoot th').each( function () {
        var title = $(this).text();
        $(this).html( '<input type="text" placeholder="Search '+title+'" />' );
    } );
 
    $('#userlist tfoot tr').appendTo('#userlist thead');
 */
 var oTable;
var userManage = {
	currentItem : null,
	fuzzySearch : true,
	getQueryCondition : function(data) {
		var param = {};
		var startTime="";
		var endTime="";
		var searchText="";
		serachText=$("#searchText").val();
		searchTime=$('#config-demo').val();
		var arrs= new Array();
		arrs=searchTime.split("to");
		startTime=arrs[0];
		endTime=arrs[1];
		//组装查询参数
		param.fuzzySearch = userManage.fuzzySearch;
		
		if (userManage.fuzzySearch) {
			param.searchText = searchText;
			param.startTime = startTime;
			param.endTime = endTime;
		}else{
			param.name = $("#name-search").val();
			param.position = $("#position-search").val();
			param.office = $("#office-search").val();
			param.extn = $("#extn-search").val();
			param.status = $("#status-search").val();
			param.role = $("#role-search").val();
		}
		//组装分页参数
		//param.startIndex = data.start;
		//param.pageSize = data.length;
		//param.draw = data.draw;
		
		return param;
	}
};
	$('#config-demo').daterangepicker(
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
		$('#config-demo').html(start.format('YYYY-MM-DD HH:mm:ss') + ' - ' + end.format('YYYY-MM-DD HH:mm:ss'));
   });
   
	
    oTable = $('#userlist').dataTable({

	"aLengthMenu": [
		[10,15, 20, -1],
		[10, 15, 20, "All"] // change per page values here
	],
	"oLanguage" : { // 汉化	
	"sProcessing" : "正在加载数据...",				
	"sLengthMenu" : "每页显示_MENU_条 ",				
	"sZeroRecords" : "没有您要搜索的内容",				
	"sInfo" : "显示_START_ 到 _END_ 条记录——总记录数为 _TOTAL_ 条",	
	"sInfoEmpty" : "记录数为0",
	"sInfoFiltered" : "(全部记录数 _MAX_条)",				
	"sInfoPostFix" : "",
	"sSearch" : "搜索",
	"sUrl" : "",				
	"oPaginate" : {	
		"sFirst" : "第一页",
		"sPrevious" : " 上一页 ",	
		"sNext" : " 下一页 ",
		"sLast" : " 最后一页 "	
	   }			
   },	

	
	// set the initial value
	"iDisplayLength": 10,
	//"sDom": '<"top"iflp<"clear">>rt<"bottom"ilp<"clear">>',
	//"sDom": "<'row'<'span6'f>r>t<'row'<'span6'l><'span6'p>>",
	"sDom": '<"top"><"row"<"col-md-6"f>r>t<"bottom"><"row"<"col-md-6"l><"col-md-6"p>>',

	"sPaginationType": "bootstrap",
	"sPaginationType" : "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认	
	"bProcessing" : false,
	
	"sScrollX": "100%",   //表格的宽度  
    "sScrollXInner": "110%",   //表格的内容宽度
	"bServerSide" : true,
	"bDestroy": true,

	"bJQueryUI": true,			
	"bPaginate" : true,// 分页按钮			
	"bFilter" : false,// 搜索栏			
//    	"bLengthChange" : false,// 每行显示记录数
	"iDisplayStart":0,
	"sAjaxSource": 'admin/User_list', 
	"bRetrieve":true,	
	"bStateSave": true,	
	"fnServerData" : function (sSource, aoData, fnCallback,oSettings) {
//    		  var jsonParam = JSON.stringify(aoData);
		var param = userManage.getQueryCondition(aoData);
		aoData.push({
					"name":"param","value":param
				});	
		$.ajax({
			"type" : 'post',
			"contentType" : "application/x-www-form-urlencoded; charset=utf-8",
			"url" : sSource,
			"async": true,
			"dataType" : "json",
			"data" :{aoData :JSON.stringify(aoData),"param":param},
			"success" : function(resp) {
				var member = eval("("+resp+")");
				fnCallback(member);
			}
		});
	},

	"fnRowCallback" : function(nRow, aData, iDisplayIndex) {  
		/* 用来改写用户权限的 
		if (aData.power == '1')  
			$('td:eq(5)', nRow).html('管理员');  
		if (aData.power == '2')  
			$('td:eq(5)', nRow).html('资料下载');  
		if (aData.power == '3')  
			$('td:eq(5)', nRow).html('一般用户'); */   
		return nRow;  
	},  
	"aoColumns":[
	{"mDataProp": null, "sWidth": "5%", "sDefaultContent": "<input type='checkbox' id='checkBox' class='checkboxes' value='1'></input>", "bSortable": false },
	{ mDataProp: "id", sWidth : "5%","sClass":"center","bVisible": false},
	{ mDataProp: "phone",  "sClass":"center",},
	{ mDataProp: "realName", "sClass":"center",},
	{ mDataProp: "userName", "sClass":"center" ,},
	{ mDataProp: "carID",  "sClass":"center",},
	{ mDataProp: "address",  "sClass":"center",},
	{ mDataProp: "createTime",  "sClass":"center",},
	{mDataProp: null, "sDefaultContent": "<a id='edituser'>编辑</a>" ,"bSortable": false },
	{mDataProp: null, "sDefaultContent": "<a id='deluser' >删除</a>" ,"bSortable": false }
	],
	
	"aoColumnDefs" : [ {
        "aTargets": [7], 
            "bSearchable": false, 
            "sType": 'date',
            "fnRender": function ( oObj ) {
                var javascriptDate = new Date(oObj.aData[7]);
                javascriptDate = javascriptDate.getDate()+"/"+(javascriptDate.getMonth()+1)+"/"+javascriptDate.getFullYear();
                return "<div class='date'>"+javascriptDate+"<div>";
            }
        }
    ],
	
//"dom": '<"toolbar">frtip'
                  
   
});

//oTable end
/*
$("div.top").html('<form id="searchUser" class="navbar-form" role="search"><div class="input-group  dateRange"><span class="input-group-addon">注册时间</span><input type="text" id="config-demo" class="form-control" style="font-size: 18px;line-height: 30px placeholder="注册时间"/></div><div class="input-group" style="font-size: 18px"><input type="text" id="searchText" class="form-control" style="font-size: 18px;line-height: 30px"placeholder="姓名、手机号、车牌号..."/></div><div class="controls input-group" style="font-size: 18px;line-height: 30px"><span class="input-group-btn"><button  type="submit" id="searchbtn" style="font-size:18px;width:60px" title="Search"><i class="glyphicon glyphicon-search"></i></button></span></div></form>');*/
                            

$("#searchbtn").click(function(){
	
		oTable.api().search($(this).val()).draw();
		
	});

/*
$("#searchbtn").click(function(){
	var startTime="";
		var endTime="";
		var searchText="";
		var searchTime="";
		var arrs= new Array();
		serachText=$("#searchText").val();
		arrs=$("#config-demo").val().split("to");
		startTime=arrs[0];
		endTime=arrs[1];
		var oSettings = oTable.fnSettings();
    	oSettings.aoServerParams.push({
			"fn": function (aoData) {
				aoData.push({
					"name":"startTime","value":startTime} ,
					{"name":"endTime","value": endTime},
					{"name":"serachText","value":serachText
				});	
			}
		});
		oTable.serachText=$("#searchText").val();
		oTable.arrs=$("#config-demo").val().split("to");
		oTable.ajax.reload();
    	oTable.draw();

	});

	
	//编辑行数据
	var nEditing = null;
	//$('#userlist a#edituser').live('click', function (e) {
	/*$('#userlist a#edituser').live('click', function (e) {
		e.preventDefault();

		// Get the row as a parent of the link that was clicked on 
		var nRow = $(this).parents('tr')[0];
		if (nEditing !== null && nEditing != nRow) {
			// Currently editing - but not this row - restore the old before continuing to edit mode 
			restoreRow(oTable, nEditing);
			editRow(oTable, nRow);
			nEditing = nRow;
		} else {
			// No edit in progress - let's start one 
			editRow(oTable, nRow);
			nEditing = nRow;
		}
	});
	*/
	
	$('#userlist a#edituser').live('click',function(event) { //当点击表格内某一条记录的时候，会将此记录的cId和cName写入到隐藏域中
		$(event.target.parentNode).addClass('row_selected');
		var nRow = $(this).parents('tr')[0];
		var aData = oTable.fnGetData(nRow);
		window.location.href="../admin/User_edit.html?userId="+aData.id
		
	});
	
	$('#userlist a#deluser').live('click',function(event) { //当点击表格内某一条记录的时候，会将此记录的cId和cName写入到隐藏域中
		$(event.target.parentNode).addClass('row_selected');
		var nRow = $(this).parents('tr')[0];
		var aData = oTable.fnGetData(nRow);
		if(aData !=null){
			if (confirm("确认删除所选记录？删除后无法恢复，您确认？？？") == false) {
                    return;
            }
		}
		$.post("../admin/User_delete",{userId:JSON.stringify(aData.id)},function(){oTable.fnDraw();alert("删除成功！");
		});
	});

	$('#userlist a#cancel').live('click', function (e) {
		e.preventDefault();
		if ($(this).attr("id") == "cancel") {
			var nRow = $(this).parents('tr')[0];
			cancelEditRow(oTable, nRow)
		} else {
			restoreRow(oTable, nEditing);
			nEditing = null;
		}
	});
	
	$('#userlist a#save').live('click', function (e) {
		e.preventDefault();
		if ($(this).attr("id") == "save") {
			var nRow = $(this).parents('tr')[0];
			saveRow(oTable, nRow)
			nEditing = null;
		} else {
			restoreRow(oTable, nEditing);
			nEditing = null;
		}
	});
	
	$("#del").click(function(){
		var ids="";
         $('#userlist').find('tr > td:first-child input:checkbox').each(function () {
			 if (this.checked) {
				 var nRow = $(this).parents('tr')[0];
				 var aData = oTable.fnGetData(nRow);
				 ids+=aData.id+",";	
			 }
          });
         //如果没有勾选，提示
         if (ids === "") {
             alert("请选择一行数据！");
             return;
         } else {
			 if (confirm("确认删除所选记录？删除后无法恢复，您确认？？？") == false) {
                    return;
                }     
			$.post("../admin/User_delete",{userId:ids},function(){oTable.fnDraw();alert("删除成功！");}); 	
         }
	});
	
//双击删除行数据
     $("#userlist tbody").dblclick(function(event) { //当点击表格内某一条记录的时候，会将此记录的cId和cName写入到隐藏域中
		$(event.target.parentNode).addClass('row_selected');
		var aData = oTable.fnGetData(event.target.parentNode);
		if(aData !=null){
			if (confirm("确认删除所选记录？删除后无法恢复，您确认？？？") == false) {
                    return;
            }
		}
		$.post("../admin/User_delete",{userId:JSON.stringify(aData.id)},function(){oTable.fnDraw();alert("删除成功！");
		});
	});
		
	
	function editRow(oTable, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		jqTds[0].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.id + '">';
		jqTds[1].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.phone + '">';
		jqTds[2].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.realName + '">';
		jqTds[3].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.userName + '">';

		jqTds[4].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.carID + '">';

		jqTds[5].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.address + '">';
		jqTds[6].innerHTML = '<input type="text" class="m-wrap small" value="' + aData.createTime + '">';
		jqTds[7].innerHTML = '<a id="save" href="">保存</a>';
		jqTds[8].innerHTML = '<a id="cancel" href="">取消</a>';
	}


	function saveRow(oTable, nRow) {
		var jqInputs = $('input', nRow);
		var aData = oTable.fnGetData(nRow);
		var user = {"user.id":aData.id,"user.phone":jqInputs[1].value,"user.realName":jqInputs[2].value,"user.userName":jqInputs[3].value,"user.email":jqInputs[4].value,"user.power":jqInputs[5].value};
		
		$.ajax({
			"type" : 'post',
			"contentType" : "application/x-www-form-urlencoded; charset=utf-8",
			"url" : "../admin/User_edit",
			"dataType" : "json",
			"data" : user,	
			"success" : function(){
				oTable.fnDraw();
				alert("修改成功！");
			},
			"error" : function(){
				oTable.fnDraw();
				alert("修改失败！");
			}
		});
	}

	function cancelEditRow(oTable, nRow) {
//		restoreRow(oTable, nEditing);
        nEditing = null;
		oTable.fnDraw();
	}
	
	function restoreRow(oTable, nRow) {
		var aData = oTable.fnGetData(nRow);
		var jqTds = $('>td', nRow);
		for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
			oTable.fnUpdate(aData[i], nRow, i, false);
		}
		oTable.fnDraw();
	}
	$(function(){
		 $('#dropdown-import').on('click', function(e) {
		e.preventDefault();
		$(this).closest('input[type=file]').trigger('click');
		})
	})
	
	$(document).on('click', 'th input:checkbox', function() {
	 var that = this;
	 $(this).closest('table').find('tr > td:first-child input:checkbox')
		 .each(function() {
			 this.checked = that.checked;
	  });
	});
	
	
	
	$('#dropdown-import').on('click', function(e) {
		e.preventDefault();
		$('#importFile').trigger('click');
		
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
						oTable.fnDraw();
					},
					error: function (data, status, e)//服务器响应失败处理函数
					{
						alert(e);
					}
				})
		   })
	})
	
	$("#searchbtn1").click(function(){
		var startTime="";
		var endTime="";
		var searchText="";
		serachText=$("#searchText").val();
		searchTime=$('#config-demo').val();
		var arrs= new Array();
		arrs=searchTime.split("to");
		startTime=arrs[0];
		endTime=arrs[1];
		if (serachText === ""&&startTime==="") {
             alert("请输入查询内容！");
             return;
         } else {
			alert(serachText);
			alert(startTime);
			alert(endTime);
			
			$.ajax({ 
			url:"admin/User_select", 
			type:"post", 
			dataType:"json", 
			"data" :{startTime:startTime,endTime:endTime,serachText:serachText},
			 success:function(data){
						oTable.fnDraw();
				},
				error:function(){
					alert("登录超时，请重新登录！");
				}
			}); 
         } 
	})
	
});