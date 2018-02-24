package com.njuse.xiaotidu;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.njuse.selfdefview.ClipImageView;
import com.njuse.selfdefview.Line;
import com.njuse.utils.Constants;

import java.io.File;

public class CameraConfirmActivity extends AppCompatActivity {
    private String fileName;
    private ImageView preview;
    private Button confirm;
    private Button reshot;
    private ClipImageView clipImageView;
    private Line line;
    private Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera_confirm);

        //初始化控件
        initWidget();

        bitmap = BitmapFactory.decodeFile(fileName);
        clipImageView.setImageBitmap(bitmap);

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CameraConfirmActivity.this,Constants.clipImagePath,Toast.LENGTH_LONG).show();

                //展示搜索结果

                Intent i=new Intent(CameraConfirmActivity.this,SearchResultActivity.class);
                startActivity(i);
                finish();
            }
        });

        reshot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CameraConfirmActivity.this,CameraActivity.class));
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
        clipImageView = findViewById(R.id.clipimageview);

//        Toast.makeText(CameraConfirmActivity.this,fileName,Toast.LENGTH_LONG).show();     //测试之用
        preview.setImageURI(Uri.fromFile(new File(fileName)));
    }

    public void saveClipImage(View view) {
        line = clipImageView.getClipLine();
        Log.i("结果", line.getLeft() + " " + line.getTop() + " " + line.getRight() + " " + line.getBottom());
        bitmap=bitmap.createBitmap(bitmap, (int)line.getLeft(),(int)line.getTop(),(int)line.getRight(),(int)line.getBottom());//截取图片
        Constants.saveBitmap(bitmap);//保存截取图片
        setResult(RESULT_OK);
        finish();
    }


    /**
     * 获取图片
     * @return
     */
    public Bitmap getBitmap()
    {
        saveClipImage(clipImageView);
        return  bitmap;
    }

    public void cancel(View view) {
        finish();
    }
}
