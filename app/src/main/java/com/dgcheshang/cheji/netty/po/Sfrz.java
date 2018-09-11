package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 身份认证
 * @author Administrator
 *
 */
public class Sfrz implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String sxx;//时效性
	private String jszdbh;//计时终端编号
	private String xxlx;//请求信息类型
	private String rylx;//请求人员类型
	private String zjhm;//身份证号码

	public String getSxx() {
		return sxx;
	}
	public void setSxx(String sxx) {
		this.sxx = sxx;
	}
	public String getJszdbh() {
		return jszdbh;
	}
	public void setJszdbh(String jszdbh) {
		this.jszdbh = jszdbh;
	}
	public String getXxlx() {
		return xxlx;
	}
	public void setXxlx(String xxlx) {
		this.xxlx = xxlx;
	}
	public String getRylx() {
		return rylx;
	}
	public void setRylx(String rylx) {
		this.rylx = rylx;
	}
	public String getZjhm() {
		return zjhm;
	}
	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}


	public byte[] getSfrzBytes(){
		byte[] bs=new byte[2];
		bs[0]=Byte.valueOf(this.xxlx);
		bs[1]=Byte.valueOf(this.rylx);
		byte[] temp=this.zjhm.getBytes();
		String hs=ByteUtil.bytesToHexString(temp);
		temp=ByteUtil.hexStringTOFinalbyte(hs,18,1);
		bs=ByteUtil.byteMerger(bs,temp);
		return bs;
	}
}
