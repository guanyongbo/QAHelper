package com.noxgroup.qahelper.permission.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;

/**
 * @author huangjian
 * @create 2019/12/23
 * @Description
 */
public class BgStartActPermissionUtil {

    public static boolean hasBgStartActivityPermission(Context context) {
        if (RomUtils.checkIsVivoRom()) {
            return ViVoUtils.hasBgStartActivityPermission(context);
        } else if (RomUtils.checkIsMiuiRom()) {
            return MiuiUtils.hasBgStartActivityPermission(context);
        } else {
            return true;
        }
    }

    public static Intent getPermissionIntent(Context context) {
        if (RomUtils.checkIsVivoRom()) {
            return ViVoUtils.getApplyVivoPermissionIntent(context);
        } else if (RomUtils.checkIsMiuiRom()) {
            return MiuiUtils.getApplyMiuiPermissionIntent(context);
        } else {
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
            return intent;
        }
    }
}
