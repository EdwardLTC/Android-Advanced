package com.edward.myapplication.api;

import com.edward.myapplication.modal.RegisReturn;
import com.edward.myapplication.modal.Player;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ServiceLogin {
    String BASE_SERVICE_LOGIN = "https://hoccungminh.dinhnt.com/";

    @POST("apis/register")
    Observable<RegisReturn> loginResult(@Body Player player);
}
