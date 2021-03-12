package com.noxgroup.qahelper.ftp.download;

import android.os.Environment;
import android.util.Log;

import com.noxgroup.qahelper.util.Utils;

import java.io.File;

/**
 * @Author: SongRan
 * @Date: 2021/1/20
 * @Desc:
 */
public class DownLoadFileUtils {
    private static String downloadFilePath = Environment.getExternalStorageDirectory() + File.separator + "0QAHelper" + File.separator;

    public static String getDownloadFilePath() {
        File file;
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            file = Utils.getApp().getExternalFilesDir(null);
//        } else {
//            Log.e("SRLog", "DownLoadFileUtils getDownloadFilePath: downloadFilePath: "+downloadFilePath);
//            file = new File(downloadFilePath);
//        }
        file = Utils.getApp().getExternalFilesDir(null);
        if (!file.exists()) {
            Log.e("SRLog", "DownLoadFileUtils getDownloadFilePath: mkdirs");
            boolean result = file.mkdirs();
            Log.e("SRLog", "DownLoadFileUtils getDownloadFilePath: 创建文件夹: " + result);
        }
        return file.getAbsolutePath();
    }

}
