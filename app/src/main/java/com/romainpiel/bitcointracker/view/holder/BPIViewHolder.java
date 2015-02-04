package com.romainpiel.bitcointracker.view.holder;

import android.view.View;
import android.widget.TextView;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class BPIViewHolder extends BindableViewHolder<BPI> {

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

    @Override
    public void bind(BPI item) {
        date.setText(simpleDateFormat.format(item.getDate()));
        close.setText(String.format("$%.2f", item.getClose()));

        int changeTextColorRes = R.color.textColorSecondary;
        String changeText = String.format("%.2f%%", item.getChange() * 100);
        String changeArrow = "";
        if (item.getChange() > 0) {
            changeArrow = " ▲";
            changeTextColorRes = R.color.bpiChangeIncrease;
        } else if (item.getChange() < 0) {
            changeArrow = " ▼";
            changeTextColorRes = R.color.bpiChangeDecrease;
        }
        change.setText(changeText + changeArrow);
        change.setTextColor(change.getResources().getColor(changeTextColorRes));
    }
}
