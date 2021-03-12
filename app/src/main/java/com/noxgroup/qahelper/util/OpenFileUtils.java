package com.noxgroup.qahelper.util;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.core.content.FileProvider;

import com.noxgroup.qahelper.BuildConfig;

import java.io.File;


/**
 * @author huangjian
 * @create 2019/2/22
 * @Description
 */
public class OpenFileUtils {
    public static void openFile(Context context, String filePath) {
        try {
            File file = new File(filePath);
            if (file == null || !file.exists()) return;
            Intent intent;
            /* 取得扩展名 */
            String end = file.getName().substring(file.getName().lastIndexOf(".") + 1, file.getName().length()).toLowerCase();
            /* 依扩展名的类型决定MimeType */
            if (end.equals("m4a") || end.equals("mp3") || end.equals("mid") ||
                    end.equals("xmf") || end.equals("ogg") || end.equals("wav")
                    || end.equals("amr")) {
                intent = getAudioFileIntent(context, filePath);
            } else if (end.equals("3gp") || end.equals("mp4")) {
                intent = getVideoFileIntent(context, filePath);
            } else if (end.equals("jpg") || end.equals("gif") || end.equals("png") ||
                    end.equals("jpeg") || end.equals("bmp")) {
                intent = getImageFileIntent(context, filePath);
            } else if (end.equals("apk")) {
                intent = getApkFileIntent(context, filePath);
            } else if (end.equals("ppt")) {
                intent = getPptFileIntent(context, filePath);
            } else if (end.equals("xls")) {
                intent = getExcelFileIntent(context, filePath);
            } else if (end.equals("doc") || end.equals(("docx"))) {
                intent = getWordFileIntent(context, filePath);
            } else if (end.equals("pdf")) {
                intent = getPdfFileIntent(context, filePath);
            } else if (end.equals("chm")) {
                intent = getChmFileIntent(context, filePath);
            } else if (end.equals("txt")) {
                intent = getTextFileIntent(context, filePath);
            } else {
                intent = getAllIntent(context, filePath);
            }
            context.startActivity(intent);
        } catch (Exception e) {
        }
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getAllIntent(Context context, String filePath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "*/*", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开APK文件的intent
    public static Intent getApkFileIntent(Context context, String filePath) {
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/vnd.android.package-archive", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开VIDEO文件的intent
    public static Intent getVideoFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        setIntentDataAndType(context, intent, "video/*", new File(filePath), false);
        return intent;
    }

    //Android获取一个用于打开AUDIO文件的intent
    public static Intent getAudioFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.putExtra("oneshot", 0);
        intent.putExtra("configchange", 0);
        setIntentDataAndType(context, intent, "audio/*", new File(filePath), false);
        return intent;
    }

    //Android获取一个用于打开图片文件的intent
    public static Intent getImageFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "image/*", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开PPT文件的intent
    public static Intent getPptFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/vnd.ms-powerpoint", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开Excel文件的intent
    public static Intent getExcelFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/vnd.ms-excel", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开Word文件的intent
    public static Intent getWordFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/msword", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开CHM文件的intent
    public static Intent getChmFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/x-chm", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开文本文件的intent
    public static Intent getTextFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "text/plain", new File(filePath), true);
        return intent;
    }

    //Android获取一个用于打开PDF文件的intent
    public static Intent getPdfFileIntent(Context context, String filePath) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.addCategory("android.intent.category.DEFAULT");
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        setIntentDataAndType(context, intent, "application/pdf", new File(filePath), true);
        return intent;
    }

    public static Uri getUriForFile(Context mContext, File file) {
        Uri fileUri = null;
        if (Build.VERSION.SDK_INT >= 24) {
            fileUri = getUriForFile24(mContext, file);
        } else {
            fileUri = Uri.fromFile(file);
        }
        return fileUri;
    }

    public static Uri getUriForFile24(Context mContext, File file) {
        Uri fileUri = FileProvider.getUriForFile(mContext,
                BuildConfig.APPLICATION_ID + ".provider",
                file);
        return fileUri;
    }

    public static void setIntentDataAndType(Context mContext,
                                            Intent intent,
                                            String type,
                                            File file,
                                            boolean writeAble) {
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setDataAndType(getUriForFile(mContext, file), type);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            if (writeAble) {
                intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
        } else {
            intent.setDataAndType(Uri.fromFile(file), type);
        }
    }
}
