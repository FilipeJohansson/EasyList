package com.megadev.easylist.activity.editor;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.Lista;
import com.megadev.easylist.model.User;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditorPresenter {

    private EditorView view;

    public EditorPresenter(EditorView view) {
        this.view = view;
    }

    void saveUser(final String UID_USUARIO, final FirebaseUser user) {
        view.showProgress();

        ApiInterface apiInterface = ApiClient.getApiClient()
                .create(ApiInterface.class);
        Call<User> call = apiInterface.saveUser(UID_USUARIO);

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

    void saveList(final String NME_LISTA, final FirebaseUser user) {
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

}
