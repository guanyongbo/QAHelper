package com.noxgroup.qahelper.util;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import androidx.annotation.NonNull;

/**
 * @Author: SongRan
 * @Date: 2021/1/22
 * @Desc:
 */
public class AppPackageUtil {
    public static boolean checkAppInstalled(@NonNull String pkgName) {
        if (pkgName.isEmpty()) {
            return false;
        }
        PackageInfo packageInfo;
        try {
            packageInfo = Utils.getApp().getPackageManager().getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            //应用未安装
            packageInfo = null;
            e.printStackTrace();
        }
        return packageInfo != null;
    }
}
