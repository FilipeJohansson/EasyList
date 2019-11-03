package com.megadev.easylist.activity.editor;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.megadev.easylist.R;

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


    }
}

