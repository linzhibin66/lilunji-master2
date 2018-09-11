package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

public class Bcfb implements java.io.Serializable{

	/**
	 * 补传分包
	 */
	private static final long serialVersionUID = 1L;
	private int yslsh;//原始消息流水号
	private int gs;//重传包总数(不用传进来)
	private String xhs;//重传包ID列表
	public int getYslsh() {
		return yslsh;
	}
	public void setYslsh(int yslsh) {
		this.yslsh = yslsh;
	}
	public int getGs() {
		return gs;
	}
	public void setGs(int gs) {
		this.gs = gs;
	}
	public String getXhs() {
		return xhs;
	}
	public void setXhs(String xhs) {
		this.xhs = xhs;
	}

	public byte[] getBcfbbytes(){
		byte[] bs=null;
		short i=(short) this.yslsh;
		byte[] b1= ByteUtil.shortToByteArray(i);

		byte[] b2=new byte[1];
		String[] ss=this.xhs.split(",");
		this.gs=ss.length;
		b2[0]=new Integer(this.gs).byteValue();

		bs=ByteUtil.byteMerger(b1, b2);

		byte[] temp=null;
		for(int k=0;k<ss.length;k++){
			i=Short.valueOf(ss[k]);
			temp=ByteUtil.shortToByteArray(i);
			bs=ByteUtil.byteMerger(bs,temp);
		}
		return bs;
	}

}
