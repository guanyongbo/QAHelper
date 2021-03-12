package com.noxgroup.qahelper.appmanage;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.util.CmdUtil;

/**
 * @Author: SongRan
 * @Date: 2021/1/18
 * @Desc:
 */
public class ManageActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvUninstall;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage);
        initView();
    }

    private void initView() {
        tvUninstall = findViewById(R.id.tv_uninstall);
        tvUninstall.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_uninstall:
                //卸载应用
                CmdUtil.executeCmd(CmdUtil.CMD_CLEAN_CACHE + "com.noxgroup.app.security");
                break;
        }
    }
}
