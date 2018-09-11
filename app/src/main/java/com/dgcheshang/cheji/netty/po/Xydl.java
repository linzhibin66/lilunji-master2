package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 学员登陆
 * @author Administrator
 *
 */
public class Xydl implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String xybh;//学员编号
	private String jlbh;//当前教练编号
	private String pxkc;//培训课程
	private String ktid;//课堂ID
	public String getXybh() {
		return xybh;
	}
	public void setXybh(String xybh) {
		this.xybh = xybh;
	}
	public String getJlbh() {
		return jlbh;
	}
	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
	}
	public String getPxkc() {
		return pxkc;
	}
	public void setPxkc(String pxkc) {
		this.pxkc = pxkc;
	}
	public String getKtid() {
		return ktid;
	}
	public void setKtid(String ktid) {
		this.ktid = ktid;
	}
	public byte[] getXydlBytes(){
		byte[] b=new byte[0];
		byte[] temp =xybh.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		temp=jlbh.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.str2Bcd(pxkc);
		b=ByteUtil.byteMerger(b, temp);

		temp= ByteUtil.intToByteArray(Integer.valueOf(ktid));
		b=ByteUtil.byteMerger(b, temp);

		return b;
	}
}
