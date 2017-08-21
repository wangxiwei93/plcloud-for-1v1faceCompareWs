<%@page import="com.routon.pmax.common.decorator.PageCheckboxDecorator"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
	<link rel="stylesheet" href="${ctx}/css/zTreeStyle.css">
	<link rel="stylesheet" href="${ctx}/css/bootstrap-datetimepicker.css">
	<script src="${ctx}/js/bootstrap-datetimepicker.js"></script>
	<script src="${ctx}/js/locales/bootstrap-datetimepicker.zh-CN.js"></script>
	<div class="panel panel-default">
  		<div class="panel-heading " style="padding: 0px;">
  			<div class="" style="display: inline-block;width: 100%;">
  			
	  			<div class=" col-sm-8">
		  			<form class="form-inline" role="form" id="queryform" name="queryform" action="${ctx}/opLog/list.do"  method="post">  		
		  			<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
		  				<select id="type" name="type" class="form-control">
						  	<option value="0">全部</option>
						  	<c:forEach var="item" items="${opTypes}">
						  		
						   		<option value="${item.key}" <c:if test="${type==item.key}">selected="selected"</c:if> >${item.value}</option>
							</c:forEach>
						</select>
		  			</div>
		  			<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
			  			<div class="input-group date form_datetime">
						    <input size="20" type="text" id="startDate_createTime" name="startDate_createTime" value="${startDate_createTime}" class="form-control" readonly placeholder="请输入开始时间">
						    <span class="input-group-addon">
						    <span class="glyphicon glyphicon-calendar"></span>
						    </span>
						</div>
						
					</div>
					<div class="btn-group" style="margin-top: 5px;margin-bottom: 5px;">
			  			<div class="input-group date form_datetime">
						    <input size="20" type="text" id="endDate_createTime" name="endDate_createTime" value="${endDate_createTime}" class="form-control" readonly placeholder="请输入结束时间">
						    <span class="input-group-addon">
						    <span class="glyphicon glyphicon-calendar"></span>
						    </span>
						</div>
						
					</div>
					
		  			<div class="btn-group">
		  				<button type="submit" class="btn btn-primary" >查询</button>
		  			</div>
		  			</form>
	  			</div>
				
  			</div> 
  		</div>
  		
		<!-- table -->
		<display:table name="requestScope.pageList" id="curPage" class="table table-striped" sort="external"
			requestURI="list.do"
			decorator="com.routon.pmax.common.decorator.PageDecorator"
			export="false">
			<display:column property="id" title="<%=PageCheckboxDecorator.getTitle(pageContext)%>" decorator="com.routon.pmax.common.decorator.PageCheckboxDecorator"  style="width:2%;"/>
			
			<display:column title="操作" sortable="true" paramProperty="type"  style="width:9%;" >
			${curPage.object} 
			</display:column>
			<display:column title="内容"  property="log" sortable="true"  style="width:10%;" maxLength="50"></display:column>
			<display:column title="操作时间"  property="time"  sortable="true"  style="width:12%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="用户名"  property="userName"  sortable="true"  style="width:12%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="姓名"  property="realName" sortable="true"  style="width:12%;" decorator="com.routon.pmax.common.decorator.PageDateTimeDecorator"></display:column>
			<display:column title="IP"  property="ip" sortable="true"  style="width:10%;" ></display:column>
			
		</display:table>
 		
	</div>	
	<%@ include file="/WEB-INF/views/common/pagination.jsp" %>
	

	
<script src="${ctx}/js/common.js"></script>

<SCRIPT type="text/javascript">
$(".form_datetime").datetimepicker({
    format: "yyyy-mm-dd hh:ii:ss",
    autoclose: true,
    todayBtn: true,
   	clearBtn:true,
    language:'zh-CN',
    pickerPosition: "bottom-left"
});

</SCRIPT>	
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>

