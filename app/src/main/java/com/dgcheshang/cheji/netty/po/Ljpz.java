package com.dgcheshang.cheji.netty.po;

public class Ljpz implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String tdh;//摄像头通道号
	private String scms;//上传模式
	private String tpcc;//图片尺寸

	public String getTdh() {
		return tdh;
	}
	public void setTdh(String tdh) {
		this.tdh = tdh;
	}
	public String getScms() {
		return scms;
	}
	public void setScms(String scms) {
		this.scms = scms;
	}
	public String getTpcc() {
		return tpcc;
	}
	public void setTpcc(String tpcc) {
		this.tpcc = tpcc;
	}

	public byte[] getLjpzbytes(){
		byte[] b1=new byte[3];
		b1[0]=Byte.valueOf(scms);
		b1[1]=Byte.valueOf(tdh);
		b1[2]=Byte.valueOf(tpcc);
		return b1;
	}

}
