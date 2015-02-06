package com.romainpiel.bitcointracker.view.holder;

import android.support.v7.widget.RecyclerView;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.RelativeSizeSpan;
import android.view.View;
import android.widget.TextView;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;

import java.text.SimpleDateFormat;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;

public class BPIViewHolder extends RecyclerView.ViewHolder {

    @Optional
    @InjectView(R.id.date)
    TextView date;

    @InjectView(R.id.close)
    TextView close;

    @InjectView(R.id.change)
    TextView change;

    private SimpleDateFormat simpleDateFormat;
    private boolean largeCloseTextSize;

    public BPIViewHolder(View itemView, SimpleDateFormat simpleDateFormat) {
        super(itemView);
        ButterKnife.inject(this, itemView);
        this.simpleDateFormat = simpleDateFormat;
        this.largeCloseTextSize = close.getTextSize() >= itemView.getResources().getDimension(R.dimen.text_huge);
    }

    public void bind(BPI item) {
        if (date != null) {
            date.setText(simpleDateFormat.format(item.getDate()));
        }
        SpannableStringBuilder closeText = new SpannableStringBuilder(String.format("$%.2f", item.getClose()));
        if (largeCloseTextSize) {
            // if large text, the price looks better with a small $
            closeText.setSpan(new RelativeSizeSpan(0.4f), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        close.setText(closeText);

        int changeTextColorRes = R.color.textColorSecondary;
        String changeText = "";
        String changeArrow = "";
        if (item.getChange() != null) {
            changeText = String.format("%.2f%%", item.getChange() * 100);
            if (item.getChange() > 0) {
                changeArrow = " ▲";
                changeTextColorRes = R.color.bpiChangeIncrease;
            } else if (item.getChange() < 0) {
                changeArrow = " ▼";
                changeTextColorRes = R.color.bpiChangeDecrease;
            }
        }
        change.setText(changeText + changeArrow);
        change.setTextColor(change.getResources().getColor(changeTextColorRes));
    }
}
