package com.dgcheshang.cheji.Bean.database;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/5/4 0004.
 */
public class PicBean implements Serializable {
    private String pic_name;
    private String pic_content;

    public String getPic_name() {
        return pic_name;
    }

    public void setPic_name(String pic_name) {
        this.pic_name = pic_name;
    }

    public String getPic_content() {
        return pic_content;
    }

    public void setPic_content(String pic_content) {
        this.pic_content = pic_content;
    }

    @Override
    public String toString() {
        return "PicBean{" +
                "pic_name='" + pic_name + '\'' +
                ", pic_content='" + pic_content + '\'' +
                '}';
    }
}
