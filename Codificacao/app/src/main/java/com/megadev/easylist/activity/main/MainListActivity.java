package com.megadev.easylist.activity.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.megadev.easylist.R;
import com.megadev.easylist.activity.editor.NewProductActivity;

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