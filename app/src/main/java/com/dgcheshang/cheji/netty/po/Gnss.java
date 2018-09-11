package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;



/**
 * 生成gnss数据包
 * @author Administrator
 *
 */
public class Gnss {
	private String bjbz;
	private String zt;
	private String wd;
	private String jd;
	private String xsjlsd;
	private String wxdwsd;
	private String fx;
	private String sj;
	private String fjxx;
	public String getBjbz() {
		return bjbz;
	}
	public void setBjbz(String bjbz) {
		this.bjbz = bjbz;
	}
	public String getZt() {
		return zt;
	}
	public void setZt(String zt) {
		this.zt = zt;
	}
	public String getWd() {
		return wd;
	}
	public void setWd(String wd) {
		this.wd = wd;
	}
	public String getJd() {
		return jd;
	}
	public void setJd(String jd) {
		this.jd = jd;
	}
	public String getXsjlsd() {
		return xsjlsd;
	}
	public void setXsjlsd(String xsjlsd) {
		this.xsjlsd = xsjlsd;
	}
	public String getWxdwsd() {
		return wxdwsd;
	}
	public void setWxdwsd(String wxdwsd) {
		this.wxdwsd = wxdwsd;
	}
	public String getFx() {
		return fx;
	}
	public void setFx(String fx) {
		this.fx = fx;
	}
	public String getSj() {
		return sj;
	}
	public void setSj(String sj) {
		this.sj = sj;
	}

	public String getFjxx() {
		return fjxx;
	}
	public void setFjxx(String fjxx) {
		this.fjxx = fjxx;
	}
	public byte[] getGnssBytes(){
		byte[] b=new byte[0];
		byte[] temp= ByteUtil.intToByteArray(Integer.parseInt(bjbz,2));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.intToByteArray(Integer.parseInt(zt,2));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.intToByteArray((int)(Double.valueOf(wd)*1000000));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.intToByteArray((int)(Double.valueOf(jd)*1000000));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray((short)(Double.valueOf(xsjlsd)*10));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray((short)(Double.valueOf(wxdwsd)*10));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray(Short.valueOf(fx));
		b=ByteUtil.byteMerger(b, temp);

		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		Date d=new Date(Long.valueOf(sj));
		try {
			String time=sdf.format(d);

			temp=ByteUtil.str2Bcd(time);
			b=ByteUtil.byteMerger(b, temp);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(StringUtils.isNotEmpty(fjxx)){
			temp=ByteUtil.hexStringToByte(fjxx);
			b=ByteUtil.byteMerger(b, temp);
		}
		return b;
	}

}
