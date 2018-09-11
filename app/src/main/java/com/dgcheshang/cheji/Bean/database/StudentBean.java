package com.dgcheshang.cheji.Bean.database;

import java.io.Serializable;

/**
 *学员登录后信息
 */

public class StudentBean implements Serializable {
    private String tybh;//统一编号
    private String sfzh;//身份证号
    private String cx;//车型
    private String xm;//学员姓名
    private String pxkc;//培训课程
    private String ktid;//课堂id
    private String sj;

    public String getTybh() {
        return tybh;
    }

    public void setTybh(String tybh) {
        this.tybh = tybh;
    }

    public String getSfzh() {
        return sfzh;
    }

    public void setSfzh(String sfzh) {
        this.sfzh = sfzh;
    }

    public String getCx() {
        return cx;
    }

    public void setCx(String cx) {
        this.cx = cx;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getSj() {
        return sj;
    }

    public void setSj(String sj) {
        this.sj = sj;
    }

    public String getKtid() {
        return ktid;
    }

    public void setKtid(String ktid) {
        this.ktid = ktid;
    }

    public String getPxkc() {
        return pxkc;
    }
    public void setPxkc(String pxkc) {
        this.pxkc = pxkc;
    }

    @Override
    public String toString() {
        return "StudentBean{" +
                "tybh='" + tybh + '\'' +
                ", sfzh='" + sfzh + '\'' +
                ", cx='" + cx + '\'' +
                ", xm='" + xm + '\'' +
                ", sj='" + sj + '\'' +
                ", ktid='" + ktid + '\'' +
                '}';
    }
}
