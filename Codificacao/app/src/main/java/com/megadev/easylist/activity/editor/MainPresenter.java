package com.megadev.easylist.activity.editor;

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

    public void getData(String UID) {

        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<List<Lista>> call = apiInterface.getListas(UID);
        call.enqueue(new Callback<List<Lista>>() {
            @Override
            public void onResponse(@NonNull Call<List<Lista>> call, @NonNull Response<List<Lista>> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null)
                    view.onGetResult(response.body());
            }

            @Override
            public void onFailure(@NonNull Call<List<Lista>> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

    public void deleteLista(final int ID_LISTA) {
        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<Lista> call = apiInterface.deleteLista(ID_LISTA);
        call.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(@NonNull Call<Lista> call, @NonNull Response<Lista> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success)
                        view.onAddSuccess(response.body().getMessage());
                    else
                        view.onErrorLoading(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Lista> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onErrorLoading(t.getLocalizedMessage());
            }
        });

    }

}
