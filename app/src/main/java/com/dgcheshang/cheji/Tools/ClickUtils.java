package com.dgcheshang.cheji.Tools;

/**
 *防止多次点击
 */
public class ClickUtils {

    private static long lastClickTime;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 2000) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
