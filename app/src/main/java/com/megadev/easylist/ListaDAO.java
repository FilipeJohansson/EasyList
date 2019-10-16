package com.megadev.easylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ListaDAO {
    public static void inserirTime(Context contexto, Lista lista) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", lista.getNome());

        db.insert("listas", null, valores);

    }

    public static void editarTime(Context contexto, Lista lista) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", lista.getNome());

        db.update("lisats", valores,
                " idLista = " + lista.getId(), null);

    }

    public static void excluirTime(Context contexto, int idLista) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("listas", " idLista = " + idLista,
                null);
    }


    public static List<Lista> getListas(Context contexto) {
        List<Lista> lista = new ArrayList<>();
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM listas ORDER BY idLista",
                null);
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Lista listas = new Lista();
                listas.setId(cursor.getInt(0));
                listas.setNome(cursor.getString(1));
                lista.add(listas);
            } while (cursor.moveToNext());
        }
        return lista;
    }

    public static Lista getListaById(Context contexto, int idLista) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT * FROM listas WHERE idLista = " + idLista;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Lista lista = new Lista();
            lista.setId(cursor.getInt(0));
            lista.setNome(cursor.getString(1));

            return lista;
        } else {
            return null;
        }
    }
}
