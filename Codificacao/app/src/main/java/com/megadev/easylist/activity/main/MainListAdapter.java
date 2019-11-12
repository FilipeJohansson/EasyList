package com.megadev.easylist.activity.main;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.megadev.easylist.R;
import com.megadev.easylist.model.Item;

import java.util.List;

public class MainListAdapter extends RecyclerView.Adapter<MainListAdapter.RecyclerViewAdapter> {

    private Context context;
    private List<Item> itens;
    private ItemClickListener itemClickListener;

    public MainListAdapter(Context context, List<Item> itens, ItemClickListener itemClickListener) {
        this.context = context;
        this.itens = itens;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_item_list,
                parent, false);
        return new RecyclerViewAdapter(view, itemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Item item = itens.get(position);

        Boolean isChecked;

        if (item.getSTA_CHECK() == 1) {
            isChecked = true;
        } else {
            isChecked = false;
        }

        holder.productCheckBox.setChecked(isChecked);
        holder.tvProductName.setText(item.getNME_PRODUTO());
        holder.tvQuantidade.setText(item.getQUANTIDADE() + item.getUN_MEDIDA());
    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener {

        CheckBox productCheckBox;
        TextView tvProductName;
        TextView tvQuantidade;
        CardView card_item_list;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            productCheckBox = itemView.findViewById(R.id.productCheckBox);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            card_item_list = itemView.findViewById(R.id.card_item_list);

            this.itemClickListener = itemClickListener;
            card_item_list.setOnClickListener(this);

        }

        @Override
        public void onClick(View view) {
            itemClickListener.onItemClick(view, getAdapterPosition());

        }
    }

    public interface ItemClickListener {
        void onItemClick(View view, int position);

    }

}