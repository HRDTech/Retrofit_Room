package com.solucioneshr.soft.retrofit_room.room;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.solucioneshr.soft.retrofit_room.model.DataModel;

import java.util.List;

public class DataViewModel extends AndroidViewModel {
    private final DataRepository dataRepository;
    private final LiveData<List<DataModel>> mDataList;

    public DataViewModel(@NonNull Application application) {
        super(application);
        dataRepository = new DataRepository(application);
        mDataList = dataRepository.getDataList();
    }

    public LiveData<List<DataModel>> getAllData(){
        return mDataList;
    }

    public void insertData(DataModel data){
        dataRepository.insertData(data);
    }

    public void deleteAllData() {
        dataRepository.deleteData();
    }
}
