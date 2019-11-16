package com.megadev.easylist.activity.editor;

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

    public void getData(final int ID_LISTA) {

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

    public void updateItem(final int ID_ITEM, final int STA_CHECK) {

        view.showLoading();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<Item> call = apiInterface.updateItem(ID_ITEM, STA_CHECK);
        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(@NonNull Call<Item> call, @NonNull Response<Item> response) {
                view.hideLoading();
                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        view.onAddSuccess(response.body().getMessage());

                    } else {
                        view.onAddError(response.body().getMessage());


                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<Item> call, @NonNull Throwable t) {
                view.hideLoading();
                view.onAddError(t.getLocalizedMessage());
            }
        });

    }

}
