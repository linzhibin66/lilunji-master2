package com.dgcheshang.cheji.netty.util;


import android.util.Log;

import com.rscja.deviceapi.BDNavigation;
import com.rscja.deviceapi.BDNavigation.BDLocationListener;
import com.rscja.deviceapi.entity.BDLocation;

/**
 * Created by Administrator on 2017/5/23 0023.
 */

public class BDLocationUtil{
    public static BDNavigation bd;

    BDLocationListener listener=new  BDLocationListener(){
        @Override
        public void onLocationChanged(BDLocation bdLocation) {
            Log.e("TAG",""+bdLocation.getLat());
            Log.e("TAG",""+bdLocation.getLon());
            Log.e("TAG",""+bdLocation.getAltitude());
        }

        @Override
        public void onDataResult(String s) {
            Log.e("TAG","模块返回数据时调用："+s);
        }
    };

    public BDLocationUtil() {
        try {
            this.bd=BDNavigation.getInstance();
            bd.open();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getGps(){
        bd.addBDLocationListener(BDNavigation.BDProviderEnum.valueOf("BD"), listener);
    }
}
