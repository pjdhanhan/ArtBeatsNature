package com.liuzhen.mylibrary.Utils.LogUtils;

import android.util.Log;


public class LogUtil {
    private static boolean switcher = true;// ＬＯＧ开关　True->Show False->Hide

    public static void logWrite(String tag, String message) {
        if (message == null)
            message = "null";
        if (switcher) {
            try {
                Log.i(tag, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}