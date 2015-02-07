package com.romainpiel.bitcointracker.view.adapter;

import android.os.Parcel;

import com.google.common.collect.Lists;
import com.romainpiel.bitcointracker.model.BPI;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import java.util.ArrayList;
import java.util.Date;

import static org.fest.assertions.api.Assertions.assertThat;

@RunWith(RobolectricTestRunner.class)
public class HistoryAdapterTest {

    private HistoryAdapter adapter;

    @Before
    public void setUp() throws Exception {
        adapter = new HistoryAdapter();
    }

    @Test
    public void checkItems_withCurrentWithoutItems() {
        adapter.setCurrent(new BPI(new Date(System.currentTimeMillis()), 1.234f, 0.123f));
        adapter.setItems(new ArrayList<BPI>());

        assertThat(adapter.getItemCount()).isEqualTo(1);
        assertThat(adapter.getItemViewType(0)).isEqualTo(HistoryAdapter.TYPE_HEADER);
    }

    @Test
    public void checkItems_withoutCurrentWithItems() {
        adapter.setCurrent(null);
        adapter.setItems(Lists.newArrayList(new BPI(new Date(System.currentTimeMillis()), 1.234f, 0.123f)));

        assertThat(adapter.getItemCount()).isEqualTo(1);
        assertThat(adapter.getItemViewType(0)).isEqualTo(HistoryAdapter.TYPE_ITEM);
    }

    @Test
    public void checkItems_withoutCurrentWithoutItems() {
        adapter.setCurrent(null);
        adapter.setItems(new ArrayList<BPI>());

        assertThat(adapter.getItemCount()).isEqualTo(0);
    }

    @Test
    public void parcelState() {

        adapter.setCurrent(new BPI(new Date(System.currentTimeMillis()), 1.234f, 0.123f));
        adapter.setItems(Lists.newArrayList(new BPI(new Date(System.currentTimeMillis()), 2f, 3f)));

        Parcel parcel = Parcel.obtain();
        HistoryAdapter.State state = adapter.getState();
        state.writeToParcel(parcel, 0);

        HistoryAdapter.State unparceledState = HistoryAdapter.State.CREATOR.createFromParcel(parcel);

        assertThat(unparceledState).isEqualsToByComparingFields(state);
    }
}
