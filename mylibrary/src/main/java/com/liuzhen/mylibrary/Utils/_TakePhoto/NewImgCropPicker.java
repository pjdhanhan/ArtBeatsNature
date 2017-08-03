package com.liuzhen.mylibrary.Utils._TakePhoto;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.liuzhen.mylibrary.R;

/**
 * 作者：Created by Administrator on 2016/7/12.
 */
public class NewImgCropPicker extends PopupWindow implements View.OnClickListener {
    private Context context;
    private View parentView;
    private View root;
    private TextView btn_take_photo, btn_pick_photo, btn_cancel;
    private View ll_parent;
    public static final String TAKE_PHOTO = "TAKE_PHOTO";
    public static final String FROM_ALBUM = "FROM_ALBUM";

    public NewImgCropPicker(Context context, View parentView, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        this.parentView = parentView;
        setTouchable(true);
        setOutsideTouchable(true);
        root = LayoutInflater.from(context).inflate(R.layout.img_picker_layout, null);
        setUpViews();
        setContentView(root);
        initLayout();
        ColorDrawable dw = new ColorDrawable(0x7DC0C0C0);
        setBackgroundDrawable(dw);
        setClippingEnabled(true);

    }

    // 弹出动画、高度等设置
    private void initLayout() {
        setHeight(LinearLayout.LayoutParams.MATCH_PARENT);
        setWidth(LinearLayout.LayoutParams.MATCH_PARENT);
    }


    public void show() {
        showAtLocation(parentView, Gravity.CENTER | Gravity.CENTER_HORIZONTAL,
                0, 0);
    }

    private void setUpViews() {

        ll_parent = root.findViewById(R.id.ll_parent);
        btn_take_photo = (TextView) root.findViewById(R.id.tv_takePhoto);
        btn_pick_photo = (TextView) root.findViewById(R.id.tv_fromAlbum);
        btn_cancel = (TextView) root.findViewById(R.id.tv_cancle);
        // 添加onclick事件
        btn_take_photo.setOnClickListener(this);
        btn_pick_photo.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        ll_parent.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_takePhoto) {
            if (choose != null) {
                choose.Choose(TAKE_PHOTO);
            }
            dismiss();

        } else if (i == R.id.tv_fromAlbum) {
            if (choose != null) {
                choose.Choose(FROM_ALBUM);
            }
            dismiss();

        } else if (i == R.id.tv_cancle) {
            dismiss();

            dismiss();

        } else if (i == R.id.ll_parent) {
            dismiss();

        } else {
            dismiss();

        }
    }

    private OnChooseListen choose;

    public interface OnChooseListen {
        void Choose(String type);
    }

    public void SetOnChoose(OnChooseListen choose) {
        this.choose = choose;
    }

}
