package com.op.materialdesigndemo;

import android.app.Application;

import com.op.materialdesigndemo.db.BehaviorDatabaseHelper;

public class NewsMDApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        BehaviorDatabaseHelper.getInstance(this).init();
    }
}
