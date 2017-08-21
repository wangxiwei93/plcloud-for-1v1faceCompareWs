<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">


<div class="panel panel-default">
  		<div class="panel-heading">
			<div class="pull-right">
				<a class="btn btn-primary" href="${ctx}/user/list.do?page=${page}" role="button">返回</a>
			</div>
			<c:choose>
				<c:when test="${user.id!=null}">
			    	<h5>编辑-<strong>${user.userName}</strong></h5>
			   	</c:when>
			    <c:otherwise>
			    	<h5>新增</h5>
			   	</c:otherwise>
			</c:choose>
  		</div>
  		<div class="panel-body">
    		
			<form:form id="userForm" name="userForm" class="form-horizontal" role="form" method="post" enctype="multipart/form-data" >
				<input id="id" name="id" type="hidden" value="${user.id}" >
				<input id="groupIds" name="groupIds" type="hidden" value="${user.groupIds}" >
				
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">登录名</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="userName" name="userName" placeholder="登录名" value="${user.userName}">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">姓名</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="realName" name="realName" placeholder="用户姓名" value="${user.realName}">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">电话</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="phone" name="phone" placeholder="电话" value="${user.realName}">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="title" class="col-sm-2 control-label">公司</label>
			    <div class="col-sm-4">
			      <input type="text" class="form-control" id="company" name="company" placeholder="公司" value="${user.company}">
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="url" class="col-sm-2 control-label">权限角色</label>
			    <div class="col-sm-5">
			    	<c:forEach items="${roles}" var="role" varStatus="wl"> 
				    	<label class="checkbox-inline" style="margin-left: 0px;margin-right: 10px;">
				    		<c:choose>
				    			<c:when test="${role.checked }">
				    				<input type="checkbox" id="roleId${wl.index}" checked="checked" name="roleIds" value="${role.id}">${role.name}
				    			</c:when>
				    			<c:otherwise>
				    				<input type="checkbox" id="roleId${wl.index}" name="roleIds" value="${role.id}">${role.name}
				    			</c:otherwise>
				    		</c:choose>
						  
						</label>
			    	</c:forEach>
			    </div>
			  </div>
			  <div class="form-group">
			    <label for="url" class="col-sm-2 control-label">权限分组</label>
			    <div class="col-sm-4">
			    	<div class="input-group">
			    		 <input type="text" class="form-control" id="group_texts" name="group_texts" readonly placeholder="请选择分组" value="${user.group_texts}" >
					      <span class="input-group-btn">
					        <button class="btn btn-default" type="button" onclick="openGroupTreeModal()" >选择<span class="caret"></span></button>
					      </span>
			    	</div>
			    </div>
			  </div>
			  <div class="form-group">
			    <div class="col-sm-offset-2 col-sm-10">
			      <button id="savebtn" name="savebtn" type="button" class="btn btn-primary" 
			      		onclick="save('#userForm', 'save.do', g_ctx + '/user/list.do?page=${page}')">保存</button>
			    </div>
			  </div>
			</form:form>
    		
  		</div>
</div>



<%@ include file="/WEB-INF/views/common/myModal.jsp" %>
<script src="${ctx}/js/common.js"></script>		
<script src="${ctx}/js/jquery.ztree.all-3.5.min.js"></script>	
<%@ include file="/WEB-INF/views/common/groupModal.jsp" %>

<SCRIPT type="text/javascript">

function openGroupTreeModal() {
	var zNodes_group =${groupTreeBeans};
	var setting_group = {
		check : {
			enable : true

		},
		data : {
			simpleData : {
				enable : true
			}
		}
	};
	openGroupTree("groupIds","group_texts",setting_group,zNodes_group);
}
</SCRIPT>	

<%@ include file="/WEB-INF/views/foot_n.jsp" %>

