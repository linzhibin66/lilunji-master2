package com.dgcheshang.cheji.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * SQLite数据库单例管理
 */

public class DbManager {
    private static MyDatabase myDatabase=null;
    private static DbManager instance;
    //多线程控制
    private AtomicInteger mOpenCounter = new AtomicInteger();
    private SQLiteDatabase db;

    /**
     * 获取实例
     * @param context
     * @return
     */
    public static synchronized DbManager getInstance(Context context){
        if(instance==null){
            initializeInstance(context);
        }
        return instance;
    }

    /**
     * 初始化实例
     * @param context
     */
    public static synchronized void initializeInstance(Context context){
        if(instance==null){
            myDatabase=getMyDatabase(context);
            instance=new DbManager();
        }
    }

    /**
     * 调取数据库
     * @param context
     * @return
     */
    public static MyDatabase getMyDatabase(Context context){
        if(myDatabase==null){
            init(context);
        }
        return myDatabase;
    }

    private static synchronized void init(Context context){
        myDatabase=new MyDatabase(context);
    }

    /*public synchronized SQLiteDatabase getWritableDatabase(){
        if(mOpenCounter.incrementAndGet()==1){
            //db=myDatabase.getWritableDatabase();
            Log.e("TAG","打开占用的数量："+mOpenCounter);
            db=myDatabase.getReadableDatabase();
        }
        return db;
    }*/

    public synchronized SQLiteDatabase getReadableDatabase(){
        /*if(mOpenCounter.incrementAndGet()==1){
            Log.e("TAG","打开占用的数量："+mOpenCounter);
            db=myDatabase.getReadableDatabase();
        }*/
        if(db==null){
            Log.e("TAG","打开占用的数量："+mOpenCounter);
            db=myDatabase.getReadableDatabase();
        }
        return db;
    }

    public synchronized void closeDatabase(){
        /*if(mOpenCounter.decrementAndGet()==0){
            Log.e("TAG","删除后的数量："+mOpenCounter);
            db.close();
        }*/
    }
}