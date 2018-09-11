package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dgcheshang.cheji.R;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.LocationUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;

/**
 * 车辆信息
 * */
public class CarDetailActivity extends Activity implements View.OnClickListener{
    Context context = CarDetailActivity.this;
    TextView tv_net_state,tv_con_state,tv_jq_state,tv_camera_state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_detail);
        init();
    }

    /**
     * 初始化布局
     * */
    private void init() {
        View layout_back = findViewById(R.id.layout_back);//返回按键
        TextView tv_phonenum = (TextView) findViewById(R.id.tv_phonenum);//手机号码
        TextView tv_imei = (TextView) findViewById(R.id.tv_imei);//IMEI
        TextView tv_IPaddress = (TextView) findViewById(R.id.tv_IPaddress);//IP地址
        TextView tv_model = (TextView) findViewById(R.id.tv_model);//型号
        tv_net_state = (TextView) findViewById(R.id.tv_net_state);//网络状态
        tv_con_state = (TextView) findViewById(R.id.tv_con_state);//连接状态
        tv_jq_state = (TextView) findViewById(R.id.tv_jq_state);//鉴权状态
        tv_camera_state = (TextView) findViewById(R.id.tv_camera_state);//摄像头状态

        int lianjie = NettyConf.constate;
        int jianquan =NettyConf.jqstate;
        boolean camerastate = NettyConf.camerastate;

        if(NettyConf.netstate!=null){
            if(NettyConf.netstate){
                tv_net_state.setText("连接");
            }else {
                tv_net_state.setText("断开");
            }
        }else{
            tv_net_state.setText("监听失败");
        }

        if(lianjie==1){
            tv_con_state.setText("已连接");
        }else {
            tv_con_state.setText("已断开");
        }
        if(jianquan==1){
            tv_jq_state.setText("已鉴权");
        }else {
            tv_jq_state.setText("未鉴权");
        }

        if(camerastate){
            tv_camera_state.setText("连接");
        }else {
            tv_camera_state.setText("断开");
        }
        tv_imei.setText(NettyConf.imei);
        tv_phonenum.setText(NettyConf.mobile);
        tv_model.setText(NettyConf.model);
        tv_IPaddress.setText(ZdUtil.getIPAddress(context));
        layout_back.setOnClickListener(this);
    }

    /**
     * 点击监听
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back://返回按钮
                finish();
                break;
        }
    }
}
