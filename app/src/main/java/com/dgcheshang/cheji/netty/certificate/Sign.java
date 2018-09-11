package com.dgcheshang.cheji.netty.certificate;


import java.security.MessageDigest;
import java.security.PrivateKey;

import javax.crypto.Cipher;


/**
 * 证书签名方法（加密）
 * @author Administrator
 *
 */
public class Sign {
	public static String sign(String data, long timestamp, PrivateKey key) throws Exception {
		return sign(data.getBytes("utf-8"), timestamp, key);
	}
	public static String sign(String data, PrivateKey key) throws Exception{
		return sign(data.getBytes("utf-8"), 0, key);
	}
	public static String sign(byte [] data, PrivateKey key) throws Exception {
		return sign(data, 0, key);
	}
	public static String sign(byte [] data, long timestamp, PrivateKey key) throws Exception {
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data);
		if(timestamp > 0){
			md.update(EncodeUtil.toBE(timestamp));
		}
		
		byte[] hash = md.digest();
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] encrypted = cipher.doFinal(hash);

		return HexBin.encode(encrypted);
//		return ByteUtil.bytesToHexString(encrypted);
	}
	
}


