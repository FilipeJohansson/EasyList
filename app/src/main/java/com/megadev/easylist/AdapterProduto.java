package com.megadev.easylist;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class AdapterProduto extends BaseAdapter {
    private List<Produto> listaProdutos;
    private Context context;
    private LayoutInflater inflater;

    public AdapterProduto(Context context, List<Produto> listaProdutos) {
        this.context = context;
        this.listaProdutos = listaProdutos;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listaProdutos.size();
    }

    @Override
    public Object getItem(int i) {
        return listaProdutos.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listaProdutos.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ItemSuporte item;

        if (view == null) {
            view = inflater.inflate(R.layout.layout_produto, null);
            item = new ItemSuporte();
            item.tvProduto = (TextView) view.findViewById(R.id.tvProduto);
            item.tvQuantidade = (TextView) view.findViewById(R.id.tvQuantidade);
            item.layoutProduto = (LinearLayout) view.findViewById(R.id.layoutProduto);
            view.setTag(item);
        } else {
            item = (ItemSuporte) view.getTag();
        }

        Produto produto = listaProdutos.get(i);
        item.tvProduto.setText(produto.getNome());
        item.tvQuantidade.setText(String.valueOf(produto.getQuantidade()));

        if (produto.getNome().equals(context.getString(R.string.txtListaVazia))) {
            item.tvQuantidade.setText(" ");
        }

        if (i % 2 == 0) {
            item.layoutProduto.setBackgroundColor(Color.WHITE);
        } else {
            item.layoutProduto.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    private class ItemSuporte {
        TextView tvProduto, tvQuantidade;
        LinearLayout layoutProduto;
    }
}
