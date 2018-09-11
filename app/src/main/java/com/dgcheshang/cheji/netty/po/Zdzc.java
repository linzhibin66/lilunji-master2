package com.dgcheshang.cheji.netty.po;

import android.util.Log;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;

/**
 * 终端注册
 * @author Administrator
 *
 */
public class Zdzc implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String syid;//省域ID
	private String sxyid;//市县域ID
	private String zzsid;//制造商id
	private String zdxh;//终端型号
	private String xlh;//序列号
	private String imei;//imei

	public String getSyid() {
		return syid;
	}
	public void setSyid(String syid) {
		this.syid = syid;
	}
	public String getSxyid() {
		return sxyid;
	}
	public void setSxyid(String sxyid) {
		this.sxyid = sxyid;
	}
	public String getZzsid() {
		return zzsid;
	}
	public void setZzsid(String zzsid) {
		this.zzsid = zzsid;
	}
	public String getZdxh() {
		return zdxh;
	}
	public void setZdxh(String zdxh) {
		this.zdxh = zdxh;
	}
	public String getXlh() {
		return xlh;
	}
	public void setXlh(String xlh) {
		this.xlh = xlh;
	}
	public String getImei() {
		return imei;
	}
	public void setImei(String imei) {
		this.imei = imei;
	}

	public byte[] getZdzcbytes(){
		byte[] b=new byte[0];

		byte[] temp= ByteUtil.shortToByteArray(Short.valueOf(syid));
		b=ByteUtil.byteMerger(b, temp);

		temp=ByteUtil.shortToByteArray(Short.valueOf(sxyid));
		b=ByteUtil.byteMerger(b, temp);

		temp=zzsid.getBytes();
		temp=ByteUtil.hexStringTOFinalbyte(ByteUtil.bytesToHexString(temp), 5, 1);
		b=ByteUtil.byteMerger(b, temp);

		if(NettyConf.debug){
			Log.e("TAG","终端型号："+zdxh);
		}
		temp=zdxh.getBytes();
		temp=ByteUtil.hexStringTOFinalbyte(ByteUtil.bytesToHexString(temp), 20, 2);
		b=ByteUtil.byteMerger(b, temp);

		temp=xlh.getBytes();
		temp=ByteUtil.hexStringTOFinalbyte(ByteUtil.bytesToHexString(temp), 7, 2);
		b=ByteUtil.byteMerger(b, temp);

		temp=imei.getBytes();
		b=ByteUtil.byteMerger(b, temp);

		return b;
	}
}
