package com.edward.myapplication;


import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ServiceAPI {
    String Base_Service ="https://api.fpt.ai/";

    @POST("hmi/tts/v5")
    Observable<Test> getAPI(@Header("api_key") String apiKey, @Body String data);
}
