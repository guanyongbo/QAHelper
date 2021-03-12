package com.noxgroup.qahelper.permission;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.permission.adapter.AppAdapter;
import com.noxgroup.qahelper.permission.helper.PermissionHelper;
import com.noxgroup.qahelper.permission.utils.SettingPageUtil;
import com.noxgroup.qahelper.permission.widget.CustomerCheckBox;
import com.noxgroup.qahelper.util.AppDataConfig;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @Author: SongRan
 * @Date: 2021/1/15
 * @Desc: 权限管理页面
 */
public class SettingPageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvPermissionWindow;
    private TextView tvPermissionStorage;
    private TextView tvPermissionUsage;
    private TextView tvPermissionNotification;
    private TextView tvPermissionAccessibility;
    private TextView tvAppManage;
    private PermissionHelper permissionHelper;
    private String packageName;
    private RecyclerView recyclerView;
    private CustomerCheckBox checkbox;
    private TextView tvTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_permission);
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> appMap = AppDataConfig.getAppData();
        List<String> list = new ArrayList<>(appMap.keySet());
        AppAdapter adapter = new AppAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.INVISIBLE);
                }
            }
        });
        adapter.setOnClickListener(new AppAdapter.OnClickListener() {
            @Override
            public boolean onClick(int position) {
                packageName = appMap.get(list.get(position));
                checkbox.setText(list.get(position));
                checkbox.setChecked(false);
                return false;
            }
        });
        packageName = getPackageName();
        permissionHelper = new PermissionHelper(new WeakReference<>(this));
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.setting);
        }
        tvPermissionStorage = findViewById(R.id.tv_permission_storage);
        tvPermissionWindow = findViewById(R.id.tv_permission_window);
        tvPermissionUsage = findViewById(R.id.tv_permission_usage);
        tvPermissionNotification = findViewById(R.id.tv_permission_notification);
        tvPermissionAccessibility = findViewById(R.id.tv_permission_accessibility);
        tvAppManage = findViewById(R.id.tv_app_manage);
        checkbox = findViewById(R.id.checkbox);
        recyclerView = findViewById(R.id.recycler_view);
        tvTime = findViewById(R.id.tv_time);
        checkbox.setOnClickListener(this);
        tvPermissionStorage.setOnClickListener(this);
        tvPermissionWindow.setOnClickListener(this);
        tvPermissionUsage.setOnClickListener(this);
        tvPermissionNotification.setOnClickListener(this);
        tvPermissionAccessibility.setOnClickListener(this);
        tvAppManage.setOnClickListener(this);
        tvTime.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_permission_storage:
                //跳转悬浮窗权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.STORAGE, packageName);
                break;
            case R.id.tv_permission_window:
                //跳转悬浮窗权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.WINDOW, packageName);
                break;
            case R.id.tv_permission_usage:
                //跳转使用情况访问权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.USAGE, packageName);
                break;
            case R.id.tv_permission_notification:
                //跳转通知访问权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.NOTIFICATION, packageName);
                break;
            case R.id.tv_permission_accessibility:
                //跳转辅助权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.ACCESSIBILITY, packageName);
                break;
            case R.id.tv_app_manage:
                //跳转辅助权限页面
                permissionHelper.gotoSettingPage(PermissionHelper.DETAIL, packageName);
                break;
            case R.id.tv_time:
                //跳转时间调整页面
                startActivity(SettingPageUtil.getTimeDateIntent());
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
        }
        return super.onOptionsItemSelected(item);
    }

}
