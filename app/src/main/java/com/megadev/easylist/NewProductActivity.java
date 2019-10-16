package com.megadev.easylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewProductActivity extends AppCompatActivity {

    private EditText etQuantidade, etProduto;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        etQuantidade = (EditText) findViewById(R.id.etQuantidade);
        etProduto = (EditText) findViewById(R.id.etProduto);

        btnSalvar = (Button) findViewById(R.id.btnSalvarProduto);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar() {
        String nome = etProduto.getText().toString();
        String camisa = etQuantidade.getText().toString();

        if (nome.isEmpty()||camisa.isEmpty()) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(android.R.drawable.ic_dialog_alert);
            alerta.setTitle(getString(R.string.txtAlerta));
            alerta.setMessage(getString(R.string.txtAlertaProduto));
            alerta.setPositiveButton("OK", null);
            alerta.show();
        } else {
            Produto produto = new Produto();
            produto.setNome(nome);
            produto.setQuantidade(Integer.valueOf(camisa));
            produto.setIdLista(getIntent().getExtras().getInt("idLista"));
            ProdutoDAO.inserirProduto(this, produto);
            this.finish();
        }

    }
}

