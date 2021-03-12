package com.noxgroup.qahelper.permission.helper;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.provider.Settings;
import android.util.Log;

import androidx.annotation.NonNull;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.permission.utils.PermissionSettingPage;
import com.noxgroup.qahelper.permission.utils.PermissionUtil;
import com.noxgroup.qahelper.permission.utils.WindowPermissionUtil;
import com.noxgroup.qahelper.util.QALogUtil;
import com.noxgroup.qahelper.util.Utils;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.lang.ref.WeakReference;

/**
 * @Author: SongRan
 * @Date: 2021/1/15
 * @Desc: 权限页面跳转辅助类
 */
public class PermissionHelper {
    public static final int USAGE = 0;
    public static final int NOTIFICATION = 1;
    public static final int WINDOW = 2;
    public static final int ACCESSIBILITY = 3;
    public static final int STORAGE = 4;
    public static final int DETAIL = 5;
    private final Activity activity;

    public PermissionHelper(WeakReference<Activity> activityWeakReference) {
        activity = activityWeakReference.get();
    }

    public void gotoSettingPage(int type, @NonNull String packageName) {
        //检查应用是否安装
        PackageManager manager = Utils.getApp().getPackageManager();
        if (manager.getLaunchIntentForPackage(packageName) == null) {
            ToastUtils.showShort(R.string.app_not_install);
            return;
        }

        Intent intent = null;
        switch (type) {
            case DETAIL:
                intent = PermissionSettingPage.google(packageName);
                break;
            case STORAGE:
                PermissionSettingPage.start(activity, false, packageName);
                break;
            case USAGE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                } else {
                    Log.e(QALogUtil.TAG_QA, "PermissionHelper gotoSettingPage: Android 5.0 无使用情况访问权限");
                }
                break;
            case NOTIFICATION:
                intent = PermissionUtil.getNotificationListenerIntent();
                break;
            case WINDOW:
                intent = WindowPermissionUtil.getWindowPermissionIntent(activity, packageName);
                break;
            case ACCESSIBILITY:
                intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                break;
        }
        try {
            if (intent != null) {
                activity.startActivity(intent);
            }
        } catch (Exception e) {
            if (type == WINDOW) {
                //再试一次跳转悬浮窗权限跳转
                intent = WindowPermissionUtil.commonGetWindowPerIntent(activity, packageName);
                activity.startActivity(intent);
            }
            e.printStackTrace();
        }
    }
}
