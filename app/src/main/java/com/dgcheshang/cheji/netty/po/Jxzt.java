package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

public class Jxzt implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String zt;//禁训状态
	private String tsxx;//提示消息内容;

	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getTsxx() {
		return tsxx;
	}
	public void setTsxx(String tsxx) {
		this.tsxx = tsxx;
	}

	public byte[] getJxztBytes(){
		byte[] bs=null;

		byte[] temp=new byte[1];
		temp[0]=Byte.valueOf(zt);

		bs=temp;

		byte[] b3=new byte[0];
		if(StringUtils.isNotEmpty(tsxx)){
			b3=tsxx.getBytes();
		}

		int len=b3.length;

		byte[] b2=new byte[1];
		b2[0]=(byte) len;

		bs= ByteUtil.byteMerger(bs, b2);
		bs=ByteUtil.byteMerger(bs, b3);

		//补零位
		temp=new byte[1];
		temp[0]=0;
		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}

}
