package com.zzr.progerssdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.Random;

public class CircleBitmap extends View {
    private Paint paint;
    private Bitmap bitmap;
    private int defaultId;
    private int radius;
    private int bgColor;
    private int bgWidth;
    private int defaultColor = 0XFFD1B31F;
    private boolean isOpenRandom;

    public CircleBitmap(Context context) {
        this(context, null);
    }

    public CircleBitmap(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleBitmap(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.CircleBitmap);
        defaultId = a.getResourceId(R.styleable.CircleBitmap_srcBitmap, -1);
        bgWidth = a.getResourceId(R.styleable.CircleBitmap_colorBitmapWidth, 200);
        bgColor = a.getColor(R.styleable.CircleBitmap_colorBitmap, defaultColor);
        isOpenRandom = a.getBoolean(R.styleable.CircleBitmap_openRandomColor, false);
        a.recycle();
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setDither(true);
        if (defaultId > 0) {
            bitmap = BitmapFactory.decodeResource(getContext().getResources(), defaultId);
        } else {
            //根据颜色生产bitmap
            bitmap = Bitmap.createBitmap(bgWidth, bgWidth, Bitmap.Config.ARGB_8888);
            //填充颜色
            //填充颜色
            int color = isOpenRandom ? Color.parseColor(getRandColor()) : bgColor;
            bitmap.eraseColor(color);
        }

        BitmapShader shader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(shader);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        radius = Math.min(getWidth(), getHeight()) / 2;
        canvas.drawCircle(getWidth() / 2f, getHeight() / 2f, radius, paint);
    }

    /**
     * 获取十六进制的颜色代码.例如  "#5A6677"
     * 分别取R、G、B的随机值，然后加起来即可
     *
     * @return String
     */
    private String getRandColor() {
        String R, G, B;
        Random random = new Random();
        R = Integer.toHexString(random.nextInt(256)).toUpperCase();
        G = Integer.toHexString(random.nextInt(256)).toUpperCase();
        B = Integer.toHexString(random.nextInt(256)).toUpperCase();

        R = R.length() == 1 ? "0" + R : R;
        G = G.length() == 1 ? "0" + G : G;
        B = B.length() == 1 ? "0" + B : B;

        return "#" + R + G + B;
    }
}
