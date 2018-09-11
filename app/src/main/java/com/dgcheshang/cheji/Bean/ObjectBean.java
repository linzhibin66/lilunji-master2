package com.dgcheshang.cheji.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/4/26 0026.
 */
public class ObjectBean implements Serializable{

    private String name1;
    private String number;


    public String getName1() {
        return name1;
    }

    public void setName1(String name1) {
        this.name1 = name1;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    @Override
    public String toString() {
        return "ObjectBean{" +
                "name1='" + name1 + '\'' +
                ", number='" + number + '\'' +
                '}';
    }
}
