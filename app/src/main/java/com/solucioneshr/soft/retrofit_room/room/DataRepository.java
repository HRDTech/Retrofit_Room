package com.solucioneshr.soft.retrofit_room.room;

import android.content.Context;
import androidx.lifecycle.LiveData;
import com.solucioneshr.soft.retrofit_room.model.DataModel;
import java.util.List;

public class DataRepository {
    private final LiveData<List<DataModel>> mDataList;
    private final DataDao mDataDao;


    public DataRepository(Context context) {
        DataDatabase dataDB = DataDatabase.getInstance(context);
        mDataDao = dataDB.dataDao();
        mDataList = mDataDao.getAllData();
    }

    public LiveData<List<DataModel>> getDataList(){
        return mDataList;
    }

    public void insertData(DataModel data){
        DataDatabase.dbExecutor.execute(
                () -> mDataDao.insertData(data)
        );
    }

    public void deleteData(){
        DataDatabase.dbExecutor.execute(
                mDataDao::deleteDate
        );
    }
}
