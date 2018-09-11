package com.dgcheshang.cheji.netty.thread;

import com.dgcheshang.cheji.Tools.Speaking;

/**
 * Created by Administrator on 2017/8/16.
 */

public class SpeakThread extends Thread {
    @Override
    public void run() {
        //启动报读
        new Speaking().getInstance();
    }
}
