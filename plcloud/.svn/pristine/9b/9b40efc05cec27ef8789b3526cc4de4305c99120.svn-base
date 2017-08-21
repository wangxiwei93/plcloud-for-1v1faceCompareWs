package com.routon.pmax.common.decorator;

import java.util.Calendar;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.TableDecorator;
import org.displaytag.pagination.PaginatedList;

/**
 * Does nothing. Simply needed to define a concrete class to test getListIndex() and getViewIndex() from the abstract
 * TableDecorator class
 * @author fgiust
 * @version $Revision: 1081 $ ($Author: fgiust $)
 */
public class PageDecorator extends TableDecorator
{
  /**
   * FastDateFormat used to format the date object.
   */
  //private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss"); //$NON-NLS-1$

  // just subclass TableDecorator
  /**
   * Creates a new Wrapper decorator who's job is to reformat some of the data located in our TestObject's.
   */
  public PageDecorator()
  {
	  super();
  }

  /**
   * 获得导出的文件名，注意：
   * exportFileNamePrefix 给导出的文件名加一个前缀，如"TerminalList"表示终端列表
   * exportFileNamePrefix 只能是英文，不支持中文名导出
   * exportFileNamePrefix 由外部jsp页面传入，如：
   * pageContext.setAttribute("exportFileNamePrefix", "TerminalList");
   */
  public static String getExportFileName(HttpServletRequest request, String exportFileNamePrefix)
  {
	  //取得当前时间。注意，不能是HH:mm:ss，否则文件名非法！
	  String curTime = FastDateFormat.getInstance("yyyy-MM-dd HH-mm-ss").format(Calendar.getInstance().getTime());
	  //取得当前页号
	  PaginatedList pageList = (PaginatedList)(request.getAttribute("pageList"));
	  int pageNumber = pageList.getPageNumber();
	  //拼文件名
	  String exportFileName = exportFileNamePrefix+"_"+curTime+"_"+pageNumber+".xls";
	  
	  return exportFileName;
  }

}
