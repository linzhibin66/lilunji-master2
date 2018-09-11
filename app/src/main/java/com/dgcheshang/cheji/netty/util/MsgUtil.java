package com.dgcheshang.cheji.netty.util;


import android.util.Log;

import com.dgcheshang.cheji.netty.clientreply.CxcsR;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Header;
import com.dgcheshang.cheji.netty.po.MsgAll;
import com.dgcheshang.cheji.netty.po.MsgExtend;
import com.dgcheshang.cheji.netty.po.ParamsSz;
import com.dgcheshang.cheji.netty.po.Zdjq;
import com.dgcheshang.cheji.netty.po.Zdzc;
import com.dgcheshang.cheji.netty.po.ZdzcR;
import com.dgcheshang.cheji.netty.proputil.MsgID;
import com.dgcheshang.cheji.netty.serverreply.CommonR;
import com.dgcheshang.cheji.netty.thread.HandleFbdata;

import java.util.HashMap;
import java.util.Map;

/**
 * 计时平台使用
 * @author Administrator
 *
 */
public class MsgUtil {

	/**
	 * 心跳
	 */
	public static String getXthf(String mobile){
		byte[] b2=new byte[0];
		Header h=new Header();
		h.setProtver(128);
		h.setMsgid("0002");
		h.setBodyprop("0000000000000000");
		h.setMobileno(mobile);
		h.setMsgserno(0);
		byte[] b1=h.getHeaderBytes();
		String hexmsg=getMsg(b1, b2);
		return hexmsg;
	}

	/**
	 * 获取完整的回复信息
	 * @param b1
	 * @param b2
	 * @return
	 */
	public static String getMsg(byte[] b1,byte[] b2){
		byte[] temp=ByteUtil.byteMerger(b1, b2);
		int code=ByteUtil.getValidateCode(temp);
		byte[] b3=new byte[1];
		b3[0]=new Integer(code).byteValue();
		temp=ByteUtil.byteMerger(temp, b3);
		String hex=ByteUtil.bytesToHexString(temp);

		StringBuffer sb=new StringBuffer();
		for(int i=0;i<hex.length();i=i+2){
			sb.append(hex.substring(i, i+2)+",");
		}
		hex=sb.toString().replaceAll("7D", "7D,01");
		hex=hex.replaceAll("7E", "7D,02");
		hex=hex.replaceAll(",", "");
		hex="7E"+hex+"7E";

		return hex;
	}

	/**
	 * 获取
	 * @param header
	 * @param jg
	 * @return
	 */
	public static CommonR getCommonRhs(Header header,String jg){
		//通用应答
		CommonR cr=new CommonR();
		cr.setJg(Integer.valueOf(jg));
		cr.setLsh(header.getMsgserno());
		cr.setMsgid(header.getMsgid());
		return cr;
	}

	/**
	 * 解析数据
	 * @param msg
	 * @return
	 */
	public static MsgAll getMsgAll(String msg){
		try{
			MsgAll ma=new MsgAll();
			ma.setHexString(msg);
			//解析头部
			//获取去掉分割符消息的字节数组
			byte[] bytes=ByteToCls.hexStringTobyte(msg);

			//效验码检测
			boolean flag=ByteUtil.validateCode(bytes);

			if(!flag){
				//效验码错误
				ma.setCode("1");
				ma.setErrormsg("效验码错误！");
				return ma;
			}
			//效验码成功
			//获取消息头
			Header header=ByteToCls.getHeader(bytes);
			if(header==null){
				ma.setCode("2");
				ma.setErrormsg("消息头解析失败！");
				return ma;
			}

			//消息头成功解析赋值
			ma.setHeader(header);

			//获取消息体
			byte[] body=null;
			if(header.getSffb().equals("0")){
				body=ByteUtil.subBytes(bytes, 16, bytes.length-17);
			}else{
				body=ByteUtil.subBytes(bytes, 20, bytes.length-21);
			}
			if(header.getSjcd()!=0&&header.getSjcd()!=body.length){
				//数据长度错误,返回通用错误
				ma.setCode("3");
				ma.setErrormsg("消息体长度跟头部指定长度不符！");
				return ma;
			}

			//判断是否分包分包返回
			if(header.getSffb().equals("1")){
				//数据长度错误,返回通用错误
				ma.setObject(body);
				ma.setCode("4");
				ma.setErrormsg("分包信息");
				return ma;
			}

			//对消息体进行解析
			Object o=getBodyObject(header,body);
			if(o==null){
				//消息体解析失败
				ma.setCode("5");
				ma.setErrormsg("消息体解析失败！");
			}else{
				//消息体解析成功
				ma.setObject(o);
				ma.setCode("0");
			}

			return ma;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}
	}


	//解析消息体
	public static Object getBodyObject(Header header,byte[] body){
		try{
			Object o="";
			if(header.getMsgid().equals(MsgID.getValue("zdzc"))){
				//终端注册
				Zdzc zdzc=ByteToCls.getZdzc(body);
				return zdzc;
			}else if(header.getMsgid().equals("8100")){
				//终端注册应答
				ZdzcR zr=ByteToCls.getZdzcR(body);
				return zr;
			}else if(header.getMsgid().equals(MsgID.getValue("zdjq"))){
				//终端鉴权处理
				Zdjq zdjq=ByteToCls.getZdjq(body);
				return zdjq;
			}else if(header.getMsgid().equals("0001")){
				//通用应答计时终端下发的指令
				CommonR cr=ByteToCls.getCommonR(body);
				cr.setZdid(header.getMobileno());
				//cr.setSj(ZdUtil.getBdtime());
				return cr;
			}else if(header.getMsgid().equals("8103")){
				//终端参数设置解析
				ParamsSz pz=ByteToCls.getParamsSz(body);
				return pz;
			}else if(header.getMsgid().equals("8106")){
				//查询指定终端参数
				return ByteToCls.getCxcs(body);
			}else if(header.getMsgid().equals("0104")){
				//查询终端参数回复
				CxcsR cr=ByteToCls.getCxcsR(body);
				return cr;
			}else if(header.getMsgid().equals("8105")){
				//终端控制指令解析
				return ByteToCls.getZdkz(body);
			}else if(header.getMsgid().equals("8202")){
				//位置信息跟踪控制
				return ByteToCls.getGzkz(body);
			}else if(header.getMsgid().equals("8001")){
				//监督平台信息回复
				CommonR cr=ByteToCls.getCommonR(body);
				cr.setZdid(header.getMobileno());
				//cr.setSj(CommonUtil.getBdtime());
				return cr;
			}else if(header.getMsgid().equals("8003")){
				//补传分包请求解析
				return ByteToCls.getBcfb(body);
			}else if(header.getMsgid().equals("7003")){
				//升级指令
				return ByteToCls.getUpgrade(body);
			}else if(header.getMsgid().equals("0900")||header.getMsgid().equals("8900")){
				//数据上行透传
				MsgExtend me=ByteToCls.getMsgExtend(body);
				return me;
			}
			return o;
		}catch(Exception e){
			e.printStackTrace();
			return null;
		}

	}



	/**
	 * 分包分析0.保存成功，1，数据接收完毕 2.补发分包消息
	 */
	public static Map<String,Object> getFbxx(Header header, byte[] body, String hs){
		Map<String,Object> map=new HashMap<String, Object>();
		byte[] sbody=null;//合成后的消息体
		String msg="0";
		String xhs="";

		//存进redis并设置存活时间
		String key=header.getMsgid()+"_"+header.getMsgserno();

		NettyConf.fbdata.put(key,body);
		//存活线程启动
		HandleFbdata handleFbdata = new HandleFbdata(key);
		Thread th=new Thread(handleFbdata);
		th.start();

		//有分包要进行处理，查看是否接收完毕
		//开始流水号
		int k=header.getMsgserno()-header.getPacksortno()+1;
		//结束流水号加1
		int j=k+header.getMsgpackcnt();
		//组合字节数组变量
		byte[] sb=new byte[0];
		boolean fg=true;
		for(int i=k;i<j;i++){
			String gkey=header.getMsgid()+"_"+i;
			byte[] temp= (byte[]) NettyConf.fbdata.get(gkey);
			if(temp==null){
				fg=false;
				break;
			}else{
				sb=ByteUtil.byteMerger(sb, temp);
			}
		}

		if(fg){
			//组合完成
			msg="1";
			sbody=sb;
			//删除redis里面的缓存
			for(int i=k;i<j;i++){
				String gkey=header.getMsgid()+"_"+i;
				NettyConf.fbdata.remove(gkey);
			}
		}else{
			if(header.getMsgpackcnt()==header.getPacksortno()){
				//组合失败后看当前缺失的包
				StringBuffer sbf=new StringBuffer();
				for(int i=k;i<j;i++){
					String gkey=header.getMsgid()+"_"+i;
					byte[] temp= (byte[]) NettyConf.fbdata.get(gkey);
					if(temp==null){
						sbf.append(i-k+1+",");
					}
				}
				msg="2";
				xhs=sbf.toString().substring(0,sbf.length()-1);
			}
		}

		map.put("msg", msg);
		map.put("sbody", sbody);
		map.put("xhs", xhs);
		return map;
	}
}
