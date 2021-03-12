package com.noxgroup.qahelper.database.bean;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * @Author: SongRan
 * @Date: 2021/3/5
 * @Desc: App信息
 */

@Entity
public class SelectApp {
    @PrimaryKey
    @NonNull
    public String packageName;
    @ColumnInfo
    public String appName;

    public SelectApp(@NonNull String packageName, String appName) {
        this.packageName = packageName;
        this.appName = appName;
    }
}
