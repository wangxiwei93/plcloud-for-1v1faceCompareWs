package com.routon.pmax.common.utils.web;


import java.awt.Color;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityCodeServlet extends HttpServlet { 

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6420359369288418515L;

	public void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException { 
		doPost(request, response); 
	} 
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException { 
		SecurityCode sc = SecurityCode.GenerateCode(4,false,false,false,false, Color.GREEN, new Color(32,155,31));
		HttpSession session=request.getSession();   
		session.setAttribute(SecurityCode.SK_CheckCode, sc.getCode()); 
		session.setAttribute(SecurityCode.SK_CheckCodeCreatTime, System.currentTimeMillis());
		
		//禁止图像缓存 
		response.setHeader("Pragma", "No-cache"); 
		response.setHeader("Cache-Control", "no-cache"); 
		response.setDateHeader("Expires", 0);   
		response.setContentType("image/jpeg"); 
		//创建二进制的输出流 
		ServletOutputStream sos=response.getOutputStream(); 
		// 将图像输出到Servlet输出流中。 
		ImageIO.write(sc.getImg(), "jpeg", sos); 
		sos.flush(); 
		sos.close(); 
	} 
	/*****************
	public Color getRandColor(int lower,int upper){ 
		Random random = new Random(); 
		if(upper>255) 
			upper=255; 
		if(upper<1) 
			upper=1; 
		if(lower<1) 
			lower=1; 
		if(lower>255) 
			lower=255; 
		int r=lower+random.nextInt(upper-lower); 
		int g=lower+random.nextInt(upper-lower); 
		int b=lower+random.nextInt(upper-lower); 
		return new Color(r,g,b); 
	}
	**********/ 
} 