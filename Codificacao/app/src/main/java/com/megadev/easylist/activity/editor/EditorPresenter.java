package com.megadev.easylist.activity.editor;

import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.Item;
import com.megadev.easylist.model.Lista;
import com.megadev.easylist.model.User;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    public void saveUser(final String UID_USUARIO, final String EMAIL, final FirebaseUser user) {
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<User> call = apiInterface.saveUser(UID_USUARIO, EMAIL);

        call.enqueue(new Callback<User>() {
            @Override
            public void onResponse(@NonNull Call<User> call, @NonNull Response<User> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        view.onAddSuccess(response.body().getMessage(), user);

                    } else {
                        view.onAddError(response.body().getMessage(), user);

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<User> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage(), user);

            }
        });

    }

    public void saveList(final String NME_LISTA, final FirebaseUser user) {
        view.showProgress();

        String UID_USUARIO = user.getUid();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Lista> call = apiInterface.saveList(NME_LISTA, UID_USUARIO);

        call.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(@NonNull Call<Lista> call, @NonNull Response<Lista> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        view.onAddSuccess(response.body().getMessage(), user);

                    } else {
                        view.onAddError(response.body().getMessage(), user);


                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<Lista> call, @NonNull Throwable t) {
                view.hideProgress();

                view.onAddError(t.getLocalizedMessage(), user);

            }
        });

    }

    public void saveItem(final int STA_CHECK,
                  final float QUANTIDADE,
                  final float VLR_UNITARIO,
                  final int ID_LISTA,
                  final String NME_PRODUTO,
                  final String DSC_PRODUTO,
                  final String UN_MEDIDA,
                  final FirebaseUser user) {
        view.showProgress();

        float VLR_TOTAL = QUANTIDADE * VLR_UNITARIO;

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Item> call = apiInterface.saveItem(STA_CHECK, QUANTIDADE, VLR_UNITARIO, VLR_TOTAL, ID_LISTA, NME_PRODUTO, DSC_PRODUTO, UN_MEDIDA);

        call.enqueue(new Callback<Item>() {
            @Override
            public void onResponse(@NonNull Call<Item> call, @NonNull Response<Item> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        view.onAddSuccess(response.body().getMessage(), user);

                    } else {
                        view.onAddError(response.body().getMessage(), user);


                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<Item> call, @NonNull Throwable t) {
                view.hideProgress();

                view.onAddError(t.getLocalizedMessage(), user);

            }
        });

    }

    public void shareList(final int ID_LISTA, final int ID_COMPARTILHADO, final FirebaseUser user) {
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Lista> call = apiInterface.shareList(ID_LISTA, ID_COMPARTILHADO);

        call.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(@NonNull Call<Lista> call, @NonNull Response<Lista> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success)
                        view.onAddSuccess(response.body().getMessage(), user);
                    else
                        view.onAddError(response.body().getMessage(), user);

                }

            }

            @Override
            public void onFailure(@NonNull Call<Lista> call, @NonNull Throwable t) {
                view.hideProgress();
                if (t instanceof IOException)
                    view.onAddError(t.getLocalizedMessage(), user);
                else
                    view.onAddError(t.getLocalizedMessage(), user);
            }
        });

    }

    public void getUser(final String EMAIL, final FirebaseUser user) {
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<User>> call = apiInterface.getUser(EMAIL);

        call.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(@NonNull Call<List<User>> call, @NonNull Response<List<User>> response) {
                view.hideProgress();

                if (response.isSuccessful() && response.body() != null) {
                    view.onGetResult(response.body());

                }

            }

            @Override
            public void onFailure(@NonNull Call<List<User>> call, @NonNull Throwable t) {
                view.hideProgress();
                view.onAddError(t.getLocalizedMessage(), user);
            }
        });

    }

}
