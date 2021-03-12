package com.noxgroup.qahelper.util;

import android.annotation.SuppressLint;
import android.app.Application;

import androidx.annotation.NonNull;

import java.lang.reflect.InvocationTargetException;

/**
 * @author huangjian
 * @create 2019/1/10
 * @Description
 */
public final class Utils {
    private static Application sApplication;

    private Utils() {
    }

    public static void init(@NonNull final Application application) {
        if (sApplication == null) {
            if (application != null) {
                sApplication = application;
            } else {
                sApplication = getApplicationByReflect();
            }
        }
    }

    private static Application getApplicationByReflect() {
        try {
            @SuppressLint("PrivateApi")
            Class<?> activityThread = Class.forName("android.app.ActivityThread");
            Object thread = activityThread.getMethod("currentActivityThread").invoke(null);
            Object app = activityThread.getMethod("getApplication").invoke(thread);
            if (app == null) {
                throw new NullPointerException("u should init first");
            }
            return (Application) app;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        throw new NullPointerException("u should init first");
    }

    public static Application getApp() {
        if (sApplication != null) return sApplication;
        Application app = getApplicationByReflect();
        init(app);
        return app;
    }
}
