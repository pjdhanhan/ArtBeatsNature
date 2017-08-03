package com.liuzhen.mylibrary.Utils.SmartScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

import java.util.Date;

/**
 * Created by Wxd on 2017-07-21.
 */

public class SmartScrollView extends ScrollView {
    private boolean isScrolledToTop = true;// 初始化的时候设置一下值
    private boolean isScrolledToBottom = false;

    public SmartScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private ISmartScrollChangedListener mSmartScrollChangedListener;

    /**
     * 定义监听接口
     */
    public interface ISmartScrollChangedListener {
        void onScrolledToBottom();

        void onScrolledToTop();
    }

    public void setScanScrollChangedListener(ISmartScrollChangedListener smartScrollChangedListener) {
        mSmartScrollChangedListener = smartScrollChangedListener;
    }


    private void notifyScrollChangedListeners() {
        if (isScrolledToTop) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToTop();
            }
        } else if (isScrolledToBottom) {
            if (mSmartScrollChangedListener != null) {
                mSmartScrollChangedListener.onScrolledToBottom();
            }
        }
    }

    @Override
    protected void onOverScrolled(int scrollX, int scrollY, boolean clampedX, boolean clampedY) {
        super.onOverScrolled(scrollX, scrollY, clampedX, clampedY);
        if (scrollY == 0) {
            isScrolledToTop = clampedY;
            isScrolledToBottom = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = clampedY;
        }
        notifyScrollChangedListeners();
    }

    private long preQuitTime, currentQuitTime;

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        // 这个log可以研究ScrollView的上下padding对结果的影响
        if (getScrollY() == 0) {
            isScrolledToTop = true;
            isScrolledToBottom = false;
            if (isScrolledToTop) {
                currentQuitTime = new Date().getTime();
                if (currentQuitTime - preQuitTime < 500) {

                } else {
                    preQuitTime = currentQuitTime;
                    if (scrolledtotop!=null){
                        scrolledtotop.ScrolledToTop(isScrolledToTop);
                    }
                }
            }

        } else if (getScrollY() + getHeight() - getPaddingTop() - getPaddingBottom() == getChildAt(0).getHeight()) {
            isScrolledToBottom = true;
            if (isScrolledToBottom) {
                currentQuitTime = new Date().getTime();
                if (currentQuitTime - preQuitTime < 500) {
                } else {
                    preQuitTime = currentQuitTime;
                    if (scrolledtobottom!=null){
                        scrolledtobottom.ScrolledToBottom(isScrolledToBottom);
                    }
                }
            }
            isScrolledToTop = false;
        } else {
            isScrolledToTop = false;
            isScrolledToBottom = false;
        }
        notifyScrollChangedListeners();
    }

    private ScrolledToBottom scrolledtobottom;

    public interface ScrolledToBottom {
        void ScrolledToBottom(boolean position);
    }

    public void ScrolledToBottom(ScrolledToBottom scrolledtobottom) {
        this.scrolledtobottom = scrolledtobottom;
    }
    private ScrolledToTop scrolledtotop;

    public interface ScrolledToTop {
        void ScrolledToTop(boolean position);
    }

    public void ScrolledToTop(ScrolledToTop scrolledtotop) {
        this.scrolledtotop = scrolledtotop;
    }


}
