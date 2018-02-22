package com.njuse.selfdefview;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;

import com.njuse.xiaotidu.R;

/**
 * Created by Administrator on 2018/2/22.
 */

public class ShotPortView extends View{
    public ShotPortView(Context context) {
        super(context);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_shot_port,null);
    }

    public ShotPortView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_shot_port,null);
    }

    public ShotPortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_shot_port,null);
    }

    public ShotPortView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_shot_port,null);
    }
}
