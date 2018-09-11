package com.dgcheshang.cheji.netty.po;
import com.dgcheshang.cheji.netty.util.ByteUtil;

import org.apache.commons.lang3.StringUtils;

public class Zdkz implements java.io.Serializable{

	private static final long serialVersionUID = 1L;
	private String mlz;//命令字
	private String mlcs;//命令参数


	public String getMlz() {
		return mlz;
	}
	public void setMlz(String mlz) {
		this.mlz = mlz;
	}
	public String getMlcs() {
		return mlcs;
	}
	public void setMlcs(String mlcs) {
		this.mlcs = mlcs;
	}
	/**
	 * 获取终端控制的字节数组
	 * @return
	 */

	public byte[] getZdkzBytes(){
		//分割符
		byte[] sep=";".getBytes();

		byte[] bs=new byte[1];

		int k=Integer.parseInt(this.mlz);
		bs[0]=(byte) k;

		byte[] temp=new byte[0];
		String type="";
		if(mlz.equals("1")){
			//1.无线升级
			type="string,string,string,string,string,short,short,str,string,string,short";

		}else if(mlz.equals("2")){
			type="byte,string,string,string,string,string,short,short,short";
		}

		String[] types=type.split(",");
		String[] ss=this.mlcs.split(";");
		System.out.println(mlcs);
		for(int i=0;i<types.length;i++){
			String str=ss[i];
			if(StringUtils.isNotEmpty(str)){
				if(types[i].equals("string")){
					temp=str.getBytes();
				}else if(types[i].equals("short")){
					temp= ByteUtil.shortToByteArray(Short.valueOf(str));
				}else if(types[i].equals("byte")){
					temp=new byte[1];
					temp[0]=Byte.valueOf(str);
				}else if(types[i].equals("str")){
					temp=str.getBytes();
				}
			}else{
				temp=new byte[0];
			}
			bs=ByteUtil.byteMerger(bs, temp);
			if(i!=types.length-1){
				bs=ByteUtil.byteMerger(bs, sep);
			}
		}

		return bs;
	}
}
