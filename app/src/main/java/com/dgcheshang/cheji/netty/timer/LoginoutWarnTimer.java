package com.dgcheshang.cheji.netty.timer;

import com.dgcheshang.cheji.Tools.Speaking;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/17.
 */

public class LoginoutWarnTimer extends TimerTask {
    @Override
    public void run() {
        Speaking.in("今日有效培训时段即将结束,五分钟后将自动退出");
    }
}
