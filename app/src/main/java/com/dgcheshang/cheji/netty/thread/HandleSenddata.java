package com.dgcheshang.cheji.netty.thread;

import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Database.MyDatabase;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Forward;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.util.GatewayService;
import com.dgcheshang.cheji.netty.util.ZdUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.TimerTask;

/**
 * 处理分包数据
 */
public class HandleSenddata extends TimerTask{
    private String key;//对应的数据键

    public HandleSenddata(String key) {
        this.key = key;
    }


    @Override
    public void run() {
        Object o=NettyConf.senddata.get(key);
        if(o!=null&&o instanceof Forward){
            Forward f= (Forward) o;
            if(f.getCs()>=NettyConf.cfcs){
                NettyConf.senddata.remove(key);

                if(f.getType()==1&&ZdUtil.issave) {
                    Tdata tdata = new Tdata(key, f.getNr(),f.getLevel(),f.getInitsj());
                    //存入数据库
                    DbHandle.insertTdataf(tdata);
                }
                this.cancel();
            }else{
                boolean flag=true;
                if(NettyConf.jqstate!=1&&f.getType()!=0){
                    flag=false;
                }else {
                    flag = GatewayService.sendHexMsgToServer("serverChannel", f.getNr());
                }
                if(flag) {
                    f.setCs(f.getCs() + 1);
                    NettyConf.senddata.put(f.getLsh() + "", f);
                }else{
                    NettyConf.senddata.remove(key);

                    if(f.getType()==1&&ZdUtil.issave) {
                        Tdata tdata = new Tdata(key, f.getNr(),f.getLevel(),f.getInitsj());
                        //存入数据库
                        DbHandle.insertTdata(tdata);
                    }

                    this.cancel();
                }
            }
        }else{
            this.cancel();
        }
    }
}
