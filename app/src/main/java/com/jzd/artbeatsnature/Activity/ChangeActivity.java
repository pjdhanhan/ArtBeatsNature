package com.jzd.artbeatsnature.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.Comment.Contants;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;

import java.util.HashMap;
import java.util.Map;

public class ChangeActivity extends BaseActivity {

    private EditText oldpsd, newpsd, confirmpwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.mainColor);
        setContentView(R.layout.activity_change);
        intView();
    }

    private void intView() {
        findViewsetlisten(R.id.confirm);
        findViewsetlisten(R.id.tv_back);
        oldpsd = (EditText) findViewById(R.id.oldpsd);
        newpsd = (EditText) findViewById(R.id.newpsd);
        confirmpwd = (EditText) findViewById(R.id.confirmpwd);
    }

    @Override
    public void analysisErr(String e) {
        LogUtil.logWrite("analysisErr", e);
        if (e.equals("1")) {
            ToastUtils.disPlayShort(ChangeActivity.this, "密码不正确");
            return;
        } else {
            ToastUtils.disPlayShort(ChangeActivity.this, "修改失败");
        }
    }

    @Override
    public <T> void analysis(T result, int flag) {
        switch (flag) {
            case Contants.modifyPwd:
                LogUtil.logWrite("analysisErr", result.toString());
                if (result.toString().equals("1")) {
                    MyApp.getInstance().getUserbean().setUserPwd(newpsd.getText().toString());
                    setResult(10);
                    finish();
                } else {
                    ToastUtils.disPlayShort(ChangeActivity.this, "修改失败");
                }
                break;
        }
    }

    private void modifyPwd() {
        Map map = new HashMap();
        map.put("userAccount", MyApp.getInstance().getUserbean().getJobNumber());
        map.put("oldPwd", oldpsd.getText().toString());
        map.put("userPwd", newpsd.getText().toString());
        LogUtil.logWrite("map", map.toString());
        get(Config.modifyPwd, map, Contants.modifyPwd);

    }


    private boolean Checkpwd(EditText editText) {
        if (editText.getText().toString().equals("")) {
            ToastUtils.disPlayShort(ChangeActivity.this, editText.getHint().toString());
            return false;
        } else {
            return true;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.confirm:
                if (Checkpwd(oldpsd) && Checkpwd(newpsd)) {
                    if (newpsd.getText().toString().equals(confirmpwd.getText().toString())) {
                        modifyPwd();
                    } else {
                        ToastUtils.disPlayShort(ChangeActivity.this, getString(R.string.psd_confirm));
                    }
                }
                break;
            case R.id.tv_back:
                finish();
                break;

        }
    }
}
