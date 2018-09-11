package com.dgcheshang.cheji.netty.clientreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 查询参数回复
 * @author Administrator
 *
 */
public class CxyycsR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int jg;//执行结果
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
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
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
	 * 查询应用 参数回复字节数组
	 * @return
	 */
	public byte[] getCxyycsRbytes(){
		byte[] bs=new byte[5];

		bs[0]=(byte) jg;
		bs[1]=Byte.valueOf(pzsjjg);
		bs[2]=Byte.valueOf(zpscsz);
		bs[3]=Byte.valueOf(sfbdfjxx);
		bs[4]=Byte.valueOf(xhyssj);

		byte[] temp= ByteUtil.shortToByteArray(Short.valueOf(scjg));
		bs=ByteUtil.byteMerger(bs, temp);

		temp=ByteUtil.shortToByteArray(Short.valueOf(dcyssj));
		bs=ByteUtil.byteMerger(bs, temp);

		temp=ByteUtil.shortToByteArray(Short.valueOf(cxyzsj));
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[2];
		temp[0]=Byte.valueOf(jlkxxx);
		temp[1]=Byte.valueOf(xykxxx);
		bs=ByteUtil.byteMerger(bs, temp);

		temp=ByteUtil.shortToByteArray(Short.valueOf(tlptsj));
		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}

}
