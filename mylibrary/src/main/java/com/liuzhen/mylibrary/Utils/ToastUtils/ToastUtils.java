package com.liuzhen.mylibrary.Utils.ToastUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.liuzhen.mylibrary.R;


/**
 * Created by hjm on 2016/1/19.
 */
public class ToastUtils {

    private static Toast toast;


    /**
     * @param context context
     * @param content 信息内容
     * @param
     */
    //黑色透明的文本 无对号与叉号
    public static void disPlayShortCenterWithImage(Context context, String content) {
        toast = new Toast(context);

        //自定义布局
        View view = View.inflate(context, R.layout.toast, null);
        view.getBackground().setAlpha(150);
//        ImageView iv_check = (ImageView) view.findViewById(R.id.iv_check);
//        if (!flag){
//            iv_check.setImageResource(R.drawable.wd_error_flag);
//        }else {
//            iv_check.setImageResource(R.drawable.wd_pet_toast);
//        }
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText(content);

        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    //黑色透明的文本  true为对钩  false为叉号
    public static void disPlayShortCenterWithImage1(Context context, String content, boolean flag) {
        toast = new Toast(context);

        //自定义布局
        View view = View.inflate(context, R.layout.toast1, null);
        view.getBackground().setAlpha(150);
        ImageView iv_check = (ImageView) view.findViewById(R.id.iv_check);
        if (!flag) {
            iv_check.setImageResource(R.mipmap.toast_error);
        } else {
            iv_check.setImageResource(R.mipmap.toast_hook);
        }
        TextView textView = (TextView) view.findViewById(R.id.tv_content);
        textView.setText(content);

        toast.setView(view);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }


    public static void disPlayShort(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }

    public static void disPlayLong(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_LONG).show();
    }

    public static void disPlayShortCenter(Context context, String content) {
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void disPlayLongCenter(Context context, String content) {
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public static void disPlayShort(Context context, String content, int imgId) {
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(imgId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void disPlayShortCenter(Context context, String content, int imgId) {
        toast = Toast.makeText(context, content, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(imgId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void disPlayLong(Context context, String content, int imgId) {
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(imgId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void disPlayLongCenter(Context context, String content, int imgId) {
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageResource(imgId);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void disPlayLongCenter(Context context, String content, Bitmap bitmap) {
        toast = Toast.makeText(context, content, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout toastView = (LinearLayout) toast.getView();
        ImageView imageCodeProject = new ImageView(context);
        imageCodeProject.setImageBitmap(bitmap);
        toastView.addView(imageCodeProject, 0);
        toast.show();
    }

    public static void disPlayShort(Context context, View view) {
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setView(view);
        toast.show();
    }

    public static void disPlayShortCenter(Context context, View view) {
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void disPlayLong(Context context, View view) {
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public static void disPlayLongCenter(Context context, View view) {
        toast = new Toast(context);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setView(view);
        toast.show();
    }

    public static void dissmis() {
        if (toast != null) {
            toast.cancel();
        }
    }
}
