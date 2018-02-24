package com.njuse.xiaotidu;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuestionDetailActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView searchResult;
    private TextView questionDescription;
    private TextView answerDescription;
    private Button warning;
    private Button share;
    private Button collect;
    private Button shotMore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        //初始化控件
        intiWidget();

        //监听事件
        searchResult.setOnClickListener(this);
        shotMore.setOnClickListener(this);
    }

    private void intiWidget() {
        searchResult = findViewById(R.id.search_result_textview);
        questionDescription = findViewById(R.id.question_description_textview);
        answerDescription = findViewById(R.id.answer_description_textview);
        warning = findViewById(R.id.warning_button);
        share = findViewById(R.id.share_question_button);
        collect = findViewById(R.id.collect_button);
        shotMore = findViewById(R.id.shot_more_button);

        questionDescription.setText(getIntent().getStringExtra("question"));
        answerDescription.setText("答案答案答案答案答案答案\n答案答案答案答案答案答案\n答案答案答案答案答案答案答案\n答案答案答案答案答案答案答案答案答案答案");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_result_textview:
                startActivity(new Intent(QuestionDetailActivity.this,SearchResultActivity.class));
                finish();
                break;
            case R.id.shot_more_button:
                startActivity(new Intent(QuestionDetailActivity.this,CameraActivity.class));
                finish();
                break;
        }
    }
}
