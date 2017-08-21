package com.routon.pmax.common.decorator;

import java.util.Date;
import javax.servlet.jsp.PageContext;
import org.apache.commons.lang.time.FastDateFormat;
import org.displaytag.decorator.DisplaytagColumnDecorator;
import org.displaytag.exception.DecoratorException;
import org.displaytag.properties.MediaTypeEnum;


/**
 * Simple column decorator which formats a date.
 * @author epesh
 * @author Fabrizio Giustina
 * @version $Revision$ ($Author$)
 */
public class PageDateDecorator implements DisplaytagColumnDecorator
{

    /**
     * FastDateFormat used to format the date object.
     */
    private FastDateFormat dateFormat = FastDateFormat.getInstance("yyyy-MM-dd"); //$NON-NLS-1$

    /**
     * transform the given object into a String representation. The object is supposed to be a date.
     * @see org.displaytag.decorator.DisplaytagColumnDecorator#decorate(Object, PageContext, MediaTypeEnum)
     */
    public Object decorate(Object columnValue, PageContext pageContext, MediaTypeEnum media) throws DecoratorException
    {
	      //！！！奇怪：DB上定义的Datetime列，（极少数）怎么有可能columnValue是String而不是Date，见TerminalFault.jsp
    	if(columnValue!=null)
    	{
    		if(columnValue instanceof Date){
    			return dateFormat.format((Date) columnValue);
    		}
    		else{
        	
    			return columnValue;
  
    		}
    	}      
    	else{
    		return null;
    	}	  
    }
}
