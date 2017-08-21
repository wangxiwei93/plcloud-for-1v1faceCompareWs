<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>

<%@ include file="/WEB-INF/views/head_n.jsp" %>

  <style type="text/css">

	.home-btn-info{color:#fff;background-color:transparent; border-color:transparent;background-image: url("${ctx}/images/home_menu_btn.png");width: 100%;height:57px;color: white;
	font-family: 微软雅黑;font-size: 20px;line-height: 57px;cursor: pointer;text-align: center;}
	.home-btn-info.active,.home-btn-info.focus,.home-btn-info:active,.home-btn-info:focus,.home-btn-info:hover,.open>.dropdown-toggle.home-btn-info{color:#fff;background-color:transparent; border-color:transparent;background-image: url("${ctx}/images/home_menu_btn_active.png");}
  </style>
     

<div>
		<script type="text/javascript">
		function goUrl(url){
			document.location.href = '${ctx}'+url;
		}
		</script>
		<div class="container">
			
			<div class="container"  style="margin-top: 100px;width: 85%">
			平台首页
			</div>
			
		</div>
	</body>
</html>