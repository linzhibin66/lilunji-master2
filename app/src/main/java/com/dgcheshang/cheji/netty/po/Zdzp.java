package com.dgcheshang.cheji.netty.po;

public class Zdzp implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String zpbh;//照片编号
	
	public String getZpbh() {
		return zpbh;
	}
	public void setZpbh(String zpbh) {
		this.zpbh = zpbh;
	}
	public byte[] getZdzpBytes(){
		byte[] bs=this.zpbh.getBytes();
		return bs;
	}
}
