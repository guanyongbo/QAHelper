package com.noxgroup.qahelper;

import android.app.Application;
import android.content.Context;

import com.noxgroup.qahelper.database.manager.AppDataBase;
import com.noxgroup.qahelper.util.Utils;

/**
 * @Author: SongRan
 * @Date: 2021/1/18
 * @Desc:
 */
public class MyApplication extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Utils.init(this);
        AppDataBase.getInstance();
    }
}
