package com.noxgroup.qahelper.util;

/**
 * @Author: SongRan
 * @Date: 2021/2/25
 * @Desc:
 */
public class FileUtil {

    public static String getSizeString(long fileSize) {
        long current = fileSize;
        String size = "";
        String unit = "";
        if (current < 0) {
            current = 0;
        }
        if (current < 1024) {
            size = String.valueOf(current);
            unit = "B";
        } else if (current < 1048576) {
            double d = (double) current / (double) 1024;
            size = String.format("%.1f", d);
            unit = "KB";
        } else if (current < 1073741824) {
            double d = (double) current / (double) 1048576;
            size = String.format("%.1f", d);
            unit = "MB";
        } else {
            double d = (double) current / (double) 1073741824;
            size = String.format("%.1f", d);
            unit = "GB";
        }
        return size + unit;
    }
}
