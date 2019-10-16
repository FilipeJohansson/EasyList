package com.megadev.easylist;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Banco extends SQLiteOpenHelper {

    private static final int VERSAO = 1;
    private static final String NOME = "EasyList";

    public Banco(Context contexto){
        super(contexto, NOME, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS listas ( " +
                " idLista INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " nome TEXT );" );
        sqLiteDatabase.execSQL( "CREATE TABLE IF NOT EXISTS produtos ( " +
                " idProduto INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT ," +
                " idLista INTEGER," +
                " nome TEXT ," +
                " quantidade INTEGER ," +
                " FOREIGN KEY(idLista) REFERENCES listas(idLista));" );
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
