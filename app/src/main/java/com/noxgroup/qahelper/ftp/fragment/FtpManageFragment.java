package com.noxgroup.qahelper.ftp.fragment;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.noxgroup.qahelper.ftp.adapter.FTPFileAdapter;
import com.noxgroup.qahelper.ftp.bean.DocumentInfo;
import com.noxgroup.qahelper.ftp.download.DownLoadFileUtils;
import com.noxgroup.qahelper.ftp.task.DownloadTask;
import com.noxgroup.qahelper.ftp.task.FTPLoginTask;
import com.noxgroup.qahelper.ftp.widget.PathRemoteView;
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
public class FtpManageFragment extends BaseFragment {
    private List<DocumentInfo> dataList = new ArrayList<>();
    private FTPFileAdapter adapter;
    private RecyclerView recyclerView;
    private PathRemoteView pathView;
    private String rootPath = "/bignoxData/bignoxData/software/qa/Mobile";
    private String currentFolder = "/";
    private FTPLoginTask task;
    private View loadingView;
    private DocumentInfo downloadInfo;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
        initData();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_ftp;
    }

    private void initView(View view) {
        recyclerView = view.findViewById(R.id.recycler_view);
        pathView = view.findViewById(R.id.path_view);
        loadingView = view.findViewById(R.id.progress_bar);
    }

    private void initData() {
        adapter = new FTPFileAdapter(activity, dataList);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity));
        recyclerView.setAdapter(adapter);
        showLoadingView();
        task = new FTPLoginTask(dataList, new FTPLoginTask.LoadListener() {
            @Override
            public void onComplete() {
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
        }, rootPath);
        task.execute();
        adapter.setOnClickListener(new FTPFileAdapter.OnClickListener() {
            @Override
            public boolean onClick(int position) {
                DocumentInfo info = dataList.get(position);
                //如果是文件则单击打开，否则进入文件夹中
                if (info.fileType == DocumentInfo.FILE_TYPE) {
                } else {
                    changePath(info.path, info.displayName);
                }
                return false;
            }

            @Override
            public void onLongClick(int position) {
                DocumentInfo info = dataList.get(position);
                if (info.fileType == DocumentInfo.FILE_TYPE) {
                    //弹出下载弹框
                    checkDownload(info);
                }
            }
        });
        pathView.initData(currentFolder, new PathRemoteView.PathListener() {
            @Override
            public void onPathChanged(String path) {
                //路径
                changePath(path, "");
            }
        });
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

    private void changePath(String path, String displayName) {
        if (!TextUtils.isEmpty(displayName)) {
            if (path.endsWith("/")) {
                path += displayName;
            } else {
                path += File.separator + displayName;
            }
        }
        String finalPath = path;
        showLoadingView();
        task = new FTPLoginTask(dataList, new FTPLoginTask.LoadListener() {
            @Override
            public void onComplete() {
                Log.e("SRLog", "FtpManageFragment onComplete: " + dataList.size());
                if (dataList != null && !dataList.isEmpty()) {
                    hideLoadingView();
                    currentFolder = finalPath;
                    try {
                        pathView.buildPathView(currentFolder);
                    } catch (Exception e) {
                        activity.finish();
                    }
                    //加载数据
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onEmpty() {
                Log.e("SRLog", "FtpManageFragment onEmpty: ");
                hideLoadingView();
                ToastUtils.showShort(R.string.dir_empty);
            }

            @Override
            public void onFail() {
                hideLoadingView();
                ToastUtils.showShort(R.string.login_fail);
            }
        }, path);
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

    public boolean checkBack() {
        //当前路径是不合法的路径或者根目录直接返回
        if (!currentFolder.contains("/") || currentFolder.equals("/")) {
            return true;
        }
        String path = currentFolder.substring(0, currentFolder.lastIndexOf("/"));
        if (TextUtils.isEmpty(path)) {
            path = "/";
        }
        changePath(path, "");
        return false;
    }
}
