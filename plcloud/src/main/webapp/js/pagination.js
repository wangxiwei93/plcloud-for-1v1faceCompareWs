function gotopage(pageIndex,currentPage,maxpage) {
	
	if (pageIndex == null) {
		pageIndex = $("#pagination_page").val();
		
		if (pageIndex == "" || !isNumber(pageIndex) || pageIndex < 1) {
			$("#pagination_page").val("");
			$("#pagination_page")[0].focus();
			return false;
		}
		
	}
	
	if (pageIndex != currentPage) {
		
		if (pageIndex > maxpage) {
			pageIndex = maxpage;
		}
		
		if (pageIndex != currentPage) {
			//alert(basePath + $("#select_page")[0].options[pageIndex-1].value);
			document.location.href = $("#select_page")[0].options[pageIndex-1].value;
		}
		
		
	} 
	
}

function export_excel(){
	var url = $("#excel_export_zl").parent()[0].href +'&exportflag=true';
	document.location.href = url;
}