package com.example.lazyloading.listview;

import android.view.View;

import org.json.JSONObject;

public interface ListViewBaseAdapterAutoBindingBluePrint {

    int getCount();

    public View getView(int pos, JSONObject data);

    public View getView(int pos, Object data);

}
