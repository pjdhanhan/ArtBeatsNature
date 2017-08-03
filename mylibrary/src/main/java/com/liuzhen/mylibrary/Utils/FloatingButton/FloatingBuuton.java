package com.liuzhen.mylibrary.Utils.FloatingButton;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.DrawableRes;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.R;
import com.liuzhen.mylibrary.Utils.LogUtils.LogUtil;
import com.squareup.picasso.Picasso;

/**
 * Created by Wxd on 2017-06-10.
 */
public class FloatingBuuton {
    private static final int MOVE_LENGH = 300;

    private int screenHeight;
    private int screenWidth;
    /**
     * 被拖动的图
     */
    private ImageButton iv_drag;
    private SharedPreferences sp;
    private Context context;

    public FloatingBuuton(Context context) {
        this.context = context;
        this.screenHeight = ((BaseActivity) context).getWindowManager().getDefaultDisplay()
                .getHeight();

        this.screenWidth = ((BaseActivity) context).getWindowManager().getDefaultDisplay()
                .getWidth();

        LogUtil.logWrite("DefaultDisplay", "screenHeight:" + screenHeight + "||" + "screenWidth:" + screenWidth);
        ViewGroup decorView = (ViewGroup) ((BaseActivity) context).getWindow().getDecorView();
        LayoutInflater factory = LayoutInflater.from(context);
        View layout = factory.inflate(R.layout.draglayout, null);

        decorView.addView(layout);
        this.iv_drag = (ImageButton) layout.findViewById(R.id.imageview_drag);
        this.sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
    }

    public void setImageIcon(@DrawableRes int imageID) {
        Picasso.with(context).load(imageID).error(R.mipmap.ic_launcher).into(this.iv_drag);

    }

    /**
     * 显示可拖动的客服电话图标
     */
    public void showDragCallView() {
        this.iv_drag.setVisibility(View.VISIBLE);
        DisplayMetrics metric = new DisplayMetrics();
        ((BaseActivity) context).getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels; // 屏幕宽度（像素）
        int height = metric.heightPixels; // 屏幕高度（像素）
//        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
//        iv_drag.measure(w, h);
//        int viewheight = iv_drag.getMeasuredHeight();
//        int viewwidth = iv_drag.getMeasuredWidth();
        int lastx = this.sp.getInt("lastx", width - 200);
        int lasty = this.sp.getInt("lasty", height - 200);

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) this.iv_drag
                .getLayoutParams();

        params.leftMargin = lastx;
        params.topMargin = lasty;
        this.iv_drag.setLayoutParams(params);

        this.iv_drag.setOnTouchListener(new View.OnTouchListener() {
            int startX;
            int startY;
            long downTime;
            long upTime;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:// 手指第一次触摸到屏幕
                        //    iv_drag.setBackgroundResource(R.drawable.setting_cusromer_drag_down);
                        this.startX = (int) event.getRawX();
                        this.startY = (int) event.getRawY();
                        downTime = System.currentTimeMillis();
                        break;
                    case MotionEvent.ACTION_MOVE:// 手指移动
                        int newX = (int) event.getRawX();
                        int newY = (int) event.getRawY();

                        int dx = newX - this.startX;
                        int dy = newY - this.startY;
// 计算出来控件原来的位置
                        int l = iv_drag.getLeft();
                        int r = iv_drag.getRight();
                        int t = iv_drag.getTop();
                        int b = iv_drag.getBottom();
                        int newt = t + dy;
                        int newb = b + dy;
                        int newl = l + dx;
                        int newr = r + dx;
                        if ((newl < 0) || (newt < 0) || (newr > screenWidth)
                                || (newb > screenHeight)) {
                            break;
                        }

// 更新iv在屏幕的位置.
                        iv_drag.layout(newl, newt, newr, newb);
                        this.startX = (int) event.getRawX();
                        this.startY = (int) event.getRawY();

                        break;
                    case MotionEvent.ACTION_UP: // 手指离开屏幕的一瞬间

                        int lastx = iv_drag.getLeft();
                        int lasty = iv_drag.getTop();
                        upTime = System.currentTimeMillis();

                        if (Math.abs(lastx - startX) < MOVE_LENGH
                                && Math.abs(lasty - startY) < MOVE_LENGH
                                && (upTime - downTime) < 150l) {
                            if (OnItemClick != null) {
                                OnItemClick.OnItemClick();
                            }
                        }
                        SharedPreferences.Editor editor = sp.edit();
                        editor.putInt("lastx", lastx);
                        editor.putInt("lasty", lasty);
                        editor.commit();
                        //     iv_drag.setBackgroundResource(R.drawable.setting_cusromer_drag);
                        break;
                }
                return true;
            }
        });
    }

    /**
     *
     */
    public void hideDragCallView() {

        this.iv_drag.setVisibility(View.GONE);
    }

    /**
     * 打电话
     */
    private OnItemClick OnItemClick;

    public interface OnItemClick {
        void OnItemClick();
    }

    public void OnItemClick(OnItemClick OnItemClick) {
        this.OnItemClick = OnItemClick;
    }

}
