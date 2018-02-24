package com.njuse.xiaotidu;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.njuse.utils.MyAdapter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SearchResultActivity  extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView questionList;
    private Button shotAgain;
    private MyAdapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private String questionDescription;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        //初始化控件
        initWidget();
        //初始化适配器数据
        initData();
        initView();

        //设置监听事件
        shotAgain.setOnClickListener(this);
        mAdapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
//                Toast.makeText(SearchResultActivity.this,"click " + position + " item", Toast.LENGTH_SHORT).show();
                View view1 = mLayoutManager.findViewByPosition(position);
                LinearLayout layout = (LinearLayout) view1;
                TextView textView = layout.findViewById(R.id.item_tv);
                questionDescription = textView.getText().toString();
                Intent intent = new Intent(SearchResultActivity.this,QuestionDetailActivity.class);
                intent.putExtra("question",questionDescription);
                startActivity(intent);
                finish();
            }

            @Override
            public void onItemLongClick(View view, int position) {
//                Toast.makeText(SearchResultActivity.this,"long click " + position + " item", Toast.LENGTH_SHORT).show();
            }
        });

    }

    //初始化控件
    private void initWidget() {
        questionList = findViewById(R.id.search_result_list_recyclerview);
        shotAgain = findViewById(R.id.shot_again_button);
    }

    private void initData() {
        mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mAdapter = new MyAdapter(getData());
    }

    private void initView() {
        // 设置布局管理器
        questionList.setLayoutManager(mLayoutManager);
        // 设置adapter
        questionList.setAdapter(mAdapter);
    }

    private ArrayList<String> getData() {
        ArrayList<String> data = new ArrayList<>();
        String question = "给出一个集合A和A上的关系R，求关系R的传递闭包。\n" +
                "例如：\n" +
                "A={0,1,2} , R={<0,0>,<1,0>,<2,2>,<1,2>,<2,1>}   \n" +
                "t(R) = {<0,0>,<1,0>,<2,2>,<2,1>,<1,2>,<1,1>,<2,0>};";
        for(int i = 0; i < 20; i++) {
            data.add(question);
        }

        return data;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.shot_again_button:
                startActivity(new Intent(SearchResultActivity.this,CameraActivity.class));
                finish();
                break;
        }
    }
}
