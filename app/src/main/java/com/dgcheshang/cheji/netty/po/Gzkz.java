package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

public class Gzkz implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String sjjg;//时间间隔
	private String yxq;//位置跟踪有效期

	public String getSjjg() {
		return sjjg;
	}
	public void setSjjg(String sjjg) {
		this.sjjg = sjjg;
	}
	public String getYxq() {
		return yxq;
	}
	public void setYxq(String yxq) {
		this.yxq = yxq;
	}


	public byte[] getGzkzBytes(){
		byte[] bs=null;
		short i=Short.valueOf(sjjg);
		byte[] b1= ByteUtil.shortToByteArray(i);

		int k=Integer.valueOf(yxq);
		byte[] b2=ByteUtil.intToByteArray(k);

		bs=ByteUtil.byteMerger(b1, b2);
		return bs;
	}

}
