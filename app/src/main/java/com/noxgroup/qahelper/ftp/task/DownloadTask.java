package com.noxgroup.qahelper.ftp.task;

import android.os.AsyncTask;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.ftp.helper.FTPHelper;
import com.noxgroup.qahelper.util.OpenFileUtils;
import com.noxgroup.qahelper.util.TimeUtil;
import com.noxgroup.qahelper.util.Utils;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @Author: SongRan
 * @Date: 2021/1/20
 * @Desc:
 */
public class DownloadTask extends AsyncTask<Void, Integer, Boolean> {
    private String url;
    private String downloadPath;
    private String fileName = "";
    private boolean isOpen;

    public DownloadTask(String url, String downloadPath, String fileName, boolean isOpen) {
        this.url = url;
        this.downloadPath = downloadPath;
        this.fileName = fileName;
        this.isOpen = isOpen;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        ToastUtils.showShort(Utils.getApp().getString(R.string.download_start, fileName));
    }

    @Override
    protected Boolean doInBackground(Void... voids) {
        File file = new File(downloadPath + File.separator + fileName);
        if (file.exists()) {
            //重命名文件
            String fileType = "";
            if (fileName.contains(".")) {
                fileType = fileName.substring(fileName.lastIndexOf("."));
                fileName = fileName.substring(0, fileName.lastIndexOf(".")) + "_" + TimeUtil.getCurrentTimeHms() + fileType;
            } else {
                fileName += "_" + TimeUtil.getCurrentTimeHms();
            }
        } else {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        OutputStream outputStream = null;
        try {
            outputStream = new FileOutputStream(downloadPath + File.separator + fileName);
            return FTPHelper.getInstance().download(url, outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    protected void onPostExecute(Boolean isSuccess) {
        super.onPostExecute(isSuccess);
        ToastUtils.showShort(Utils.getApp().getString(isSuccess ? R.string.download_suc : R.string.download_fail, fileName));
        if (isOpen) {
            //打开文件
            OpenFileUtils.openFile(Utils.getApp(), downloadPath + File.separator + fileName);
        }
    }
}
