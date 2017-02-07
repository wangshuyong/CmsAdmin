package com.sea.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
	private static final String ALGORITHM_MD5 = "MD5";

	public static String getPassMD5(String pass) {
		String keys = null;
		try {
			MessageDigest md = MessageDigest.getInstance(ALGORITHM_MD5);
			if (pass == null) {
				pass = "";
			}
			byte[] bPass = pass.getBytes("UTF-8");
			md.update(bPass);
			keys = bytesToHexString(md.digest());
		} catch (NoSuchAlgorithmException aex) {
		} catch (java.io.UnsupportedEncodingException uex) {
			// logger.error("can not encode the password - " +
			// uex.getMessage());
		}
		return keys;
	}
	
	public final static String bytesToHexString(byte[] bArray) {
		StringBuffer sb = new StringBuffer(bArray.length);
		String sTemp;
		for (int i = 0; i < bArray.length; i++) {
			sTemp = Integer.toHexString(0xFF & bArray[i]);
			if (sTemp.length() < 2) {
				sb.append(0);
			}
			sb.append(sTemp.toUpperCase());
		}
		return sb.toString();
	}
	
}
