package com.dgcheshang.cheji.netty.po;


import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 教练员登出
 * @author Administrator
 *
 */
public class Jlydc implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String jlybh;//教练员编号
	private String pxkc;//培训课程
	private String ktid;//课堂ID

	public String getJlybh() {
		return jlybh;
	}
	public void setJlybh(String jlybh) {
		this.jlybh = jlybh;
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
	public byte[] getJlydcBytes(){
		byte[] b=new byte[0];
		byte[] temp=jlybh.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		temp= ByteUtil.intToByteArray(Integer.valueOf(ktid));
		b=ByteUtil.byteMerger(b, temp);
		return b;
	}
}
