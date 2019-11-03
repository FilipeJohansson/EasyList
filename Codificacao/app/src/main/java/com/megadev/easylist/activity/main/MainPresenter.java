package com.megadev.easylist.activity.main;

import androidx.annotation.NonNull;

import java.util.List;

import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.Lista;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainPresenter {

    private MainView view;

    public MainPresenter(MainView view) {
        this.view = view;
    }

    void getData(String UID) {

        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<List<Lista>> call = apiInterface.getListas(UID);
        call.enqueue(new Callback<List<Lista>>() {
            @Override
            public void onResponse(@NonNull Call<List<Lista>> call, @NonNull Response<List<Lista>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<Lista>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
