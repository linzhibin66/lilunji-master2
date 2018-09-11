package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 照片上传
 * @author Administrator
 *
 */
public class Zpsc implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String sxx;//时效性
	private String jszdbh;//计时终端编号
	private String bh;
	private String scms;//上传模式
	private String tdh;//摄像头通道号
	private String tpcc;//图片尺寸
	private String sjlx;//事件类型
	private String ktid;//课堂id
	private String rlsb;//人脸识别置信度
	private String zbs;//总包数
	private String zpbh;//照片编号
	private String zpsjcd;//照片数据长度
	public String getSxx() {
		return sxx;
	}

	public void setSxx(String sxx) {
		this.sxx = sxx;
	}

	public String getJszdbh() {
		return jszdbh;
	}

	public void setJszdbh(String jszdbh) {
		this.jszdbh = jszdbh;
	}

	public String getBh() {
		return bh;
	}

	public void setBh(String bh) {
		this.bh = bh;
	}

	public String getScms() {
		return scms;
	}

	public void setScms(String scms) {
		this.scms = scms;
	}

	public String getTdh() {
		return tdh;
	}

	public void setTdh(String tdh) {
		this.tdh = tdh;
	}

	public String getTpcc() {
		return tpcc;
	}

	public void setTpcc(String tpcc) {
		this.tpcc = tpcc;
	}

	public String getSjlx() {
		return sjlx;
	}

	public void setSjlx(String sjlx) {
		this.sjlx = sjlx;
	}

	public String getKtid() {
		return ktid;
	}

	public void setKtid(String ktid) {
		this.ktid = ktid;
	}

	public String getRlsb() {
		return rlsb;
	}

	public void setRlsb(String rlsb) {
		this.rlsb = rlsb;
	}

	public String getZbs() {
		return zbs;
	}

	public void setZbs(String zbs) {
		this.zbs = zbs;
	}

	public String getZpbh() {
		return zpbh;
	}

	public void setZpbh(String zpbh) {
		this.zpbh = zpbh;
	}

	public String getZpsjcd() {
		return zpsjcd;
	}

	public void setZpsjcd(String zpsjcd) {
		this.zpsjcd = zpsjcd;
	}

	public byte[] getZpscBytes(){
		byte[] bs=new byte[0];

		byte[] temp=zpbh.getBytes();
		bs= ByteUtil.byteMerger(bs, temp);
		System.out.println(bs.length);

		temp=bh.getBytes();
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		int n=Integer.valueOf(scms);
		temp[0]=(byte)n;
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		temp[0]=Byte.valueOf(tdh);
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		temp[0]=Byte.valueOf(tpcc);
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		temp[0]=Byte.valueOf(sjlx);
		bs=ByteUtil.byteMerger(bs, temp);

		//总包数默认20
		temp=ByteUtil.shortToByteArray(Short.valueOf(zbs));
		bs=ByteUtil.byteMerger(bs, temp);

		System.out.println(zpsjcd);
		temp=ByteUtil.intToByteArray(Integer.valueOf(zpsjcd));
		bs=ByteUtil.byteMerger(bs, temp);

		System.out.println(ktid);
		temp=ByteUtil.intToByteArray(Integer.valueOf(ktid));
		bs=ByteUtil.byteMerger(bs, temp);

		temp=new byte[1];
		temp[0]=Byte.valueOf(rlsb);
		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}


}
