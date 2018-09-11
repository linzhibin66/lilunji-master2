package com.dgcheshang.cheji.netty.po;
import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.text.SimpleDateFormat;

public class Cxzp implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String cxfs;//1按时间查询
	private String kssj;//开始时间
	private String jssj;//结束时间

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

	public byte[] getCxzpBytes() throws Exception{
		byte[] bs=null;

		byte[] b1=new byte[1];
		b1[0]=Byte.valueOf(cxfs);


		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		SimpleDateFormat sdf2=new SimpleDateFormat("yyMMddHHmmss");
		byte[] b2= ByteUtil.str2Bcd(sdf2.format(sdf.parse(kssj)));

		bs=ByteUtil.byteMerger(b1, b2);

		byte[] temp=ByteUtil.str2Bcd(sdf2.format(sdf.parse(jssj)));

		bs=ByteUtil.byteMerger(bs,temp);
		System.out.println(bs.length);
		return bs;
	}
}
