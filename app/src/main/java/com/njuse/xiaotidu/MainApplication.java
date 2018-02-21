package com.njuse.xiaotidu;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.njuse.utils.Permissions;

/**
 * Created by Administrator on 2018/2/21.
 */

public class MainApplication extends AppCompatActivity {
    private String userInfoDB;
    private String userInfoTable;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String path;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Permissions.verifyStoragePermissions(MainApplication.this);
        setContentView(R.layout.activity_splash);

        userInfoDB = "userinfo.db";
        userInfoTable = "userinfo";
        path = this.getFilesDir() + "/"; //数据库路径
        db = SQLiteDatabase.openOrCreateDatabase(path + userInfoDB, null);
        String querySql = "SELECT * FROM " + userInfoTable + " WHERE islogin='" + 1 + "'";
        try {
            cursor = db.rawQuery(querySql, null);
        } catch (SQLException se) {
            //执行创建数据表
            String createTableSql = "create table " + userInfoTable + " (_id integer primary key autoincrement,account varchar(50),password varchar(16),islogin varchar(4))";
            db.execSQL(createTableSql);
            cursor = db.rawQuery(querySql, null);
        }

//        Toast.makeText(MainApplication.this, cursor.getCount() + "", Toast.LENGTH_LONG).show();   //用作测试

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (cursor.getCount() != 0) {
                    Intent intent = new Intent(MainApplication.this, MainActivity.class);
                    intent.putExtra("account", cursor.getColumnIndex("account"));
                    startActivity(intent);
                } else {
                    startActivity(new Intent(MainApplication.this, LoginActivity.class));
                }
                MainApplication.this.finish();
            }
        }, 2000);
    }
}




