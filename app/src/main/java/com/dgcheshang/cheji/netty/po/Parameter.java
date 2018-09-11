package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.proputil.ParamType;
import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.io.UnsupportedEncodingException;

public class Parameter implements java.io.Serializable{
	/**
	 * 参数项
	 */
	private static final long serialVersionUID = 1L;
	private String id;//参数ID
	private int cd;//参数长度
	private String value;//参数值

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public int getCd() {
		return cd;
	}
	public void setCd(int cd) {
		this.cd = cd;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}


	public byte[] getParameterBytes(){
		byte[] bs=null;
		byte[] b1= ByteUtil.hexStringTOFinalbyte(this.id, 4, 1);

		//获取当前参数的类型
		String type= ParamType.getValue(id);
		byte[] b3=null;
		if(type.equals("int")){
			b3=ByteUtil.intToByteArray(Integer.valueOf(value));
		}else if(type.equals("short")){
			b3=ByteUtil.shortToByteArray(Short.valueOf(value));
		}else if(type.equals("byte")){
			b3=new byte[1];
			int i=Integer.valueOf(value);
			b3[0]=(byte) i;
		}else if(type.equals("string")){
			try {
				b3=new String(this.value).getBytes("GBK");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			byte[] b4=new byte[1];
			b4[0]=0;
			b3=ByteUtil.byteMerger(b3, b4);
		}

		cd=b3.length;
		byte[] b2=new byte[1];
		b2[0]=(byte) cd;

		bs=ByteUtil.byteMerger(b1, b2);
		bs=ByteUtil.byteMerger(bs, b3);
		return bs;
	}

}
