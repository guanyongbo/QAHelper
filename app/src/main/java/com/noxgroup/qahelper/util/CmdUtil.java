package com.noxgroup.qahelper.util;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @Author: SongRan
 * @Date: 2021/1/18
 * @Desc: 命令执行工具类
 * 需要手动授予"GRANT_RUNTIME_PERMISSIONS"权限，否则不生效。
 * 授权方法：连接手机，adb 执行"adb shell pm grant com.noxgroup.qahelper android.permission.GRANT_RUNTIME_PERMISSIONS"
 */
public class CmdUtil {
    public static final String CMD_CLEAN_CACHE = "pm clear ";//需要packageName
    public static final String CMD_START_APP = "am start -n ";//需要被启动页面的包名和activity路径
    public static final String CMD_STOP_APP = "am force-stop ";//需要packageName
    public static final String CMD_UNINSTALL_APP = "uninstall ";//需要packageName
    public static final String CMD_LIST_PACKAGE = "pm list packages";

    public static boolean executeCmd(String cmd) {
        try {
            Process process = Runtime.getRuntime().exec(cmd);
            BufferedReader ie = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            BufferedReader in = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder data = new StringBuilder();
            StringBuilder error = new StringBuilder();
            String errorMessage = "";
            while ((errorMessage = ie.readLine()) != null
                    && !error.toString().equals("null")) {
                error.append(errorMessage).append("\n");
            }

            String line = "";
            while ((line = in.readLine()) != null
                    && !line.equals("null")) {
                data.append(line).append("\n");
            }
            Log.e(QALogUtil.TAG_QA, "CmdUtil executeCmd: data: " + data);
            Log.e(QALogUtil.TAG_QA, "CmdUtil executeCmd: error: " + error);
            if (!TextUtils.isEmpty(error.toString())) {
                return false;
            }
        } catch (IOException e) {
            Log.e(QALogUtil.TAG_QA, "CmdUtil executeCmd: 报错日志如下:");
            e.printStackTrace();
            return false;
        }
        return true;
    }

}

