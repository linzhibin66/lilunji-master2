package com.dgcheshang.cheji.netty.clientreply;


import com.dgcheshang.cheji.netty.util.ByteUtil;
import org.apache.commons.lang3.StringUtils;

/**
 * 应用参数设置回复
 * @author Administrator
 *
 */
public class JxztR implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private int jg;//执行结果
	private int zt;
	private int cd;
	private String tsxx;
	public int getJg() {
		return jg;
	}
	public void setJg(int jg) {
		this.jg = jg;
	}
	public int getZt() {
		return zt;
	}
	public void setZt(int zt) {
		this.zt = zt;
	}
	public int getCd() {
		return cd;
	}
	public void setCd(int cd) {
		this.cd = cd;
	}
	public String getTsxx() {
		return tsxx;
	}
	public void setTsxx(String tsxx) {
		this.tsxx = tsxx;
	}

	/**
	 * 获取禁训状态的回复字节数组
	 * @return
	 */
	public byte[] getJxztRbytes(){
		byte[] bs=new byte[3];
		bs[0]=(byte) jg;
		bs[1]=(byte) zt;
		if(StringUtils.isNotEmpty(tsxx)){
			bs[2]=(byte) tsxx.getBytes().length;
			byte[] temp=tsxx.getBytes();
			bs= ByteUtil.byteMerger(bs, temp);

			//补零位
			temp=new byte[1];
			temp[0]=0;
			bs=ByteUtil.byteMerger(bs, temp);
		}else{
			bs[2]=0;
		}

		return bs;
	}



}
