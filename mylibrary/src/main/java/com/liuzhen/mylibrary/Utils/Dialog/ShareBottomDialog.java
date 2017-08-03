package com.liuzhen.mylibrary.Utils.Dialog;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.liuzhen.mylibrary.R;
import com.liuzhen.mylibrary.Utils.Dialog.animation.FlipVerticalSwingEnter;
import com.liuzhen.mylibrary.Utils.Dialog.base.BottomBaseDialog;
import com.liuzhen.mylibrary.Utils.Dialog.utils.ViewFindUtils;


public class ShareBottomDialog extends BottomBaseDialog<ShareBottomDialog> {
    private LinearLayout ll_wechat_friend_circle;
    private View cancle;


    public ShareBottomDialog(Context context, View animateView) {
        super(context, animateView);
    }

    public ShareBottomDialog(Context context) {
        super(context);
    }

    @Override
    public View onCreateView() {
        showAnim(new FlipVerticalSwingEnter());
        dismissAnim(null);
        View inflate = View.inflate(context, R.layout.dialog_share, null);
        ll_wechat_friend_circle = ViewFindUtils.find(inflate, R.id.ll_wechat_friend_circle);
        cancle = ViewFindUtils.find(inflate, R.id.cancle);
        return inflate;
    }

    @Override
    public void setUiBeforShow() {

        ll_wechat_friend_circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onclick != null) {
                    onclick.OnClick();
                }
                dismiss();

            }
        });
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private OnClick onclick;

    public interface OnClick {
        void OnClick();
    }

    public void OnClick(OnClick onclick) {
        this.onclick = onclick;
    }

}
