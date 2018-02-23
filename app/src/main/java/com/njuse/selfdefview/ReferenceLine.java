package com.njuse.selfdefview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.apollographql.apollo.api.internal.Utils;
import com.njuse.utils.ScreenUtils;

/**
 * Created by Administrator on 2018/2/22.
 * <p>
 * 相机网格参考线
 */

public class ReferenceLine extends View {
    private Paint paint;    //画线对应的画笔

    public ReferenceLine(Context context) {
        super(context);
        init();
    }

    public ReferenceLine(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ReferenceLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ReferenceLine(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    /*
    初始化画笔
     */
    private void init() {
        paint = new Paint();
        paint.setAntiAlias(true);//是否抗锯齿
        paint.setColor(Color.parseColor("#ffffff"));//画笔颜色
        paint.setStrokeWidth(1);//设置线的宽度
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int screenWidth = ScreenUtils.getScreenWidth(getContext());
        int screenHeight = ScreenUtils.getScreenHeight(getContext());
        //将屏幕划分为3*3的网格
        int width = screenWidth/3;
        int height = screenHeight/3;

        for (int i = width,j = 0;i<=screenWidth && j<2;i+=width,j++){
            canvas.drawLine(i,0,i,screenHeight,paint);      //画垂直线
        }
        for (int j = height,i = 0;j<=screenHeight && i<2;j+=height,i++){
            canvas.drawLine(0,j,screenWidth,j,paint);       //画水平线
        }
    }
}
