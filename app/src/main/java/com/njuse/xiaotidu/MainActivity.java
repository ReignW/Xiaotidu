package com.njuse.xiaotidu;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;

import com.njuse.utils.Permissions;

import java.util.Timer;
import java.util.TimerTask;

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
                Permissions.verifyCameraPermission(MainActivity.this);
                final Handler handler = new Handler();
                final Timer timer = new Timer(false);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED){
                                    Intent i=new Intent(MainActivity.this,CameraActivity.class);
                                    startActivity(i);
                                    timer.cancel();
                                }
                            }
                        });
                    }
                },0,100);

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
