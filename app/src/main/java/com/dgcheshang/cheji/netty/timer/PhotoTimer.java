package com.dgcheshang.cheji.netty.timer;

import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

public class PhotoTimer extends TimerTask{
	public String TAG="PhotoTimer";

	@Override
	public void run() {
		if(ZdUtil.pdGps()) {
			ZdUtil.sendZpsc("129", "0", "5");
		}else{
		}
	}
}


