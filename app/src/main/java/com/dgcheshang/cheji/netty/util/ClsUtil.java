package com.dgcheshang.cheji.netty.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class ClsUtil {
	private static final String TEMP_ENCODING = "ISO-8859-1";
	private static final String DEFAULT_ENCODING = "UTF-8";

	//序列化对象
	public static String writeToStr(Object obj) throws IOException {
		// 此类实现了一个输出流，其中的数据被写入一个 byte 数组。
		// 缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray() 和 toString() 获取数据。
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		// 专用于java对象序列化，将对象进行序列化
		ObjectOutputStream objectOutputStream = null;
		String serStr = null;
		try {
			objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
			objectOutputStream.writeObject(obj);
			serStr = byteArrayOutputStream.toString(TEMP_ENCODING);
			serStr = java.net.URLEncoder.encode(serStr, DEFAULT_ENCODING);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			objectOutputStream.close();
		}
		return serStr;
	}

	//反序列化
	public static Object deserializeFromStr(String serStr) throws IOException {
		ByteArrayInputStream byteArrayInputStream = null;
		ObjectInputStream objectInputStream = null;
		try {
			String deserStr = java.net.URLDecoder.decode(serStr, DEFAULT_ENCODING);
			byteArrayInputStream = new ByteArrayInputStream(deserStr.getBytes(TEMP_ENCODING));
			objectInputStream = new ObjectInputStream(byteArrayInputStream);
			return objectInputStream.readObject();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} finally {
			objectInputStream.close();
			byteArrayInputStream.close();
		}
		return null;
	}
}
