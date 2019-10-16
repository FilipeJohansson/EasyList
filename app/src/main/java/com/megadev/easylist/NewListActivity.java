package com.megadev.easylist;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class NewListActivity extends AppCompatActivity {

    private EditText etNomeTime;
    private Button btnSalvar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);

        etNomeTime = (EditText) findViewById(R.id.etNomeLista);

        btnSalvar = (Button) findViewById(R.id.btnSalvar);

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                salvar();
            }
        });
    }

    private void salvar() {
        String nome = etNomeTime.getText().toString();

        if (nome.isEmpty()) {
            AlertDialog.Builder alerta = new AlertDialog.Builder(this);
            alerta.setIcon(android.R.drawable.ic_dialog_alert);
            alerta.setTitle(getString(R.string.txtAlerta));
            alerta.setMessage(getString(R.string.txtAlertaListas));
            alerta.setPositiveButton("OK", null);
            alerta.show();
        } else {
            Lista lista = new Lista();
            lista.setNome(nome);

            ListaDAO.inserirTime(this, lista);
            this.finish();
        }
    }
}


