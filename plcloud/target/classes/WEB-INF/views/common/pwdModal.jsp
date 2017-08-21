<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<script type="text/javascript">
<!--


$('#changepwdmodal').on('hide.bs.modal', function (e) {
	$("#oldPwd").val("");
	$("#newPwd").val("");
	$("#newPwdConfirm").val("");
});

function openChangePwdModal(){
	$("#oldPwd").val("");
	$("#newPwd").val("");
	$("#newPwdConfirm").val("");
	$("#changepwdmodal").modal('show');
	
}


function pwdformSave(formId, saveUrl, jumpUrl){
	
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
			alert('密码修改成功,请重新用新密码登陆!');
			document.location.href = jumpUrl;
			
		}
		else {
			alert(info.msg);
			$("#savebtn").attr("disabled", false);
			
		}
		
		//$('#myModal').modal('show');
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

function pwdsave(form, saveUrl, jumpUrl) {
	var fs = new pwdformSave(form, saveUrl, jumpUrl);
	$("#savebtn").attr("disabled", true);
	fs.submit();
}

//-->
</script>

<div class="modal fade" id="changepwdmodal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog"  >
    <div class="modal-content" >
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">修改密码</h4>
      </div>
      <div class="modal-body" >	
      		<form:form id="pwdForm" name="pwdForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="userId" name="userId" type="hidden" value="${userProfile.currentUserId}" >
				<input id="loginName" name="loginName" type="hidden" value="${userProfile.currentUserLoginName}" >
			  <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">旧密码</label>
			    <div class="col-sm-4">
			      <input type="password" class="form-control" id="oldPwd" name="oldPwd" placeholder="旧密码" value="">
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">新密码</label>
			    <div class="col-sm-4">
			      <input type="password" class="form-control" id="newPwd" name="newPwd" placeholder="新密码" value="">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="title" class="col-sm-3 control-label">新密码确认</label>
			    <div class="col-sm-4">
			      <input type="password" class="form-control" id="newPwdConfirm" name="newPwdConfirm" placeholder="新密码确认" value="">
			    </div>
			  </div>
			</form:form>
      </div>
      <div class="modal-footer">
      	<button id="savebtn" type="button" class="btn btn-primary" onclick="pwdsave('#pwdForm', g_ctx + '/changepwd.do', g_ctx + '/logout.do')">保存</button>
      </div>
    </div>
  </div>
</div> 