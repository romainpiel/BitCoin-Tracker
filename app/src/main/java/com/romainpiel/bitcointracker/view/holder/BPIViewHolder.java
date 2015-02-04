package com.romainpiel.bitcointracker.view.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BPIViewHolder extends RecyclerView.ViewHolder {

    @InjectView(R.id.date)
    TextView date;

    @InjectView(R.id.close)
    TextView close;

    @InjectView(R.id.change)
    TextView change;

    private SimpleDateFormat simpleDateFormat;

    public BPIViewHolder(View itemView, SimpleDateFormat simpleDateFormat) {
        super(itemView);
        this.simpleDateFormat = simpleDateFormat;
        ButterKnife.inject(this, itemView);
    }

    public void bind(BPI bpi) {
        date.setText(simpleDateFormat.format(bpi.getDate()));
        close.setText(String.format("$%.2f", bpi.getClose()));

        int changeTextColorRes = R.color.textColorSecondary;
        String changeText = String.format("%.2f%%", bpi.getChange() * 100);
        String changeArrow = "";
        if (bpi.getChange() > 0) {
            changeArrow = " ▲";
            changeTextColorRes = R.color.bpiChangeIncrease;
        } else if (bpi.getChange() < 0) {
            changeArrow = " ▼";
            changeTextColorRes = R.color.bpiChangeDecrease;
        }
        change.setText(changeText + changeArrow);
        change.setTextColor(change.getResources().getColor(changeTextColorRes));
    }
}
