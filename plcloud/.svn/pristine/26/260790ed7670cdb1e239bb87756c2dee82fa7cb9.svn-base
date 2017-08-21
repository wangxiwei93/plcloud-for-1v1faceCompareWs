package com.routon.pmax.common.utils.web;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.CubicCurve2D;
import java.awt.image.BufferedImage;
import java.util.Random;

import javax.servlet.http.HttpSession;

public class SecurityCode {
	
	public static String SK_CheckCode = "SK_CheckCode";
	public static String SK_CheckCodeCreatTime = "SK_CheckCodeCreatTime";
	
	static private char[] codeChar="0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
	private String code;

	private BufferedImage img;

	private SecurityCode() {

	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}
	
	/**
	 * 生成验证码（默认）
	 * 
	 * @return 验证码对象
	 */
	public static SecurityCode GenerateCode() {
		//return GenerateCode(true, new Color(51, 51, 51), new Color(0, 0, 0));
		return GenerateCode(4,true,true,true,true,new Color(51, 51, 51), new Color(0, 0, 0));
	}
	
	/**
	 * 生成验证码
	 * 
	 * @param number
	 *            验证码长度
	 * @param liuqu
	 *            验证码是否扭曲
	 * @param shibie
	 *            验证码是否防止机器识别
	 * @param zimu
	 *            验证码是否包含字母（只是数字）
	 * @param hunpai
	 *            验证码是否大小写混排
	 * @param dotColor
	 *            点的颜色
	 * @param fontColor
	 *            验证码的颜色
	 * @return 验证码对象
	 */
	public static SecurityCode GenerateCode(int number,boolean liuqu, boolean shibie,boolean zimu,boolean hunpai,Color dotColor, Color fontColor) {
		SecurityCode sc = new SecurityCode();
		
		int width = 17*number+3;
		int height = 30;

		BufferedImage image=new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR); 
		Graphics g=image.getGraphics(); 
		Random random=new Random();//创建一个随机类 
	
		g.setColor(new Color(255,255,255));
		g.fillRect(0, 0, width, height);//画背景 
		
		if(shibie==true){
		//	g.setColor(new Color(51,51,51));// 随机产生50点，使图象中的认证码不易被其它程序探测到 
			g.setColor(dotColor);
			for(int i=0;i<50;i++){ 
				int x=random.nextInt(width); 
				int y=random.nextInt(height); 
				g.drawLine(x, y, x, y); 
			} 
		}
		Font font = new Font("Times New Roman", Font.ITALIC + Font.BOLD, 24); // 创建字体，字体的大小应该根据图片的高度来定。 
		g.setFont(font);//设置字体 
			
		if(shibie==true){
			g.setColor(new Color(0,0,0));
			CubicCurve2D c = new CubicCurve2D.Double();
			c.setCurve(0, 14, width/3+random.nextInt(4),random.nextInt(20),
					width/3*2+random.nextInt(4),random.nextInt(20),width, 12);
			Graphics2D g2= (Graphics2D) g;
			g2.draw(c);
		}
		//g.setColor(new Color(0,0,0));  
		g.setColor(fontColor);  
		int max;
		if(zimu==false){
			max=10;
		}else{
			if(hunpai==false){
				max=36;
			}else{
				max=62;
			}
		}
		StringBuilder sRand=new StringBuilder(); 
		for(int i=0;i<number;i++){ 
			String ch=String.valueOf(codeChar[random.nextInt(max)]); 
			sRand.append(ch);
			
			if(liuqu==true){
				g.drawString(ch, 17*i+5, 20+random.nextInt(5)); 
			}
			else{
				g.drawString(String.valueOf(ch), 17 * i + 5, 22);
			}
			
			
		} 
		sc.code=sRand.toString();
		sc.setImg(image);
		g.dispose();//图像生效 
		return sc;
	}

	/**
	 * 对校验码进行验证
	 * 
	 * @param veryCode
	 *            用户输入的验证码
	 * @param session
	 *            当前用户的会话
	 * @return 验证成功返回true，反之返回false
	 */
	public static Boolean validate(String veryCode, HttpSession session) {
		String sessionCode = (String) session.getAttribute("checkCode");

		if (veryCode != null && veryCode.equalsIgnoreCase(sessionCode)) {
			return true;
		}

		return false;
	}
}
