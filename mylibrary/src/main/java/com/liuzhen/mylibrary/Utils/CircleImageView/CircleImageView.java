package com.liuzhen.mylibrary.Utils.CircleImageView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.Xfermode;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.graphics.drawable.TransitionDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.liuzhen.mylibrary.R;


public class CircleImageView extends ImageView {
    public int mBorderThickness = 0;  //可以设置边框粗细
    private Context mContext;
    public int mBorderColor = 0xFFFFFFFF;  //边框颜色
    private int mAlpha = 255;
    private int mViewAlphaScale = 256;
    private Xfermode mXfermode;
    private boolean mHasColorFilter = false;
    private boolean mColorMod = false;
    private ColorFilter mColorFilter = null;

    public CircleImageView(Context context) {
        super(context);
        mContext = context;
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        setCustomAttributes(attrs);
    }

    public void applyColorFilterr(int color, Mode mode) {
        ColorFilter cf = new PorterDuffColorFilter(color, mode);
        if (mColorFilter != cf) {
            mColorFilter = cf;
            mHasColorFilter = true;
            mColorMod = true;
            applyColorMod();
            invalidate();
        }
    }

    private void applyColorMod() {
        Drawable mDrawable = getDrawable();
        if (mDrawable != null && mColorMod) {
            mDrawable = mDrawable.mutate();
            if (mHasColorFilter) {
                mDrawable.setColorFilter(mColorFilter);
            }
            mDrawable.setAlpha(mAlpha * mViewAlphaScale >> 8);
        }
    }

    private void setCustomAttributes(AttributeSet attrs) {
        TypedArray a = mContext.obtainStyledAttributes(attrs,
                R.styleable.roundedimageview);
        mBorderThickness = a.getDimensionPixelSize(R.styleable.roundedimageview_border_thickness, 0);
        mBorderColor = a.getColor(R.styleable.roundedimageview_border_color, mBorderColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        Drawable drawable = getDrawable();

        if (drawable == null) {
            return;
        }

        if (getWidth() == 0 || getHeight() == 0) {
            return;
        }
        this.measure(0, 0);
        if (drawable.getClass() == NinePatchDrawable.class)
            return;

        Bitmap b;

        if (drawable instanceof TransitionDrawable) {
            b = ((BitmapDrawable) (((TransitionDrawable) drawable).getDrawable(1))).getBitmap();
        } else {
            b = ((BitmapDrawable) drawable).getBitmap();
        }
        Bitmap bitmap = b.copy(Config.ARGB_8888, true);

        int w = getWidth(), h = getHeight();

        int radius = (w < h ? w : h) / 2 - mBorderThickness;
        Bitmap roundBitmap = getCroppedBitmap(bitmap, radius);
        // roundBitmap=ImageUtils.setCircularInnerGlow(roundBitmap, 0xFFBAB399,
        // 4, 1);
        //canvas.drawBitmap(roundBitmap, w / 2 - radius, 8, null);

        //定义边框粗细
        drawCircleBorder(canvas, radius + mBorderThickness / 2,
                mBorderColor);

        final Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setDither(true);
        paint.setColor(mBorderColor);
        canvas.setDrawFilter(new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG));
        canvas.drawCircle(w / 2,
                h / 2, radius + mBorderThickness, paint);
        canvas.drawBitmap(roundBitmap, w / 2 - radius, h / 2 - radius, null);


    }

    public static Bitmap getCroppedBitmap(Bitmap bmp, int radius) {
        Bitmap scaledSrcBmp;
        int diameter = radius * 2;
        if (bmp.getWidth() != diameter || bmp.getHeight() != diameter)
            scaledSrcBmp = Bitmap.createScaledBitmap(bmp, diameter, diameter, false);
        else
            scaledSrcBmp = bmp;
        Bitmap output = Bitmap.createBitmap(scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight(),
                Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, scaledSrcBmp.getWidth(), scaledSrcBmp.getHeight());

        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(Color.parseColor("#BAB399"));
        canvas.drawCircle(scaledSrcBmp.getWidth() / 2,
                scaledSrcBmp.getHeight() / 2, scaledSrcBmp.getWidth() / 2, paint);
        paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
        canvas.drawBitmap(scaledSrcBmp, rect, rect, paint);

        return output;
    }

    // 控件默认长宽

    private int defaultWidth = 0;

    private int defaultHeight = 0;

    /**
     * 边缘画圆
     */
    private void drawCircleBorder(Canvas canvas, int radius, int color) {

        Paint paint = new Paint();

        /* 去锯齿 */

        paint.setAntiAlias(true);

        paint.setFilterBitmap(true);

        paint.setDither(true);

        paint.setColor(color);

        /* 设置paint的　style　为STROKE：空心 */

        paint.setStyle(Paint.Style.STROKE);

        /* 设置paint的外框宽度 */

        paint.setStrokeWidth(mBorderThickness);

        canvas.drawCircle(defaultWidth / 2, defaultHeight / 2, radius, paint);

    }
}