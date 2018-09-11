package com.dgcheshang.cheji.netty.thread;

import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Database.MyDatabase;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ZdUtil;

/**
 * Created by Administrator on 2017/6/30.
 */

public class CacheDelete extends Thread{
    @Override
    public void run() {
        //删除数据库过期的数据
        Long t= ZdUtil.getLongTime();
        Long l=t-7*24*60*60*1000;
        if(NettyConf.debug){
            Log.e("TAG","删除开始时间："+l);
        }
        String[] params={String.valueOf(l)};
        int num= DbHandle.deleteData("tsfrz", "sj < ?",params);
        if(NettyConf.debug){
            Log.e("TAG","清除认证信息数量："+num);
        }

        num=DbHandle.deleteData("tdata", "initsj < ?",params);
        if(NettyConf.debug){
            Log.e("TAG","清除缓存信息数量："+num);
        }

        num=DbHandle.deleteData("tdataf", "initsj < ?",params);
        if(NettyConf.debug){
            Log.e("TAG","清除顽固信息数量："+num);
        }


        Long l2= NettyConf.hcsj*24*60*60*1000;
        String[] params2={String.valueOf(l2)};

        num=DbHandle.deleteData("xsjl","sj<?",params2);
        if(NettyConf.debug){
            Log.e("TAG","清除学时记录信息数量："+num);
        }
        num=DbHandle.deleteData("zpsc","sj<?",params2);
        if(NettyConf.debug){
            Log.e("TAG","清除照片初始化信息数量："+num);
        }
        num=DbHandle.deleteData("zpdata","sj<?",params2);
        if(NettyConf.debug){
            Log.e("TAG","清除照片内容信息数量："+num);
        }
    }
}
