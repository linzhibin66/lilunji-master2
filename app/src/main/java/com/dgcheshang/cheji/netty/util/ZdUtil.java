package com.dgcheshang.cheji.netty.util;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Toast;

import com.dgcheshang.cheji.Bean.database.StudentBean;
import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.certificate.Sign;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.init.ZdClient;
import com.dgcheshang.cheji.netty.po.Bcfb;
import com.dgcheshang.cheji.netty.po.ImeiPassword;
import com.dgcheshang.cheji.netty.po.Jlydc;
import com.dgcheshang.cheji.netty.po.QzStuOut;
import com.dgcheshang.cheji.netty.po.Sfrz;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.po.Xydc;
import com.dgcheshang.cheji.netty.po.Zdjq;
import com.dgcheshang.cheji.netty.po.Zdzc;
import com.dgcheshang.cheji.netty.po.Zpdata;
import com.dgcheshang.cheji.netty.po.Zpsc;
import com.dgcheshang.cheji.netty.proputil.PropertiesUtil;
import com.dgcheshang.cheji.netty.thread.CacheDelete;
import com.dgcheshang.cheji.netty.thread.CacheSend;
import com.dgcheshang.cheji.netty.timer.CoachoutTimer;
import com.dgcheshang.cheji.netty.timer.ConTimer;
import com.dgcheshang.cheji.netty.timer.PzysTimer;
import com.dgcheshang.cheji.netty.timer.StudentoutTimer;

import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ZdUtil {
	public static  String TAG="ZdUtil";
	//水印用
	public static boolean ispz=false;//是否在拍照
	public static boolean issave=true;//是否保存数据

	/**
	 * 获取身份认证信息
	 * @param ickxlh 1.教练员  4.学员
	 * @param type
     */
	public static void sendSfrz(String ickxlh,String type){
		Sfrz sfrz=new Sfrz();
		sfrz.setXxlx("1");
		sfrz.setRylx(type);
		sfrz.setZjhm(ickxlh);
		byte[] b3=sfrz.getSfrzBytes();
		byte[] b2= MsgUtilClient.getMsgExtend(b3,"0401", "13", "2");
		List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900", NettyConf.mobile, "1");

		GatewayService.sendHexMsgToServer("serverChannel", list.get(0).getData());
		//ForwardUtil.sendData(list,0,1);
	}
	/**
	 * 终端注册
	 */
	public static void sendZdzc(){
		if(StringUtils.isBlank(NettyConf.mobile)||StringUtils.isBlank(NettyConf.shengID)||StringUtils.isBlank(NettyConf.host)||StringUtils.isBlank(NettyConf.shiID)||NettyConf.port == 0){
			Toast.makeText(CjApplication.getInstance(),"请填写完整注册参数",Toast.LENGTH_LONG).show();
		}else {
			Zdzc zdzc = new Zdzc();
			zdzc.setImei(NettyConf.imei);
			zdzc.setSxyid(NettyConf.shiID);
			zdzc.setSyid(NettyConf.shengID);
			zdzc.setXlh(NettyConf.zdxlh);
			zdzc.setZdxh(NettyConf.model);
			zdzc.setZzsid(NettyConf.zzsid);
			byte[] b2 = zdzc.getZdzcbytes();
			List<Tdata> list = MsgUtilClient.generateMsg(b2, "0100", NettyConf.mobile, "0");
			GatewayService.sendHexMsgToServer("serverChannel", list.get(0).getData());
		}
	}

	/**
	 * 照片初始化
	 * */
	public static void sendZpsc(String scms,String tdh,String lx){
		ispz=true;//正在拍照中

        Message msg=new Message();
        msg.arg1=2;
        Bundle bundle = new Bundle();
        bundle.putString("scms",scms);
        bundle.putString("tdh",tdh);
        bundle.putString("lx",lx);
        msg.setData(bundle);
		Timer ysTimer=new Timer();
		PzysTimer pt=new PzysTimer(msg);
		ysTimer.schedule(pt,1000);
	}

    /**
     * 照片初始化
     * */
    public static void sendZpsc2(String scms,String tdh,String lx,String gnss,String picpath){
        //先组合照片数据
        String s=String.valueOf(new Date().getTime());
        String zpbh=s.substring(s.length()-12,s.length()-2);

        //图片加水印
        String mark="";
        DecimalFormat df = new DecimalFormat("#.000");
        if(lx.equals("20")||lx.equals("21")){
            mark= PropertiesUtil.getValue("jlwatermark");
            mark=mark.replace("{{jlbh}}",NettyConf.jbh);
        }else{
            if(lx.equals("17")||lx.equals("18")||lx.equals("19")){
                mark= PropertiesUtil.getValue("xywatermark");
                mark=mark.replace("{{xybh}}",NettyConf.xbh);
                mark=mark.replace("{{jlbh}}",NettyConf.jbh);
            }else{
                if(NettyConf.xystate==1){
                    mark= PropertiesUtil.getValue("xywatermark");
                    mark=mark.replace("{{xybh}}",NettyConf.xbh);
                    mark=mark.replace("{{jlbh}}",NettyConf.jbh);
                }else if(NettyConf.jlstate==1){
                    mark= PropertiesUtil.getValue("jlwatermark");
                    mark=mark.replace("{{jlbh}}",NettyConf.jbh);
                }else{
                    mark= PropertiesUtil.getValue("watermark");
                }
            }

        }
        mark=mark.replace("{{jxbh}}",NettyConf.pxjgbh);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		mark=mark.replace("{{time}}",sdf.format(new Date()));

		ispz=false;//拍照结束
        byte[] zpsj=ImageMarkUtil.picMark(picpath,mark);
		if(zpsj!=null) {
			if (NettyConf.debug) {
				Log.e("TAG", "返回的数据长度：" + zpsj.length);
			}

			Zpdata zd = new Zpdata();
			zd.setZpbh(zpbh);
			zd.setZpsj(zpsj);

			//存入数据库
			DbHandle.insertZpdata(zd);

			//删除图片
			Uri uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
			ContentResolver mContentResolver = CjApplication.getInstance().getContentResolver();
			String where = MediaStore.Images.Media.DATA + "='" + picpath + "'";
			mContentResolver.delete(uri, where, null);

			byte[] b3 = zd.getZpdatabytes();
			byte[] b2 = MsgUtilClient.getMsgExtend(b3, "0306", "13", "2");

			List<Tdata> list = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "1");
			int len = list.size();

			//int lsh=MsgUtilClient.generateLsh();
			//初始化流水号与照片数据对应
			// NettyConf.zpdataStr.put(lsh+"",msg);
			//再组合照片上传初始化
			Zpsc zpsc = new Zpsc();
			zpsc.setZpbh(zpbh);
			zpsc.setScms(scms);
			zpsc.setTdh(tdh);
			zpsc.setTpcc("1");//需接洽
			zpsc.setSjlx(lx);
			if (lx.equals("20") || lx.equals("21")) {
				zpsc.setKtid("0");
				zpsc.setBh(NettyConf.jbh);
			} else if (lx.equals("17") || lx.equals("18") || lx.equals("19")) {
				zpsc.setKtid(NettyConf.ktid);
				zpsc.setBh(NettyConf.xbh);
			} else {
				if (NettyConf.xystate == 1) {
					zpsc.setKtid(NettyConf.ktid);
					zpsc.setBh(NettyConf.xbh);
				} else if (NettyConf.jlstate == 1) {
					zpsc.setKtid("0");
					zpsc.setBh(NettyConf.jbh);
				} else {
					zpsc.setKtid("0");
					zpsc.setBh("0000000000000000");
				}
			}
			zpsc.setRlsb("50");//需接洽
			String srclen = String.valueOf(zpsj.length);
			zpsc.setZpsjcd(srclen);
			zpsc.setZbs(String.valueOf(len));
			byte[] zpscb3 = zpsc.getZpscBytes();
			byte[] zpscb2 = MsgUtilClient.getMsgExtend(zpscb3, "0305", "13", "2");
			List<Tdata> list2 = MsgUtilClient.generateMsg(zpscb2, "0900", NettyConf.mobile, "1");

			//存入数据库
			DbHandle.insertZpsc(zpsc);
			if (list2.size() > 0) {
				String parentid = list2.get(0).getKey();
				//处理照片数据
				for (Tdata tdata : list) {
					tdata.setParentid(parentid);
					DbHandle.insertTdata(tdata);
				}
			}


			if (ZdUtil.pdNetwork() && NettyConf.constate == 1 && NettyConf.jqstate == 1) {
				GatewayService.sendHexMsgToServer("serverChannel",list2);
			} else {
				if (NettyConf.debug) {
					Log.e("TAG", "照片缓存");
				}
				DbHandle.insertTdatas(list2, 4);
			}


			if (lx.equals("18")) {
				StudentoutTimer studentoutTimer=new StudentoutTimer();
				new Timer().schedule(studentoutTimer,300);
			}

			if (lx.equals("21")) {
				CoachoutTimer coachoutTimer=new CoachoutTimer();
				new Timer().schedule(coachoutTimer,300);
			}
		}else{
			Speaking.in("无拍照数据");
			if(NettyConf.debug){
				Log.e("TAG","拍照数据为空！");
			}
		}
    }

	/**
	 * 发送终端鉴权
	 */
	public static void sendZdjqHex(){
		if(NettyConf.debug){
			Log.e("TAG","发送鉴权信息");
		}
		//已注册成功鉴权
		Zdjq zdjq = new Zdjq();
		try {
			long ts=new Date().getTime()/1000;
			zdjq.setSjc(String.valueOf(ts));
			zdjq.setJqmw(Sign.sign(NettyConf.jszdbh, ts, NettyConf.key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		byte[] b2=zdjq.getZdjqbytes();
		List<Tdata> list= MsgUtilClient.generateMsg(b2,"0102",NettyConf.mobile,"0");
		ForwardUtil.sendData(list,0,1);
	}

	/**
	 * 终端注销
	 */
	public static void sendZdzx(){
		byte[] b2=new byte[0];
		List<Tdata> list=MsgUtilClient.generateMsg(b2,"0003",NettyConf.mobile,"0");
		ForwardUtil.sendData(list,0,1);
	}

	/**
	 * 发送补传分包数据
	 * @param bcfb
     */
	public static void replyBcfb(Bcfb bcfb){
		int yslsh=bcfb.getYslsh();
		String xhs=bcfb.getXhs();
		Log.e("TAG","补包请求："+xhs);
		String[] ss=xhs.split(";");

		String temp="";
		for(int i=0;i<ss.length;i++){
			int lsh=yslsh+Integer.parseInt(ss[i])-1;
			if(i==ss.length-1){
				temp=temp+lsh;
			}else{
				temp=temp+lsh+",";
			}
		}
		if(NettyConf.debug){
			Log.e("TAG","补包流水号:"+temp);
		}
		String sql = "select * from tdata where key in (?)";
		String[] param = {temp};
		List<Tdata> tdatas=DbHandle.queryTdata(sql,param);
		if(NettyConf.debug){
			Log.e("TAG","补包数量:"+tdatas.size());
		}
		GatewayService.sendHexMsgToServer("serverChannel", tdatas);
	}

	/**
	 * 发送缓存数据
	 */
	public static void sendCache(){
		CacheSend cs=new CacheSend();
		Thread t=new Thread(cs);
		t.start();
	}

	//缓存数据清除
	public static void deleteCache(){
		CacheDelete cacheDelete=new CacheDelete();
		Thread t=new Thread(cacheDelete);
		t.start();
	}

	public static boolean pdGps(){
		/*if(LocationUtil.state&&NettyConf.location!=null){
			return true;
		}else{
			return false;
		}*/
		return true;
	}
	public static boolean pdNetwork(){
		ConnectivityManager manager = (ConnectivityManager) CjApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeInfo = manager.getActiveNetworkInfo();
		if(activeInfo==null){
            return false;
		}else {
			return true;
		}
	}

	/**
	 * 链接服务器
	 */
	public static void conServer() {
		if (NettyConf.constate == 0) {
			try {
				if (ZdClient.conTimer != null) {
					ZdClient.conTimer.cancel();
					ZdClient.conTimer = null;
				}

			} catch (Exception e) {

			}
			Timer timer = new Timer();
			TimerTask timerTask = new ConTimer();
			timer.schedule(timerTask, 1000, 35 * 1000);
			ZdClient.conTimer = timer;//记录下来
		} else if (NettyConf.constate == 1) {
			if (NettyConf.zcstate == 0) {
				//没注册过注册
				ZdUtil.sendZdzc();
			} else if (NettyConf.zcstate == 1) {
				//发送终端鉴权
				ZdUtil.sendZdjqHex();
			}
		}

	}

	/**
	 * 请求密码匹配 type 1是教练 4是学员
	 */
	public static void matchPassword(int type,String password){
		ImeiPassword imeiPassword=new ImeiPassword();
		imeiPassword.setImei(NettyConf.imei);
		imeiPassword.setType(type);
		imeiPassword.setPassword(password);
		byte[] b3 = imeiPassword.getImeiPasswordBytes();
		List<Tdata> list;
		if(type==9){
			byte[] b2 = MsgUtilClient.getMsgExtend(b3, "1001", "13", "0");
			list = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "0");
		}else {
			byte[] b2 = MsgUtilClient.getMsgExtend(b3, "1001", "13", "2");
			list = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "1");
		}
		//ForwardUtil.sendData(list, 0, 1);
		if(NettyConf.debug){
			Log.e("TAG","发送管理员密码认证");
		}
		GatewayService.sendHexMsgToServer("serverChannel", list.get(0).getData());
	}

	//学员登出
	public static void studentOut1(){
		try {
			ZdUtil.sendZpsc("129", "0", "18");//调用拍照
		}catch (Exception e){
			Speaking.in("学员登出数据异常");
		}
	}

	public static List<Tdata> studentOut2(){
		Xydc xydc = new Xydc();
		xydc.setXybh(NettyConf.xbh);
		xydc.setKtid(NettyConf.ktid);
		SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
		long l=ZdUtil.getLongTime();
		xydc.setDcsj(sdf.format(new Date(l)));
		xydc.setDlzsj((l-Long.valueOf(NettyConf.xydlsj))/60000+"");
		byte[] b3 = xydc.getXydcBytes();
		byte[] b2 = MsgUtilClient.getMsgExtend(b3, "0202", "13", "2");
		List<Tdata> list = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "1");
		return list;
	}

	public static void handleStudentOut(String xybh){
		Speaking.in("学员登出成功，下一位");
		//处理内存和列表
		String[] params={xybh};
		//删除登陆列表内容
		int num=DbHandle.deleteData("stulogin","tybh=?",params);
		//删除身份认证缓存信息
		DbHandle.deleteData("tsfrz","tybh=?",params);
	}

	//教练登出
	public static void coachOut1(){
		try {
			ZdUtil.sendZpsc("129", "0", "21");//调用拍照
		}catch(Exception e){
			Speaking.in("教练员登出数据异常");
		}
	}

	public static List<Tdata> coachOut2(){
		Jlydc jlydc = new Jlydc();
		jlydc.setJlybh(NettyConf.jbh);
		jlydc.setKtid(NettyConf.ktid);
		byte[] b3 = jlydc.getJlydcBytes();
		byte[] b2 = MsgUtilClient.getMsgExtend(b3, "0102", "13", "2");
		List<Tdata> list = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "1");
		return list;
	}

	public static void handleCoachOut(){
		Speaking.in("教练员登出成功");
		if(NettyConf.jlstate!=0) {
			NettyConf.jlstate = 0;
		}
		//保存教练信息
		SharedPreferences coachsp = CjApplication.getInstance().getSharedPreferences("coach", Context.MODE_PRIVATE); //私有数据
		SharedPreferences.Editor editor = coachsp.edit();//获取编辑器
		editor.putInt("jlstate",0);
		editor.commit();

		if(NettyConf.handlersmap.get("logincoach")==null) {
			Message msg = new Message();
			msg.arg1 = 5;
			Handler handler = (Handler) NettyConf.handlersmap.get("login");
			handler.sendMessage(msg);
		}
	}

	//判断当前时间是否能登陆
	public static boolean canLogin(){
		SimpleDateFormat sdf=new SimpleDateFormat("HHmmss");
		String s=sdf.format(new Date(ZdUtil.getLongTime()));
		int sj=Integer.valueOf(s);
		if(sj<NettyConf.maxTime&&sj>NettyConf.minTime){
			if(NettyConf.jxzt==1) {
				return true;
			}else{
				Speaking.in("设备处于禁训状态");
				return false;
			}
		}else{
			Speaking.in("现在不是培训时间段");
			return false;
		}
	}


	public static long getLongTime(){
		return new Date().getTime();
	}

	/**
	 * 学员强制退出
	 */
	public static void qzStuOut(){
		DbHandle.deleteData("stulogin","tybh is null",null);
		long l=DbHandle.getNum("select count(*) from stulogin",null);
		if(l>0){
			long num=l/20;
			if(l%20>0){
				num++;
			}

			for(long i=0;i<num;i++){
				String[] params={i*20+""};
				ArrayList<StudentBean> list=DbHandle.queryStuxx("select * from stulogin limit 20 offset ?",params);
				QzStuOut qzStuOut=new QzStuOut();
				qzStuOut.setKtid(NettyConf.ktid);
				SimpleDateFormat sdf=new SimpleDateFormat("yyMMddHHmmss");
				long l2=ZdUtil.getLongTime();
				qzStuOut.setDcsj(sdf.format(new Date(l2)));

				List<String> slist=new ArrayList<String>();
				for(StudentBean studentBean:list){
					slist.add(studentBean.getTybh());
				}
				qzStuOut.setSlist(slist);

				byte[] b3=qzStuOut.getQzStuOutbytes();
				byte[] b2 = MsgUtilClient.getMsgExtend(b3, "1004", "13", "2");
				List<Tdata> tlist = MsgUtilClient.generateMsg(b2, "0900", NettyConf.mobile, "1");
				GatewayService.sendHexMsgToServer("serverChannel",tlist);
			}

			DbHandle.deleteData("stulogin",null,null);

			if(NettyConf.handlersmap.get("loginstudent")!=null){
				Message msg=new Message();
				msg.arg1=11;
				Handler handler= (Handler) NettyConf.handlersmap.get("loginstudent");
				handler.sendMessage(msg);
			}


			if(NettyConf.jlstate==1){
				//教练登出
				ZdUtil.coachOut1();
			}
		}else{
			if(NettyConf.handlersmap.get("loginstudent")!=null){
				Message msg=new Message();
				msg.arg1=11;
				Handler handler= (Handler) NettyConf.handlersmap.get("loginstudent");
				handler.sendMessage(msg);
			}
			if(NettyConf.jlstate==1){
				//教练登出
				ZdUtil.coachOut1();
			}
		}
	}

	/**
	 * 获取本机IP
	 * */

	public static String getIPAddress(Context context) {
		NetworkInfo info = ((ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
				try {
					for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
						NetworkInterface intf = en.nextElement();
						for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
							InetAddress inetAddress = enumIpAddr.nextElement();
							if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
								return inetAddress.getHostAddress();
							}
						}
					}
				} catch (SocketException e) {
					e.printStackTrace();
				}

			} else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
				WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
				WifiInfo wifiInfo = wifiManager.getConnectionInfo();
				String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
				return ipAddress;
			}
		} else {
			//当前无网络连接,请在设置中打开网络
		}
		return null;
	}

	/**
	 * 将得到的int类型的IP转换为String类型
	 * @param ip
	 * @return
	 */
	public static String intIP2StringIP(int ip) {
		return (ip & 0xFF) + "." +
				((ip >> 8) & 0xFF) + "." +
				((ip >> 16) & 0xFF) + "." +
				(ip >> 24 & 0xFF);
	}

}


