package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
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
import com.dgcheshang.cheji.Tools.LoadingDialogUtils;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.networkUrl.NetworkUrl;
import com.google.gson.Gson;

import java.util.HashMap;
import java.util.Map;

/**
 *关于我们
 */

public class AboutActivity extends Activity implements View.OnClickListener{

    Context context=AboutActivity.this;
    private Dialog loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        initView();
    }

    /**
     * 初始化布局
     * */
    private void initView() {
        View layout_back = findViewById(R.id.layout_back);
        View layout_cheshang = findViewById(R.id.layout_cheshang);//车尚科技
        View layout_update = findViewById(R.id.layout_update);//版本更新

        TextView tv_version = (TextView) findViewById(R.id.tv_version);//版本号


        tv_version.setText(NettyConf.version);
        layout_back.setOnClickListener(this);
        layout_cheshang.setOnClickListener(this);
        layout_update.setOnClickListener(this);
    }

    /**
     * 点击监听
     * */
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.layout_back://返回
                finish();
                break;

            case R.id.layout_cheshang://车尚科技
                Intent intent = new Intent();
                intent.setClass(context,CheshangActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                break;

            case R.id.layout_update:
                loading = LoadingDialogUtils.createLoadingDialog(context, "正在检测...");
                getVersion();
                break;
        }

    }

    /**
     * 检测新版本
     * */
    private void getVersion() {
        StringRequest request = new StringRequest(Request.Method.POST, NetworkUrl.UpdateCodeUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loading.cancel();
                try {
                    Gson gson = new Gson();
                    VersionBean versionbean = gson.fromJson(response, VersionBean.class);

                    //判断是否版本一致
                    if (Double.valueOf(versionbean.getVersion())>Double.valueOf(NettyConf.version)) {
                        updateDialog(versionbean);
                    }else {
                        Toast.makeText(context,"当前已是最新版本",Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    if(NettyConf.debug){
                        Log.e("TAG",e.getMessage());
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.e("TGA","volleyError="+volleyError);
                Toast.makeText(AboutActivity.this,"网络请求失败",Toast.LENGTH_SHORT).show();
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
     * 更新版本Dialog
     * */
    private void updateDialog(final VersionBean versionbean) {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(AboutActivity.this);
        dialog.setTitle("版本更新");
        dialog.setMessage("最新版本"+versionbean.getVersion()+"，是否更新？");
        dialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        //进行版本更新
                        //downFile(versionbean.getUrl());

                        Message msg = new Message();
                        msg.arg1 = 6;
                        Bundle bundle = new Bundle();
                        bundle.putSerializable("url",versionbean.getUrl());
                        msg.setData(bundle);
                        Handler handler = (Handler) NettyConf.handlersmap.get("login");
                        handler.sendMessage(msg);
                        finish();
                    }
                });
        dialog.setNegativeButton("取消",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                    }
                });
        // 显示
        dialog.show();

    }
}
