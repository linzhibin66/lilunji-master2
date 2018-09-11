package com.dgcheshang.cheji.netty.serverreply;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;



public class ZdzcR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int lsh;//应答流水号
	private int jg;//结果   0：成功；1：车辆已被注册；2：数据库中无该车辆；3：终端已被注册4：数据库中无该终端。只有在成功后才返回以下内容
	private String ptbh;//平台编号
	private String pxjgbh;//培训机构编号
	private String jszdbh;//计时终端编号
	private String zskl;//证书口令
	private String zs;//终端证书



	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getPtbh() {
		return ptbh;
	}
	public void setPtbh(String ptbh) {
		this.ptbh = ptbh;
	}
	public String getPxjgbh() {
		return pxjgbh;
	}
	public void setPxjgbh(String pxjgbh) {
		this.pxjgbh = pxjgbh;
	}
	public String getJszdbh() {
		return jszdbh;
	}
	public void setJszdbh(String jszdbh) {
		this.jszdbh = jszdbh;
	}
	public String getZskl() {
		return zskl;
	}
	public void setZskl(String zskl) {
		this.zskl = zskl;
	}
	public String getZs() {
		return zs;
	}
	public void setZs(String zs) {
		this.zs = zs;
	}

	public byte[] getZdzcRbytes(){
		byte[] bs=null;
		//消息流水号
		short i=(short) this.lsh;
		byte[] b1= ByteUtil.shortToByteArray(i);

		//结果
		byte[] b2=new byte[1];
		b2[0]=new Integer(this.jg).byteValue();

		bs=ByteUtil.byteMerger(b1, b2);

		if(jg==0){
			//平台编号
			byte[] temp=this.ptbh.getBytes();
			bs=ByteUtil.byteMerger(bs, temp);

			//培训机构编号
			temp=this.pxjgbh.getBytes();
			bs=ByteUtil.byteMerger(bs, temp);

			//计时终端编号
			temp=this.jszdbh.getBytes();
			bs=ByteUtil.byteMerger(bs, temp);

			//证书口令
			temp=this.zskl.getBytes();
			String hs=ByteUtil.bytesToHexString(temp);
			temp=ByteUtil.hexStringTOFinalbyte(hs, 12, 2);
			bs=ByteUtil.byteMerger(bs, temp);

			//终端证书
			if(StringUtils.isNotEmpty(zs)){
				temp=this.zs.getBytes();
				bs=ByteUtil.byteMerger(bs, temp);
			}
			//补零位
			temp=new byte[1];
			temp[0]=0;
			bs=ByteUtil.byteMerger(bs, temp);

		}
		System.out.println(bs.length);
		return bs;
	}
}
