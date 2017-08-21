package com.routon.pmax.common.decorator;

import javax.servlet.jsp.PageContext;

import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;

/**
 * <p>
 * Title:PageIntegerDecorator
 * </p>
 * <p>
 * Description:Page Integer Decorator
 * 
 * @author: ldz
 * @version 1.0
 */
public class PageIntegerDecorator implements DisplaytagColumnDecorator {

	/**
	 * decorate
	 * 
	 * @param columnValue
	 * @param pageContext
	 * @param media
	 */
	public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException {
		// TODO Auto-generated method stub
		
		if(columnValue != null) {
	        
			try {
				
				return Integer.parseInt(columnValue.toString());
			}
			catch (NumberFormatException e) {
				
				return columnValue.toString();
			}
	    
		}
		else {
			
	        return null; 
	    }    
	}
}

