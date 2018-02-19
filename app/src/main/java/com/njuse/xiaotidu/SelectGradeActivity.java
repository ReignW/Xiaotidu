package com.njuse.xiaotidu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

public class SelectGradeActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private boolean one_selected = false;
    private boolean two_selected = false;
    private Spinner spin_one;
    private Spinner spin_two;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.njuse.xiaotidu.R.layout.activity_select_grade);
        bindViews();
    }
    private void bindViews() {
        spin_one = (Spinner) findViewById(com.njuse.xiaotidu.R.id.spinner);
        spin_two = (Spinner) findViewById(com.njuse.xiaotidu.R.id.spinner);
        spin_one.setOnItemSelectedListener(this);
        spin_two.setOnItemSelectedListener(this);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (parent.getId()){
            case com.njuse.xiaotidu.R.id.spinner:
                if(one_selected){
                    //
                }
                else one_selected = true;
                break;
            case com.njuse.xiaotidu.R.id.spinner2:
                if(two_selected){
                    //
                }
                else two_selected = true;
                break;
        }
    }
    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
