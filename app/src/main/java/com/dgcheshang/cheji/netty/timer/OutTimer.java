package com.dgcheshang.cheji.netty.timer;

import android.content.Context;
import android.content.SharedPreferences;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimerTask;

/**
 * Created by Administrator on 2017/7/31.
 */

public class OutTimer extends TimerTask {
    @Override
    public void run() {
        if(NettyConf.jlstate==1){
            SharedPreferences coachsp = CjApplication.getInstance().getSharedPreferences("coach", Context.MODE_PRIVATE); //私有数据
            String s=coachsp.getString("dlday","");
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMdd");
            if(!s.equals(sdf.format(new Date()))){
                ZdUtil.qzStuOut();
            }
        }
    }
}
