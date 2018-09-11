package com.dgcheshang.cheji.netty.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.LocationUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/10 0010.
 */

public class StateTimer extends TimerTask {

    @Override
    public void run() {
        int lianjie = NettyConf.constate;
        int jianquan =NettyConf.jqstate;
        boolean b = NettyConf.netstate;
        boolean state = LocationUtil.state;
        Message msg = new Message();
        msg.arg1=3;
        Bundle bundle = new Bundle();
        bundle.putInt("lianjie", lianjie);
        bundle.putInt("jianquan", jianquan);
        bundle.putBoolean("network",b);
        bundle.putBoolean("gpsstate",state);
        msg.setData(bundle);
        Handler handler = (Handler) NettyConf.handlersmap.get("login");
        handler.sendMessage(msg);
    }
}
