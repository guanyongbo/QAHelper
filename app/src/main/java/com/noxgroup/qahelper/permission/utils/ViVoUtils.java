package com.noxgroup.qahelper.permission.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;

/**
 * @author huangjian
 * @create 2018/11/17
 * @Description
 */
public class ViVoUtils {

    public static final ComponentName[] ROM_VIVO = {
            //国外
//            new ComponentName("com.coloros.safecenter", "com.coloros.safecenter.permission.floatwindow.FloatWindowListActivity"),
            //国内
            ComponentName.unflattenFromString("com.vivo.permissionmanager/.activity.SoftPermissionDetailActivity"),
            //无效，需要在应用内设置权限开启
//            new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.FloatWindowManagerActivity"),

            ComponentName.unflattenFromString("com.iqoo.secure/.safeguard.SoftPermissionDetailActivity"),
            new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"),
            new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"),
            new ComponentName("com.iqoo.secure", "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"),

            new ComponentName("com.vivo.permissionmanager", "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"),
    };

    public static Intent getApplyVivoPermissionIntent(Context context) {
        Intent intent = new Intent();
        intent.putExtra("packagename", context.getPackageName());
        for (int i = 0; i < ROM_VIVO.length; i++) {
            try {
                intent.setComponent(ROM_VIVO[i]);
                if (context.getPackageManager().resolveActivity(intent, 0) != null) {
                    return intent;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Boolean supportShow = null;//做缓存

    public static boolean supportShowFloatWindow() {
        if (supportShow != null) {
            return supportShow;
        }
        try {
            //vivo 根据 Rom 版本进行判断。>= 4.0。（adb shell getprop 查看属性信息）
            String romProperty = RomUtils.getSystemProperty("ro.vivo.os.version");
            if (!TextUtils.isEmpty(romProperty)) {
                String[] split = romProperty.split("\\.");
                if (split.length > 0) {
                    if (Integer.parseInt(split[0]) >= 4) {
                        supportShow = true;
                        return supportShow;
                    }
                }
            }
        } catch (Exception e) {
        }
        supportShow = false;
        return supportShow;
    }


    /**
     * 获取是否有后台弹出界面权限：
     * 默认有权限
     *
     * @param context
     * @return
     */
    public static boolean hasBgStartActivityPermission(Context context) {
        boolean result = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            String packageName = context.getPackageName();
            Uri uri2 = Uri.parse("content://com.vivo.permissionmanager.provider.permission/start_bg_activity");
            String selection = "pkgname = ?";
            String[] selectionArgs = new String[]{packageName};
            Cursor cursor = null;
            try {
                cursor = context
                        .getContentResolver()
                        .query(uri2, null, selection, selectionArgs, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int currentmode = cursor.getInt(cursor.getColumnIndex("currentstate"));
                        result = currentmode == 0;
                    }
                }
            } catch (Throwable throwable) {
            } finally {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return result;
    }


}
