package com.megadev.easylist.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.megadev.easylist.R;
import com.megadev.easylist.model.Lista;

import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Lista> listas;
    private ItemClickListener itemClickListener;

    public MainAdapter(Context context, List<Lista> listas, ItemClickListener itemClickListener) {
        this.context = context;
        this.listas = listas;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_list,
                parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Lista lista = listas.get(position);
        holder.tvListName.setText(lista.getNME_LISTA());
    }

    @Override
    public int getItemCount() {
        return listas.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        TextView tvListName;
        CardView card_item_list;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            tvListName = itemView.findViewById(R.id.tvListName);
            card_item_list = itemView.findViewById(R.id.card_item_list);

            this.itemClickListener = itemClickListener;
            card_item_list.setOnClickListener(this);
            card_item_list.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());

        }

        @Override
        public boolean onLongClick(View view) {
            itemClickListener.onLongItemClick(view, getAdapterPosition());
            return true;
        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);
        void onLongItemClick(View view, int position);
    }

}
