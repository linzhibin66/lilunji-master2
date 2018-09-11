package com.dgcheshang.cheji.netty.timer;

import android.os.Handler;
import android.os.Message;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/17.
 */

public class LoginoutTimer extends TimerTask {
    @Override
    public void run() {

        //学员登出
        ZdUtil.qzStuOut();

    }
}
