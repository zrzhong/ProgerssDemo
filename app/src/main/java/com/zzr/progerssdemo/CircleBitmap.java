package com.zzr.progerssdemo;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class CircleBitmap extends View {
    private Paint paint;
    private Bitmap bitmap;
    private int defaultId;
    private int radius;
    private int bgColor;
    private int bgWidth;
    private int defaultColor = 0XFFD1B31F;

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
            bitmap.eraseColor(bgColor);
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
}
