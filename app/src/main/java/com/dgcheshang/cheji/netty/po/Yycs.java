package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;


public class Yycs implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String csbh;//参数编号
	private String pzsjjg;//拍照时间间隔
	private String zpscsz;//照片上传设置
	private String sfbdfjxx;//是否报读附加消息
	private String xhyssj;//熄火后停止学时计时的延时时间
	private String scjg;//熄火后停止学时计时的延时时间
	private String dcyssj;//熄火后教练自动登出的延时时间
	private String cxyzsj;//重新验证身份时间
	private String jlkxxx;//教练跨校教学习
	private String xykxxx;//学员跨校学习
	private String tlptsj;//响应平台同类消息时间间隔
	public String getCsbh() {
		return csbh;
	}
	public void setCsbh(String csbh) {
		this.csbh = csbh;
	}
	public String getPzsjjg() {
		return pzsjjg;
	}
	public void setPzsjjg(String pzsjjg) {
		this.pzsjjg = pzsjjg;
	}
	public String getZpscsz() {
		return zpscsz;
	}
	public void setZpscsz(String zpscsz) {
		this.zpscsz = zpscsz;
	}
	public String getSfbdfjxx() {
		return sfbdfjxx;
	}
	public void setSfbdfjxx(String sfbdfjxx) {
		this.sfbdfjxx = sfbdfjxx;
	}
	public String getXhyssj() {
		return xhyssj;
	}
	public void setXhyssj(String xhyssj) {
		this.xhyssj = xhyssj;
	}
	public String getScjg() {
		return scjg;
	}
	public void setScjg(String scjg) {
		this.scjg = scjg;
	}
	public String getDcyssj() {
		return dcyssj;
	}
	public void setDcyssj(String dcyssj) {
		this.dcyssj = dcyssj;
	}
	public String getCxyzsj() {
		return cxyzsj;
	}
	public void setCxyzsj(String cxyzsj) {
		this.cxyzsj = cxyzsj;
	}
	public String getJlkxxx() {
		return jlkxxx;
	}
	public void setJlkxxx(String jlkxxx) {
		this.jlkxxx = jlkxxx;
	}
	public String getXykxxx() {
		return xykxxx;
	}
	public void setXykxxx(String xykxxx) {
		this.xykxxx = xykxxx;
	}
	public String getTlptsj() {
		return tlptsj;
	}
	public void setTlptsj(String tlptsj) {
		this.tlptsj = tlptsj;
	}
	/**
	 * 获取应用参数的字节数组
	 * @return
	 */
	public byte[] getYycsBytes(){
		byte[] bs=null;

		byte[] b1=new byte[1];
		b1[0]=Byte.valueOf(csbh);

		bs=b1;

		byte[] temp=new byte[1];
		if(StringUtils.isNotEmpty(pzsjjg)){
			temp[0]=Byte.valueOf(pzsjjg);
		}
		bs= ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		if(StringUtils.isNotEmpty(zpscsz)){
			temp[0]=Byte.valueOf(zpscsz);
		}
		bs=ByteUtil.byteMerger(bs, temp);


		temp=new byte[1];
		if(StringUtils.isNotEmpty(sfbdfjxx)){
			temp[0]=Byte.valueOf(sfbdfjxx);
		}
		bs=ByteUtil.byteMerger(bs, temp);


		temp=new byte[1];
		if(StringUtils.isNotEmpty(xhyssj)){
			temp[0]=Byte.valueOf(xhyssj);
		}
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[2];
		if(StringUtils.isNotEmpty(scjg)){
			temp=ByteUtil.shortToByteArray(Short.valueOf(scjg));
		}
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[2];
		if(StringUtils.isNotEmpty(dcyssj)){
			temp=ByteUtil.shortToByteArray(Short.valueOf(dcyssj));
		}
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[2];
		if(StringUtils.isNotEmpty(cxyzsj)){
			temp=ByteUtil.shortToByteArray(Short.valueOf(cxyzsj));
		}
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		if(StringUtils.isNotEmpty(jlkxxx)){
			temp[0]=Byte.valueOf(jlkxxx);
		}
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		if(StringUtils.isNotEmpty(xykxxx)){
			temp[0]=Byte.valueOf(xykxxx);
		}
		bs=ByteUtil.byteMerger(bs, temp);


		temp=new byte[2];
		if(StringUtils.isNotEmpty(tlptsj)){
			temp=ByteUtil.shortToByteArray(Short.valueOf(tlptsj));
		}
		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}
}
