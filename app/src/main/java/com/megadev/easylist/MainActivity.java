package com.megadev.easylist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    private ListView lvListas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvListas = findViewById(R.id.lvListas);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, NewListActivity.class);
                startActivity(i);
            }
        });

        lvListas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Lista time = (Lista) adapterView.getItemAtPosition(i);

                Intent intent = new Intent(MainActivity.this, MainListActivity.class);
                intent.putExtra("idLista", time.getId());
                startActivity(intent);
            }
        });

        lvListas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                excluir((Lista) adapterView.getItemAtPosition(i));
                return true;
            }
        });
    }

    private void excluir(final Lista lista) {
        AlertDialog.Builder alerta = new AlertDialog.Builder(this);
        alerta.setTitle(getString(R.string.txtExcluir) + " " + lista.getNome());
        alerta.setMessage(getString(R.string.txtConfirmaExclusao));
        alerta.setNeutralButton((getString(R.string.btnCancelar)), null);
        alerta.setPositiveButton((getString(R.string.btnConfirmar)), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                ListaDAO.excluirTime(MainActivity.this, lista.getId());
                carregarLista();
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
        carregarLista();
    }

    private void carregarLista() {
        List<Lista> lista = ListaDAO.getListas(this);

        if (lista.size() == 0) {
            lvListas.setEnabled(false);
            Lista fake = new Lista();
            fake.setNome(getString(R.string.txtListaVazia));
            lista.add(fake);
        } else {
            lvListas.setEnabled(true);
        }

        AdapterLista adapter = new AdapterLista(this, lista);

        lvListas.setAdapter(adapter);

    }

}