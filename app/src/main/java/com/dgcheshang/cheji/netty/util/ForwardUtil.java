package com.dgcheshang.cheji.netty.util;

import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Database.MyDatabase;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Forward;
import com.dgcheshang.cheji.netty.po.Header;
import com.dgcheshang.cheji.netty.po.MsgAll;
import com.dgcheshang.cheji.netty.po.MsgExtend;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.serverreply.CommonR;
import com.dgcheshang.cheji.netty.thread.HandleSenddata;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Timer;

public class ForwardUtil {

	/**
	 *
	 * @param list
	 * @param type 0代表重发次数后不保存不需鉴权，1代表重发次数后保存(需鉴权),2代表重发次数后不保存(需鉴权)
	 * @param level 教练员登陆1级别,学员登陆2级别,位置汇报3级别,照片数据4级别,学时记录5级别,学员登出6级别,教练登出7级别
     */
	public static void sendData(List<Tdata> list,int type,int level){
		if(list.size()>0) {
			for (Tdata d : list) {
				addSendData(d.getData(),d.getInitsj(), Integer.parseInt(d.getKey()), type,level);
			}
		}
	}

	/**
	 * 发送数据转发
	 * @param data
	 * @param lsh
     */
	public static void addSendData(String data,Long initsj,int lsh, int type,int level){
		//0代表重发次数后不保存不需鉴权，1代表重发次数后保存(需鉴权),2代表重发次数后不保存(需鉴权)

		Forward f = new Forward();
		f.setLsh(lsh);
		f.setNr(data);
		f.setCs(0);
		f.setType(type);
		f.setLsh(level);
		f.setInitsj(initsj);
		NettyConf.senddata.put(lsh + "", f);

		//启动监听线程
		HandleSenddata timerTask = new HandleSenddata(lsh + "");

		Timer timer=new Timer();

		timer.schedule(timerTask, 0, NettyConf.cfjg * 1000);

		NettyConf.timermap.put(lsh+"",timer);

	}

	/**
	 * 回复了的清理回复数据
	 * @param msgAll
     */
	public static boolean deleteSendData(MsgAll msgAll){
		boolean flag=true;
		String lsh="";
		Header header=msgAll.getHeader();
		if(msgAll.getCode().equals("0")){
			if(header.getMsgid().equals("8001")){
				CommonR cr= (CommonR) msgAll.getObject();
				lsh=String.valueOf(cr.getLsh());
			}  else if (header.getMsgid().equals("8900")) {
				MsgExtend me = (MsgExtend) msgAll.getObject();
				String ids = "8101,8102,8201,8202,8305,8401,8402,8403";
				if (ids.indexOf(me.getMsgid()) != -1) {
					lsh = String.valueOf(header.getMsgserno());
				}
			}
		}else {
			Speaking.in("无效卡");
		}

		if(StringUtils.isNotEmpty(lsh)){
			Object o=NettyConf.senddata.get(lsh);
			if(o!=null){
				NettyConf.senddata.remove(lsh);
			}

			o=NettyConf.timermap.get(lsh);
			if(o!=null){
				Timer timer= (Timer) o;
				timer.cancel();
			}

			String[] params={lsh};
			int num= DbHandle.deleteData("tdata","key=? and parentid is null",params);
			if(NettyConf.debug){
				Log.e("TAG","清除缓存数量:"+num);
			}
			if(num>0){
				flag=false;
			}else{
				num=DbHandle.deleteData("tdataf","key=? and parentid is null",params);
				if(NettyConf.debug){
					Log.e("TAG","清除顽固数量："+num);
				}
				if(num>0){
					flag=false;
				}
			}

		}
		return flag;
	}
}


