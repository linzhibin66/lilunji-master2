package com.dgcheshang.cheji.netty.timer;

import android.os.Handler;
import android.os.Message;

import com.dgcheshang.cheji.netty.conf.NettyConf;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class PzysTimer extends TimerTask {
    private Message msg;

    public PzysTimer(Message msg) {
        this.msg = msg;
    }

    @Override
    public void run() {
        Handler handler = (Handler) NettyConf.handlersmap.get("login");
        handler.sendMessage(msg);
    }
}
