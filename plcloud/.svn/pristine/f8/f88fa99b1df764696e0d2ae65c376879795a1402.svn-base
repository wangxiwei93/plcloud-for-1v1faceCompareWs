package com.routon.pmax.common.decorator;

import javax.servlet.jsp.PageContext;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.pagination.PaginatedList;
import org.displaytag.properties.MediaTypeEnum;


/**
* 用于丰富displaytag列显示的功能，在表格中显示一列 序号，各页连续递增
*/
public class PageRownumDecorator implements DisplaytagColumnDecorator {

	/**
     * columnValue表示被包装的该列定义的rowNum属性给定的值：viewIndex、listIndex
	 * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
	 * org.displaytag.properties.MediaTypeEnum)
	 */
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
	{
		//如果是页面展现
		if(media.getCode()==MediaTypeEnum.HTML.getCode())
		{
		    PaginatedList pageList = (PaginatedList)(pageContext.getRequest().getAttribute("pageList"));
			int pageNumber = pageList.getPageNumber();
			int pageSize = pageList.getObjectsPerPage();
			int curPage_rowNum = ((Integer)columnValue).intValue();
			int rowNum = (pageNumber-1) * pageSize + curPage_rowNum + 1;
      
			return ( new Integer(rowNum) );
		}

		//如果是导出：导出所有记录，不分页
		else
		{
			int listIndex = ((Integer)columnValue).intValue() + 1;
      
			return ( new Integer(listIndex) );
		}
	}
}

