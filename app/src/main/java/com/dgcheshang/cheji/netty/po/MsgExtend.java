package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.certificate.Sign;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ByteUtil;
import org.apache.commons.lang3.StringUtils;


public class MsgExtend implements java.io.Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String ywlx;//业务类型0x13为驾培业务
	private String msgid;//透传消息ID
	private String msgsx;//扩展消息属性
	private String sxx;//0：实时消息，1：补传消息；
	private String ydsx;//应答属性0：不需要应答，1：需要应答；
	private String sfjm;//0：未加密，1：SHA1，2：SHA256；
	private int jpbxh;//驾培包序号
	private String jszdbh;//计时终端编号
	private int sjcd;//数据长度
	private byte[] nr;//数据内容
	private byte[] content;//待验签内容
	private String sign;//校验串
	private Object o;


	public String getYwlx() {
		return ywlx;
	}
	public void setYwlx(String ywlx) {
		this.ywlx = ywlx;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getMsgsx() {
		return msgsx;
	}
	public void setMsgsx(String msgsx) {
		this.msgsx = msgsx;
	}
	public int getJpbxh() {
		return jpbxh;
	}
	public void setJpbxh(int jpbxh) {
		this.jpbxh = jpbxh;
	}
	public String getJszdbh() {
		return jszdbh;
	}
	public void setJszdbh(String jszdbh) {
		this.jszdbh = jszdbh;
	}
	public int getSjcd() {
		return sjcd;
	}
	public void setSjcd(int sjcd) {
		this.sjcd = sjcd;
	}

	public byte[] getNr() {
		return nr;
	}
	public void setNr(byte[] nr) {
		this.nr = nr;
	}
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public String getSxx() {
		return sxx;
	}
	public void setSxx(String sxx) {
		this.sxx = sxx;
	}
	public String getYdsx() {
		return ydsx;
	}
	public void setYdsx(String ydsx) {
		this.ydsx = ydsx;
	}
	public String getSfjm() {
		return sfjm;
	}
	public void setSfjm(String sfjm) {
		this.sfjm = sfjm;
	}
	public byte[] getContent() {
		return content;
	}
	public void setContent(byte[] content) {
		this.content = content;
	}
	public Object getO() {
		return o;
	}
	public void setO(Object o) {
		this.o = o;
	}
	public byte[] getMsgExtendbytes(){
		byte[] bs=null;

		byte[] b0= ByteUtil.hexStringToByte("13");


		byte[] b1=ByteUtil.hexStringToByte(msgid);
		bs=ByteUtil.byteMerger(b0, b1);

		byte[] b2=ByteUtil.str2Tobytes(msgsx);
		bs=ByteUtil.byteMerger(bs, b2);

		short i=(short) this.jpbxh;
		byte[] temp=ByteUtil.shortToByteArray(i);
		bs=ByteUtil.byteMerger(bs, temp);

		temp=this.jszdbh.getBytes();
		bs=ByteUtil.byteMerger(bs, temp);

		if(nr!=null){
			this.sjcd=nr.length;
		}
		temp=ByteUtil.intToByteArray(sjcd);
		bs=ByteUtil.byteMerger(bs, temp);
		if(nr!=null){
			bs=ByteUtil.byteMerger(bs, nr);
		}
		if(StringUtils.isNotEmpty(sfjm)&&sfjm.equals("2")){
			try {
				byte[] dsj=ByteUtil.subBytes(bs, 1, bs.length-1);
				String sign= Sign.sign(dsj, NettyConf.key);
				temp=ByteUtil.hexStringToByte(sign);
				bs=ByteUtil.byteMerger(bs, temp);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return bs;
	}

}
