package com.megadev.easylist.api;

import com.megadev.easylist.model.Lista;
import com.megadev.easylist.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.Query;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("save_list.php")
    Call<Lista> saveList(
            @Field("NME_LISTA") String NME_LISTA,
            @Field("UID_USUARIO") String UID_USUARIO
    );

    @FormUrlEncoded
    @POST("save_user.php")
    Call<User> saveUser(
            @Field("UID_USUARIO") String UID_USUARIO
    );

    @GET("request_listas.php")
    Call<List<Lista>> getListas(
            @Query("UID_USUARIO") String UID_USUARIO
    );

}
