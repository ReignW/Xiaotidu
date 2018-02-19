package com.njuse.xiaotidu;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.List;

/**
 * Created by lenovo on 2018/2/5.
 */

public class FgFavourite extends Fragment {
    private List<Problem> mData = null;
    private Context mContext;
    private ProblemAdapter mAdapter = null;
    private ListView problem_list;
    public FgFavourite() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fg_favourite, container, false);

        return view;
    }
}
