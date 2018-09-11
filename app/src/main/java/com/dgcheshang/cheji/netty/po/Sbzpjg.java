package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.util.List;



public class Sbzpjg implements java.io.Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int sfjs;//是否上报结束
	private int znum;//符合条件的照片总数
	private int num;//此次发送的照片数目
	private String zdid;
	private List<String> sList;
	
	public int getSfjs() {
		return sfjs;
	}
	public void setSfjs(int sfjs) {
		this.sfjs = sfjs;
	}
	public int getZnum() {
		return znum;
	}
	public void setZnum(int znum) {
		this.znum = znum;
	}
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
	}
	public List<String> getsList() {
		return sList;
	}
	public void setsList(List<String> sList) {
		this.sList = sList;
	}
	public String getZdid() {
		return zdid;
	}
	public void setZdid(String zdid) {
		this.zdid = zdid;
	}
	
	public byte[] getSbzpjgBytes(){
		byte[] bs=new byte[2];
		bs[0]=(byte) sfjs;
		bs[1]=(byte) znum;
		
		if(znum!=0){
			byte[] temp=new byte[1];
			temp[0]=(byte) num;
			bs=ByteUtil.byteMerger(bs, temp);
			for(int i=0;i<sList.size();i++){
				temp=sList.get(i).getBytes();
				bs= ByteUtil.byteMerger(bs, temp);
			}
		}
		return bs;
	}
	

	
}
