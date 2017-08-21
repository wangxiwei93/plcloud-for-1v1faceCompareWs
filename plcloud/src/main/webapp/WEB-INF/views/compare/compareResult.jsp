<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net" %>
<%@ include file="/WEB-INF/views/head_n.jsp" %>
<div class="panel panel-default">
	<h3>人脸1:1比对，请选择两张照片：</h3>
	<label class="control-label"></label>
	<input id = "input-1" name="image1" type="file" multiple class="file"><br>
	<input id = "input-2" type="text" class="form-control" required placeholder="设置比对阈值,分值范围：0-1的小数"><br>
	<button id ="compareButton" type="button" class="btn btn-danger" onclick="">开始比对</button>
	<br>
	<div id="display">
		<h4>比对分数：</h4>
		<div id = "scores" style="color:#FF0000"></div>
		<h4>比对结果：</h4>
		<div id = "result" style="color:#FF0000"></div>
	</div>
</div>
<script>

$("#compareButton").click(function() {
	var threshold = $("#input-2").val();
	if(threshold == ""){
		alert("您未填写阈值！");
		return;
	}
	$.ajax({
		url:'${base}compare/getImageNum.do',
		type:'post',
		async:false,
		beforeSend:function(){
			 /* document.write(_LoadingHtml);  */
			$("#compareButton").attr("disabled","true");
			$("#display").html('<img src="../images/loading.gif"/>');
		},
		success:function(data){
/* 	        var loadingMask = document.getElementById('loadingDiv');
	        loadingMask.parentNode.removeChild(loadingMask); */
			if(data == 0){
				alert("请先上传两张照片！");
				$("#compareButton").removeAttr("disabled");
				$("#display").html('<h4>比对分数：</h4><div id = "scores" style="color:#FF0000"></div><h4>比对结果：</h4><div id = "result" style="color:#FF0000"></div>');
				return;
			} else{
				jQuery.post("${base}compare/TwoImagePath.do", 
						{'threshold' : threshold}, 
						function(data, textStatus) {
							$("#compareButton").removeAttr("disabled");
							$("#display").html('<h4>比对分数：</h4><div id = "scores" style="color:#FF0000"></div><h4>比对结果：</h4><div id = "result" style="color:#FF0000"></div>');
							var scores = data.scores;
							var text = data.text;
							$("#scores").html(scores);
							$("#result").html(text);
						}, "json");
			}
		}
	});		
});

//初始化fileinput
var FileInput = function () {
	var oFile = new Object();
	//初始化fileinput控件（第一次初始化）
	oFile.Init = function(ctrlName, uploadUrl) {
	var control = $('#' + ctrlName);
	var uploadUrl = '${base}compare/scores.do';
	//初始化上传控件的样式
	control.fileinput({
		language: 'zh', //设置语言
		uploadUrl: uploadUrl, //上传的地址
		allowedFileExtensions: ['jpg', 'gif', 'png'],//接收的文件后缀
		showUpload: true, //是否显示上传按钮
		showCaption: false,//是否显示标题
		browseClass: "btn btn-primary", //按钮样式	 
		//dropZoneEnabled: false,//是否显示拖拽区域
		//minImageWidth: 50, //图片的最小宽度
		//minImageHeight: 50,//图片的最小高度
		//maxImageWidth: 1000,//图片的最大宽度
		//maxImageHeight: 1000,//图片的最大高度
		maxFileSize: 2048,//单位为kb，如果为0表示不限制文件大小
		//minFileCount: 0,
		maxFileCount: 2, //表示允许同时上传的最大文件个数
		enctype: 'multipart/form-data',
		validateInitialCount:true,
		previewFileIcon: "<i class='glyphicon glyphicon-king'></i>",
		msgFilesTooMany: "选择上传的文件数量({n}) 超过允许的最大数值{m}！",
	});
	//导入文件上传完成之后的事件
	$("#input-1").on("fileuploaded", function (event, data, previewId, index) {
		var response = data.response;
		if (response == 2) {
			return;
		}
	});
}
	return oFile;
};

$(function () {
/* 	document.getElementById("div").style.display=""; */
    //0.初始化fileinput
    var oFileInput = new FileInput();
    oFileInput.Init("input-1", "/api/OrderApi/ImportOrder");
});
</script>
<script src="${ctx}/js/base-loading.js"></script>
<script src="${ctx}/js/fileinput.min.js"></script>
<script src="${ctx}/js/zh.js"></script>
<%@ include file="/WEB-INF/views/foot_n.jsp" %>