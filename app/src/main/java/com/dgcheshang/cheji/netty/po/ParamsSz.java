package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.util.List;

public class ParamsSz implements java.io.Serializable{

	/**
	 * 参数设置
	 */
	private static final long serialVersionUID = 1L;
	private int count;//参数总数
	private int fsnum;//分包参数个数
	private List<Parameter> paramList;//参数项列表
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public int getFsnum() {
		return fsnum;
	}
	public void setFsnum(int fsnum) {
		this.fsnum = fsnum;
	}
	public List<Parameter> getParamList() {
		return paramList;
	}
	public void setParamList(List<Parameter> paramList) {
		this.paramList = paramList;
	}


	public byte[] getParamsSzBytes(){
		fsnum=paramList.size();

		byte[] bs=null;

		byte[] b1=new byte[1];
		b1[0]=(byte) count;

		byte[] b2=new byte[1];
		b2[0]=(byte) fsnum;

		bs= ByteUtil.byteMerger(b1, b2);

		byte[] b3=null;
		for(Parameter p:paramList){
			byte[] temp=p.getParameterBytes();
			if(b3==null){
				b3=temp;
			}else{
				b3=ByteUtil.byteMerger(b3, temp);
			}
		}
		bs=ByteUtil.byteMerger(bs, b3);
		return bs;
	}

}
