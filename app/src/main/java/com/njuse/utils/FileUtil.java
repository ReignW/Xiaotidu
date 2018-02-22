package com.njuse.utils;

import android.os.Handler;

import java.io.File;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Administrator on 2018/2/22.
 */

public class FileUtil {
    private static long oldLength = 0;

    /**
     * 等待文件(非目录)读写完毕，费时的操作，不要放在主线程
     *
     * @param file 文件
     */
    public static void waitForWirtenCompleted(final File file) {
        if (!file.exists())
            return;

        final Handler handler = new Handler();
        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        if (oldLength == file.length()) {
                            timer.cancel();
                        } else {
                            oldLength = file.length();
                        }
                    }
                });
            }
        }, 0, 100);
    }
}
