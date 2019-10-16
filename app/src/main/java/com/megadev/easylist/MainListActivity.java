package com.megadev.easylist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainListActivity extends AppCompatActivity {

    private ListView lvProdutos;
    private TextView tvNomeLista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        lvProdutos = findViewById(R.id.lvProdutos);
        tvNomeLista = findViewById(R.id.tvNomeLista);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainListActivity.this, NewProductActivity.class);
                i.putExtra("idLista", getIntent().getExtras().getInt("idLista"));
                startActivity(i);
            }
        });

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluir((Produto) adapterView.getItemAtPosition(i));
                return true;
            }
        });
    }

    private void excluir(final Produto produto) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(getString(R.string.txtExcluir) + " " + produto.getNome());
        alerta.setMessage(getString(R.string.txtConfirmaExclusao));
        alerta.setNeutralButton((getString(R.string.btnCancelar)), null);
        alerta.setPositiveButton((getString(R.string.btnConfirmar)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ProdutoDAO.excluirProduto(MainListActivity.this, produto.getId());
                carregarListaProdutos();
            }
        });
        alerta.show();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }*/


    @Override
    protected void onStart() {
        super.onStart();
        carregarListaProdutos();
    }

    private void carregarListaProdutos() {
        List<Produto> listaProdutos = ProdutoDAO.getProdutos(this, getIntent().getExtras().getInt("idLista"));

        if (listaProdutos.size() == 0) {
            lvProdutos.setEnabled(false);
            Produto fake = new Produto();
            fake.setQuantidade(0);
            fake.setNome(getString(R.string.txtListaVazia));
            listaProdutos.add(fake);
        } else {
            lvProdutos.setEnabled(true);
        }
        AdapterProduto adapter = new AdapterProduto(this, listaProdutos);

        lvProdutos.setAdapter(adapter);

    }

}