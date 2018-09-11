package com.dgcheshang.cheji.netty.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.certificate.Certificate;
import com.dgcheshang.cheji.netty.clientreply.CxcsR;
import com.dgcheshang.cheji.netty.clientreply.CxzpR;
import com.dgcheshang.cheji.netty.clientreply.JxztR;
import com.dgcheshang.cheji.netty.clientreply.LjpzR;
import com.dgcheshang.cheji.netty.clientreply.SczdzpR;
import com.dgcheshang.cheji.netty.clientreply.YycsR;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Bcfb;
import com.dgcheshang.cheji.netty.po.Cxcs;
import com.dgcheshang.cheji.netty.po.Cxzp;
import com.dgcheshang.cheji.netty.po.Header;
import com.dgcheshang.cheji.netty.po.Jxzt;
import com.dgcheshang.cheji.netty.po.Ljpz;
import com.dgcheshang.cheji.netty.po.MsgAll;
import com.dgcheshang.cheji.netty.po.MsgExtend;
import com.dgcheshang.cheji.netty.po.Parameter;
import com.dgcheshang.cheji.netty.po.ParamsSz;
import com.dgcheshang.cheji.netty.po.Sbzpjg;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.po.Upgrade;
import com.dgcheshang.cheji.netty.po.Yycs;
import com.dgcheshang.cheji.netty.po.ZdzcR;
import com.dgcheshang.cheji.netty.po.Zdzp;
import com.dgcheshang.cheji.netty.po.Zpdata;
import com.dgcheshang.cheji.netty.po.Zpsc;
import com.dgcheshang.cheji.netty.proputil.PropertiesUtil;
import com.dgcheshang.cheji.netty.proputil.YycsProp;
import com.dgcheshang.cheji.netty.serverreply.CommonR;
import com.dgcheshang.cheji.netty.serverreply.ImeiPasswordR;
import com.dgcheshang.cheji.netty.serverreply.JlydcR;
import com.dgcheshang.cheji.netty.serverreply.JlydlR;
import com.dgcheshang.cheji.netty.serverreply.SfrzR;
import com.dgcheshang.cheji.netty.serverreply.XydcR;
import com.dgcheshang.cheji.netty.serverreply.XydlR;
import com.dgcheshang.cheji.netty.thread.ZpdataSend;
import com.dgcheshang.cheji.netty.timer.CardTimer;

import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * Created by Administrator on 2017/5/9 0009.
 */
public class MsgHandle {
    private static String TAG="MsgHandle";

    /**
     * 服务器发送给客户端信息处理
     * @param msgall
     */
    public void handle(MsgAll msgall){
        Header header=msgall.getHeader();
        if(header.getMsgid().equals("8100")){
            //终端注册
            zdzc(msgall);

        }else if(header.getMsgid().equals("8001")){
            CommonR cr=(CommonR)msgall.getObject();
            if(cr.getMsgid().equals("0002")){
                //心跳发送回复直接过
            }else if(cr.getMsgid().equals("0102")){
                //鉴权
                if(NettyConf.debug) {
                    Log.e("TAG", "鉴权结果：" + cr.getJg());
                }
                jq(cr);

            }else if(cr.getMsgid().equals("0003")){
                //注销
                if(NettyConf.debug){
                    Log.e("TAG","注销结果："+cr.getJg());
                }
                cancel(cr);

            }else{
                if(NettyConf.debug) {
                    Log.e("TAG", "通用回复结果：" + cr.getJg());
                }
            }
        }else if(header.getMsgid().equals("8003")){
            //补传分包请求
            Bcfb bcfb= (Bcfb) msgall.getObject();
            ZdUtil.replyBcfb(bcfb);
        }else if(header.getMsgid().equals("8103")){
            //设置终端参数
            paramSz(msgall);

        }else if(header.getMsgid().equals("8104")){
            //查询所有参数
            cxAllcs(msgall);

        }else if(header.getMsgid().equals("8106")){
            //查询指定参数
            cxZdcs(msgall);

        }else if(header.getMsgid().equals("8105")){
            //终端控制
            zdcz(msgall);

        }else if(header.getMsgid().equals("8202")){
            // 生成跟踪控制
            wzgzkz(msgall);

        }else if(header.getMsgid().equals("7002")){
            //查询设备基本信息
            baseInfo(msgall);
        }else if(header.getMsgid().equals("7003")){
            upgrade(msgall);
        }else if(header.getMsgid().equals("8900")){
            MsgExtend me= (MsgExtend) msgall.getObject();
            if(me.getMsgid().equals("8101")){
                //教练员登录
                jldl(msgall);

            }else if(me.getMsgid().equals("8102")){
                //教练员登出
                jldc(msgall);

            }else if(me.getMsgid().equals("8201")){
                //学员登录
                xydl(msgall);

            }else if(me.getMsgid().equals("8202")){
                //学员登出
                xydc(msgall);

            }else if(me.getMsgid().equals("8305")){
                //上传终端照片
                sczp(msgall);

            }else if(me.getMsgid().equals("8302")){
                //查询照片
                cxzp(msgall);

            }else if(me.getMsgid().equals("0303")){
                //上报照片查询结果
                zpcxjg(msgall);

            }else if(me.getMsgid().equals("8304")){
                //上传指定照片
                sczdzp(msgall);

            }else if(me.getMsgid().equals("8501")){
                //设置应用参数
                szyycs(msgall);

            }else if(me.getMsgid().equals("8503")){
                //查询应用参数
                cxzdyy(msgall);
            }else if(me.getMsgid().equals("8301")){
                //立即拍照指令
                ljpz(msgall);
            }else if(me.getMsgid().equals("8502")){
                //设置禁训状态
                jxzt(msgall);
            }else if(me.getMsgid().equals("8401")){
                //身份信息获取
                sfrz(msgall);
            }else if(me.getMsgid().equals("7001")){
                //强制登出密码匹配
                imeiPasswordR(msgall);
            }
        }
    }

    /**
     * 查询设备基本信息
     * @param msgAll
     */
    public void baseInfo(MsgAll msgAll){
        List<Parameter> pList=new ArrayList<Parameter>();

        Parameter p1=new Parameter();
        p1.setId("1001");
        p1.setValue(NettyConf.version);
        pList.add(p1);


        ParamsSz pz=new ParamsSz();
        pz.setParamList(pList);
        pz.setCount(pList.size());
        pz.setFsnum(pList.size());

        byte[] b2=pz.getParamsSzBytes();

        List<Tdata> list=MsgUtilClient.generateMsg(b2,"1002",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");

        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 终端注册
     * */

    public void zdzc(MsgAll msgall){
        ZdzcR zr=(ZdzcR)msgall.getObject();
        if(NettyConf.debug){
            Log.d("TAG","注册结果："+zr.getJg());
        }

        if(zr.getJg()==0){
            //当前注册状态不是成功时接收
            if(NettyConf.zcstate!=1) {
                NettyConf.zcstate = 1;
                SharedPreferences sp = CjApplication.getInstance().getSharedPreferences("jianquan", Context.MODE_PRIVATE);
                SharedPreferences.Editor edit = sp.edit();
                edit.putString("ptbh",zr.getPtbh());//平台编号
                edit.putString("pxjgbh",zr.getPxjgbh());//培训机构编号
                edit.putString("jszdbh",zr.getJszdbh());//终端统一编号
                edit.putString("zs",zr.getZs());//证书
                edit.putString("zskl",zr.getZskl());//证书口令
                edit.putInt("zcstate",1);//注册状态
                edit.commit();
                SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
                SharedPreferences.Editor csedit = zdcssp.edit();
                csedit.putString("0013",NettyConf.host);//ip
                csedit.putString("0018",String.valueOf(NettyConf.port));//tcp端口
                csedit.commit();
                NettyConf.ptbh = zr.getPtbh();
                NettyConf.pxjgbh = zr.getPxjgbh();
                NettyConf.jszdbh = zr.getJszdbh();
                NettyConf.zs = zr.getZs();
                NettyConf.zskl = zr.getZskl();
                NettyConf.key = Certificate.getPrivateKey(zr.getZs(), zr.getZskl().toCharArray());
                ZdUtil.sendZdjqHex();//终端鉴权
            }
        }else{
            String msg="";
            if(zr.getJg()==1){
                msg="车辆已被注册";
            }else if(zr.getJg()==2){
                msg="数据库中无该车辆或电话号码输入错误";
            }else if(zr.getJg()==3){
                msg="终端已被注册";
            }else if(zr.getJg()==4){
                msg="数据库中无该终端";
            }
            Speaking.in(msg);
            if(NettyConf.handlersmap.get("main")!=null){
                Message message=new Message();
                message.arg1=7;
                Handler handler = (Handler) NettyConf.handlersmap.get("main");
                handler.sendMessage(message);
            }

        }
    }

    /**
     * 鉴权
     * */
    public void jq(CommonR cr){
        SharedPreferences jianquan = CjApplication.getInstance().getSharedPreferences("jianquan", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = jianquan.edit();
        if(cr.getJg()!=0){
            Speaking.in("鉴权失败");
            //鉴权失败注册状态返回0
            NettyConf.zcstate=0;
            NettyConf.jqstate=0;
            editor.putInt("zcstate",0);//注册状态
            editor.commit();

            //连接服务器
            ZdUtil.conServer();

        }else{
            if(NettyConf.jqstate!=1){
                NettyConf.jqstate=1;
                Speaking.in("鉴权成功");
            }
            editor.putString("ptbh",NettyConf.ptbh);//平台编号
            editor.putString("pxjgbh",NettyConf.pxjgbh);//培训机构编号
            editor.putString("jszdbh",NettyConf.jszdbh);//终端统一编号
            editor.putString("zs",NettyConf.zs);//证书
            editor.putString("zskl",NettyConf.zskl);//证书口令
            editor.commit();
            ZdUtil.sendCache();
        }

        if(NettyConf.handlersmap.get("main")!=null){
            Message msg=new Message();
            msg.arg1=1;
            Bundle bundle = new Bundle();
            bundle.putInt("jqjg",cr.getJg());
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("main");
            handler.sendMessage(msg);
        }
    }

    /**
     * 注销
     * */
    public void cancel(CommonR cr){
            Message msg = new Message();
            msg.arg1=2;
            Bundle bundle = new Bundle();
            bundle.putInt("zxjg",cr.getJg());
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("main");
            handler.sendMessage(msg);
    }

    /**
     * 参数设置
     */
    public void paramSz(MsgAll msgAll){
        int jg=0;
        try {
            ParamsSz pasz = (ParamsSz) msgAll.getObject();
            List<Parameter> paramList = pasz.getParamList();
            SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
            SharedPreferences.Editor edit = zdcssp.edit();
            for (Parameter p : paramList) {
                String id = p.getId();
                String value = p.getValue();
                if (StringUtils.isNotEmpty(value)) {
                    edit.putString(id, value);
                }

                //立即生效参数
                if(id.equals("0004")){
                    NettyConf.minTime=Integer.valueOf(value);//最早登陆时间
                }else if(id.equals("0005")){
                    NettyConf.maxTime=Integer.valueOf(value);//最晚登陆时间
                }else if(id.equals("0003")){
                    NettyConf.cfcs=Integer.valueOf(value);//重传次数
                }
            }
            edit.commit();
        }catch(Exception e){
            e.printStackTrace();
            jg=1;
        }
        byte[] b2=MsgUtilClient.generateCommonR(msgAll, jg);
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0001",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");

        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 查询所有参数
     * */
    public void cxAllcs(MsgAll msgAll){
        List<Parameter> pList=new ArrayList<Parameter>();
        SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
        Map<String, Object> all = (Map<String, Object>) zdcssp.getAll();
        for(String s:all.keySet()){
            if(!s.equals("isset")){
                String o = zdcssp.getString(s,"0");
                if(StringUtils.isNotEmpty(o)){
                    Parameter p=new Parameter();
                    p.setId(s);
                    p.setValue(o);
                    pList.add(p);
                }
            }
        }
        CxcsR cr=new CxcsR();
        cr.setLsh(msgAll.getHeader().getMsgserno());
        cr.setpList(pList);
        byte[] b2=cr.getCxcsRBytes();
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0104",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");

        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 查询指定参数
     * */
    public void cxZdcs(MsgAll msgAll){
        List<Parameter> pList=new ArrayList<Parameter>();
        SharedPreferences zdcssp = CjApplication.getInstance().getSharedPreferences("zdcs", Context.MODE_PRIVATE);
        Cxcs cxcs = (Cxcs) msgAll.getObject();
        String ids = cxcs.getIds();
        if(NettyConf.debug){
            Log.e("TAG","ids="+ids);
        }
        String[] split = ids.split(",");
        for(String s:split){
            String o =zdcssp.getString(s,"");
            Parameter p=new Parameter();
            p.setId(s);
            p.setValue(o);
            pList.add(p);
        }

        CxcsR cr=new CxcsR();
        cr.setLsh(msgAll.getHeader().getMsgserno());
        cr.setpList(pList);
        byte[] b2=cr.getCxcsRBytes();
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0104",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");

        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 终端控制
     * */
    public void zdcz(MsgAll msgAll){
        byte[] b2=MsgUtilClient.generateCommonR(msgAll, 0);
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0001",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");
        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 位置跟踪控制
     * */
    public void wzgzkz(MsgAll msgAll){

        byte[] b2=MsgUtilClient.generateCommonR(msgAll, 0);
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0001",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");
        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 查询照片
     * */
    public void cxzp(MsgAll msgAll){
        MsgExtend me = (MsgExtend) msgAll.getObject();
        Cxzp cxzp = (Cxzp) me.getO();

        //第一次回复
        int jg=1;
        CxzpR  cxzpR=new CxzpR();
        cxzpR.setJg(jg);
        byte[] b3=cxzpR.getCxzpRbytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0302",me.getJpbxh(), "13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");

        GatewayService.sendHexMsgToServer("serverChannel", list);

        if(jg==1) try {
            String sql = "select * from zpsc where sj>? and sj<?";
            SimpleDateFormat sdf = new SimpleDateFormat("yyMMddHHmmss");
            Date parse = sdf.parse(cxzp.getKssj());
            long time = parse.getTime();
            Date parse1 = sdf.parse(cxzp.getJssj());
            long time1 = parse1.getTime();
            String[] params = {String.valueOf(time), String.valueOf(time1)};
            ArrayList<Zpsc> zplist = DbHandle.queryZpsc(sql, params);
            Sbzpjg sbzpjg = new Sbzpjg();
            if (zplist.size() == 0) {
                sbzpjg.setZnum(0);
                sbzpjg.setSfjs(1);
                sbzpjg.setNum(0);
                byte[] zpb3 = sbzpjg.getSbzpjgBytes();
                byte[] zpb2 = MsgUtilClient.getMsgExtendHf(zpb3, "0303",me.getJpbxh(), "13", "2");
                list= MsgUtilClient.generateMsg(zpb2, "0900", MsgUtilClient.generateLsh(), NettyConf.mobile, "1");
                GatewayService.sendHexMsgToServer("serverChannel", list);
            }else {
                List<String> sList=new ArrayList<String>();
                for(Zpsc s:zplist){
                    String bh = s.getZpbh();
                    if(StringUtils.isNotEmpty(bh)&&bh.length()==10){
                        sList.add(bh);
                    }
                }

                int count=zplist.size();
                int zpbhnum= Integer.valueOf(PropertiesUtil.getValue("zpbhnum"));
                //分次数
                int len=count/zpbhnum;

                if(count%zpbhnum>0){
                    len++;
                }

                List<String> tlist=new ArrayList<String>();
                for(int i=0;i<len;i++){
                    if(i==len-1){
                        tlist=sList.subList((len-1)*zpbhnum,sList.size());
                        sbzpjg.setSfjs(1);
                    }else{
                        tlist=sList.subList(i*zpbhnum,(i+1)*zpbhnum);
                        sbzpjg.setSfjs(0);
                    }

                    sbzpjg.setZnum(sList.size());
                    sbzpjg.setNum(tlist.size());
                    sbzpjg.setsList(tlist);
                    byte[] zpb3 = sbzpjg.getSbzpjgBytes();
                    byte[] zpb2 = MsgUtilClient.getMsgExtendHf(zpb3,"0303",me.getJpbxh(), "13", "2");
                    list = MsgUtilClient.generateMsg(zpb2, "0900", MsgUtilClient.generateLsh(), NettyConf.mobile, "1");
                    GatewayService.sendHexMsgToServer("serverChannel", list);
                }
            }

        } catch (Exception e) {
        }
    }

    /**
     * 上报照片查询结果
     * */
    public void zpcxjg(MsgAll msgAll){
        Sbzpjg sg=new Sbzpjg();
        sg.setSfjs(1);
        sg.setZnum(2);
        sg.setNum(2);
        List<String> sList=new ArrayList<String>();
        for(int i=1;i<3;i++){
            sList.add("000000000"+i);
        }
        sg.setsList(sList);
        byte[] b3=sg.getSbzpjgBytes();
        byte[] b2= MsgUtilClient.getMsgExtend(b3,"0303", "13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",MsgUtilClient.generateLsh(), NettyConf.mobile,"1");

        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 上传指定照片
     * */
    public void sczdzp(MsgAll msgAll){
        int jg=0;
        Zpsc zpsc=new Zpsc();
        Zpdata zpdata=new Zpdata();
        MsgExtend me = (MsgExtend) msgAll.getObject();
        Zdzp zdzp= (Zdzp) me.getO();
        String zpbh = zdzp.getZpbh();
        String sql="select * from zpsc where zpbh=?";
        String[] params={zpbh};
        ArrayList<Zpsc> zpscs = DbHandle.queryZpsc(sql, params);
        if(zpscs.size()==0){
            jg=1;
        }else {
            zpsc=zpscs.get(0);
        }

        if(jg==0) {
            sql = "select * from zpdata where zpbh=?";
            String[] param = {zpbh};
            ArrayList<Zpdata> zpdatas = DbHandle.queryZpdata(sql, param);
            if (zpdatas.size() == 0) {
                jg=1;
            }else {
                zpdata=zpdatas.get(0);
            }
        }

        SczdzpR sr=new SczdzpR();
        sr.setJg(jg);
        byte[] b3=sr.getSczdzpbytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0304",me.getJpbxh(), "13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");
        //发送
        GatewayService.sendHexMsgToServer("serverChannel", list);

        if(jg==0){

            byte[] zpb3=zpdata.getZpdatabytes();
            byte[] zpb2= MsgUtilClient.getMsgExtendHf(zpb3, "0306",me.getJpbxh(), "13", "2");

            List<Tdata> list1=MsgUtilClient.generateMsg(zpb2,"0900", NettyConf.mobile, "1");

            zpsc.setScms("2");
            byte[] zpscb3=zpsc.getZpscBytes();
            byte[] zpscb2= MsgUtilClient.getMsgExtendHf(zpscb3,"0305",me.getJpbxh(), "13", "2");
            List<Tdata> list2=MsgUtilClient.generateMsg(zpscb2,"0900", NettyConf.mobile, "1");

            if(list2.size()>0){
                String parentid=list2.get(0).getKey();
                //处理照片数据
                for(Tdata tdata:list1){
                    tdata.setParentid(parentid);
                    DbHandle.insertTdata(tdata);
                }
                ForwardUtil.sendData(list2,0,1);
            }
        }
    }

    /**
     * 设置应用参数
     * */
    public void szyycs(MsgAll msgAll){
        int jg=2;
        SharedPreferences yycssp = CjApplication.getInstance().getSharedPreferences("yycs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = yycssp.edit();
        MsgExtend me = (MsgExtend) msgAll.getObject();
        Yycs yycs = (Yycs) me.getO();
        String pzsjjg = yycs.getPzsjjg();
        String zpscsz = yycs.getZpscsz();
        String sfbdfjxx = yycs.getSfbdfjxx();
        String xhyssj = yycs.getXhyssj();
        String scjg = yycs.getScjg();
        String dcyssj = yycs.getDcyssj();
        String cxyzsj = yycs.getCxyzsj();
        String jlkxxx = yycs.getJlkxxx();
        String xykxxx = yycs.getXykxxx();
        String tlptsj = yycs.getTlptsj();

        if(yycs.getCsbh().equals("0")){
            edit.putString("1",pzsjjg);
            edit.putString("2",zpscsz);
            edit.putString("3",sfbdfjxx);
            edit.putString("4",xhyssj);
            edit.putString("5",scjg);
            edit.putString("6",dcyssj);
            edit.putString("7",cxyzsj);
            edit.putString("8",jlkxxx);
            edit.putString("9",xykxxx);
            edit.putString("10",tlptsj);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("1")){
            //定时拍照时间间隔
            edit.putString("1",pzsjjg);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("2")){
            //照片上传设置
            edit.putString("2",zpscsz);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("3")){
            //是否报读附加消息
            edit.putString("3",sfbdfjxx);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("4")){
            //熄火后停止学时计时的延时时间
            edit.putString("4",xhyssj);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("5")){
            //熄火后GNSS数据包上报间隔
            edit.putString("5",scjg);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("6")){
            //熄火后教练自动登出的延时时间
            edit.putString("6",dcyssj);
            edit.commit();
            jg=1;
        }else if(yycs.getCsbh().equals("7")){
            //重新验证身份时间间隔
            edit.putString("7",cxyzsj);
            edit.commit();
            jg=1;
        }
        YycsR yr = new YycsR();
        yr.setJg(jg);
        byte[] b3=yr.getYycsRBytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0501", me.getJpbxh(),"13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");
        GatewayService.sendHexMsgToServer("serverChannel", list);

    }

    /**
     * 查询计时终端应用参数
     * */
    public void cxzdyy(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        SharedPreferences yycssp = CjApplication.getInstance().getSharedPreferences("yycs", Context.MODE_PRIVATE);
        Map<String, Object> list = (Map<String, Object>) yycssp.getAll();

        Yycs yycs=new Yycs();
        for(String s :list.keySet()){
            if(!s.equals("isset")){
                try {
                    Field f=yycs.getClass().getDeclaredField(YycsProp.getValue(s));
                    f.setAccessible(true);
                    f.set(yycs,String.valueOf(list.get(s)));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        yycs.setCsbh("0");
        byte[] b3=yycs.getYycsBytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0503",me.getJpbxh(), "13", "2");
        List<Tdata> tlist=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");
        GatewayService.sendHexMsgToServer("serverChannel", tlist);
    }

    /**
     * 教练员登录应答
     * */
    public void jldl(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        JlydlR jr= (JlydlR) me.getO();
        if(NettyConf.debug) {
            Log.e("TAG", "教练员登录结果:" + jr.getJg());
        }

        Message msg=new Message();
        msg.arg1 = 1;
        Bundle bundle = new Bundle();
        bundle.putSerializable("jldlr", jr);
        msg.setData(bundle);
        Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
        handler.sendMessage(msg);


    }

    /**
     * 教练员登出应答
     * */
    public void jldc(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        JlydcR jlydcr = (JlydcR) me.getO();
        if(NettyConf.debug) {
            Log.e("TAG", "教练员登出结果:" + jlydcr.getJg());
        }
        if(NettyConf.handlersmap.get("logincoach")!=null) {
            Message msg = new Message();
            msg.arg1 = 2;
            Bundle bundle = new Bundle();
            bundle.putSerializable("jlydcr", jlydcr);
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
            handler.sendMessage(msg);
        }else{
            ZdUtil.handleCoachOut();
        }
    }

    /**
     * 学员登录
     * */
    public void xydl(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        XydlR xydlr = (XydlR) me.getO();
        if(NettyConf.debug) {
            Log.e("TAG", "学员登录结果:" + xydlr.getJg());
        }
        Message msg=new Message();
        msg.arg1=1;
        //传递xydl
        Bundle bundle = new Bundle();
        bundle.putSerializable("xydlr",xydlr);
        msg.setData(bundle);
        Handler handler = (Handler) NettyConf.handlersmap.get("stulogin");
        handler.sendMessage(msg);

    }
    /**
     * 学员登出
     * */
    public void xydc(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        XydcR xydcr = (XydcR) me.getO();
        if(NettyConf.handlersmap.get("stuout")!=null) {
            Message msg = new Message();
            msg.arg1 = 2;
            Bundle bundle = new Bundle();
            bundle.putSerializable("xydcr", xydcr);
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("stuout");
            handler.sendMessage(msg);
        }else{
            ZdUtil.handleStudentOut(NettyConf.xbh);
        }
    }

    /**
     * 上传终端照片
     * */
    public void sczp(MsgAll msgAll){
        Header header=msgAll.getHeader();
        MsgExtend me= (MsgExtend) msgAll.getObject();
        SczdzpR sr= (SczdzpR) me.getO();
        if(NettyConf.debug) {
            Log.e("TAG", "照片初始化结果：" + sr.getJg());
        }
        if(sr.getJg()==0){
            //正式上传照片数据
            int lsh=header.getMsgserno();
            String sql = "select * from tdata where parentid=?";
            String[] param = {String.valueOf(lsh)};
            ArrayList<Tdata> list = DbHandle.queryTdata(sql, param);
            if(NettyConf.debug){
                Log.e("TAG","照片详情发送数量:"+list.size());
            }
            if(list.size()>0){
                ZpdataSend zs=new ZpdataSend(list,0,1);
                Thread t=new Thread(zs);
                t.start();
            }
        }
    }


    /**
     * 处理立即拍照指令
     * @param msgAll
     */
    public void ljpz(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        Ljpz ljpz= (Ljpz) me.getO();

        //判断拍照结果...
        int jg=1;
        if(ZdUtil.ispz){
            jg=4;
        }

        //第一次应答
        LjpzR lr=new LjpzR();
        lr.setJg(jg);
        lr.setScms(Integer.parseInt(ljpz.getScms()));
        lr.setTdh(Integer.parseInt(ljpz.getTdh()));
        lr.setSjcc("1");
        byte[] b3=lr.getLjpzRbytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0301",me.getJpbxh(), "13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");

        GatewayService.sendHexMsgToServer("serverChannel", list);

        if(jg==1) {
            String sjlx = "0";
            if (NettyConf.xystate == 1) {
                sjlx = "19";
            }
            ZdUtil.sendZpsc("1", ljpz.getTdh(), sjlx);
        }
    }

    /**
     * 设置禁训状态
     * @param msgAll
     */
    public void jxzt(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        Jxzt jxzt= (Jxzt) me.getO();
        SharedPreferences params = CjApplication.getInstance().getSharedPreferences("jxzt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = params.edit();
        edit.putString("zt",jxzt.getZt());
        edit.commit();

        //禁训状态立即生效
        NettyConf.jxzt= Integer.valueOf(jxzt.getZt());

        JxztR jr=new JxztR();
        jr.setJg(1);
        jr.setZt(Integer.parseInt(jxzt.getZt()));
        byte[] b3=jr.getJxztRbytes();
        byte[] b2= MsgUtilClient.getMsgExtendHf(b3,"0502",me.getJpbxh(), "13", "2");
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0900",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"1");
        GatewayService.sendHexMsgToServer("serverChannel", list);
    }

    /**
     * 身份认证
     * @param msgAll
     */
    public void sfrz(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        SfrzR sr= (SfrzR) me.getO();

            Message msg=new Message();
            msg.arg1=5;
            if(sr.getLx()==1){
                //教练员信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("jlxx",sr);
                msg.setData(bundle);
                Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
                handler.sendMessage(msg);
            }else if(sr.getLx()==4){
                //学员信息
                Bundle bundle = new Bundle();
                bundle.putSerializable("xyxx",sr);
                msg.setData(bundle);
                Handler handler = (Handler) NettyConf.handlersmap.get("stulogin");
                handler.sendMessage(msg);
            }

    }

    /**
     * 强制登出
     * 返回结果0成功
     * */
    public void imeiPasswordR(MsgAll msgAll){
        MsgExtend me= (MsgExtend) msgAll.getObject();
        ImeiPasswordR ir= (ImeiPasswordR) me.getO();
        if(NettyConf.debug){
            Log.e("TAG","密码验证结果："+ir.getJg());
        }
        Message msg = new Message();
        msg.arg1=10;
        Bundle bundle = new Bundle();
        if(ir.getType()==1){
            //教练员验证
            bundle.putInt("yzjg",ir.getJg());
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("logincoach");
            handler.sendMessage(msg);
        }else if(ir.getType()==4){
            //学员验证
            bundle.putInt("yzjg",ir.getJg());
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("loginstudent");
            handler.sendMessage(msg);
        }else if(ir.getType()==9){
            //设置权限验证
            bundle.putInt("yzjg",ir.getJg());
            msg.setData(bundle);
            Handler handler = (Handler) NettyConf.handlersmap.get("main");
            handler.sendMessage(msg);
        }
    }

    public void upgrade(MsgAll msgAll){
        int jg=0;
        Upgrade upgrade= (Upgrade) msgAll.getObject();
        String[] ss=upgrade.getMsg().split(";");
        String version=ss[0];
        String url=ss[1];
        if(NettyConf.xystate==1||NettyConf.jlstate==1){
            jg=1;
        }else {
            if (Double.valueOf(version) > Double.valueOf(NettyConf.version)) {
                //进行版本更新
                Message msg = new Message();
                msg.arg1 = 6;
                Bundle bundle = new Bundle();
                bundle.putSerializable("url", url);
                msg.setData(bundle);
                Handler handler = (Handler) NettyConf.handlersmap.get("login");
                handler.sendMessage(msg);
            } else {
                jg=2;
            }
        }

        byte[] b2=MsgUtilClient.generateCommonR(msgAll,jg);
        List<Tdata> list=MsgUtilClient.generateMsg(b2,"0001",msgAll.getHeader().getMsgserno(), NettyConf.mobile,"0");
        GatewayService.sendHexMsgToServer("serverChannel", list);

    }

}
