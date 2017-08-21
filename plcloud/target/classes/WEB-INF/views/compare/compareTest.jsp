<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	pageContext.setAttribute("ctx", basePath);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>1v1compareTestPage</title>
</head>
<body>
 <form id="submit_form" method="post" action="${ctx}v2/facecompare1v1.do" target="exec_target" enctype="multipart/form-data">
     <input type="file" name="images" id="upload_file" multiple>
	 <input name="upload" type="submit" value="upload" />
 </form>
</body>
</html>