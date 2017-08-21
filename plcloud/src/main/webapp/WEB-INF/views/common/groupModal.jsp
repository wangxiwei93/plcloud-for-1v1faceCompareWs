<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	
<script type="text/javascript">
<!--

var setting_group = {
	check: {
		enable: true
		,chkStyle: "radio"
		,radioType: "all"
	},
	data: {
		simpleData: {
			enable: true
		}
	}
};



var zTree_group;
var groupIdInputId;
var groupTextInputId;
var onlyleafcheck = false;
var clickCallback;
/**
 * 打开分组树
 */
function openGroupTree(groupId_inputId, groupText_inputId, settingGroup,zNodes_group,onlyleaf_check, click_Callback) {
	if(settingGroup != null){
		setting_group = settingGroup;
	}
	if(onlyleaf_check != null){
		onlyleafcheck = onlyleaf_check;
	}
	if(click_Callback != null){
		clickCallback = click_Callback;
	}else {
		clickCallback = null;
	}
	groupIdInputId = groupId_inputId;
	groupTextInputId = groupText_inputId;
	
	zTree_group = $.fn.zTree.init($("#groupTree"), setting_group, zNodes_group);
	
	var ids = $("#"+groupIdInputId).val();
	var id_array= ids.split(","); 
	for (var i=0, l=id_array.length; i < l; i++) {
		var id = id_array[i];
		var node = zTree_group.getNodesByParam("id",id);
		if (node && node.length > 0) {
			expandParentNode(node[0]);
			zTree_group.expandNode(node[0], true, true, true);
			zTree_group.checkNode(node[0],true,true);	
		}
		
	}
	$('#groupTreeModal').modal('show');
}

function queryGroupTree(){
	var groupName = $('#groupName').val();
	var data = {};
	data.name = groupName;
	data.onlyleafcheck = onlyleafcheck;
	$.ajax({ 
		type        : "POST"
		,url         : "${ctx}/unauthen/groups/list.do"
		,data        : data
		,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
		,dataType    : "json"
		,cache		  : false	
		,success: function(info) {
			zTree_group.destroy();
			zTree_group = $.fn.zTree.init($("#groupTree"), setting_group, info);
			if (groupName.length > 0){
				zTree_group.expandAll(true);
			}else {
				var ids = $("#"+groupIdInputId).val();
				var id_array= ids.split(","); 
				for (var i=0, l=id_array.length; i < l; i++) {
					var id = id_array[i];
					var node = zTree_group.getNodesByParam("id",id);
					if (node && node.length > 0) {
						expandParentNode(node[0]);
						zTree_group.expandNode(node[0], true, true, true);
						zTree_group.checkNode(node[0],true,true);	
					}
					
				}
			}
		}
		,error : function(XMLHttpRequest, textStatus, errorThrown) {    
			alert(XMLHttpRequest.status + textStatus);    
		 } 		
		}
	);
	
}


function expandParentNode (node){
	var parent  = node.getParentNode();
	if(parent != null && parent.level>0){
		expandParentNode(parent);
		zTree_group.expandNode(parent, true, true, true);
	}
	
}

function selectGroup() {
	if (clickCallback != null) {
		clickCallback();
		return false;
	}
	
	var nodes = zTree_group.getNodes();
	var ids = [];
	var names = [];
	for(var i=0;i<nodes.length;i++){
		getNodeCheckedGroupID(nodes[i],ids,names);
	}
	
	$("#"+groupIdInputId).val(ids.join(','));
	$("#"+groupTextInputId).val(names.join(','));
	$('#groupTreeModal').modal('hide');
}

//node选中的分组
function getNodeCheckedGroupID(node, ids, names)
{
	var halfCheck = node.getCheckStatus();
	if (halfCheck && halfCheck.checked && !halfCheck.half){
		ids.push(node.id);
		names.push(node.name);
	}	
	else {
		var cs = node.children;
		if (cs){
			for(var i=0; i<cs.length; i++){
				getNodeCheckedGroupID(cs[i], ids,names);
			}
		}
	}	
}

//node选中的叶子分组
function getonlyleafNodeCheckedGroupID(node, ids, names)
{
	var cs = node.children;
	if (cs && cs.length > 0){
		for(var i=0; i<cs.length; i++){
			getonlyleafNodeCheckedGroupID(cs[i], ids,names);
		}
	}else{
		var halfCheck = node.getCheckStatus();
		if (halfCheck && halfCheck.checked && !halfCheck.half){
			ids.push(node.id);
			names.push(node.name);
		}	
	}
}

//-->
</script>
<div class="modal fade" id="groupTreeModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog modal-sm" >
    <div class="modal-content" >
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">选择分组</h4>
      </div>
      <div class="modal-body" style="padding: 0px;">
      	<div class="input-group" style="margin-top: 5px;margin-bottom: 5px;margin-left: 10px;margin-right: 10px;">
			<input id="groupName" name="groupName" type="text" class="form-control" placeholder="请输入分组名称" >
			<span class="input-group-btn">
	        	<button class="btn btn-default" type="button" onclick="queryGroupTree()">查询</button>
	      	</span>
		</div>
        <ul id="groupTree" class="ztree"></ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="selectGroup()">选择</button>
      </div>
    </div>
  </div>
</div>