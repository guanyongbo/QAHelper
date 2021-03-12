package com.noxgroup.qahelper.language.util;

import android.app.backup.BackupManager;
import android.content.res.Configuration;

import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.util.CmdUtil;
import com.noxgroup.qahelper.util.Utils;
import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.lang.reflect.Method;
import java.util.Locale;

/**
 * @Author: SongRan
 * @Date: 2021/1/16
 * @Desc: 修改系统语言工具类
 * 需要手动授予"CHANGE_CONFIGURATION"权限，否则不生效。
 * 授权方法：连接手机，adb 执行"adb shell pm grant com.noxgroup.qahelper android.permission.CHANGE_CONFIGURATION"
 * 无权限报错日志：
 * java.lang.reflect.InvocationTargetException
 * at java.lang.reflect.Method.invoke(Native Method)
 * at com.noxgroup.qahelper.language.util.LanguageUtil.setLanguage(LanguageUtil.java:36)
 * at com.noxgroup.qahelper.language.LanguageActivity.changeLanguage(LanguageActivity.java:58)
 * at com.noxgroup.qahelper.language.LanguageActivity.access$100(LanguageActivity.java:22)
 * at com.noxgroup.qahelper.language.LanguageActivity$1.onClick(LanguageActivity.java:51)
 * at com.noxgroup.qahelper.permission.adapter.AppAdapter$1.onClick(AppAdapter.java:41)
 * at android.view.View.performClick(View.java:7201)
 * at android.view.View.performClickInternal(View.java:7170)
 * at android.view.View.access$3500(View.java:806)
 * at android.view.View$PerformClick.run(View.java:27562)
 * at android.os.Handler.handleCallback(Handler.java:883)
 * at android.os.Handler.dispatchMessage(Handler.java:100)
 * at android.os.Looper.loop(Looper.java:214)
 * at android.app.ActivityThread.main(ActivityThread.java:7682)
 * at java.lang.reflect.Method.invoke(Native Method)
 * at com.android.internal.os.RuntimeInit$MethodAndArgsCaller.run(RuntimeInit.java:516)
 * at com.android.internal.os.ZygoteInit.main(ZygoteInit.java:950)
 * Caused by: java.lang.SecurityException: Permission Denial: updateConfiguration() from pid=26791, uid=10476 requires android.permission.CHANGE_CONFIGURATION
 */
public class LanguageUtil {

    public static boolean setLanguage(String language) {
        Locale locale = new Locale(language);
        try {
            Object objIActMag;
            Class clzIActMag = Class.forName("android.app.IActivityManager");
            Class clzActMagNative = Class.forName("android.app.ActivityManagerNative");
            Method mtdActMagNative$getDefault = clzActMagNative.getDeclaredMethod("getDefault");
            objIActMag = mtdActMagNative$getDefault.invoke(clzActMagNative);
            Method mtdIActMag$getConfiguration = clzIActMag.getDeclaredMethod("getConfiguration");
            Configuration config = (Configuration) mtdIActMag$getConfiguration
                    .invoke(objIActMag);
            config.locale = locale;
            Class clzConfig = Class
                    .forName("android.content.res.Configuration");
            java.lang.reflect.Field userSetLocale = clzConfig
                    .getField("userSetLocale");
            userSetLocale.set(config, true);
            Class[] clzParams = {Configuration.class};
            Method mtdIActMag$updateConfiguration = clzIActMag.getDeclaredMethod("updateConfiguration", clzParams);
            mtdIActMag$updateConfiguration.invoke(objIActMag, config);
            BackupManager.dataChanged("com.android.providers.settings");
        } catch (Exception e) {
            e.printStackTrace();
            //需要"android.permission.GRANT_RUNTIME_PERMISSIONS"权限，三方应用无此权限(只有系统应用可以拥有此权限)
            //报错信息如下：
            /**
             *     java.lang.SecurityException: grantRuntimePermission: Neither user 10841 nor current process has android.permission.GRANT_RUNTIME_PERMISSIONS.
             *         at android.app.ContextImpl.enforce(ContextImpl.java:1930)
             *         at android.app.ContextImpl.enforceCallingOrSelfPermission(ContextImpl.java:1958)
             *         at com.android.server.pm.permission.PermissionManagerService.grantRuntimePermission(PermissionManagerService.java:2172)
             *         at com.android.server.pm.permission.PermissionManagerService.access$900(PermissionManagerService.java:134)
             *         at com.android.server.pm.permission.PermissionManagerService$PermissionManagerServiceInternalImpl.grantRuntimePermission(PermissionManagerService.java:3155)
             *         at com.android.server.pm.PackageManagerService.grantRuntimePermission(PackageManagerService.java:6035)
             *         at com.android.server.pm.PackageManagerShellCommand.runGrantRevokePermission(PackageManagerShellCommand.java:1976)
             *         at com.android.server.pm.PackageManagerShellCommand.onCommand(PackageManagerShellCommand.java:235)
             *         at android.os.ShellCommand.exec(ShellCommand.java:104)
             *         at com.android.server.pm.PackageManagerService.onShellCommand(PackageManagerService.java:22640)
             *         at android.os.Binder.shellCommand(Binder.java:892)
             *         at android.os.Binder.onTransact(Binder.java:776)
             *         at android.content.pm.IPackageManager$Stub.onTransact(IPackageManager.java:4883)
             *         at com.android.server.pm.PackageManagerService.onTransact(PackageManagerService.java:4249)
             *         at android.os.Binder.execTransactInternal(Binder.java:1032)
             *         at android.os.Binder.execTransact(Binder.java:1005)
             */
            boolean executeResult = CmdUtil.executeCmd("pm grant com.noxgroup.qahelper android.permission.CHANGE_CONFIGURATION");
            if (!executeResult) {
                return false;
            }
        }
        ToastUtils.showShort(Utils.getApp().getString(R.string.changed, language));
        return true;
    }
}
