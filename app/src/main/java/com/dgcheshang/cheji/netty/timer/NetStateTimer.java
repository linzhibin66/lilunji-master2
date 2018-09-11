package com.dgcheshang.cheji.netty.timer;

import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.init.ZdClient;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/8.
 */

public class NetStateTimer extends TimerTask{
    @Override
    public void run() {
        try {
            boolean state = ZdUtil.pdNetwork();
            if (NettyConf.netstate == null) {
                NettyConf.netstate = state;
            } else if (NettyConf.netstate != state) {
                NettyConf.netstate = state;
                //如果网络变化
                if (state) {
                    ZdUtil.conServer();
                } else {
                    if (ZdClient.conTimer != null) {
                        ZdClient.conTimer.cancel();
                        ZdClient.conTimer = null;
                    }

                    NettyConf.constate = 0;
                    NettyConf.jqstate = 0;
                    Speaking.in("网络已断开");
                }
            }
        }catch(Exception e){}
    }
}
