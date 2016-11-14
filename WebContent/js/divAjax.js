/****
*
*页面中可能用的到Ajax异步请求<br/>
*
*主要是以Prototype.js和YUI为基础 开源项目
*
* 版权 @ http://www.prototypejs.org   http://www.yahoo.com
*
*@Author zhouyang
*
****/


/** 根据请求更新层 **/
function divUpdater(url,divName){
//	$(divName).innerHTML = "<center><img src='" + projectPath + "web/web/js/blue-loading.gif'/></center>" ;
	$(divName).innerHTML = "<center>loading</center>" ;
	var myAjax = new Ajax.Updater(
		divName, 
		url, 
		{
			method: 'post', 
			evalScripts:true 
		}
	);
}


/** 刷新标签页   yui**/
function flushPage(url){
	var tabName = tabView.get('activeTab');
//	alert("tabName:"+tabName+"    url="+url);
	refreshTab(tabName,url);	
}


function divRequest(url,divName){
	var myAjax = new Ajax.Request(
			url, 
			{
				method: 'post', 
				onComplete: showResponse
			}
	);
}

function showResponse(transport){
	 // alert(transport.responseText);
	  document.getElementById(divName).innerText = transport.responseText ;
}