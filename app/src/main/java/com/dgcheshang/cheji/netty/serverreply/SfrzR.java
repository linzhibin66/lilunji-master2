package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

public class SfrzR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jpbxh;//驾培包序号
	private int jg;//查询结果
	private int xxcd;//身份认证信息长度
	private String xx;//身份认证信息内容
	private byte lx;
	private String uuid;
	private String tybh;//16位
	private String sfzh;//18位，前补0x00
	private String cx;//2
	private String xm;//姓名
	private String sj;
	public int getJpbxh() {
		return jpbxh;
	}
	public void setJpbxh(int jpbxh) {
		this.jpbxh = jpbxh;
	}
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public int getXxcd() {
		return xxcd;
	}
	public void setXxcd(int xxcd) {
		this.xxcd = xxcd;
	}
	public String getXx() {
		return xx;
	}
	public void setXx(String xx) {
		this.xx = xx;
	}

	public byte getLx() {
		return lx;
	}
	public void setLx(byte lx) {
		this.lx = lx;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getTybh() {
		return tybh;
	}

	public void setTybh(String tybh) {
		this.tybh = tybh;
	}

	public String getSfzh() {
		return sfzh;
	}

	public void setSfzh(String sfzh) {
		this.sfzh = sfzh;
	}

	public String getCx() {
		return cx;
	}

	public void setCx(String cx) {
		this.cx = cx;
	}

	public String getXm() {
		return xm;
	}

	public void setXm(String xm) {
		this.xm = xm;
	}

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}
}
