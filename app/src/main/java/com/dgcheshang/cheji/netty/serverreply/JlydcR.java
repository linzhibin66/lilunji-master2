package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

public class JlydcR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jg;//结果   1：登录成功；2：无效的教练员编号；3：准教车型不符；9：其他错误
	private String jlbh;//教练编号
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getJlbh() {
		return jlbh;
	}
	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
	}

	public byte[] getJlydcbytes(){
		byte[] bs=null;
		byte[] b1=new byte[1];
		b1[0]=new Integer(this.jg).byteValue();

		byte[] b2=this.jlbh.getBytes();
		bs= ByteUtil.byteMerger(b1, b2);

		return bs;
	}

}
