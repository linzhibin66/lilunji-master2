package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.text.SimpleDateFormat;

public class Cxxsjl implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String cxfs;//查询方式
	private String kssj;//开始时间
	private String jssj;//结束时间
	private String cxts;//查询条数

	public String getCxfs() {
		return cxfs;
	}
	public void setCxfs(String cxfs) {
		this.cxfs = cxfs;
	}
	public String getKssj() {
		return kssj;
	}
	public void setKssj(String kssj) {
		this.kssj = kssj;
	}
	public String getJssj() {
		return jssj;
	}
	public void setJssj(String jssj) {
		this.jssj = jssj;
	}
	public String getCxts() {
		return cxts;
	}
	public void setCxts(String cxts) {
		this.cxts = cxts;
	}


	public byte[] getCxxsjlBytes() throws Exception{
		byte[] bs=null;
		byte[] b1=new byte[1];
		b1[0]=Byte.valueOf(cxfs);

		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");

		String s1=sdf2.format(sdf.parse(kssj));
		byte[] b2= ByteUtil.str2Bcd(s1);

		bs=ByteUtil.byteMerger(b1, b2);

		String s2=sdf2.format(sdf.parse(jssj));
		byte[] temp=ByteUtil.str2Bcd(s2);

		bs=ByteUtil.byteMerger(bs, temp);

		short i=Short.valueOf(cxts);
		temp=ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}

}
