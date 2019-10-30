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
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_list);

        lvProdutos = findViewById(R.id.lvProdutos);
        tvNomeLista = findViewById(R.id.tvNomeLista);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent i = new Intent(MainListActivity.this, NewProductActivity.class);
            i.putExtra("idLista", getIntent().getExtras().getInt("idLista"));
            startActivity(i);
        });

        lvProdutos.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                return true;
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();

    }


}