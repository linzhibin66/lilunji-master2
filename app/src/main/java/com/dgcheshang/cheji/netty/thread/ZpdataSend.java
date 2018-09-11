package com.dgcheshang.cheji.netty.thread;

import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.util.ForwardUtil;

import java.util.ArrayList;

/**
 * Created by Administrator on 2017/6/30.
 */

public class ZpdataSend extends Thread {
    private ArrayList<Tdata> list;
    private int type;
    private int level;

    public ZpdataSend(ArrayList<Tdata> list, int type, int level) {
        this.list = list;
        this.type = type;
        this.level = level;
    }

    @Override
    public void run() {
       for(int i=0;i<list.size();i++){
           ForwardUtil.sendData(list.subList(i,i+1),type,level);
           try {
               sleep(NettyConf.level8);
           } catch (InterruptedException e) {
               e.printStackTrace();
           }
       }
    }
}
