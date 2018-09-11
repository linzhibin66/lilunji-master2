package com.dgcheshang.cheji;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.dgcheshang.cheji.netty.init.ZdClient;

/**
 * Created by Administrator on 2017/4/28 0028.
 */
public class CjApplication extends Application {
    private static CjApplication instance;
    private static RequestQueue queue ;

    public static CjApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        MultiDex.install(CjApplication.this);
        super.onCreate();
        instance=this;

        queue = Volley.newRequestQueue(getApplicationContext()); // 实例化RequestQueue对象
    }

    public static RequestQueue getHttpQueue() {
        return queue;
    }

}
