package com.dgcheshang.cheji.netty.po;

public class Forward implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int lsh;//转发
	private String nr;//转发内容
	private int cs;//转发的次数
	private int type;//0为普通信息 1为关键数据
	private int level;//发送级别
	private Long initsj;//最初时间可能为空
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public String getNr() {
		return nr;
	}
	public void setNr(String nr) {
		this.nr = nr;
	}
	public int getCs() {
		return cs;
	}
	public void setCs(int cs) {
		this.cs = cs;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public Long getInitsj() {
		return initsj;
	}
	public void setInitsj(Long initsj) {
		this.initsj = initsj;
	}
}
