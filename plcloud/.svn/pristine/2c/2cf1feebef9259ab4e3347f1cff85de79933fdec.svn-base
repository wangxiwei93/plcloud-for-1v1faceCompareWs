/**
 * 
 */
package com.routon.pmax.common;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * <p>
 * Title: EncodeUtils
 * </p>
 * <p>
 * Description: 定义编码相关的工具类
 * </p>
 * <p>
 * Copyright: Copyright (c) 2013
 * </p>
 * <p>
 * Company:
 * </p>
 * <p>
 * Date: 2013-5-13
 * </p>
 * 
 * @author 
 * @version 1.0
 */
@Component
public class EncodeUtils {
	private Logger logger = LoggerFactory.getLogger(EncodeUtils.class);

	/**
	 * 获取密码的MD5戳
	 * 
	 * @param password
	 *            密码
	 * @param salt
	 *            密码混淆串
	 * @return 密码的MD5戳
	 */
	public String getPasswordMD5(String password) {
		MessageDigest messageDigest = null;
		byte[] digest = null;
		
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
		}

		if (messageDigest == null) {
			return password;
		}

		digest = messageDigest.digest((password+"tvb").getBytes());
		

		return Hex.encodeHexString(digest);
	}
	
	public static void main(String[] args) {
		EncodeUtils EncodeUtils = new EncodeUtils();
		String s= EncodeUtils.getPasswordMD5("123456");
		System.out.println(s);
	}

//	/**
//	 * 获取16位随机密码戳
//	 * 
//	 * @return 16位随机密码戳
//	 */
//	public String getEncodedSalt() {
//		RandomUtil randomUtil = new RandomUtil();
//		randomUtil.setCharset("a-zA-Z0-9");
//		randomUtil.setLength("16");
//		String salt = "";
//
//		try {
//			randomUtil.generateRandomObject();
//			salt = randomUtil.getRandom();
//		} catch (Exception e) {
//			logger.error("getEncodedSalt方法异常", e);
//		}
//
//		return salt;
//	}

}
