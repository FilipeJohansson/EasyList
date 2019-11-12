package com.megadev.easylist.activity.editor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.main.MainListActivity;

public class NewProductActivity extends AppCompatActivity implements EditorView {

    private FirebaseAuth mAuth;

    private EditText etQuantidade, etProduto;
    private Button btnSalvar;

    ProgressDialog progressDialog;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        mAuth = FirebaseAuth.getInstance();
        presenter = new EditorPresenter(this);

        int sessionId = getIntent().getExtras().getInt("EXTRA_SESSION_ID");

        etProduto = (EditText) findViewById(R.id.etProduto);
        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        btnSalvar = (Button) findViewById(R.id.btnSalvarProduto);

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adicionando Produto");

        btnSalvar.setOnClickListener(view -> {
            String NME_PRODUTO = etProduto.getText().toString();
            int QUANTIDADE = Integer.parseInt(etQuantidade.getText().toString());
            FirebaseUser user = mAuth.getCurrentUser();

            if (NME_PRODUTO.isEmpty()) {
                etProduto.setError("Por favor, digite um produto");

            } else {
                presenter.saveItem(0,
                        QUANTIDADE,
                        2.3f,
                        sessionId,
                        NME_PRODUTO,
                        "Teste",
                        "Un",
                        user);

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
        Toast.makeText(NewProductActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        finish(); // back to main activity
    }

    @Override
    public void onAddError(String message, FirebaseUser user) {
        Toast.makeText(NewProductActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
        // if error, still in this activity
    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(NewProductActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}

