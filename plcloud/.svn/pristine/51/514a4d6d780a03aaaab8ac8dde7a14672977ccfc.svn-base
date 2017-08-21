package com.routon.pmax.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;

/**
 * 
 * @author j
 *
 */
public class UserUtil {
	
	public static String sha1pwd(String pwd) {		
		
		try{
			MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
			sha1.update(("pmax:" + pwd).getBytes());
			
			return Hex.encodeHexString(sha1.digest());			
		}
		catch(NoSuchAlgorithmException e){
			throw new RuntimeException("NoSuchAlgorithmException SHA-1: " + e.getMessage());
		}		
	}
}
