package com.dgcheshang.cheji.netty.timer;

import com.dgcheshang.cheji.Tools.Speaking;
import com.dgcheshang.cheji.netty.util.ZdUtil;

import java.util.TimerTask;

/**
 * Created by Administrator on 2017/6/22 0022.
 */

public class NetTimer extends TimerTask {
    @Override
    public void run() {
        ZdUtil.issave=false;
        Speaking.in("超过十五分钟网络异常，接下来培训记录将会无效，请及时登出");
    }
}
