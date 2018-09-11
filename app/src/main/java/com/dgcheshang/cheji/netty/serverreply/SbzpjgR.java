package com.dgcheshang.cheji.netty.serverreply;



public class SbzpjgR implements java.io.Serializable{

	/**
	 * 上报照片结果回复
	 */
	private static final long serialVersionUID = 1L;
	private int jg;
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}


	public byte[] getSbzpjgRbytes(){
		byte[] bs=new byte[1];

		bs[0]=Byte.valueOf(String.valueOf(jg));

		return bs;
	}

}
