package com.example.postpc_2021_ex8;

import android.app.Application;

public class RootsApplication extends Application {

    private RootsHolder dataHolder;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        dataHolder = new RootsHolder(this);
    }

    private static RootsApplication instance = null;

    public static RootsApplication getInstance() { return instance; }
    public RootsHolder getDataHolder() { return dataHolder; }
}
