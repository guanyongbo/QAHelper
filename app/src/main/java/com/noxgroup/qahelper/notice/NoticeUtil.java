package com.noxgroup.qahelper.notice;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.widget.RemoteViews;

import androidx.core.app.NotificationCompat;

import com.noxgroup.qahelper.MainActivity;
import com.noxgroup.qahelper.R;
import com.noxgroup.qahelper.language.LanguageActivity;
import com.noxgroup.qahelper.permission.SettingPageActivity;
import com.noxgroup.qahelper.util.Utils;

/**
 * @Author: SongRan
 * @Date: 2021/1/21
 * @Desc:
 */
public class NoticeUtil {
    private static final int notificationId = 877438;
    public static final String NOTICE_TYPE = "type";
    public static final String CHANNEL_ID = "notice_help";
    public static final int NOTICE_TYPE_HOME = 1001;
    public static final int NOTICE_TYPE_SETTING = 1002;
    public static final int NOTICE_TYPE_LANGUAGE = 1003;
    public static final int NOTICE_TYPE_DOWNLOAD = 1004;
    public static final int NOTICE_TYPE_START_APP = 1005;


    /**
     * 展示通知栏
     */
    public static void showNotice() {
        try {
            NotificationCompat.Builder builder = getNoticeBuilder();
            NotificationChannel channel = getNoticeChannel();
            if (builder != null) {
                NotificationManager notificationManager = (NotificationManager) Utils.getApp().getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= 26 && channel != null) {
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(notificationId, builder.build());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static NotificationCompat.Builder getNoticeBuilder() {
        Context context = Utils.getApp();
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(), R.layout.notice_remoteview);
        //主页
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra(NOTICE_TYPE, NOTICE_TYPE_HOME);
        PendingIntent paddingIntent = PendingIntent.getActivity(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_home, paddingIntent);
        //启动应用
        intent.putExtra(NOTICE_TYPE, NOTICE_TYPE_START_APP);
        paddingIntent = PendingIntent.getActivity(context, 2, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_start_app, paddingIntent);
        //设置页
        intent = new Intent(context, SettingPageActivity.class);
        intent.putExtra(NOTICE_TYPE, NOTICE_TYPE_SETTING);
        paddingIntent = PendingIntent.getActivity(context, 3, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_setting, paddingIntent);
        //语言切换
        intent = new Intent(context, LanguageActivity.class);
        intent.putExtra(NOTICE_TYPE, NOTICE_TYPE_LANGUAGE);
        paddingIntent = PendingIntent.getActivity(context, 4, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        remoteViews.setOnClickPendingIntent(R.id.tv_language, paddingIntent);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);
        builder.setSmallIcon(R.mipmap.icon_small)
                .setOngoing(true)
                .setCustomContentView(remoteViews)
                .setSound(null)
                .setAutoCancel(false)
                .setLocalOnly(true)
                .setTimeoutAfter(0);
        try {
            builder.setPriority(NotificationCompat.PRIORITY_MAX);
            return builder;
        } catch (Exception e) {
            e.printStackTrace();
            return builder;
        }
    }

    private static NotificationChannel getNoticeChannel() {
        if (Build.VERSION.SDK_INT < 26) {
            return null;
        }
        NotificationChannel notificationChannel = new NotificationChannel(CHANNEL_ID, Utils.getApp().getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);
        notificationChannel.enableLights(true);
        notificationChannel.setLightColor(-65536);
        notificationChannel.setShowBadge(false);
        notificationChannel.enableVibration(false);//震动不可用
        notificationChannel.setSound(null, null); //设置没有声音
        return notificationChannel;
    }

}
