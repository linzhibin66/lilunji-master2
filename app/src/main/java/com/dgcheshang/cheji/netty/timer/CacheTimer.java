package com.dgcheshang.cheji.netty.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ZdUtil;
import com.rscja.deviceapi.RFIDWithISO14443A;
import com.rscja.deviceapi.entity.SimpleRFIDEntity;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CacheTimer extends TimerTask{

    @Override
    public void run() {
        ZdUtil.deleteCache();
    }
}
