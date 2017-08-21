package com.routon.pmax.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * <p>
 * Title: AjaxUtil
 * </p>
 * <p>
 * Description: 定义Ajax请求处理的工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company: Routon Electronic Co.,Ltd.
 * </p>
 * <p>
 * Date: 2013-5-2
 * </p>
 * 
 * @author zhouzhihui
 * @version 1.0
 */
public class AjaxUtil {
	/**
	 * 判断当前请求是否是Ajax请求
	 * 
	 * @param requestedWith
	 *            请求头中的串
	 * @return 是返回true，反之返回false
	 */
	public static boolean isAjaxRequest(String requestedWith) {
		return requestedWith != null ? "XMLHttpRequest".equals(requestedWith) : false;
	}

	/**
	 * 判断当前请求是否是Ajax上传
	 * 
	 * @param request
	 *            HTTP请求
	 * @return 是返回true，反之返回false
	 */
	public static boolean isAjaxUploadRequest(HttpServletRequest request) {
		return request.getParameter("ajaxUpload") != null;
	}

}
