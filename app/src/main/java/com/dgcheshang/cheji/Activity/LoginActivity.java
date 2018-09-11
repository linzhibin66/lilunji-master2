package com.dgcheshang.cheji.Activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.usb.UsbDevice;
import android.media.AudioManager;
import android.media.SoundPool;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.dgcheshang.cheji.Bean.VersionBean;
import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.R;
import com.dgcheshang.cheji.Tools.CameraManager;
import com.dgcheshang.cheji.Tools.Helper;
import com.dgcheshang.cheji.Tools.LoadingDialogUtils;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.Tools.update.DownLoadApk;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.init.ZdClient;
import com.dgcheshang.cheji.netty.thread.SpeakThread;
import com.dgcheshang.cheji.netty.timer.CacheTimer;
import com.dgcheshang.cheji.netty.timer.LoginoutTimer;
import com.dgcheshang.cheji.netty.timer.LoginoutWarnTimer;
import com.dgcheshang.cheji.netty.timer.OutTimer;
import com.dgcheshang.cheji.netty.util.InitUtil;
import com.dgcheshang.cheji.netty.util.ZdUtil;
import com.dgcheshang.cheji.networkUrl.NetworkUrl;
import com.google.gson.Gson;
import com.rscja.deviceapi.OTG;
import com.serenegiant.usb.CameraDialog;
import com.serenegiant.usb.DeviceFilter;
import com.serenegiant.usb.IFrameCallback;
import com.serenegiant.usb.Size;
import com.serenegiant.usb.USBMonitor;
import com.serenegiant.usb.UVCCamera;
import com.serenegiant.widget.UVCCameraTextureView;
import com.shenyaocn.android.Encoder.CameraRecorder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;

/**
 * 主菜单
 * */
public class LoginActivity extends BaseActivity implements View.OnClickListener,CameraDialog.CameraDialogParent {
    public static LoginActivity instance = null;

    Context context = LoginActivity.this;

    private static final String TAG = "MainActivity";
    public static final int REQUEST_COACH = 1;//跳转教练登录页面
    public static final int REQUEST_STUDENT = 2;//跳转学员登录页面
    public static final int REQUEST_SETTING = 3;//跳转设置ip,端口页面

    Dialog loading;
    TextView tv_coach_state,tv_student_state;
    private final Object mSync = new Object();
    String fileurl="/sdcard/APPdown";//下载文件夹路径
    BroadcastReceiver receiver;//下载广播
    NetworkReceiver networkReceiver;//网络监听广播
    View layout_showphoto;
    SoundPool soundPool;
    public static boolean hyconstate=false;//华盈连接标志
    //摄像头参数
    private USBMonitor mUSBMonitor;					// 用于监视USB设备接入
    private UVCCamera mUVCCameraL;					// 表示左边摄像头设备
    private UVCCamera mUVCCameraR;					// 表示右边摄像头设备
    private OutputStream snapshotOutStreamL;		// 用于左边摄像头拍照
    private String snapshotFileNameL;
    private UVCCameraTextureView mUVCCameraViewL;	// 用于左边摄像头预览
    private Surface mLeftPreviewSurface;
    private static final int PREVIEW_WIDTH = 320;
    private static final int PREVIEW_HEIGHT = 240;
    private static final boolean DEBUG = true;
    private CameraRecorder mp4RecorderL=new CameraRecorder(1);
    private int currentWidth = UVCCamera.DEFAULT_PREVIEW_WIDTH;
    private int currentHeight = UVCCamera.DEFAULT_PREVIEW_HEIGHT;


    Handler handler=new  Handler(){
        @Override
        public void handleMessage(Message msg) {

            if(msg.arg1==1) {//注销
                handleCancel(msg);
            }else if(msg.arg1==2){
                Bundle data = msg.getData();
                final String scms = data.getString("scms");
                final String tdh = data.getString("tdh");
                final String lx = data.getString("lx");
                final String gnss=data.getString("gnss");
                //拍照提前发出滴滴声
                soundPool.play(1,1, 1, 0, 0, 1);
                //开启摄像头
                mUSBMonitor.register();
                final String path = captureSnapshot();
                if(NettyConf.debug){
                    Log.e("TAG","照片路径："+path);
                }

                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ZdUtil.sendZpsc2(scms,tdh,lx,gnss,path);
                        //  关闭摄像头
                        releaseCameraL();
                        mUSBMonitor.unregister();
                    }
                },3000);
            }else if(msg.arg1==3){

            }else if(msg.arg1==5){
                handleshow();
            }else if(msg.arg1==6){
                Bundle data = msg.getData();
                String url= (String) data.getSerializable("url");
                downFile(url);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        new Thread(new SpeakThread()).start();

        NettyConf.handlersmap.put("login",handler);
        //初始化
        InitUtil.initSystem();
        loading = LoadingDialogUtils.createLoadingDialog(context, "正在初始化...");
        initView();
        //广播
        registerReceiver();
        instance = this;

        //拍照秒提示嘀嘀声初始化
        soundPool= new SoundPool(10, AudioManager.STREAM_SYSTEM,5);
        soundPool.load(CjApplication.getInstance(), R.raw.didi4,1);

        //清除缓存数据
        CacheTimer cacheTimer=new CacheTimer();
        new Timer().schedule(cacheTimer,0,24*60*60*1000);


        //是否启动强制退出
        new Timer().schedule(new OutTimer(),20);

        //启动强制登出定时器
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date=new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
        date=calendar.getTime();
        StringBuffer sb=new StringBuffer(sdf.format(date).substring(0,11));
        sb.append("00:00:00");
        try {
            if(NettyConf.debug){
                Log.e("TAG","登出计时器触发时间:"+sb.toString());
            }
            //String hs="2017-07-17 15:08:20";
            Date d=sdf.parse(sb.toString());
            LoginoutTimer lt=new LoginoutTimer();
            new Timer().schedule(lt,d,24*60*60*1000);

            calendar.setTime(d);
            calendar.add(Calendar.MINUTE, -5);

            Date d2=calendar.getTime();
            if(NettyConf.debug){
                Log.e("TAG","登出报警计时器触发时间:"+sdf.format(d2));
            }
            LoginoutWarnTimer lwt=new LoginoutWarnTimer();

            //String hs2="2017-07-17 15:08:00";
            new Timer().schedule(lwt,d2,24*60*60*1000);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 初始化布局
     * */
    private void initView() {
        View layout_back = findViewById(R.id.layout_back);//返回
        View layout_coach = findViewById(R.id.layout_coach);//教练
        View layout_student = findViewById(R.id.layout_student);//学员
        tv_coach_state = (TextView) findViewById(R.id.tv_coach_state);//教练显示状态
        tv_student_state = (TextView) findViewById(R.id.tv_student_state);//学员显示状态
        View layout_cardetail = findViewById(R.id.layout_cardetail);//车辆信息
        View layout_about = findViewById(R.id.layout_about);//关于我们
        View layout_basic_set = findViewById(R.id.layout_basic_set);//基本设置
        View layout_setting = findViewById(R.id.layout_setting);//参数设置
        layout_showphoto = findViewById(R.id.layout_showphoto);//显示拍照框
        layout_showphoto.setVisibility(View.INVISIBLE);

        //摄像头
        mUVCCameraViewL = (UVCCameraTextureView)findViewById(R.id.camera_view_L);
        mUVCCameraViewL.setAspectRatio(PREVIEW_WIDTH / (float)PREVIEW_HEIGHT);

        refreshControls();
        mUSBMonitor = new USBMonitor(this, mOnDeviceConnectListener);
        final List<DeviceFilter> filters = DeviceFilter.getDeviceFilters(this, R.xml.device_filter);
        mUSBMonitor.setDeviceFilter(filters);
        mUSBMonitor.register();//start
        refreshControls();

        layout_back.setOnClickListener(this);
        layout_coach.setOnClickListener(this);
        layout_student.setOnClickListener(this);
        layout_cardetail.setOnClickListener(this);
        layout_about.setOnClickListener(this);
        layout_setting.setOnClickListener(this);
        layout_basic_set.setOnClickListener(this);
        layout_showphoto.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                layout_showphoto.setVisibility(View.INVISIBLE);
                //关闭摄像头
                if(mUSBMonitor.isRegistered()){
                    //注册了
                    releaseCameraL();
                    mUSBMonitor.unregister();
                }
                return true;
            }
        });
    }

    /**
     * 点击监听
     * */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            case R.id.layout_back://摄像头
                layout_showphoto.setVisibility(View.VISIBLE);
                if(mUSBMonitor!=null){
                    //注册了
                    releaseCameraL();
                    mUSBMonitor.unregister();
                }
                mUSBMonitor.register();
                break;

            case R.id.layout_coach://教练员管理
                if(NettyConf.jlstate==1||ZdUtil.canLogin()) {
                    intent.setClass(context, LoginCoachActivity.class);
                    startActivityForResult(intent, REQUEST_COACH);
                }
                break;

            case R.id.layout_student://学员管理
                if(ZdUtil.canLogin()) {
                    if (NettyConf.jlstate == 0) {
                        Toast.makeText(context, "请先教练员登录", Toast.LENGTH_SHORT).show();
                    } else {
                        intent.setClass(context, LoginStudentActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
                break;

            case R.id.layout_basic_set://基本设置
                intent.setClass(context,SystemSetActivity.class);
                startActivityForResult(intent, REQUEST_SETTING);
                break;

            case R.id.layout_setting://设置
                intent.setClass(context,MainActivity.class);
                startActivityForResult(intent, REQUEST_SETTING);
                break;


            case R.id.layout_cardetail://车辆信息
                intent.setClass(context,CarDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.layout_about://关于我们
                intent.setClass(context,AboutActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

        }
    }
    /**
     * 跳转页面返回回来结果处理
     * */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode){
            case REQUEST_COACH://教练页面返回来
                switch (resultCode){
                    case LoginCoachActivity.LOGIN_COA_SUCCESS://教练返回回来

                        break;
                }
                break;

            case REQUEST_STUDENT://学员页面返回来
                switch (resultCode){
                    case LoginStudentActivity.LOGIN_STU_SUCCESS://学员返回回来

                        break;
                }
                break;

        }
    }

    /**
     * 控制返回键无效
     * */
    public boolean onKeyDown(int keyCode,KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            //这里重写返回键
//            return true;
        }
        return false;
    }


    /**
     * 注销返回处理
     *
     * @param msg*/
    public void handleCancel(Message msg){

        Bundle data = msg.getData();
        int zxjg = (int) data.get("zxjg");
        if(zxjg==1){//注销成功
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
            //清除保存状态
            SharedPreferences jianquan = getSharedPreferences("jianquan", Context.MODE_PRIVATE);
            Intent intent = new Intent();
            intent.setClass(context,MainActivity.class);
            jianquan.edit().clear();
            startActivity(intent);
            finish();
        }else {
            //注销失败
        }
    }

    public void handleshow(){
        if(NettyConf.jlstate==1){
            tv_coach_state.setText("教练员管理(已登录)");
        }else {
            tv_coach_state.setText("教练员管理(未登录)");
        }
    }

    /**
     * app获取版本,是否需要更新
     * */
    public void getVersion( ) {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkUrl.UpdateCodeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    Gson gson = new Gson();
                    VersionBean versionbean = gson.fromJson(response, VersionBean.class);

                    //管理员卡uid
                    if(!versionbean.getManageruid().equals("")){
                        //保存管理员卡号
                        SharedPreferences uidsp = getSharedPreferences("uid", Context.MODE_PRIVATE);
                        SharedPreferences.Editor edit = uidsp.edit();
                        if(NettyConf.debug){
                            Log.e("TAG",versionbean.getManageruid());
                        }
                        edit.putString("uid",versionbean.getManageruid());
                        edit.commit();
                    }
                    //判断是否版本一致
                    if (Double.valueOf(versionbean.getVersion())>Double.valueOf(NettyConf.version)) {
                        //进行版本更新
//                        updateDialog(versionbean.getUrl(), versionbean.getMsg());
                        if(versionbean.getImei().equals("")){
                            //全部更新
                            downFile(versionbean.getUrl());
                        }else {
                            //个别更新
                            String imei = versionbean.getImei();
                            String[] split = imei.split(",");
                            for (int i=0; i<split.length;i++){
                                if(split[i].equals(NettyConf.imei)){
                                    downFile(versionbean.getUrl());
                                    return;
                                }

                            }
                        }
                    }
                }catch (Exception e){

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TGA","volleyError="+volleyError);
            }
        }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> map = new HashMap<>();
                return map;
            }
        };
        CjApplication.getHttpQueue().add(request);
    }


    /**
     * 版本更新提示
     *
     * @param url
     * @param msg*/
    private void updateDialog(final String url, final String msg){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);  //先得到构造器
        builder.setTitle("版本提示"); //设置标题
        builder.setMessage("有新版本更新，是否更新？"); //设置内容
        builder.setPositiveButton("下载", new DialogInterface.OnClickListener() { //设置确定按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                Toast.makeText(context, "开始下载", Toast.LENGTH_SHORT).show();
                DownLoadApk.download(context,url,"驾培车机下载","cheshang");
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() { //设置取消按钮
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        //参数都设置完成了，创建并显示出来//不可按返回键取消
        builder.setCancelable(false).create().show();
    }

    /**
     * 下载文件
     * */
    public void downFile(String url){
        loading = LoadingDialogUtils.createLoadingDialog(context, "版本更新中...");
        File  destDir = new File(fileurl);
        //先判断是否有之前下载的文件，有则删除，
        if (!destDir.exists()) {
            destDir.mkdirs();
        }else {
            File[] files = destDir.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()){
                    File appfile = new File(files[i].getPath());
                    appfile.delete();
                }
            }
            destDir.mkdirs();
        }
        final DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse(url);
        DownloadManager.Request request = new DownloadManager.Request(uri);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir("APPdown", "cheji.apk");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE);
        request.setDescription("培训系统app正在下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        request.setAllowedOverRoaming(false);
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
         request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
         final long refernece = dManager.enqueue(request);

        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);

        receiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                       long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
//                                Intent install = new Intent(Intent.ACTION_VIEW);
//                                Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
//                                 install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
//                                install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                                startActivity(install);
                                updateApp();
                               }
                        }
              };
         registerReceiver(receiver, filter);
        }

    /**
     * 自动升级
     * */
    public void updateApp(){

        Boolean blStart = true;    //  安装后是否启动 ,  true，启动； false，不启动
        Intent apk = new Intent("com.wskyo.intent.systemupdate.InstallApk");
        apk.setPackage("com.wskyo.systemupdate");
        apk.addFlags(Intent.FLAG_RECEIVER_FOREGROUND);
        apk.putExtra("ApkFile", fileurl+"/cheji.apk");//  apk 目录
        apk.putExtra("ApkStart",blStart);
        startService(apk);

    }

    /**
     * 注册网络监听广播
     * */

    private  void registerReceiver(){
        IntentFilter filter=new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        networkReceiver = new NetworkReceiver();
        this.registerReceiver(networkReceiver, filter);
    }

    /**
     * 网络广播
     * */
    public class NetworkReceiver extends BroadcastReceiver {
        boolean shenji=false;
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            NetworkInfo wifiNetInfo = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

            boolean state;

            if (!mobNetInfo.isConnected() && !wifiNetInfo.isConnected()) {
                state=false;
            }else{
                state=true;
                if(shenji==false){
                    getVersion();//获取版本更新
                    shenji=true;
                }
            }

            if (NettyConf.netstate == null) {
                NettyConf.netstate = state;
                if(state){
                    ZdUtil.conServer();
                }
            } else if (NettyConf.netstate != state) {
                NettyConf.netstate = state;
                //如果网络变化
                if (state) {
                    ZdUtil.conServer();
                } else {
                    if (ZdClient.conTimer != null) {
                        ZdClient.conTimer.cancel();
                        ZdClient.conTimer = null;
                    }

                    NettyConf.constate = 0;
                    NettyConf.jqstate = 0;
                    Speaking.in("网络已断开");
                }
            }

        }
    }

    // 实现快照抓取
    private synchronized String captureSnapshot() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH.mm.ss.SSSS");
        Date currentTime = new Date();
        //判断哪个摄像头在使用，则抓拍哪个

        //左边摄像头在使用
        snapshotFileNameL = Environment.getExternalStorageDirectory().getAbsolutePath() + "/chejiCamera";
        File path = new File(snapshotFileNameL);
        if (!path.exists())
            path.mkdirs();
        snapshotFileNameL += "/IPC_";
        snapshotFileNameL += format.format(currentTime);
        snapshotFileNameL += ".L.jpg";
        File recordFile = new File(snapshotFileNameL);	// 左边摄像头快照的文件名
        if(recordFile.exists()) {
            recordFile.delete();
        }
        try {
            boolean newFile = recordFile.createNewFile();
            snapshotOutStreamL = new FileOutputStream(recordFile);

        } catch (Exception e){
            Log.e("TAG",e.getMessage());
        }

        return snapshotFileNameL;

    }


    private synchronized void releaseCameraL() {
        synchronized (this) {

            if (mUVCCameraL != null) {
                try {
                    mUVCCameraL.setStatusCallback(null);
                    mUVCCameraL.setButtonCallback(null);
                    mUVCCameraL.close();
                    mUVCCameraL.destroy();
                } catch (final Exception e) {
                    Log.e(TAG, e.getMessage());
                } finally {
                    //Log.d(TAG, "*******releaseCameraL mUVCCameraL=null");
                    mUVCCameraL = null;
                }
            }
            if (mLeftPreviewSurface != null) {
                mLeftPreviewSurface.release();
                mLeftPreviewSurface = null;
            }
//			try {
//
//			if (mLeftPreviewSurface != null) {
//				mLeftPreviewSurface.release();
//				mLeftPreviewSurface = null;
//			}
//				}
//			 catch (final Exception e) {
//					Log.e(TAG, e.getMessage());
//			}finally{
//				mLeftPreviewSurface = null;
//				//Log.d(TAG, "*******releaseCameraL mLeftPreviewSurface=null");
//			}
        }
    }


    //判断是哪个摄像头在使用
    Boolean isCameraL =false;
    Boolean isCameraR =false;
    private final USBMonitor.OnDeviceConnectListener mOnDeviceConnectListener = new USBMonitor.OnDeviceConnectListener() {
        @Override
        public void onAttach(final UsbDevice device) {
            if (DEBUG) Log.i(TAG, "onAttach:" + device);
            final List<UsbDevice> list = mUSBMonitor.getDeviceList();
            mUSBMonitor.requestPermission(list.get(0));

            if(list.size() > 1)
                new Handler().postDelayed(new Runnable() {public void run() {mUSBMonitor.requestPermission(list.get(1));}}, 200);
        }

        @Override
        public void onConnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock, final boolean createNew) {

            if (DEBUG) Log.i(TAG, "onConnect:"+ctrlBlock.getVenderId());

            if(!NettyConf.camerastate) {
                NettyConf.camerastate = true;
//                Speaking.in("摄像头已开启");
            }
            if(loading!=null){
                loading.cancel();

            }
            synchronized (this) {
                if (mUVCCameraL != null && mUVCCameraR != null) { // 如果左右摄像头都打开了就不能再接入设备了
                    return;
                }
                if (ctrlBlock.getVenderId() == 2){

                    if (mUVCCameraL != null && mUVCCameraL.getDevice().equals(device)){
                        return;
                    }
                } else if (ctrlBlock.getVenderId() == 3) {

                    if ((mUVCCameraR != null && mUVCCameraR.getDevice().equals(device))) {
                        return;
                    }
                }else {
                    return;
                }
                final UVCCamera camera = new UVCCamera();
                final int open_camera_nums = (mUVCCameraL != null ? 1 : 0) + (mUVCCameraR != null ? 1 : 0);
                camera.open(ctrlBlock);

                try {
                    camera.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT, UVCCamera.FRAME_FORMAT_MJPEG, 0.5f); // 0.5f是一个重要参数，表示带宽可以平均分配给两个摄像头，如果是一个摄像头则是1.0f，可以参考驱动实现
                } catch (final IllegalArgumentException e1) {
                    if (DEBUG) Log.i(TAG, "MJPEG Failed");
                    try {
                        camera.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT, UVCCamera.DEFAULT_PREVIEW_MODE, 0.5f);
                    } catch (final IllegalArgumentException e2) {
                        try {
                            currentWidth = UVCCamera.DEFAULT_PREVIEW_WIDTH;
                            currentHeight = UVCCamera.DEFAULT_PREVIEW_HEIGHT;
                            camera.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT, UVCCamera.DEFAULT_PREVIEW_MODE, 0.5f);
                        } catch (final IllegalArgumentException e3) {
                            camera.destroy();

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //Toast.makeText(ShowCameraViewActivity.this, "UVC设备错误", Toast.LENGTH_LONG).show();
                                }
                            });

                            return;
                        }
                    }
                }

                // 将摄像头进行分配
                if(ctrlBlock.getVenderId() == 2 ||ctrlBlock.getVenderId() == 3&& mUVCCameraL == null) {
                    isCameraL=true;
                    mUVCCameraL = camera;
                    try {
                        if (mLeftPreviewSurface != null) {
                            mLeftPreviewSurface.release();
                            mLeftPreviewSurface = null;
                        }

                        final SurfaceTexture st = mUVCCameraViewL.getSurfaceTexture();
                        if (st != null)
                            mLeftPreviewSurface = new Surface(st);
                        mUVCCameraL.setPreviewDisplay(mLeftPreviewSurface);

                        mUVCCameraL.setFrameCallback(mUVCFrameCallbackL, UVCCamera.PIXEL_FORMAT_YUV420SP);
                        mUVCCameraL.startPreview();
                    } catch (final Exception e) {
                        Log.e(TAG, e.getMessage());
                    }
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        refreshControls();

                        //      if (mUVCCameraL != null || mUVCCameraR != null)
                        //      startAudio();
                    }
                });
            }
        }

        @Override
        public void onDisconnect(final UsbDevice device, final USBMonitor.UsbControlBlock ctrlBlock) {
            if (DEBUG) Log.i(TAG, "onDisconnect:" + device);
//            if(NettyConf.camerastate) {
//                NettyConf.camerastate = false;
//                Speaking.in("摄像头已断开");
//            }
		/*	runOnUiThread(new Runnable() {
				@Override
				public void run() {
					//refreshControls();
					if (mUVCCameraL == null && mUVCCameraR == null)
						stopAudio();
				}
			});*/
        }

        @Override
        public void onDettach(final UsbDevice device) {
            if (DEBUG) Log.i(TAG, "onDettach:" + device);
            if ((mUVCCameraL != null) && mUVCCameraL.getDevice().equals(device)) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        releaseCameraL();
                    }
                });

            }

        }

        @Override
        public void onCancel(final UsbDevice device) {
            if (DEBUG) Log.i(TAG, "onCancel:");
        }
    };

    // 左边摄像头的NV21视频帧回调
    private final IFrameCallback mUVCFrameCallbackL = new IFrameCallback() {
        @Override
        public void onFrame(final ByteBuffer frame) {

            if(mUVCCameraL == null)
                return;

            final Size size = mUVCCameraL.getPreviewSize();
            byte[] buffer = null;

            int FrameSize = frame.remaining();
            if (buffer == null) {
                buffer = new byte[FrameSize];
                frame.get(buffer);
            }
            if (mp4RecorderL.isVideoRecord()) { // 将视频帧发送到编码器
                mp4RecorderL.feedData(buffer);
            }

            if(snapshotOutStreamL != null) { // 将视频帧压缩成jpeg图片，实现快照捕获
                if (!(FrameSize < size.width * size.height * 3 / 2) && (buffer != null)) {
                    try {
                        new YuvImage(buffer, ImageFormat.NV21, size.width, size.height, null).compressToJpeg(new Rect(0, 0, size.width, size.height), 60, snapshotOutStreamL);
                        snapshotOutStreamL.flush();
                        snapshotOutStreamL.close();
                        Helper.fileSavedProcess(LoginActivity.this, snapshotFileNameL);
                    } catch (Exception ex) {
                    } finally {
                        snapshotOutStreamL = null;
                    }
                }
            }
            buffer = null;
        }
    };


    // 刷新UI控件状态
    private void refreshControls() {
        try {
            findViewById(R.id.textViewUVCPromptL).setVisibility(mUVCCameraL != null ? View.GONE : View.VISIBLE);
            invalidateOptionsMenu();
        } catch (Exception e){}
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("TAG", "onStart:");
        OTG.getInstance().on();

    }

    @Override
    protected void onResume() {
        super.onResume();
        handleshow();
    }

    @Override
    protected void onStop() {
        Log.e("TAG", "onStop:");
        super.onStop();
        if (mUSBMonitor != null) {
            releaseCameraL();
            mUSBMonitor.unregister();

        }
    }

    @Override
    public void onDestroy() {
        Log.e("TAG", "onDestroy:");

        //解绑广播
        if(receiver!=null){
            unregisterReceiver(receiver);
        }
        if(networkReceiver!=null){
            unregisterReceiver(networkReceiver);
        }
        if(loading!=null){
            loading.cancel();
        }
        if (mUSBMonitor != null) {
            mUSBMonitor.destroy();
            mUSBMonitor = null;
        }
        super.onDestroy();
    }


    /**
     * to access from CameraDialog
     * @return
     */
    @Override
    public USBMonitor getUSBMonitor() {
        return mUSBMonitor;
    }

    @Override
    public void onDialogResult(boolean canceled) {
        if (DEBUG) Log.v(TAG, "onDialogResult:canceled=" + canceled);
        if (canceled) {
        }
    }

}
