package com.romainpiel.bitcointracker.view.adapter;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;
import com.romainpiel.bitcointracker.view.holder.BPIViewHolder;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<BPIViewHolder> {

    private State state;
    private SimpleDateFormat simpleDateFormat;

    public HistoryAdapter() {
        this(new State());
    }

    public HistoryAdapter(State state) {
        this.state = state;
        this.simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    }

    public State getState() {
        return state;
    }

    public void setItems(List<BPI> items) {
        this.state.items.clear();
        this.state.items.addAll(items);
    }

    @Override
    public BPIViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bpi, viewGroup, false);
        return new BPIViewHolder(itemView, simpleDateFormat);
    }

    @Override
    public void onBindViewHolder(BPIViewHolder viewHolder, int i) {
        viewHolder.bind(state.items.get(i));
    }

    @Override
    public int getItemCount() {
        return state.items.size();
    }

    public static class State implements Parcelable {

        private ArrayList<BPI> items;

        public State() {
            this.items = new ArrayList<>();
        }

        private State(Parcel in) {
            items = in.createTypedArrayList(BPI.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeTypedList(items);
        }

        public static final Creator<State> CREATOR = new Creator<State>() {
            public State createFromParcel(Parcel source) {
                return new State(source);
            }

            public State[] newArray(int size) {
                return new State[size];
            }
        };
    }
}
