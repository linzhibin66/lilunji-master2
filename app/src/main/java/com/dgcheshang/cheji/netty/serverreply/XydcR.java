package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

public class XydcR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jg;//结果   1：登录成功；2：无效的教练员编号；3：准教车型不符；9：其他错误
	private String xybh;//教练编号
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getXybh() {
		return xybh;
	}
	public void setXybh(String xybh) {
		this.xybh = xybh;
	}
	public byte[] getXydcbytes(){
		byte[] bs=null;
		byte[] b1=new byte[1];
		b1[0]=new Integer(this.jg).byteValue();

		byte[] b2=this.xybh.getBytes();
		bs= ByteUtil.byteMerger(b1, b2);

		return bs;
	}

}
