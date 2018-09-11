package com.dgcheshang.cheji.netty.serverreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;

public class QqtybhR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int jpbxh;//驾培包序号
	private int jg;//查询结果
	private String tybh;//统一编号
	private String zjcx;//准（教/考）车型
	public int getJpbxh() {
		return jpbxh;
	}
	public void setJpbxh(int jpbxh) {
		this.jpbxh = jpbxh;
	}
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public String getTybh() {
		return tybh;
	}
	public void setTybh(String tybh) {
		this.tybh = tybh;
	}
	public String getZjcx() {
		return zjcx;
	}
	public void setZjcx(String zjcx) {
		this.zjcx = zjcx;
	}
	public byte[] getQqtybhRbytes(){
		byte[] bs=null;

		short i=(short) this.jpbxh;
		byte[] b1= ByteUtil.shortToByteArray(i);

		byte[] b2=new byte[1];
		b2[0]=new Integer(this.jg).byteValue();

		bs=ByteUtil.byteMerger(b1, b2);

		if(jg==0){
			byte[] temp=this.tybh.getBytes();

			if(temp.length<18){
				String hex=ByteUtil.bytesToHexString(temp);
				for(int k=temp.length;k<18;i++){
					hex="00"+hex;
				}
				temp=ByteUtil.hexStringToByte(hex);
			}
			bs=ByteUtil.byteMerger(bs, temp);


			if(zjcx!=null){
				temp=this.zjcx.getBytes();
				bs=ByteUtil.byteMerger(bs, temp);
			}else{
				String hs="0000";
				temp=ByteUtil.hexStringToByte(hs);
				bs=ByteUtil.byteMerger(bs, temp);
			}
		}
		return bs;
	}

}
