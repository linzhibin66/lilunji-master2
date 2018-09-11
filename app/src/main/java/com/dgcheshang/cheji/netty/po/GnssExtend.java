package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * 位置附件信息
 * @author Administrator
 *
 */
public class GnssExtend {
	private Double lc;
	private Double yl;
	private Integer gd;
	private Integer fdjzs;
	public Double getLc() {
		return lc;
	}
	public void setLc(Double lc) {
		this.lc = lc;
	}
	public Double getYl() {
		return yl;
	}
	public void setYl(Double yl) {
		this.yl = yl;
	}
	public Integer getGd() {
		return gd;
	}
	public void setGd(Integer gd) {
		this.gd = gd;
	}
	public Integer getFdjzs() {
		return fdjzs;
	}
	public void setFdjzs(Integer fdjzs) {
		this.fdjzs = fdjzs;
	}

	/**
	 * 获取附加信息字节数据
	 * @return
	 */
	public byte[] getGnssExtendBytes(){
		byte[] b=new byte[0];
		byte[] temp=new byte[0];
		if(lc!=null){
			String id="01";
			temp=ByteUtil.hexStringToByte(id);
			b=ByteUtil.byteMerger(b, temp);

			temp=new byte[1];
			temp[0]=4;
			b= ByteUtil.byteMerger(b, temp);

			temp=ByteUtil.intToByteArray((int)(lc*10));
			b=ByteUtil.byteMerger(b, temp);

		}
		if(yl!=null){
			String id="02";
			temp=ByteUtil.hexStringToByte(id);
			b=ByteUtil.byteMerger(b, temp);

			temp=new byte[1];
			temp[0]=2;
			b=ByteUtil.byteMerger(b, temp);

			temp=ByteUtil.intToByteArray((int)(yl*10));
			b=ByteUtil.byteMerger(b, temp);

		}

		if(gd!=null){
			String id="03";
			temp=ByteUtil.hexStringToByte(id);
			b=ByteUtil.byteMerger(b, temp);

			temp=new byte[1];
			temp[0]=2;
			b=ByteUtil.byteMerger(b, temp);

			temp=ByteUtil.intToByteArray(gd);
			b=ByteUtil.byteMerger(b, temp);

		}

		if(fdjzs!=null){
			String id="05";
			temp=ByteUtil.hexStringToByte(id);
			b=ByteUtil.byteMerger(b, temp);

			temp=new byte[1];
			temp[0]=2;
			b=ByteUtil.byteMerger(b, temp);

			temp=ByteUtil.shortToByteArray(Short.valueOf(String.valueOf(fdjzs)));
			b=ByteUtil.byteMerger(b, temp);
		}
		return b;
	}
}
