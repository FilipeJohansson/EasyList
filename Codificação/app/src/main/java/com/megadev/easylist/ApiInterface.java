package com.megadev.easylist;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("save.php")
    Call<Lista> saveList(
            @Field("NME_LISTA") String NME_LISTA,
            @Field("UID_USUARIO") String UID_USUARIO
    );

}
