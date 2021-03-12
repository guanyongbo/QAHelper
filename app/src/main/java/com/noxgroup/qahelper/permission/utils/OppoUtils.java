package com.noxgroup.qahelper.permission.utils;

import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @author huangjian
 * @create 2019/6/3
 * @Description
 */
public class OppoUtils {

    public static final ComponentName[] ROM_OPPO = {
            //国外
            new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity"),
            //国内
            new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.sysfloatwindow.FloatWindowListActivity"),
            new ComponentName("com.oppo.safe", "com.oppo.safe.permission.floatwindow.FloatWindowListActivity"),
            new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.startup.StartupAppListActivity"),
            new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.startupapp.StartupAppListActivity"),
            new ComponentName("com.oppo.safe", "com.oppo.safe.permission.startup.StartupAppListActivity")
    };

    public static boolean checkIsOppoRom() {
        //https://github.com/zhaozepeng/FloatWindowPermission/pull/26
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("oppo");
    }

    public static boolean checkFloatWindowPermission(Context context) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            return checkOp(context, 24); //OP_SYSTEM_ALERT_WINDOW = 24;
        }
        return true;
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean checkOp(Context context, int op) {
        final int version = Build.VERSION.SDK_INT;
        if (version >= 19) {
            AppOpsManager manager = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
            try {
                Class clazz = AppOpsManager.class;
                Method method = clazz.getDeclaredMethod("checkOp", int.class, int.class, String.class);
                return AppOpsManager.MODE_ALLOWED == (int) method.invoke(manager, op, Binder.getCallingUid(), context.getPackageName());
            } catch (Exception e) {
            }
        } else {
        }
        return false;
    }


    public static Intent getApplyOppoPermissionIntent(Context context) {
        Intent intent = new Intent();
        for (int i = 0; i < ROM_OPPO.length; i++) {
            try {
                intent.setComponent(ROM_OPPO[i]);
                if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                    return intent;
                }
            } catch (Exception e) {
            }
        }
        return getCommonPermissionIntent(context);
    }

    /**
     * 尝试另外的方式打开
     *
     * @param context
     * @return
     */
    public static Intent getCommonPermissionIntent(Context context) {
        try {
            Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                return intent;
            }
        } catch (Exception e) {
        }
        return null;
    }
}
