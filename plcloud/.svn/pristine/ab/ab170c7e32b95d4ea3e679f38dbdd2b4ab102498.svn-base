<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>

<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<style>
<!--
.ztree li a.curSelectedNode { height: 18px; }
.ztree li a:hover {text-decoration:none}
.count:hover {text-decoration:underline;}
-->
</style>
	<div class="panel panel-default">
  		<div class="panel-heading">
  			<div class="btn-group">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/group/list.do"  method="post">  		
  			<div class="btn-group">
  				<input id="name" name="name" type="text" class="form-control" placeholder="请输入分组名称" value="${name}">
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >查询</button>
  			</div>
  			</form>
  			</div>
  			
  			<div class="pull-right">
  				<c:if test="${(!empty userPrivilege['10000101'])}">
				<div class="btn-group" >
  					<button type="button" class="btn btn-primary" onclick="addGroup()">新增分组</button>
  				</div>
  				</c:if>
  			</div> 
  		</div>
  		<div class="panel-body">
			<ul id="tree" class="ztree"></ul>
 		</div>
	</div>	
	<div class="modal fade" id="myform" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
	  <div class="modal-dialog">
	    <div class="modal-content" style="width: 300px;">
	      <div class="modal-header">
	        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
	        <h4 class="modal-title" id="myModalLabel">新增分组</h4>
	      </div>
	      <div class="modal-body">
	       	<form:form class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
			  <div class="form-group" style="margin-right: 0px;">
			    <label for="title" class="col-sm-4 control-label" style="padding-left: 5px;padding-right: 5px;">分组名称</label>
			    <div class="col-sm-8" style="padding-left: 0px">
			      <input type="text" class="form-control" id="groupName" name="groupName" placeholder="分组名称" value="">
			    </div>
			  </div>
			</form:form>
	      </div>
	      <div class="modal-footer">
	        <button type="button" class="btn btn-primary" onclick="addSubmit()">确定</button>
	      </div>
	    </div>
	  </div>
	</div>
	
	
	
<script src="${ctx}/js/common.js"></script>
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	
<SCRIPT type="text/javascript">
<!--


var setting = {
		view: {
			//addHoverDom: addHoverDom,
			//removeHoverDom: removeHoverDom,
			//addDiyDom: addDiyDom,
			selectedMulti: false
		},
		edit: {
			enable: true,
			editNameSelectAll: true,
			showRemoveBtn: showRemoveBtn,
			showRenameBtn: showRenameBtn
		},
		data: {
			simpleData: {
				enable: true
			}
		},
		callback: {
			//beforeDrag: beforeDrag,
			//beforeEditName: beforeEditName,
			beforeRemove: delGroup,
			beforeRename: beforeRename,
			//onRemove: delGroup,
			//onRename: onRename
		}
	};
	
var IDMark_Switch = "_switch",
IDMark_Icon = "_ico",
IDMark_Span = "_span",
IDMark_Input = "_input",
IDMark_Check = "_check",
IDMark_Edit = "_edit",
IDMark_Remove = "_remove",
IDMark_Ul = "_ul",
IDMark_A = "_a";

function addDiyDom(treeId, treeNode) {
	
	var aObj = $("#" + treeNode.tId + IDMark_A);
	if (treeNode.userCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='userCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.userCount+");return false;'>用户数:"+treeNode.userCount+"</span>";
		aObj.append(editStr);
		var btn = $("#userCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	} 
	
	if (treeNode.terminalCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='terminalCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.terminalCount+");return false;'>终端数:"+treeNode.terminalCount+"</span>";
		aObj.append(editStr);
		var btn = $("#terminalCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
	if (treeNode.resourceCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='resourceCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.resourceCount+");return false;'>资源数:"+treeNode.resourceCount+"</span>";
		aObj.append(editStr);
		var btn = $("#resourceCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
	if (treeNode.noticeCount > 0) {
		var editStr = "&nbsp;&nbsp;<span id='noticeCount_" +treeNode.id+ "' class='count' onclick='alert("+treeNode.noticeCount+");return false;'>公告数:"+treeNode.noticeCount+"</span>";
		aObj.append(editStr);
		var btn = $("#noticeCount_"+treeNode.id);
		//if (btn) btn.bind("click", function(){alert("diy Button for " + treeNode.name);});
	}
}

function removeHoverDom(treeId, treeNode) {
	$("#userCount_"+treeNode.id).unbind().remove();
	$("#terminalCount_"+treeNode.id).unbind().remove();
	$("#resourceCount_"+treeNode.id).unbind().remove();
	$("#noticeCount_"+treeNode.id).unbind().remove();
}

var zNodes =${groupTreeBeans};

function showRenameBtn(treeId, treeNode){
	<c:if test="${(empty userPrivilege['10000101'])}">
		if(true){
			return false;
		}
	</c:if>
	return  treeNode.id != 2 && treeNode.id != 1;
}

function showRemoveBtn(treeId, treeNode) {
	<c:if test="${(empty userPrivilege['10000102'])}">
		if(true){
			return false;
		}
	</c:if>
	var isParent = treeNode.isParent;
	return !isParent && treeNode.id != 2;
}

var zTree;
$(document).ready(function(){
	
	zTree = $.fn.zTree.init($("#tree"), setting, zNodes);
	
	
});

function addGroup(){
	
	var nodes = zTree.getSelectedNodes();
	if (nodes.length != 1) {
		alert("请选择一个父节点!");
		return false;
	}
	if (nodes[0].id == 2) {
		alert("该节点下不能建分组!");
		return false;
	}
	$("#groupName").val("");
	$('#myform').modal('show');
}

function addSubmit(){
	
	var groupName = $("#groupName").val();
	if (groupName == "") {
		alert("请输入分组名称!");
		return false;
	}
	
	var nodes = zTree.getSelectedNodes();
	var data = {};
	data.name = groupName;
	data.pid = nodes[0].id;
	var url = "${ctx}/group/save.do"
	$.ajax({ 
		type        : "POST"
		,url         : url
		,data        : data
		,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
		,dataType    : "json"
		,cache		  : false	
		,success: function(info) {
			
			if (info.code == 1) {
				var obj = eval('(' + info.msg + ')');
				//var node =  info.msg;
				zTree.addNodes(nodes[0], obj);
				$('#myform').modal('hide');
				zTree.cancelSelectedNode(nodes[0]);
				
			}
			else if (info.code == 0) {
				alert(info.msg);
			}
			else if (info.code == -1) {
				alert("新增分组异常!");
			}					
		}
		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		} 
		
	}
	);
}


function beforeRename(treeId, treeNode, newName, isCancel) {
	var flag = false;
	if(newName.length ==0){
		alert("请输入分组名称!");
		return flag;
	}
	
	var data = {};
	data.id = treeNode.id;
	data.name = newName;
	data.pid = treeNode.pid;
	$.ajax({ 
		type        : "POST"
		,url         :"${ctx}/group/save.do"
		,data        : data
		,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
		,dataType    : "json"
		,cache		  : false	
		,async:false
		,success: function(info) {
			
			if (info.code == 1) {
				flag = true;
			}
			else if (info.code == 0) {
				alert(info.msg);
			}
			else if (info.code == -1) {
				alert("保存异常!");
			}					
		}
		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		} 
	}
	);
	
	return flag;
}

function delGroup(treeId, treeNode){
	var flag = false;
	if(confirm("确认删除吗?")) {
		var querydata = {};
		querydata.id = treeNode.id;
		$.ajax({ 
			type        : "POST"
			,url         :"${ctx}/group/delete.do"
			,data        : querydata
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			 ,async:false
			,success: function(info) {
				
				if (info.code == 1) {
					alert("删除成功!");
					//$("#queryform").submit();
					//document.location.href = jumpUrl;
					//zTree.removeNode(treeNode, false);
					flag = true;
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
	
	return flag;

	
	
	
}





//-->
</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

