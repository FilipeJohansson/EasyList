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
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.EditorPresenter;
import com.megadev.easylist.activity.editor.EditorView;
import com.megadev.easylist.model.User;

import java.util.List;
import java.util.Objects;

public class NewProductActivity extends Activity implements EditorView {

    private FirebaseAuth mAuth;

    private EditText etQuantidade, etProduto, etDescricao;
    private TextView tvCountNameProduct, tvCountDesc, tvCountQnt;
    private Spinner spnMedida;

    ProgressDialog progressDialog;

    EditorPresenter presenter;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        /* PopUp activity */
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        int width = displayMetrics.widthPixels;
        int height = displayMetrics.heightPixels;

        getWindow().setLayout((int)(width*.8), (int)(height*.6));

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.gravity = Gravity.CENTER;
        params.x = 0;
        params.y = -20;

        getWindow().setAttributes(params);
        /* /PopUp activity/ */

        mAuth = FirebaseAuth.getInstance();
        presenter = new EditorPresenter(this);

        int sessionId = Objects.requireNonNull(getIntent().getExtras()).getInt("EXTRA_SESSION_ID");

        etProduto = findViewById(R.id.etProduto);
        etQuantidade = findViewById(R.id.etQuantidade);
        etDescricao = findViewById(R.id.etDescricao);
        tvCountNameProduct = findViewById(R.id.tvCountNameProduct);
        tvCountDesc = findViewById(R.id.tvCountDesc);
        tvCountQnt = findViewById(R.id.tvCountQnt);
        spnMedida = findViewById(R.id.spnMedida);

        Button btnSalvar = findViewById(R.id.btnSalvarProduto);

        tvCountNameProduct.setText("0/20");
        tvCountDesc.setText("0/30");
        tvCountQnt.setText("0/5");

        etProduto.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCountNameProduct.setText(charSequence.length() + "/20");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etDescricao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCountDesc.setText(charSequence.length() + "/30");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        etQuantidade.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                tvCountQnt.setText(charSequence.length() + "/5");
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Adicionando Produto");

        btnSalvar.setOnClickListener(view -> {
            String NME_PRODUTO = etProduto.getText().toString();
            String DESCRICAO = etDescricao.getText().toString();
            String QNT = etQuantidade.getText().toString();
            String MEDIDA = spnMedida.getSelectedItem().toString();
            FirebaseUser user = mAuth.getCurrentUser();

            float QUANTIDADE = 1;

            if (!QNT.isEmpty()) {
                QUANTIDADE = Float.parseFloat(QNT);
            }

            if (NME_PRODUTO.isEmpty())
                etProduto.setError("Por favor, digite um produto");
            else if (NME_PRODUTO.length() > 20)
                etProduto.setError("Limite de caracteres excedito");
            else if (DESCRICAO.length() > 30)
                etDescricao.setError("Limite de caracteres excedido");
            else if (QNT.isEmpty())
                etQuantidade.setError("Por favor, coloque uma quantidade");
            else if (QNT.length() > 5)
                etQuantidade.setError("Limite de caracteres excedido");
            else if (QUANTIDADE > 999.9f)
                etQuantidade.setError("Valor máximo permitido: 999.9");
            else if (QUANTIDADE < 0)
                etQuantidade.setError("Quantidade mínima: 0");
            else
                presenter.saveItem(0,
                        QUANTIDADE,
                        2.3f,
                        sessionId,
                        NME_PRODUTO,
                        DESCRICAO,
                        MEDIDA,
                        user);

        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateUI(mAuth.getCurrentUser());

    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI(mAuth.getCurrentUser());
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
        finish();
    }

    @Override
    public void onAddError(String message, FirebaseUser user) {
        Toast.makeText(NewProductActivity.this,
                message,
                Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetResult(List<User> users) {

    }

    private void updateUI(FirebaseUser currentUser) {

        if (currentUser == null) {
            Intent intent = new Intent(NewProductActivity.this, LoginActivity.class);
            startActivity(intent);

        }

    }

}