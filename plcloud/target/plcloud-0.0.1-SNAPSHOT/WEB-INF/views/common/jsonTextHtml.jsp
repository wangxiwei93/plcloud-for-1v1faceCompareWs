<%@page import="com.routon.pmax.common.utils.JsonMsgBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@page import="net.sf.json.JSONObject"%>
<%	
	JsonMsgBean jsonMsg = (JsonMsgBean)request.getAttribute("jsonMsg");
	
	String json = "";
	
	if (jsonMsg != null){
		json = JSONObject.fromObject(jsonMsg).toString();	
	}

	response.setContentType("text/html;charset=UTF-8");            
	response.getOutputStream().write(json.getBytes("utf8"));
	response.getOutputStream().flush();
	
	out.clear();
	out = pageContext.pushBody();

%>