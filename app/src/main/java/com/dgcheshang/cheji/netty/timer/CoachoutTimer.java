package com.dgcheshang.cheji.netty.timer;

import android.os.Handler;
import android.os.Message;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Database.MyDatabase;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.serverreply.JlydcR;
import com.dgcheshang.cheji.netty.util.ForwardUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.List;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/8.
 */

public class CoachoutTimer extends TimerTask {
    @Override
    public void run() {
        if(NettyConf.handlersmap.get("logincoach")!=null) {
            Message msg = new Message();
            msg.arg1 = 8;
            Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
            handler.sendMessage(msg);
        }else{
            List<Tdata> list= ZdUtil.coachOut2();
            if(ZdUtil.pdNetwork()&&NettyConf.constate==1&&NettyConf.jqstate==1){
                ForwardUtil.sendData(list, 1,7);
            }else{
                DbHandle.insertTdatas(list,7);
                ZdUtil.handleCoachOut();
            }
        }
    }
}
