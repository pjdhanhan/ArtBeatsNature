package com.liuzhen.mylibrary.Utils.BottomView;

import android.support.annotation.DrawableRes;

/**
 * Created by Wxd on 2017-04-11.
 */
public class BottomItem {
    private int SelectedImage;
  
    private int UnSelectedImage;
    private String title;

    public BottomItem(@DrawableRes int SelectedImage, @DrawableRes int UnSelectedImage, String title) {
        this.SelectedImage = SelectedImage;
        this.UnSelectedImage = UnSelectedImage;
        this.title = title;
    }

    public int getSelectedImage() {
        return SelectedImage;
    }

    public void setSelectedImage(int SelectedImage) {
        this.SelectedImage = SelectedImage;
    }

    public int getUnSelectedImage() {
        return UnSelectedImage;
    }

    public void setUnSelectedImage(int UnSelectedImage) {
        this.UnSelectedImage = UnSelectedImage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
