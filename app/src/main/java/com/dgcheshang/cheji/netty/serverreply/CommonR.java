package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

public class CommonR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String zdid;//终端手机号
	private int lsh;//应答流水号
	private String msgid;//应答ID
	private int jg;//结果    0：成功/确认；1：失败；2：消息有误；3：不支持
	private String sj;//接收的时间

	public CommonR() {
		super();
	}

	public CommonR(int lsh, String msgid, int jg) {
		super();
		this.lsh = lsh;
		this.msgid = msgid;
		this.jg = jg;
	}

	public String getZdid() {
		return zdid;
	}

	public void setZdid(String zdid) {
		this.zdid = zdid;
	}

	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
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

	public String getSj() {
		return sj;
	}

	public void setSj(String sj) {
		this.sj = sj;
	}

	public byte[] getCommonRBytes(){
		byte[] bs=null;
		//消息流水号
		short i=(short) this.lsh;
		byte[] b1= ByteUtil.shortToByteArray(i);

		//消息ID
		byte[] b2=ByteUtil.hexStringToByte(this.getMsgid());

		bs=ByteUtil.byteMerger(b1, b2);

		byte[] temp=new byte[1];
		temp[0]=new Integer(this.jg).byteValue();

		bs=ByteUtil.byteMerger(bs, temp);
		return bs;
	}

}
