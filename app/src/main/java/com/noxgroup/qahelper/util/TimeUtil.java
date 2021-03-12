package com.noxgroup.qahelper.util;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: SongRan
 * @Date: 2021/1/20
 * @Desc:
 */
public class TimeUtil {
    public static String getCurrentTimeHms() {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date(System.currentTimeMillis()));
    }

    public static String getTimeHms(long timeMillis) {
        Log.e("SRLog", "TimeUtil getTimeHms: timeMillis: " + timeMillis);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(new Date(timeMillis));
    }
}
