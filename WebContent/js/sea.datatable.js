// JavaScript Document

jQuery(document).ready(function () {
    TableEditable.init();
    //搜索
    $("#btn_Search").click(function () {
  initUserList()
    })
    
	$("#userlist tbody").on( 'click', 'button#delete', function () {
            var data = TableEditable.oTable.row( $(this).parents('tr') ).data();
			alert(JSON.stringify(data[0]));
			//var aData = oTable.fnGetData(event.target.parentNode);
            $.ajax({
                url: '../User_delete',
                type: 'get',
                dataType: 'json',
                data: {userId:JSON.stringify(data[0])},
            })
            .done(function(data) {
                if (data=="success") {
                    TableEditable.init().oTable.ajax.reload();
                };
            })
            .fail(function() {
                alert("error");
            });
        });
});
 
var ajaxJson= function() {  
        var url = 'admin/User_list';  
        //获取表单值，并以json的数据形式保存到params中  
        var param =  {"currentPage":0,"pagerMethod":"first"};
        //使用$.post方式      
        $.ajax({
			url:url,
			type:"POST",
			data:param,
			dataType:"json",
			success: function(data) {
				//var member = eval("("+data+")");    //包数据解析为json 格式    
				$.each(data, function(key,value) {
					$("#userlist").append("<tr>/tr>").append("<td>"+value.id+"</td>").append("<td>"+value.phone+"</td>").append("<td>"+value.realName+"</td><");
			 });
		},
    });  
}
			
var oTable = null;
var initUserList = function () {
    var table = $('#userlist');
    if (oTable == null) { //仅第一次检索时初始化Datatable   
       oTable = table.dataTable({			
    	"iDisplayLength" : 10,// 每页显示行数			
    	"bSort" : false,// 排序			
//    	"bStateSave": false,        // 保存状态到cookie 很重要 ，
    	//"aLengthMenu": [[50,100,500,1000,10000], [50,100,500,1000,10000]],//定义每页显示数据数量		
    	//"iScrollLoadGap":400,//用于指定当DataTable设置为滚动时，最多可以一屏显示多少条数据	
    	//"aaSorting": [[4, "desc"]],			
//    	"bInfo" : true,// Showing 1 to 10 of 23 entries 总记录数没也显示多少等信息		
//    	"bWidth":true,			
//    	"sScrollY": "62%",	
//    	"sScrollX": "100%",	
//    	"bScrollCollapse": true,			
    	
    	} ); 
 
    }
}

var TableEditable = function () {

    return {
        //main function to initiate the module
        init: function () {
            function restoreRow(oTable, nRow) {
                var aData = oTable.fnGetData(nRow);
                var jqTds = $('>td', nRow);
                for (var i = 0, iLen = jqTds.length; i < iLen; i++) {
                    oTable.fnUpdate(aData[i], nRow, i, false);
                }
                oTable.fnDraw();
            }

            var oTable = $('#userlist').dataTable({
                "aLengthMenu": [
                    [5, 15, 20, -1],
                    [5, 15, 20, "All"] // change per page values here
                ],
				"oLanguage" : { // 汉化	
    	     	"sProcessing" : "正在加载数据...",				
    			"sLengthMenu" : "显示_MENU_条 ",				
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
			    "fnRowCallback" : function(nRow, aData, iDisplayIndex) {  
                        /* 用来改写用户权限的 */  
                        if (aData.power == '1')  
                            $('td:eq(8)', nRow).html('管理员');  
                        if (aData.power == '0')  
                            $('td:eq(8)', nRow).html('一般用户');  
						if (aData.sex == '1')  
                            $('td:eq(5)', nRow).html('女');  
                        if (aData.sex == '0')  
                            $('td:eq(5)', nRow).html('男'); 
                      
                        return nRow;  
                 }, 
                // set the initial value
                "iDisplayLength": 10,
                "sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
                "sPaginationType": "bootstrap",
                "sPaginationType" : "full_numbers", // 分页，一共两种样式 另一种为two_button // 是datatables默认	
				"bProcessing" : false,
				"bServerSide" : true,
				"bDestroy": true,
				"bJQueryUI": true,			
				"bPaginate" : true,// 分页按钮			
				"bFilter" : true,// 搜索栏			
		//    	"bLengthChange" : false,// 每行显示记录数
				"iDisplayStart":0,
				"sAjaxSource": 'admin/User_list', 
				"bRetrieve":true,	
				"bStateSave": true,	
    	  		"fnServerData" : function (sSource, aoData, fnCallback) {
//    		  var jsonParam = JSON.stringify(aoData);
					$.ajax({
						"type" : 'get',
						"contentType": "application/json",
						"url" : sSource,
						"dataType" : "json",
						"data" : {aoData :JSON.stringify(aoData)},
						"success" : function(resp) {
							var member = eval("("+resp+")");
							fnCallback(member);
						}
					});
				},
				"aoColumns":[
				{"mDataProp": null, "sWidth": "5%", "sDefaultContent": "<input type='checkbox' class='checkboxes' value='1'></input>", "bSortable": false },
				{ mDataProp: "id", sWidth : "5%","sClass":"center" ,},
				{ mDataProp: "phone",  "sClass":"center",},
				{ mDataProp: "realName", "sClass":"center",},
				{ mDataProp: "age", "sClass":"center" ,},
				{ mDataProp: "sex", "sClass":"center",},
				{ mDataProp: "email",  "sClass":"center",},
				{ mDataProp: "address", "sClass":"center",},
				{ mDataProp: "power",  "sClass":"center",},
				{mDataProp: null, "sDefaultContent": "<a class='edit' href=''>编辑</a>" ,"bSortable": false },
				{mDataProp: null, "sDefaultContent": "<button id='delete' href='../User_delete' value='shanchu'></button>", "bSortable": false }
                ]
            });

//<a class='edit' id='delet href='../User_edit?userId=id'>删除</a>
			 var nEditing = null;

			jQuery('#userlist .group-checkable').change(function () {
                var set = jQuery(this).attr("data-set");
                var checked = jQuery(this).is(":checked");
                jQuery(set).each(function () {
                    if (checked) {
                        $(this).attr("checked", true);
                    } else {
                        $(this).attr("checked", false);
                    }
                });
                jQuery.uniform.update(set);
            });
			
	

             /** 
			$("#userlist tbody").click(function(event) { //当点击表格内某一条记录的时候，会将此记录的cId和cName写入到隐藏域中
               
				$(oTable.fnSettings().aoData).each(function() {
                   $(this.nTr).removeClass('row_selected');
                });
				$(event.target.parentNode).addClass('row_selected');
				var aData = oTable.fnGetData(event.target.parentNode);
				$.ajax({ 
					type: "GET", 
					url: "../User_edit", 
					data: {userId:JSON.stringify(aData.id)},//参数名要和Action 中的参数名相同 
					dataType: "json", 
					cache: false, 
					 success: function (result) { 
					  location.href ="../User_edit.html"; 
					} 
				});
			});**/
        }

    };

}();