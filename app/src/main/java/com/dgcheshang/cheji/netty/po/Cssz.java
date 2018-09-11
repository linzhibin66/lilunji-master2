package com.dgcheshang.cheji.netty.po;

/**
 * 终端参数设置
 * @author Administrator
 *
 */
public class Cssz implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String fsjg;//发送间隔
	private String tcpydcssj;//TCP应答超时时间
	private String tcpcccs;//TCP消息重传次数
	private String udpydcssj;//UDP消息应答超时时间
	private String udpcccs;//UDP消息重传次数
	private String smsydcssj;//SMS消息应答超时时间
	private String smscccs;//SMS消息重传次数
	private String fwqapn;//主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP拨号号码
	private String fwqwxyh;//主服务器无线通信拨号用户名
	private String fwqwxmm;//主服务器无线通信拨号密码
	private String fwqip;//主服务器地址，IP或域名
	private String bffwqfwd;//备份服务器APN，无线通信拨号访问点
	private String bffwqyh;//备份服务器无线通信拨号用户名
	private String bffwqmm;//备份服务器无线通信拨号密码
	private String bffwqip;//备份服务器地址，IP或域名
	private String tcpdk;//服务器TCP端口
	private String udpdk;//服务器UDP端口
	private String wzhbcl;//位置汇报策略，0：定时汇报；1：定距汇报；2：定时和定距汇报
	private String wzhbfn;//位置汇报方案，0：根据ACC状态；1：根据登录状态和ACC状态，先判断登录状态，若登录再根据ACC状态
	private String jsywdlhbsj;//驾驶员未登录汇报时间间隔，单位为秒(s),>0
	private String xmhbsj;//休眠时汇报时间间隔，单位为秒(s),>0
	private String jjbjsj;//紧急报警时汇报时间间隔，单位为秒(s),>0
	private String qssjhb;//缺省时间汇报间隔，单位为秒(s),>0
	private String qsjlhb;//缺省距离汇报间隔，单位为米(m),>0
	private String jsywdlhbjl;//驾驶员未登录汇报距离间隔，单位为米(m),>0
	private String xmhbjl;//休眠时汇报距离间隔，单位为米(m),>0
	private String jjbjbhjl;//紧急报警时汇报距离间隔，单位为米(m),>0
	private String gdbcjd;//拐点补传角度，<180°
	private String jkptdh;//监控平台电话号码
	private String fwdhhm;//复位电话号码，可采用此电话号码拨打终端电话让终端复位
	private String hfccdhhm;//恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复出厂设置
	private String jkptsmsdh;//监控平台SMS电话号码
	private String zdsmsbjhm;//接收终端SMS文本报警号码
	private String zddhjtcl;//终端电话接听策略，0：自动接听；1：ACC ON时自动接听，OFF时手动接听
	private String mczcth;//每次最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制
	private String dyzcth;//当月最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制
	private String jtdh;//监听电话号码
	private String jgptdxhm;//监管平台特权短信号码
	private String bjpjz;//报警屏蔽字。与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警被屏蔽
	private String bjfswb;//报警发送文本SMS开关，与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警时发送文本SMS
	private String bjpskg;//报警拍摄开关，与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警时摄像头拍摄
	private String bjpsccbs;//报警拍摄存储标识，与位置信息汇报消息中的报警标识相对应，相应
	private String gjbs;//关键标识，与位置信息汇报消息中的报警标识相对应，相应位为1则对相应报警为关键报警
	private String zgsd;//最高速度，单位为公里每小时(km/h)
	private String cscssj;//超速持续时间，单位为秒(s)
	private String lxjssjmx;//连续驾驶时间门限，单位为秒(s)
	private String dtljjssj;//当天累计驾驶时间门限，单位为秒(s)
	private String zxsj;//最小休息时间，单位为秒(s)
	private String zctcsj;//最长停车时间，单位为秒(s)
	private String txsp;//图像/视频质量，1-10,1最好
	private String ld;//亮度，0-255
	private String dbd;//对比度，0-127
	private String bhd;//饱和度，0-127
	private String sd;//色度，0-255
	private String cllcds;//车辆里程表读数，1/10km
	private String clsyid;//车辆所在的省域ID
	private String clsid;//车辆所在的市域ID
	private String jtbmbfhm;//公安交通管理部门颁发的机动车号牌
	private String clys;//车牌颜色，按照JT/T415-2006的5.4.12
	private String clycxs;//车辆脉冲系数，车辆行驶1km距离过程中产生的脉冲信号个数

	public String getFsjg() {
		return fsjg;
	}
	public void setFsjg(String fsjg) {
		this.fsjg = fsjg;
	}
	public String getTcpydcssj() {
		return tcpydcssj;
	}
	public void setTcpydcssj(String tcpydcssj) {
		this.tcpydcssj = tcpydcssj;
	}
	public String getTcpcccs() {
		return tcpcccs;
	}
	public void setTcpcccs(String tcpcccs) {
		this.tcpcccs = tcpcccs;
	}
	public String getUdpydcssj() {
		return udpydcssj;
	}
	public void setUdpydcssj(String udpydcssj) {
		this.udpydcssj = udpydcssj;
	}
	public String getUdpcccs() {
		return udpcccs;
	}
	public void setUdpcccs(String udpcccs) {
		this.udpcccs = udpcccs;
	}
	public String getSmsydcssj() {
		return smsydcssj;
	}
	public void setSmsydcssj(String smsydcssj) {
		this.smsydcssj = smsydcssj;
	}
	public String getSmscccs() {
		return smscccs;
	}
	public void setSmscccs(String smscccs) {
		this.smscccs = smscccs;
	}
	public String getFwqapn() {
		return fwqapn;
	}
	public void setFwqapn(String fwqapn) {
		this.fwqapn = fwqapn;
	}
	public String getFwqwxyh() {
		return fwqwxyh;
	}
	public void setFwqwxyh(String fwqwxyh) {
		this.fwqwxyh = fwqwxyh;
	}
	public String getFwqwxmm() {
		return fwqwxmm;
	}
	public void setFwqwxmm(String fwqwxmm) {
		this.fwqwxmm = fwqwxmm;
	}
	public String getFwqip() {
		return fwqip;
	}
	public void setFwqip(String fwqip) {
		this.fwqip = fwqip;
	}
	public String getBffwqfwd() {
		return bffwqfwd;
	}
	public void setBffwqfwd(String bffwqfwd) {
		this.bffwqfwd = bffwqfwd;
	}
	public String getBffwqyh() {
		return bffwqyh;
	}
	public void setBffwqyh(String bffwqyh) {
		this.bffwqyh = bffwqyh;
	}
	public String getBffwqmm() {
		return bffwqmm;
	}
	public void setBffwqmm(String bffwqmm) {
		this.bffwqmm = bffwqmm;
	}
	public String getBffwqip() {
		return bffwqip;
	}
	public void setBffwqip(String bffwqip) {
		this.bffwqip = bffwqip;
	}
	public String getTcpdk() {
		return tcpdk;
	}
	public void setTcpdk(String tcpdk) {
		this.tcpdk = tcpdk;
	}
	public String getUdpdk() {
		return udpdk;
	}
	public void setUdpdk(String udpdk) {
		this.udpdk = udpdk;
	}
	public String getWzhbcl() {
		return wzhbcl;
	}
	public void setWzhbcl(String wzhbcl) {
		this.wzhbcl = wzhbcl;
	}
	public String getWzhbfn() {
		return wzhbfn;
	}
	public void setWzhbfn(String wzhbfn) {
		this.wzhbfn = wzhbfn;
	}
	public String getJsywdlhbsj() {
		return jsywdlhbsj;
	}
	public void setJsywdlhbsj(String jsywdlhbsj) {
		this.jsywdlhbsj = jsywdlhbsj;
	}
	public String getXmhbsj() {
		return xmhbsj;
	}
	public void setXmhbsj(String xmhbsj) {
		this.xmhbsj = xmhbsj;
	}
	public String getJjbjsj() {
		return jjbjsj;
	}
	public void setJjbjsj(String jjbjsj) {
		this.jjbjsj = jjbjsj;
	}
	public String getQssjhb() {
		return qssjhb;
	}
	public void setQssjhb(String qssjhb) {
		this.qssjhb = qssjhb;
	}
	public String getQsjlhb() {
		return qsjlhb;
	}
	public void setQsjlhb(String qsjlhb) {
		this.qsjlhb = qsjlhb;
	}
	public String getJsywdlhbjl() {
		return jsywdlhbjl;
	}
	public void setJsywdlhbjl(String jsywdlhbjl) {
		this.jsywdlhbjl = jsywdlhbjl;
	}
	public String getXmhbjl() {
		return xmhbjl;
	}
	public void setXmhbjl(String xmhbjl) {
		this.xmhbjl = xmhbjl;
	}
	public String getJjbjbhjl() {
		return jjbjbhjl;
	}
	public void setJjbjbhjl(String jjbjbhjl) {
		this.jjbjbhjl = jjbjbhjl;
	}
	public String getGdbcjd() {
		return gdbcjd;
	}
	public void setGdbcjd(String gdbcjd) {
		this.gdbcjd = gdbcjd;
	}
	public String getJkptdh() {
		return jkptdh;
	}
	public void setJkptdh(String jkptdh) {
		this.jkptdh = jkptdh;
	}
	public String getFwdhhm() {
		return fwdhhm;
	}
	public void setFwdhhm(String fwdhhm) {
		this.fwdhhm = fwdhhm;
	}
	public String getHfccdhhm() {
		return hfccdhhm;
	}
	public void setHfccdhhm(String hfccdhhm) {
		this.hfccdhhm = hfccdhhm;
	}
	public String getJkptsmsdh() {
		return jkptsmsdh;
	}
	public void setJkptsmsdh(String jkptsmsdh) {
		this.jkptsmsdh = jkptsmsdh;
	}
	public String getZdsmsbjhm() {
		return zdsmsbjhm;
	}
	public void setZdsmsbjhm(String zdsmsbjhm) {
		this.zdsmsbjhm = zdsmsbjhm;
	}
	public String getZddhjtcl() {
		return zddhjtcl;
	}
	public void setZddhjtcl(String zddhjtcl) {
		this.zddhjtcl = zddhjtcl;
	}
	public String getMczcth() {
		return mczcth;
	}
	public void setMczcth(String mczcth) {
		this.mczcth = mczcth;
	}
	public String getDyzcth() {
		return dyzcth;
	}
	public void setDyzcth(String dyzcth) {
		this.dyzcth = dyzcth;
	}
	public String getJtdh() {
		return jtdh;
	}
	public void setJtdh(String jtdh) {
		this.jtdh = jtdh;
	}
	public String getJgptdxhm() {
		return jgptdxhm;
	}
	public void setJgptdxhm(String jgptdxhm) {
		this.jgptdxhm = jgptdxhm;
	}
	public String getBjpjz() {
		return bjpjz;
	}
	public void setBjpjz(String bjpjz) {
		this.bjpjz = bjpjz;
	}
	public String getBjfswb() {
		return bjfswb;
	}
	public void setBjfswb(String bjfswb) {
		this.bjfswb = bjfswb;
	}
	public String getBjpskg() {
		return bjpskg;
	}
	public void setBjpskg(String bjpskg) {
		this.bjpskg = bjpskg;
	}
	public String getBjpsccbs() {
		return bjpsccbs;
	}
	public void setBjpsccbs(String bjpsccbs) {
		this.bjpsccbs = bjpsccbs;
	}
	public String getGjbs() {
		return gjbs;
	}
	public void setGjbs(String gjbs) {
		this.gjbs = gjbs;
	}
	public String getZgsd() {
		return zgsd;
	}
	public void setZgsd(String zgsd) {
		this.zgsd = zgsd;
	}
	public String getCscssj() {
		return cscssj;
	}
	public void setCscssj(String cscssj) {
		this.cscssj = cscssj;
	}
	public String getLxjssjmx() {
		return lxjssjmx;
	}
	public void setLxjssjmx(String lxjssjmx) {
		this.lxjssjmx = lxjssjmx;
	}
	public String getDtljjssj() {
		return dtljjssj;
	}
	public void setDtljjssj(String dtljjssj) {
		this.dtljjssj = dtljjssj;
	}
	public String getZxsj() {
		return zxsj;
	}
	public void setZxsj(String zxsj) {
		this.zxsj = zxsj;
	}
	public String getZctcsj() {
		return zctcsj;
	}
	public void setZctcsj(String zctcsj) {
		this.zctcsj = zctcsj;
	}
	public String getTxsp() {
		return txsp;
	}
	public void setTxsp(String txsp) {
		this.txsp = txsp;
	}
	public String getLd() {
		return ld;
	}
	public void setLd(String ld) {
		this.ld = ld;
	}
	public String getDbd() {
		return dbd;
	}
	public void setDbd(String dbd) {
		this.dbd = dbd;
	}
	public String getBhd() {
		return bhd;
	}
	public void setBhd(String bhd) {
		this.bhd = bhd;
	}
	public String getSd() {
		return sd;
	}
	public void setSd(String sd) {
		this.sd = sd;
	}
	public String getCllcds() {
		return cllcds;
	}
	public void setCllcds(String cllcds) {
		this.cllcds = cllcds;
	}
	public String getClsyid() {
		return clsyid;
	}
	public void setClsyid(String clsyid) {
		this.clsyid = clsyid;
	}
	public String getClsid() {
		return clsid;
	}
	public void setClsid(String clsid) {
		this.clsid = clsid;
	}
	public String getJtbmbfhm() {
		return jtbmbfhm;
	}
	public void setJtbmbfhm(String jtbmbfhm) {
		this.jtbmbfhm = jtbmbfhm;
	}
	public String getClys() {
		return clys;
	}
	public void setClys(String clys) {
		this.clys = clys;
	}
	public String getClycxs() {
		return clycxs;
	}
	public void setClycxs(String clycxs) {
		this.clycxs = clycxs;
	}
}
