package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.InputType;
import android.util.Log;
import android.os.RemoteException;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.dgcheshang.cheji.R;
import com.dgcheshang.cheji.Tools.LoadingDialogUtils;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.timer.CardTimer;
import com.dgcheshang.cheji.netty.timer.LoadingTimer;
import com.dgcheshang.cheji.netty.util.GatewayService;
import com.dgcheshang.cheji.netty.util.ZdUtil;
import com.rscja.customservices.ICustomServices;
import com.rscja.deviceapi.RFIDWithISO14443A;

import org.apache.commons.lang3.StringUtils;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import io.netty.channel.ChannelHandlerContext;


/**
 * 端口IP连接
 * */
public class MainActivity extends Activity  implements View.OnClickListener{

    public Context context=MainActivity.this;

    public static final int SETTING_SUCCESS = 0;//设置成功后
    public static boolean isGPS=false;//判断GPS是否启动
    public final int SETTING=0;
    EditText edt_sheng,edt_shi,edt_phonenumb,edt_duankou,edt_ip;
    int color=0;
    int jianchen=0;//省份简称
    TextView tv_show_uid;
    String uid;
    View layout_admin;
    RFIDWithISO14443A mRFID;
    Button bt_sure,bt_cancel,bt_have_set;
    SharedPreferences.Editor editor;
    private Dialog loading;
    LoadingTimer loadingTimer;
    Timer timer;
    ICustomServices mCustomServices;
    private  Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1==1){
                //鉴权
                jqHmandle(msg);

            }else if(msg.arg1==2){
                //注销
                handleCancel(msg);
            }else if(msg.arg1==6){
                if(NettyConf.cardtimer!=null){
                    NettyConf.cardtimer.cancel();
                    NettyConf.cardtimer=null;
                }
                //刷卡成功后返回uid
                String adminuid = msg.getData().getString("adminuid");
                tv_show_uid.setText("UID:"+adminuid);
                adminLogin(adminuid);

            }else if(msg.arg1==7){
                //注册失败
                if(loadingTimer!=null) {
                    loadingTimer.cancel();
                }
                if(timer!=null) {
                    timer.cancel();
                }
                LoadingDialogUtils.closeDialog(loading);
            }else if (msg.arg1==10){
                //强制登出返回回来处理
                int yzjg = msg.getData().getInt("yzjg",1);
                if(yzjg==0){
                    LoadingDialogUtils.closeDialog(loading);
                    edt_ip.setInputType(InputType.TYPE_CLASS_TEXT);
                    edt_duankou.setInputType(InputType.TYPE_CLASS_TEXT);
                    edt_sheng.setInputType(InputType.TYPE_CLASS_TEXT);
                    edt_shi.setInputType(InputType.TYPE_CLASS_TEXT);
                    edt_phonenumb.setInputType(InputType.TYPE_CLASS_TEXT);
                    layout_admin.setVisibility(View.VISIBLE);
                }else {
                    loading.cancel();
                    Speaking.in("密码验证失败");
                }
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NettyConf.handlersmap.put("main",handler);
        initView();

        CardTimer.isstop=false;
        if(NettyConf.cardtimer!=null){
            NettyConf.cardtimer.cancel();
            NettyConf.cardtimer=null;
        }
        try {
            mRFID = RFIDWithISO14443A.getInstance();
        } catch (Exception e) {
        }

    }

    /**
     * 初始化布局
     * */
    private void initView() {
        SharedPreferences jianquan = getSharedPreferences("jianquan", Context.MODE_PRIVATE);
        editor = jianquan.edit();
        //管理员卡号
        SharedPreferences uidsp = getSharedPreferences("uid", Context.MODE_PRIVATE);
        uid = uidsp.getString("uid", NettyConf.uid);

        View layout_back = findViewById(R.id.layout_back);//返回按钮
        tv_show_uid = (TextView) findViewById(R.id.tv_show_uid);//uid显示
        layout_admin = findViewById(R.id.layout_admin);//管理员布局显示
        bt_sure = (Button) findViewById(R.id.bt_sure);//确定按钮
        bt_cancel = (Button) findViewById(R.id.bt_cancel);//注销按钮
        bt_have_set = (Button) findViewById(R.id.bt_have_set);//获取设置权限
        edt_ip = (EditText) findViewById(R.id.edt_ip);//IP
        edt_duankou = (EditText) findViewById(R.id.edt_duankou);//端口
        edt_sheng = (EditText) findViewById(R.id.edt_sheng);//省域
        edt_shi = (EditText) findViewById(R.id.edt_shi);//市域
        edt_phonenumb = (EditText) findViewById(R.id.edt_phonenumb);//手机号
        edt_ip.setInputType(InputType.TYPE_NULL);
        edt_duankou.setInputType(InputType.TYPE_NULL);
        edt_sheng.setInputType(InputType.TYPE_NULL);
        edt_shi.setInputType(InputType.TYPE_NULL);
        edt_phonenumb.setInputType(InputType.TYPE_NULL);
        layout_admin.setVisibility(View.INVISIBLE);
        //赋值
        if(NettyConf.host.equals("0")||NettyConf.port==0){
            edt_ip.setText("");
            edt_duankou.setText("");
        }else {
            edt_ip.setText(NettyConf.host);
            edt_duankou.setText(String.valueOf(NettyConf.port));
        }
        edt_sheng.setText(NettyConf.shengID);
        edt_shi.setText(NettyConf.shiID);
        edt_phonenumb.setText(NettyConf.mobile);
        if(NettyConf.jqstate==1){
            bt_sure.setText("已连接");
        }
        edt_phonenumb.setText(NettyConf.mobile);

        bt_sure.setOnClickListener(this);
        layout_back.setOnClickListener(this);
        bt_cancel.setOnClickListener(this);
        bt_have_set.setOnClickListener(this);
    }

    /**
     * 点击监听
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.bt_sure://确定
                //判断是否进行设置
                String sheng = edt_sheng.getText().toString().trim();
                String shi = edt_shi.getText().toString().trim();
                String host = edt_ip.getText().toString().trim();
                String duankou = edt_duankou.getText().toString().trim();
                String phonenumb = edt_phonenumb.getText().toString().trim();
                if(sheng.equals("")||shi.equals("")||host.equals("")||duankou.equals("")||phonenumb.equals("")){
                    Toast.makeText(context,"信息未填写完整",Toast.LENGTH_SHORT).show();
                }else {
                    //保存车辆信息
                    SharedPreferences zdcssp = context.getSharedPreferences("zdcs", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edit = zdcssp.edit();
                    edit.putString("0081",sheng);//省域
                    edit.putString("0082",shi);//市域
                    edit.putString("0048",phonenumb);//电话号码
                    edit.putString("0013",host);//ip
                    edit.putString("0018",duankou);//ip
                    edit.commit();

                    NettyConf.shengID=sheng;
                    NettyConf.shiID=shi;
                    NettyConf.mobile=phonenumb;
                    NettyConf.host=host;
                    if(StringUtils.isNotEmpty(duankou)) {
                        NettyConf.port = Integer.valueOf(duankou);
                    }
                    loading = LoadingDialogUtils.createLoadingDialog(context, "正在连接...");
                    loadingTimer = new LoadingTimer(loading);
                    timer = new Timer();
                    timer.schedule(loadingTimer,NettyConf.controltime);
                    putData();
                }

                break;

            case R.id.layout_back://返回
                setHomekeylock();
                finish();
                break;

            case R.id.bt_cancel://注销

                if(NettyConf.xystate==0&&NettyConf.jlstate==0){//判断是否学员跟教练员都登出
                    showCancelDialog();
                }else {
                    Toast.makeText(context,"当前还有未登出状态，请先登出",Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.bt_have_set://获取设置权限
                haveSetDialog();
                break;
        }
    }

    /**
     * 获取设置权限Dialog
     * */
    private void haveSetDialog() {
        final AlertDialog builder = new AlertDialog.Builder(this,R.style.CustomDialog).create(); // 先得到构造器
        builder.show();
        builder.getWindow().setContentView(R.layout.dialog_appoint_edt);
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//解决不能弹出键盘
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.dialog_appoint_edt, null);
        builder.getWindow().setContentView(view);
        final EditText edt_content = (EditText) view.findViewById(R.id.edt_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        Button bt_cacnel = (Button) view.findViewById(R.id.bt_cacnel);
        Button bt_sure = (Button) view.findViewById(R.id.bt_sure);
        tv_title.setText("密码验证");

        //取消
        bt_cacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        //确定
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String yzmm = edt_content.getText().toString().trim();
                if(!yzmm.equals("")){
                    builder.dismiss();
                    loading = LoadingDialogUtils.createLoadingDialog(context, "正在验证...");
                    ZdUtil.matchPassword(9,yzmm);


                }else {
                    Toast.makeText(context,"请输入验证密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 连接接口
     * */
    private void putData() {
        Object o=GatewayService.getGatewayChannel("serverChannel");
        if(o!=null){
            ChannelHandlerContext clientChannel = (ChannelHandlerContext) o;
            clientChannel.close();
        }else {
            ZdUtil.conServer();
        }
    }

    /**
     * 判断服务是否启动,context上下文对象 ，className服务的name
     **/
    public static boolean isServiceRunning(Context mContext, String className) {

        boolean isRunning = false;
        ActivityManager activityManager = (ActivityManager) mContext
                .getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                .getRunningServices(30);

        if (!(serviceList.size() > 0)) {
            return false;
        }

        for (int i = 0; i < serviceList.size(); i++) {
            if (serviceList.get(i).service.getClassName().equals(className) == true) {
                isRunning = true;
                break;
            }
        }
        return isRunning;
    }

    /**
     * 控制返回键无效
     * */
    public boolean onKeyDown(int keyCode,KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            //这里重写返回键
//            return true;
//            showOutDialog();
        }
        return false;

    }

    /**
     * 退出dialog
     * */
    private void showOutDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定要退出本应用吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        int currentVersion = android.os.Build.VERSION.SDK_INT;
                        if (currentVersion > android.os.Build.VERSION_CODES.ECLAIR_MR1) {
                            Intent startMain = new Intent(Intent.ACTION_MAIN);
                            startMain.addCategory(Intent.CATEGORY_HOME);
                            startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(startMain);
                            System.exit(0);
                        } else {// android2.1
                            android.app.ActivityManager am = (android.app.ActivityManager) getSystemService(ACTIVITY_SERVICE);
                            am.restartPackage(getPackageName());
                        }
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 注销dialog
     * */
    private void showCancelDialog(){
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);
        normalDialog.setTitle("提示");
        normalDialog.setMessage("您确定要注销连接吗？");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        ZdUtil.sendZdzx();
                    }
                });
        normalDialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
        // 显示
        normalDialog.show();
    }

    /**
     * 鉴权返回来处理
     * */
    public void jqHmandle(Message msg){
        int jqjg = msg.getData().getInt("jqjg");
        if(jqjg==0){
            bt_sure.setText("已连接");
        }
        if(loadingTimer!=null) {
            loadingTimer.cancel();
        }
        if(timer!=null) {
            timer.cancel();
        }
        LoadingDialogUtils.closeDialog(loading);
    }

    /**
     * 注销返回处理
     *
     * @param msg*/
    public void handleCancel(Message msg){

        Bundle data = msg.getData();
        int zxjg = (int) data.get("zxjg");
        if(zxjg==0){//注销成功
            //清除定位计时器
            Object o=NettyConf.timermap.get("wzhb");
            if(o!=null){
                Timer timer= (Timer) o;
                timer.cancel();
            }
            //清除定位服务
            o=NettyConf.servicemap.get("wzhb");
            if(o!=null){
                Intent intent= (Intent) o;
                stopService(intent);
            }

            NettyConf.zcstate=0;//改变注册状态
            NettyConf.jqstate=0;//改变鉴权状态
            bt_sure.setText("连接");
            //清除保存状态
            SharedPreferences jianquan = getSharedPreferences("jianquan", Context.MODE_PRIVATE);
            jianquan.edit().clear();
            Speaking.in("注销成功");

        }else {
            //注销失败
            Speaking.in("注销失败");
        }
    }

    /**
     * 管理人员登录进行设置
     *
     * @param adminuid*/
    public void adminLogin(String adminuid){
        if(!StringUtils.isEmpty(uid)){
            if(uid.contains(adminuid)){
                layout_admin.setVisibility(View.VISIBLE);
                edt_ip.setInputType(InputType.TYPE_CLASS_TEXT);
                edt_duankou.setInputType(InputType.TYPE_CLASS_TEXT);
                edt_sheng.setInputType(InputType.TYPE_CLASS_TEXT);
                edt_shi.setInputType(InputType.TYPE_CLASS_TEXT);
                edt_phonenumb.setInputType(InputType.TYPE_CLASS_TEXT);
                setHomekey();
                return;
            }else{
                Speaking.in("此卡无管理员权限");
            }
        }else{
            Speaking.in("此卡无效");
        }
    }

    /**
     * 释放home键跟菜单键，及下拉通知栏功能
     * */
    boolean b;
    public void setHomekey(){
        Intent intentCust = new Intent();
        intentCust.setAction("com.rscja.CustomService");
        intentCust.setPackage("com.rscja.customservices");
         b = bindService(intentCust, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mCustomServices = ICustomServices.Stub.asInterface(iBinder);
            try {
                mCustomServices.setLauncher("com.dgcheshang.cheji","MainActivity");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //禁用
            try {
                if (mCustomServices != null) {
                    //启用/禁用home键
                    mCustomServices.setHomeKeyEnable(true);
                    //启用/禁用多任务键
                    mCustomServices.setAppSwitchKeyEnable(true);
                    //禁止下拉菜单
                    mCustomServices.setStatusBarDown(true);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    boolean b2;
    public void setHomekeylock(){
        if(b){
            unbindService(mServiceConnection);
            b=false;
        }

        Intent intentCust = new Intent();
        intentCust.setAction("com.rscja.CustomService");
        intentCust.setPackage("com.rscja.customservices");
        b2 = bindService(intentCust, mServiceConnectionlock, Context.BIND_AUTO_CREATE);
    }

    ServiceConnection mServiceConnectionlock = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mCustomServices = ICustomServices.Stub.asInterface(iBinder);
            try {
                mCustomServices.setLauncher("com.dgcheshang.cheji","MainActivity");
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            //禁用
            try {
                if (mCustomServices != null) {
                    //启用/禁用home键
                    mCustomServices.setHomeKeyEnable(false);
                    //启用/禁用多任务键
                    mCustomServices.setAppSwitchKeyEnable(false);
                    //禁止下拉菜单
                    mCustomServices.setStatusBarDown(false);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName componentName) {
        }
    };

    @Override
    protected void onResume() {
        if(NettyConf.debug){
            Log.e("TAG","onResume");
        }
        super.onResume();
        //刷卡
        mRFID.init();

        if(NettyConf.cardtimer!=null){
            NettyConf.cardtimer.cancel();
            NettyConf.cardtimer=null;
        }

        CardTimer cardTimer=new CardTimer(mRFID,"admincard");
        NettyConf.cardtimer=new Timer();
        NettyConf.cardtimer.schedule(cardTimer,300,2000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(NettyConf.debug){
            Log.e("TAG","ondestory");
        }

        NettyConf.handlersmap.remove("main");
        if(timer!=null){
            timer.cancel();
        }
        if(NettyConf.cardtimer!=null){
            NettyConf.cardtimer.cancel();
            NettyConf.cardtimer=null;
        }
        if(mRFID!=null){
            mRFID.free();
        }
        if(b){
            unbindService(mServiceConnection);
        }

        if(b2){
            unbindService(mServiceConnectionlock);
        }


    }
}
