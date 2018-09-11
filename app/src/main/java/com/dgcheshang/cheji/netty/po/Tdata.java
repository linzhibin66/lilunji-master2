package com.dgcheshang.cheji.netty.po;

/**
 * Created by Administrator on 2017/6/22.
 */

public class Tdata implements java.io.Serializable{

    private static final long serialVersionUID = 1L;
    private String key;//关键值
    private String parentid;//对应的外键
    private String data;//数据
    private int level;//级别
    private Long initsj;//最初保存时间
    private Long sj;//最近一次的保存时间

    public Tdata() {
    }

    public Tdata(String key, String data) {
        this.key = key;
        this.data = data;
    }

    public Tdata(String key, String data, Long initsj) {
        this.key = key;
        this.data = data;
        this.initsj = initsj;
    }

    public Tdata(String key, String parentid, String data) {
        this.key = key;
        this.parentid = parentid;
        this.data = data;
    }

    public Tdata(String key, String data, int level, Long initsj) {
        this.key = key;
        this.data = data;
        this.level = level;
        this.initsj = initsj;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public Long getInitsj() {
        return initsj;
    }

    public void setInitsj(Long initsj) {
        this.initsj = initsj;
    }

    public Long getSj() {
        return sj;
    }

    public void setSj(Long sj) {
        this.sj = sj;
    }
}
