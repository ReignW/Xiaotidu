package com.njuse.utils;

/**
 * Created by Administrator on 2018/2/23.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.Toast;

import com.njuse.xiaotidu.CameraConfirmActivity;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by Myy on 2016/8/29.
 */
public class Constants {

//    public static String clipImagePath = Environment.getDownloadCacheDirectory()+"/temp.png";//文件路径
    public static String clipImagePath = "/storage/emulated/0/temp.png";//文件路径

    /**
     * 保存图片
     * @param cropBitmap
     */
    public static void saveBitmap(Bitmap cropBitmap) {
        File file = new File(Constants.clipImagePath);
        FileOutputStream os=null;
        if (file.exists())
            file.delete();
        try {
            file.createNewFile();
            os = new FileOutputStream(file);
            if (cropBitmap.compress(Bitmap.CompressFormat.PNG, 100, os)){
                os.flush();
                Log.i("asjfoiaifo;ajivfa","保存成功");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
