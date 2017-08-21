package com.routon.pmax.common.service;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

/**
 * 
 * @author J.
 *
 */
@Service
public class MessageServiceImpl {
	
	private Logger logger = LoggerFactory.getLogger(MessageServiceImpl.class);
	
	@Resource(name="messageSource")
	private	MessageSource messageSource;
	
	public String getOneMessage(BindingResult bindingResult){
		
		List<ObjectError> errs = bindingResult.getAllErrors();
		if (errs != null && errs.size() > 0){
			return getMessage(errs.get(0));	
		}
		
		return "";
	}
	
	public String getOneMessage(BindingResult bindingResult, String ...fields){
		for (String f : fields){
			FieldError fe = bindingResult.getFieldError(f);
			if (fe != null){
				return fe.getDefaultMessage();
			}
		}
		
//		List<ObjectError> errs = bindingResult.getAllErrors();
//		if (errs != null && errs.size() > 0){
//			return getMessage(errs.get(0));	
//		}
//		
		return "";
	}
	
	public String getMessage(ObjectError err){
		
		String result = null;
			
		String[] codes = err.getCodes();
		
		if (codes != null){
			for (String code : codes) {
				try{
					result = messageSource.getMessage(code, null, null);
					break;
				}
				catch(NoSuchMessageException e){
					logger.warn("未定义消息: {}", code);
					continue;
				}
			}
		}
		
		return result != null ? result : err.getDefaultMessage();		
	}	
	
}
