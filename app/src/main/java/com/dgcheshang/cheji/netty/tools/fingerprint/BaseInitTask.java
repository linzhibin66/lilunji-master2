package com.dgcheshang.cheji.netty.tools.fingerprint;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import com.dgcheshang.cheji.R;

/**
 * 模块初始化任务类
 *
 * @author liuruifeng 2014-5-6
 */
public class BaseInitTask extends AsyncTask<String, Integer, Boolean> {
    protected ProgressDialog mypDialog;
    protected Activity mContxt;

    public BaseInitTask(Activity act) {
        mContxt = act;
    }

    @Override
    protected void onCancelled(Boolean aBoolean) {
        super.onCancelled(aBoolean);
        dismissDialog();

        Log.i("BaseInitTask", "onCancelled()");

    }

    public void dismissDialog() {
        if (mypDialog != null) {
            mypDialog.cancel();
        }
    }

    @Override
    protected Boolean doInBackground(String... params) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    protected void onPostExecute(Boolean result) {
        super.onPostExecute(result);

        mypDialog.cancel();

        if (!result) {
            UIHelper.ToastMessage(mContxt, R.string.fingerprint_msg_init_fail);
        }
    }

    @Override
    protected void onPreExecute() {
        // TODO Auto-generated method stub
        super.onPreExecute();

        mypDialog = new ProgressDialog(mContxt);
        mypDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mypDialog.setMessage(mContxt.getString(R.string.app_msg_init));
        mypDialog.setCanceledOnTouchOutside(false);
        if (mContxt != null) {
            mypDialog.show();
        }
    }

}
