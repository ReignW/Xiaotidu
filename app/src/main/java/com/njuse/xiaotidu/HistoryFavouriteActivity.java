package com.njuse.xiaotidu;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class HistoryFavouriteActivity extends AppCompatActivity  implements RadioGroup.OnCheckedChangeListener, ViewPager.OnPageChangeListener {

    private TextView txt_topbar;
    private RadioGroup rg_tab_bar;
    private RadioButton rb_history;
    private RadioButton rb_favourite;
    private ViewPager vpager;

    private MyFragmentPagerAdapter mAdapter;

    //几个代表页面的常量
    public static final int PAGE_ONE = 0;
    public static final int PAGE_TWO = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.njuse.xiaotidu.R.layout.activity_history_favourite);
        mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        bindViews();
        rb_favourite.setChecked(true);
    }

    private void bindViews() {
        rg_tab_bar = (RadioGroup) findViewById(com.njuse.xiaotidu.R.id.rg_tab_bar);
        rb_history = (RadioButton) findViewById(com.njuse.xiaotidu.R.id.history);
        rb_favourite = (RadioButton) findViewById(com.njuse.xiaotidu.R.id.favourite);
        rg_tab_bar.setOnCheckedChangeListener(this);

        vpager = (ViewPager) findViewById(com.njuse.xiaotidu.R.id.vpager);
        vpager.setAdapter(mAdapter);
        vpager.setCurrentItem(0);
        vpager.addOnPageChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case com.njuse.xiaotidu.R.id.favourite:
                vpager.setCurrentItem(PAGE_ONE);
                break;
            case com.njuse.xiaotidu.R.id.history:
                vpager.setCurrentItem(PAGE_TWO);
                break;

        }
    }
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //state的状态有三个，0表示什么都没做，1正在滑动，2滑动完毕
        if (state == 2) {
            switch (vpager.getCurrentItem()) {
                case PAGE_ONE:
                    rb_favourite.setChecked(true);
                    break;
                case PAGE_TWO:
                    rb_history.setChecked(true);
                    break;

            }
        }
    }
}
