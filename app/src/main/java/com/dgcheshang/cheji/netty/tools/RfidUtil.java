package com.dgcheshang.cheji.netty.tools;

import android.util.Log;

import com.rscja.deviceapi.RFIDWithISO14443A;
import com.rscja.deviceapi.entity.SimpleRFIDEntity;
import com.rscja.deviceapi.exception.ConfigurationException;
import com.rscja.deviceapi.exception.RFIDReadFailureException;
import com.rscja.deviceapi.exception.RFIDVerificationException;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;

/**
 *读卡
 */

public class RfidUtil {

    /**
     * code 为0代表读卡成功，1为读卡失败 key形式为扇区+"_"+块号，value为id+"_"+type+"_"+data的形式
     * @return
     */
    public static Map<String,Object> getCardInfo(){
        Map<String,Object> map=new HashMap<>();
        RFIDWithISO14443A  mRFID=null;
        SimpleRFIDEntity entity = null;
        try {
            try {
                mRFID = RFIDWithISO14443A.getInstance();
                mRFID.init();
            } catch (ConfigurationException e) {
                e.printStackTrace();
            }

            entity = mRFID.request();
            if (entity == null) {
                // 读卡失败
                map.put("code", "1");

            } else {
                //读卡成功
                map.put("code", "0");
                map.put("Uid",entity.getType());
                RFIDWithISO14443A.KeyType nKeyType = RFIDWithISO14443A.KeyType.TypeB;
                String key="00FFFFFFFF00";
                boolean fg=mRFID.VerifySector(0, key, nKeyType);
                Log.e("TAG","结果:"+fg);


                /*String key = "666666666666";
                boolean fg=mRFID.VerifySector(0, key, nKeyType);
                Log.e("TAG","密码验证结果："+fg);

                for (int i = 0; i < 16; i++) {
                    int num = 0;
                    if (i == 0) {
                        num = 1;
                    }
                    for (int j = num; j < 3; j++) {
                        try {
                            entity = mRFID.read(key, nKeyType, i, j);
                            if (entity != null) {
                                String id = i + "_" + j;
                                String value = entity.getId() + "_" + entity.getType() + "_" + entity.getData();
                                map.put(id, value);
                            }
                        } catch (RFIDVerificationException e) {
                            e.printStackTrace();
                        } catch (RFIDReadFailureException e) {
                            e.printStackTrace();
                        }
                    }
                }*/
            }
        }finally {
            mRFID.free();
        }
        return map;
    }


}
