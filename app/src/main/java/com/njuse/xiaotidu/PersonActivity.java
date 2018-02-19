package com.njuse.xiaotidu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PersonActivity extends Activity implements AdapterView.OnItemClickListener {
    private String[] names = new String[]{"消息", "设置", "反馈", "合作", "评分"};
    private int[] imgIds = new int[]{com.njuse.xiaotidu.R.mipmap.messages, com.njuse.xiaotidu.R.mipmap.setting, com.njuse.xiaotidu.R.mipmap.feedback, com.njuse.xiaotidu.R.mipmap.cooperate, com.njuse.xiaotidu.R.mipmap.evaluate};
    private List<Map<String, Object>> data;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.njuse.xiaotidu.R.layout.activity_person);
        data = new ArrayList<Map<String, Object>>();
        SimpleAdapter myAdapter = new SimpleAdapter(this, getdata(), com.njuse.xiaotidu.R.layout.person_list_item, new String[]{"imgs", "name"}, new int[]{com.njuse.xiaotidu.R.id.imgtou, com.njuse.xiaotidu.R.id.name

        });
        listView = (ListView) findViewById(com.njuse.xiaotidu.R.id.person_list);
        listView.setAdapter(myAdapter);
        listView.setOnItemClickListener(this);
    }

    private List<Map<String, Object>> getdata() {
        for (int i = 0; i < names.length; i++) {
            Map<String, Object> showitem = new HashMap<String, Object>();
            showitem.put("imgs", imgIds[i]);
            showitem.put("name", names[i]);
            data.add(showitem);
        }
        return data;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0: {
                Intent i = new Intent(PersonActivity.this, MainActivity.class);
                startActivity(i);
                break;
            }                                                   //消息
            case 1: {
                Intent i = new Intent(PersonActivity.this, SelectGradeActivity.class);
                startActivity(i);
                break;
            }                                                   //设置
            case 2: {
                Intent i = new Intent(PersonActivity.this, FeedbackActivity.class);
                startActivity(i);
                break;
            }                                                   //反馈
            case 3: {
                Intent i = new Intent(PersonActivity.this, MainActivity.class);
                startActivity(i);
                break;
            }                                                   //合作
            case 4: {
                Intent i = new Intent(PersonActivity.this, MainActivity.class);
                startActivity(i);
                break;
            }                                                   //评分
        }
        ;
        return;
    }
};


