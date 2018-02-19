package com.njuse.xiaotidu;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by lenovo on 2018/2/5.
 */

public class FgHistory extends Fragment {
    public FgHistory() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(com.njuse.xiaotidu.R.layout.fg_favourite, container, false);

        return view;
    }
}
