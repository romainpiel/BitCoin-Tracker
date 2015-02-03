package com.romainpiel.bitcointracker;

import android.os.Bundle;

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
