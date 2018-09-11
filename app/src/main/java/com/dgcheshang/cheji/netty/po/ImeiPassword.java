package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

/**
 * Created by Administrator on 2017/7/11.
 */

public class ImeiPassword implements java.io.Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String imei;
    private int type;
    private String password;

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public byte[] getImeiPasswordBytes(){
        byte[] bs=new byte[0];
        byte[] temp=imei.getBytes();
        bs= ByteUtil.byteMerger(bs,temp);

        temp=new byte[1];
        temp[0]=(byte)type;
        bs= ByteUtil.byteMerger(bs,temp);

        temp=password.getBytes();
        bs= ByteUtil.byteMerger(bs,temp);

        return bs;
    }
}
