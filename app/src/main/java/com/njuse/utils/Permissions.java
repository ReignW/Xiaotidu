package com.njuse.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.njuse.xiaotidu.MainApplication;

/**
 * Created by Administrator on 2018/2/21.
 */

public class Permissions {
    //安卓6.0以上动态获取内存卡读取权限
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };

    //判断是否有内存卡读权限
    public static boolean verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    public static boolean verifyCameraPermission(Activity activity){
        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
                /* 相机请求码 */
            final int REQUEST_CAMERA = 0;
            ActivityCompat.requestPermissions(activity,
                    new String[]{android.Manifest.permission.CAMERA},REQUEST_CAMERA);
        }
        return true;
    }
}
