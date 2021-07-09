package com.anilduyguc.cryptocurrency.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.anilduyguc.cryptocurrency.R;
import com.anilduyguc.cryptocurrency.model.CryptoModel;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RowHolder> {
    private ArrayList<CryptoModel> cryptoModelArrayList;
    private String[] colors={"#c3ce11","#34ce11","#11cec9","#e613e7","#13e7d7","#fb5f5f","#b224f2","#aecdd0"};

    public RecyclerViewAdapter(ArrayList<CryptoModel> cryptoModelArrayList) {
        this.cryptoModelArrayList = cryptoModelArrayList;
    }

    @NonNull
    @Override
    public RowHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view=layoutInflater.inflate(R.layout.row_layout,parent,false);
        return new RowHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.RowHolder holder, int position) {
        holder.bind(cryptoModelArrayList.get(position),colors,position);
    }

    @Override
    public int getItemCount() {
        return cryptoModelArrayList.size();
    }

    public class RowHolder extends RecyclerView.ViewHolder {
        TextView textName,textPrice;
        public RowHolder(@NonNull View itemView) {
            super(itemView);

        }

        public void bind(CryptoModel model,String[] colors,Integer postion){
            itemView.setBackgroundColor(Color.parseColor(colors[postion % 8]));
            textPrice=itemView.findViewById(R.id.textPrice);
            textName=itemView.findViewById(R.id.textName);

            textName.setText(model.currency);
            textPrice.setText(model.price);
        }
    }
}
