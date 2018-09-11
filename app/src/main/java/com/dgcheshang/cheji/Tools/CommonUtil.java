package com.dgcheshang.cheji.Tools;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

import android.util.Log;

public class CommonUtil {
	
    public static byte[] short2Byte(short a){  
        byte[] b = new byte[2];  
          
        b[0] = (byte) (a >> 8);  
        b[1] = (byte) (a);  
          
        return b;  
    }  
      
    public static void short2Byte(short a, byte[] b, int offset){  
        b[offset] = (byte) (a >> 8);  
        b[offset+1] = (byte) (a);  
    }  
      
    public static short byte2Short(byte[] b){  
        return (short) (((b[0] & 0xff) << 8) | (b[1] & 0xff));  
    }  
      
    public static short byte2Short(byte[] b, int offset){  
        return (short) (((b[offset] & 0xff) << 8) | (b[offset+1] & 0xff));  
    }  
  
  
    public static void long2Byte(long a, byte[] b, int offset) {          
        b[offset + 0] = (byte) (a >> 56);  
        b[offset + 1] = (byte) (a >> 48);  
        b[offset + 2] = (byte) (a >> 40);  
        b[offset + 3] = (byte) (a >> 32);  
  
        b[offset + 4] = (byte) (a >> 24);  
        b[offset + 5] = (byte) (a >> 16);  
        b[offset + 6] = (byte) (a >> 8);  
        b[offset + 7] = (byte) (a);  
    }  
  

    public static long byte2Long(byte[] b, int offset) {  
         return ((((long) b[offset + 0] & 0xff) << 56)  
         | (((long) b[offset + 1] & 0xff) << 48)  
         | (((long) b[offset + 2] & 0xff) << 40)  
         | (((long) b[offset + 3] & 0xff) << 32)  
           
         | (((long) b[offset + 4] & 0xff) << 24)  
         | (((long) b[offset + 5] & 0xff) << 16)  
         | (((long) b[offset + 6] & 0xff) << 8)  
         | (((long) b[offset + 7] & 0xff) << 0));  
    }  
  
 
    public static long byte2Long(byte[] b) {  
         return  
         ((b[0]&0xff)<<56)|  
         ((b[1]&0xff)<<48)|  
         ((b[2]&0xff)<<40)|  
         ((b[3]&0xff)<<32)|  
          
         ((b[4]&0xff)<<24)|  
         ((b[5]&0xff)<<16)|  
         ((b[6]&0xff)<<8)|  
         (b[7]&0xff);  
    }  
 
    public static byte[] long2Byte(long a) {  
        byte[] b = new byte[4 * 2];  
  
        b[0] = (byte) (a >> 56);  
        b[1] = (byte) (a >> 48);  
        b[2] = (byte) (a >> 40);  
        b[3] = (byte) (a >> 32);  
          
        b[4] = (byte) (a >> 24);  
        b[5] = (byte) (a >> 16);  
        b[6] = (byte) (a >> 8);  
        b[7] = (byte) (a >> 0);  
  
        return b;  
    }  
  
 
    public static int byte2Int(byte[] b) {  
        return ((b[0] & 0xff) << 24) | ((b[1] & 0xff) << 16)  
                | ((b[2] & 0xff) << 8) | (b[3] & 0xff);  
    }  
  
 
    public static int byte2Int(byte[] b, int offset) {  
        return ((b[offset++] & 0xff) << 24) | ((b[offset++] & 0xff) << 16)  
                | ((b[offset++] & 0xff) << 8) | (b[offset++] & 0xff);  
    }  
  

    public static byte[] int2Byte(int a) {  
        byte[] b = new byte[4];  
        b[0] = (byte) (a >> 24);  
        b[1] = (byte) (a >> 16);  
        b[2] = (byte) (a >> 8);  
        b[3] = (byte) (a);  
  
        return b;  
    }  
  
   
    public static void int2Byte(int a, byte[] b, int offset) {        
        b[offset++] = (byte) (a >> 24);  
        b[offset++] = (byte) (a >> 16);  
        b[offset++] = (byte) (a >> 8);  
        b[offset++] = (byte) (a);  
    } 
    
    public static void printf_bytes(byte[] _data,String tag)
	{
			String tmp = getHexBytes(_data);
			Log.i(tag, tmp);
	}
    
    public static void printf_bytes(byte[] _data, int _len,String tag)
	{
			String tmp = "";
			for(int i = 0; i < _len; i++)
			{
				tmp += toHex(_data[i]);
				tmp += " ";
			}
			Log.i(tag, tmp);
	}
    
    public static void printf_bytes_send(byte[] _data, int _len,String tag)
    {
			String tmp = "";
			for (int i = 0; i < _len; i++) {
				tmp += toHex(_data[i]);
				tmp += " ";
			}
			Log.i(tag, tmp);
	}
    
    public static String getHexBytes(byte[] _data){
    	String tmp = "";
		for(int i = 0; i < _data.length; i++)
		{
			tmp += toHex(_data[i]);
			tmp += " ";
		}
		return tmp;
    }
    

    
  
    public static String toHex(byte b) {
        Integer I = new Integer((((int) b) << 24) >>> 24);
        int i = I.intValue();

        if (i < (byte) 16) {
            return "0" + Integer.toString(i, 16);
        } else {
            return Integer.toString(i, 16);
        }
    }
    
   
    public static byte[] byte2Array(byte b) {  
        byte[] array = new byte[8];  
        for (int i = 0; i < 8; i++) {  
            array[i] = (byte)(b & 1);  
            b = (byte) (b >> 1);  
        }  
        return array;  
    }
    
   
    public static byte bitToByte(String byteStr) {  
	    int re, len;  
	    if (null == byteStr) {  
	        return 0;  
	    }  
	    len = byteStr.length();  
	    if (len != 4 && len != 8) {  
	        return 0;  
	    }  
	    if (len == 8) {
	        if (byteStr.charAt(0) == '0') {
	            re = Integer.parseInt(byteStr, 2);  
	        } else {
	            re = Integer.parseInt(byteStr, 2) - 256;  
	        }  
	    } else {
	        re = Integer.parseInt(byteStr, 2);  
	    }  
	    return (byte) re;  
	}
    
    public static String getYMDHMSBCDTime(byte[] b) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer("20");
		for (int i = 0; i < 6; i++) {
			String link = "";
			if (i >= 0 && i <= 1) {
				link = "-";
			} else if (i == 2) {
				link = " ";
			} else if (i > 2 && i < 5) {
				link = ":";
			} else {
				link = "";
			}
			String stmp = Integer.toHexString(b[i] & 0xFF);
			buffer.append((stmp.length() == 1) ? "0" + stmp + link : stmp
					+ link);
		}
		return buffer.toString();
	}
	
	public static String getYMDBCDTime(byte[] b) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer("20");
        for(int i=0;i<3;i++)
        {
        	String link = "";
       	 		if(i>=0&&i<=1){
       		 link = "-";
       	 		}else{
       		 link = "";
       	 		}
        	  String stmp =Integer.toHexString(b[i] & 0xFF);
        	  buffer.append((stmp.length()==1)? "0"+stmp+link : stmp+link);
        }
		return buffer.toString();
	}
	
	public static String getOthersBCD(byte[] b) {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String stmp = Integer.toHexString(b[i] & 0xFF);
			buffer.append((stmp.length() == 1) ? "0" + stmp : stmp);
		}
		return buffer.toString();
	}

	public static String ASCII2String(byte[] b){
	        StringBuffer s = new StringBuffer();
	        for(int i=0;i<b.length;i++){
	        	if((b[i] != 0) && (b[i] != -1)){
	        		char c = (char) b[i];
	        		s.append(c);
	        	}
	        }
		return  s.toString();
	}

	public static String timeStringFormat2String(String str) {
		   List<String> list = new ArrayList<String>();
		   String streee = "";
		   
		   for (int i = 0; i <str.length(); i++) {
		    streee = str.substring(i, i + 1);
		    list.add(streee);
		   }
		   StringBuffer strb = new StringBuffer();
		   for (int j = 0; j < list.size(); j++) {
		    String a = list.get(j).toString();
		    if (!a.equals("-")&&!a.equals(" ")&&!a.equals(":")){
		     strb.append(a);
		   }
		   }
		   return strb.toString();
		  }

	public static byte[] hexStringToByte(String hex) {   
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	}  
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789ABCDEF".indexOf(c);   
	    return b;   
	}
	

    public static byte[] int2ByteArray(int target) {  
        byte[] array = new byte[4];  
        for (int i = 0; i < 4; i++) {  
            int offSet = array.length -i -1;  
            array[i] = (byte) (target >> 8 * offSet & 0xFF);  
        }  
        return array;  
    }

    public static byte[] shortToByte(short s) { 
    	byte[] targets = new byte[2];  
        for (int i = 0; i < 2; i++) {  
            int offset = (targets.length - 1 - i) * 8;  
            targets[i] = (byte) ((s >>> offset) & 0xff);  
        }  
        return targets; 
    }
    
    

	public static String byteToHexString(byte[] bArray){
		String result = null;
		StringBuffer sb = new StringBuffer(bArray.length);
		try {
			for(int i=0;i<bArray.length;i++){
				String sTemp = Integer.toHexString(0xFF & bArray[i]);
				if (sTemp.length() < 2)
					sb.append(0);
				sb.append(sTemp.toUpperCase(Locale.getDefault()));
			}
			result = sb.toString();
		} catch (Exception e) {
			// TODO: handle exception
			result = null;
		}
		
		return result;
	}
		
	public static byte[] str2Bcd(String asc) {
	    int len = asc.length();
	    int mod = len % 2;

	    if (mod != 0) {
	     asc = "0" + asc;
	     len = asc.length();
	    }

	    byte abt[] = new byte[len];
	    if (len >= 2) {
	     len = len / 2;
	    }

	    byte bbt[] = new byte[len];
	    abt = asc.getBytes();
	    int j, k;

	    for (int p = 0; p < asc.length()/2; p++) {
	     if ( (abt[2 * p] >= '0') && (abt[2 * p] <= '9')) {
	    	 j = abt[2 * p] - '0';
	     } else if ( (abt[2 * p] >= 'a') && (abt[2 * p] <= 'z')) {
	    	 j = abt[2 * p] - 'a' + 0x0a;
	     } else {
	    	 j = abt[2 * p] - 'A' + 0x0a;
	     }

	     if ( (abt[2 * p + 1] >= '0') && (abt[2 * p + 1] <= '9')) {
    	 	 k = abt[2 * p + 1] - '0';
	     } else if ( (abt[2 * p + 1] >= 'a') && (abt[2 * p + 1] <= 'z')) {
	    	 k = abt[2 * p + 1] - 'a' + 0x0a;
	     }else {
	    	 k = abt[2 * p + 1] - 'A' + 0x0a;
	     }

	     int a = (j << 4) + k;
	     byte b = (byte) a;
	     bbt[p] = b;
	    }
	    return bbt;
	}
	
	
	public static boolean hasgbk(String str)
	 {
	     char[] chars=str.toCharArray();
	     boolean isGB2312=false;
	     for(int i=0;i<chars.length;i++){
            byte[] bytes=(""+chars[i]).getBytes();
            if(bytes.length==2){
                int[] ints=new int[2];
                ints[0]=bytes[0]& 0xff;
                ints[1]=bytes[1]& 0xff;
                if(ints[0]>=0x81 && ints[0]<=0xFE && ints[1]>=0x40 && ints[1]<=0xFE){
                    isGB2312=true;
                    break;
                }
            }
            if(Character.isLetter(chars[i])){
           	 	isGB2312=true;
           	 	break;
            }
	     }
	     
	  return isGB2312;
	 } 
	

	public static boolean hasAlphaNum(String str){
		boolean flag = false;
		boolean result = str.matches(".*\\p{Alpha}.*");
		boolean result2 = str.matches(".*\\d+.*");
		if(result || result2)
			flag = true;
		return flag;
	}
	
	public static int byteToInt2(byte[] b) {

        int mask=0xff;
        int temp=0;
        int n=0;
        for(int i=0;i<b.length;i++){
           n<<=8;
           temp=b[i]&mask;
           n|=temp;
       }
       return n;
   }
	
	

	public static byte[] timeToByte(String _time){
		long time = 0;
		long time1 = 0;
		try {
			time1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2000-01-01 00:00")).getTime();
			time = (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(_time)).getTime();
			time = (time - time1)/1000 - 8*60*60;
		} catch (Exception e) {
			// TODO Auto-generated catch block
		}
		byte[] b = new byte[4];
        b[3] = (byte) ((time >> 24 ) & 0xff);
        b[2] = (byte) ((time >> 16) & 0xff);
        b[1] = (byte) ((time >> 8) & 0xff);
        b[0] = (byte) (time & 0xff);
        return b;
	}
	

	public static byte[] timeToByteBigEndian(String _time){
		long time = 0;
		long time1 = 0;
		try {
			time1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2000-01-01 00:00")).getTime();
			time = (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(_time)).getTime();
			time = (time - time1)/1000 - 8*60*60;
		} catch (Exception e) {

		}
		byte[] b = new byte[4];
        b[0] = (byte) ((time >> 24 ) & 0xff);
        b[1] = (byte) ((time >> 16) & 0xff);
        b[2] = (byte) ((time >> 8) & 0xff);
        b[3] = (byte) (time & 0xff);
        return b;
	}
	
	

	public static int convertToInt(long num){
		byte[] b = new byte[4];
        b = convertData(num);
       return byteToInt2(b);
	}
	

	public static byte[] convertData(long num){
		byte[] b = new byte[4];
		b[3] = (byte) ((num >> 24 ) & 0xff);
        b[2] = (byte) ((num >> 16) & 0xff);
        b[1] = (byte) ((num >> 8) & 0xff);
        b[0] = (byte) (num & 0xff);
        return b;
	}

	public static String convertToTime(long timeArray){
		long time1 = 0;
		long time = 0;
		try{
			time1 = (new SimpleDateFormat("yyyy-MM-dd HH:mm").parse("2000-01-01 00:00")).getTime();
			time = timeArray*1000 + time1;
		}catch(Exception ex){
			
		}
		
		Date now = new Date(time); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		dateFormat.setTimeZone(TimeZone.getTimeZone("GMT+8")); 
		return dateFormat.format(now);
	}

	public static byte[] int2Byte(long a) {
        byte[] b = new byte[4];
        b[0] = (byte) ((a >> 24 ) & 0xff);
        b[1] = (byte) ((a >> 16) & 0xff);
        b[2] = (byte) ((a >> 8) & 0xff);
        b[3] = (byte) (a & 0xff);
        return b;
    }
	

	public static int bytesToInt(byte[] src, int offset) {  
	    int value;    
	    value = (int) ((src[offset] & 0xFF)   
	            | ((src[offset+1] & 0xFF)<<8)   
	            | ((src[offset+2] & 0xFF)<<16)   
	            | ((src[offset+3] & 0xFF)<<24));  
	    return value;  
	}
	

	public static short convertShort(short num){
		byte[] array = new byte[2];
		array[0] = (byte) ((num >> 8) & 0xff);
		array[1] = (byte) (num & 0xff);
		return byte2Short(array,0);
	}
	
  
	public static byte[] getBooleanArray(byte b) {   
        byte[] array = new byte[8];   
        for (int i = 7; i >= 0; i--) {   
            array[i] = (byte)(b & 1);   
            b = (byte) (b >> 1);   
        }   
        return array;   
    } 

	
	public static long convertStrToMillis(String timeStr) {
		long time = 0;
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(timeStr));
			time = c.getTimeInMillis();
		} catch (Exception ex) {
			
		}
		return time;
	}

	public static String converFormatTimeString(String timeStr){
		long mill = convertStrToMillis(timeStr);
		Date now = new Date(mill); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(now);
	}
	

	public static String converMillsToFormatString(long mills){
		Date now = new Date(mills); 
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return dateFormat.format(now);
	}

	public static long getTheEarlyerTime(List<String> timeList){
		if(timeList.size() == 0)
			return 0;
		long time = convertStrToMillis(timeList.get(0));
		for(int i = 0;i<timeList.size();i++){
			long tmpTime = convertStrToMillis(timeList.get(i));
			if(tmpTime<time)
				time = tmpTime;
		}
		return time;
	}
}
