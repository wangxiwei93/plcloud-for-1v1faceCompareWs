function del(id, delUrl, jumpUrl){
	if(confirm("确认删除吗?")) {
		var querydata = {};
		querydata.id = id;
		$.ajax({ 
			type        : "POST"
			,url         : delUrl
			,data        : querydata
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
					alert("删除成功!");
					//$("#queryform").submit();
					document.location.href = jumpUrl;
					
				}
				else if (info.code == 0) {
					alert(info.msg);
				}
				else if (info.code == -1) {
					alert("删除异常!");
				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
	            
		        alert(XMLHttpRequest.status + textStatus);    
			} 
		}
		);
	}
}

function formSave(formId, saveUrl, jumpUrl){
	
	this.form = $(formId);
	this.saveUrl = saveUrl;
	this.jumpUrl = jumpUrl;
	
	this.showRequest = function(formData, jqForm, options) { 	   
	    return true; 
	};
	
	this.showResponse = function(responseText, statusText, xhr, $form)  { 
		var info = responseText;
		console.log(info);
		if (info.code == 1) {
			console.log('info.code == 1');	
			$('#myDlgContent').text('保存成功');
			$('#myModal').on('hidden.bs.modal', function (e) {
				document.location.href = jumpUrl;
			});
		}
		else {
			$('#myDlgContent').text(info.msg);
			$("#savebtn").attr("disabled", false);
			$('#myModal').on('hidden.bs.modal', function (e) {});			
		}
		
		$('#myModal').modal('show');
	};
	
	this.form.ajaxForm({ 
			target:        	'#output1',
		    beforeSubmit:  	this.showRequest,
		    success:       	this.showResponse,
		 
			url:       		this.saveUrl,
		    type:      		'post',
		    dataType:  		'json',
		    contentType: 	"application/x-www-form-urlencoded;charset=utf-8;" 		
	});	
	
	this.submit = function(){
		this.form.submit();	
	};
	
};

function save(form, saveUrl, jumpUrl) {
	var fs = new formSave(form, saveUrl, jumpUrl);
	fs.submit();
}


function isShiShu(str) {  
	var reg=/^\d+(\.{0,1}\d+){0,1}$/; 
	return reg.test(str);
}
function isNumber(str){
	var reg = /^\d+$/;
	return reg.test(str);
}
function gotourl(url){
	document.location.href = url;
}

function export_e(){
	var url = $("#excel_export_zl").parent()[0].href +'&exportflag=true';
	document.location.href = url;
}

//获取选择的Value
function getCheckedRowValue(checkboxSuffix){
	if(typeof(checkboxSuffix) == "undefined" || checkboxSuffix == null)
	{
		checkboxSuffix = "";
	}
	var returnValue = '';var s1 = '';
	var objs = document.getElementsByName('checkRow_' + checkboxSuffix);
	for(var i=0;i < objs.length;i=i+1){
		if(objs[i].checked){
			returnValue += s1 + objs[i].value;s1=',';
		}
	}
	return returnValue;
}
