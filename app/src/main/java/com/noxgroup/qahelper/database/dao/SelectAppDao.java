package com.noxgroup.qahelper.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.noxgroup.qahelper.database.bean.SelectApp;

import java.util.List;

/**
 * @Author: SongRan
 * @Date: 2021/3/5
 * @Desc:
 */
@Dao
public interface SelectAppDao {
    @Query("SELECT * FROM selectapp")
    List<SelectApp> getAllApp();

    @Insert
    void insertApp(SelectApp selectApp);

    @Delete
    void deleteApp(SelectApp selectApp);
}
