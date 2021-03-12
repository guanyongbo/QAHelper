package com.noxgroup.qahelper.language;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.language.adapter.LanguageAdapter;
import com.noxgroup.qahelper.language.util.LanguageUtil;
import com.noxgroup.qahelper.util.DialogUtil;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: SongRan
 * @Date: 2021/1/16
 * @Desc:
 */
public class LanguageActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private String language;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language);
        initView();
        initData();
    }

    private void initData() {
        Map<String, String> languageMap = new LinkedHashMap<>();
        languageMap.put("中文_zh", "zh");
        languageMap.put("英文_en", "en");
        languageMap.put("阿语_ar", "ar");
        languageMap.put("西语_es", "es");
        languageMap.put("法语_fr", "fr");
        languageMap.put("印尼语_in", "in");
        languageMap.put("印地语_hi", "hi");
        languageMap.put("日语_ja", "ja");
        languageMap.put("韩语_ko", "ko");
        languageMap.put("葡语_pt", "pt");
        languageMap.put("泰语_th", "th");
        languageMap.put("土耳其语_tr", "tr");
        languageMap.put("越南语_vn", "vn");
        languageMap.put("繁体_tw", "tw");
        languageMap.put("捷克语_cs", "cs");
        languageMap.put("德语_de", "de");
        languageMap.put("俄语_ru", "ru");
        languageMap.put("瑞典语_sv", "sv");
        List<String> list = new ArrayList<>(languageMap.keySet());
        LanguageAdapter adapter = new LanguageAdapter(this, list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnClickListener(new LanguageAdapter.OnClickListener() {
            @Override
            public boolean onClick(int position) {
                language = languageMap.get(list.get(position));
                //切换系统语言
                changeLanguage(language);
                return false;
            }
        });
    }

    private void changeLanguage(String language) {
        boolean result = LanguageUtil.setLanguage(language);
        if (!result) {
            DialogUtil.showHelpDialog(new WeakReference<>(this), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转帮助页面
                    startActivity(new Intent(LanguageActivity.this, LanguageHelpActivity.class));
                }
            });
        }
    }

    private void initView() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(false);
            actionBar.setTitle(R.string.language_change);
        }
        recyclerView = findViewById(R.id.recycler_view);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toobar_language, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return false;
            case R.id.menu_language:
                //跳转下载历史页面
                startActivity(new Intent(LanguageActivity.this, LanguageHelpActivity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
