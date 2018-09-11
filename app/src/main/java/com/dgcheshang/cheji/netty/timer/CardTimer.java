package com.dgcheshang.cheji.netty.timer;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.tools.RfidUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;
import com.rscja.deviceapi.RFIDWithISO14443A;
import com.rscja.deviceapi.entity.SimpleRFIDEntity;
import com.rscja.deviceapi.exception.ConfigurationException;

import org.apache.commons.lang3.StringUtils;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/23.
 */

public class CardTimer extends TimerTask{
    public static boolean isstop=false;
    private RFIDWithISO14443A mRFID;
    private String whocard;

    public CardTimer(RFIDWithISO14443A mRFID, String whocard) {
        this.mRFID = mRFID;
        this.whocard=whocard;
    }

    @Override
    public void run() {
        if(NettyConf.debug){
            Log.e("TAG","刷卡类型："+whocard);
        }
        if (!isstop) {
            isstop=true;
            SimpleRFIDEntity entity = null;
            try {
                try {
                    if (mRFID == null) {
                        mRFID = RFIDWithISO14443A.getInstance();
                        Thread.sleep(1000);
                        mRFID.init();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    isstop=false;
                }
                entity = mRFID.request();
                if (entity == null) {
                    // 读卡失败
                    if(NettyConf.debug) {
                        Log.e("TAG", "读卡失败");
                    }
                    isstop=false;
                } else {
                    //读卡成功
                    String uid = entity.getId();
                    if(NettyConf.debug){
                        Log.e("TAG", "uid:" + uid);
                    }
                    if(StringUtils.isNotEmpty(uid)) {
                        Message msg = new Message();
                        msg.arg1 = 6;
                        Bundle bundle = new Bundle();

                        if (whocard.equals("jlcard")) {//教练刷卡
                            bundle.putString("jluid", uid);
                            msg.setData(bundle);
                            Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
                            handler.sendMessage(msg);
                        } else if (whocard.equals("xycardlogin")) {//学员刷卡
                            bundle.putString("xyuid", uid);
                            msg.setData(bundle);
                            Handler handler = (Handler) NettyConf.handlersmap.get("stulogin");
                            handler.sendMessage(msg);
                        } else if (whocard.equals("xycardout")) {//学员刷卡
                            bundle.putString("xyuid", uid);
                            msg.setData(bundle);
                            Handler handler = (Handler) NettyConf.handlersmap.get("stuout");
                            handler.sendMessage(msg);
                        } else if (whocard.equals("admincard")) {//管理员设置
                            bundle.putString("adminuid", uid);
                            msg.setData(bundle);
                            Handler handler = (Handler) NettyConf.handlersmap.get("main");
                            handler.sendMessage(msg);
                        }
                    }else{
                        isstop=false;
                    }

                }
            } catch (Exception e2) {
                isstop=false;
                if(NettyConf.debug){
                    Log.e("TAG",e2.getMessage());
                }
            }
        }
    }
}
