package com.solucioneshr.soft.retrofit_room;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.BaseTransientBottomBar;
import com.google.android.material.snackbar.Snackbar;
import com.solucioneshr.soft.retrofit_room.adapters.DataViewAdapter;
import com.solucioneshr.soft.retrofit_room.model.DataModel;
import com.solucioneshr.soft.retrofit_room.room.DataViewModel;
import com.solucioneshr.soft.retrofit_room.service.ServiceData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerViewData;
    private ProgressBar progressBar;
    private static final int PERMISSION_REQUEST = 101;

    @SuppressLint("ResourceType")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerViewData = findViewById(R.id.recyclerData);
        recyclerViewData.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewData.setHasFixedSize(true);
        recyclerViewData.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        progressBar = findViewById(R.id.progressLoad);

        ViewModelProvider.AndroidViewModelFactory factory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        DataViewModel dataViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factory).get(DataViewModel.class);

        checkPermission();

        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        FloatingActionButton fab = findViewById(R.id.fabSyncCloud);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onLoadWebServiceData(dataViewModel);
            }
        });

        if (networkInfo != null && networkInfo.isConnected()) {
            fab.setVisibility(View.VISIBLE);
            onLoadWebServiceData(dataViewModel);
        } else {
            onLoadDatabaseData(dataViewModel);
            fab.setVisibility(View.INVISIBLE);
            Snackbar.make(findViewById(R.id.fabSyncCloud), "No tiene conexión, los datos son los almacendados", BaseTransientBottomBar.LENGTH_INDEFINITE)
                    .show();
        }



    }

    private void onLoadWebServiceData(DataViewModel vwData){
        Call<List<DataModel>> call = ServiceData.getInstance().getMyApi().getDataWebservice();
        call.enqueue(new Callback<List<DataModel>>() {
            @Override
            public void onResponse(Call<List<DataModel>> call, Response<List<DataModel>> response) {
                if (response.isSuccessful()){
                    List<DataModel> dataModelList = response.body();

                    vwData.deleteAllData();

                    for (DataModel item : dataModelList){
                        vwData.insertData(item);
                    }

                    DataViewAdapter adapter = new DataViewAdapter(dataModelList, getApplicationContext());
                    recyclerViewData.setAdapter(adapter);
                    recyclerViewData.setVisibility(View.VISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    Toast.makeText(getApplicationContext(), "Error en los datos recibidos", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<List<DataModel>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Error " + t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    private void onLoadDatabaseData(DataViewModel vwData){
        vwData.getAllData().observe(this, dataModels -> {
            DataViewAdapter adapter = new DataViewAdapter(dataModels, getApplicationContext());
            recyclerViewData.setAdapter(adapter);
            recyclerViewData.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.INVISIBLE);
        });
    }

    private void checkPermission(){
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_NETWORK_STATE) == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_NETWORK_STATE}, PERMISSION_REQUEST);
        }
    }

    @SuppressLint("ResourceType")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST){
            if (grantResults[0] == PackageManager.PERMISSION_DENIED){
                Snackbar.make(findViewById(R.id.fabSyncCloud), "Tiene que accesar el Permiso", BaseTransientBottomBar.LENGTH_INDEFINITE)
                        .show();
            }
        }
    }
}