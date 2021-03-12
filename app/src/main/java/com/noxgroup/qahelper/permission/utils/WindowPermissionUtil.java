package com.noxgroup.qahelper.permission.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author huangjian
 * @create 2019/6/5
 * @Description
 */
public class WindowPermissionUtil {

    public static Intent getWindowPermissionIntent(Context context,String packageName) {
        if (RomUtils.checkIsVivoRom()) {
            if (ViVoUtils.supportShowFloatWindow()) {
                return commonGetWindowPerIntent(context,packageName);
            } else {
                return ViVoUtils.getApplyVivoPermissionIntent(context);
            }
        } else if (RomUtils.checkIsOppoRom()) {
            return OppoUtils.getApplyOppoPermissionIntent(context);
        } else if (RomUtils.checkIsMeizuRom()) {
            //兼容魅族手机
            try {
                Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                intent.putExtra("packageName", packageName);
                return intent;
            } catch (Exception e) {
                try {
                    // 最新的魅族flyme 6.2.5 用上述方法获取权限失败, 不过又可以用下述方法获取权限了
                    return commonGetWindowPerIntent(context,packageName);
                } catch (Exception eFinal) {
                }
            }
        } else if (Build.VERSION.SDK_INT >= 23) {
            if (RomUtils.checkIsMeizuRom()) {
                try {
                    Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
                    intent.putExtra("packageName", packageName);
                    return intent;
                } catch (Exception e) {
                    try {
                        // 最新的魅族flyme 6.2.5 用上述方法获取权限失败, 不过又可以用下述方法获取权限了
                        return commonGetWindowPerIntent(context,packageName);
                    } catch (Exception eFinal) {
                    }
                }
            } else {
                return commonGetWindowPerIntent(context,packageName);
            }
        }
        return null;
    }

    private static Boolean isForbiddenDeepClean = null;

    /**
     * 禁止进行深度清理，这些机型暂时没有适配
     *
     * @return
     */
    public static boolean isForbiddenDeepClean(@NonNull Context context) {
        try {
            if (isForbiddenDeepClean == null) {
                if (RomUtils.checkIsVivoRom() && !ViVoUtils.supportShowFloatWindow()) {
                    isForbiddenDeepClean = true;
                } else if (RomUtils.checkIsOppoRom()) {
                    //oppo手机暂时无法跳转的
                    isForbiddenDeepClean = OppoUtils.getApplyOppoPermissionIntent(context) == null;
                } else if (RomUtils.checkIsLGForbiddenRom()) {
                    isForbiddenDeepClean = true;
                } else {
                    isForbiddenDeepClean = false;
                }
            }
        } catch (Exception e) {
            return true;
        }
        return isForbiddenDeepClean;
    }

    /**
     * 是否需要判断悬浮窗权限
     *
     * @return
     */
    public static boolean needCheckWindowPermission() {
        return RomUtils.checkIsOppoRom() || RomUtils.checkIsVivoRom() || RomUtils.isOverMarshmallow();
    }


    public static Intent commonGetWindowPerIntent(Context context,String packageName) {
        try {
            Class clazz = Settings.class;
            Field field = clazz.getDeclaredField("ACTION_MANAGE_OVERLAY_PERMISSION");
            Intent intent = new Intent(field.get(null).toString());
            if (!(RomUtils.checkIsHuaweiRom() && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)) {
                //fixbug 8.0+ 华为手机获取悬浮窗权限一直为 false
                intent.setData(Uri.parse("package:" + packageName));
            }
            return intent;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 判断是否有悬浮窗权限
     *
     * @param context
     * @return
     */
    private static boolean checkFloatPermission(Context context,String packageName) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT)
            return true;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            try {
                Class cls = Class.forName("android.content.Context");
                Field declaredField = cls.getDeclaredField("APP_OPS_SERVICE");
                declaredField.setAccessible(true);
                Object obj = declaredField.get(cls);
                if (!(obj instanceof String)) {
                    return false;
                }
                String str2 = (String) obj;
                obj = cls.getMethod("getSystemService", String.class).invoke(context, str2);
                cls = Class.forName("android.app.AppOpsManager");
                Field declaredField2 = cls.getDeclaredField("MODE_ALLOWED");
                declaredField2.setAccessible(true);
                Method checkOp = cls.getMethod("checkOp", Integer.TYPE, Integer.TYPE, String.class);
                int result = (Integer) checkOp.invoke(obj, 24, Binder.getCallingUid(), packageName);
                return result == declaredField2.getInt(cls);
            } catch (Exception e) {
                return false;
            }
        } else {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                AppOpsManager appOpsMgr = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
//                if (appOpsMgr == null)
//                    return false;
//                int mode = appOpsMgr.checkOpNoThrow("android:system_alert_window", android.os.Process.myUid(), context
//                        .getPackageName());
//                return mode == AppOpsManager.MODE_ALLOWED || mode == AppOpsManager.MODE_IGNORED;
//            } else {
//            }
            return Settings.canDrawOverlays(context);
        }
    }


    private static boolean huaweiPermissionCheck(Context context) {
        return HuaweiUtils.checkFloatWindowPermission(context);
    }

    private static boolean miuiPermissionCheck(Context context) {
        return MiuiUtils.checkFloatWindowPermission(context);
    }

    private static boolean meizuPermissionCheck(Context context) {
        return MeizuUtils.checkFloatWindowPermission(context);
    }

    private static boolean qikuPermissionCheck(Context context) {
        return QikuUtils.checkFloatWindowPermission(context);
    }

    private static boolean oppoROMPermissionCheck(Context context) {
        return OppoUtils.checkFloatWindowPermission(context);
    }

    private static boolean commonROMPermissionCheck(Context context) {
        //最新发现魅族6.0的系统这种方式不好用，天杀的，只有你是奇葩，没办法，单独适配一下
        if (RomUtils.checkIsMeizuRom()) {
            return meizuPermissionCheck(context);
        } else {
            Boolean result = true;
            if (Build.VERSION.SDK_INT >= 23) {
                try {
                    Class clazz = Settings.class;
                    Method canDrawOverlays = clazz.getDeclaredMethod("canDrawOverlays", Context.class);
                    result = (Boolean) canDrawOverlays.invoke(null, context);
                } catch (Exception e) {
                }
            }
            return result;
        }
    }

    public static boolean needWaitCheckPermission() {
        //fixbug sony :https://stackoverflow.com/questions/46173460/why-in-android-o-method-settings-candrawoverlays-returns-false-when-user-has
        return RomUtils.checkIsSonyRom() && Build.VERSION.SDK_INT == Build.VERSION_CODES.O;
    }

}
