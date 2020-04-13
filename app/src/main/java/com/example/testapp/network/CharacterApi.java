package com.example.testapp.network;

import com.example.testapp.model.CharacterResults;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface CharacterApi {

    @GET("character/")
    Observable<CharacterResults> getCharacters(@Query("page") int page);
}
