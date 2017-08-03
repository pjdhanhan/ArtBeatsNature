package com.jzd.artbeatsnature.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Bundle;
import android.view.WindowManager;

import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.SqlUtils.SqlHelper;

public class FirstActivity extends BaseActivity {

    private SqlHelper dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.transparent);
        setContentView(R.layout.activity_first);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        MyApp.getInstance().setPhoneheight(wm.getDefaultDisplay().getHeight());
        MyApp.getInstance().setPhonewidth(wm.getDefaultDisplay().getWidth());
        dao = new SqlHelper(this, MyApp.getInstance().getSqlCode(), "UserInfor");
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (dao.SelectUserBean() == 0) {
                    startActivity(new Intent(FirstActivity.this, LoginActivity.class));
                } else {
                    startActivity(new Intent(FirstActivity.this, MainActivity.class));
                }
                finish();
            }
        }, 2000);
    }
}
