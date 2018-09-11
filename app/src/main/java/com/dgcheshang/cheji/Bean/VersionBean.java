package com.dgcheshang.cheji.Bean;

import java.io.Serializable;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class VersionBean implements Serializable {

    private String version ;//版本号
    private String url;//路径
    private String imei;//指定imei号更新
    private String manageruid;//管理员卡号

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getManageruid() {
        return manageruid;
    }

    public void setManageruid(String manageruid) {
        this.manageruid = manageruid;
    }

    @Override
    public String toString() {
        return "VersionBean{" +
                "version='" + version + '\'' +
                ", url='" + url + '\'' +
                ", imei='" + imei + '\'' +
                ", manageruid='" + manageruid + '\'' +
                '}';
    }
}
