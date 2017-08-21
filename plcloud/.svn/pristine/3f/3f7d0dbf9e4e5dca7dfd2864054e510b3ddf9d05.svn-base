<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
<div class="panel panel-default">
  		<div class="panel-heading">
			<div class="pull-right">
				<a class="btn btn-primary" href="${ctx}/role/list.do?page=${page}" role="button">返回</a>
			</div>
			<c:choose>
				<c:when test="${role.id!=null}">
			    	<h5>编辑-<strong>${role.name}</strong></h5>
			   	</c:when>
			    <c:otherwise>
			    	<h5>新增<strong>${role.name}</strong></h5>
			   	</c:otherwise>
			</c:choose>
  		</div>
  		<div class="panel-body">
    		
			<form:form id="roleForm" name="roleForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="id" name="id" type="hidden" value="${role.id}" >
				<input id="menuIds" name="menuIds" type="hidden" value="${role.menuIds}" >
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">角色名称</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="name" name="name" placeholder="角色名称" value="${role.name}">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="url" class="col-sm-2 control-label">权限菜单</label>
			    <div class="col-sm-4">
			    	<div class="input-group">
			    		 <input type="text" class="form-control" id="menuNames" name="menuNames" readonly placeholder="请选择权限菜单" value="${role.menuNames}" >
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button" data-toggle="modal" data-target="#mytree">选择<span class="caret"></span></button>
					      </span>
			    	</div>
			    </div>
			  </div>
			 <div class="form-group">
			    <label for="url" class="col-sm-2 control-label">备注</label>
			    <div class="col-sm-5">
			      <textarea id="remark" name="remark" class="form-control" rows="5">${role.remark}</textarea>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button id="savebtn" name="savebtn" type="button" class="btn btn-primary" 
			      		onclick="save('#roleForm', 'save.do', g_ctx + '/role/list.do?page=${page}')">保存</button>
			    </div>
			  </div>
			</form:form>
    		
  		</div>
</div>

<div class="modal fade" id="mytree" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
  <div class="modal-dialog">
    <div class="modal-content" style="width: 300px;">
      <div class="modal-header">
        <button type="button" class="close" data-dismiss="modal"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
        <h4 class="modal-title" id="myModalLabel">选择菜单</h4>
      </div>
      <div class="modal-body">
        <ul id="treeDemo" class="ztree"></ul>
      </div>
      <div class="modal-footer">
        <button type="button" class="btn btn-primary" onclick="select()">选择</button>
      </div>
    </div>
  </div>
</div>

<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<script src="${ctx}/js/common.js"></script>		
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	

<SCRIPT type="text/javascript">
		<!--
		var setting = {
			check: {
				enable: true
			},
			data: {
				simpleData: {
					enable: true
				}
			}
		};

		var zNodes =${menuTreeBeans};
		
		
		var zTree;
		$(document).ready(function(){
			zTree = $.fn.zTree.init($("#treeDemo"), setting, zNodes);
			
			
		});
		
		function select(){
			var nodes = zTree.getCheckedNodes(true);
			var ids = "";
			var menuNames = "";
			for(var i=0;i<nodes.length;i++){
				if(ids==""){
					ids = nodes[i].id;
					menuNames = nodes[i].name;
				}else {
					ids +=",";
					ids +=nodes[i].id;
					
					menuNames +=",";
					menuNames +=nodes[i].name;
				}
				
			}
			
			$("#menuIds").val(ids);
			$("#menuNames").val(menuNames);
			$('#mytree').modal('hide')
		}
		//-->
	</SCRIPT>	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

