<%@page import="org.springframework.web.context.WebApplicationContext"%>
<%@page import="org.springframework.web.context.ContextLoader"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
//System.out.println("server ip:"+basePath);
pageContext.setAttribute("ctx", path);
pageContext.setAttribute("base", basePath);
WebApplicationContext webApplicationContext =  ContextLoader.getCurrentWebApplicationContext();
Properties properties = (Properties) webApplicationContext.getBean("globalProperties");
String terminal_barcode_enable = properties.getProperty("terminal_barcode_enable", "false");
pageContext.setAttribute("terminal_barcode_enable", terminal_barcode_enable.equals("true")?true:false);
String terminal_secretKey_enable = properties.getProperty("terminal_secretKey_enable", "false");
pageContext.setAttribute("terminal_secretKey_enable", terminal_secretKey_enable.equals("true")?true:false);
String terminal_default_secretKey = properties.getProperty("terminal_default_secretKey", "11111111");
pageContext.setAttribute("terminal_default_secretKey", terminal_default_secretKey);

%>

<!DOCTYPE html>
<html lang="zh-cn">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1">
    <meta http-equiv="X-UA-Compatible" content="IE=9" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="shortcut icon" href="../../docs-assets/ico/favicon.png">

    <title>云管理平台</title>

    <!-- Bootstrap core CSS 
    <link rel="stylesheet" href="http://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css">
     <link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
-->
	<link rel="stylesheet" href="${ctx}/css/bootstrap.min.css">
	<link rel="stylesheet" href="${ctx}/css/fileinput.min.css">
	
    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
      <script src="http://cdn.bootcss.com/html5shiv/3.7.0/html5shiv.min.js"></script>
      <script src="http://cdn.bootcss.com/respond.js/1.3.0/respond.min.js"></script>
    <![endif]-->
    
    <!-- Placed at the end of the document so the pages load faster 
    <script src="http://cdn.bootcss.com/jquery/1.11.0/jquery.min.js"></script>
    <script src="http://cdn.bootcss.com/bootstrap/3.0.0/js/bootstrap.min.js"></script>-->
    
   
  	<script src="${ctx}/js/jquery.min.js"></script>
  	<script src="${ctx}/js/bootstrap.min.js"></script>
    
    <script src="${ctx}/js/jquery.form.js"></script>
    <script src="${ctx}/js/jquery.enplaceholder.js"></script>
    <link rel="stylesheet" href="${ctx}/css/common.css">   
    
    <script type="text/javascript">
    	var g_ctx = '${ctx}';
    	$(document).ready(function(){
    		$('input').placeholder();
			
		});
    	
    </script>
     <style type="text/css">
     body{
     	font-family: 微软雅黑;
     }
        .dropdown-submenu {
            position: relative;
        }
        .dropdown-submenu > .dropdown-menu {
            top: 0;
            left: 100%;
            margin-top: -6px;
            margin-left: -1px;
            -webkit-border-radius: 0 6px 6px 6px;
            -moz-border-radius: 0 6px 6px;
            border-radius: 0 6px 6px 6px;
        }
        .dropdown-submenu:hover > .dropdown-menu {
            display: block;
        }
        .dropdown-submenu > a:after {
            display: block;
            content: " ";
            float: right;
            width: 0;
            height: 0;
            border-color: transparent;
            border-style: solid;
            border-width: 5px 0 5px 5px;
            border-left-color: #ccc;
            margin-top: 5px;
            margin-right: -10px;
            color: black;
        }
        .dropdown-submenu:hover > a:after {
            border-left-color: #fff;
            color:#00a3c2 ;
        }
        .dropdown-submenu.pull-left {
            float: none;
        }
        .dropdown-submenu.pull-left > .dropdown-menu {
            left: -100%;
            margin-left: 10px;
            -webkit-border-radius: 6px 0 6px 6px;
            -moz-border-radius: 6px 0 6px 6px;
            border-radius: 6px 0 6px 6px;
        }
        
       .navbar-default .navbar-nav>li>a{
		    font-family: 微软雅黑;font-size: 16px;color: black;
		}
	  .navbar-default .navbar-nav>li>a:hover,
	  .navbar-default .navbar-nav>li>a:focus {
	    color: #00a3c2;
	    background-color: transparent;
	  }
	  .navbar-default .navbar-nav>.open>a, .navbar-default .navbar-nav>.open>a:focus, .navbar-default .navbar-nav>.open>a:hover{
	  	color: #00a3c2;
	    background-color: transparent;
	  }
	  
	  .navbar-default .navbar-right>li>a{
		    font-family: 微软雅黑;font-size: 14px;color: #898989;
		}
	  .navbar-default .navbar-right>li>a:hover,
	  .navbar-default .navbar-right>li>a:focus {
	    color: #00a3c2;
	    background-color: transparent;
	  }
	  .navbar-default .navbar-right>.open>a, .navbar-default .navbar-right>.open>a:focus, .navbar-default .navbar-right>.open>a:hover{
	  	color: #00a3c2;
	    background-color: transparent;
	  }
	  
	  .dropdown-menu>li>a:hover,
	  .dropdown-menu>li>a:focus,{
	  	color: #00a3c2;
	  }
        
    </style>
  </head>
  
  <body>

  <nav class="navbar navbar-default navbar-fixed-top" role="navigation" style="background-color: #e7e7e7;">
  <div class="container" > 
 

   
    
   	<div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target=".navbar-collapse" aria-expanded="false" aria-controls="navbar">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="${ctx}/home.do" style="font-family: 微软雅黑;font-size: 16px;color: black;">云管理平台</a>
    </div>
   	<div>
   	 <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="navbar-collapse collapse" >
      <ul class="nav navbar-nav">
		<c:forEach items="${mainmenus}" var="menu" varStatus="status">    
			
			<li style="padding-bottom: 15px;padding-top: 15px;"><span style="color: #00a3c2" >|</span></li>
			<li class="dropdown">
			<a href="#" class="" data-toggle="dropdown" >${menu.name}<span class="caret"></span></a>
	        <ul class="dropdown-menu" role="menu">
	        	<c:forEach items="${menu.systemmenus}" var="childmenu" varStatus="childstatus">    
	          		<c:choose>
	          			<c:when test="${fn:length(childmenu.systemmenus)>0}">
	          				<li class="dropdown-submenu">
				            	 <a href="javascript:;">${childmenu.name}</a>
				                 <ul class="dropdown-menu">
				                 	<c:forEach items="${childmenu.systemmenus}" var="submenu" varStatus="substatus">  
				                     	<li ><a href="${ctx}${submenu.path}">${submenu.name}</a></li>
				                     </c:forEach>
				                 </ul>
				            </li>
	          			</c:when>
	          			<c:otherwise>
	          				<li><a href="${ctx}${childmenu.path}">${childmenu.name}</a></li>
	          				<c:if test="${!childstatus.last && childmenu.code==2}">
	          				<li class="divider"></li>
	          				</c:if>
	          			</c:otherwise>
	          		</c:choose>
	            </c:forEach>
	        </ul>
	        </li>
	        <c:if test="${status.last}">
	        <li style="padding-bottom: 15px;padding-top: 15px;"><span style="color: #00a3c2" >|</span></li>
	        </c:if>
	        
	        
		</c:forEach>
      
        <%--
        
        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">终端管理 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
          	<li><a href="${ctx}/group/list.do">分组管理</a></li>
            <li><a href="${ctx}/terminal/list.do">终端列表</a></li>
            <li><a href="${ctx}/resource/list/4.do">终端程序</a></li>
          </ul>
        </li>
        
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">内容管理 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${ctx}/resource/list/3.do">APP应用</a></li>
            <li class="divider"></li>
            <li class="dropdown-submenu">
            	 <a href="javascript:;">开机广告</a>
                 <ul class="dropdown-menu">
                     <li ><a href="${ctx}/resource/list/2.do">开机图片</a></li>
                     <li ><a href="${ctx}/resource/list/1.do">开机视频</a></li>
                 </ul>
            </li>
          </ul>
        </li>
         <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">公告管理 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${ctx}/notice/list.do">公告列表</a></li>
          </ul>
        </li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">系统管理 <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${ctx}/user/list.do">用户管理</a></li>
            <li><a href="${ctx}/role/list.do">角色管理</a></li>
            <li class="divider"></li>
            <li><a href="#">操作日志</a></li>
          </ul>
        </li>
     
  --%>
   		</ul>
      <ul class="nav navbar-nav navbar-right">
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown">当前用户:${userProfile.currentUserRealName} <span class="caret"></span></a>
          <ul class="dropdown-menu" role="menu" style="min-width: 120px;" >
            <li style="width: 120px;"><a href="javascript:void(0);" onclick="openChangePwdModal()"><span class="glyphicon glyphicon-lock" style="top: 2px;margin-right: 2px;"></span><span >修改密码</a></span></li>
            <li class="divider" style="width: 120px;"></li>
            <li style="width: 120px;"><a href="${ctx}/logout.do"><span class="glyphicon glyphicon-off" style="top: 2px;margin-right: 2px;"></span><span >安全退出</a></span></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>
<%@ include file="/WEB-INF/views/common/pwdModal.jsp" %>   
<div class="container">
  	
 

