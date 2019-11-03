package com.megadev.easylist.activity.editor;

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
import com.megadev.easylist.R;
import com.megadev.easylist.api.ApiClient;
import com.megadev.easylist.api.ApiInterface;
import com.megadev.easylist.model.Lista;

public class NewListActivity extends AppCompatActivity implements EditorView {

    private FirebaseAuth mAuth;

    private EditText etNomeLista;
    private Button btnSalvar;

    ProgressDialog progressDialog;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        presenter = new EditorPresenter(this);

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
                FirebaseUser user = mAuth.getCurrentUser();

                if (NME_LISTA.isEmpty()) {
                    etNomeLista.setError("Por favor, digite um nome para a lista");

                } else {
                    presenter.saveList(NME_LISTA, user);

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

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(NewListActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

    @Override
    public void showProgress() {
        progressDialog.show();
    }

    @Override
    public void hideProgress() {
    progressDialog.dismiss();
    }

    @Override
    public void onAddSuccess(String message, FirebaseUser user) {
        Toast.makeText(NewListActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish(); // back to main activity
    }

    @Override
    public void onAddError(String message, FirebaseUser user) {
        Toast.makeText(NewListActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        // if error, still in this activity
    }
}


