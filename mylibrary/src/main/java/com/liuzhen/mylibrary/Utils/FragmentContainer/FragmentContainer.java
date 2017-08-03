package com.liuzhen.mylibrary.Utils.FragmentContainer;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.liuzhen.mylibrary.BaseActivity;
import com.liuzhen.mylibrary.R;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Administrator on 2017/3/27.
 */

public class FragmentContainer extends FrameLayout {
    private Context context;
    private List<Fragment> fragments = new ArrayList<>();
    private NoScrollViewPager pager;

    private int pagelimit;

    public FragmentContainer(Context context) {
        super(context);
        this.context = context;
    }

    public FragmentContainer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

    }


//    public void setLayoutView(List<Fragment> list,PageAdapter adapter) {
//        this.adapter = adapter;
//        this.fragments = list;
//        intView(adapter);
//    }

    public void setLayoutView(List<Fragment> list) {
        this.fragments = list;
        intView();
    }

    private void intView() {
        pager = new NoScrollViewPager(context);
        pager.setOffscreenPageLimit(pagelimit);//预加载
        pager.setId(0);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        pager.setLayoutParams(params);
        pager.setId(R.id.parallax_pager);
        pager.setNoScroll(true);
        //绑定

        if (context instanceof BaseActivity) {
            BaseActivity actvivity = (BaseActivity) context;
            pager.setAdapter(new BootPageAdapter(actvivity.getSupportFragmentManager()));
        }
        addView(pager, params);

    }

    public void setOffscreenPageLimit(int pagelimit) {
        this.pagelimit = pagelimit;
    }

//    private void intView(DIYFragment.PageAdapter adapter) {
//        pager = new NoScrollViewPager(context);
//
//        pager.setOffscreenPageLimit(pagelimit);//预加载
//        pager.setId(0);
//        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        pager.setLayoutParams(params);
//        pager.setId(R.id.parallax_pager);
//        pager.setNoScroll(true);
//        //绑定
//        pager.setAdapter(adapter);
//
//        addView(pager, params);
//
//    }

    public void setCurrentItem(int position) {
        pager.setCurrentItem(position);
    }

    public int getCurrentItem() {
        return pager.getCurrentItem();
    }


    private class BootPageAdapter extends FragmentPagerAdapter {


        public BootPageAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragments.get(position);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }

    public interface ItemClick {
        void ItemClick(int position);
    }

    private ItemClick ItemClick;

    public void ItemClick(ItemClick bottomClick) {
        this.ItemClick = ItemClick;
    }

}
