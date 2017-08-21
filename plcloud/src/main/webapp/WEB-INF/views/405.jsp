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
		<h1>405 请求方法不允许：${pageContext.request.method}</h1>
		<p>
			对不起，请求中指定的请求方法不能被用于请求相应的资源，<a href="${ctx}/">返回主页</a>。
		</p>
	<!-- end .content --></div>
<script src="${ctx}/js/common.js"></script>
 	
<%@ include file="/WEB-INF/views/foot_n.jsp" %>