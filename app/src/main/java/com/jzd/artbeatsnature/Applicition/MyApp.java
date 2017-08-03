package com.jzd.artbeatsnature.Applicition;


import com.liuzhen.mylibrary.Base.LibApp;
import com.liuzhen.mylibrary.Utils.SqlUtils.SqlBean;

/**
 * Created by Wxd on 2017-07-12.
 */

public class MyApp extends LibApp {
    private int Phonewidth, Phoneheight;
    private static MyApp instance;
    private SqlBean bean;

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static MyApp getInstance() {
        if (instance == null) {
            instance = new MyApp();
        } else {
            return instance;
        }
        return instance;
    }

    public int getPhonewidth() {
        return Phonewidth;
    }

    public void setPhonewidth(int phonewidth) {
        Phonewidth = phonewidth;
    }

    public void setPhoneheight(int phoneheight) {
        Phoneheight = phoneheight;
    }

    public int getPhoneheight() {
        return Phoneheight;
    }


    public int getSqlCode() {
        return 1;
    }

}
