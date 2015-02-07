package com.romainpiel.bitcointracker.view.holder;

import android.view.View;

import com.romainpiel.bitcointracker.R;
import com.romainpiel.bitcointracker.model.BPI;

import org.fest.assertions.api.ANDROID;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.ParameterizedRobolectricTestRunner;
import org.robolectric.Robolectric;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

@RunWith(ParameterizedRobolectricTestRunner.class)
public class BPIViewHolderTest {

    @ParameterizedRobolectricTestRunner.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                {R.layout.item_bpi_current, new SimpleDateFormat("dd/MM/yyyy")},
                {R.layout.item_bpi, new SimpleDateFormat("dd/MM/yyyy")}
        });
    }

    private int layoutId;
    private SimpleDateFormat simpleDateFormat;

    private BPIViewHolder viewHolder;

    public BPIViewHolderTest(int layoutId, SimpleDateFormat simpleDateFormat) {
        this.layoutId = layoutId;
        this.simpleDateFormat = simpleDateFormat;
    }

    @Before
    public void setUp() throws Exception {
        viewHolder = new BPIViewHolder(View.inflate(Robolectric.application, layoutId, null), simpleDateFormat);
    }

    private int getColor(int resId) {
        return Robolectric.application.getResources().getColor(resId);
    }

    @Test
    public void bind_increase() {
        BPI bpi = new BPI(new Date(System.currentTimeMillis()), 225.25f, 0.0124f);

        viewHolder.bind(bpi);

        if (viewHolder.date != null) {
            ANDROID.assertThat(viewHolder.date).hasTextString(simpleDateFormat.format(bpi.getDate()));
        }
        ANDROID.assertThat(viewHolder.close).hasTextString("$" + bpi.getClose());
        ANDROID.assertThat(viewHolder.change).hasTextString((bpi.getChange() * 100) + "% ▲");
        ANDROID.assertThat(viewHolder.change).hasCurrentTextColor(getColor(R.color.bpiChangeIncrease));
    }

    @Test
    public void bind_decrease() {
        BPI bpi = new BPI(new Date(System.currentTimeMillis()), 225.25f, - 0.0124f);

        viewHolder.bind(bpi);

        if (viewHolder.date != null) {
            ANDROID.assertThat(viewHolder.date).hasTextString(simpleDateFormat.format(bpi.getDate()));
        }
        ANDROID.assertThat(viewHolder.close).hasTextString("$" + bpi.getClose());
        ANDROID.assertThat(viewHolder.change).hasTextString((bpi.getChange() * 100) + "% ▼");
        ANDROID.assertThat(viewHolder.change).hasCurrentTextColor(getColor(R.color.bpiChangeDecrease));
    }

    @Test
    public void bind_noChange() {
        BPI bpi = new BPI(new Date(System.currentTimeMillis()), 225.25f);

        viewHolder.bind(bpi);

        if (viewHolder.date != null) {
            ANDROID.assertThat(viewHolder.date).hasTextString(simpleDateFormat.format(bpi.getDate()));
        }
        ANDROID.assertThat(viewHolder.close).hasTextString("$" + bpi.getClose());
        ANDROID.assertThat(viewHolder.change).hasTextString("");
    }
}
