package com.routon.pmax.common.utils;

import java.util.Iterator;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

/**
 * 
 * @author J.
 *
 */
public class FileUtil {
	
	public static MultipartFile getFile(MultipartHttpServletRequest multipartRequest){
		
		return getFile(multipartRequest, null);
	}
	
	public static MultipartFile getFile(MultipartHttpServletRequest multipartRequest, String inputName){		
		
		Iterator<String> iter = multipartRequest.getFileNames();
		while (iter.hasNext()) {
			MultipartFile file = multipartRequest.getFile((String) iter.next());			
			if (file != null && file.getSize() > 0) {
				if (inputName == null){
					return file;
				}
				else if (inputName.equalsIgnoreCase(file.getName())){
					return file;
				}
			}
		}
		
		return null;
	}	
	
	public static MultipartFile[] getFiles(MultipartHttpServletRequest multipartRequest, String[] inputNames){		
		
		MultipartFile[] rs = new MultipartFile[inputNames.length];
		
		Iterator<String> iter = multipartRequest.getFileNames();
		while (iter.hasNext()) {

			MultipartFile file = multipartRequest.getFile((String) iter.next());
			
			if (file != null && file.getSize() > 0) {
				
				for (int i=0; i<inputNames.length; i++){
					if (inputNames[i].equalsIgnoreCase(file.getName())){
						rs[i] = file;
					}		
				}
				
			}
		}
		
		return rs;
	}
	
	public static String getFilenameExt(String path){
		
		if (path == null) {
			return "";
		}
		int sepIndex = path.lastIndexOf(".");
		return (sepIndex != -1 ? path.substring(sepIndex + 1) : "");
	}
	
	public static String getFilenameExtWithDot(String path){
		
		if (path == null) {
			return "";
		}
		int sepIndex = path.lastIndexOf(".");
		return (sepIndex != -1 ? path.substring(sepIndex) : "");
	}	
	
}

