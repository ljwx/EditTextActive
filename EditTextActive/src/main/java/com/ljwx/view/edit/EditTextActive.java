package com.ljwx.view.edit;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatEditText;
import android.text.InputFilter;
import android.util.AttributeSet;

import java.lang.reflect.Field;

public class EditTextActive extends AppCompatEditText {

    private int mMaxLenght = 0;
    private float mMarginValue = 0;
    private float mRadiu = 0f;
    private Paint mBgPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mBgColorY = Color.parseColor("#FF6D00");
    private int mBgColorN = Color.parseColor("#d9d9d9");


    public EditTextActive(Context context) {
        super(context, null);
    }

    public EditTextActive(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray attr = context.obtainStyledAttributes(attrs, R.styleable.EditTextActive);
        mMarginValue = attr.getDimension(R.styleable.EditTextActive_etaMarginLeft, 30f);
        mRadiu = attr.getDimension(R.styleable.EditTextActive_etaRadiu, 15);
        mBgColorN = attr.getColor(R.styleable.EditTextActive_etaBgColor, mBgColorN);
        mBgColorY = attr.getColor(R.styleable.EditTextActive_etaSolidColor, mBgColorY);
        attr.recycle();
        init();
    }

    public EditTextActive(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void init() {
        mMaxLenght = getMaxLength();
        setCursorVisible(false);
        setBackground(null);
    }

//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//
//        String txt = getText().toString();
//        if (MeasureSpec.AT_MOST == heightMode || MeasureSpec.UNSPECIFIED == heightMode) {
////                height = (int) (getPaint().descent() - getPaint().ascent() + ptm);
//            height = getMeasuredHeight();
//        }
//
//        if (MeasureSpec.AT_MOST == widthMode) {
//            width = getHeight() * mMaxLenght + ((mMaxLenght - 1) * mMarginValue);
//        }
//        setMeasuredDimension(width, height);
//    }

//    @Override
//    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
//        super.onSizeChanged(w, h, oldw, oldh);
//
//    }

//    @Override
//    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
//        super.onLayout(changed, left, top, right, bottom);
//        setMeasuredDimension(getHeight() * mMaxLenght + ((mMaxLenght - 1) * mMarginValue), getHeight());
//    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawBg(canvas);
        drawSolid(canvas);
        drawText(canvas);
    }

    private void drawBg(Canvas canvas) {
        RectF rf = new RectF(0, 0, getHeight(), getHeight());
        mBgPaint.setColor(mBgColorN);
        canvas.save();
        for (int i = 0; i < mMaxLenght; i++) {
            canvas.drawRoundRect(rf, mRadiu, mRadiu, mBgPaint);
            canvas.translate((getHeight() + mMarginValue), 0);
        }
        canvas.restore();
    }

    private void drawSolid(Canvas canvas) {
        RectF rf = new RectF(0, 0, getHeight(), getHeight());
        canvas.save();
        mBgPaint.setColor(mBgColorY);
        for (int i = 0; i < Math.min(getText().length(), mMaxLenght); i++) {
            float t = (getHeight() + mMarginValue);
            canvas.drawRoundRect(rf, mRadiu, mRadiu, mBgPaint);
            canvas.translate(t, 0);
        }
        canvas.restore();
    }

    private void drawText(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < Math.min(getText().length(), mMaxLenght); i++) {
            String num = getText().charAt(i) + "";
            float x = getHeight() / 2 - getPaint().measureText(num) / 2;
            float y = (getHeight() / 2) + Math.abs(getPaint().ascent() + getPaint().descent()) / 2;
            canvas.drawText(num, x, y, getPaint());
            canvas.translate((getHeight() + mMarginValue), 0);
        }
        canvas.restore();
    }


    public int getMaxLength() {
        int length = 4;
        try {
            InputFilter[] inputFilters = getFilters();
            for (InputFilter filter : inputFilters) {
                Class<?> c = filter.getClass();
                if (c.getName().equals("android.text.InputFilter$LengthFilter")) {
                    Field[] f = c.getDeclaredFields();
                    for (Field field : f) {
                        if (field.getName().equals("mMax")) {
                            field.setAccessible(true);
                            length = (Integer) field.get(filter);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return length;
    }
}
