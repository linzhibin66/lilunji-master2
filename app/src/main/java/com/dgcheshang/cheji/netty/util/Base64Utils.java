package com.dgcheshang.cheji.netty.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import sun.misc.BASE64Encoder;

public class Base64Utils {


    /**
     * 获取bse64文件的类型
     * @return
     */
    public static String getBase64Type(String base64){
        if(base64.substring(0,4).equals("data")){
            return base64.substring(base64.indexOf("/")+1, base64.indexOf(";"));
        }else{
            return "jpg";
        }

    }

    // 图片转化成base64字符串
    public static String GetImageBase64(String imgFile) {// 将图片文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取图片字节数组
        try {
            in = new FileInputStream(imgFile);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            // 对字节数组Base64编码
            BASE64Encoder encoder = new BASE64Encoder();
            return encoder.encode(data);// 返回Base64编码过的字节数组字符串
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decode(String str){
        byte[] bt = null;
        try {
            sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
            bt = decoder.decodeBuffer( str );
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bt;
    }


    public static void main(String[] args) {
        String source=Base64Utils.GetImageBase64("D:/1.jpg");
        System.out.println(source);
    }
}
