package com.example.lifeline;

import android.app.Application;

public class GlobalCount extends Application{

    private int count=0;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
