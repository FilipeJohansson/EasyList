package com.megadev.easylist;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO {
    public static void inserirProduto(Context contexto, Produto produto) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", produto.getNome());
        valores.put("idLista", produto.getIdLista());
        valores.put("quantidade", produto.getQuantidade());

        db.insert("produtos", null, valores);
    }

    public static void editarProduto(Context contexto, Produto produto) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();

        ContentValues valores = new ContentValues();
        valores.put("nome", produto.getNome());
        valores.put("idLista", produto.getIdLista());
        valores.put("quantidade", produto.getQuantidade());

        db.update("produtos", valores,
                " idProduto = " + produto.getId(), null);

    }

    public static void excluirProduto(Context contexto, int idProduto) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getWritableDatabase();
        db.delete("produtos", " idProduto = " + idProduto,
                null);
    }


    public static List<Produto> getProdutos(Context contexto, int idLista) {
        List<Produto> listaProduto = new ArrayList<>();
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getReadableDatabase();


        String sql = "SELECT idProduto,nome,quantidade FROM produtos WHERE idLista = " + idLista + " ORDER BY idProduto ";
        Cursor cursor = db.rawQuery( sql ,null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Produto produto = new Produto();
                produto.setId(cursor.getInt(0));
                produto.setNome(cursor.getString(1));
                produto.setQuantidade(cursor.getInt(2));
                listaProduto.add(produto);
            } while (cursor.moveToNext());
        }
        return listaProduto;
    }

    public static Produto getProdutoById(Context contexto, int idProduto) {
        Banco banco = new Banco(contexto);
        SQLiteDatabase db = banco.getReadableDatabase();

        String sql = "SELECT * FROM produtos WHERE idProduto = " + idProduto;
        Cursor cursor = db.rawQuery(sql, null);

        if (cursor.getCount() > 0) {
            cursor.moveToFirst();

            Produto produto = new Produto();
            produto.setId(cursor.getInt(0));
            produto.setNome(cursor.getString(1));
            produto.setQuantidade(cursor.getInt(2));

            return produto;
        } else {
            return null;
        }
    }
}
