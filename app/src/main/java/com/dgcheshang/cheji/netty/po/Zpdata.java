package com.dgcheshang.cheji.netty.po;


import com.dgcheshang.cheji.netty.util.ByteUtil;

public class Zpdata implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String zpbh;//照片编号
	private byte[] zpsj;//照片数据
	private String zdid;//Sim
	private String jpbxh;//驾培包序号

	public String getZpbh() {
		return zpbh;
	}
	public void setZpbh(String zpbh) {
		this.zpbh = zpbh;
	}
	public byte[] getZpsj() {
		return zpsj;
	}
	public void setZpsj(byte[] zpsj) {
		this.zpsj = zpsj;
	}
	public String getZdid() {
		return zdid;
	}
	public void setZdid(String zdid) {
		this.zdid = zdid;
	}
	public String getJpbxh() {
		return jpbxh;
	}
	public void setJpbxh(String jpbxh) {
		this.jpbxh = jpbxh;
	}

	public byte[] getZpdatabytes(){
		byte[] bs=new byte[0];

		byte[] temp=zpbh.getBytes();
		bs=ByteUtil.byteMerger(bs, temp);
		bs= ByteUtil.byteMerger(bs, zpsj);

		return bs;
	}


}
