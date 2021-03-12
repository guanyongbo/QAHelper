package com.noxgroup.qahelper.permission.utils;

import android.content.Intent;
import android.os.Build;
import android.provider.Settings;

/**
 * @Author: SongRan
 * @Date: 2021/1/15
 * @Desc:
 */
public class PermissionUtil {
    public  static Intent getNotificationListenerIntent() {
        try {
            Intent intent;
            if (Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
                intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
            } else {
                intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
            }
            return intent;
        } catch (Exception e) {
        }
        return null;
    }
}
