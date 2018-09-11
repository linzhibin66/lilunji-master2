package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import com.dgcheshang.cheji.R;
import com.rscja.customservices.ICustomServices;

/**
 * 起始页
 * */
public class LogoActivity extends Activity {

    ICustomServices mCustomServices;
    Context context=LogoActivity.this;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        initView();
        Intent intentCust = new Intent();
//        intentCust.addFlags(BIND_WAIVE_PRIORITY);
        intentCust.setAction("com.rscja.CustomService");
        intentCust.setPackage("com.rscja.customservices");
        bindService(intentCust, mServiceConnection, Context.BIND_AUTO_CREATE);
    }



    @Override
    protected void onDestroy() {
        //解除绑定
        unbindService(mServiceConnection);
        super.onDestroy();
    }

    ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            mCustomServices = ICustomServices.Stub.asInterface(iBinder);
            try {
                mCustomServices.setLauncher("com.dgcheshang.cheji","LogoActivity");
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

    private void initView() {
        ImageView image_logo = (ImageView) findViewById(R.id.image_logo);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.logo_anim);
        image_logo.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent();
                intent.setClass(context,LoginActivity.class);
                startActivity(intent);
                finish();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
