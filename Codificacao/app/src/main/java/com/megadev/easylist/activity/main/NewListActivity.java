package com.megadev.easylist.activity.main;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.EditorPresenter;
import com.megadev.easylist.activity.editor.EditorView;

public class NewListActivity extends Activity implements EditorView {

    private FirebaseAuth mAuth;

    private EditText etNomeLista;
    private TextView tvCountNameList;
    private Button btnSalvar;

    ProgressDialog progressDialog;

    EditorPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.4));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);

        presenter = new EditorPresenter(this);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        etNomeLista = (EditText) findViewById(R.id.etNomeLista);
        tvCountNameList = (TextView) findViewById(R.id.tvCountNameList);
        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        etNomeLista.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCountNameList.setText(charSequence.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Criando Lista");

        btnSalvar.setOnClickListener(view -> {
            String NME_LISTA = etNomeLista.getText().toString();
            FirebaseUser user = mAuth.getCurrentUser();

            if (NME_LISTA.isEmpty())
                etNomeLista.setError("Por favor, digite um nome para a lista");
            else if (NME_LISTA.length() > 20)
                etNomeLista.setError("Número máximo de caracteres excecido");
            else
                presenter.saveList(NME_LISTA, user);

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

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(NewListActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}


