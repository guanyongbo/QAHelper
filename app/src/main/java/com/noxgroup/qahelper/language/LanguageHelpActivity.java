package com.noxgroup.qahelper.language;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.permission.widget.BackTextView;
import com.noxgroup.qahelper.util.toast.ToastUtils;

/**
 * @Author: SongRan
 * @Date: 2021/1/22
 * @Desc:
 */
public class LanguageHelpActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView tvCopy;
    private TextView tvAdbContent;
    private BackTextView backTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_help);
        initView();
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.help);
        }
        tvCopy = findViewById(R.id.tv_copy);
        tvAdbContent = findViewById(R.id.tv_adb_content);
        tvCopy.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_copy:
                //复制adb命令到剪切板
                copyContent();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void copyContent() {
        try {
            ClipboardManager clipboardManager = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText("adb", tvAdbContent.getText().toString().trim());
            clipboardManager.setPrimaryClip(clipData);
        } catch (Exception e) {
        }
        ToastUtils.showShort(R.string.copy_suc);
    }
}
