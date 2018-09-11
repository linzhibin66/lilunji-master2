package com.dgcheshang.cheji.Bean.database;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/11 0011.
 */
public class RegistSuccessBean implements Serializable {

    private String host;
    private int port;
    private String register;
    private int lsh;
    private String ptbh;
    private String pxjgbh;
    private String jszdbh;
    private String zs;
    private String zskl;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getRegister() {
        return register;
    }

    public void setRegister(String register) {
        this.register = register;
    }

    public int getLsh() {
        return lsh;
    }

    public void setLsh(int lsh) {
        this.lsh = lsh;
    }

    public String getPtbh() {
        return ptbh;
    }

    public void setPtbh(String ptbh) {
        this.ptbh = ptbh;
    }

    public String getPxjgbh() {
        return pxjgbh;
    }

    public void setPxjgbh(String pxjgbh) {
        this.pxjgbh = pxjgbh;
    }

    public String getJszdbh() {
        return jszdbh;
    }

    public void setJszdbh(String jszdbh) {
        this.jszdbh = jszdbh;
    }

    public String getZs() {
        return zs;
    }

    public void setZs(String zs) {
        this.zs = zs;
    }

    public String getZskl() {
        return zskl;
    }

    public void setZskl(String zskl) {
        this.zskl = zskl;
    }

    @Override
    public String toString() {
        return "RegistSuccessBean{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", register='" + register + '\'' +
                ", lsh=" + lsh +
                ", ptbh='" + ptbh + '\'' +
                ", pxjgbh='" + pxjgbh + '\'' +
                ", jszdbh='" + jszdbh + '\'' +
                ", zs='" + zs + '\'' +
                ", zskl='" + zskl + '\'' +
                '}';
    }
}
