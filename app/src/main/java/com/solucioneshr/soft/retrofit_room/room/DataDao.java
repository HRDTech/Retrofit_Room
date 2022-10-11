package com.solucioneshr.soft.retrofit_room.room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.solucioneshr.soft.retrofit_room.model.DataModel;

import java.util.List;

@Dao
public interface DataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertData(DataModel data);

    @Query("select * from data order by id asc")
    LiveData<List<DataModel>> getAllData();

    @Query("delete from data")
    void deleteDate();
}
