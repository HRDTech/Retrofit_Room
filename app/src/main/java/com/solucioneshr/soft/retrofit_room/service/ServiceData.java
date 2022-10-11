package com.solucioneshr.soft.retrofit_room.service;

import com.solucioneshr.soft.retrofit_room.ConfigApk;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceData {
    private static ServiceData instance = null;
    private DataApi myApi;

    private ServiceData() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(ConfigApk.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        myApi = retrofit.create(DataApi.class);
    }

    public static synchronized ServiceData getInstance() {
        if (instance == null) {
            instance = new ServiceData();
        }
        return instance;
    }

    public DataApi getMyApi() {
        return myApi;
    }
}
