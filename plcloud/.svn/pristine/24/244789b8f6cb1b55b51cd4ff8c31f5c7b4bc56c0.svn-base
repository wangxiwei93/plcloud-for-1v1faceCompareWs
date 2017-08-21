<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>

	<div class="panel panel-default">
  		<div class="panel-heading">
  			<div class="btn-group">
  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/user/list.do"  method="post">  		
  			<div class="btn-group">
  				<input id="userName" name="userName" type="text" class="form-control" placeholder="请输入用户登录名称" value="${userName}">
  			</div>
  			<div class="btn-group">
  				<input id="realName" name="realName" type="text" class="form-control" placeholder="请输入用户真实姓名" value="${realName}">
  			</div>
  			<div class="btn-group">
  				<button type="submit" class="btn btn-primary" >查询</button>
  			</div>
  			</form>
  			</div>
			<div class="pull-right">
				<c:if test="${(!empty userPrivilege['40000101'])}">
				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="gotourl('${ctx}/user/add.do?page=${page}')">新增</button>
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000102'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="edit()">编辑</button>
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000103'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-primary" onclick="reSetPwd()">重置密码</button>
  				</div>
  				</c:if>
  				<c:if test="${(!empty userPrivilege['40000104'])}">
  				<div class="btn-group">
  					<button type="button" class="btn btn-danger" onclick="deleteUser()">删除</button>
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
			<display:column title="登录名" sortable="true"  property="userName" style="width:10%;">
			</display:column>
			<display:column title="姓名" sortable="true"  property="realName" style="width:10%;">
			</display:column>
			<display:column title="公司" sortable="true"  property="company" style="width:10%;">
			</display:column>
			<display:column title="电话" sortable="true"  property="phone" style="width:10%;">
			</display:column>
			<display:column title="创建时间"  property="createTime"  sortable="true"  style="width:20%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="修改时间"  property="modifyTime"  sortable="true"  style="width:20%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="状态" sortable="true"  sortProperty="status" style="width:10%;">
				<c:choose>
					<c:when test="${curPage.status == 1}">
					有效
					</c:when>
				</c:choose>
				<c:choose>
					<c:when test="${curPage.status == 0}">
					无效
					</c:when>
				</c:choose>
			</display:column>
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
	gotourl('${ctx}/user/edit.do?page=${page}&id='+selectedIds);
}

function reSetPwd(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds == ""){
		alert("至少选择一个用户重置密码!");
		return false;
	}
	if(confirm("确定重置密码吗?")) {
		var querydata = {};
		querydata.id = selectedIds;
		$.ajax({ 
			type        : "POST"
			,url         : "${ctx}/user/resetPwd.do"
			,data        : querydata
			,contentType : "application/x-www-form-urlencoded;charset=utf-8;"
			,dataType    : "json"
			,cache		  : false	
			,success: function(info) {
				
				if (info.code == 1) {
					alert("密码重置成功!");
				}
				else if (info.code == 0) {
					alert(info.msg);
				}
				else if (info.code == -1) {
					alert("密码重置异常!");
				}					
			}
			,error : function(XMLHttpRequest, textStatus, errorThrown) {    
				alert(XMLHttpRequest.status + textStatus);    
			} 	
		}
		);
	}
	
}

function deleteUser(){
	var selectedIds = getCheckedRowValue("");
	if(selectedIds == ""){
		alert("至少选择一个删除!");
		return false;
	}
	del(selectedIds,'${ctx}/user/delete.do', g_ctx + '/user/list.do?page=${page}');
}
</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

