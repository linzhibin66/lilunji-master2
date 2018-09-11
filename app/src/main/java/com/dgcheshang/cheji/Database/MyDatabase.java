package com.dgcheshang.cheji.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.dgcheshang.cheji.Bean.database.PicBean;
import com.dgcheshang.cheji.Bean.database.RegistSuccessBean;
import com.dgcheshang.cheji.Bean.database.StudentBean;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.po.Xsjl;
import com.dgcheshang.cheji.netty.po.Zpdata;
import com.dgcheshang.cheji.netty.po.Zpsc;
import com.dgcheshang.cheji.netty.serverreply.SfrzR;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import org.apache.commons.lang3.StringUtils;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *图片数据库
 */
public class MyDatabase extends SQLiteOpenHelper {
    private SQLiteDatabase db ;

    public MyDatabase(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.db = getWritableDatabase();
    }

    public MyDatabase(Context context) {//构造函数,接收上下文作为参数,直接调用的父类的构造函数
        super(context, DbConstants.DB_NAME, null, DbConstants.DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建照片上传初始化数据
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_ZPSC+ "("+
                "did integer primary key autoincrement,"+//多媒体数据主键
                "zpbh varchar(10),"+//照片编号
                "bh varchar(16),"+//编号
                "tdh varchar(5),"+//通道号
                "tpcc varchar(5),"+//图片尺寸
                "sjlx varchar(5),"+//时间类型
                "zbs varchar(5),"+//总包数
                "sjcd varchar(10),"+//照片数据长度
                "ktid varchar(20),"+//课堂id
                "rlsb varchar(5),"+//人脸识别
                "sj integer"+//时间
                ")"
        );

        //创建注册成功后存值
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_ZPDATA+ "("+
                "zid integer primary key autoincrement,"+//多媒体数据主键
                "zpbh varchar(10),"+//照片编号
                "zpsj text,"+//照片数据
                "sj integer"+//时间
                ")"
        );

        //创建注册成功后存值
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_DATA+ "("+
                "id integer primary key autoincrement,"+//多媒体数据主键
                "key varchar(20),"+//流水号
                "parentid varchar(20),"+//对应的主流水号，可以为空
                "data text,"+//缓存数据
                "level integer,"+//缓存数据
                "initsj integer,"+//最初保存时间
                "sj integer"+//时间
                ")"
        );

        //创建注册成功后存值
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_DATAF+ "("+
                "id integer primary key autoincrement,"+//多媒体数据主键
                "key varchar(20),"+//流水号
                "parentid varchar(20),"+//对应的主流水号，可以为空
                "data text,"+//缓存数据
                "level integer,"+//缓存数据
                "initsj integer,"+//最初保存时间
                "sj integer"+//时间
                ")"
        );

        //创建注册成功后存值
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_SFRZ+ "("+
                "id integer primary key autoincrement,"+//多媒体数据主键
                "uuid varchar(10),"+//对应内码
                "lx varchar(2),"+//人员类型
                "tybh varchar(32),"+//统一编号
                "sfzh varchar(32),"+//身份证号
                "cx varchar(4),"+//车型
                "xx text,"+//身份信息
                "xm varchar(32),"+//身份信息
                "sj integer"+//时间
                ")"
        );


        //学员登录后保存信息
        db.execSQL( "CREATE TABLE IF NOT EXISTS "+
                DbConstants.T_STU_LOGIN+ "("+
                "id integer primary key autoincrement,"+//学员登录后数据主键
                "tybh varchar(32),"+//统一编号
                "sfzh varchar(32),"+//身份证号
                "cx varchar(4),"+//车型
                "xm varchar(32),"+//姓名
                "pxkc varchar(20),"+//培训课程
                "ktid varchar(20),"+//课堂ID
                "sj integer"+//时间
                ")"
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
