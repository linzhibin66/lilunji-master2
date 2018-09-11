package com.dgcheshang.cheji.netty.timer;

import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/8.
 */

public class JqTimer extends TimerTask {

    @Override
    public void run() {
        ZdUtil.sendZdjqHex();
    }
}
