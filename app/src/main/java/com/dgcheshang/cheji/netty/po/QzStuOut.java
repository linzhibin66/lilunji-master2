package com.dgcheshang.cheji.netty.po;

import com.dgcheshang.cheji.netty.util.ByteUtil;

import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */

public class QzStuOut implements java.io.Serializable{
    private static final long serialVersionUID = 1L;
    private String ktid;
    private String dcsj;
    private List<String> slist;//编号集合

    public String getKtid() {
        return ktid;
    }

    public void setKtid(String ktid) {
        this.ktid = ktid;
    }

    public String getDcsj() {
        return dcsj;
    }

    public void setDcsj(String dcsj) {
        this.dcsj = dcsj;
    }

    public List<String> getSlist() {
        return slist;
    }

    public void setSlist(List<String> slist) {
        this.slist = slist;
    }

    public byte[] getQzStuOutbytes(){
        byte[] b=new byte[0];

        byte[] temp=ByteUtil.intToByteArray(Integer.valueOf(ktid));
        b=ByteUtil.byteMerger(b, temp);

        temp= ByteUtil.str2Bcd(dcsj);
        b=ByteUtil.byteMerger(b, temp);

        for(String s:slist){
            temp=s.getBytes();
            b=ByteUtil.byteMerger(b, temp);
        }
        return b;
    }
}
