package com.liuzhen.mylibrary.Utils.CarouselFigure;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.liuzhen.mylibrary.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by 刘振 on 2017/5/6.  轮播图
 */
public class CarouselFigure extends RelativeLayout {
    private Context context;
    private List<String> images;
    private List<Integer> Intimages;
    private ViewPager viewpager;
    private LinearLayout dots;
    private List<ImageView> mImageViews = new ArrayList<>();
    private Timer timer;
    private TimerTask task;
    public int conunt;
    public int endPosition = Integer.MAX_VALUE;
    public int TYPE;
    private boolean DotsGone = false;


    public CarouselFigure(Context context) {
        super(context);
        this.context = context;
    }

    public CarouselFigure(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public CarouselFigure(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void setImage(List<String> image) {
        this.images = image;
        TYPE = 1;
        conunt = image.size();
        while (images.size() <= 3) {
            images.addAll(image);
        }
        setViews();
    }

    @DrawableRes
    public void setBitImage(List<Integer> Intimages) {
        this.Intimages = Intimages;
        TYPE = 2;
        conunt = Intimages.size();
        while (Intimages.size() <= 3) {
            Intimages.addAll(Intimages);
        }
        setViews();
    }

    //赋值前调用
    public void setDotsGone(boolean DotsGone) {
        this.DotsGone = DotsGone;

    }

    private void setViews() {
        View v = LayoutInflater.from(context).inflate(R.layout.myviewpager, this);
        viewpager = (ViewPager) v.findViewById(R.id.viewpager);
        dots = (LinearLayout) v.findViewById(R.id.dots);
        if (DotsGone) {
            dots.setVisibility(GONE);
        } else {
            dots.setVisibility(VISIBLE);
        }
        dots.removeAllViews();
        mImageViews.clear();
        switch (TYPE) {
            case 1:
                for (int i = 0; i < images.size(); i++) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Picasso.with(context).load(images.get(i)).placeholder(R.mipmap.ic_launcher)
                            .into(imageView);
                    mImageViews.add(imageView);
                }
                break;
            case 2:
                for (int i = 0; i < Intimages.size(); i++) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    Picasso.with(context).load(Intimages.get(i)).placeholder(R.mipmap.ic_launcher).error(R.mipmap.ic_launcher)
                            .into(imageView);
                    mImageViews.add(imageView);
                }
                break;
        }


        for (int k = 0; k < conunt; k++) {
            ImageView dot = new ImageView(context);
            dot.setTag("UNSELECTED");
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.leftMargin = 25;
            dot.setLayoutParams(params);
            if (k == 0) {
                dot.setBackgroundResource(R.mipmap.dot02);
            } else {
                dot.setBackgroundResource(R.mipmap.dot01);
            }
            dot.setScaleType(ImageView.ScaleType.CENTER);
            dots.addView(dot);
        }

        MyPagerAdapter adpter = new MyPagerAdapter();
        viewpager.setAdapter(adpter);

        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            public int posi;

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                this.posi = position;
                if (pagerchange != null && position != 0) {
                    pagerchange.PagerChange(posi % conunt);
                }
                dots.getChildAt(position % conunt).setTag("SELECTED");
                for (int j = 0; j < mImageViews.size(); j++) {
                    mImageViews.get(j).setOnClickListener(new OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (imageClick != null) {
                                imageClick.onClick(posi % conunt);
                            }
                        }
                    });
                }
                for (int i = 0; i < dots.getChildCount(); i++) {
                    if (dots.getChildAt(i).getTag().equals("SELECTED")) {
                        dots.getChildAt(i).setTag("UNSELECTED");
                        dots.getChildAt(i).setBackgroundResource(R.mipmap.dot02);

                    } else {
                        dots.getChildAt(i).setBackgroundResource(R.mipmap.dot01);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    public void StopMove() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
    }

    public void startMove(long delay) {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        if (task != null) {
            task.cancel();
            task = null;
        }
        if (timer == null) {
            timer = new Timer();
        }

        if (task == null) {
            task = new TimerTask() {
                @Override
                public void run() {
// TODO Auto-generated method stub
                    Message message = Message.obtain();
                    message.what = 1;
                    handler.sendMessage(message);
                }

            };
        }
        if (timer != null && task != null) {
            timer.schedule(task, delay, delay);
        }
    }


    public class MyPagerAdapter extends PagerAdapter {

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {   //这个一个定要有，返回也就这样也。
            // TODO Auto-generated method stub
            return arg0 == arg1;
        }

        @Override
        public int getCount() {    //获得size
            // TODO Auto-generated method stub
            return Integer.MAX_VALUE;
        }

        @Override
        public void destroyItem(ViewGroup container, int position,
                                Object object) {          //销毁Item
            // TODO Auto-generated method stub
            ((ViewPager) container).removeView(mImageViews.get(position % mImageViews.size()));
            //super.destroyItem(container, position, object);
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {    //实例化Item
            // TODO Auto-generated method stub
            ((ViewPager) container).addView(mImageViews.get(position % mImageViews.size()));
            return mImageViews.get(position % mImageViews.size());
        }
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    if (viewpager.getCurrentItem() < endPosition - 1) {
                        viewpager.setCurrentItem(viewpager.getCurrentItem() + 1);
                    } else {
                        if (goposition != null) {
                            goposition.go();
                        }
                    }
                    break;

            }
        }


    };


    private OnImageClick imageClick;

    public interface OnImageClick {
        void onClick(int position);
    }

    public void setOnImageClick(OnImageClick imageClick) {
        this.imageClick = imageClick;
    }


    private OnPagerChange pagerchange;

    public interface OnPagerChange {
        void PagerChange(int position);
    }

    public void setOnPagerChange(OnPagerChange pagerchange) {
        this.pagerchange = pagerchange;
    }


    private GoPosition goposition;

    public interface GoPosition {
        void go();
    }

    public void setGoPosition(int endPosition, GoPosition goposition) {
        this.endPosition = endPosition;
        this.goposition = goposition;
    }


}
