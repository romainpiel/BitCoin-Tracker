package com.romainpiel.bitcointracker.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.view.holder.BPIViewHolder;

import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<BPIViewHolder> {

    private List<BPI> items;

    public HistoryAdapter() {
        this.items = new ArrayList<>();
    }

    public void setItems(List<BPI> items) {
        this.items.clear();
        this.items.addAll(items);
    }

    @Override
    public BPIViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bpi, viewGroup, false);
        return new BPIViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(BPIViewHolder viewHolder, int i) {
        viewHolder.bind(items.get(i));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
