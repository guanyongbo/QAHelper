package com.noxgroup.qahelper.ftp;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.base.BaseFragment;
import com.noxgroup.qahelper.ftp.adapter.FragmentViewPagerAdapter;
import com.noxgroup.qahelper.ftp.fragment.ApkManageFragment;
import com.noxgroup.qahelper.ftp.fragment.FtpManageFragment;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/1/19
 * @Desc:
 */
public class FTPActivity extends AppCompatActivity {
    private TabLayout tablayout;
    private ViewPager viewPager;
    private FtpManageFragment ftpManageFragment;
    private ApkManageFragment apkManageFragment;
    private List<BaseFragment> fragmentList = new ArrayList<>();
    private List<String> titleList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ftp);
        initView();
        initData();
    }

    private void initData() {
        titleList.add(getString(R.string.apk));
        titleList.add(getString(R.string.ftp));
        apkManageFragment = new ApkManageFragment();
        ftpManageFragment = new FtpManageFragment();
        fragmentList.add(ftpManageFragment);
        fragmentList.add(apkManageFragment);
        FragmentViewPagerAdapter fragmentViewPagerAdapter = new FragmentViewPagerAdapter(getSupportFragmentManager(), fragmentList, titleList);
        viewPager.setAdapter(fragmentViewPagerAdapter);
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.app_download);
        }
        tablayout = findViewById(R.id.tab_layout);
        viewPager = findViewById(R.id.view_pager);
        tablayout.setupWithViewPager(viewPager);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1001) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //申请成功
                ftpManageFragment.download();
            } else {
                ToastUtils.showShort(R.string.auth_fail);
            }
        }
    }

    @Override
    public void onBackPressed() {
        //当前选择的是FTP管理页面检查返回逻辑
        if (ftpManageFragment.checkBack()) {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_ftp, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.menu_download:
                //跳转下载历史页面
                startActivity(new Intent(FTPActivity.this, DownloadHistoryActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
