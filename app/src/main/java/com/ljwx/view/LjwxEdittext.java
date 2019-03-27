package com.ljwx.view;

import android.content.Context;
import android.graphics.Canvas;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.util.Log;

public class LjwxEdittext extends AppCompatEditText {
    public LjwxEdittext(Context context) {
        super(context);
    }

    public LjwxEdittext(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LjwxEdittext(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.e("ljwx", "ondraw");
    }
}
