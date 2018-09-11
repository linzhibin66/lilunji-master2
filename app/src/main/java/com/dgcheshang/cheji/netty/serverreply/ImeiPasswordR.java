package com.dgcheshang.cheji.netty.serverreply;

public class ImeiPasswordR implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int jg;
	private int type;
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

}
