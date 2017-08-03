package com.jzd.artbeatsnature.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.gson.Gson;
import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.SqlUtils.SqlBean;
import com.liuzhen.mylibrary.Utils.SqlUtils.SqlHelper;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends BaseActivity {


    private EditText userName, pasword;
    private CheckBox remenberpsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.transparent);
        setContentView(R.layout.activity_login);
        intView();
        getLoginInformation();
    }

    private void intView() {
        findViewsetlisten(R.id.login);
        userName = (EditText) findViewById(R.id.userName);
        pasword = (EditText) findViewById(R.id.pasword);
        remenberpsw = (CheckBox) findViewById(R.id.remenberpsw);
    }


    @Override
    public void analysisErr(String e) {
        ToastUtils.disPlayLongCenter(this, "账号或密码错误");

    }

    @Override
    public <T> void analysis(T result, int flag) {
        cancleProgressBar();
        switch (flag) {
            case Contants.CheckLogin:
                LogUtil.logWrite("result", result.toString());
                SqlBean bean = new Gson().fromJson((String) result, SqlBean.class);
                if (bean.getUserName() != null) {
                    SqlHelper dao = new SqlHelper(LoginActivity.this, MyApp.getInstance().getSqlCode(), "UserInfor");
                    switch (dao.SelectUserBean()) {
                        case 0:
                            dao.saveUserInfor(bean);
                            break;
                        default:
                            dao.delete();
                            dao.saveUserInfor(bean);
                    }
                    SavePWS();
                    MyApp.getInstance().setUserBean(bean);
                    startActivity(new Intent(this, MainActivity.class));
                    finish();
                } else {
                    ToastUtils.disPlayLongCenter(this, "账号或密码错误");
                }
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                if (isNotEmpty(userName) && isNotEmpty(pasword)) {
                    CheckLogin();
                }
                break;
        }
    }

    //登录
    private void CheckLogin() {
        showProgressBar();
        Map map = new HashMap();
        map.put("userName", userName.getText().toString());
        map.put("userPwd", pasword.getText().toString());
        get(Config.CheckLogin, map, Contants.CheckLogin);
    }


    /**
     * 保存登录账号 密码
     */
    private void SavePWS() {
        SharedPreferences mSharedPreferences = getSharedPreferences("saveLoginInformation", 0);
        SharedPreferences.Editor mEditor = mSharedPreferences.edit();
        mEditor.putString("userName", userName.getText().toString());
        if (remenberpsw.isChecked()) {
            mEditor.putString("userPwd", pasword.getText().toString());
            mEditor.putBoolean("isChecked", true);
        } else {
           // mEditor.putString("userName", "");
            mEditor.putString("userPwd", "");
            mEditor.putBoolean("isChecked", false);
        }
        mEditor.commit();
    }


    public void getLoginInformation() {
        //检查本地保存的账号密码
        SharedPreferences mSharedPreferences = getSharedPreferences("saveLoginInformation", 0);
        String lname = mSharedPreferences.getString("userName", "");
        String pwd = mSharedPreferences.getString("userPwd", "");
        boolean isChecked = mSharedPreferences.getBoolean("isChecked", true);
        remenberpsw.setChecked(isChecked);
        userName.setText(lname);
        if (isChecked) {
            pasword.setText(pwd);
        }else{
           // userName.setText("");
            pasword.setText("");
        }
    }

}
