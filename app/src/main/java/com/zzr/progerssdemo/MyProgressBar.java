package com.zzr.progerssdemo;

import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

public class MyProgressBar extends View {
    private int curProgress;
    private int maxProgress;
    private Paint paint;
    //头部小圆圈的画笔
    private Paint headPaint;
    //圆环宽度
    private int ringWidth;
    //圆环颜色
    private int ringColor = 0XFFE4E4D4;//不能省略前面两个ff
    //圆弧开始颜色
    private int startColor = 0XFFFEEB30;
    private int endColor = 0XFFFE4E6F;
    private int headColor = 0XFFFC4E6E;
    //小圆圈宽度
    private float minWidth;
    private Context context;
    private int[] colors;
    //模糊
    private EmbossMaskFilter emboss = null;
    private BlurMaskFilter mBlur = null;

    public void setCurProgress(int curProgress) {
        this.curProgress = curProgress;
        invalidate();
    }

    public void setMaxProgress(int maxProgress) {
        this.maxProgress = maxProgress;
    }

    public MyProgressBar(Context context) {
        super(context);
        init(context);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyProgressBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        paint = new Paint();
        paint.setAntiAlias(true);
        ringWidth = dp2px(9);

        headPaint = new Paint();
        headPaint.setAntiAlias(true);
        headPaint.setColor(headColor);
        headPaint.setStyle(Paint.Style.FILL);
        minWidth = dp2px(36);

        colors = new int[]{startColor, endColor};
//        colors = new int[]{0XFFFFFFFF, 0XFF000000};
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画圆环
        drawCircle(canvas);
        //画圆弧
        drawArc(canvas);
        //画文字
        drawText(canvas);
        //画小圆圈
        drawHead(canvas);
    }

    private void drawHead(Canvas canvas) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
    }

    private void drawText(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setTextSize(80);
        paint.setStrokeWidth(0);
//        int progress = curProgress / maxProgress * 100;
        String text = (float) curProgress / maxProgress * 100 + "%";
        Rect bound = new Rect();
        paint.getTextBounds(text, 0, text.length(), bound);
        int left = getWidth() / 2 - bound.width() / 2;
        int bottom = getWidth() / 2 + bound.height() / 2;
        canvas.drawText(text, left, bottom, paint);
    }

    private void drawArc(Canvas canvas) {
        //包裹圆弧的矩形 所绘制的圆环将会是此矩形的内切椭圆
        RectF rectF = new RectF(ringWidth / 2f, ringWidth / 2f, getWidth() - ringWidth / 2f,
                getHeight() - ringWidth / 2f);
        //圆弧扫过的角度
        float sweepAngle = ((float) curProgress / maxProgress) * 360;
        Log.i("drawArc", "sweepAngle: " + sweepAngle);
        paint.setDither(true);
        //颜色渐变
        float[] po = {0.25f, 0.75f};
        SweepGradient gradient = new SweepGradient(getWidth() / 2f, getHeight() / 2f, colors, po);
        paint.setShader(gradient);
//        paint.setColor(endColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        //设置线的类型,边是圆的
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc(rectF, 90, sweepAngle, false, paint);
    }

    private void drawCircle(Canvas canvas) {
        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int radius = getWidth() / 2 - ringWidth / 2;
        paint.setColor(ringColor);
//        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(ringWidth);
        canvas.drawCircle(centerX, centerY, radius, paint);
    }

    private int dp2px(float dpValue) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * density + 0.5);
    }
}
