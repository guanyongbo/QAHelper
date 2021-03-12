package com.noxgroup.qahelper.permission.utils;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import java.util.Locale;

/**
 * @author huangjian
 * @create 2018/10/30
 * @Description
 */
public class PermissionSettingPage {
    private static final String MARK = Build.MANUFACTURER.toLowerCase(Locale.ENGLISH);

    /**
     * 跳转到应用权限设置页面
     *
     * @param context 上下文对象
     * @param newTask 是否使用新的任务栈启动
     */
    public static void start(Activity context, boolean newTask, String packageName) {
        Intent intent = null;
        intent = google(packageName);
        if (intent == null || !hasIntent(context, intent)) {
            if (MARK.contains("huawei")) {
                intent = huawei(context);
            } else if (MARK.contains("xiaomi")) {
                intent = xiaomi(context, packageName);
            } else if (MARK.contains("oppo")) {
                intent = oppo(context, packageName);
            } else if (MARK.contains("vivo")) {
                intent = vivo(context, packageName);
            } else if (MARK.contains("meizu")) {
                intent = meizu(packageName);
            }
        }

        if (newTask) {
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        try {
            context.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Intent google(String packageName) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.fromParts("package", packageName, null));
        return intent;
    }

    private static Intent huawei(Context context) {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.systemmanager.addviewmonitor.AddViewMonitorActivity"));
        if (hasIntent(context, intent)) return intent;
        intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.notificationmanager.ui.NotificationManagmentActivity"));
        return intent;
    }

    private static Intent xiaomi(Context context, String packageName) {
        Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
        intent.putExtra("extra_pkgname", packageName);
        if (hasIntent(context, intent)) return intent;

        intent.setPackage("com.miui.securitycenter");
        if (hasIntent(context, intent)) return intent;

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
        if (hasIntent(context, intent)) return intent;

        intent.setClassName("com.miui.securitycenter", "com.miui.permcenter.permissions.PermissionsEditorActivity");
        return intent;
    }

    private static Intent oppo(Context context, String packageName) {
        Intent intent = new Intent();
        intent.putExtra("packageName", packageName);
        intent.setClassName("com.color.safecenter", "com.color.safecenter.permission.floatwindow.FloatWindowListActivity");
        if (hasIntent(context, intent)) return intent;

        intent.setClassName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity");
        if (hasIntent(context, intent)) return intent;

        intent.setClassName("com.oppo.safe", "com.oppo.safe.permission.PermissionAppListActivity");
        return intent;
    }

    private static Intent vivo(Context context, String packageName) {
        Intent intent = new Intent();
        intent.setClassName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.FloatWindowManager");
        intent.putExtra("packagename", packageName);
        if (hasIntent(context, intent)) return intent;

        intent.setComponent(new ComponentName("com.iqoo.secure", "com.iqoo.secure.safeguard.SoftPermissionDetailActivity"));
        return intent;
    }

    private static Intent meizu(String packageName) {
        Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
        intent.putExtra("packageName", packageName);
        intent.setComponent(new ComponentName("com.meizu.safe", "com.meizu.safe.security.AppSecActivity"));
        return intent;
    }

    private static boolean hasIntent(Context context, Intent intent) {
        return context.getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY).size() > 0;
    }
}
