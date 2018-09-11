package com.dgcheshang.cheji.netty.certificate;

import java.security.MessageDigest;
import java.security.cert.X509Certificate;
import java.util.Arrays;

import javax.crypto.Cipher;

/**
 * 验证签名方法（解密）
 * @author Administrator
 *
 */
public class Verify {
	public static boolean verify(String data, long timestamp, String encodedEncryptedStr,
			X509Certificate userCert) throws Exception 
	{
		return verify(data.getBytes("utf-8"), timestamp, encodedEncryptedStr, userCert);
	}
	public static boolean verify(String data, String encodedEncryptedStr,
			X509Certificate userCert) throws Exception
	{
		return verify(data.getBytes("utf-8"), 0, encodedEncryptedStr, userCert);
	}
	public static boolean verify(byte [] data, String encodedEncryptedStr,
			X509Certificate userCert) throws Exception{
		return verify(data, 0,encodedEncryptedStr, userCert);
	}
	public static boolean verify(byte [] data, long timestamp, String encodedEncryptedStr,
			X509Certificate userCert) throws Exception
	{
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(data);
		if(timestamp > 0){
			md.update(EncodeUtil.toBE(timestamp));
		}
		
		byte[] hash = md.digest();
		byte[] encryptedStr = HexBin.decode(encodedEncryptedStr);
		//byte[] encryptedStr=encodedEncryptedStr.getBytes();
		System.out.println(encryptedStr);
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, userCert);
		byte[] plain = cipher.doFinal(encryptedStr);
		boolean ok = Arrays.equals(hash, plain);
		return ok;
	}


}

