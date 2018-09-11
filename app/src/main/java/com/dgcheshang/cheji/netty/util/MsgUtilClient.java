package com.dgcheshang.cheji.netty.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Bcfb;
import com.dgcheshang.cheji.netty.po.Header;
import com.dgcheshang.cheji.netty.po.MsgAll;
import com.dgcheshang.cheji.netty.po.MsgExtend;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.serverreply.CommonR;

import java.util.ArrayList;
import java.util.List;

public class MsgUtilClient {

	/**
	 *
	 * @param b2
	 * @param msgid
	 * @param mobile
	 * @param sfjm
     * @return
     */
	public static List<Tdata> generateMsg(byte[] b2 ,String msgid,String mobile,String sfjm){
		//int fbzj=Integer.valueOf(PropertiesUtil.getValue("fbzj"));
		List<Tdata> list=new ArrayList<Tdata>();
		int fbzj=732;
		int zjlen=b2.length;
		int len=zjlen/fbzj;
		if(len*fbzj<zjlen){
			//判断是否存在余数，有则加1
			len=len+1;
		}
		String hexstring="";
		if(len<1){
			int msgserno=MsgUtilClient.generateLsh();
            if(NettyConf.debug){
                Log.e("TAG","流水号："+msgserno);
            }
			//无分包时
			Header h=new Header();
			h.setProtver(128);
			h.setMsgid(msgid);
			short i=(short) b2.length;
			byte[] temp=ByteUtil.shortToByteArray(i);
			String s=ByteUtil.bytesToByteString(temp);
			s=s.substring(6,16);
			s="00000"+sfjm+s;
			h.setBodyprop(s);
			h.setMobileno(mobile);
			h.setMsgserno(msgserno);

			byte[] b1=h.getHeaderBytes();
			hexstring=MsgUtil.getMsg(b1, b2);

			//加入转发队列
			list.add(new Tdata(String.valueOf(msgserno),hexstring));
			//ForwardUtil.addSendData(hexstring,msgserno);
		}else{
			int msgserno=MsgUtilClient.generateLshPl(len);
			//按批次数取连续流水号
			for(int k=0;k<len;k++){
				Header h2=new Header();
				h2.setProtver(128);
				h2.setMsgid(msgid);
				byte[] t=new byte[0];
				if(k==(len-1)){
					//是最后一个时直接取余数
					t=ByteUtil.subBytes(b2, k*fbzj, zjlen-k*fbzj);
				}else{
					//不是最后个直接切割
					t=ByteUtil.subBytes(b2, k*fbzj, fbzj);
				}
				short i=(short) t.length;
				byte[] temp=ByteUtil.shortToByteArray(i);
				String s=ByteUtil.bytesToByteString(temp);
				s=s.substring(6,16);
				if(len==1){
					//不分包时组合
					s="000000"+s;
				}else{
					//分包组合时属性
					s="001000"+s;
				}
				h2.setBodyprop(s);
				h2.setMobileno(mobile);
				int lsh=msgserno+k;
				h2.setMsgserno(lsh);

				if(len>1){
					//有分包时加入分包消息
					h2.setMsgpackcnt(len);
					h2.setPacksortno(k+1);
				}
				byte[] b1=h2.getHeaderBytes();
				String xhs=MsgUtil.getMsg(b1, t);


				list.add(new Tdata(String.valueOf(lsh),xhs));
			}
		}
		return list;
	}

	public static List<Tdata> generateMsg(byte[] b2 ,String msgid,int msgserno,String mobile,String sfjm){
		//int fbzj=Integer.valueOf(PropertiesUtil.getValue("fbzj"));
		List<Tdata> list=new ArrayList<Tdata>();
		int fbzj=732;
		int zjlen=b2.length;
		int len=zjlen/fbzj;
		if(len*fbzj<zjlen){
			//判断是否存在余数，有则加1
			len=len+1;
		}
		if(NettyConf.debug) {
			System.out.println("分包个数" + len);
		}
		String hexstring="";
		if(len<1){
			//无分包时
			Header h=new Header();
			h.setProtver(128);
			h.setMsgid(msgid);
			short i=(short) b2.length;
			byte[] temp=ByteUtil.shortToByteArray(i);
			String s=ByteUtil.bytesToByteString(temp);
			s=s.substring(6,16);
			s="00000"+sfjm+s;
			h.setBodyprop(s);
			h.setMobileno(mobile);
			h.setMsgserno(msgserno);

			//培训中预留状态
			if(NettyConf.xystate==1){
				h.setYlzd(1);
			}

			byte[] b1=h.getHeaderBytes();
			hexstring=MsgUtil.getMsg(b1, b2);

			//加入转发队列
			list.add(new Tdata(String.valueOf(msgserno),hexstring));
		}else{
			//按批次数取连续流水号
			for(int k=0;k<len;k++){
				Header h2=new Header();
				h2.setProtver(128);
				h2.setMsgid(msgid);
				byte[] t=new byte[0];
				if(k==(len-1)){
					//是最后一个时直接取余数
					t=ByteUtil.subBytes(b2, k*fbzj, zjlen-k*fbzj);
				}else{
					//不是最后个直接切割
					t=ByteUtil.subBytes(b2, k*fbzj, fbzj);
				}
				short i=(short) t.length;
				byte[] temp=ByteUtil.shortToByteArray(i);
				String s=ByteUtil.bytesToByteString(temp);
				s=s.substring(6,16);
				if(len==1){
					//不分包时组合
					s="000000"+s;
				}else{
					//分包组合时属性
					s="001000"+s;
				}
				h2.setBodyprop(s);
				h2.setMobileno(mobile);
				int lsh=msgserno+k;
				h2.setMsgserno(lsh);

				//培训中预留状态
				if(NettyConf.xystate==1){
					h2.setYlzd(1);
				}

				if(len>1){
					//有分包时加入分包消息
					h2.setMsgpackcnt(len);
					h2.setPacksortno(k+1);
				}
				byte[] b1=h2.getHeaderBytes();
				String xhs=MsgUtil.getMsg(b1, t);

				list.add(new Tdata(String.valueOf(lsh),xhs));
			}
		}
		return list;
	}

	/**
	 * 客户端扩展消息返回扩展消息体
	 * @param b3
	 * @param msgid
	 * @return
	 */
	public static byte[] getMsgExtend(byte[] b3,String msgid,String ywlx,String sfjm){
		MsgExtend m=new MsgExtend();
		m.setMsgid(msgid);
		String msgsx="";
		if("0".equals(sfjm)){
			msgsx="0000000000000010";
		}else{
			msgsx="0000000000100010";
		}
		m.setMsgsx(msgsx);
		m.setJpbxh(MsgUtilClient.generateJpbxh());
		m.setJszdbh(NettyConf.jszdbh);
		m.setSfjm(sfjm);
		m.setYwlx(ywlx);
		m.setNr(b3);
		return m.getMsgExtendbytes();
	}

	public static byte[] getMsgExtendHf(byte[] b3,String msgid,int jpbxh,String ywlx,String sfjm){
		MsgExtend m=new MsgExtend();
		m.setMsgid(msgid);
		String msgsx="0000000000100010";
		m.setMsgsx(msgsx);
		m.setJpbxh(jpbxh);
		m.setJszdbh(NettyConf.jszdbh);
		m.setSfjm(sfjm);
		m.setYwlx(ywlx);
		m.setNr(b3);
		return m.getMsgExtendbytes();
	}

	/**
	 * 客户端生成通用回复(扩展消息)
	 * @return
	 */
	public static byte[] generateCommonR(MsgAll ma, int jg){
		Header header=ma.getHeader();
		CommonR cr=new CommonR();
		cr.setLsh(header.getMsgserno());
		cr.setMsgid(header.getMsgid());
		cr.setJg(jg);
		byte[] b2=cr.getCommonRBytes();
		return b2;
	}

	/**
	 * 生成流水号
	 * @return
     */
	public static int generateLsh(){
        SharedPreferences lshsp = CjApplication.getInstance().getSharedPreferences("LSH", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = lshsp.edit();
        int lsh= lshsp.getInt("cunlsh",0);
		if(lsh==32000){
			edit.putInt("cunlsh",0);
		}else{
            edit.putInt("cunlsh",lsh+1);
		}
        edit.commit();
		if(NettyConf.debug){
			Log.e("TAG","获取流水号为："+lsh);
		}
		return lsh;
	}

	/**
	 * 批量生成流水号
	 * @return
	 */
	public static int generateLshPl(int count){
        SharedPreferences lshsp = CjApplication.getInstance().getSharedPreferences("LSH", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = lshsp.edit();
		int lsh= lshsp.getInt("cunlsh",0);
        edit.putInt("cunlsh",lsh+count);
		edit.commit();
		return lsh;
	}

	public static int generateJpbxh(){
        SharedPreferences lshsp = CjApplication.getInstance().getSharedPreferences("LSH", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = lshsp.edit();
		int jpbxh= lshsp.getInt("cunjpbxh",0);
		if(jpbxh==32000){
            edit.putInt("cunjpbxh",0);
		}else{
            edit.putInt("cunjpbxh",jpbxh+1);
		}
        edit.commit();
		return jpbxh;
	}

	/**
	 * 获取客户端通用应答
	 * @param header
	 * @param jg
     * @return
     */
	public String getCommonRC(Header header,String jg){
		CommonR cr=MsgUtil.getCommonRhs(header, jg);
		byte[] b2=cr.getCommonRBytes();
		byte[] b1=getCommonRheaderC(header);
		String hexmsg=getRMsg(b1, b2);
		return hexmsg;
	}

	/**
	 * 获取客户端通用
	 * @return
	 */
	public byte[] getCommonRheaderC(Header header){
		Header h=new Header();
		h.setProtver(128);
		h.setMsgid("0001");
		h.setBodyprop("0000000000000101");
		h.setMobileno(NettyConf.mobile);
		h.setMsgserno(0);
		return h.getHeaderBytes();
	}



	/**
	 * 获取完整的回复信息
	 * @param b1
	 * @param b2
	 * @return
	 */
	public String getRMsg(byte[] b1,byte[] b2){
		byte[] temp=ByteUtil.byteMerger(b1, b2);
		int code=ByteUtil.getValidateCode(temp);
		byte[] b3=new byte[1];
		b3[0]=new Integer(code).byteValue();
		temp=ByteUtil.byteMerger(temp, b3);
		String hex=ByteUtil.bytesToHexString(temp).toUpperCase();
		hex=hex.replace("7D", "7D01");
		hex=hex.replace("7E", "7D02");
		hex="7E"+hex+"7E";
		return hex;
	}

	/**
	 * 补包消息生成
	 * @param header
	 * @param xhs
     * @return
     */
	public String getBcfbRequest(Header header,String xhs){
		Bcfb bcfb=new Bcfb();
		int yslsh=header.getMsgserno()-header.getMsgpackcnt()+1;
		bcfb.setYslsh(yslsh);
		bcfb.setXhs(xhs);
		bcfb.setGs(xhs.split(",").length);
		byte[] b2=bcfb.getBcfbbytes();
		String hexmsg=getRMsg(header, b2,"8003");
		return hexmsg;
	}

	/**
	 *
	 * @param header
	 * @param b2
	 * @param msgid
	 * @return
	 */
	public String getRMsg(Header header,byte[] b2,String msgid){
		//查看是否需要分包
		String hexstring="";
		int fbzj=NettyConf.fbzj;
		int zjlen=b2.length;
		int len=zjlen/fbzj;
		if(len*fbzj<zjlen){
			//判断是否存在余数，有则加1
			len=len+1;
		}
		if(NettyConf.debug) {
			System.out.println("分包个数" + len);
		}
		if(len==1){
			//无分包时
			Header h=new Header();
			h.setProtver(128);
			h.setMsgid(msgid);
			short i=(short) b2.length;
			byte[] temp=ByteUtil.shortToByteArray(i);
			String s=ByteUtil.bytesToByteString(temp);
			s=s.substring(6,16);
			s="000000"+s;
			h.setBodyprop(s);
			h.setMobileno(NettyConf.mobile);
			h.setMsgserno(header.getMsgserno());

			byte[] b1=h.getHeaderBytes();
			hexstring=getRMsg(b1, b2);
		}else{
			//按批次数取连续流水号
			int msgserno=generateLshPl(len);
			for(int k=0;k<len;k++){
				Header h2=new Header();
				h2.setProtver(128);
				h2.setMsgid(msgid);
				byte[] t=new byte[0];
				if(k==(len-1)){
					//是最后一个时直接取余数
					t=ByteUtil.subBytes(b2, k*fbzj, zjlen-k*fbzj);
				}else{
					//不是最后个直接切割
					t=ByteUtil.subBytes(b2, k*fbzj, fbzj);
				}
				short i=(short) t.length;
				byte[] temp=ByteUtil.shortToByteArray(i);
				String s=ByteUtil.bytesToByteString(temp);
				s=s.substring(6,16);
				if(len==1){
					//不分包时组合
					s="000000"+s;
				}else{
					//分包组合时属性
					s="001000"+s;
				}
				h2.setBodyprop(s);
				h2.setMobileno(NettyConf.mobile);
				int lsh=msgserno+k;
				h2.setMsgserno(lsh);

				if(len>1){
					//有分包时加入分包消息
					h2.setMsgpackcnt(len);
					h2.setPacksortno(k+1);
				}
				byte[] b1=h2.getHeaderBytes();
				String xhs=getRMsg(b1, t);
				hexstring=hexstring+xhs;

				//如果是有分包则存入redis方便补传
				String key=""+lsh;
			}
		}
		return hexstring;
	}



}
