package com.liuzhen.mylibrary.Utils.BottomView;

import android.content.Context;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.liuzhen.mylibrary.R;

import java.util.List;

/**
 * Created by Wxd on 2017-04-11.
 */
public class MyBottomNavigation extends LinearLayout {
    private int SelectedImage = -1, UnSelectedImage = -1;
    private int SelectedColor = -1, UnSelectedColor = -1;
    private List<BottomItem> Items;
    private Context context;

    public MyBottomNavigation(Context context) {
        super(context);
        this.context = context;
    }

    public MyBottomNavigation(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public MyBottomNavigation(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    //设置背景颜色
    public void setBackgroundItemResource(@DrawableRes int SelectedImage, @DrawableRes int UnSelectedImage) {
        this.SelectedImage = SelectedImage;
        this.UnSelectedImage = UnSelectedImage;
    }

    //设置文字颜色
    @ColorInt
    public void setItemTextColor(@ColorRes int SelectedColor, @ColorRes int UnSelectedColor) {
        this.SelectedColor = SelectedColor;
        this.UnSelectedColor = UnSelectedColor;
    }

    public void setItems(List<BottomItem> Items) {
        this.Items = Items;
        setOrientation(LinearLayout.HORIZONTAL);
        AddItems();
    }

    private void AddItems() {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        for (int i = 0; i < Items.size(); i++) {
            final View bottomView = inflater.inflate(R.layout.bottomnavagation, null);
            View item = bottomView.findViewById(R.id.item);
            item.setTag(i);
            ImageView Imageitem = (ImageView) bottomView.findViewById(R.id.image);
            TextView Textitem = (TextView) bottomView.findViewById(R.id.text);
            if (i == 0) {
                if (SelectedImage != -1) {
                    item.setBackgroundResource(SelectedImage);
                }
            } else {
                if (UnSelectedImage != -1) {
                    item.setBackgroundResource(UnSelectedImage);
                }
            }
            if (i == 0) {
                Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getSelectedImage()));
            } else {
                Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getUnSelectedImage()));
            }
            if (i == 0) {
                Textitem.setTextColor(getResources().getColor(SelectedColor));
            } else {
                Textitem.setTextColor(getResources().getColor(UnSelectedColor));
            }
            Textitem.setText(Items.get(i).getTitle());
            LayoutParams params = new LayoutParams(0, LayoutParams.WRAP_CONTENT, 1);
            item.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(final View v) {
                    for (int i = 0; i < getChildCount(); i++) {
                        ImageView Imageitem = (ImageView) getChildAt(i).findViewById(R.id.image);
                        TextView Textitem = (TextView) getChildAt(i).findViewById(R.id.text);
                        View item = getChildAt(i).findViewById(R.id.item);
                        if (Integer.parseInt(v.getTag().toString()) == i) {
                            if (SelectedImage != -1) {
                                item.setBackgroundResource(SelectedImage);
                            }
                            Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getSelectedImage()));
                            Textitem.setTextColor(getResources().getColor(SelectedColor));
                        } else {
                            if (UnSelectedImage != -1) {
                                item.setBackgroundResource(UnSelectedImage);
                            }

                            Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getUnSelectedImage()));
                            Textitem.setTextColor(getResources().getColor(UnSelectedColor));
                        }
                    }
                    if (bottomClick != null) {
                        bottomClick.OnBottomClick(Integer.parseInt(v.getTag().toString()));
                    }
                }
            });
            addView(item, params);
        }

    }

    //设置当前选择项
    public void setCurrentItem(int position) {
        for (int i = 0; i < getChildCount(); i++) {
            ImageView Imageitem = (ImageView) getChildAt(i).findViewById(R.id.image);
            TextView Textitem = (TextView) getChildAt(i).findViewById(R.id.text);
            View item = getChildAt(i).findViewById(R.id.item);
            if (position == i) {
                if (SelectedImage != -1) {
                    item.setBackgroundResource(SelectedImage);
                }
                Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getSelectedImage()));
                Textitem.setTextColor(getResources().getColor(SelectedColor));
            } else {
                if (UnSelectedImage != -1) {
                    item.setBackgroundResource(UnSelectedImage);
                }
                Imageitem.setImageDrawable(getResources().getDrawable(Items.get(i).getUnSelectedImage()));
                Textitem.setTextColor(getResources().getColor(UnSelectedColor));
            }
        }
    }

    public interface OnBottomClick {
        void OnBottomClick(int position);
    }

    private OnBottomClick bottomClick;

    public void OnBottomClick(OnBottomClick bottomClick) {
        this.bottomClick = bottomClick;
    }


}


