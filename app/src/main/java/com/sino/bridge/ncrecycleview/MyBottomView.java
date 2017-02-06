package com.sino.bridge.ncrecycleview;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by user on 2016/12/28.
 */
public class MyBottomView extends RelativeLayout {
    public MyBottomView(Context context) {
        super(context);
        initView(context);
    }

    private void initView(Context context) {
        TextView textView = new TextView(context);
        textView.setText("加载中......");
        textView.setGravity(Gravity.CENTER);
        ProgressBar  progressBar=new ProgressBar(context);
        progressBar.getId();
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(params);
        addView(textView);
        addView(progressBar);
    }

    public MyBottomView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyBottomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MyBottomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

}
