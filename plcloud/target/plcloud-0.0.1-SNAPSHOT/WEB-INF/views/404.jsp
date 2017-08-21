<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>
<style type="text/css">
<!--
div.content {
	text-align: center;
	font-size: 24px;
}

div.content h1 {
	color: red;
	font-size: 24px;
	font-weight: bolder;
}

div.content p,div.content a {
	margin: 10px 0;
	font-size: 24px;
}
-->
</style>
	<div class="content">
		<h1>404 资源未找到</h1>
		<p>
			对不起，你请求的资源不存在，<a href="${ctx}/">返回主页</a>。
		</p>
	<!-- end .content --></div>
<script src="${ctx}/js/common.js"></script>
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>