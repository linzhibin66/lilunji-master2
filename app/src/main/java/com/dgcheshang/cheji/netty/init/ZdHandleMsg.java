package com.dgcheshang.cheji.netty.init;

import com.dgcheshang.cheji.netty.clientreply.SczdzpR;
import com.dgcheshang.cheji.netty.po.MsgAll;
import com.dgcheshang.cheji.netty.po.MsgExtend;
import com.dgcheshang.cheji.netty.util.ForwardUtil;
import com.dgcheshang.cheji.netty.util.GatewayService;
import com.dgcheshang.cheji.netty.util.MsgHandle;
import com.dgcheshang.cheji.netty.util.MsgUtil;
import com.dgcheshang.cheji.netty.util.MsgUtilClient;

import org.apache.commons.lang3.StringUtils;

import java.util.Map;

/**
 * Created by Administrator on 2017/5/19.
 */

public class ZdHandleMsg extends Thread{
    private String msg;

    public ZdHandleMsg(String msg) {
        this.msg = msg;
    }

    public void run(){
        String hexmsg="";
        //组拼原始数据
        msg=("7E"+msg+"7E").toUpperCase();

        MsgAll ma= MsgUtil.getMsgAll(msg);
        if(ma!=null){
            //删除内存信息
            boolean flag=true;
            if(!ma.getCode().equals("4")){
                flag=ForwardUtil.deleteSendData(ma);
            }
            String msgid="";
            //如果是照片上传回复则上传
            if(ma.getObject() instanceof MsgExtend){
                MsgExtend me= (MsgExtend) ma.getObject();
                if(me.getO() instanceof SczdzpR){
                    msgid=me.getMsgid();
                }
            }
            if(flag||StringUtils.isNotEmpty(msgid)) {
                //处理信息
                MsgHandle msgHandle = new MsgHandle();
                MsgUtilClient msgUtilClient = new MsgUtilClient();
                if (ma.getCode().equals("0")) {
                    msgHandle.handle(ma);
                } else if (ma.getCode().equals("4")) {
                    //分包
                    byte[] body = (byte[]) ma.getObject();
                    Map<String, Object> map = MsgUtil.getFbxx(ma.getHeader(), body, msg);
                    if (map.get("msg").equals("0")) {
                        //保存回复通用应答
                        hexmsg = msgUtilClient.getCommonRC(ma.getHeader(), "0");
                        if (StringUtils.isNotEmpty(hexmsg)) {
                            GatewayService.sendHexMsgToServer("serverChannel", hexmsg);
                        }
                    } else if (map.get("msg").equals("2")) {
                        hexmsg = msgUtilClient.getCommonRC(ma.getHeader(), "0");
                        if (StringUtils.isNotEmpty(hexmsg)) {
                            GatewayService.sendHexMsgToServer("serverChannel", hexmsg);
                        }
                        //发送补传分包信息
                        String xhs = String.valueOf(map.get("xhs"));
                        String bcmsg = msgUtilClient.getBcfbRequest(ma.getHeader(), xhs);
                        if (StringUtils.isNotEmpty(bcmsg)) {
                            GatewayService.sendHexMsgToServer("serverChannel", bcmsg);
                        }
                    } else if (map.get("msg").equals("1")) {
                        //分包接收完毕,组合包并按流程走
                        //保存回复通用应答
                        hexmsg = msgUtilClient.getCommonRC(ma.getHeader(), "0");
                        if (StringUtils.isNotEmpty(hexmsg)) {
                            GatewayService.sendHexMsgToServer("serverChannel", hexmsg);
                        }

                        //按流程走
                        body = (byte[]) map.get("sbody");
                        Object o = MsgUtil.getBodyObject(ma.getHeader(), body);
                        ma.setObject(o);

                        msgHandle.handle(ma);
                    }
                } else {
                    //错误
                    hexmsg = msgUtilClient.getCommonRC(ma.getHeader(), "2");
                    if (StringUtils.isNotEmpty(hexmsg)) {
                        GatewayService.sendHexMsgToServer("serverChannel", hexmsg);
                    }
                }
            }

        }
    }
}
