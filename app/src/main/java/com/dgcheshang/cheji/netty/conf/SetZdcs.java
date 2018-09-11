package com.dgcheshang.cheji.netty.conf;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/5/22 0022.
 */

public class SetZdcs {
    /**
     * 终端参数设置
     * */
    public void setZdcs(Context context){
        SharedPreferences zdcssp = context.getSharedPreferences("zdcs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = zdcssp.edit();
        edit.putString("isset","true");//是否设置
        edit.putString("0001","30");//客户端心跳发送间隔，单位为秒(s)
        edit.putString("0002","30");//TCP消息应答超时时间，单位为秒(s)
        edit.putString("0003","3");//TCP消息重传次数
        edit.putString("0004","050000");//UDP消息应答超时时间，单位为秒(s)
        edit.putString("0005","235500");//UDP消息重传次数
        edit.putString("0006","30");//SMS消息应答超时时间，单位为秒(s)
        edit.putString("0007","3");//SMS消息重传次数
        edit.putString("0010","0");//主服务器APN，无线通信拨号访问点。若网络制式为CDMA，则该处为PPP拨号号码
        edit.putString("0011","0");//主服务器无线通信拨号用户名
        edit.putString("0012","0");//主服务器无线通信拨号密码
        edit.putString("0013","192.168.8.138");//主服务器地址，IP或域名
        edit.putString("0014","0");//备份服务器APN，无线通信拨号访问点
        edit.putString("0015","0");//备份服务器无线通信拨号用户名
        edit.putString("0016","0");//备份服务器无线通信拨号密码
        edit.putString("0017","0");//备份服务器地址，IP或域名
        edit.putString("0018","7878");//服务器TCP端口
        edit.putString("0019","0");//服务器UDP端口
        edit.putString("0020","0");//位置汇报策略，0：定时汇报；1：定距汇报；2：定时和定距汇报
        edit.putString("0021","0");//位置汇报方案，0：根据ACC状态；1：根据登录状态和ACC状态，先判断登录状态，若登录再根据ACC状态
        edit.putString("0022","600");//驾驶员未登录汇报时间间隔，单位为秒(s),>0
        edit.putString("0027","15");//休眠时汇报时间间隔，单位为秒(s),>0
        edit.putString("0028","15");//紧急报警时汇报时间间隔，单位为秒(s),>0
        edit.putString("0029","15");//缺省时间汇报间隔，单位为秒(s),>0
        edit.putString("002C","50");//缺省距离汇报间隔，单位为米(m),>0
        edit.putString("002D","150");//驾驶员未登录汇报距离间隔，单位为米(m),>0
        edit.putString("002E","50");//休眠时汇报距离间隔，单位为米(m),>0
        edit.putString("002F","50");//紧急报警时汇报距离间隔，单位为米(m),>0
        edit.putString("0030","90");//拐点补传角度，<180°
        edit.putString("0040","0");//监控平台电话号码
        edit.putString("0041","0");//复位电话号码，可采用此电话号码拨打终端电话让终端复位
        edit.putString("0042","0");//恢复出厂设置电话号码，可采用此电话号码拨打终端电话让终端恢复出厂设置
        edit.putString("0043","0");//监控平台SMS电话号码
        edit.putString("0044","0");//接收终端SMS文本报警号码
        edit.putString("0045","0");//终端电话接听策略，0：自动接听；1：ACC ON时自动接听，OFF时手动接听
        edit.putString("0046","0");//每次最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制
        edit.putString("0047","0");//当月最长通话时间，单位为秒(s),0为不允许通话，0xFFFFFFFF为不限制
        edit.putString("0048","");//监听电话号码
        edit.putString("0049","0");//监管平台特权短信号码
        edit.putString("0050","1");//报警屏蔽字。与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警被屏蔽
        edit.putString("0051","1");//报警发送文本SMS开关，与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警时发送文本SMS
        edit.putString("0052","1");//报警拍摄开关，与位置信息汇报消息中的报警标识相对应，相应位为1则相应报警时摄像头拍摄
        edit.putString("0053","1");//报警拍摄存储标识，与位置信息汇报消息中的报警标识相对应，相应位为1则对相应报警时牌的照片进行存储，否则实时长传
        edit.putString("0054","1");//关键标识，与位置信息汇报消息中的报警标识相对应，相应位为1则对相应报警为关键报警
        edit.putString("0055","100");//最高速度，单位为公里每小时(km/h)
        edit.putString("0056","5");//超速持续时间，单位为秒(s)
        edit.putString("0057","14400");//连续驾驶时间门限，单位为秒(s)
        edit.putString("0058","14400");//当天累计驾驶时间门限，单位为秒(s)
        edit.putString("0059","1200");//最小休息时间，单位为秒(s)
        edit.putString("0070","5");//图像/视频质量，1-10,1最好
        edit.putString("0071","180");//亮度，0-255
        edit.putString("0072","60");//对比度，0-127
        edit.putString("0073","60");//饱和度，0-127
        edit.putString("0074","120");//色度，0-255
        edit.putString("0080","1");//车辆里程表读数，1/10km
        edit.putString("0081","44");//车辆所在的省域ID
        edit.putString("0082","1900");//车辆所在的市域ID
        edit.putString("0083","");//公安交通管理部门颁发的机动车号牌
        edit.putString("0084","");//车牌颜色，按照JT/T415-2006的5.4.12
        edit.putString("0085","100");//车辆脉冲系数，车辆行驶1km距离过程中产生的脉冲信号个数
        edit.commit();
    }
/**
 * 应用参数
 * */
    public void setYycs(Context context){
        SharedPreferences zdcssp = context.getSharedPreferences("yycs", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = zdcssp.edit();
        edit.putString("isset","true");//是否已经设置
        edit.putString("1","15");//定时拍照时间间隔，单位：min，默认值15。在学员登录后间隔固定时间拍摄照片。
        edit.putString("2","1");//照片上传设置，0：不自动请求上传；1：自动请求上传
        edit.putString("3","1");//是否报读附加消息，1：自动报读；2：不报读。
        edit.putString("4","5");//熄火后停止学时计时的延时时间,单位：min
        edit.putString("5","3600");//熄火后GNSS数据包上传间隔，单位：s，默认值3600，0表示不上传
        edit.putString("6","150");//熄火后教练自动登出的延时时间，单位：min，默认值150
        edit.putString("7","30");//重新验证身份时间，单位：min，默认值30
        edit.putString("8","2");//教练跨校教学，1：允许，2：禁止
        edit.putString("9","1");//学员跨校学习，1：允许，默认值，2：禁止
        edit.putString("10","30");//响应平台同类消息时间间隔，单位：s，在该时间间隔内对平台发送的多次相同ID消息可拒绝执行回复失败
        edit.commit();
    }

    /**
     * 禁训状态
     * */
    public void setJxzt(Context context){
        SharedPreferences zdcssp = context.getSharedPreferences("jxzt", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = zdcssp.edit();
        edit.putString("isset","true");//是否已经设置
        edit.putString("zt","1");//1可用2禁用
        edit.commit();
    }

}
