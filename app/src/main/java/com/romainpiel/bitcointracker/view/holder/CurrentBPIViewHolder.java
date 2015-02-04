package com.romainpiel.bitcointracker.view.holder;

import android.view.View;
import android.widget.TextView;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class CurrentBPIViewHolder extends BindableViewHolder<BPI> {

    @InjectView(R.id.close)
    TextView close;

    public CurrentBPIViewHolder(View itemView) {
        super(itemView);
        ButterKnife.inject(this, itemView);
    }

    @Override
    public void bind(BPI item) {
        close.setText(String.format("$%.2f", item.getClose()));
    }
}
