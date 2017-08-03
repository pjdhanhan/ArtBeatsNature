package com.liuzhen.mylibrary.Utils._TakePhoto.model;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.liuzhen.mylibrary.R;


/**
 * Created by Administrator on 2016/6/1.
 */
public class CustomProgress extends Dialog {
    public static CharSequence msg;

    public CustomProgress(Context context) {
        super(context);
    }

    public CustomProgress(Context context, int theme) {
        super(context, theme);
    }

    /**
     * 弹出自定义ProgressDialog
     *
     * @param context        上下文
     * @param message        提示
     * @param cancelable     是否按返回键取消
     * @param cancelListener 按下返回键监听
     * @return
     */
    public static CustomProgress show(Context context, CharSequence message, boolean cancelable, OnCancelListener cancelListener) {
        msg = message;

        CustomProgress dialog = new CustomProgress(context, R.style.Custom_Progress);
//        dialog.setTitle(msg);
        dialog.setContentView(R.layout.fragment_loading);
        // 按返回键是否取消
        dialog.setCancelable(cancelable);
        // 监听返回键处理
        dialog.setOnCancelListener(cancelListener);
        // 设置居中
        dialog.getWindow().getAttributes().gravity = Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        // 设置背景层透明度
        lp.dimAmount = 0.2f;
        dialog.getWindow().setAttributes(lp);
        // dialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        dialog.show();
        return dialog;
    }
}
