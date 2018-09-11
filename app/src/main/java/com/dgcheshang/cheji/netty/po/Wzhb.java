package com.dgcheshang.cheji.netty.po;

/**
 * 位置信息汇报
 * @author Administrator
 *
 */
public class Wzhb implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private String bjbz;//报警标识
	private String zt;//状态
	private String wd;//纬度
	private String jd;//经度
	private String xsjlsd;//行驶记录速度
	private String wxdwsd;//卫星定位速度
	private String fx;//方向
	private String sj;//时间
	private String lc;//里程
	private String yl;//油量
	private String gd;//高度
	private String fdjzs;//发动机转速

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
	public String getLc() {
		return lc;
	}
	public void setLc(String lc) {
		this.lc = lc;
	}
	public String getYl() {
		return yl;
	}
	public void setYl(String yl) {
		this.yl = yl;
	}
	public String getGd() {
		return gd;
	}
	public void setGd(String gd) {
		this.gd = gd;
	}
	public String getFdjzs() {
		return fdjzs;
	}
	public void setFdjzs(String fdjzs) {
		this.fdjzs = fdjzs;
	}

}
