/*
 * Copyright (C) 2016 Facishare Technology Co., Ltd. All Rights Reserved.
 */
package com.noxgroup.qahelper.permission.utils;

import android.os.Build;
import android.text.TextUtils;

import androidx.annotation.Keep;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Locale;
import java.util.concurrent.ConcurrentHashMap;

public class RomUtils {
    private static final String TAG = "RomUtils";

    /**
     * 获取 emui 版本号
     *
     * @return
     */
    public static double getEmuiVersion() {
        try {
            String emuiVersion = getSystemProperty("ro.build.version.emui");
            String version = emuiVersion.substring(emuiVersion.indexOf("_") + 1);
            return Double.parseDouble(version);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 4.0;
    }

    /**
     * 获取小米 rom 版本号，获取失败返回 -1
     *
     * @return miui rom version code, if fail , return -1
     */
    public static int getMiuiVersion() {
        String version = getSystemProperty("ro.miui.ui.version.name");
        if (version != null) {
            try {
                return Integer.parseInt(version.substring(1));
            } catch (Exception e) {
            }
        }
        return -1;
    }


    public static ConcurrentHashMap<String, String> propMap = new ConcurrentHashMap<>();

    @Keep
    public static String getSystemProperty(String propName) {
        if (TextUtils.isEmpty(propName)) {
            return "";
        }
        if (propMap != null && propMap.containsKey(propName)) {
            return propMap.get(propName);
        }
        String line;
        BufferedReader input = null;
        try {
            Process p = Runtime.getRuntime().exec("getprop " + propName);
            input = new BufferedReader(new InputStreamReader(p.getInputStream()), 1024);
            line = input.readLine();
            input.close();
        } catch (Exception ex) {
            line = "";
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (Exception e) {
                }
            }
        }
        if (line == null) {
            line = "";
        }
        if (propMap == null) {
            propMap = new ConcurrentHashMap<>();
        }
        propMap.put(propName, line);
        return line;
    }

    public static boolean checkIsHuaweiRom() {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("huawei");
    }

    /**
     * check if is miui ROM
     */
    public static boolean checkIsMiuiRom() {
        return !TextUtils.isEmpty(getSystemProperty("ro.miui.ui.version.name"));
    }

    public static boolean checkIsMeizuRom() {
        //return Build.MANUFACTURER.contains("Meizu");
        String meizuFlymeOSFlag = getSystemProperty("ro.build.display.id");
        if (TextUtils.isEmpty(meizuFlymeOSFlag)) {
            return false;
        } else if (meizuFlymeOSFlag.toLowerCase(Locale.ENGLISH).contains("flyme")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean checkIs360Rom() {
        //fix issue https://github.com/zhaozepeng/FloatWindowPermission/issues/9
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("qiku")
                || Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("360");
    }

    public static boolean checkIsOppoRom() {
        //https://github.com/zhaozepeng/FloatWindowPermission/pull/26
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("oppo");
    }

    public static boolean checkIsLGForbiddenRom() {
        //LGE LM-G710,Android 8.0遮挡不住
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("lge") && Build.MODEL.toLowerCase(Locale.ENGLISH).contains("lm-g") && Build.VERSION.SDK_INT == Build.VERSION_CODES.O;
    }

    public static boolean checkIsVivoRom() {
        //https://github.com/zhaozepeng/FloatWindowPermission/pull/26
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("vivo");
    }

    public static boolean checkIsSonyRom() {
        return Build.MANUFACTURER.toLowerCase(Locale.ENGLISH).contains("sony");
    }

    /**
     * 是否是 6.0 以上版本
     */
    public static boolean isOverMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }
}
