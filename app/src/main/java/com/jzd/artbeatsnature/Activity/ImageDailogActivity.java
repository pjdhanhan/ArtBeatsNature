package com.jzd.artbeatsnature.Activity;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.jzd.artbeatsnature.Comment.Config;
import com.jzd.artbeatsnature.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ImageDailogActivity extends Activity {

    private ViewPager viewpager;
    private String[] Picture;
    private List<ImageView> images = new ArrayList<>();
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_dailog);
        if (getIntent().hasExtra("Picture")) {
            position = getIntent().getIntExtra("position", 0);
            Picture = getIntent().getStringArrayExtra("Picture");
            for (int i = 0; i < Picture.length; i++) {
                ImageView imageView = new ImageView(this);
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                Picasso.with(this).load(R.mipmap.logo).into(imageView);
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams
                        (ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                imageView.setLayoutParams(params);
                imageView.setPadding(10, 10, 10, 10);
                Picasso.with(this).load(Config.P_URL + Picture[i]).
                        placeholder(R.mipmap.logo).error(R.mipmap.logo).into(imageView);
                images.add(imageView);
            }
        }
        intView();
    }

    private void intView() {
        viewpager = (ViewPager) findViewById(R.id.viewpager);
        viewpager.setAdapter(pagerAdapter);
        viewpager.setCurrentItem(position);
    }

    PagerAdapter pagerAdapter = new PagerAdapter() {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return Picture.length;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {
            // TODO Auto-generated method stub
            container.removeView(images.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // TODO Auto-generated method stub
            container.addView(images.get(position));
            return images.get(position);
        }
    };

}
