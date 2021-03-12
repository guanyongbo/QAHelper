package com.noxgroup.qahelper.database.manager;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.noxgroup.qahelper.database.bean.SelectApp;
import com.noxgroup.qahelper.database.dao.SelectAppDao;
import com.noxgroup.qahelper.util.Utils;

/**
 * @Author: SongRan
 * @Date: 2021/3/5
 * @Desc: 注意：如果您的应用在单个进程中运行，在实例化 AppDatabase 对象时应遵循单例设计模式。每个 RoomDatabase 实例的成本相当高，而您几乎不需要在单个进程中访问多个实例。
 * 如果您的应用在多个进程中运行，请在数据库构建器调用中包含 enableMultiInstanceInvalidation()。这样，如果您在每个进程中都有一个 AppDatabase 实例，可以在一个进程中使共享数据库文件失效，并且这种失效会自动传播到其他进程中 AppDatabase 的实例。
 */

@Database(entities = {SelectApp.class}, version = 1, exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract SelectAppDao selectAppDao();

    private static volatile AppDataBase appDataBase;

    public static AppDataBase getInstance() {
        if (appDataBase == null) {
            synchronized (AppDataBase.class) {
                if (appDataBase == null) {
                    appDataBase = Room.databaseBuilder(Utils.getApp(), AppDataBase.class, "qahelper").addCallback(new RoomDatabase.Callback() {
                        @Override
                        public void onCreate(@NonNull SupportSQLiteDatabase db) {
                            super.onCreate(db);
                            Log.e("SRLog", "AppDataBase onCreate: ");
                        }

                        @Override
                        public void onOpen(@NonNull SupportSQLiteDatabase db) {
                            super.onOpen(db);
                            Log.e("SRLog", "AppDataBase onOpen: ");
                        }

                        @Override
                        public void onDestructiveMigration(@NonNull SupportSQLiteDatabase db) {
                            super.onDestructiveMigration(db);
                            Log.e("SRLog", "AppDataBase onDestructiveMigration: ");
                        }
                    }).build();
                }
            }
        }
        return appDataBase;
    }

    public void insertSelectApp() {
        selectAppDao().insertApp(new SelectApp("123", "456"));
    }

    public void getAppSize() {
        int size = selectAppDao().getAllApp().size();
        Log.e("SRLog", "AppDataBase getAppSize: " + size);
    }
}
