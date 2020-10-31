package com.qiniu.pili.droid.streaming.demo.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import com.qiniu.pili.droid.streaming.demo.R;

/**
 * @PackageName : com.qiniu.pili.droid.streaming.demo.view
 * @File : WaterHandler.java
 * @Date : 2020/9/24 2020/9/24
 * @Author : K
 * @E-mail : vip@devkit.vip
 * @Version : V 1.0
 * @Describe ：
 */
public class WaterHandler extends Handler {

    Bitmap bitmap;
    Bitmap logo;
    public boolean draw;
    public Activity mContext;
    private OnWaterListener mWaterListener;
    private int videoWidth ,videoHeight;

    public WaterHandler(Looper looper) {
        super(looper);

    }

    public void init(Activity context, int width, int height) {
        mContext = context;
        videoWidth = width;
        videoHeight= height;
        logo = getBitmap(context, R.mipmap.icon_logo_watermark);
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);


    }

    public void setWaterListener(OnWaterListener waterListener) {
        mWaterListener = waterListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        if (msg.what == 0) {
            draw();
            sendEmptyMessageDelayed(0, 100);
        } else {

        }
    }


    public void draw() {
        Canvas canvas = new Canvas(bitmap);
//        canvas.drawColor(Color.parseColor("#ff00ff"));

        Paint paint = new Paint();
        canvas.drawBitmap(logo, dp2px(mContext, 10), dp2px(mContext, 10), paint);
        drawTitle(canvas);

        if (mWaterListener != null) {
            mWaterListener.onDrawWater(bitmap);
        }
    }

    private void drawBottom(Canvas canvas) {


    }

    private void drawTitle(Canvas canvas) {


        String title = "2020佛山怡翠超";

        TextPaint mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setColor(Color.parseColor("#ffffff"));
        mTextPaint.setTextSize(dp2sp(mContext, 12));
        mTextPaint.setAntiAlias(true);
        mTextPaint.setDither(true);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.setStyle(Paint.Style.FILL);
        // 1.用FontMetrics对象计算高度
        Rect rect = new Rect();
            mTextPaint.getTextBounds(title, 0, title.length(), rect);
        int textWidth = rect.width();
        float textHeight = rect.height() * (title.length() > 15 ? 2 : 1);


        Paint bg = new Paint();
        bg.setColor(Color.parseColor("#26F9C5"));
        bg.setStyle(Paint.Style.FILL);
        bg.setAntiAlias(true);

        float radius = dp2px(mContext, 12);
        float margin = dp2px(mContext, 6);
        float[] radiusArray = {0f, 0f, radius, radius, 0f, 0f, radius, radius};


        float marginTop = dp2px(mContext, 10);
        float marginRight = dp2px(mContext, 10);
        float marginText = dp2px(mContext, 4);

        RectF rectF = new RectF(videoWidth - textWidth - (margin * 4) - marginRight, marginTop, videoWidth - marginRight, textHeight + marginTop + (marginText * 2));
        Path path = new Path();
        path.addRoundRect(rectF, radiusArray, Path.Direction.CW);
        canvas.drawPath(path, bg);


        float tvLeft = videoWidth - textWidth - (margin * 2) - margin - marginRight;
        float tvTop = marginTop;
        float tvRight = videoWidth - marginRight - margin;
        float tvBottom = textHeight + marginTop + (marginText * 2);
        bg.setColor(Color.parseColor("#5AB3FF"));
        RectF rectF2 = new RectF(tvLeft, tvTop, tvRight, tvBottom);// 设置个新的长方形
        Path path2 = new Path();
        path2.addRoundRect(rectF2, radiusArray, Path.Direction.CW);
        canvas.drawPath(path2, bg);


        //计算baseline
        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        float distance = (fontMetrics.descent - fontMetrics.ascent) / 2 - fontMetrics.descent;
        float baseline = rectF.centerY() + distance;
        canvas.drawText(title, rectF.centerX(), baseline, mTextPaint);

    }


    public Bitmap getBitmap(Context context, int resId) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        TypedValue value = new TypedValue();
        context.getResources().openRawResource(resId, value);
        options.inTargetDensity = value.density;
        options.inScaled = false;
        return BitmapFactory.decodeResource(context.getResources(), resId, options);
    }

    public void start() {
        draw = true;
        sendEmptyMessage(0);
    }

    public void stop() {
        draw = false;
        removeMessages(0);
        sendEmptyMessage(1);
    }

    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    public static int dp2sp(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                dpVal, context.getResources().getDisplayMetrics());
    }


}
