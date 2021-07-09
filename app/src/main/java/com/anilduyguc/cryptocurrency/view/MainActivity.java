package com.anilduyguc.cryptocurrency.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.anilduyguc.cryptocurrency.R;
import com.anilduyguc.cryptocurrency.adapter.RecyclerViewAdapter;
import com.anilduyguc.cryptocurrency.model.CryptoModel;
import com.anilduyguc.cryptocurrency.service.CryptoApi;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    private String BASE_URL="https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //https://api.nomics.com/v1/prices?key=c884e7f58d78125dc746ab0b835c89ca6efa5fd1
        recyclerView=findViewById(R.id.recyclerView);
        //Retrofit and JSON

        Gson gson=new GsonBuilder().setLenient().create();
        retrofit= new Retrofit.Builder().baseUrl(BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create(gson)).build();

        getDataFromAPI();

    }

    private void getDataFromAPI(){
        final CryptoApi cryptoApi = retrofit.create(CryptoApi.class);
        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoApi.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));


        /*Call<List<CryptoModel>> call=cryptoApi.getData();

        call.enqueue(new Callback<List<CryptoModel>>() {
            @Override
            public void onResponse(Call<List<CryptoModel>> call, Response<List<CryptoModel>> response) {
                if(response.isSuccessful()){
                    List<CryptoModel> responseList = response.body();
                    cryptoModels=new ArrayList<>(responseList);
                    //RecyclerView
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    recyclerViewAdapter =new RecyclerViewAdapter(cryptoModels);
                    recyclerView.setAdapter(recyclerViewAdapter);

                    for(CryptoModel cryptoModel: cryptoModels)
                        System.out.println(cryptoModel);
                }
            }

            @Override
            public void onFailure(Call<List<CryptoModel>> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage().toString(),Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
*/

    }
    private void handleResponse(List<CryptoModel> cryptoModelList){
        cryptoModels=new ArrayList<>(cryptoModelList);
        //RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerViewAdapter =new RecyclerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}