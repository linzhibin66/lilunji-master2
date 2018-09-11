package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

public class Header implements java.io.Serializable{
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	// 协议版本号
	private int protver;
	// 消息ID
	private String msgid;
	// 消息体属性
	private String bodyprop;
	//是否分包 0.不分包 1.分包
	private String sffb;
	//加密方式 0.没加密 1.RSA加密
	private String jmfs;
	//数据长度
	private int sjcd;
	// 终端手机号
	private String mobileno;
	// 消息流水号
	private int msgserno;
	//预留字段
	private int ylzd;
	// 消息总包数
	private int msgpackcnt;
	// 包序号
	private int packsortno;


	public int getProtver() {
		return protver;
	}
	public void setProtver(int protver) {
		this.protver = protver;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getBodyprop() {
		return bodyprop;
	}
	public void setBodyprop(String bodyprop) {
		this.bodyprop = bodyprop;
	}
	public String getMobileno() {
		return mobileno;
	}
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}
	public int getMsgserno() {
		return msgserno;
	}
	public void setMsgserno(int msgserno) {
		this.msgserno = msgserno;
	}
	public int getYlzd() {
		return ylzd;
	}
	public void setYlzd(int ylzd) {
		this.ylzd = ylzd;
	}
	public int getMsgpackcnt() {
		return msgpackcnt;
	}
	public void setMsgpackcnt(int msgpackcnt) {
		this.msgpackcnt = msgpackcnt;
	}
	public int getPacksortno() {
		return packsortno;
	}
	public void setPacksortno(int packsortno) {
		this.packsortno = packsortno;
	}
	public String getSffb() {
		return sffb;
	}
	public void setSffb(String sffb) {
		this.sffb = sffb;
	}
	public String getJmfs() {
		return jmfs;
	}
	public void setJmfs(String jmfs) {
		this.jmfs = jmfs;
	}
	public int getSjcd() {
		return sjcd;
	}
	public void setSjcd(int sjcd) {
		this.sjcd = sjcd;
	}

	/**
	 * 转换成字节数组
	 * @return
	 */

	public byte[] getHeaderBytes(){
		byte[] bs=null;

		//协议号
		byte[] b1=new byte[1];
		b1[0]=new Integer(this.protver).byteValue();

		//消息ID
		byte[] b2= ByteUtil.hexStringToByte(this.getMsgid());

		bs=ByteUtil.byteMerger(b1, b2);

		//消息体属性
		byte[] temp=ByteUtil.str2Tobytes(this.bodyprop);

		bs=ByteUtil.byteMerger(bs, temp);

		//终端手机号
		temp=ByteUtil.str2Bcd(this.mobileno);
		if(temp.length<8){
			String hex=ByteUtil.bytesToHexString(temp);
			for(int i=temp.length;i<8;i++){
				hex="00"+hex;
			}
			temp=ByteUtil.hexStringToByte(hex);
		}

		bs=ByteUtil.byteMerger(bs, temp);

		//消息流水号
		short i=(short) this.getMsgserno();
		temp=ByteUtil.shortToByteArray(i);

		bs=ByteUtil.byteMerger(bs, temp);

		//预留
		temp=new byte[1];
		temp[0]=(byte) ylzd;
		bs=ByteUtil.byteMerger(bs, temp);

		//看分包总数是否为0
		if(this.msgpackcnt>1){
			i=(short) this.msgpackcnt;
			temp=ByteUtil.shortToByteArray(i);

			bs=ByteUtil.byteMerger(bs, temp);

			i=(short) this.packsortno;
			temp=ByteUtil.shortToByteArray(i);

			bs=ByteUtil.byteMerger(bs, temp);
		}

		return bs;
	}

}
