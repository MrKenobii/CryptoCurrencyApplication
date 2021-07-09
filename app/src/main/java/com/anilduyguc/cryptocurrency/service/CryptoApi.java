package com.anilduyguc.cryptocurrency.service;

import com.anilduyguc.cryptocurrency.model.CryptoModel;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.GET;

public interface CryptoApi {
    @GET("prices?key=c884e7f58d78125dc746ab0b835c89ca6efa5fd1")
    Observable<List<CryptoModel>> getData();

    //public Call<List<CryptoModel>> getData();
}
