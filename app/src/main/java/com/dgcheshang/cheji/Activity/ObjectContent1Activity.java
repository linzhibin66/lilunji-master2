package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dgcheshang.cheji.Bean.ObjectBean;
import com.dgcheshang.cheji.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * 培训列表
 * */
public class ObjectContent1Activity extends Activity implements View.OnClickListener{

    Context context=ObjectContent1Activity.this;

    public static final int LOGIN_CONTENT_SUCCESS = 1;

    String objectone="{\"oneobject\":[{\"name1\":\"法律、法规及道路交通信号\",\"number\":\"01\"},{\"name1\":\"机动车基本知识\",\"number\":\"02\"},{\"name1\":\"第一部分综合复习及考核\",\"number\":\"03\"}]}";
    String objectfour="{\"fourobject\":[{\"name1\":\"安全、文明驾驶知识\",\"number\":41},{\"name1\":\"危险源辨识知识\",\"number\":42},{\"name1\":\"夜间和高速公路安全驾驶知识\",\"number\":43},{\"name1\":\"恶劣气象和复杂道路条件下的安全驾驶知识\",\"number\":44},{\"name1\":\"紧急情况应急处置知识\",\"number\":45},{\"name1\":\"危险化学品知识\",\"number\":46},{\"name1\":\"典型事故案例分析\",\"number\":47},{\"name1\":\"第四部分综合复习及考核\",\"number\":48}]}";
    String objecttype;
    List<ObjectBean> objectBean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_content1);
        initView();
    }

    /**
     * 初始化布局
     * */
    private void initView() {
        Bundle extras = getIntent().getExtras();
        objecttype = extras.getString("objecttype");//获取StudyChangeActivity的培训部分：2为第二部分，3为第三部分
        View layout_back = findViewById(R.id.layout_back);//返回
        TextView tv_title = (TextView) findViewById(R.id.tv_title);//标题
        ListView listView = (ListView) findViewById(R.id.listView);
        JSONObject jsonObject = null;
        try {
            Gson gson = new Gson();
            if(objecttype.toString().equals("1")){
                tv_title.setText("第一部分");
                jsonObject = new JSONObject(objectone);
                String twoobject = jsonObject.getString("oneobject");
                objectBean = gson.fromJson(twoobject, new TypeToken<List<ObjectBean>>() {
                }.getType());

            }else if(objecttype.toString().equals("4")){
                tv_title.setText("第四部分");
                jsonObject = new JSONObject(objectfour);
                String twoobject = jsonObject.getString("fourobject");
                objectBean = gson.fromJson(twoobject, new TypeToken<List<ObjectBean>>() {
                }.getType());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        MyAdapter myAdapter = new MyAdapter();
        listView.setAdapter(myAdapter);
        layout_back.setOnClickListener(this);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Intent intent = new Intent();
                intent.putExtra("pxnr",objectBean.get(position).getName1());//传值培训内容
                intent.putExtra("kcbh",objectBean.get(position).getNumber());//传培训课程编号
                intent.putExtra("objecttype",objecttype);//培训第几部分
                setResult(LOGIN_CONTENT_SUCCESS,intent);
                finish();
            }
        });
    }



    /**
     * 点击监听
     * */
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.layout_back://返回
                finish();
                break;
        }
    }

    /**
     * list适配器
     * */
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return   objectBean.size();

        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            ViewHodler viewHodler=null;
            if(convertView==null){
                convertView= LayoutInflater.from(context).inflate(R.layout.object_list_item,null);
                viewHodler = new ViewHodler();
                viewHodler.tv_content = (TextView) convertView.findViewById(R.id.tv_content);

                convertView.setTag(viewHodler);
            }else {
                viewHodler = (ViewHodler) convertView.getTag();
            }

            ObjectBean objectBean = ObjectContent1Activity.this.objectBean.get(position);

            viewHodler.tv_content.setText(objectBean.getName1());

            return convertView;
        }
    }
    public class ViewHodler{
        TextView tv_content;

    }

}
