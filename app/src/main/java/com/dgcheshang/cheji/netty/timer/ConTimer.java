package com.dgcheshang.cheji.netty.timer;

import android.util.Log;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.init.ZdClient;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/5/31.
 */

public class ConTimer extends TimerTask{
    @Override
    public void run() {
        if(NettyConf.constate==0){
            if(NettyConf.debug){
                Log.e("TAG","启动连接服务器");
            }

            //如果连接为空
            Thread th=new Thread(){
                @Override
                public	void run(){
                    try {
                        new ZdClient().run();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            };
            th.start();

            ZdClient.th=th;
        }else{
            Log.e("TAG","取消");
            this.cancel();
        }
    }
}
