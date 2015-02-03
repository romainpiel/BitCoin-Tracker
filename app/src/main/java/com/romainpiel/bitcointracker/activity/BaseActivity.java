package com.romainpiel.bitcointracker.activity;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.romainpiel.bitcointracker.BitCoinApplication;
import com.romainpiel.bitcointracker.R;

public abstract class BaseActivity extends ActionBarActivity {

    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResource());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    protected abstract int getLayoutResource();

    public void inject(Object object) {
        ((BitCoinApplication) super.getApplication()).inject(object);
    }

    protected void setActionBarIcon(@Nullable Drawable icon) {
        toolbar.setNavigationIcon(icon);
    }
}