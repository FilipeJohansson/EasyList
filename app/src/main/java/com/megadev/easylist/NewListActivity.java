package com.megadev.easylist;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class NewListActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    private EditText etNomeLista;
    private Button btnSalvar;

    ProgressDialog progressDialog;

    ApiInterface apiInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etNomeLista = (EditText) findViewById(R.id.etNomeLista);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Criando Lista");

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String NME_LISTA = etNomeLista.getText().toString();
                String UID_USUARIO = mAuth.getUid().trim();

                if (NME_LISTA.isEmpty()) {
                    etNomeLista.setError("Por favor, digite um nome para a lista");

                } else {
                    saveList(NME_LISTA, UID_USUARIO);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void saveList(final String NME_LISTA, final String UID_USUARIO) {
        progressDialog.show();

        apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<Lista> call = apiInterface.saveList(NME_LISTA, UID_USUARIO);

        call.enqueue(new Callback<Lista>() {
            @Override
            public void onResponse(@NonNull Call<Lista> call, @NonNull Response<Lista> response) {
                progressDialog.dismiss();

                if (response.isSuccessful() && response.body() != null) {
                    Boolean success = response.body().getSuccess();

                    if (success) {
                        Toast.makeText(NewListActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        finish(); // back to main activity

                    } else {
                        Toast.makeText(NewListActivity.this,
                                response.body().getMessage(),
                                Toast.LENGTH_SHORT).show();
                        // if error, still in this activity

                    }

                }

            }

            @Override
            public void onFailure(@NonNull Call<Lista> call, @NonNull Throwable t) {
                progressDialog.dismiss();

                Toast.makeText(NewListActivity.this,
                        t.getLocalizedMessage(),
                        Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(NewListActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}


