<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>

<%-- <jsp:include page="/WEB-INF/views/compare/OcxTest.html" /> --%>
<font size="4">摄像头区域:</font>
<div style="border-style:solid; border-width:1px; border-color:#000; WIDTH:404px; HEIGHT:303px">
	<OBJECT ID="LivingDetect_ocx" WIDTH=400 HEIGHT=300 CLASSID="CLSID:B850D5E6-B30E-4A49-A49B-CA685198EACF">
    	<PARAM NAME="_Version" VALUE="65536">
    	<PARAM NAME="_ExtentX" VALUE="2646">
    	<PARAM NAME="_ExtentY" VALUE="1323">
    	<PARAM NAME="_StockProps" VALUE="0">
	</OBJECT>
</div>
<br><br>
<input type="button" value="检测摄像头设备" class="btn btn-sm btn-primary" onclick="refreshCameras()" />
<select id="cameraid" style="width:190px;height:25px"></select>
<input type="button" value="打开摄像头" class="btn btn-sm btn-primary" style="width:90px;" onClick="openCamera()">
<input type="button" value="关闭摄像头" class="btn btn-sm btn-primary" style="width:90px;" onClick="closeCamera()">
<input type="button" value="关于" style="width:75px;" class="btn btn-sm btn-danger" onClick="About()" />
<br><br>
<font size="3">检测项:</font>
<select id="checktype" class="selectpicker show-tick form-control" name="checktype">
    <option value="0">所有</option>
    <option value="1">眨眼</option>
    <option value="2">张嘴</option>
    <option value="3">点头</option>
    <option value="4">摇头</option>
</select>
<font size="3">超时时间:</font><input type="text" id="overtime" value="3000" style="width:70px"/>(ms)
<input type="checkbox" id="isShowImport" onClick="showImport()"><font size="2">显示关键点</font><br><br>
<input type="button" value="开始检测" class="btn btn-sm btn-primary" onClick="startCheck()">
<input type="button" value="停止检测" class="btn btn-sm btn-primary" onClick="stopCheck()">
<div id="checkResult" style="width:500px"><b>活体检测结果:</b></div>


<script language="javascript" for="LivingDetect_ocx" event="onChecked(type)" type="text/javascript">
    showResult(type);
</script>

<script>

function showResult(type)
{
    if (type == 1)
    {
        setCheckResult("<font color=\"#3F48FF\">已检测到眨眼</font>");
    }
    else if (type == 2)
    {
        setCheckResult("<font color=\"#3F48FF\">已检测到张嘴</font>");
    }
    else if (type == 3)
    {
        setCheckResult("<font color=\"#3F48FF\">已检测到点头</font>");
    }
    else if (type == 4)
    {
        setCheckResult("<font color=\"#3F48FF\">已检测到摇头</font>");
    }
    else if (type == 0)
    {
        setCheckResult("未检测到");
    }
}

function refreshCameras()
{
    var cameras = document.getElementById("cameraid");
    cameras.options.length = 0;
    var strCams = LivingDetect_ocx.getCameras();

    if (strCams.length > 0) {
        var strs = new Array();
        strs = strCams.split(";");
        for (i = 0; i < strs.length ; i++) {
            if (strs[i].length > 0) {
                var objOption = new Option(i + "." + strs[i], i);
                cameras.add(objOption, i);
            }
        }

        cameras.disabled = false;
    }
    else
    {
        var objOption = new Option("未找到摄像头设备", 0);
        cameras.add(objOption, 0);
        cameras.disabled = true;
    }
}

function hideOcx()
{
    LivingDetect_ocx.style.visibility = "hidden";

    refreshCameras();
}

function refreshCameras()
{
    var cameras = document.getElementById("cameraid");
    cameras.options.length = 0;
    var strCams = LivingDetect_ocx.getCameras();

    if (strCams.length > 0) {
        var strs = new Array();
        strs = strCams.split(";");
        for (i = 0; i < strs.length ; i++) {
            if (strs[i].length > 0) {
                var objOption = new Option(i + "." + strs[i], i);
                cameras.add(objOption, i);
            }
        }

        cameras.disabled = false;
    }
    else
    {
        var objOption = new Option("未找到摄像头设备", 0);
        cameras.add(objOption, 0);
        cameras.disabled = true;
    }
}

function About()
{
    //LivingDetect_ocx.AboutBox();
    var version = LivingDetect_ocx.getVersion();
    alert("版本 " + version);
}

function openCamera()
{
    var cameras = document.getElementById("cameraid");
    //alert(cameras.value);
    if (!cameras.disabled)
    {
        LivingDetect_ocx.openCamera(cameras.value);
        LivingDetect_ocx.style.visibility = "visible";
        setCheckResult("");
    }
    else
    {
        alert("没有可用的摄像头设备！");
    }
}

function closeCamera()
{
    var cameras = document.getElementById("cameraid");
    if (!cameras.disabled)
    {
        LivingDetect_ocx.style.visibility = "hidden";
        LivingDetect_ocx.closeCamera();
        setCheckResult("已关闭摄像头，并停止检测");
    }
}

function setCheckResult(strRet)
{
    var result = document.getElementById("checkResult");
    result.innerHTML = "<b>活体检测结果:" + strRet + "</b>";
}

function startCheck()
{
    var check = document.getElementById("checktype");
    var overtime1 = document.getElementById("overtime");
    var ret = LivingDetect_ocx.startCheck(check.value, overtime1.value); 
    if (ret == 0)
    {
        setCheckResult("正在检测中......");
    }
    else if (ret == -52)
    {
        setCheckResult("<font color=\"#FF0000\">活体检测初始化失败,未找到授权文件</font>");
    }
    else if (ret == -71) {
        setCheckResult("<font color=\"#FF0000\">活体检测初始化失败,未找到活体检测模型文件</font>");
    }
    else
    {
        setCheckResult("<font color=\"#FF0000\">活体检测初始化失败</font>");
    }
}

function stopCheck()
{
    LivingDetect_ocx.stopCheck();
    setCheckResult("已停止检测");
}

function showImport()
{
    var isShow = document.getElementById("isShowImport");
    LivingDetect_ocx.showImport(isShow.checked);
    //alert(isShow.checked);
}

$(function () {
    //初始化
    hideOcx();
}); 
</script>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>