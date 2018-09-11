package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 学时记录
 * @author Administrator
 *
 */
public class Xsjl implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String sxx;//时效性
	private String jszdbh;//计时终端编号
	private String xsjlbh;//学时记录编号
	private String sblx;//上报类型
	private String xybh;//学员编号
	private String jlbh;//教练编号
	private String ktid;//课堂ID
	private String jlcssj;//记录产生时间
	private String pxkc;//培训课程
	private String jlzt;//记录状态
	private String zdsd;//最大速度
	private String xclc;//学车里程
	private String gnss;

	public String getSxx() {
		return sxx;
	}
	public void setSxx(String sxx) {
		this.sxx = sxx;
	}
	public String getJszdbh() {
		return jszdbh;
	}
	public void setJszdbh(String jszdbh) {
		this.jszdbh = jszdbh;
	}
	public String getXsjlbh() {
		return xsjlbh;
	}
	public void setXsjlbh(String xsjlbh) {
		this.xsjlbh = xsjlbh;
	}
	public String getSblx() {
		return sblx;
	}
	public void setSblx(String sblx) {
		this.sblx = sblx;
	}
	public String getXybh() {
		return xybh;
	}
	public void setXybh(String xybh) {
		this.xybh = xybh;
	}
	public String getJlbh() {
		return jlbh;
	}
	public void setJlbh(String jlbh) {
		this.jlbh = jlbh;
	}
	public String getKtid() {
		return ktid;
	}
	public void setKtid(String ktid) {
		this.ktid = ktid;
	}
	public String getJlcssj() {
		return jlcssj;
	}
	public void setJlcssj(String jlcssj) {
		this.jlcssj = jlcssj;
	}
	public String getPxkc() {
		return pxkc;
	}
	public void setPxkc(String pxkc) {
		this.pxkc = pxkc;
	}
	public String getJlzt() {
		return jlzt;
	}
	public void setJlzt(String jlzt) {
		this.jlzt = jlzt;
	}
	public String getZdsd() {
		return zdsd;
	}
	public void setZdsd(String zdsd) {
		this.zdsd = zdsd;
	}
	public String getXclc() {
		return xclc;
	}
	public void setXclc(String xclc) {
		this.xclc = xclc;
	}

	public String getGnss() {
		return gnss;
	}
	public void setGnss(String gnss) {
		this.gnss = gnss;
	}


	public byte[] getXsjlBytes(){
		byte[] b=new byte[0];
		byte[] temp=xsjlbh.getBytes();
		b= ByteUtil.byteMerger(b, temp);

		//temp=ByteUtil.hexStringToByte(sblx);
		temp=new byte[1];
		temp[0]=Byte.valueOf(sblx);
		b=ByteUtil.byteMerger(b, temp);

		temp=xybh.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		temp=jlbh.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.intToByteArray(Integer.valueOf(ktid));
		b=ByteUtil.byteMerger(b, temp);

		SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
		try {
			jlcssj=sdf.format(new Date());
			temp=ByteUtil.str2Bcd(jlcssj);
			b=ByteUtil.byteMerger(b, temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		temp=ByteUtil.str2Bcd(pxkc);
		b=ByteUtil.byteMerger(b, temp);

		temp=new byte[1];
		temp[0]=Byte.valueOf(jlzt);
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray((short)(Double.valueOf(zdsd)*10));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray((short)(Double.valueOf(xclc)*10));
		b=ByteUtil.byteMerger(b, temp);
		temp=ByteUtil.hexStringToByte(gnss);
		b=ByteUtil.byteMerger(b, temp);
		return b;
	}

}
