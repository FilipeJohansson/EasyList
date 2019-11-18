package com.megadev.easylist.activity.main;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter holder, int position) {
        Item item = itens.get(position);

        boolean isChecked;

        if (item.getSTA_CHECK() == 1) {
            isChecked = true;
            strikeThroughText(holder.tvProductName);

        } else
            isChecked = false;

        if (item.getDSC_PRODUTO() == null)
            holder.card_item_list_relative.removeView(holder.tvDescricao);
        else
            holder.tvDescricao.setText(item.getDSC_PRODUTO());

        if (item.getQUANTIDADE() == 0)
            holder.tvQuantidade.setVisibility(View.INVISIBLE);
        else
            holder.tvQuantidade.setText(item.getQUANTIDADE() + item.getUN_MEDIDA());

        holder.productCheckBox.setChecked(isChecked);
        holder.tvProductName.setText(item.getNME_PRODUTO());

    }

    @Override
    public int getItemCount() {
        return itens.size();
    }

    class RecyclerViewAdapter extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {

        CheckBox productCheckBox;
        TextView tvProductName;
        TextView tvQuantidade;
        TextView tvDescricao;
        Spinner spinner;
        CardView card_item_list;
        RelativeLayout card_item_list_relative;
        ItemClickListener itemClickListener;

        RecyclerViewAdapter(View itemView, ItemClickListener itemClickListener) {
            super(itemView);

            productCheckBox = itemView.findViewById(R.id.productCheckBox);
            tvProductName = itemView.findViewById(R.id.tvProductName);
            tvDescricao = itemView.findViewById(R.id.tvDescricao);
            tvQuantidade = itemView.findViewById(R.id.tvQuantidade);
            spinner = itemView.findViewById(R.id.spnMedida);
            card_item_list = itemView.findViewById(R.id.card_item_list);
            card_item_list_relative = itemView.findViewById(R.id.card_item_list_relative);

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

    private void strikeThroughText(TextView price){
        price.setPaintFlags(price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
    }

}