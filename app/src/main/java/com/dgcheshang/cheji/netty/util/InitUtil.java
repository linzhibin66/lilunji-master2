package com.dgcheshang.cheji.netty.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.netty.certificate.Certificate;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.conf.SetZdcs;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by Administrator on 2017/7/19.
 */

public class InitUtil {

    //初始化
    public static void initSystem(){
        new LocationUtil().getGPS();
        getDeviceId();
        setCS();
        setNum();
    }

    /**
     * 获取设备基本信息
     * */
    public static void getDeviceId(){
        TelephonyManager tm = (TelephonyManager) CjApplication.getInstance().getSystemService(Context.TELEPHONY_SERVICE);
        String IMEI = tm.getDeviceId();
        String line1Number = tm.getLine1Number();//电话号码
        String model= android.os.Build.MODEL;//型号
        String xlh=android.os.Build.SERIAL;
        String mf = Build.MANUFACTURER;//制造商
        String simSn = tm.getSimSerialNumber();
        if(NettyConf.debug){
            Log.e("TAG","IMEI="+IMEI+",电话号码="+line1Number+",model ="+model+",zzsid ="+mf+",序列号="+xlh+"---"+simSn);
        }

        if(StringUtils.isNotEmpty(model)) {
            NettyConf.model = model;
            if(NettyConf.debug){
                Log.e("TAG","修改后的终端型号："+NettyConf.model);
            }
        }
        if(StringUtils.isNotEmpty(line1Number)&&StringUtils.isEmpty(NettyConf.mobile)){
            NettyConf.mobile=line1Number;
        }
        if(StringUtils.isNotEmpty(xlh)){
            if(xlh.length()>7){
                NettyConf.zdxlh=xlh.substring(xlh.length()-7,xlh.length());
            }else{
                NettyConf.zdxlh=xlh;
            }
        }
        NettyConf.imei=IMEI;
        String versionName = getVersionName(CjApplication.getInstance());//获取版本号
        NettyConf.version=versionName;

    }

    /**
     * 获取app的VersionName
     * */
    public  static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * 赋值给静态
     * */

    public static void setNum(){
        //获取鉴权成功后的sp。
        SharedPreferences sp = CjApplication.getInstance().getSharedPreferences("jianquan", Context.MODE_PRIVATE);
        String ptbh = sp.getString("ptbh", "");//平台编号
        String pxjgbh = sp.getString("pxjgbh", "");//培训机构编号
        String jszdbh = sp.getString("jszdbh", "");//终端统一编号
        String zs = sp.getString("zs", "");//证书
        String zskl = sp.getString("zskl", "");//证书口令
        int zcstate = sp.getInt("zcstate", 0);//注册状态

        NettyConf.ptbh=ptbh;
        NettyConf.pxjgbh=pxjgbh;
        NettyConf.jszdbh=jszdbh;
        NettyConf.zs=zs;
        NettyConf.zskl=zskl;
        if(StringUtils.isNotEmpty(zs)&&StringUtils.isNotEmpty(zskl)){
            NettyConf.key = Certificate.getPrivateKey(zs, zskl.toCharArray());
        }
        NettyConf.zcstate=zcstate;

        //终端参数赋值
        SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
        String host = zdcssp.getString("0013", "");//ip
        String port = zdcssp.getString("0018", "");//tcp端口
        String shengID = zdcssp.getString("0081", "44");//省域
        String shiID = zdcssp.getString("0082", "1900");//市域
        String phone = zdcssp.getString("0048", "");//手机号码
        String xtjg = zdcssp.getString("0001", "30");
        String cfjg = zdcssp.getString("0002", "30");
        String cfcs=zdcssp.getString("0003", "3");
        String mintime=zdcssp.getString("0004", "050000");//最早登陆时间
        String maxtime=zdcssp.getString("0005", "235500");//最晚登陆时间

        NettyConf.host=host;
        NettyConf.port=Integer.valueOf(port);
        NettyConf.shengID=shengID;
        NettyConf.shiID=shiID;
        NettyConf.mobile=phone;

        NettyConf.xtjg=Integer.valueOf(xtjg);//心跳间隔
        NettyConf.cfjg=Integer.valueOf(cfjg);//重发间隔
        NettyConf.cfcs=Integer.valueOf(cfcs);//重传次数

        NettyConf.minTime=Integer.valueOf(mintime);
        NettyConf.maxTime=Integer.valueOf(maxtime);

        //应用参数设置
        SharedPreferences yycssp = CjApplication.getInstance().getSharedPreferences("yycs", Context.MODE_PRIVATE);
        String makephotojg=yycssp.getString("1","15");
        NettyConf.makephotojg=Integer.parseInt(makephotojg)*60;//拍照发送间隔时间
        String cxyzsj=yycssp.getString("7","30");
        NettyConf.cxyzsj=Integer.parseInt(cxyzsj);

        //教练赋值
        SharedPreferences coachsp = CjApplication.getInstance().getSharedPreferences("coach", Context.MODE_PRIVATE);
        int jlstate = coachsp.getInt("jlstate", 0);
        String jlbh = coachsp.getString("jlbh", "");
        String cx = coachsp.getString("cx", "");
        String jzjhm = coachsp.getString("jzjhm", "");
        String pxck = coachsp.getString("pxck", "");//培训课程
        String ktid = coachsp.getString("ktid", "");//培训课程
        if(!jlbh.equals("")){
            NettyConf.jbh=jlbh;
            NettyConf.jlstate=jlstate;
            NettyConf.cx=cx;
            NettyConf.jzjhm=jzjhm;
            NettyConf.pxkc=pxck;
            NettyConf.ktid=ktid;
        }

        //禁训状态
        SharedPreferences jxsp = CjApplication.getInstance().getSharedPreferences("jxzt", Context.MODE_PRIVATE);
        String jxzt = jxsp.getString("jxzt", "1");
        NettyConf.jxzt=Integer.valueOf(jxzt);
    }

    /**
     * 设置参数
     * */
    public static void setCS(){
        SetZdcs setZdcs = new SetZdcs();
        SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
        String isset = zdcssp.getString("isset", "");
        if(!isset.equals("true")){
            setZdcs.setZdcs(CjApplication.getInstance());
        }
        SharedPreferences yycssp = CjApplication.getInstance().getSharedPreferences("yycs", Context.MODE_PRIVATE);
        String isset1 = yycssp.getString("isset", "");
        if(!isset1.equals("true")){
            setZdcs.setYycs(CjApplication.getInstance());
        }

        SharedPreferences jxzt = CjApplication.getInstance().getSharedPreferences("jxzt", Context.MODE_PRIVATE);
        String isset2 = jxzt.getString("isset", "");
        if(!isset2.equals("true")){
            setZdcs.setJxzt(CjApplication.getInstance());
        }
    }

}
