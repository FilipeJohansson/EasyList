package com.megadev.easylist.activity.main;

import androidx.annotation.NonNull;

import java.util.List;

import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.Item;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainListPresenter {

    private MainListView view;

    public MainListPresenter(MainListView view) {
        this.view = view;
    }

    void getData(int ID_LISTA) {

        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<List<Item>> call = apiInterface.getItens(ID_LISTA);
        call.enqueue(new Callback<List<Item>>() {
            @Override
            public void onResponse(@NonNull Call<List<Item>> call, @NonNull Response<List<Item>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Item>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
