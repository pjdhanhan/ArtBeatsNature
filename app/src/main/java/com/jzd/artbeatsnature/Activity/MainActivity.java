package com.jzd.artbeatsnature.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.jzd.artbeatsnature.Fragment.MainTainFragment;
import com.jzd.artbeatsnature.Fragment.UserFragment;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.BottomView.BottomItem;
import com.liuzhen.mylibrary.Utils.BottomView.MyBottomNavigation;
import com.liuzhen.mylibrary.Utils.FragmentContainer.FragmentContainer;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.liuzhen.mylibrary.Utils.ToastUtils.ToastUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends BaseActivity {

    private FragmentContainer fragmentcontainer;
    private MainTainFragment maintain;
    private UserFragment user;
    private MyBottomNavigation bottomnavigation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setThemeColor(R.color.transparent);
        setContentView(R.layout.activity_main);
        intView();
    }

    private void intView() {
        IntFragmentContainer();
        setBottom();
    }

    private void IntFragmentContainer() {
        fragmentcontainer = (FragmentContainer) findViewById(R.id.fragmentcontainer);//初始化控件
        fragmentcontainer.setOffscreenPageLimit(4);
        List<Fragment> list = new ArrayList<>();
        maintain = new MainTainFragment();
        user = new UserFragment();
        list.add(maintain);
        list.add(user);
        fragmentcontainer.setLayoutView(list);  //赋值
        fragmentcontainer.setCurrentItem(0);//设置选中项

    }


    private void setBottom() {
        bottomnavigation = (MyBottomNavigation) findViewById(R.id.bottomnavigation); //初始化控件
        //设置 被选中时文字颜色
        bottomnavigation.setItemTextColor(R.color.yellow, R.color.white);
        List<BottomItem> bottomItems = new ArrayList<>();
        bottomItems.add(new BottomItem(R.mipmap.main01, R.mipmap.main02, getResources().getString(R.string.maintain))); //添加 Item 文字 选择及未选择时图片
        bottomItems.add(new BottomItem(R.mipmap.main03, R.mipmap.main04, getResources().getString(R.string.userinfor)));
        bottomnavigation.setItems(bottomItems);
        // 选择及未选择时背景图片
        //设置选中位置
        bottomnavigation.setCurrentItem(0);
        bottomnavigation.OnBottomClick(new MyBottomNavigation.OnBottomClick() {
            @Override
            public void OnBottomClick(int position) {
                fragmentcontainer.setCurrentItem(position);//设置选中项
                maintain.getVisibility();
            }
        });
    }

    @Override
    public <T> void analysis(T result, int flag) {
        LogUtil.logWrite("analysis", "analysis" + flag);

    }

    public void GotoLogin() {
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private long preQuitTime, currentQuitTime;
    private boolean isExit = false;
    private boolean isFristExit = true;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == event.KEYCODE_BACK) {
            if (maintain.getVisibility()) {
                return false;
            }
            currentQuitTime = new Date().getTime();
            if (currentQuitTime - preQuitTime < 3000) {
                if (isFristExit) {
                    isExit = true;
                    isFristExit = false;
                } else {
                    finish();
                }
            } else {
                preQuitTime = currentQuitTime;
                ToastUtils.disPlayShortCenter(this, "再次点击退出系统");

            }
        }
        return false;
    }

}
