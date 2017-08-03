package com.liuzhen.mylibrary.Utils.NoScrollView;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by Wxd on 2017-06-13.
 */
public class NoScrollListView extends ListView {
    public NoScrollListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int mExpandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, mExpandSpec);
    }
}
