package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

public class XydlR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jg;//结果   1：登录成功；2：无效的教练员编号；3：准教车型不符；9：其他错误
	private String xybh;//学员编号
	private int zpxxs;//总培训学时
	private int wcxs;//当前培训部分已完成学时
	private int zpxlc;//总培训里程
	private int wclc;//当前培训部分已完成里程
	private int sfbd;
	private int fjxxcd;
	private String fjxx;
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getXybh() {
		return xybh;
	}
	public void setXybh(String xybh) {
		this.xybh = xybh;
	}
	public int getZpxxs() {
		return zpxxs;
	}
	public void setZpxxs(int zpxxs) {
		this.zpxxs = zpxxs;
	}
	public int getWcxs() {
		return wcxs;
	}
	public void setWcxs(int wcxs) {
		this.wcxs = wcxs;
	}
	public int getZpxlc() {
		return zpxlc;
	}
	public void setZpxlc(int zpxlc) {
		this.zpxlc = zpxlc;
	}
	public int getWclc() {
		return wclc;
	}
	public void setWclc(int wclc) {
		this.wclc = wclc;
	}
	public int getSfbd() {
		return sfbd;
	}
	public void setSfbd(int sfbd) {
		this.sfbd = sfbd;
	}
	public int getFjxxcd() {
		return fjxxcd;
	}
	public void setFjxxcd(int fjxxcd) {
		this.fjxxcd = fjxxcd;
	}
	public String getFjxx() {
		return fjxx;
	}
	public void setFjxx(String fjxx) {
		this.fjxx = fjxx;
	}
	public byte[] getXydlbytes(){
		byte[] bs=null;
		byte[] b1=new byte[1];
		b1[0]=new Integer(this.jg).byteValue();

		byte[] b2=this.xybh.getBytes();

		bs=ByteUtil.byteMerger(b1, b2);

		short i=(short) this.zpxxs;
		byte[] temp= ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);

		i=(short) this.wcxs;
		temp=ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);

		i=(short) this.zpxlc;
		temp=ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);

		i=(short) this.wclc;
		temp=ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);

		byte y=(byte)this.sfbd;
		temp=new byte[1];
		temp[0]=y;
		bs=ByteUtil.byteMerger(bs, temp);

		try {
			if(StringUtils.isNotEmpty(this.fjxx)){
				String s=new String(this.fjxx.getBytes(),"GBK");
				fjxxcd=s.getBytes().length;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		temp=new byte[1];
		temp[0]=new Integer(this.fjxxcd).byteValue();

		bs=ByteUtil.byteMerger(bs, temp);

		try {
			if(StringUtils.isNotEmpty(this.fjxx)){
				String s=new String(this.fjxx.getBytes(),"GBK");
				temp=s.getBytes();
				bs=ByteUtil.byteMerger(bs, temp);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		//补零位
		temp=new byte[1];
		temp[0]=0;
		bs=ByteUtil.byteMerger(bs, temp);

		return bs;
	}

}
