package com.routon.pmax.common.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * <p>
 * Title: PageCheckboxDecorator
 * </p>
 * <p>
 * Description: 用于丰富displaytag列显示的功能，在表格中显示一列checkbox
 * 
 * @version 1.0
 */
public class PageCheckboxDecorator implements DisplaytagColumnDecorator {

  /**
   * getTitle
   * @param pageContext
   * @return
   */
  public static String getTitle(PageContext pageContext)
  {
	  String checkboxSuffix = "";
	  
	  if(pageContext.getAttribute("checkboxSuffix") != null)
	  {
		  checkboxSuffix = pageContext.getAttribute("checkboxSuffix").toString();
	  }
	 
	  StringBuffer ostrTitle = new StringBuffer("<input type='checkbox' id='checkPage_"+checkboxSuffix+"' name='checkPage_"+ checkboxSuffix+"' value='' onclick='checkPageSelected_" + checkboxSuffix + "(this);' />");
	  
	  ostrTitle.append("<script language='javascript' type='text/javascript'>");
	  ostrTitle.append("function checkPageSelected_" + checkboxSuffix + "(obj){");
	  ostrTitle.append("var objs = document.getElementsByName('checkRow_" + checkboxSuffix + "');");
	  ostrTitle.append("var checked = obj.checked;");
	  ostrTitle.append("	for(var i=0;i < objs.length;i++){");
	  ostrTitle.append("		objs[i].checked = checked;");
	  ostrTitle.append("	}");
	  ostrTitle.append("}");
	  ostrTitle.append("   ");
	  ostrTitle.append("function checkRowSelected_" + checkboxSuffix + "(obj){");
	  ostrTitle.append("var objs = document.getElementsByName('checkRow_" + checkboxSuffix + "');");
	  ostrTitle.append("	if(!obj.checked){");
	  ostrTitle.append("		document.getElementById('checkPage_" + checkboxSuffix + "').checked = '';");
	  ostrTitle.append("		return;");
	  ostrTitle.append("	}else{");
	  ostrTitle.append("	document.getElementById('checkPage_" + checkboxSuffix + "').checked = 'checked';");
	  ostrTitle.append("	}");
	  ostrTitle.append("	for(var i=0;i < objs.length;i++){");
	  ostrTitle.append("		if(!objs[i].checked && objs[i] != obj){");
	  ostrTitle.append("			document.getElementById('checkPage_" + checkboxSuffix + "').checked = '';break;");
	  ostrTitle.append("		}");
	  ostrTitle.append("	}");
	  ostrTitle.append("}");
	  ostrTitle.append("</script>");
	 
	  return ostrTitle.toString();
  }
  
  
  /**
   * getTitle
   * @param pageContext
   * @return
   */
  public static String getTitleSelectOne(PageContext pageContext)
  {
	  String checkboxSuffix = "";
	  
	  if(pageContext.getAttribute("checkboxSuffix") != null)
	  {
		  checkboxSuffix = pageContext.getAttribute("checkboxSuffix").toString();
	  }
	 
	  StringBuffer ostrTitle = new StringBuffer("<input type='checkbox' id='checkPage_"+checkboxSuffix+"' disabled='disabled' name='checkPage_"+ checkboxSuffix+"' value='' onclick='checkPageSelected_" + checkboxSuffix + "(this);' />");
	  
	  ostrTitle.append("<script language='javascript' type='text/javascript'>");
	  ostrTitle.append("function checkPageSelected_" + checkboxSuffix + "(obj){");
	  ostrTitle.append("var objs = document.getElementsByName('checkRow_" + checkboxSuffix + "');");
	  ostrTitle.append("var checked = obj.checked;");
	  ostrTitle.append("	for(var i=0;i < objs.length;i++){");
	  ostrTitle.append("		objs[i].checked = checked;");
	  ostrTitle.append("	}");
	  ostrTitle.append("}");
	  ostrTitle.append("   ");
	  ostrTitle.append("function checkRowSelected_" + checkboxSuffix + "(obj){");
	  ostrTitle.append("var objs = document.getElementsByName('checkRow_" + checkboxSuffix + "');");
	  ostrTitle.append(" 	for(var i=0;i < objs.length;i++){");
	  //ostrTitle.append(" objs[i].checked = '';		if(!objs[i].checked && objs[i] != obj){" );
	  ostrTitle.append(" objs[i].checked = ''; ");
	  ostrTitle.append("			");
	  //ostrTitle.append("		}");
	  ostrTitle.append("	}");
	  ostrTitle.append("	if(!obj.checked){");
	  ostrTitle.append("		obj.checked = true;");
	  ostrTitle.append("		return;");
	  ostrTitle.append("	}else{");
	  ostrTitle.append("		obj.checked = false;");
	  ostrTitle.append("	}");
	
	  ostrTitle.append("}");
	  ostrTitle.append("</script>");
	 
	  return ostrTitle.toString();
  }

	/**
	* 该列全部显示为checkbox，其value为该列带有的ID
	* columnValue表示被包装的该列定义的property属性给定的值

	* @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(java.lang.Object, javax.servlet.jsp.PageContext,
	* org.displaytag.properties.MediaTypeEnum)
	*/
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
	{
		  String checkboxSuffix = "";
		 
		  if(pageContext.getAttribute("checkboxSuffix") != null)
		  {
			  checkboxSuffix = pageContext.getAttribute("checkboxSuffix").toString();
		  }
		  
		  String checkboxHtml = "<input type='checkbox' name='checkRow_"+checkboxSuffix+"' value='"+columnValue+"' onclick='checkRowSelected_" + checkboxSuffix + "(this)' ";
		  
		  String checkboxSeleted = "";
		 
		  if(pageContext.getAttribute("checkboxChecked") != null)
		  {
			  checkboxSeleted = pageContext.getAttribute("checkboxChecked").toString();
			  
			  if(checkboxSeleted.length() > 0 && !checkboxSeleted.substring(0,1).equals(","))
			  {
				  checkboxSeleted = "," + checkboxSeleted +",";
			  }
		  }
		  
		  if(checkboxSeleted.indexOf("," + columnValue + ",") >= 0)
		  {
			  checkboxHtml += " checked='checked'";
		  }
		  checkboxHtml += "/>";
		  
		  return checkboxHtml;

		//同理如果对于该列要显示为一个图片，可以返回”<input type=’image’ src=’…’ />”

	}

}

