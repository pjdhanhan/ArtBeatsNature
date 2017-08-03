package com.jzd.artbeatsnature.Activity;

import android.content.pm.ActivityInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.jzd.artbeatsnature.Applicition.MyApp;
import com.jzd.artbeatsnature.R;
import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.Utils.drawableview.DrawableView;
import com.liuzhen.mylibrary.Utils.drawableview.DrawableViewConfig;

public class HandSignActivity extends BaseActivity {

    private DrawableView paintView;
    public static Bitmap bmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//隐藏标题
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);//设置全屏
        setContentView(R.layout.activity_hand_sign);
        intView();
    }

    private void intView() {
        findViewsetlisten(R.id.tv_back);
        findViewsetlisten(R.id.repaint);
        findViewsetlisten(R.id.save);
        paintView = (DrawableView) findViewsetlisten(R.id.paintView);
        DrawableViewConfig config = new DrawableViewConfig();
        config.setStrokeColor(getResources().getColor(android.R.color.black));
        config.setStrokeWidth(20.0f);
        config.setMinZoom(1.0f);
        config.setMaxZoom(1.0f);
        config.setShowCanvasBounds(true);
        config.setCanvasHeight(MyApp.getInstance().getPhonewidth());
        config.setCanvasWidth((int) (MyApp.getInstance().getPhoneheight() - MyApp.getInstance().getPhoneheight() * 0.1));
        paintView.setConfig(config);
    }

    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_back:
                finish();
                break;
            case R.id.repaint:
                paintView.undo();
                break;
            case R.id.save:
                // 从DrawableView获得Bitmap
                bmp = paintView.obtainBitmap();
                setResult(10);
                finish();
                break;
        }
    }
}
