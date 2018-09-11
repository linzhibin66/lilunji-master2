package com.dgcheshang.cheji.netty.thread;

import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Database.MyDatabase;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.util.ForwardUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/30.
 */

public class CacheSend extends Thread {
    @Override
    public void run() {
        //删除数据库过期的数据
        Long t=ZdUtil.getLongTime();
        String sql = "select * from tdata where level=? and parentid is null order by initsj asc";

        String sql2 = "select * from tdataf where level=? and sj<? order by initsj asc";
        Long m=t-1*24*60*60*1000;

        for(int i=1;i<=7;i++){
            String[] param={i+""};
            ArrayList<Tdata> list = DbHandle.queryTdata(sql, param);
            if(NettyConf.debug){
                Log.e("TAG","级别"+i+"缓存发送数据条数："+list.size());
            }
            if(list.size()>0) {
                //发送
                for(int j=0;j<list.size();j++){
                    ForwardUtil.sendData(list.subList(j,j+1), 0,1);
                    try {
                        if(i==1) {
                            sleep(NettyConf.level1);
                        }else if(i==2){
                            sleep(NettyConf.level2);
                        }else if(i==3){
                            sleep(NettyConf.level3);
                        }else if(i==4){
                            sleep(NettyConf.level4);
                        }else if(i==5){
                            sleep(NettyConf.level5);
                        }else if(i==6){
                            sleep(NettyConf.level6);
                        }else if(i==7){
                            sleep(NettyConf.level7);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            String[] pa={i+"",String.valueOf(m)};
            ArrayList<Tdata> list2 = DbHandle.queryTdata(sql2, pa);
            if(list2.size()>0){
                for(int j=0;j<list2.size();j++){
                    ForwardUtil.sendData(list2.subList(j,j+1), 0,1);
                    try {
                        if(i==1) {
                            sleep(NettyConf.level1);
                        }else if(i==2){
                            sleep(NettyConf.level2);
                        }else if(i==3){
                            sleep(NettyConf.level3);
                        }else if(i==4){
                            sleep(NettyConf.level4);
                        }else if(i==5){
                            sleep(NettyConf.level5);
                        }else if(i==6){
                            sleep(NettyConf.level6);
                        }else if(i==7){
                            sleep(NettyConf.level7);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }
}
