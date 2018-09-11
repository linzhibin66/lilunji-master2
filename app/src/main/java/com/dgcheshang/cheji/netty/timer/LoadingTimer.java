package com.dgcheshang.cheji.netty.timer;

import android.app.Dialog;

import com.dgcheshang.cheji.Tools.LoadingDialogUtils;

import java.util.TimerTask;

/**
 *控件定时器
 */

public class LoadingTimer extends TimerTask{
    private Dialog loading;
    public LoadingTimer(Dialog loading){
        this.loading=loading;
    }
    @Override
    public void run() {
        LoadingDialogUtils.closeDialog(loading);
    }
}
