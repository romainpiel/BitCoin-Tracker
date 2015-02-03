package com.romainpiel.bitcointracker;

import android.app.Application;

import dagger.ObjectGraph;

public class BitCoinApplication extends Application {

    private ObjectGraph graph;

    @Override
    public void onCreate() {
        super.onCreate();

        graph = ObjectGraph.create(getModules());
    }

    protected Object[] getModules() {
        return new Object[]{
                new BitCoinApplicationModule()
        };
    }

    public void inject(Object object) {
        graph.inject(object);
    }
}
