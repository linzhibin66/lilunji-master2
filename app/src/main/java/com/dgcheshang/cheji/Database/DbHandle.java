package com.dgcheshang.cheji.Database;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.dgcheshang.cheji.Bean.database.StudentBean;
import com.dgcheshang.cheji.CjApplication;
import com.dgcheshang.cheji.netty.conf.NettyConf;
import com.dgcheshang.cheji.netty.po.Tdata;
import com.dgcheshang.cheji.netty.po.Zpdata;
import com.dgcheshang.cheji.netty.po.Zpsc;
import com.dgcheshang.cheji.netty.serverreply.SfrzR;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * SQLite业务处理
 */

public class DbHandle {
    private static DbManager dbManager= DbManager.getInstance(CjApplication.getInstance());

    /**
     * 增加学员信息
     * */
    public static void insertStuData(StudentBean studentBean){
        try {
            ContentValues cv = new ContentValues();
            cv.put("tybh", studentBean.getTybh());
            cv.put("sfzh", studentBean.getSfzh());
            cv.put("xm", studentBean.getXm());
            cv.put("sj", ZdUtil.getLongTime());
            cv.put("cx", studentBean.getCx());
            cv.put("ktid", NettyConf.ktid);
            cv.put("pxkc", NettyConf.pxkc);
            long l = dbManager.getReadableDatabase().insert(DbConstants.T_STU_LOGIN, null, cv);
            Log.e("TAG", "学员登录后信息存储" + l);
        }catch(Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 查学员信息
     * */
    public static ArrayList<StudentBean> stuQuery(){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.query(DbConstants.T_STU_LOGIN, null, null, null, null, null, null);
            ArrayList<StudentBean> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    StudentBean studentBean = new StudentBean();
                    studentBean.setTybh(cursor.getString(1));
                    studentBean.setSfzh(cursor.getString(2));
                    studentBean.setCx(cursor.getString(3));
                    studentBean.setXm(cursor.getString(4));
                    studentBean.setPxkc(cursor.getString(5));
                    studentBean.setKtid(cursor.getString(6));
                    studentBean.setSj(cursor.getString(7));
                    list.add(studentBean);

                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch(Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 查询是否已登录（或未登录）学员
     * */
    public static ArrayList<StudentBean> queryStuxx(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            ArrayList<StudentBean> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    StudentBean studentbean = new StudentBean();
                    studentbean.setTybh(cursor.getString(1));
                    studentbean.setSfzh(cursor.getString(2));
                    studentbean.setCx(cursor.getString(3));
                    studentbean.setXm(cursor.getString(4));
                    studentbean.setPxkc(cursor.getString(5));
                    studentbean.setKtid(cursor.getString(6));
                    studentbean.setSj(cursor.getString(7));
                    list.add(studentbean);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch(Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }

    }
    /**
     * 查询是否已登录（或未登录）学员
     * */
    public static long getNum(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            cursor.moveToFirst();
            Long count = cursor.getLong(0);
            cursor.close();
            return count;
        }catch(Exception e){
            return 0l;
        }finally {
            dbManager.closeDatabase();
        }
    }
    /**
     * 增
     * */
    public static  void insertZpsc(Zpsc zpsc){
        try {
            ContentValues cv=new ContentValues();
            cv.put("zpbh",zpsc.getZpbh());
            cv.put("bh",zpsc.getBh());
            cv.put("tdh",zpsc.getTdh());
            cv.put("tpcc",zpsc.getTpcc());
            cv.put("sjlx",zpsc.getSjlx());
            cv.put("zbs",zpsc.getZbs());
            cv.put("sjcd",zpsc.getZpsjcd());
            cv.put("ktid",zpsc.getKtid());
            cv.put("rlsb",zpsc.getRlsb());
            cv.put("sj", ZdUtil.getLongTime());
            long l=dbManager.getReadableDatabase().insert(DbConstants.T_ZPSC,null,cv);
            Log.e("TAG","照片上传存储结果"+l);
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 增
     * */
    public static  void insertZpdata(Zpdata zpdata){
        try {
            ContentValues cv=new ContentValues();
            cv.put("zpbh",zpdata.getZpbh());
            cv.put("zpsj",zpdata.getZpsj());
            cv.put("sj", ZdUtil.getLongTime());
            long l=dbManager.getReadableDatabase().insert(DbConstants.T_ZPDATA,null,cv);
        Log.e("TAG","照片数据存储结果"+l);
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 增
     * */
    public static void insertTdata(Tdata tdata){
        try{
            ContentValues cv=new ContentValues();
            cv.put("key",tdata.getKey());
            cv.put("parentid",tdata.getParentid());
            cv.put("data",tdata.getData());
            if(tdata.getInitsj()==null){
                cv.put("initsj", ZdUtil.getLongTime());
            }else{
                cv.put("initsj",tdata.getInitsj());
            }
            cv.put("sj", ZdUtil.getLongTime());
            long l=dbManager.getReadableDatabase().insert(DbConstants.T_DATA,null,cv);
            Log.e("TAG","缓存存储结果"+l);
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 增
     * */
    public static void insertTdatas(List<Tdata> tdataList){
        try {
            for (Tdata tdata : tdataList) {
                ContentValues cv = new ContentValues();
                cv.put("key", tdata.getKey());
                cv.put("parentid", tdata.getParentid());
                cv.put("data", tdata.getData());
                if (tdata.getInitsj() == null) {
                    cv.put("initsj", ZdUtil.getLongTime());
                } else {
                    cv.put("initsj", tdata.getInitsj());
                }
                cv.put("sj", ZdUtil.getLongTime());
                long l = dbManager.getReadableDatabase().insert(DbConstants.T_DATA, null, cv);
                Log.e("TAG", "缓存存储结果" + l);
            }
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 增
     * */
    public static void insertTdatas(List<Tdata> tdataList, int level){
        try {
            for (Tdata tdata : tdataList) {
                ContentValues cv = new ContentValues();
                cv.put("key", tdata.getKey());
                cv.put("parentid", tdata.getParentid());
                cv.put("data", tdata.getData());
                cv.put("level", level);
                if (tdata.getInitsj() == null) {
                    cv.put("initsj", ZdUtil.getLongTime());
                } else {
                    cv.put("initsj", tdata.getInitsj());
                }
                cv.put("sj", ZdUtil.getLongTime());
                long l = dbManager.getReadableDatabase().insert(DbConstants.T_DATA, null, cv);
                Log.e("TAG", "缓存存储结果" + l);
            }
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 增
     * */
    public static void insertTdataf(Tdata tdata){
        try {
            ContentValues cv = new ContentValues();
            cv.put("key", tdata.getKey());
            cv.put("parentid", tdata.getParentid());
            cv.put("data", tdata.getData());
            if (tdata.getInitsj() == null) {
                cv.put("initsj", ZdUtil.getLongTime());
            } else {
                cv.put("initsj", tdata.getInitsj());
            }
            cv.put("sj", ZdUtil.getLongTime());
            long l = dbManager.getReadableDatabase().insert(DbConstants.T_DATAF, null, cv);
            Log.e("TAG", "缓存存储结果" + l);
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    public static void insertTsfrz(SfrzR sfrzR){
        try {
            String[] params = {sfrzR.getUuid(), String.valueOf(sfrzR.getLx())};
            int num = deleteData(DbConstants.T_SFRZ, "uuid=? and lx=?", params);
            if (NettyConf.debug) {
                Log.e("TAG", "身份认证删除数量：" + num);
            }
            ContentValues cv = new ContentValues();
            cv.put("uuid", sfrzR.getUuid());
            cv.put("lx", String.valueOf(sfrzR.getLx()));
            cv.put("tybh", sfrzR.getTybh());
            cv.put("sfzh", sfrzR.getSfzh());
            cv.put("cx", sfrzR.getCx());
            cv.put("xx", sfrzR.getXx());
            cv.put("xm", sfrzR.getXm());
            cv.put("sj", ZdUtil.getLongTime());
            long l = dbManager.getReadableDatabase().insert(DbConstants.T_SFRZ, null, cv);
            if(NettyConf.debug) {
                Log.e("TAG", "身份认证存储结果" + l);
            }
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 查询照片上传
     * @param sql
     * @param params
     * @return
     */
    public static ArrayList<Zpsc> queryZpsc(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            ArrayList<Zpsc> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Zpsc zpsc = new Zpsc();
                    zpsc.setZpbh(cursor.getString(1));
                    zpsc.setBh(cursor.getString(2));
                    zpsc.setTdh(String.valueOf(cursor.getShort(3)));
                    zpsc.setTpcc(String.valueOf(cursor.getShort(4)));
                    zpsc.setSjlx(String.valueOf(cursor.getShort(5)));
                    zpsc.setZbs(String.valueOf(cursor.getShort(6)));
                    zpsc.setZpsjcd(String.valueOf(cursor.getInt(7)));
                    zpsc.setKtid(String.valueOf(cursor.getInt(8)));
                    zpsc.setRlsb(String.valueOf(cursor.getShort(9)));
                    list.add(zpsc);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 查询照片数据
     * @param sql
     * @param params
     * @return
     */
    public static ArrayList<Zpdata> queryZpdata(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            ArrayList<Zpdata> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Zpdata z = new Zpdata();
                    z.setZpbh(cursor.getString(1));
                    z.setZpsj(cursor.getBlob(2));
                    list.add(z);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 查询缓存数据
     * @param sql
     * @param params
     * @return
     */
    public static ArrayList<Tdata> queryTdata(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            ArrayList<Tdata> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    Tdata tdata = new Tdata();
                    tdata.setKey(cursor.getString(1));
                    tdata.setParentid(cursor.getString(2));
                    tdata.setData(cursor.getString(3));
                    tdata.setInitsj(cursor.getLong(4));
                    list.add(tdata);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }
    }

    public static ArrayList<SfrzR> queryTsfrz(String sql, String[] params){
        try {
            SQLiteDatabase readableDatabase = dbManager.getReadableDatabase();
            Cursor cursor = readableDatabase.rawQuery(sql, params);
            ArrayList<SfrzR> list = new ArrayList<>();
            if (cursor.moveToFirst()) {
                do {
                    SfrzR sr = new SfrzR();
                    sr.setUuid(cursor.getString(1));
                    sr.setLx(Byte.valueOf(cursor.getString(2)));
                    sr.setTybh(cursor.getString(3));
                    sr.setSfzh(cursor.getString(4));
                    sr.setCx(cursor.getString(5));
                    sr.setXx(cursor.getString(6));
                    sr.setXm(cursor.getString(7));
                    sr.setSj(cursor.getString(8));
                    list.add(sr);
                } while (cursor.moveToNext());
            }
            cursor.close();

            return list;
        }catch (Exception e){
            if(NettyConf.debug){
                Log.e("TAG",e.getMessage());
            }
            return null;
        }finally {
            dbManager.closeDatabase();
        }
    }

    /**
     * 删除数据
     * @param params
     * @return
     */
    public static int deleteData(String table ,String condition,String[] params){
        try {
            int num = dbManager.getReadableDatabase().delete(table, condition, params);
            return num;
        }catch(Exception e){
            Log.e("TAG",e.getMessage());
            return 0;
        }finally {
            dbManager.closeDatabase();
        }
    }

}
