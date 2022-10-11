package com.solucioneshr.soft.retrofit_room.service;

import com.solucioneshr.soft.retrofit_room.model.DataModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DataApi {

    @GET("posts")
    Call<List<DataModel>> getDataWebservice();
}
