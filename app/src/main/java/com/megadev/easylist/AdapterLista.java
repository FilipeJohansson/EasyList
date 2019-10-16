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

public class AdapterLista extends BaseAdapter {
    private List<Lista> listas;
    private Context context;
    private LayoutInflater inflater;

    public AdapterLista(Context context, List<Lista> listas) {
        this.context = context;
        this.listas = listas;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listas.size();
    }

    @Override
    public Object getItem(int i) {
        return listas.get(i);
    }

    @Override
    public long getItemId(int i) {
        return listas.get(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        ItemSuporte item;

        if (view == null) {
            view = inflater.inflate(R.layout.layout_listas, null);
            item = new ItemSuporte();
            item.tvNome = (TextView) view.findViewById(R.id.tvListaNome);
            item.layoutListas = (LinearLayout) view.findViewById(R.id.layoutListas);
            view.setTag(item);
        } else {
            item = (ItemSuporte) view.getTag();
        }

        Lista _lista = listas.get(i);
        item.tvNome.setText(_lista.getNome());

        if (i % 2 == 0) {
            item.layoutListas.setBackgroundColor(Color.WHITE);
        } else {
            item.layoutListas.setBackgroundColor(Color.WHITE);
        }

        return view;
    }

    private class ItemSuporte {
        TextView tvId, tvNome;
        LinearLayout layoutListas;
    }
}
