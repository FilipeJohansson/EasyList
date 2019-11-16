package com.megadev.easylist.api;

import com.megadev.easylist.model.Item;
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
    @POST("save_user.php")
    Call<User> saveUser(
            @Field("UID_USUARIO") String UID_USUARIO
    );

    @FormUrlEncoded
    @POST("save_list.php")
    Call<Lista> saveList(
            @Field("NME_LISTA") String NME_LISTA,
            @Field("UID_USUARIO") String UID_USUARIO
    );

    @FormUrlEncoded
    @POST("save_item.php")
    Call<Item> saveItem(
            @Field("STA_CHECK") int STA_CHECK,
            @Field("QUANTIDADE") float QUANTIDADE,
            @Field("VLR_UNITARIO") float VLR_UNITARIO,
            @Field("VLR_TOTAL") float VLR_TOTAL,
            @Field("ID_LISTA") int ID_LISTA,
            @Field("NME_PRODUTO") String NME_PRODUTO,
            @Field("DSC_PRODUTO") String DSC_PRODUTO,
            @Field("UN_MEDIDA") String UN_MEDIDA
    );

    @FormUrlEncoded
    @POST("update_item.php")
    Call<Item> updateItem(
            @Field("ID_ITEM") int ID_ITEM,
            @Field("STA_CHECK") int STA_CHECK
    );

    @GET("request_listas.php")
    Call<List<Lista>> getListas(
            @Query("UID_USUARIO") String UID_USUARIO
    );

    @GET("request_itens.php")
    Call<List<Item>> getItens(
            @Query("ID_LISTA") int ID_LISTA
    );

    @FormUrlEncoded
    @POST("delete_list.php")
    Call<Lista> deleteLista(
            @Field("ID_LISTA") int ID_LISTA
    );

}
