package com.noxgroup.qahelper.ftp;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.ftp.adapter.FileAdapter;
import com.noxgroup.qahelper.ftp.bean.FileInfo;
import com.noxgroup.qahelper.ftp.download.DownLoadFileUtils;
import com.noxgroup.qahelper.util.OpenFileUtils;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/1/20
 * @Desc:
 */
public class DownloadHistoryActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private FileAdapter adapter;
    private List<FileInfo> dataList = new ArrayList<>();
    private TextView tvDelete;
    private boolean isEdit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_history);
        initView();
        iniData();
    }

    private void iniData() {
        File file = new File(DownLoadFileUtils.getDownloadFilePath());
        if (file.exists() && file.listFiles() != null) {
            for (File listFile : file.listFiles()) {
                dataList.add(new FileInfo(listFile.getName(), listFile.getAbsolutePath(), false, listFile.getName().endsWith(".apk")));
            }
            adapter = new FileAdapter(DownloadHistoryActivity.this, dataList);
            recyclerView.setLayoutManager(new LinearLayoutManager(DownloadHistoryActivity.this));
            recyclerView.setAdapter(adapter);
            adapter.setOnClickListener(new FileAdapter.OnClickListener() {
                @Override
                public boolean onClick(int position) {
                    OpenFileUtils.openFile(DownloadHistoryActivity.this, dataList.get(position).getFilePath());
                    return false;
                }
            });
        }
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.download_history);
        }
        recyclerView = findViewById(R.id.recycler_view);
        tvDelete = findViewById(R.id.tv_delete);
        tvDelete.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.edit:
                isEdit = !isEdit;
                item.setTitle(isEdit ? R.string.done : R.string.edit);
                changeDeleteViewState(isEdit);
                adapter.setEdit(isEdit);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void changeDeleteViewState(boolean isEdit) {
        tvDelete.setVisibility(isEdit ? View.VISIBLE : View.GONE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_delete:
                //删除选中项
                deleteFile();
                break;
        }
    }

    private void deleteFile() {
        List<FileInfo> list = adapter.getSelectList();
        if (list.size() > 0) {
            for (FileInfo fileInfo : list) {
                File file = new File(fileInfo.getFilePath());
                if (file.exists()) {
                    file.delete();
                }
            }
            dataList.removeAll(list);
            adapter.notifyDataSetChanged();
            ToastUtils.showShort(getString(R.string.delete_suc, list.size()));
        } else {
            ToastUtils.showShort(R.string.not_select);
        }
    }
}
