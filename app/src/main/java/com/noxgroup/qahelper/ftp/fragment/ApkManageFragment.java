package com.noxgroup.qahelper.ftp.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.base.BaseFragment;
import com.noxgroup.qahelper.ftp.adapter.APKAdapter;
import com.noxgroup.qahelper.ftp.bean.DocumentInfo;
import com.noxgroup.qahelper.ftp.download.DownLoadFileUtils;
import com.noxgroup.qahelper.ftp.task.DownloadTask;
import com.noxgroup.qahelper.ftp.task.SearchApkTask;
import com.noxgroup.qahelper.permission.utils.PermissionSettingPage;
import com.noxgroup.qahelper.util.DialogUtil;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/2/25
 * @Desc:
 */
public class ApkManageFragment extends BaseFragment {
    private List<DocumentInfo> dataList = new ArrayList<>();
    private APKAdapter adapter;
    private RecyclerView recyclerView;
    private View loadingView;
    private DocumentInfo downloadInfo;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apk;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        initData();
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        loadingView = view.findViewById(R.id.progress_bar);
    }

    private void initData() {
        adapter = new APKAdapter(activity, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new APKAdapter.OnClickListener() {
            @Override
            public void onHistory(int position) {
                //版本历史
                DocumentInfo info = dataList.get(position);
            }

            @Override
            public void onInstall(int position) {
                //安装
                DocumentInfo info = dataList.get(position);
                Log.e("SRLog", "FTPActivity onLongClick: fileName: " + info.displayName);
                checkDownload(info);
            }
        });
        showLoadingView();
        SearchApkTask task = new SearchApkTask(dataList, new SearchApkTask.LoadListener() {
            @Override
            public void onComplete() {
                Log.e("SRLog", "ApkManageFragment onComplete: " + dataList.size());
                hideLoadingView();
                if (dataList != null && !dataList.isEmpty()) {
                    //加载数据
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onEmpty() {
                hideLoadingView();
                ToastUtils.showShort(R.string.dir_empty);
            }

            @Override
            public void onFail() {
                hideLoadingView();
                ToastUtils.showShort(R.string.login_fail);
            }
        });
        task.execute();
    }


    private void showLoadingView() {
        loadingView.setVisibility(View.VISIBLE);
        activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE,
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void hideLoadingView() {
        loadingView.setVisibility(View.INVISIBLE);
        activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
    }

    private void checkDownload(DocumentInfo info) {
        downloadInfo = info;
        boolean hasStoragePer = ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        if (hasStoragePer) {
            download();
        } else {
            boolean shouldShowRequestPermissionRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (!shouldShowRequestPermissionRationale) {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1001);
            } else {
                //跳转设置页授予权限
                Intent intent = PermissionSettingPage.google(activity.getPackageName());
                startActivityForResult(intent, 1001);
            }
        }
    }


    public void download() {
        String url = downloadInfo.path + File.separator + downloadInfo.displayName;
        DialogUtil.showDownloadDialog(new WeakReference<>(activity), new DialogUtil.DownloadClickListener() {
            @Override
            public void onDownload() {
                new DownloadTask(url, DownLoadFileUtils.getDownloadFilePath(), downloadInfo.displayName, false).execute();
            }

            @Override
            public void onDownloadAndOpen() {
                new DownloadTask(url, DownLoadFileUtils.getDownloadFilePath(), downloadInfo.displayName, true).execute();
            }

            @Override
            public void onCancel() {
            }
        });
    }
}
