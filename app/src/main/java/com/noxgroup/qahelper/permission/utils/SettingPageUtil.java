package com.noxgroup.qahelper.permission.utils;

import android.content.Intent;
import android.provider.Settings;

/**
 * @Author: SongRan
 * @Date: 2021/1/16
 * @Desc: 设置页跳转
 */
public class SettingPageUtil {
    public static Intent getTimeDateIntent() {
        Intent intent = new Intent(Settings.ACTION_DATE_SETTINGS);
        return intent;
    }
}
