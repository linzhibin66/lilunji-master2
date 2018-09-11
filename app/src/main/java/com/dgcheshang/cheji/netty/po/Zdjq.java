package com.dgcheshang.cheji.netty.po;


import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

/**
 * 终端鉴权
 * @author Administrator
 *
 */
public class Zdjq implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String sjc;//时间戳
	private String jqmw;//鉴权密文

	public String getSjc() {
		return sjc;
	}
	public void setSjc(String sjc) {
		this.sjc = sjc;
	}
	public String getJqmw() {
		return jqmw;
	}
	public void setJqmw(String jqmw) {
		this.jqmw = jqmw;
	}

	public byte[] getZdjqbytes(){
		byte[] b=new byte[0];
		byte[] temp= ByteUtil.intToByteArray(Integer.valueOf(sjc));
		b=ByteUtil.byteMerger(b, temp);

		if(StringUtils.isNotEmpty(jqmw)){
			temp=ByteUtil.hexStringToByte(jqmw);
			b=ByteUtil.byteMerger(b, temp);
		}
		return b;

	}


}
