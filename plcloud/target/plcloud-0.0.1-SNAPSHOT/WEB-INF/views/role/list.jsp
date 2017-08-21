<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>

	<div class="panel panel-default">
  		<div class="panel-heading">
  			<div class="btn-group">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/role/list.do"  method="post">  		
  			<div class="btn-group">
  				<input id="name" name="name" type="text" class="form-control" placeholder="请输入角色名称" value="${name}">
  				
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >查询</button>
  			</div>
  			</form>
  			</div>
			<div class="pull-right">
				<c:if test="${(!empty userPrivilege['40000201'])}">
				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="gotourl('${ctx}/role/add.do?page=${page}')">新增</button>
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000202'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
  					
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000203'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-danger" onclick="deleteRole()">删除</button>
  				</div>
  				</c:if>
  			</div> 
  		</div>
  		
		<!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.pmax.common.decorator.PageDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.pmax.common.decorator.PageCheckboxDecorator" media="html" style="width:2%;"/>
			<display:column title="ID"  property="id"  sortable="true"  style="width:5%;" />
			<display:column title="角色名称" sortable="true"  property="name" style="width:15%;">
			</display:column>
			<display:column title="创建时间"  property="createTime"  sortable="true"  style="width:20%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modifyTime"  sortable="true"  style="width:20%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
		</display:table>
 		
	</div>	
	<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
	
	
	
<script src="${ctx}/js/common.js"></script>
<SCRIPT type="text/javascript">
function edit(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds==""){
		alert("请选择一个进行编辑!");
		return false;
	}
	var selectedId = selectedIds.split(",");
	if (selectedId.length != 1) {
		alert("请选择一个进行编辑!");
		return false;
	}
	gotourl('${ctx}/role/edit.do?page=${page}&id='+selectedIds);
}
function deleteRole(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds == ""){
		alert("至少选择一个删除!");
		return false;
	}
	del(selectedIds,'${ctx}/role/delete.do', g_ctx + '/role/list.do?page=${page}')
}
</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

