package com.dgcheshang.cheji.netty.clientreply;

import com.dgcheshang.cheji.netty.po.Parameter;
import com.dgcheshang.cheji.netty.util.ByteUtil;
import java.util.List;

/**
 * 查询参数回复
 * @author Administrator
 *
 */
public class CxcsR implements java.io.Serializable{

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private int lsh;//应答流水号
	private int cscount;//应答参数个数
	private int csnum;//包参数个数
	private List<Parameter> pList;//参数项列表
	public int getLsh() {
		return lsh;
	}
	public void setLsh(int lsh) {
		this.lsh = lsh;
	}
	public int getCscount() {
		return cscount;
	}
	public void setCscount(int cscount) {
		this.cscount = cscount;
	}
	public int getCsnum() {
		return csnum;
	}
	public void setCsnum(int csnum) {
		this.csnum = csnum;
	}
	public List<Parameter> getpList() {
		return pList;
	}
	public void setpList(List<Parameter> pList) {
		this.pList = pList;
	}

	//生成字节码
	public byte[] getCxcsRBytes(){
		byte[] bs=new byte[0];
		byte[] temp= ByteUtil.shortToByteArray((short)lsh);
		bs=ByteUtil.byteMerger(bs, temp);

		cscount=pList.size();
		temp=new byte[1];
		temp[0]=(byte) cscount;
		bs=ByteUtil.byteMerger(bs, temp);

		csnum=pList.size();
		temp=new byte[1];
		temp[0]=(byte) csnum;
		bs=ByteUtil.byteMerger(bs, temp);

		for(Parameter p:pList){
			temp=p.getParameterBytes();
			bs=ByteUtil.byteMerger(bs, temp);
		}

		return bs;
	}


}
