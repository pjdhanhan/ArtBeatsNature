package com.liuzhen.mylibrary.Utils.NetWorkUtils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by admin on 2016/1/19.
 */
public class NetWorkUtils {
    // 判断网络是否已连接
    public static boolean isNetworkConnect(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isAvailable()) {

            return info.isConnected();
        }

        return false;
    }

    // 判断有没有连接WiFi网络
    public static boolean isWifiConnect(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        return info.isConnected();
    }

    // 判断有没有连接Mobile网络
    public static boolean isMobileConnect(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

        return info.isConnected();
    }

    // 判断当前连接的网络类型
    public static String getNetworkType(Context context) {

        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo info = manager.getActiveNetworkInfo();

        if (info != null && info.isAvailable()) {

            return info.getTypeName();
        }

        return "网络不可用";
    }
}
