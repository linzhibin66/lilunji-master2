package com.dgcheshang.cheji.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.dgcheshang.cheji.Bean.database.StudentBean;
import com.dgcheshang.cheji.Database.DbHandle;
import com.dgcheshang.cheji.R;
import com.dgcheshang.cheji.Tools.LoadingDialogUtils;
import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;


/**
 * 学员登录
 * */
public class LoginStudentActivity extends Activity implements View.OnClickListener{

    Context context=LoginStudentActivity.this;
    private String TAG="LoginStudentActivity";
    public static final int LOGIN_STU_SUCCESS = 1;
    ArrayList<StudentBean> studentlist;
    TextView tv_kechen;
    ListView listview;
    String yzmm;
    Dialog loading;
    TextView tv_coachname,loginnum;
    int isqiantui=0;//强退状态,0表示未强退，1表示强退
    Handler handler=new  Handler(){
        @Override
        public void handleMessage(Message msg) {
            if(msg.arg1==10){
                //强制登出验证返回结果
                int yzjg = msg.getData().getInt("yzjg");
                if(yzjg==0){
                    /*if(!ZdUtil.ispz){
                        //学员登出
                        ZdUtil.qzStuOut();
                    }else {
                        Toast.makeText(context,",正在拍照请稍后操作",Toast.LENGTH_SHORT).show();
                        loading.cancel();
                    }*/
                    isqiantui=1;
                    myAdapter.notifyDataSetChanged();

                }else {
                    loading.cancel();
                    Speaking.in("密码验证失败");
                }
            }else if(msg.arg1==11){
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);
        NettyConf.handlersmap.put("loginstudent",handler);
        initView();
    }

    /**
     * 初始化布局
     * */
    MyAdapter myAdapter;
    private void initView() {
        SharedPreferences coachsp = getSharedPreferences("coach", Context.MODE_PRIVATE);//教练保存的数据
        View layout_back = findViewById(R.id.layout_back);
        tv_kechen = (TextView) findViewById(R.id.tv_kechen);//选择课程显示
        listview = (ListView) findViewById(R.id.listview);//学员登陆列表
        Button bt_login = (Button) findViewById(R.id.bt_login);//登录
        Button bt_out = (Button) findViewById(R.id.bt_out);//登录
        View layout_qzout = findViewById(R.id.layout_qzout);//强制登出
        tv_coachname = (TextView) findViewById(R.id.tv_coachname);//教练姓名
        loginnum = (TextView) findViewById(R.id.loginnum);//登录个数
        if(NettyConf.jlstate!=0){
            tv_kechen.setText(coachsp.getString("xzkc",""));
            tv_coachname.setText(coachsp.getString("jlxm",""));
        }

        layout_qzout.setOnClickListener(this);
        layout_back.setOnClickListener(this);
        bt_login.setOnClickListener(this);
        bt_out.setOnClickListener(this);
    }

    /**
     * 点击监听
     * */
    @Override
    public void onClick(View view) {
        Intent intent = new Intent();

        switch (view.getId()){
            case R.id.layout_back://返回
                finish();
                break;

            case R.id.bt_login://登录
                if(ZdUtil.canLogin()) {
                    intent.setClass(context, StuLoginActivity.class);
//                    startActivityForResult(intent, REQUEST_A);
                    startActivity(intent);
                }
                break;

            case R.id.bt_out://登出
                intent.setClass(context,StuOutActivity.class);
//                startActivityForResult(intent,REQUEST_B);
                startActivity(intent);
                break;
            case R.id.layout_qzout://强制登出
                showliuyanDialog();
                break;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        if(NettyConf.debug){
            Log.e("TAG","onResume");
        }
        //显示登录列表
        studentlist = DbHandle.stuQuery();
        loginnum.setText(studentlist.size()+"");
        Collections.reverse(studentlist);
        myAdapter = new MyAdapter();
        listview.setAdapter(myAdapter);
    }

    /**
     * 已登录学员Adapter
     * */
    public class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if(studentlist!=null){
                return studentlist.size();
            }else {
                return 0;
            }
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            ViewHodler viewHodler=null;
            if(convertView==null){
                convertView= LayoutInflater.from(context).inflate(R.layout.stulist2_item,null);
                viewHodler = new ViewHodler();
                viewHodler.tv_name = (TextView) convertView.findViewById(R.id.tv_name);//姓名
                viewHodler.bt_only_out = (Button) convertView.findViewById(R.id.bt_only_out);//登出按钮
                viewHodler.tv_idcard = (TextView) convertView.findViewById(R.id.tv_idcard);//身份证
                viewHodler.tv_logintime = (TextView) convertView.findViewById(R.id.tv_logintime);//登录时间

                convertView.setTag(viewHodler);
            }else {
                viewHodler = (ViewHodler) convertView.getTag();
            }
            StudentBean studentbean = studentlist.get(position);
            String xm = studentbean.getXm();
            if(isqiantui==0){
                viewHodler.bt_only_out.setVisibility(View.GONE);
            }else {
                viewHodler.bt_only_out.setVisibility(View.VISIBLE);
            }
            final String sfzh = studentbean.getSfzh();
            String tybh = studentbean.getTybh();
            String sj=studentbean.getSj();
            SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sj=sdf.format(new Date(Long.valueOf(sj)));
            viewHodler.tv_name.setText(xm);
            viewHodler.tv_idcard.setText(sfzh);
            viewHodler.tv_logintime.setText(sj);
            //退出按钮监听
            viewHodler.bt_only_out.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return convertView;
        }
    }

    class ViewHodler{
        TextView tv_name;
        Button bt_only_out;
        TextView tv_idcard;
        TextView tv_logintime;

    }

    /**
     * 强制登出dialog
     *
     * */

    private void showliuyanDialog(){
        final AlertDialog builder = new AlertDialog.Builder(this,R.style.CustomDialog).create(); // 先得到构造器
        builder.show();
        builder.getWindow().setContentView(R.layout.dialog_appoint_edt);
        builder.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);//解决不能弹出键盘
        LayoutInflater factory = LayoutInflater.from(this);
        View view = factory.inflate(R.layout.dialog_appoint_edt, null);
        builder.getWindow().setContentView(view);
        final EditText edt_content = (EditText) view.findViewById(R.id.edt_content);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        Button bt_cacnel = (Button) view.findViewById(R.id.bt_cacnel);
        Button bt_sure = (Button) view.findViewById(R.id.bt_sure);
        tv_title.setText("登出验证");

        //取消
        bt_cacnel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.dismiss();
            }
        });

        //确定
        bt_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                yzmm = edt_content.getText().toString().trim();
                if(!yzmm.equals("")){
                    loading = LoadingDialogUtils.createLoadingDialog(context, "正在登出...");
                    ZdUtil.matchPassword(4,yzmm);
                    builder.dismiss();
                }else {
                    Toast.makeText(context,"请输入登出密码",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        NettyConf.handlersmap.remove("loginstudent");
    }

}
