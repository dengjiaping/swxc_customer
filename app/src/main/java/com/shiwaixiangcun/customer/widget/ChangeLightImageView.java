package com.shiwaixiangcun.customer.widget;

import android.content.Context;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;


/**
 * Created by Administrator on 2016/8/20/020.
 */

public class ChangeLightImageView extends ImageView {
    public ChangeLightImageView(Context context) {
        super(context);
        init();
    }

    public ChangeLightImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ChangeLightImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setOnTouchListener(onTouchListener);
    }

    public OnTouchListener onTouchListener = new OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    changeLight((ImageView) view, 0);

                    break;
                case MotionEvent.ACTION_DOWN:
                    changeLight((ImageView) view, -50);
                    break;
                case MotionEvent.ACTION_MOVE:
                    // changeLight(view, 0);
                    break;
                case MotionEvent.ACTION_CANCEL:
                    changeLight((ImageView) view, 0);
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private void changeLight(ImageView imageview, int brightness) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[]{1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0});
        imageview.setColorFilter(new ColorMatrixColorFilter(matrix));

    }
}
