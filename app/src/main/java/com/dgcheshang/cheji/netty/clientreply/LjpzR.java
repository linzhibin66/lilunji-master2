package com.dgcheshang.cheji.netty.clientreply;


/**
 * 查询参数回复
 * @author Administrator
 *
 */
public class LjpzR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int jg;//执行结果
	private int scms;//上传模式
	private int tdh;//摄像头通道号
	private String sjcc;//图片实际尺寸
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public int getScms() {
		return scms;
	}
	public void setScms(int scms) {
		this.scms = scms;
	}
	public int getTdh() {
		return tdh;
	}
	public void setTdh(int tdh) {
		this.tdh = tdh;
	}
	public String getSjcc() {
		return sjcc;
	}
	public void setSjcc(String sjcc) {
		this.sjcc = sjcc;
	}


	/**
	 * 生产立即拍照字节数组
	 * @return
	 */
	public byte[] getLjpzRbytes(){
		byte[] bs=new byte[4];

		bs[0]=(byte) jg;
		bs[1]=(byte) scms;
		bs[2]=(byte) tdh;
		bs[3]=Byte.valueOf(sjcc);

		return bs;
	}


}
