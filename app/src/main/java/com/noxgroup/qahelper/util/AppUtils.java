package com.noxgroup.qahelper.util;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

import com.noxgroup.qahelper.util.toast.ToastUtils;

import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/1/18
 * @Desc:
 */
public class AppUtils {
    public static void go2Browser(String url) {
        try {
            Uri uri = Uri.parse(url);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.setData(uri);
            if (intent.resolveActivity(Utils.getApp().getPackageManager()) != null) { //有浏览器
                PackageManager pm = Utils.getApp().getPackageManager();

                List<ResolveInfo> activities = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
                if (null != activities && activities.size() > 0) {
                    intent.setPackage(activities.get(0).activityInfo.packageName);
                    Utils.getApp().startActivity(intent);
                }
            } else {
                ToastUtils.showShort("No Browser Error");
            }
        } catch (Exception e) {
        }
    }
}
