<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>


<script type="text/javascript" src="${ctx}/js/pagination.js"></script>
<c:if  test="${maxpage > 0}">
 
<div class="panel-footer"  style="padding-top: 7px;padding-bottom: 3px;padding-left: 10px;background-color:#f5f5f5;border-bottom-right-radius: 3px;border-bottom-left-radius: 3px; " >
		
			<div class="btn-group">
				<div class="btn-group" style="margin-right: 6px;">
				  <ul class="pagination" style="margin-bottom: 0px;margin-top: 0px;">
					<c:choose> 
						<c:when test="${page > 1}">  
							<li><a href="javascript:void(0);" onclick="gotopage(${page-1},${page},${maxpage})"  >&laquo;</a></li>
						</c:when>
						<c:otherwise>
							
							<li class="disabled" ><a href="javascript:void(0);" onclick="gotopage(${page-1},${page},${maxpage})"  >&laquo;</a></li>
							
						</c:otherwise>
					</c:choose>
					
					<c:choose> 
						<c:when test="${maxpage - page >= 5}">  
							<c:set var="j" value="1"></c:set>
							<c:forEach var="i" begin="${page}" end="${maxpage}">
								<c:choose> 
									<c:when test="${j == 6}">  
									
										<li><span class="dot">...</span></li>
										<li><a href="javascript:void(0);" onclick="gotopage(${maxpage},${page},${maxpage})" >${maxpage}</a></li>
									</c:when>
									<c:when test="${j < 6}">  
										<c:choose> 
											<c:when test="${j==1}">  
												<li class="active" ><a href="javascript:void(0);"  >${i}</a></li>
											</c:when>
											<c:otherwise>
												<li><a href="javascript:void(0);" onclick="gotopage(${i},${page},${maxpage})" >${i}</a></li>
											</c:otherwise>
										</c:choose>
										
									</c:when>
								</c:choose>
								
								<c:set var="j" value="${j+1}"></c:set>  
							</c:forEach>  
						</c:when>
						<c:otherwise>
							<c:forEach var="i" begin="${(maxpage - 4)<=0?1:(maxpage - 4)}" end="${maxpage}">
								<c:choose> 
									<c:when test="${i==page}">  
										<li class="active"><a href="javascript:void(0);"  >${i}</a></li>
									</c:when>
									<c:otherwise>
										<li><a href="javascript:void(0);" onclick="gotopage(${i},${page},${maxpage})" >${i}</a></li>
									</c:otherwise>
								</c:choose>
							</c:forEach>
						</c:otherwise>
						</c:choose>
						
						<c:choose> 
							<c:when test="${page == maxpage}">  
								<li class="disabled"><a href="javascript:void(0);"   >&raquo;</a></li>
								
							</c:when>
							<c:otherwise>
								<li><a href="javascript:void(0);" onclick="gotopage(${page+1},${page},${maxpage})"  >&raquo;</a></li>
								
							</c:otherwise>
						</c:choose>
					
			   		</ul>
			   	</div>
			   	<div class="btn-group">
			   		<form class="form-inline" role="form" >  		
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">第</label>
		  			</div>
		  			<div class="btn-group input-group-sm">
		  				<input id="pagination_page" name="pagination_page" type="text" class="form-control"  style="width: 40px;">
		  			</div>
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">页</label> 
		  			</div>
		  			<div class="btn-group">
		  				<button type="button" class="btn btn-primary" onclick="gotopage(null,${page},${maxpage})">确定</button>
		  			</div>
		  			<div class="btn-group">
		  				<label for="exampleInputEmail1">共${pageList.totalCount}条记录</label>
		  			</div>
		  			</form>
			   	</div>	
			   	
			</div>
			<div class="pull-right">
	   			<div class="btn-group">
	   				<button type="button" class="btn btn-primary" onclick="export_excel()">导出excel</button>
	   			</div>	
	   		</div>
	<!-- 
	<div class="pull-right">
	   			<div class="btn-group">
	   				<button type="button" class="btn btn-primary" onclick="export_excel()">导出excel</button>
	   			</div>	
	   		</div>
		
		   	<span>第</span>
		   	<input id="pagination_page" name="pagination_page" type="text" class="num">
		   	<span>页</span>
		   	<a href="javascript:;" class="pager-btn" onclick="gotopage(null,${page},${maxpage})"  >确定</a>
    -->
	
</div> 
 	</c:if>

  
