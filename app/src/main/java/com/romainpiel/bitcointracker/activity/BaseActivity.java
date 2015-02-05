package com.romainpiel.bitcointracker.activity;

import android.support.v7.app.ActionBarActivity;

import com.romainpiel.bitcointracker.BitCoinApplication;

public abstract class BaseActivity extends ActionBarActivity {

    public void inject(Object object) {
        ((BitCoinApplication) super.getApplication()).inject(object);
    }
}