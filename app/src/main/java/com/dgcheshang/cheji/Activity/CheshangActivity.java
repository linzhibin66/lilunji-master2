package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.dgcheshang.cheji.R;

/**
 *车尚科技
 */

public class CheshangActivity extends Activity implements View.OnClickListener{
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cheshang);
        initView();
    }

    /**
     * 初始化布局
     * */
    private void initView() {
        View layout_back = findViewById(R.id.layout_back);
        layout_back.setOnClickListener(this);

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
        }
    }
}
