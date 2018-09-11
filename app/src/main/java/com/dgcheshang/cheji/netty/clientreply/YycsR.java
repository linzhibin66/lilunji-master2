package com.dgcheshang.cheji.netty.clientreply;

/**
 * 应用参数设置回复
 * @author Administrator
 *
 */
public class YycsR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int jg;//执行结果
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}

	public byte[] getYycsRBytes(){
		byte[] bs=new byte[1];
		bs[0]=(byte) jg;
		return bs;
	}


}
