package com.dgcheshang.cheji.netty.util;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.netty.conf.NettyConf;

public class LocationUtil {
	public static boolean state=true;//定位状态

	LocationListener locationListener = new LocationListener() {
		/**
		 * 位置信息变化时触发
		 */
		@Override
		public void onLocationChanged(Location location) {


		}
		/**
		 * GPS状态变化时触发
		 */
		@Override
		public void onStatusChanged(String s, int i, Bundle bundle) {
		}
		/**
		 * GPS开启时触发
		 */
		@Override
		public void onProviderEnabled(String s) {
			if(NettyConf.debug) {
				Log.e("TAG", "GPS开启时触发");
			}
		}
		/**
		 * GPS禁用时触发
		 */
		@Override
		public void onProviderDisabled(String s) {
			state=false;
			if(NettyConf.debug) {
				Log.e("TAG", "GPS禁用时触发");
			}
		}
	};
	/**
	 * 获取经纬度
	 *
	 * */
	public void getGPS() {
		LocationManager locationManager = (LocationManager) CjApplication.getInstance().getSystemService(Context.LOCATION_SERVICE);
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置定位精准度
		criteria.setAltitudeRequired(true);//是否要求海拔
		criteria.setBearingRequired(true);//是否要求方向
		criteria.setCostAllowed(false);//是否要求收费
		criteria.setSpeedRequired(true);//是否要求速度
		criteria.setBearingAccuracy(Criteria.ACCURACY_HIGH);//设置方向精确度
		criteria.setSpeedAccuracy(Criteria.ACCURACY_HIGH);//设置速度精确度
		criteria.setPowerRequirement(Criteria.POWER_LOW);//设置相对省电
//         取得效果最好的位置服务
		String provider = locationManager.getBestProvider(criteria, true);
		if (ActivityCompat.checkSelfPermission(CjApplication.getInstance(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(CjApplication.getInstance(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
			// TODO: Consider calling
			//    ActivityCompat#requestPermissions
			// here to request the missing permissions, and then overriding
			//   public void onRequestPermissionsResult(int requestCode, String[] permissions,
			//                                          int[] grantResults)
			// to handle the case where the user grants the permission. See the documentation
			// for ActivityCompat#requestPermissions for more details.
			return;
		}
		locationManager.getLastKnownLocation(provider);

		locationManager.requestLocationUpdates(provider, 1000, 0, locationListener);

//		float speed = location.getSpeed();//速度
//		float bearing = location.getBearing();//方向
//		long time = location.getTime();//时间
//		double altitude = location.getAltitude();//海拔高度
//		float accuracy = location.getAccuracy();//精准度
//		double latitude = location.getLatitude();//纬度
//		double longitude = location.getLongitude();//经度
//
//		Log.d("TAG","定位信息："+location);


	}



	
}


