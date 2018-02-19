package com.njuse.xiaotidu;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //拍照搜题
        Button camera_button=(Button)this.findViewById(R.id.camera_button);
        camera_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,CameraActivity.class);
                startActivity(i);
            }
        });
    //用户设置
        Button person_button=(Button)this.findViewById(R.id.person);
        person_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,PersonActivity.class);
                startActivity(i);
            }
        });
    //文本搜题
        Button word_search_button=(Button)this.findViewById(R.id.word_search);
        word_search_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,TextActivity.class);
                startActivity(i);
            }
        });
    //收藏历史
        Button history_favourite_button=(Button)this.findViewById(R.id.favourite_history);
        history_favourite_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,HistoryFavouriteActivity.class);
                startActivity(i);
            }
        });
    };


}
