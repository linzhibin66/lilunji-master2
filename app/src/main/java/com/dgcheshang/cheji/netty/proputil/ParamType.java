package com.dgcheshang.cheji.netty.proputil;

import android.content.Context;

import com.dgcheshang.cheji.CjApplication;

import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * app配置文件引入
 * @author joxhome
 *
 */
public class ParamType {
	private static Properties pro = null;
	private static String fileName = "paramType.properties";
	public ParamType(){
		loadPropertyesFile(fileName);
	}

	private static void loadPropertyesFile(String filePath){
		try{
			pro = new Properties();
			Context context= CjApplication.getInstance();
			InputStream ins = context.getAssets().open(fileName);
			pro.load(ins);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public static String getValue(String key){
		try{
			if(null == pro)
				loadPropertyesFile(fileName);
			return pro.getProperty(key);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public void setValue(String key, String value) {
		pro.setProperty(key, value);
	}

	public static Iterator<String> getKeys(){
		try{
			if(null == pro)
				loadPropertyesFile(fileName);
			return pro.stringPropertyNames().iterator();
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

}


