package com.romainpiel.bitcointracker.activity;

import android.os.Bundle;

import com.romainpiel.bitcointracker.R;

public class MainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setActionBarIcon(null);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_main;
    }
}
