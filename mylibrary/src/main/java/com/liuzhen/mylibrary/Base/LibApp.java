package com.liuzhen.mylibrary.Base;

import android.app.Application;
import android.content.Context;

import com.liuzhen.mylibrary.Utils.SqlUtils.SqlBean;

/**
 * Created by Wxd on 2017-07-12.
 */

public class LibApp extends Application {
    public static Context context;
    public static SqlBean userbean;


    @Override
    public void onCreate() {
        super.onCreate();
        context = getApplicationContext();
    }

    public static Context getContext() {
        return context;
    }

    public static void setUserBean(SqlBean obj) {
        userbean = obj;
    }

    public static SqlBean getUserbean() {
        return userbean;
    }
}
