package com.noxgroup.qahelper.util.toast;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import androidx.annotation.StringRes;

import com.noxgroup.qahelper.util.Utils;


/**
 * @author huangjian
 * @create 2019/3/19
 * @Description
 */
public class ToastUtils {
    private static Toast toast;

    /**
     * 防止多次点击重复显示
     *
     * @param txt
     */
    public static void showShortImmi(CharSequence txt) {
        if (!TextUtils.isEmpty(txt)) {
            try {
                if (toast != null) {
                    toast.cancel();
                }
                toast = ToastCompat.makeText(Utils.getApp(), txt, Toast.LENGTH_SHORT);
                toast.show();
            } catch (Exception e) {
            }
        }
    }


    public static void showShort(@StringRes int resId) {
        try {
            Context context = Utils.getApp();
            if (context != null) {
                ToastCompat.makeText(context, resId, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }

    public static void showShort(String string) {
        try {
            Context context = Utils.getApp();
            if (context != null) {
                ToastCompat.makeText(context, string, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
        }
    }
}
