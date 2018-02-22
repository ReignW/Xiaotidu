package com.njuse.xiaotidu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class CameraConfirmActivity extends AppCompatActivity {
    private String fileName;
    private ImageView preview;
    private Button confirm;
    private Button reshot;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_confirm);

        //初始化控件
        initWidget();

        Button person_button=(Button)this.findViewById(R.id.confirm_button);
        person_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //展示搜索结果

                Intent i=new Intent(CameraConfirmActivity.this,SearchResultActivity.class);
                startActivity(i);
                finish();
            }
        });

        Button back_button=(Button)this.findViewById(R.id.reshot_button);
        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CameraConfirmActivity.this.finish();
            }
        });
    }

    private void initWidget() {
        //获取照片的路径
        fileName = getIntent().getStringExtra("fileName");

        preview = findViewById(R.id.preview_imageview);
        confirm = findViewById(R.id.confirm_button);
        reshot = findViewById(R.id.reshot_button);

//        Toast.makeText(CameraConfirmActivity.this,fileName,Toast.LENGTH_LONG).show();     //测试之用
        preview.setImageURI(Uri.fromFile(new File(fileName)));
    }
}
