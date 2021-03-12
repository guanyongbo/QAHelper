package com.noxgroup.qahelper;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.noxgroup.qahelper.ftp.FTPActivity;
import com.noxgroup.qahelper.language.LanguageActivity;
import com.noxgroup.qahelper.notice.NoticeUtil;
import com.noxgroup.qahelper.permission.SettingPageActivity;
import com.noxgroup.qahelper.util.AppDataConfig;
import com.noxgroup.qahelper.util.AppPackageUtil;
import com.noxgroup.qahelper.util.DialogUtil;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvSettingPage;
    private TextView tvAppDownload;
    private TextView tvLanguage;
    private TextView tvAppStart;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        getIntentExtra(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        getIntentExtra(intent);
    }

    private void getIntentExtra(Intent intent) {
        if (intent != null && intent.hasExtra(NoticeUtil.NOTICE_TYPE)) {
            Log.e("SRLog", "MainActivity getIntentExtra: " + intent.getIntExtra(NoticeUtil.NOTICE_TYPE, 0));
            if (intent.getIntExtra(NoticeUtil.NOTICE_TYPE, 0) == NoticeUtil.NOTICE_TYPE_START_APP) {
                selectApp();
            }
        }

    }

    private void initView() {
        tvSettingPage = findViewById(R.id.tv_setting_page);
        tvAppDownload = findViewById(R.id.tv_app_download);
        tvLanguage = findViewById(R.id.tv_language);
        tvAppStart = findViewById(R.id.tv_app_start);
        tvSettingPage.setOnClickListener(this);
        tvLanguage.setOnClickListener(this);
        tvAppDownload.setOnClickListener(this);
        tvAppStart.setOnClickListener(this);
        NoticeUtil.showNotice();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_setting_page:
                //跳转权限页面
                startActivity(new Intent(this, SettingPageActivity.class));
                break;
            case R.id.tv_language:
                //跳转时间调整页面
                startActivity(new Intent(this, LanguageActivity.class));
                break;
            case R.id.tv_app_download:
                //跳转FTP管理页面
                startActivity(new Intent(this, FTPActivity.class));
                break;
            case R.id.tv_app_start:
                selectApp();
                break;
        }
    }

    private void selectApp() {
        //选择应用
        DialogUtil.showAppDialog(new WeakReference<>(this), new DialogUtil.AppDataListener() {
            @Override
            public void onSelect(String packageName) {
                //启动应用
                startApp(packageName);
            }
        });
    }

    private void startApp(String packageName) {
        //检查应用是否安装
        if (!AppPackageUtil.checkAppInstalled(packageName)) {
            ToastUtils.showShort(R.string.app_not_install);
        } else {
            //启动应用
            String appPath = AppDataConfig.getAppFullPath(packageName);
            if (!TextUtils.isEmpty(appPath)) {
                Intent intent = new Intent();
                //包名 包名+类名（全路径）
                intent.setClassName(packageName, appPath);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.showShort(R.string.start_fail);
                }
            }
        }
    }
}