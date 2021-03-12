package com.noxgroup.qahelper.ftp.task;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.noxgroup.qahelper.ftp.bean.DocumentInfo;
import com.noxgroup.qahelper.ftp.bean.FTPInfo;
import com.noxgroup.qahelper.ftp.helper.FTPHelper;

import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/1/19
 * @Desc: FTP登录异步任务
 */
public class SearchApkTask extends AsyncTask<Void, Integer, List<DocumentInfo>> {
    private List<DocumentInfo> dataList;
    private LoadListener listener;
    private String dir = "/bignoxData/bignoxData/software/qa/Mobile";

    public SearchApkTask(List<DocumentInfo> dataList, LoadListener listener) {
        this.dataList = dataList;
        this.listener = listener;
    }

    @Override
    protected List<DocumentInfo> doInBackground(Void... voids) {
        //初始化FTPInfo
        if (!FTPHelper.getInstance().isConnected()) {
            FTPInfo ftpInfo = new FTPInfo("10.8.1.201", "21", dir, "noxuser", "duodian@123456", "UTF-8", "ACTIVE");
            boolean loginResult = FTPHelper.getInstance().login(ftpInfo);
            Log.e("SRLog", "FTPLoginTask doInBackground: loginResult: " + loginResult);
        }
        return FTPHelper.getInstance().searchApk(dir);
    }

    @Override
    protected void onPostExecute(List<DocumentInfo> documentInfoList) {
        super.onPostExecute(documentInfoList);
        if (documentInfoList != null && documentInfoList.size() > 0) {
            Log.e("SRLog", "FTPLoginTask onPostExecute: " + documentInfoList.size());
            dataList.clear();
            dataList.addAll(documentInfoList);
            listener.onComplete();
        } else {
            if (TextUtils.isEmpty(dir)) {
                //登录失败
                listener.onFail();
            } else {
                listener.onEmpty();
            }
        }
    }

    public interface LoadListener {
        void onComplete();

        void onEmpty();

        void onFail();
    }
}
