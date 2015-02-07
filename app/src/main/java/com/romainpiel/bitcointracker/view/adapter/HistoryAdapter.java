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

    public static final int TYPE_HEADER = 0;
    public static final int TYPE_ITEM = 1;

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

    public BPI getCurrent() {
        return this.state.current;
    }

    public void setCurrent(BPI current) {
        this.state.current = current;
    }

    public List<BPI> getItems() {
        return this.state.items;
    }

    public void setItems(List<BPI> items) {
        this.state.items.clear();
        this.state.items.addAll(items);
    }

    @Override
    public BPIViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bpi_current, viewGroup, false);
                break;
            case TYPE_ITEM:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_bpi, viewGroup, false);
                break;
        }
        return new BPIViewHolder(view, simpleDateFormat);
    }

    @Override
    public void onBindViewHolder(BPIViewHolder viewHolder, int position) {
        int viewType = getItemViewType(position);
        BPI item = null;
        switch (viewType) {
            case TYPE_HEADER:
                item = state.current;
                break;
            case TYPE_ITEM:
                item = state.items.get(position - (hasHeader() ? 1 : 0));
                break;
        }

        if (item != null) {
            viewHolder.bind(item);
        }
    }

    @Override
    public int getItemCount() {
        return state.items.size() + (hasHeader() ? 1 : 0);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 && hasHeader() ? TYPE_HEADER : TYPE_ITEM;
    }

    private boolean hasHeader() {
        return state.current != null;
    }

    public static class State implements Parcelable {

        private BPI current;
        private ArrayList<BPI> items;

        public State() {
            this.items = new ArrayList<>();
        }

        private State(Parcel in) {
            current = in.readParcelable(BPI.class.getClassLoader());
            items = in.createTypedArrayList(BPI.CREATOR);
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel dest, int flags) {
            dest.writeParcelable(current, 0);
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
