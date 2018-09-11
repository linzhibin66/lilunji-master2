package com.dgcheshang.cheji.netty.certificate;

import java.io.ByteArrayInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class Certificate {
	
	public static X509Certificate getBase64Cer(String cadata,char [] password){
		/*String cadata = "全国平台返回的终端证书（base64编码）";
		char [] password = "全国平台返回的终端证书口令";*/
		try{
			byte [] cabuf = new BASE64Decoder().decodeBuffer(cadata);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new ByteArrayInputStream(cabuf), password);
			Enumeration<String> aliases = keyStore.aliases();
			if (!aliases.hasMoreElements()) {
				throw new RuntimeException("no alias found");
			}
			String alias = aliases.nextElement();
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
			return cert;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getBase64String(String cadata,char [] password){
		/*String cadata = "全国平台返回的终端证书（base64编码）";
		char [] password = "全国平台返回的终端证书口令";*/
		try{
			byte [] cabuf = new BASE64Decoder().decodeBuffer(cadata);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new ByteArrayInputStream(cabuf), password);
			Enumeration<String> aliases = keyStore.aliases();
			if (!aliases.hasMoreElements()) {
				throw new RuntimeException("no alias found");
			}
			String alias = aliases.nextElement();
			X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
			String cerbase64 = (new BASE64Encoder()).encode(cert.getEncoded());
			return cerbase64;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	public static PrivateKey getPrivateKey(String cadata,char [] password){
		try{
			byte [] cabuf = new BASE64Decoder().decodeBuffer(cadata);
			KeyStore keyStore = KeyStore.getInstance("PKCS12");
			keyStore.load(new ByteArrayInputStream(cabuf), password);
			Enumeration<String> aliases = keyStore.aliases();
			if (!aliases.hasMoreElements()) {
				throw new RuntimeException("no alias found");
			}
			String alias = aliases.nextElement();
			PrivateKey privatekey = (PrivateKey) keyStore.getKey(alias, password);//私钥
			return privatekey;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}

	public static void main(String[] args) {
		
	}

}
