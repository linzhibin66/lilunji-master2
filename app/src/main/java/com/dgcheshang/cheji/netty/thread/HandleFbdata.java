package com.dgcheshang.cheji.netty.thread;

import com.dgcheshang.cheji.netty.conf.NettyConf;

/**
 * Created by Administrator on 2017/5/18.
 */

public class HandleFbdata extends Thread {
    private String key;

    public HandleFbdata(String key) {
        this.key = key;
    }

    @Override
    public void run() {
        try {
            sleep(200*1000);
            NettyConf.fbdata.remove(key);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
