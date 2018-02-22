package com.njuse.selfdefview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import com.njuse.xiaotidu.R;

/**
 * Created by Administrator on 2018/2/22.
 */

public class ShotLandView extends View{
    public ShotLandView(Context context) {
        super(context);
        // 加载布局
        LayoutInflater.from(context).inflate(R.layout.activity_shot_land,null);
    }
}
