package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

public class JlydlR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jg;//结果   1：登录成功；2：无效的教练员编号；3：准教车型不符；9：其他错误
	private String jlbh;//教练编号
	private int sfbd;//是否报读附加消息
	private int fjxxcd;//附加消息长度
	private String fjxx;//附加消息
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getJlbh() {
		return jlbh;
	}
	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
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


	public byte[] getJlydlbytes(){
		byte[] bs=null;
		byte[] b1=new byte[1];
		b1[0]=new Integer(this.jg).byteValue();

		byte[] b2=this.jlbh.getBytes();
		bs= ByteUtil.byteMerger(b1, b2);

		byte[] temp=new byte[1];
		temp[0]=new Integer(this.sfbd).byteValue();

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
